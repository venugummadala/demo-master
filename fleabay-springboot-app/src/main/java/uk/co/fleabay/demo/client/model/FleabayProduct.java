package uk.co.fleabay.demo.client.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

//@JsonIgnoreProperties(ignoreUnknown = true)
public record FleabayProduct(int productUid,
                             String productType,
                             String name,
                             String fullUrl) {
    @JsonCreator
    public FleabayProduct(@JsonProperty("product_uid") int productUid, @JsonProperty("product_type") String productType, @JsonProperty("name") String name, @JsonProperty("full_url") String fullUrl ) {
        this.productUid = productUid;
        this.productType = productType;
        this.name = name;
        this.fullUrl = fullUrl;

    }

}
