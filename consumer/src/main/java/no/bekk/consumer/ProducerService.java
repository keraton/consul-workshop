package no.bekk.consumer;

import com.orbitz.consul.CatalogClient;
import com.orbitz.consul.Consul;
import com.orbitz.consul.HealthClient;
import com.orbitz.consul.model.catalog.CatalogService;
import com.orbitz.consul.model.health.ServiceHealth;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.util.List;
import java.util.Optional;

public class ProducerService {

    private final WebTarget target;

    public ProducerService() {
        Client client = ClientBuilder.newClient();

        // Using Consul Client
        Consul consul = Consul.builder().build(); // connect to Consul on localhost
        CatalogClient catalogClient = consul.catalogClient();

        List<CatalogService> producer = catalogClient.getService("producer").getResponse();
        Optional<CatalogService> catalogServiceOptional = producer.stream().findFirst();

        if (catalogServiceOptional.isPresent()) {
            CatalogService catalogService = catalogServiceOptional.get();
            target = client.target("http://" + catalogService.getAddress() + ":" + catalogService.getServicePort());
        }
        else {
            throw new IllegalStateException("No service is found");
        }

    }

    public String produce() {
        return target.request().buildGet().invoke(String.class);
    }
}
