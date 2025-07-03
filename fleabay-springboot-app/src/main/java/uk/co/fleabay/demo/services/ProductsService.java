package uk.co.fleabay.demo.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import uk.co.fleabay.demo.client.FleabayWebClient;
import uk.co.fleabay.demo.client.model.FleabayProduct;
import uk.co.fleabay.demo.client.model.FleabayProductPrice;
import uk.co.fleabay.demo.controller.response.CombinedFleabayProduct;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductsService {
    private static final Logger logger = LoggerFactory.getLogger(ProductsService.class);

    private final FleabayWebClient fleabayWebClient;

    // Use constructor injection - it's a best practice.
    public ProductsService(FleabayWebClient fleabayWebClient) {
        this.fleabayWebClient = fleabayWebClient;
    }

    // The service now returns a Mono, keeping the chain reactive.
    public Mono<List<CombinedFleabayProduct>> getUnifiedProduct(String productType) {
        // Fetch products and prices in parallel
        Mono<Set<FleabayProduct>> productsMono = fleabayWebClient.getProducts(productType);
        Mono<Set<FleabayProductPrice>> pricesMono = fleabayWebClient.getProductsPrices(productType);

        // Zip the results together. The logic inside .map() only runs when both Monos have completed.
        return Mono.zip(productsMono, pricesMono)
                .map(this::mergeProductsWithPrices);
    }

    // The merge logic is now cleaner and operates on the results from the parallel calls.
    private List<CombinedFleabayProduct> mergeProductsWithPrices(Tuple2<Set<FleabayProduct>, Set<FleabayProductPrice>> tuple) {
        Set<FleabayProduct> fleabayProducts = tuple.getT1();
        Set<FleabayProductPrice> fleabayProductPrices = tuple.getT2();

        logger.info("Merging {} products with {} prices.", fleabayProducts.size(), fleabayProductPrices.size());

        Map<Integer, FleabayProductPrice> productPriceMap = fleabayProductPrices.stream()
                .collect(Collectors.toMap(FleabayProductPrice::productUid, price -> price));

        // The redundant filtering by productType is removed.
        return fleabayProducts.stream()
                .map(product -> {
                    FleabayProductPrice price = productPriceMap.get(product.productUid());
                    // Handle cases where a product might not have a price
                    if (price == null) {
                        return null; // Or create a CombinedFleabayProduct with default/null price values
                    }
                    return new CombinedFleabayProduct(
                            product.productUid(),
                            product.productType(),
                            product.name(),
                            product.fullUrl(),
                            price.unitPrice(),
                            price.unitPriceMeasure(),
                            price.unitPriceMeasureAmount()
                    );
                })
                .filter(java.util.Objects::nonNull) // Filter out products that had no matching price
                .collect(Collectors.toList());
    }

    public String rootpath() {
        return "ROOT OK!";
    }
}