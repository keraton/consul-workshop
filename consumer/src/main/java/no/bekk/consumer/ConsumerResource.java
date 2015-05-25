package no.bekk.consumer;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/consumer")
@Produces(MediaType.APPLICATION_JSON)
public class ConsumerResource {

    private final String port;
    private final String ipAddress;
    private final ProducerService producerService;

    public ConsumerResource(String port, String ipAddress, ProducerService producerService) {
        this.port = port;
        this.ipAddress = ipAddress;
        this.producerService = producerService;
    }

    @GET
    public String produce() {
        String response = "Consumer listening on " + ipAddress + ":" + port + " and forwarding:\n" +
                producerService.produce();
        return response;
    }
}