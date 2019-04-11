package com.binance.dex.api.client.examples;


import com.binance.dex.api.client.*;
import com.binance.dex.api.client.domain.jsonrpc.AccountResult;
import com.binance.dex.api.client.domain.jsonrpc.JsonRpcResponse;
import com.binance.dex.api.client.encoding.Crypto;
import com.binance.dex.api.proto.AppAccount;
import org.spongycastle.util.encoders.Hex;

import java.io.IOException;

public class AccountExample {


    private static final String ARG_ACCOUNT_PREFIX = Hex.toHexString("account:".getBytes());

    public static void main(String[] args) {

        String address = "tbnb16hywxpvvkaz6cecjz89mf2w0da3vfeg6z6yky2";
        BinanceDexNodeApi binanceDexNodeApi = BinanceDexApiClientGenerator.createService(BinanceDexNodeApi.class, BinanceDexEnvironment.LOCAL_NODE.getBaseUrl());
        String encodedAddress = "0x" + ARG_ACCOUNT_PREFIX + Hex.toHexString(Crypto.decodeAddress(address));
        try {
            JsonRpcResponse<AccountResult> response = binanceDexNodeApi.getAccount(encodedAddress).execute().body();
            byte[] value = response.getResult().getResponse().getValue();
            System.out.println(Hex.toHexString(value));
            byte[] realArray = new byte[value.length - 4];
            System.arraycopy(value, 4, realArray, 0, realArray.length);

            AppAccount account = AppAccount.parseFrom(realArray);
            System.out.print(account.getCoins(0).getDenom());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
