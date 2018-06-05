# Minikube Example

## Create deployment
```sh
docker build -t minikube/node .

# Create deployment
kubectl run minikube-node --image=minikube/node --port=8080 --image-pull-policy=Never
# View the Deployment:
kubectl get deployments
# View the Pod:
kubectl get pods

# View cluster events:
kubectl get events

# View the kubectl configuration:
kubectl config view
```

## Create a Service
```sh
kubectl expose deployment minikube-node --type=LoadBalancer

# View service
kubectl get services

# open the service
minikube service minikube-node

# see logs
kubectl logs <POD-NAME>
kubectl logs minikube-node
```