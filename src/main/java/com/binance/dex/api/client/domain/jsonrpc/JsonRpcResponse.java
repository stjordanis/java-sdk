package com.binance.dex.api.client.domain.jsonrpc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonRpcResponse<R> {
    private R result;

    public R getResult() {
        return result;
    }

    public void setResult(R result) {
        this.result = result;
    }
}
