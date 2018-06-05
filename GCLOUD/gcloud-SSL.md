# Generate SSL
```sh
# Generate
openssl genrsa -out ssl.key 2048
openssl req -new -key ssl.key -out ssl.csr
openssl x509 -req -days 365 -in ssl.csr -signkey ssl.key -out ssl.crt

# send gCloud
gcloud compute ssl-certificates create express-middleware \
    --certificate ./certs/ssl/ssl.crt \
    --private-key ./certs/ssl/ssl.key
```