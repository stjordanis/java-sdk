package com.binance.dex.api.client;

import com.binance.dex.api.client.domain.jsonrpc.AccountResult;
import com.binance.dex.api.client.domain.jsonrpc.BlockInfoResult;
import com.binance.dex.api.client.domain.jsonrpc.JsonRpcResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BinanceDexNodeApi {

    @GET("/abci_query?path=%22/store/acc/key%22")
    Call<JsonRpcResponse<AccountResult>> getAccount(@Query("data") String address);

    @GET("/tx_search")
    Call<JsonRpcResponse<BlockInfoResult>> getBlockInfo(@Query("query") String height);
}