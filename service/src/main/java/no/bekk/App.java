package no.bekk;

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
        System.out.println("Service running on port " + port);

        environment.jersey().register(new HelloWorldResource());

        environment.healthChecks().register("hello-world", new HelloWorldHealthCheck());
    }
}
