# Hashicorp Vault

- Secrets lifecycle manager
- Manage access through API

## Interacting with Vault

### Running vault in development
[Install Vault](https://learn.hashicorp.com/tutorials/vault/getting-started-install). Development mode will:
* Run in localhost without SSL.
* In-memory storage.
* Start unsealed.
* UI enabled.
* Key/Value secret engine enable.

These are the ways of interacting with vault:
* CLI
* GUI
* API

#### CLI
```sh
# basic command structure
value <command> <subcommand> [options] [ARGUMENTS]
# help
value <command> -help
# help for each path
value path-help PATH
```

Env variables:
```sh
export VAULT_ADDR='http://127.0.0.1:8200'
export VAULT_TOKEN='s.LJrNqYBWP0jdd4QsfKK4m6Vq'
# export VAULT_SKIP_VERIFY='s.LJrNqYBWP0jdd4QsfKK4m6Vq'
# export VAULT_FORMAT='s.LJrNqYBWP0jdd4QsfKK4m6Vq'
```
**run value in dev mode**
```sh
value server -dev
```

#### UI
After running the server `http://127.0.0.1:8200/ui` is available.

#### API
Properties of the API:
* Restful
* User by UI and CLI. Only way to interact with vault.

**example**
```sh
curl --header "X-Vault-Token: $root_token" --request GET $VAULT_ADDR/v1/sys/host-info
```

## Authenticate with vault

**Authentication methods**

* Provided by plugins
* Multiple methods allowed
* Reference external source: LDAP, Github AWS, IAM
* Internal methods. Token, Userpass and AppRole. Token method is enabled by default.

All auth methods are mounted on path/auth. All the method are used to return a token which will be used for log into Vault.

To **choose an auth method** we need to ask:
* Who is going to access Vault?
* How are they going to access it?
* What do they use today?

**Userpass and App Role**

* Userpass: is authentication with username and password
* Approle: more convenient if is going to be used for machine and applications. Consist RoleID and SecretID.

### Configuring Auth methods

```sh
# Listing auth method
vault auth list

# Enable new
vault auth enable [options] TYPE
vault auth enable -path=globopass userpass

# tune an auth method
vault auth tune [options] PATH
vault auth tune -description="First user pass" globopass/

# disable (once disabled all the information is removed)
vault auth disable [OPTIONS] PATH
vault auth disable globopass/
```

**Config userpass**

```sh
vault write auth/userpass/users/ned password=tacos
vault write auth/userpass/users/ned password=tacos
```

**Config App Role**

```sh
# see api help
vault path-help auth/GloboAppRole
# create a role
vault path-help auth/GloboAppRole/role/something
# create role and extra config for number of uses
vault write auth/GloboAppRole/role/webapp role_name="webapp" secret_id_num_uses=1 secret_id_ttl=2h
```

## Using Auth Methods
Auth methods can use CLI, UI or API. `Vault login` for interactive methods and `Value Write` for not interactive.


**usepass**:
```sh
# login using a token
vault login

# login with auth method
vault login [options] [AUTH K=V...]
vault login =method=userpass username=ned

# write with an Auth method
vault write [OPTIONS] PATH [DATA K=V...]
vault write auth/userpass/login/ned password=globomantics
```

**approle**
```sh
# see help
vault path-help auth/GloboAppRole/login
# role_id and secret_id
vault read auth/GloboAppRole/role/webapp/role-id

roleId=test

# Now let's get the secret_id, since we're generating data we
# need to use the write command instead

vault write -force auth/GloboAppRole/role/webapp/secret-id

secretId=SECRETID

# And now we can log in! This also uses the write command
vault write auth/GloboAppRole/login role_id=$roleId secret_id=$secretId
```

## Configuring vault policies
Policies define permissions in vault. Options:
* assign to a token, identity, auth methods
* no versioning (when updating they are just gone)
* default policy: created when vault server is created. Can be edited but more removed
* root policy: cannot be changed or deleted. Full control of vault server.

**Policy Syntax**
* Can be HCL files or JSON.
* Contains a Path.
* Include capabilities.

```bash
# basic path expression
path "somepath/in/vault"

# global
path "somepath/*"

# segment +
# path "somepath/in1/vault" and path "somepath/in2/vault"
path "somepath/+/apikeys"

# resolve entity fro secret engine {{}}
path "secret/{{identity.entity.name}}"

# token look up its own entity by id or name
path "identity/entity/id/{{identity.entity.name}}"
path "identity/entity/id/{{identity.entity.id}}"
```

**Capabilities**
* Create: create key
* Read: read key
* Update: update key
* Delete: delete key
* List: see keys but not its content
* Sudo: define special permission. Allow to occur other actions
* Deny: will overwrite the path

Eg:

```sh
path "accounting/data/*" {
    capabilities = ["create", "read", "update", "delete", "list"]
}

# Allow access to metadata for kv2
path "accounting/metadata/*" {
    capabilities = ["list"]
}

# Deny access to privileged accounting data
path "accounting/data/apitokens/privleged*" {
    capabilities = ["deny"]
}

path "accounting/metadata/apitokens/privileged*" {
    capabilities = ["deny"]
}
```

### Policy assignment
* Directly when created a token
* Applied through an auth method
* Assigned to an entity thorough identity secrets engine

```sh
# list policies
vault policy list

# read contents
vault policy read [options] NAME
vault policy read secrets-mgmt

# Write a new policy or update an existing one
vault policy write [options] NAME PATH | <stdin>
vault policy write secrets-mgmt secrets=mgmt.hcl

# delete
vault policy delete [OPTIONS] NAME
vault policy delete secrets-mgmt

# Format HCL according to HCL guidelines
vault policy fmt [options] PATH
vault policy fmt secrets-mgmt.hcl
```

**Create token to policy**:
```sh
# associate directly with a token
vault token create -policy="secrets-mgmt"

# assign to a user in userpass
vault write auth/userpass/users/ned token_policies="secrets-mgmt"

# assign to an entity in identity
vault write identity/entity/name/ned policies="secrets-mgmt"
```


## Using Vault Token

Tokens are a collection of data that are used to access Vault.

**Token creation** can be issued by:
* From an authentication method.
* Parent token.
* Special process to create root tokens.

**Root tokens**
* can do everything in bolt.
* do not expire.
* created when starting a vault server, existing root token or using an operator command.
* Is a good practice revoque the token as soon aas is possible.

Reason for creating then:
* Perform initial setup.
* Auth method unavailable.
* emergency situation.

**Token properties**
* Id.
* Accessor: view the token process but without retrieving the ID. Also to view issued tokens. See audit logs.
* Type
* Policies
* TTL (Time To Live)
* Orphaned: define if a token has a parent token or is standalone.

### Working with tokens

```sh
# Create token
vault token create [options]
vault token create -policy=my-policy -ttl=60m

# view properties
vault token lookup [options] [ACCESSOR | ID]
vault token lookup -accessor FJSJSFJDSFJMEWMDFS
```
## Credit
* https://app.pluralsight.com/library/courses/hashicorp-certified-vault-associate-getting-started