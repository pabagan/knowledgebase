# Deploy Kubetcl

## Container registry
https://cloud.google.com/container-registry/

```sh
# configure docker at gCloud
gcloud auth configure-docker
# Add the image to Container Registry
docker tag pabagan/node-ts-backend gcr.io/klikkie-dev/node-ts-backend:latest
# Push
docker push gcr.io/klikkie-dev/node-ts-backend:latest
# Pull
docker pull gcr.io/klikkie-dev/node-ts-backend:latest
```
