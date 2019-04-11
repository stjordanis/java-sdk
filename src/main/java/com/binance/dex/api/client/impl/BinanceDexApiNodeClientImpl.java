package com.binance.dex.api.client.impl;

import com.binance.dex.api.client.BinanceDexApiClientGenerator;
import com.binance.dex.api.client.BinanceDexApiNodeClient;
import com.binance.dex.api.client.BinanceDexNodeApi;
import com.binance.dex.api.client.domain.Account;
import com.binance.dex.api.client.domain.BlockInfo;
import com.binance.dex.api.client.domain.jsonrpc.AccountResult;
import com.binance.dex.api.client.domain.jsonrpc.BlockInfoResult;
import com.binance.dex.api.client.domain.jsonrpc.JsonRpcResponse;
import com.binance.dex.api.client.encoding.Crypto;
import com.binance.dex.api.proto.AppAccount;
import org.spongycastle.util.encoders.Hex;

import java.io.IOException;

public class BinanceDexApiNodeClientImpl implements BinanceDexApiNodeClient {

    private BinanceDexNodeApi binanceDexNodeApi;

    private final String ARG_ACCOUNT_PREFIX = Hex.toHexString("account:".getBytes());

    public BinanceDexApiNodeClientImpl(String nodeUrl) {
        this.binanceDexNodeApi = BinanceDexApiClientGenerator.createService(BinanceDexNodeApi.class, nodeUrl);
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


            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public BlockInfo getBlockInfo(Long height) {
        try {
            JsonRpcResponse<BlockInfoResult> response = binanceDexNodeApi.getBlockInfo(String.format("\"tx.height=%s\"", height.toString())).execute().body();
            BlockInfoResult result = response.getResult();
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
