
# Initial setup

Start three virtual servers with

```bash
vagrant up
```

Start a consul server on the master node

```bash
vagrant ssh master
nohup consul agent -server -bootstrap-expect 1 -data-dir /tmp/consul -config-dir /etc/consul.d/ -ui-dir /opt/consul-web/ -bind 172.20.100.2 -client 0.0.0.0 -node master &
```

Start a consul agent on an producer node, and connect it to the master

```bash
vagrant ssh producer
nohup consul agent -data-dir /tmp/consul -config-dir /etc/consul.d/ -bind 172.20.100.5 -node producer &
consul join 172.20.100.2
consul members # list all members in the cluster
```

Start a consul agent on the other application node, and connect it to the master

```bash
vagrant ssh consumer
nohup consul agent -data-dir /tmp/consul -config-dir /etc/consul.d/ -bind 172.20.100.6 -node consumer &
consul join 172.20.100.2
consul members # list all members in the cluster
```

## Registering a service

### Master

Try to register a service on the master node, and check that the information is sent to all the application servers.

Put a JSON-file in `/etc/consul.d` on the `master`. [The Consul service guide](https://www.consul.io/intro/getting-started/services.html)
will help you.

```bash
echo '{"service": {"name": "master", "tags": ["consul"] }}' \
    | sudo tee /etc/consul.d/web.json
```
To reload configuration, run this command on all nodes:

```bash
consul reload
```

### Producer

Ths json configuration for Producer 

```bash
echo '{"service": {"name": "producer", "tags": ["java"], "port" : 8080 }}' \
    | sudo tee /etc/consul.d/web.json
consul reload
```

### Consumer 

The json configuration for Consumer.


```bash
echo '{"service": {"name": "consumer", "tags": ["java"], "port" : 8080 }}' \
    | sudo tee /etc/consul.d/web.json
consul reload    
```

## Web console

After starting the master, the Consul web console is available on http://localhost:8500/ui/


