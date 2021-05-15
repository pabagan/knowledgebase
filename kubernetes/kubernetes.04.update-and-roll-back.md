# Kubernetes updating APP

* Promote an application from one environment to another (via container image updates).
* Rollback to previous versions.
* Continuous Integration and Continuous Delivery of applications with zero downtime.

## Update the version

```sh
# To list your deployments
kubectl get deployments

# get running Pods
kubectl get pods

# view current app image version
kubectl describe pods

# update version
kubectl set image <image> <lable:version>
kubectl set image deployments/kubernetes-bootcamp kubernetes-bootcamp=jocatalin/kubernetes-bootcamp:v2

# see changes
kubectl get pods
```

## Verify and update

```sh
# To list your deployments
kubectl get deployments

# get running Pods
kubectl get pods

# view current app image version
kubectl describe pods

# update version
kubectl set image <image> <lable:version>
kubectl set image deployments/kubernetes-bootcamp kubernetes-bootcamp=jocatalin/kubernetes-bootcamp:v2

# see changes
kubectl get pods
```

# Rollback an update

```sh
# perform new update tagged as v10 :
kubectl set image deployments/kubernetes-bootcamp kubernetes-bootcamp=gcr.io/google-samples/kubernetes-bootcamp:v10

# To list your deployments
kubectl get deployments

# And something is wrong… We do not have the desired number of Pods available. List the Pods again:
kubectl get pods

# A describe should give insights:
kubectl describe pods

# no image called v10. Let’s roll back to our previously working version
kubectl rollout undo deployments/kubernetes-bootcamp

# see posts
kubectl get pods

# Check again the image deployed on the them:

kubectl describe pods

# ... We see rollback to v2 succeeded
```

## Credit
https://kubernetes.io/docs/tutorials/kubernetes-basics/update/update-intro/