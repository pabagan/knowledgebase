# Deploy Kubetcl

## Container registry
https://cloud.google.com/container-registry/

```sh
# configure docker at gCloud
gcloud auth configure-docker

# Add the image to Container Registry
docker tag node-ts-backend-image gcr.io/[PROJECT-ID]/node-ts-backend-image:tag1

# Push
docker push gcr.io/[PROJECT-ID]/node-ts-backend-image:tag1

# Pull
docker pull gcr.io/[PROJECT-ID]/node-ts-backend-image:tag1

# Delete images
gcloud container images delete gcr.io/[PROJECT-ID]/node-ts-backend-image:tag1 --force-delete-tags
```
