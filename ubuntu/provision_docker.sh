#!/usr/bin/env bash
export DEBIAN_FRONTEND=noninteractive

# -------------------------------------
#
# Docker Engine (docker-compose)
# https://docs.docker.com/engine/installation/linux/ubuntulinux/
#
# -------------------------------------
# Add Sources
sudo apt-get update -q
sudo apt-get -y install apt-transport-https ca-certificates
sudo apt-key adv --keyserver hkp://p80.pool.sks-keyservers.net:80 --recv-keys 58118E89F3A912897C070ADBF76221572C52609D

# Ubuntu Trusty 14.04 (LTS)
# Open /etc/apt/sources.list.d/docker.list
# añade: 
# deb https://apt.dockerproject.org/repo ubuntu-trusty main
# Ubuntu Xenial 16.04 (LTS)
# deb https://apt.dockerproject.org/repo ubuntu-xenial main

echo "deb https://apt.dockerproject.org/repo ubuntu-xenial main" | sudo tee -a /etc/apt/sources.list.d/docker.list
sudo apt-get update -q
sudo apt-get -y purge lxc-docker
sudo apt-cache policy docker-engine

# Extra para Ubuntu 14.04, 16.04
sudo apt-get update -q
sudo apt-get -y install linux-image-extra-$(uname -r)

# Install
sudo apt-get update -q
sudo apt-get -y install docker-engine
sudo service docker start
# Test docker
sudo docker run hello-world

# Add the docker group if it doesn't already exist:
sudo groupadd docker
# Add the connected user "${USER}" to the docker group. 
sudo gpasswd -a ${USER} docker
sudo usermod -aG docker $(whoami)
# Restart the Docker daemon:
sudo service docker restart
# If you are on Ubuntu 14.04 and up use docker.io instead:
sudo service docker.io restart
# log out/in to activate the changes to groups.


#newgrp docker

# Test without sudo
docker run hello-world
