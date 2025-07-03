package uk.co.fleabay.demo.controller.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CombinedFleabayProduct(int productUid,
                                     String productType,
                                     String name,
                                     String fullUrl,
                                     double unitPrice,
                                     String unitPriceMeasure,
                                     int unitPriceMeasureAmount) {

    @JsonCreator
    public CombinedFleabayProduct(@JsonProperty("product_uid") int productUid, @JsonProperty("product_type") String productType, @JsonProperty("name") String name, @JsonProperty("full_url") String fullUrl, @JsonProperty("unit_price") double unitPrice, @JsonProperty("unit_price_measure") String unitPriceMeasure, @JsonProperty("unit_price_measure_amount") int unitPriceMeasureAmount ) {
        this.productUid = productUid;
        this.unitPrice = unitPrice;
        this.unitPriceMeasure = unitPriceMeasure;
        this.unitPriceMeasureAmount = unitPriceMeasureAmount;
        this.productType = productType;
        this.name = name;
        this.fullUrl = fullUrl;

    }
}
