#!/bin/bash

apt-get install software-properties-common
add-apt-repository "deb http://ppa.launchpad.net/webupd8team/java/ubuntu xenial main"
apt-get update
apt-get install oracle-java8-installer