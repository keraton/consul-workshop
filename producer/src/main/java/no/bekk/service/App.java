package no.bekk.service;

import com.orbitz.consul.AgentClient;
import com.orbitz.consul.Consul;
import com.orbitz.consul.model.agent.Registration;
import io.dropwizard.Application;
import io.dropwizard.lifecycle.Managed;
import io.dropwizard.setup.Environment;


public class App extends Application<AppConfig> {
    public static void main(String[] args) throws Exception {
        new App().run(args);
    }

    @Override
    public void run(AppConfig configuration, Environment environment) throws Exception {
        String port = System.getProperty("dw.server.applicationConnectors[0].port");
        String ipAddress = System.getProperty("ipAddress");
        System.out.println("Service listening on " + ipAddress + ":" + port);

        environment.jersey().register(new ProducerResource(port, ipAddress));
        environment.healthChecks().register("produces", new ProducerHealthCheck());
        environment.lifecycle().manage(new Managed() {

            @Override
            public void start() throws Exception {
                // Register service
                System.out.println("Register service");
                Consul consul = Consul.builder().build(); // connect to Consul on localhost
                AgentClient agentClient = consul.agentClient();

                agentClient.register(8080, Registration.RegCheck.http("http://localhost:8081/ping", 10), "producer", "1");
            }

            @Override
            public void stop() throws Exception {
                // Deregister service
                System.out.println("Deregister service");
                Consul consul = Consul.builder().build(); // connect to Consul on localhost
                AgentClient agentClient = consul.agentClient();

                agentClient.deregister("producer");

            }
        });



    }

}
