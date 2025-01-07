package com.api_gateway.controller;

import com.api_gateway.service.nats.NatsClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api")
public class ApiGatewayController {
    @Autowired
    private NatsClient natsClient;

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/signup")
    public void signUp() {
        String s = restTemplate.postForObject("http://user/api/signup", null, String.class);
        System.out.println(s);
        //natsClient.userCreate();
    }
}
