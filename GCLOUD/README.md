# Google Cloud

## Configure GIT private ssh

1. [Configure Private Git access](https://cloud.google.com/cloud-build/docs/access-private-github-repos).

```sh
ssh-keygen -t rsa -b 4096 -C "pabagan@gmail.com"
eval "$(ssh-agent -s)"
ssh-add ~/.ssh/id_rsa
```