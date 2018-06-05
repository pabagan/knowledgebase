# Deploy Kubetcl

## Kubctl cluster
```bash
# display info
kubectl cluster-info
# see nodes
kubectl get nodes
```

## Run and communicate with Pods
A Cluster is a group of Pods. They can communicate inside same network.

### Create a localhost proxy

```sh
# create localhost proxy
kubectl proxy

# now query
curl http://localhost:8001/version
```

## From create image to deploy
```sh
docker build --tag pabagan/node-ts-backend .

kubectl run klikkie-api --image=pabagan/node-ts-backend --port=443 --image-pull-policy=Never

kubectl get deployments
```

### Run/Query Pod
```sh
kubectl run kubernetes-bootcamp --image=gcr.io/google-samples/kubernetes-bootcamp:v1 --port=8080

## Comunicate with Pods
export POD_NAME=$(kubectl get pods -o go-template --template '{{range .items}}{{.metadata.name}}{{"\n"}}{{end}}')
echo Name of the Pod: $POD_NAME

# Now we can make an HTTP request to the application running in that pod:
# curl http://localhost:8001/api/v1/namespaces/default/pods/$POD_NAME/proxy/

# describe
kubectl describe pods
```

## Command on containers
```sh
# execute
kubectl exec $POD_NAME env
# bash Pod
kubectl exec -ti $POD_NAME bash

# We have now an open console on the container where we run our NodeJS application. The source code of the app is in the server.js file:
cat server.js
```

## Deployment
```sh
kubectl get deployments
```
## Delete deployments

```sh
# Delete all deployments:
kubectl delete deployments --all

# Delete all pods:
kubectl delete pods --all

# And stop Minikube
minikube stop
```

## Troubleshoot
```sh
kubectl get         # list resources
kubectl describe    # show detailed information about a resource
kubectl logs        # print the logs from a container in a pod
kubectl exec        # execute a command on a container in a pod
```

## Credit
https://kubernetes.io/docs/tutorials/kubernetes-basics/