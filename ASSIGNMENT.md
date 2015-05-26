Assignment 1
============

1. Start a consul process as a server on the master node.

2. Start a consul process as an agent on both app1 and app2.

3. Add a service with the name "producer" to /etc/consul.d on app1 and app2 and do a `consul reload`. [The Consul service guide](https://www.consul.io/intro/getting-started/services.html)

4. Use the HTTP API and check that you got a result for the queried service
```bash
curl http://localhost:8500/v1/catalog/service/<service name>
```

5. Do the same with the DNS API
```bash
dig @127.0.0.1 -p 8600 <service name>.service.consul
```

Assignment 2
============

The consumer app currently integrates with the producer on a hardcoded IP and port.

THIS IS NOT OK!!!

Your job is to decouple the consumer from that producer instance by introducing a whiff of service discovery. How you do it is up to you:
- By using consuls DNS API (NB: SRV records)
- By using consuls HTTP API
- Or some other way, if you've got any good ideas

Assignment 3
============

Extend the startup script of the application to use the
[maintance API](https://www.consul.io/docs/agent/http/agent.html#agent_service_maintenance)
to tell Consul the application is available when it starts, and mark it as
unavailable when it exits.

Assignment 4
============

If you've got the time, try to integrate [consul-template](https://github.com/hashicorp/consul-template) with a loadbalancer (NginX/HAProxy/Apache)

E.g. have an HAProxy instance loadbalance requests to a dynamic set of consumer apps.
