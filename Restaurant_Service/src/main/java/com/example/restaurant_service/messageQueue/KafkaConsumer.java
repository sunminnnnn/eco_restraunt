package com.example.restaurant_service.messageQueue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.restaurant_service.jpa.RestaurantEntity;
import com.example.restaurant_service.jpa.RestaurantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class KafkaConsumer {
    RestaurantRepository repository;

    @Autowired
    public KafkaConsumer(RestaurantRepository repository) {
        this.repository = repository;
    }

    @KafkaListener(topics="order-restaurant-topic")
    public void processMessage(String kafkaMessage) {
        log.info("Kafka Message: =====> " + kafkaMessage);

        Map<Object, Object> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            map = mapper.readValue(kafkaMessage, new TypeReference<Map<Object, Object>>(){}); //<= 정확히 무슨 package를 load하는지 살펴볼 것
        }catch (JsonProcessingException e ) {
            e.printStackTrace();
        }

        RestaurantEntity entity = repository.findByProductId((String)map.get("productId"));
        entity.setStock(entity.getStock() - (Integer)map.get("qty"));

        repository.save(entity);
    }
}
