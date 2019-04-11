package com.binance.dex.api.client.domain.jsonrpc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BlockInfoResult {

    private Long height;
    private Results results;

    public Long getHeight() {
        return height;
    }

    public void setHeight(Long height) {
        this.height = height;
    }

    public Results getResults() {
        return results;
    }

    public void setResults(Results results) {
        this.results = results;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Results {
        private List<DeliverTx> DeliverTx;

        public List<DeliverTx> getDeliverTx() {
            return DeliverTx;
        }

        @JsonProperty("DeliverTx")
        public void setDeliverTx(List<DeliverTx> deliverTx) {
            DeliverTx = deliverTx;
        }
    }

    public static class DeliverTx {
        private String data;
        private String log;
        private List<Tag> tags;

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public String getLog() {
            return log;
        }

        public void setLog(String log) {
            this.log = log;
        }

        public List<Tag> getTags() {
            return tags;
        }

        public void setTags(List<Tag> tags) {
            this.tags = tags;
        }
    }

    public static class Tag {

        private String key;
        private String value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
