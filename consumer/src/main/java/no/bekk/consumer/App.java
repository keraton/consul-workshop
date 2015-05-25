package no.bekk.consumer;

import io.dropwizard.Application;
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

        ProducerService producerService = new ProducerService();
        environment.jersey().register(new ConsumerResource(port, ipAddress, producerService));

        environment.healthChecks().register("hello-world", new ConsumerHealthCheck());
    }
}
