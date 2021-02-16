package com.splunk;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.logging.LoggingFeature;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

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

    @GetMapping("/bad-snack")
    public Snack badSnack() {
        Client client = ClientBuilder.newClient(new ClientConfig()
                .register(LoggingFeature.class)
                .register(JacksonFeature.class)
        );
        System.out.println("Let's fetch a snack...");
        WebTarget webTarget = client.target("http://localhost:8000/").path("snacks/gravel.json");
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();
        return response.readEntity(Snack.class);
    }

    @Produces("text/plain")
    @GetMapping("/snack2")
    public String snack2() throws Exception {
        System.out.println("Let's fetch a good delicious snack (jerky)");
        URL url = URI.create("http://localhost:8000/snacks/jerky.json").toURL();
        URLConnection conn = url.openConnection();
        conn.connect();
        InputStream in = conn.getInputStream();
        StringBuilder sb = new StringBuilder();
        while(true){
            byte[] buff = new byte[1024*100];
            int rc = in.read(buff);
            if(rc < 0) break;
            sb.append(new String(buff, 0, rc, StandardCharsets.UTF_8));
        }
        String snack = sb.toString();
        return snack;
    }

    @GetMapping("/bad-snack2")
    public Snack badSnack2() throws Exception {
        System.out.println("Let's fetch a bad snack (glass)...");
        URL url = URI.create("http://localhost:8000/snacks/glass.json").toURL();
        URLConnection conn = url.openConnection();
        conn.connect();
        InputStream in = conn.getInputStream();
        StringBuffer sb = new StringBuffer();
        while(true){
            byte[] buff = new byte[1024*100];
            int rc = in.read(buff);
            if(rc < 0) break;
            sb.append(new String(buff, StandardCharsets.UTF_8));
        }

        System.out.println(sb.toString());
        return null; // it won't work
    }

    @GetMapping("/bad-snack3")
    public Snack badSnack3() throws Exception {
        System.out.println("Let's fetch an impossibly bad snack (nowhere)...");
        URL url = URI.create("http://asdf098jiaojsdfoij:8000/snacks/boom").toURL();
        URLConnection conn = url.openConnection();
        conn.connect();
        InputStream in = conn.getInputStream();
        StringBuffer sb = new StringBuffer();
        while(true){
            byte[] buff = new byte[1024*100];
            int rc = in.read(buff);
            if(rc < 0) break;
            sb.append(new String(buff, StandardCharsets.UTF_8));
        }

        System.out.println(sb.toString());
        return null; // it won't work
    }


}
