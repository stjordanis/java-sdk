package com.binance.dex.api.client.domain.broadcast;

public class Transaction {
    private Long height;
    private String hash;

    private TxType txType;
    private Object realTx;

    public Long getHeight() {
        return height;
    }

    public void setHeight(Long height) {
        this.height = height;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public TxType getTxType() {
        return txType;
    }

    public void setTxType(TxType txType) {
        this.txType = txType;
    }

    public Object getRealTx() {
        return realTx;
    }

    public void setRealTx(Object realTx) {
        this.realTx = realTx;
    }
}
