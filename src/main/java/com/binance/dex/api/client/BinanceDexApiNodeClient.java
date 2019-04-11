package com.binance.dex.api.client;

import com.binance.dex.api.client.domain.*;
import com.binance.dex.api.client.domain.broadcast.*;
import com.binance.dex.api.client.domain.request.ClosedOrdersRequest;
import com.binance.dex.api.client.domain.request.OpenOrdersRequest;
import com.binance.dex.api.client.domain.request.TradesRequest;
import com.binance.dex.api.client.domain.request.TransactionsRequest;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface BinanceDexApiNodeClient extends BinanceDexApiRestClient {

    BlockInfo getBlockInfo(Long height);

    @Override
    default Time getTime() {
        throw new UnsupportedOperationException();
    }

    @Override
    default Infos getNodeInfo() {
        throw new UnsupportedOperationException();
    }

    @Override
    default Validators getValidators() {
        throw new UnsupportedOperationException();
    }

    @Override
    default List<Peer> getPeers() {
        throw new UnsupportedOperationException();
    }

    @Override
    default List<Fees> getFees() {
        throw new UnsupportedOperationException();
    }

    @Override
    default List<Market> getMarkets(Integer limit) {
        throw new UnsupportedOperationException();
    }

    @Override
    default AccountSequence getAccountSequence(String address) {
        throw new UnsupportedOperationException();
    }

    @Override
    default TransactionMetadata getTransactionMetadata(String hash) {
        throw new UnsupportedOperationException();
    }

    @Override
    default List<Token> getTokens(Integer limit) {
        throw new UnsupportedOperationException();
    }

    @Override
    default OrderBook getOrderBook(String symbol, Integer limit) {
        throw new UnsupportedOperationException();
    }

    @Override
    default List<Candlestick> getCandleStickBars(String symbol, CandlestickInterval interval) {
        throw new UnsupportedOperationException();
    }

    @Override
    default List<Candlestick> getCandleStickBars(String symbol, CandlestickInterval interval, Integer limit, Long startTime, Long endTime) {
        throw new UnsupportedOperationException();
    }

    @Override
    default OrderList getOpenOrders(String address) {
        throw new UnsupportedOperationException();
    }

    @Override
    default OrderList getOpenOrders(OpenOrdersRequest request) {
        throw new UnsupportedOperationException();
    }

    @Override
    default OrderList getClosedOrders(String address) {
        throw new UnsupportedOperationException();
    }

    @Override
    default OrderList getClosedOrders(ClosedOrdersRequest request) {
        throw new UnsupportedOperationException();
    }

    @Override
    default Order getOrder(String id) {
        throw new UnsupportedOperationException();
    }

    @Override
    default List<TickerStatistics> get24HrPriceStatistics() {
        throw new UnsupportedOperationException();
    }

    @Override
    default TradePage getTrades() {
        throw new UnsupportedOperationException();
    }

    @Override
    default TradePage getTrades(TradesRequest request) {
        throw new UnsupportedOperationException();
    }

    @Override
    default TransactionPage getTransactions(String address) {
        throw new UnsupportedOperationException();
    }

    @Override
    default TransactionPage getTransactions(TransactionsRequest request) {
        throw new UnsupportedOperationException();
    }

    @Override
    default List<TransactionMetadata> newOrder(NewOrder newOrder, Wallet wallet, TransactionOption options, boolean sync) throws IOException, NoSuchAlgorithmException {
        throw new UnsupportedOperationException();
    }

    @Override
    default List<TransactionMetadata> vote(Vote vote, Wallet wallet, TransactionOption options, boolean sync) throws IOException, NoSuchAlgorithmException {
        throw new UnsupportedOperationException();
    }

    @Override
    default List<TransactionMetadata> cancelOrder(CancelOrder cancelOrder, Wallet wallet, TransactionOption options, boolean sync) throws IOException, NoSuchAlgorithmException {
        throw new UnsupportedOperationException();
    }

    @Override
    default List<TransactionMetadata> transfer(Transfer transfer, Wallet wallet, TransactionOption options, boolean sync) throws IOException, NoSuchAlgorithmException {
        throw new UnsupportedOperationException();
    }

    @Override
    default List<TransactionMetadata> multiTransfer(MultiTransfer multiTransfer, Wallet wallet, TransactionOption options, boolean sync) throws IOException, NoSuchAlgorithmException {
        throw new UnsupportedOperationException();
    }

    @Override
    default List<TransactionMetadata> freeze(TokenFreeze freeze, Wallet wallet, TransactionOption options, boolean sync) throws IOException, NoSuchAlgorithmException {
        throw new UnsupportedOperationException();
    }

    @Override
    default List<TransactionMetadata> unfreeze(TokenUnfreeze unfreeze, Wallet wallet, TransactionOption options, boolean sync) throws IOException, NoSuchAlgorithmException {
        throw new UnsupportedOperationException();
    }
}
