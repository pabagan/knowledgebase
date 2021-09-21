# https://www.vaultproject.io/docs/secrets/databases
vault secrets enable database

vault write database/config/my-database \
    plugin_name="..." \
    connection_url="..." \
    allowed_roles="..." \
    username="..." \
    password="..."

# INSTANCE NAME: circular-playground-dev-2
# USERNAME: vault-circular
# PASSPORT: vault-circular
# POSTGRES_DB: postgres
# POSTGRES_USER: postgres
# POSTGRES_PASSWORD: postgres
# POSTGRES_PORT: 5432

vault write database/config/my-database \
    plugin_name="..." \
    connection_url="..." \
    allowed_roles="..." \
    username="..." \
    password="..."
