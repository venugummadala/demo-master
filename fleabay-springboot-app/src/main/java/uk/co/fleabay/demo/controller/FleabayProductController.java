package uk.co.fleabay.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import uk.co.fleabay.demo.controller.response.CombinedFleabayProduct;
import uk.co.fleabay.demo.services.ProductsService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class FleabayProductController {
    private static final Logger logger = LoggerFactory.getLogger(FleabayProductController.class);

    private final ProductsService productsService;

    public FleabayProductController(ProductsService productsService) {
        this.productsService = productsService;
    }

    // The endpoint now returns a Mono, making it fully non-blocking.
    // @SneakyThrows is removed as it's no longer needed.
    @GetMapping(path = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<List<CombinedFleabayProduct>> unifiedProducts(@RequestParam(required = false) String productType) {
        logger.info("Request received for /api/products with productType: '{}'", productType);
        String typeToSearch = Optional.ofNullable(productType).orElse("");

        return productsService.getUnifiedProduct(typeToSearch)
                .doOnSuccess(responseBody -> logger.info("Returning {} unified products.", responseBody.size()));
    }

    @GetMapping(path = "/")
    public ResponseEntity<String> getRoot() {
        logger.info(" root path = /  ");
        return new ResponseEntity<>(productsService.rootpath(), HttpStatus.OK);
    }
}