package com.api_gateway.service.nats;

import io.nats.client.Connection;
import io.nats.client.Message;
import io.nats.client.Nats;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.*;

@Service
public class NatsClient {
    private final Connection nc;

    private static final String NATS_SERVER = "nats://localhost:4222";
    private static final String USER_CREATE = "user.create";
    private static final String USER_CREATE_REPLY = "user.create_reply";

    public NatsClient() throws RuntimeException {
        try {
            nc = Nats.connect(NATS_SERVER);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public String userCreate() {
        String output = "";

        try {
            CompletableFuture<Message> future = nc.request(USER_CREATE, "Hi there ".getBytes(StandardCharsets.UTF_8));
            Message message = future.get(1, TimeUnit.SECONDS);
            output = new String(message.getData(), StandardCharsets.UTF_8);
            System.out.println("Response received: " + output);

        } catch (ExecutionException e) {
            System.out.println("Something went wrong with the execution of the request: " + e);
        } catch (TimeoutException e) {
            System.out.println("We didn't get a response in time.");
        } catch (CancellationException e) {
            System.out.println("The request was cancelled due to no responders.");
        } catch (InterruptedException e) {
            System.out.println("The request was interacted.");
        }

        return output;
    }
}
