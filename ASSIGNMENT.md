# Assignment 1

1. Start a consul process as a server on the master node.

2. Start a consul process as an agent on both consumer and producer.

3. Add a service with the name "master, "producer", "consumer" to /etc/consul.d on the respective node and do a "consul reload".(see https://github.com/keraton/consul-workshop/blob/master/README.md#registering-a-service)

4. Use the HTTP API and check that you got a result for the queried service
```bash
curl http://localhost:8500/v1/catalog/service/<service name>
```

5. Do the same with the DNS API
```bash
dig @127.0.0.1 -p 8600 <service name>.service.consul
```

# Assignment 2

The consumer app currently integrates with the producer on a hardcoded IP and port.

THIS IS NOT OK!!!

Your job is to decouple the consumer from that producer instance by introducing a whiff of service discovery. 

## By using consuls DNS API (use dnsmasq)

Consul run a DNS server in localhost at port 8600. But unfortunately java application can only use default port (53) to define the DNS server. So what we want to do in this exercice is to run another DNS server at the localhost but with the correct port number. We are going to use dnsmasq. 

### Change in the DNS

In the master node :

* Install dnsmasq
```bash
sudo apt-get install dnsmasq
```

* Run dnsmasq
```bash
sudo /etc/init.d/dnsmasq restart
```

* Test dnsmasq for google.com
```bash
dig @127.0.0.1 -p 53 google.com
```
You should have the ip address of google.com

* Test dnsmasq for master.service.consul
```bash
dig @127.0.0.1 -p 53 master.service.consul
```
You are not able to get the address of consul server.
To fix this issue, we need to do a dns forwarding, we are going to forward all request vers consul address to consul's dns server.

* Configure forwarding
```bash
echo "server=/consul/127.0.0.1#8600" > /etc/dnsmasq.d/10-consul
```

* Restart dnsmasq
```bash
sudo /etc/init.d/dnsmasq restart
```

* Test dnsmasq
```bash
dig @127.0.0.1 -p 53 master.service.consul
```

* Test use ping
```bash
ping master.service.consul
```
Redo all this in producer and consumer.

### Change your application

In the Consumer application you need to change ProducerService so it will use the domain instead of IP.

### What is advantage of this approach ?


## By using consuls HTTP API (use the Consul client of Spring Boot/Orbitz)

# Assignment 3

Extend the startup script of the application to use the
[maintance API](https://www.consul.io/docs/agent/http/agent.html#agent_service_maintenance)
to tell Consul the application is available when it starts, and mark it as
unavailable when it exits.

# Assignment 4

If you've got the time, try to integrate [consul-template](https://github.com/hashicorp/consul-template) with a loadbalancer (NginX/HAProxy/Apache)

E.g. have an HAProxy instance loadbalance requests to a dynamic set of producer apps.
