package com.splunk;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.logging.LoggingFeature;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RestController
public class Controllers {

    @GetMapping("/greeting")
    public String hello() {
        return "hello.";
    }

    @GetMapping("/snack")
    public Snack snack() {
        Client client = ClientBuilder.newClient(new ClientConfig()
                .register(LoggingFeature.class)
                .register(JacksonFeature.class)
        );
        System.out.println("Let's fetch a snack...");
        WebTarget webTarget = client.target("http://localhost:8000/").path("snacks/jerky.json");
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();
        Snack snack = response.readEntity(Snack.class);
        System.out.println("Fetched a delicious snack: " + snack);
        return snack;
    }

}
