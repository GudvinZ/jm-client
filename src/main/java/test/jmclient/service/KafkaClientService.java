package test.jmclient.service;

import test.jmclient.model.KafkaMessage;

public interface KafkaClientService {
    void sendMessage(String channelName, KafkaMessage message);
    void createChannel(String channelName);
}
