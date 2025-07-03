package uk.co.fleabay.demo;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import uk.co.fleabay.demo.controller.FleabayProductController;

@SpringBootTest
class SmokeTest {

    @Autowired
    private FleabayProductController controller;

    @Test
    void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }
}