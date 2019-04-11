package com.binance.dex.api.client;

import com.binance.dex.api.client.impl.BinanceDexApiAsyncRestClientImpl;
import com.binance.dex.api.client.impl.BinanceDexApiNodeClientImpl;
import com.binance.dex.api.client.impl.BinanceDexApiRestClientImpl;

public class BinanceDexApiClientFactory {
    private BinanceDexApiClientFactory() {
    }

    public static BinanceDexApiClientFactory newInstance() {
        return new BinanceDexApiClientFactory();
    }

    public BinanceDexApiRestClient newRestClient() {
        return newRestClient(BinanceDexEnvironment.PROD.getBaseUrl());
    }

    public BinanceDexApiRestClient newRestClient(String baseUrl) {
        return new BinanceDexApiRestClientImpl(baseUrl);
    }

    public BinanceDexApiNodeClient newNodeRpcClient() {
        return newNodeRpcClient(BinanceDexEnvironment.LOCAL_NODE.getBaseUrl());
    }

    public BinanceDexApiNodeClient newNodeRpcClient(String baseUrl) {
        return new BinanceDexApiNodeClientImpl(baseUrl);
    }

    public BinanceDexApiAsyncRestClient newAsyncRestClient() {
        return newAsyncRestClient(BinanceDexEnvironment.PROD.getBaseUrl());
    }

    public BinanceDexApiAsyncRestClient newAsyncRestClient(String baseUrl) {
        return new BinanceDexApiAsyncRestClientImpl(baseUrl);
    }

}
