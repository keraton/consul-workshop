package no.bekk.service;

public class ProducerHealthCheck extends com.codahale.metrics.health.HealthCheck {
    @Override
    protected Result check() throws Exception {
        return Result.healthy();
    }
}
