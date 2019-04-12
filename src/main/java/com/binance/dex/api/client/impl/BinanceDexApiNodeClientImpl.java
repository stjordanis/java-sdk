package com.binance.dex.api.client.impl;

import com.binance.dex.api.client.*;
import com.binance.dex.api.client.domain.Account;
import com.binance.dex.api.client.domain.Balance;
import com.binance.dex.api.client.domain.Infos;
import com.binance.dex.api.client.domain.TransactionMetadata;
import com.binance.dex.api.client.domain.broadcast.*;
import com.binance.dex.api.client.domain.jsonrpc.AccountResult;
import com.binance.dex.api.client.domain.jsonrpc.BlockInfoResult;
import com.binance.dex.api.client.domain.jsonrpc.JsonRpcResponse;
import com.binance.dex.api.client.encoding.Crypto;
import com.binance.dex.api.client.encoding.message.MessageType;
import com.binance.dex.api.client.encoding.message.TransactionRequestAssembler;
import com.binance.dex.api.proto.AppAccount;
import com.binance.dex.api.proto.Send;
import com.binance.dex.api.proto.StdTx;
import com.binance.dex.api.proto.Token;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import org.spongycastle.util.encoders.Hex;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class BinanceDexApiNodeClientImpl implements BinanceDexApiNodeClient {

    private BinanceDexNodeApi binanceDexNodeApi;

    private String hrp;

    private final String ARG_ACCOUNT_PREFIX = Hex.toHexString("account:".getBytes());

    public BinanceDexApiNodeClientImpl(String nodeUrl, String hrp) {
        this.binanceDexNodeApi = BinanceDexApiClientGenerator.createService(BinanceDexNodeApi.class, nodeUrl);
        this.hrp = hrp;
    }

    @Override
    public Infos getNodeInfo() {
//        try {
//            return null;
//            return binanceDexNodeApi.getNodeStatus().execute().body().getResult();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        return null;
    }

    @Override
    public Account getAccount(String address) {
        String encodedAddress = "0x" + ARG_ACCOUNT_PREFIX + Hex.toHexString(Crypto.decodeAddress(address));
        try {
            JsonRpcResponse<AccountResult> response = binanceDexNodeApi.getAccount(encodedAddress).execute().body();
            byte[] value = response.getResult().getResponse().getValue();
            byte[] array = new byte[value.length - 4];
            System.arraycopy(value, 4, array, 0, array.length);
            AppAccount account = AppAccount.parseFrom(array);
            return convert(account);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Transaction> getBlockTransactions(Long height) {
        try {
            JsonRpcResponse<BlockInfoResult> response = binanceDexNodeApi
                    .getBlockInfo("\"tx.height=" + height.toString() + "\"").execute().body();
            return response.getResult().getTxs().stream()
                    .map(this::convert).flatMap(List::stream).collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<TransactionMetadata> transfer(Transfer transfer, Wallet wallet, TransactionOption options, boolean sync)
            throws IOException, NoSuchAlgorithmException {
        wallet.ensureWalletIsReady(this);
        TransactionRequestAssembler assembler = new TransactionRequestAssembler(wallet, options);
        String requestPayload = "0x" + assembler.buildTransferPayload(transfer);
        System.out.println(requestPayload);
        return broadcast(requestPayload, sync, wallet);
    }

    protected List<Transaction> convert(com.binance.dex.api.client.domain.jsonrpc.BlockInfoResult.Transaction txMessage) {
        try {
            byte[] value = txMessage.getTx();
            byte[] array = new byte[value.length - 6];
            System.arraycopy(value, 6, array, 0, array.length);
            StdTx stdTx = StdTx.parseFrom(array);
            return stdTx.getMsgsList().stream()
                    .map(byteString -> {
                        byte[] bytes = byteString.toByteArray();
                        Transaction transaction = this.convert(bytes);
                        transaction.setHash(txMessage.getHash());
                        transaction.setHeight(txMessage.getHeight());
                        return transaction;
                    }).collect(Collectors.toList());
        } catch (InvalidProtocolBufferException e) {
            throw new RuntimeException(e);
        }
    }


    protected Transaction convert(byte[] bytes) {
        try {
            MessageType messageType = MessageType.getMessageType(bytes);
            switch (messageType) {
                case Send:
                    return convertTransfer(bytes);
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected Transaction convertTransfer(byte[] value) throws InvalidProtocolBufferException {
        byte[] array = new byte[value.length - 4];
        System.arraycopy(value, 4, array, 0, array.length);
        Send send = Send.parseFrom(array);
        MultiTransfer transfer = new MultiTransfer();
        transfer.setFromAddress(Crypto.encodeAddress(hrp, send.getInputsList().get(0).getAddress().toByteArray()));
        transfer.setOutputs(send.getOutputsList().stream().map(o -> {
            Output output = new Output();
            output.setAddress(Crypto.encodeAddress(hrp, o.getAddress().toByteArray()));
            output.setTokens(o.getCoinsList().stream()
                    .map(coin -> new OutputToken(coin.getDenom(), "" + coin.getAmount()))
                    .collect(Collectors.toList()));
            return output;
        }).collect(Collectors.toList()));
        Transaction transaction = new Transaction();
        transaction.setTxType(TxType.TRANSFER);
        transaction.setRealTx(transfer);
        return transaction;
    }


    protected Account convert(AppAccount appAccount) {
        Account account = new Account();
        account.setAccountNumber(new Long(appAccount.getBase().getAccountNumber()).intValue());
        account.setAddress(Crypto.encodeAddress(hrp, appAccount.getBase().getAddress().toByteArray()));

        byte[] bytes = appAccount.getBase().getPublicKey().toByteArray();
        List<Integer> publicKey = Lists.newArrayList();
        for (int i = 2; i < bytes.length; i++) {
            publicKey.add((int) bytes[i]);
        }
        account.setPublicKey(publicKey);
        account.setSequence(appAccount.getBase().getSequence());

        Map<String, Long> free = appAccount.getBase().getCoinsList().stream().collect(Collectors.toMap(Token::getDenom, Token::getAmount));
        Map<String, Long> locked = appAccount.getLockedList().stream().collect(Collectors.toMap(Token::getDenom, Token::getAmount));
        Map<String, Long> frozen = appAccount.getFrozenList().stream().collect(Collectors.toMap(Token::getDenom, Token::getAmount));

        Set<String> symbolSet = Sets.union(Sets.union(free.keySet(), locked.keySet()), frozen.keySet());
        List<Balance> balanceList = symbolSet.stream()
                .map(symbol -> new Balance(symbol, free.getOrDefault(symbol, 0L).toString(), locked.getOrDefault(symbol, 0L).toString(), frozen.getOrDefault(symbol, 0L).toString()))
                .collect(Collectors.toList());

        account.setBalances(balanceList);
        return account;
    }

    protected List<TransactionMetadata> broadcast(String requestBody, boolean sync, Wallet wallet) {
        try {
            List<TransactionMetadata> metadatas = null;
            binanceDexNodeApi.broadcast(requestBody).execute().body();
            if (!metadatas.isEmpty() && metadatas.get(0).isOk()) {
                wallet.increaseAccountSequence();
            }
            return metadatas;
        } catch (BinanceDexApiException | IOException e) {
            wallet.invalidAccountSequence();
            throw new RuntimeException(e);
        }
    }
}
