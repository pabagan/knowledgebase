# Docker

* Find Docker images at [Docker Hub](https://hub.docker.com/).

```sh
# get image from Docker Hub
docker pull <hubLinkToRepo>
docker pull google/cloud-sdk

# Create container
docker build --tag <name> .
# Start container
docker container start gcloud-config
# see container running
docker ps
# see container logs
docker logs <container name>
# log into container
docker exec -it <container name> /bin/bash
# Run container attaching a name and execute command
docker run -ti --name gcloud-config google/cloud-sdk gcloud auth application-default login
```

## Install Docker
Install Docker.io from [GCloud console](https://console.cloud.google.com/).

## Create from `Dockerfile`

Run a `Dockerfile` by executing
```bash
docker build --tag pabagan/jenkins <route-to-Dockerfile>
docker build --tag pabagan/jenkins .
```

Dockerfile example:

```bash
FROM jenkins/jenkins:lts
# set envs
ENV JENKINS_SLAVE_AGENT_PORT 50001

# if we want to install via apt
USER root
#RUN apt-get update && apt-get install -y ruby make more-thing-here

WORKDIR /var/jenkins_home

VOLUME ./jenkins_home /var/jenkins_home

EXPOSE 8080 50001

# # preinstall plugins
# RUN /usr/local/bin/install-plugins.sh docker-slaves github-branch-source:1.8

# # pass a list of plugins to a file
# COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
# RUN /usr/local/bin/install-plugins.sh < /usr/share/jenkins/ref/plugins.txt
# drop back to the regular jenkins user - good practice
USER jenkins
```

## Get images from hub
```bash
# Install Docker
sudo apt-get install docker.io
#Check Docker images
sudo docker images
# Pull nginx image
sudo docker pull nginx:1.10.0
sudo docker images
#Verify the versions match
sudo dpkg -l | grep nginx
```
## Remove images

```sh
docker rmi Image Image
```

## Create containers
```bash
sudo docker run -d <nameOfTheImage>
sudo docker run -d nginx

#docker run --name myjenkins -p 8080:8080 -p 50001:50001 --env JENKINS_SLAVE_AGENT_PORT=50001 pabagan/jenkings
docker run --name jibirijenkins \
  -p 8080:8080 -p 50001:50001 \
  pabagan/jenkins
```

## Stop containers

```sh
# Stop an instance
sudo docker stop <cid>
# or
sudo docker stop $(sudo docker ps -aq)
# Verify no more instances running
sudo docker ps
```

## Restart after stop
```sh
docker restart <container-name>
```

## Remove containers

```sh
# Remove the docker containers from the system
sudo docker rm <cid>
# or
sudo docker rm $(sudo docker ps -aq)
```


## Communicate with containers
```sh
sudo docker ps

# For use in shell scripts you might want to just get a list of container IDs (-a stands for all instances, not just running, and -q is for "quiet" - show just the numeric ID):
sudo docker ps -aq

sudo docker inspect f86cf066c304
# or
sudo docker inspect sharp_bartik

# Connect to the nginx using the internal IP
# Get the internal IP address either copying from the full inspect file or by assigning it to a shell variable:

CN="sharp_bartik"
CIP=$(sudo docker inspect --format '{{ .NetworkSettings.IPAddress }}' $CN)
curl  http://$CIP
# You can also get all instance IDs and their corresponding IP addresses by doing this:
sudo docker inspect -f '{{.Name}} - {{.NetworkSettings.IPAddress }}' $(sudo docker ps -aq)
```

## Free <none> images
```sh
docker image prune -f
```