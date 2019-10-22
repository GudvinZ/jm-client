package test.jmclient;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Service;
import test.jmclient.model.KafkaMessage;

@Service
public class KafkaMessageListener implements MessageListener<String, KafkaMessage> {

    @Override
    public void onMessage(ConsumerRecord<String, KafkaMessage> data) {
        System.out.println("topic: " + data.topic() + ", " + data.value());
    }
}
