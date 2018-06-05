# Docker Private Registry.
See [Docker Registry Docs](https://docs.docker.com/registry/deploying/).

## Basic commands
```sh
docker run -d -p 5000:5000 --name registry registry:2

# Pull (or build) some image from the hub
docker pull ubuntu

# Tag the image so that it points to your registry
docker image tag ubuntu localhost:5000/myfirstimage

# Push it
docker push localhost:5000/myfirstimage

# Pull it back
docker pull localhost:5000/myfirstimage

# Now stop your registry and remove all data
docker container stop registry && docker container rm -v registry
```

```sh
# Start the registry automatically
docker run -d \
  -p 5000:5000 \
  --restart=always \
  --name registry \
  registry:2

# Customize port
docker run -d \
  -e REGISTRY_HTTP_ADDR=0.0.0.0:5001 \
  -p 5001:5001 \
  --name registry-test \
  registry:2
```

## Certificates
```sh
docker run -d \
  --restart=always \
  --name registry \
  -v `pwd`/certs:/certs \
  -e REGISTRY_HTTP_ADDR=0.0.0.0:443 \
  -e REGISTRY_HTTP_TLS_CERTIFICATE=/certs/domain.crt \
  -e REGISTRY_HTTP_TLS_KEY=/certs/domain.key \
  -p 443:443 \
  registry:2
```