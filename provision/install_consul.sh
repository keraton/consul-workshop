#!/bin/bash

wget -q https://dl.bintray.com/mitchellh/consul/0.5.2_linux_amd64.zip

sudo apt-get -y install unzip

unzip 0.5.2_linux_amd64.zip
sudo mkdir /usr/local/bin
sudo cp consul /usr/local/bin/
sudo chmod 755 /usr/local/bin/consul

sudo mkdir /etc/consul.d
sudo chmod vagrant /etc/consul.d


