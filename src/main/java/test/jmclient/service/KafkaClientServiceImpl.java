package test.jmclient.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import test.jmclient.model.KafkaMessage;

@Service
public class KafkaClientServiceImpl implements KafkaClientService {
    private final RestTemplate restTemplate;

    @Autowired
    KafkaTemplate<String, KafkaMessage> kafkaTemplate;
    @Autowired
    ConcurrentKafkaListenerContainerFactory<String, KafkaMessage> containerFactory;
    @Autowired
    MessageListener<String, KafkaMessage> listener;

    public KafkaClientServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private void subscribeChannel(String channelName) {
        ConcurrentMessageListenerContainer<String, KafkaMessage> container = containerFactory.createContainer(channelName);
        container.setupMessageListener(listener);
        container.start();
    }

    @Override
    public void sendMessage(String channelName, KafkaMessage message) {
        kafkaTemplate.send(channelName, message);
    }

    @Override
    public void createChannel(String channelName) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("topicName", channelName);

        try {
            restTemplate.postForObject("http://localhost:8080/test", new HttpEntity<>(map, new LinkedMultiValueMap<>()), String.class);
        } catch (RestClientException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "something wrong");
        }

        subscribeChannel(channelName);
    }
}
