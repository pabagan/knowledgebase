## Install minikube
Need [Minikube](https://kubernetes.io/docs/setup/minikube/) and [Virtual Box](https://www.virtualbox.org/) or similar installed to create Kubernetes cluster.

```sh
curl -Lo minikube https://storage.googleapis.com/minikube/releases/latest/minikube-linux-amd64 && chmod +x minikube && sudo cp minikube /usr/local/bin/ && rm minikube
```

Start a Cluster:
```sh
# start minikube
minikube start
# manage minikube
minikube dashboard --url
# if fails connecting
minikube delete
```

## Credit
https://kubernetes.io/docs/tutorials/kubernetes-basics/