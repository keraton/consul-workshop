package no.bekk.consumer;

public class ConsumerHealthCheck extends com.codahale.metrics.health.HealthCheck {
    @Override
    protected Result check() throws Exception {
        return Result.healthy();
    }
}
