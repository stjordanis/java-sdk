package com.binance.dex.api.client.examples;


import com.binance.dex.api.client.BinanceDexApiClientFactory;
import com.binance.dex.api.client.BinanceDexApiNodeClient;
import com.binance.dex.api.client.BinanceDexEnvironment;
import com.binance.dex.api.client.Wallet;
import com.binance.dex.api.client.domain.Account;
import com.binance.dex.api.client.domain.Infos;
import com.binance.dex.api.client.domain.TransactionMetadata;
import com.binance.dex.api.client.domain.broadcast.Transaction;
import com.binance.dex.api.client.domain.broadcast.TransactionOption;
import com.binance.dex.api.client.domain.broadcast.Transfer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.spongycastle.util.encoders.Hex;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;


public class NodeClientExample {

    private static final String ARG_ACCOUNT_PREFIX = Hex.toHexString("account:".getBytes());

    private BinanceDexApiNodeClient binanceDexNodeApi = null;
    private Wallet wallet = null;
    private String mnemonicCode = "elder return supreme dragon moon flight orchard bus wire scare pig sure rather jealous gasp betray parent wealth basket garlic sock quality tourist abstract";


    @Before
    public void setup() throws IOException {
        binanceDexNodeApi = BinanceDexApiClientFactory.newInstance().newNodeRpcClient();
        wallet = Wallet.createWalletFromMnemonicCode(Arrays.asList(mnemonicCode.split(" ")), BinanceDexEnvironment.TEST_NET);
    }

    @Test
    public void testAccount() {
        String address = "tbnb16hywxpvvkaz6cecjz89mf2w0da3vfeg6z6yky2";
        Account account = binanceDexNodeApi.getAccount(address);
        Assert.assertEquals(address, account.getAddress());
    }

    @Test
    public void testBlockTransaction() {
        Long height = 7794210L;
        List<Transaction> transactions = binanceDexNodeApi.getBlockTransactions(height);
        Assert.assertNotNull(transactions);
        Assert.assertTrue(transactions.size() == 1);
    }

    @Test
    public void testTransfer() throws IOException, NoSuchAlgorithmException {
        String symbol = "BNB";
        Transfer transfer = new Transfer();
        transfer.setCoin(symbol);
        transfer.setFromAddress(wallet.getAddress());
        transfer.setToAddress("tbnb16hywxpvvkaz6cecjz89mf2w0da3vfeg6z6yky2");
        transfer.setAmount("1.0");
        TransactionOption options = new TransactionOption("test", 1, null);
        List<TransactionMetadata> resp = binanceDexNodeApi.transfer(transfer, wallet, options, true);
    }

    @Test
    public void testNodeInfo() {
        Infos nodeInfo = binanceDexNodeApi.getNodeInfo();
        Assert.assertNotNull(nodeInfo);
    }
}
