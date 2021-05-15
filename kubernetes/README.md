# Kubernetes

* [Kubernetes](https://kubernetes.io/docs/home/?path=users&persona=app-developer&level=foundational)
kubernetes.00.install-minikube.md
## Kubernetes tasks
* [Install](https://github.com/pabagan/knowledgebase/blob/master/kubernetes/kubernetes.00.install.md).
* [Development](https://github.com/pabagan/knowledgebase/blob/master/kubernetes/kubernetes.01.development-with-minikube.md).
* [Deploy](https://github.com/pabagan/knowledgebase/blob/master/kubernetes/kubernetes.02.deploy.md).
* [Expose](https://github.com/pabagan/knowledgebase/blob/master/kubernetes/kubernetes.03.expose.md).
* [Update](https://github.com/pabagan/knowledgebase/blob/master/kubernetes/kubernetes.04.update-and-roll-back.md).
* [Scale](https://github.com/pabagan/knowledgebase/blob/master/kubernetes/kubernetes.05.scale-and-down-scale.md).

## GCloud
* [Kubernetes in GCloud](https://github.com/pabagan/knowledgebase/blob/master/kubernetes/kubernetes.gCloud.md).
* [GCloud Quickstart example](https://github.com/pabagan/knowledgebase/blob/master/kubernetes/kubernetes.googleAppEngine.example.md)

## Create Cluster

```sh
gcloud container clusters create k0
```

## Launch instance (pods)

```sh
# launch
kubectl run nginx --image=nginx
kubectl run node --image=node
# Get pods
kubectl get pods

# Expose nginx
kubectl expose deployment nginx --port 80 --type LoadBalancer
kubectl expose deployment node --port=5000 --type LoadBalancer
kubectl expose deployment node --port=8443 --type LoadBalancer


# List services
kubectl get services
```

## Interacting with pods
```sh
kubectl port-forward <podName> 10080:80
curl http://127.0.0.1:10080
```

## Logs
```sh
kubectl logs <podName>
```
## Inspectiong instances (`kubctl describe`)

Run `kubectl describe -h` for info:

```sh
# Describe a node
kubectl describe nodes kubernetes-node-emt8.c.myproject.internal
# Describe a pod
kubectl describe pods/nginx
# Describe a pod identified by type and name in "pod.json"
kubectl describe -f pod.json
# Describe all pods
kubectl describe pods
# Describe pods by label name=myLabel
kubectl describe po -l name=myLabel
# Describe all pods managed by the 'frontend' replication controller (rc-created pods
# get the name of the rc as a prefix in the pod the name).
kubectl describe pods frontend
```

## Monitoring and Health Checks

* [Liveness](http://kubernetes.io/docs/user-guide/liveness/).

## Config and Security overview

* [Config docs](http://kubernetes.io/docs/user-guide/configmap/).
* [Secrets](http://kubernetes.io/docs/user-guide/secrets/).

### Create a secret
```sh
# to create the tls-certs secret from the TLS certificates stored under the tls directory:
kubectl create secret generic tls-certs --from-file=tls/
# kubectl will create a key for each file in the tls directory under the tls-certs secret bucket. Use the kubectl describe command to verify that:
kubectl describe secrets tls-certs
# Next we need to create a configmap entry for the proxy.conf nginx configuration file using the kubectl create configmap command:
kubectl create configmap nginx-proxy-conf --from-file=nginx/proxy.conf
# Use the kubectl describe configmap command to get more details about the nginx-proxy-conf configmap entry:
kubectl describe configmap nginx-proxy-conf

```

## Credit
https://kubernetes.io/docs/tutorials/kubernetes-basics/