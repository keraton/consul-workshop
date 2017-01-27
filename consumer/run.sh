#!/usr/bin/env bash

if [ -z $1 ] || [ -z $2 ]; then
    echo "Usage: ./run.sh <app_port> <admin_port>"
    exit 1
fi

APP_PORT=$1
ADMIN_PORT=$2
IP_ADDRESS=$(ifconfig eth1 | awk '/inet addr/{print substr($2,6)}')

java \
    -Ddw.server.applicationConnectors[0].port=$APP_PORT \
    -Ddw.server.adminConnectors[0].port=$ADMIN_PORT \
    -DipAddress=$IP_ADDRESS \
    -jar consumer-1.0-SNAPSHOT.jar \
    server
