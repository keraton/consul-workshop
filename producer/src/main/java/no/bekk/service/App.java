package no.bekk.service;

import com.orbitz.consul.AgentClient;
import com.orbitz.consul.Consul;
import com.orbitz.consul.model.agent.Registration;
import io.dropwizard.Application;
import io.dropwizard.lifecycle.Managed;
import io.dropwizard.setup.Environment;

public class App extends Application<AppConfig>
{
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
               onStart();
            }

            @Override
            public void stop() throws Exception {
               onStop();
            }
        });

    }

    private void onStop() {
        // Stopping hook
    }

    private void onStart() {
        // Starting hook
    }
}
