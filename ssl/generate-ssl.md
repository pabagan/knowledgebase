# Generate OpenSSL

* [Open SSL](https://www.openssl.org/).

```bash
openssl req -x509 -newkey rsa:4096 -keyout key.pem -out cert.pem -days 365

# no passphrase
openssl req  -nodes -new -x509  -keyout key.pem -out cert.pem

```
