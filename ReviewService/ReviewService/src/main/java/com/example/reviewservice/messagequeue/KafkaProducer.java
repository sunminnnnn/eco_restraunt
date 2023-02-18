package com.example.reviewservice.messagequeue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.reviewservice.dto.ReviewDto;
import com.example.reviewservice.dto.UserProductCountDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KafkaProducer {

	private KafkaTemplate<String, String> kafkaTemplate;

	@Autowired
	public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	public ReviewDto send(String kafkaTopic, ReviewDto reviewDto) {
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = "";
		try {
			jsonInString = mapper.writeValueAsString(reviewDto);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		kafkaTemplate.send(kafkaTopic, jsonInString);
		log.info("Kafka Producer send data from the review microservice: " + reviewDto);

		return reviewDto;
	}

	public UserProductCountDto send(String kafkaTopic, UserProductCountDto userProductCountDto) {
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = "";
		try {
			jsonInString = mapper.writeValueAsString(userProductCountDto);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		kafkaTemplate.send(kafkaTopic, jsonInString);
		log.info("Kafka Producer send data from the review microservice: " + userProductCountDto);

		return userProductCountDto;
	}
}
