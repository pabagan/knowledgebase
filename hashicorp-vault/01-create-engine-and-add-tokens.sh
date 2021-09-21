# Login Vault
export VAULT_ADDR=https://vault-prod.build.ingka.ikea.com
export VAULT_NAMESPACE=circular

# Login oidc ikea
vault login -method=oidc

vault secrets enable -path=playground -version=2 kv

vault kv put playground/postgres user=user,password=pass

vault kv list playground
vault kv list playground/postgres
vault kv get playground/postgres
vault kv get -field=user playground/postgres

vault read playground/postgres