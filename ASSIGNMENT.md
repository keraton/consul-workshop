# Assignment 1

* Start a consul process as a server on the master node.

* Start a consul process as an agent on both consumer and producer.

* Add a service with the name "master, "producer", "consumer" to /etc/consul.d on the respective node and do a "consul reload".(see https://github.com/keraton/consul-workshop/blob/master/README.md#registering-a-service)

* Use the HTTP API and check that you got a result for the queried service
```bash
curl http://localhost:8500/v1/catalog/service/<service name>
```

* Do the same with the DNS API
```bash
dig @127.0.0.1 -p 8600 <service name>.service.consul
```

* See at the local post, the consul UI : http://172.20.100.2:8500/ui/#/dc1/services 

# Assignment 2

Consul without application is not very interesting. There are two applications : producer and consumer. The consumer calls producer during its service.

## Build application

We are using vagrant in this exercise, the benefits of vagrant is that all files which in the same repository with the vagrant file are accessible in the vagrant node at /vagrant. Compile the application at local machine (use IntelliJ or Maven), the result will be automatically updated at all vagrant's node.


## Run application

* Start producer : in the producer node do
```bash
cd /vagrant/producer
./run.sh 8080 8081 #application port, admin port
```

* Test producer : in the master node do
```bash
curl http://172.20.100.5:8080
```

* Start consumer : in the consumer node do
```bash
cd /vagrant/consumer
./run.sh 8080 8081 #application port, admin port
```

* Test consumer : in the master node do
```bash
curl http://172.20.100.6:8080
```

* See at the local post, the consul UI : http://172.20.100.2:8500/ui/#/dc1/services 
* Did you see any change ?

## Add HealthCheck

* Test healthcheck : in the master node do
```bash
curl http://172.20.100.5:8081/ping # Producer
curl http://172.20.100.6:8081/ping # Consumer
```

* Add HeatlhCheck in the producer node
```bash
echo '{"service": {"id":"producer", "name": "producer", "tags": ["java"]}, "port":8080, "check": { "http": "http://localhost:8081/ping", "interval": "10s", "timeout": "1s"}}'  | sudo tee  /etc/consul.d/web.json
consul reload
```


* See at the local post, the consul UI : http://172.20.100.2:8500/ui/#/dc1/services 
* Did you see any change ?
* Turn off the application
* What is advantage of healthcheck.
* Due to error with DIG, we should change the web.json back to before.


# Assignment 3

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

To discuss. 

## By using consuls HTTP API (use the Consul client of Orbitz or Plain Java)

Consul expose their API in form of REST-HTTP. We can use java rest call to use this API (RestTemplate) or use the existing library such as consul-client that has been developped by Orbitz.

You can use Java/Consul-Client to do the service discovery using consul. The idea is to modify consumer so it will call consul to have the address of producer.

* HTTP API : [HTTP-API DOC](https://www.consul.io/docs/agent/http.html), [CATALOG-API DOC] (https://www.consul.io/docs/agent/http/catalog.html)
* Client-Consul :[Client Consul github page](https://github.com/OrbitzWorldwide/consul-client)

# Assignment 4

What we use until now is that the we configure service in the consul configuration file (/etc/consul.d/web.json). 
But what we want is that the application can register their service when it start and deregister when it stop.
First we remove the the service configuration.

* Test use ping
```bash
rm /etc/consul.d/web.json
consul reload
```

Extend the application to register (or deregister) the application is available when it starts (or stop). 
You can use Consul [maintance API](https://www.consul.io/docs/agent/http/agent.html#agent_service_maintenance) or consul client [Client Consul github page](https://github.com/OrbitzWorldwide/consul-client).

# Assignment 5

If you've got the time, try to integrate [consul-template](https://github.com/hashicorp/consul-template) with a loadbalancer (NginX/HAProxy/Apache)

E.g. have an HAProxy instance loadbalance requests to a dynamic set of producer apps.
