package com.example.ordermicroservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootTest
@SpringBootApplication
@EnableDiscoveryClient
class OrderMicroServiceApplicationTests {

    @Test
    void contextLoads() {
    }

}
