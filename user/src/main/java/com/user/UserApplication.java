package com.user;

import com.user.service.nats.NatsClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserApplication {
  public static void main(String[] args) {
    SpringApplication.run(UserApplication.class, args);

    NatsClient natsClient = new NatsClient();
    natsClient.listen();
  }
}
