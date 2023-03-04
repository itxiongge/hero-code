package com.hero.demo.controller;

import com.hero.demo.dto.UserDTO;
import com.hero.demo.service.ProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rocketmq")
public class RocketMQController {

    @Autowired
    private ProducerService mqProducerService;

    @GetMapping("/sendSomeThing")
    public void sendSomeThing() {
        mqProducerService.sendSomeThing();
    }

    @GetMapping("/send")
    public void send() {
        UserDTO user = UserDTO.builder().userId(10001L).username("Hero1").build();
        mqProducerService.send(user);
        user = UserDTO.builder().userId(10002L).username("Hero2").build();
        mqProducerService.send(user);
        user = UserDTO.builder().userId(10003L).username("Hero3").build();
        mqProducerService.send(user);
        user = UserDTO.builder().userId(10004L).username("Hero4").build();
        mqProducerService.send(user);
    }
}
