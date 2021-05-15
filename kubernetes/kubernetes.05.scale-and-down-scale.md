# Kubernetes scale App

## Scale up
```sh
# We should have 1 Pod
kubectl get deployments

# The DESIRED state is showing the configured number of replicas
# The CURRENT state show how many replicas are running now
# The UP-TO-DATE is the number of replicas that were updated to match the desired (configured) state
# The AVAILABLE state shows how many replicas are actually AVAILABLE to the users

# create 4 replicas
kubectl scale deployments/kubernetes-bootcamp --replicas=4

# create 4 replicas
kubectl get deployments

# The change was applied, and we have 4 instances of the application available. Next, let’s check if the number of Pods changed:
kubectl get pods -o wide

# There are 4 Pods
kubectl describe deployments/kubernetes-bootcamp
```

## Scale down
```sh
# reduce to 2
kubectl scale deployments/kubernetes-bootcamp --replicas=2

# see the change
kubectl get deployments
kubectl get pods -o wide
```

## Load Balancing

```sh
# describe
kubectl describe services/kubernetes-bootcamp
# set port
export NODE_PORT=$(kubectl get services/kubernetes-bootcamp -o go-template='{{(index .spec.ports 0).nodePort}}')
echo NODE_PORT=$NODE_PORT

# Execute the command multiple times port.
curl $(minikube ip):$NODE_PORT
```

## Credit
https://kubernetes.io/docs/tutorials/kubernetes-basics/