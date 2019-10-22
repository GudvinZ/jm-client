package test.jmclient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import test.jmclient.model.KafkaMessage;
import test.jmclient.service.KafkaClientService;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    KafkaClientService kafkaClientService;

    @PostMapping("/createChannel")
    public ResponseEntity<?> createChannel(@RequestParam String channelName) {
        kafkaClientService.createChannel(channelName);
        return ResponseEntity.ok().build();
    }

    @PostMapping()
    public ResponseEntity<?> sendMessage(@RequestParam String messageContent, @RequestParam String channelName) {
        kafkaClientService.sendMessage(channelName ,new KafkaMessage(1L, 1L, messageContent, LocalDateTime.now()));
        return ResponseEntity.ok().build();
    }
}
