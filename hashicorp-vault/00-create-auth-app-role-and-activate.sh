# Login Vault
export VAULT_ADDR=https://vault-prod.build.ingka.ikea.com
export VAULT_NAMESPACE=circular

# Login oidc ikea
vault login -method=oidc

# Activate app role for path playground
vault auth enable approle

# Okay armed with that we can create a new role for an app
vault write auth/approle/role/playground role_name="playground"

vault read auth/approle/role/playground/role-id

roleId=ROLEID

vault write -f auth/approle/role/playground/secret-id

secretId=SECRETID

# Sweet, now we could use that token in future requests
vault write auth/approle/login \
  role_id=$roleId \
  secret_id=$secretId

