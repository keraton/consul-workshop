package no.bekk;

public class HelloWorldHealthCheck extends com.codahale.metrics.health.HealthCheck {
    @Override
    protected Result check() throws Exception {
        return Result.healthy();
    }
}
