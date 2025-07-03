package uk.co.fleabay.demo.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import uk.co.fleabay.demo.client.model.FleabayProduct;
import uk.co.fleabay.demo.client.model.FleabayProductPrice;
import uk.co.fleabay.demo.services.ProductsService;

import java.util.Set;

@Component
public class FleabayWebClient {
    private static final Logger logger = LoggerFactory.getLogger(FleabayWebClient.class);

    // Inject URLs from application.properties
    @Value("${fleabay.api.products.url}")
    private String productsUrl;

    @Value("${fleabay.api.products-price.url}")
    private String productsPriceUrl;

    private final WebClient webClient;

    public FleabayWebClient(WebClient webClient) {
        this.webClient = webClient;
    }

    // Return Mono<Set<...>> instead of blocking
    public Mono<Set<FleabayProduct>> getProducts(String productType) {
        logger.info(" ** Fetching products for productsUrl: ***** '{}', -> productsUrl '{}'", productsUrl, productsPriceUrl);
        return getFilteredData(productsUrl, productType, new ParameterizedTypeReference<Set<FleabayProduct>>() {});
    }

    // Return Mono<Set<...>> instead of blocking
    public Mono<Set<FleabayProductPrice>> getProductsPrices(String productType) {
        return getFilteredData(productsPriceUrl, productType, new ParameterizedTypeReference<Set<FleabayProductPrice>>() {});
    }

    /**
     * Generic, non-blocking method to fetch data, removing duplication.
     */
    private <T> Mono<T> getFilteredData(String endpointUrl, String productType, ParameterizedTypeReference<T> typeReference) {
        return webClient
                .get()
                .uri(uriBuilder -> {
                    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(endpointUrl);

                    if (productType != null && !productType.isBlank()) {
                        builder.queryParam("product_type", productType);
                    }
                    return builder.build().toUri();
                })
                .retrieve()
                .bodyToMono(typeReference)
                .log(); // .log() is great for debugging reactive streams
    }

    // Helper methods are no longer needed as the client handles URL construction.
    // If they were needed, they should be corrected and use UriComponentsBuilder.
}