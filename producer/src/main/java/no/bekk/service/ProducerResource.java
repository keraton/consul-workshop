package no.bekk.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class ProducerResource {

    private final String port;
    private final String ipAddress;

    public ProducerResource(String port, String ipAddress) {
        this.port = port;
        this.ipAddress = ipAddress;
    }

    @GET
    public String produce() {
        return "Producer listening on " + ipAddress + ":" + port;
    }
}