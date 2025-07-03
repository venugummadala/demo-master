package uk.co.fleabay.demo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import uk.co.fleabay.demo.client.FleabayWebClient;
import uk.co.fleabay.demo.client.model.FleabayProduct;
import uk.co.fleabay.demo.client.model.FleabayProductPrice;
import uk.co.fleabay.demo.controller.response.CombinedFleabayProduct;
import uk.co.fleabay.demo.services.ProductsService;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.when;

/**
 * Unit tests for ProductsService.
 * This class tests the business logic of merging products and prices.
 */
@ExtendWith(MockitoExtension.class) // Use MockitoExtension for unit testing
class FleabayProductsMergeTest {

    // Mock the dependency of ProductsService
    @Mock
    private FleabayWebClient fleabayWebClient;

    // Create an instance of the service and inject the mock into it
    @InjectMocks
    private ProductsService productsService;

    @Test
    void getUnifiedProduct_shouldMergeMatchingProductsAndPrices() {
        String productType = "BUDGET";

        // 1. Arrange: Prepare mock data and define mock behavior
        Set<FleabayProduct> mockProducts = new HashSet<>();
        mockProducts.add(new FleabayProduct(6447344, "BUDGET", "Salmon Fillets x2 240g", "https://..."));
        mockProducts.add(new FleabayProduct(12345, "BUDGET", "Product without price", "https://...")); // This one won't have a matching price

        Set<FleabayProductPrice> mockPrices = new HashSet<>();
        mockPrices.add(new FleabayProductPrice(6447344, 15.63, "kg", 1));
        mockPrices.add(new FleabayProductPrice(99999, 10.0, "kg", 1)); // This one won't have a matching product

        // Mock the behavior of the web client to return our test data
        when(fleabayWebClient.getProducts(productType)).thenReturn(Mono.just(mockProducts));
        when(fleabayWebClient.getProductsPrices(productType)).thenReturn(Mono.just(mockPrices));

        // 2. Act: Call the public method we want to test
        Mono<List<CombinedFleabayProduct>> resultMono = productsService.getUnifiedProduct(productType);

        // 3. Assert: Use StepVerifier to test the reactive stream
        StepVerifier.create(resultMono)
                .expectNextMatches(combinedProducts -> {
                    // The service should only return products that have a matching price
                    if (combinedProducts.size() != 1) return false;
                    CombinedFleabayProduct product = combinedProducts.get(0);
                    return product.productUid() == 6447344 &&
                            product.name().equals("Salmon Fillets x2 240g") &&
                            product.unitPrice() == 15.63;
                })
                .verifyComplete(); // Verify that the Mono completes successfully
    }

    @Test
    void getUnifiedProduct_shouldReturnEmptyListWhenNoProducts() {
        String productType = "EMPTY";

        // Arrange: Mock the web client to return empty sets
        when(fleabayWebClient.getProducts(productType)).thenReturn(Mono.just(Collections.emptySet()));
        when(fleabayWebClient.getProductsPrices(productType)).thenReturn(Mono.just(Collections.emptySet()));

        // Act
        Mono<List<CombinedFleabayProduct>> resultMono = productsService.getUnifiedProduct(productType);

        // Assert
        StepVerifier.create(resultMono)
                .expectNextMatches(List::isEmpty) // Expect a single emission of an empty list
                .verifyComplete();
    }
}