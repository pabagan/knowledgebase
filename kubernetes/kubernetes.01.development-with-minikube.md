# Install Minikube

* [Minikube install](https://minikube.sigs.k8s.io/docs/start/).

Need [Minikube](https://kubernetes.io/docs/setup/minikube/) and [Virtual Box](https://www.virtualbox.org/) or similar installed to create Kubernetes cluster.

Mac:
```sh
brew install minikube
brew install virtuialbox
```

## Start a Cluster:
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
