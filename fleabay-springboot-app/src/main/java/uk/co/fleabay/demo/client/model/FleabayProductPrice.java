package uk.co.fleabay.demo.client.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

//@JsonIgnoreProperties(ignoreUnknown = true)
/*public record FleabayProductPrice(int productUid,
                          double unitPrice,
                          String unitPriceMeasure,
                          int unitPriceMeasureAmount) {

*/
    //@JsonCreator
    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)

    public record FleabayProductPrice(@JsonProperty("product_uid") int productUid, @JsonProperty("unit_price") double unitPrice, @JsonProperty("unit_price_measure") String unitPriceMeasure, @JsonProperty("unit_price_measure_amount") int unitPriceMeasureAmount ) {


    }
