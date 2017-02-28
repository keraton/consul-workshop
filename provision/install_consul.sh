#!/bin/bash

wget -q -nc https://releases.hashicorp.com/consul/0.7.3/consul_0.7.3_linux_amd64.zip

sudo apt-get update

sudo apt-get -y install unzip

unzip consul_0.7.3_linux_amd64.zip
sudo mkdir -p /usr/local/bin
sudo cp consul /usr/local/bin/
sudo chmod 755 /usr/local/bin/consul

sudo mkdir -p /etc/consul.d
sudo chown vagrant /etc/consul.d

### Install Web-gui

wget -q -nc https://releases.hashicorp.com/consul/0.7.3/consul_0.7.3_web_ui.zip
sudo mkdir -p /opt/consul-web
sudo unzip consul_0.7.3_web_ui.zip -d /opt/consul-web/
sudo chown -R vagrant /opt/consul-web/

### Install consul-template

wget -q -nc https://releases.hashicorp.com/consul-template/0.18.1/consul-template_0.18.1_linux_amd64.zip
unzip consul-template_0.18.1_linux_amd64.zip
sudo cp consul-template /usr/local/bin
sudo chmod 755 /usr/local/bin/consul-template
