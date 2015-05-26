package no.bekk.consumer;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

public class ProducerService {

    private final WebTarget target;

    public ProducerService() {
        Client client = ClientBuilder.newClient();
        target = client.target("http://172.20.100.6:8080")
                .path("/");
    }

    public String produce() {
        return target.request().buildGet().invoke(String.class);
    }
}
