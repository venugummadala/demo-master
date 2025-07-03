package uk.co.fleabay.demo;



import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import uk.co.fleabay.demo.controller.FleabayProductController;
import uk.co.fleabay.demo.controller.response.CombinedFleabayProduct;
import uk.co.fleabay.demo.services.ProductsService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@RunWith(SpringRunner.class)

@ComponentScan(basePackages = {"uk.co.fleabay.demo.services", "uk.co.fleabay.demo.client.model", "uk.co.fleabay.demo.controller.response", "uk.co.fleabay.demo.client"}, basePackageClasses = ProductsService.class)
@WebMvcTest(FleabayProductController.class)
public class FleabayFleabayProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Autowired
    private ProductsService mockProductsService ;
    //initilise with constructors here .. TO DO

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    /*
    TO DO Initialise Service classes with constructors here
    @Test
    public void testGetItems() throws Exception {

        // Expected JSON array
        Set<CombinedFleabayProduct> products = new HashSet<>();
        products.add(new CombinedFleabayProduct(36969, "BUDGET", "Fleabay's Skin on ASC Scottish Salmon Fillets x2 269g", "https://www.fleabay.co.uk/gol-ui/product/fleabay-responsibly-sourced-scottish-salmon-fillet-x2-269g", 15.63, "kg", 1));
       // ProductsService
        mockProductsService = org.mockito.Mockito.mock(ProductsService.class);
        when(mockProductsService.getUnifiedProduct("BUDGET")).thenReturn(List.copyOf(products));

        mockMvc.perform(get("/api/products/")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(products)));
    }*/
}
