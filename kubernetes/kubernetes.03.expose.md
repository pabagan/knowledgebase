# Expose App

## Create a new service
```sh
kubectl get pods
# current Services:
kubectl get services

# expose app at xx port
kubectl expose pabagan/node-ts-backend --type="NodePort" --port 8080

# run again the get services
kubectl get services

# find port
kubectl describe services/kubernetes-bootcamp

# set app port
export NODE_PORT=$(kubectl get services/kubernetes-bootcamp -o go-template='{{(index .spec.ports 0).nodePort}}')
echo NODE_PORT=$NODE_PORT

# query app
curl $(minikube ip):$NODE_PORT
```

## Using Labels
```sh

# created automatically a label
kubectl describe deployment

# label query list of Pods
kubectl get pods -l run=kubernetes-bootcamp

# label query list of Services
kubectl get services -l run=kubernetes-bootcamp

# Get Pod name
export POD_NAME=$(kubectl get pods -o go-template --template '{{range .items}}{{.metadata.name}}{{"\n"}}{{end}}')
echo Name of the Pod: $POD_NAME

# apply new label
kubectl label pod $POD_NAME app=v1

# check
kubectl describe pods $POD_NAME

# query now the list of pods using the new label:
kubectl get pods -l app=v1
```

## Delete a service
```sh
# delete by label
kubectl delete service -l run=kubernetes-bootcamp

# Confirm not exist
kubectl get services
# Confirm not exist and is exposed
curl $(minikube ip):$NODE_PORT

# app is still up
kubectl exec -ti $POD_NAME curl localhost:8080
```

## Credit
https://kubernetes.io/docs/tutorials/kubernetes-basics/