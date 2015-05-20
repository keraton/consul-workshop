#!/usr/bin/env bash

APP_PORT=$1
ADMIN_PORT=$2

java \
    -Ddw.server.applicationConnectors[0].port=$APP_PORT \
    -Ddw.server.adminConnectors[0].port=$ADMIN_PORT \
    -jar target/service-1.0-SNAPSHOT.jar \
    server
