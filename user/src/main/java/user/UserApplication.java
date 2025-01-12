package user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import user.adapter.messaging.NatsClient;

@EnableDiscoveryClient
@SpringBootApplication
public class UserApplication {
  public static void main(String[] args) {
    SpringApplication.run(UserApplication.class, args);

    NatsClient natsClient = new NatsClient();
    natsClient.listen();
  }
}
