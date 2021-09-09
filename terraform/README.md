# Terraform

Here is (Terraform)[https://www.terraform.io/]:
1. Defined in code.
2. Code is stored in source control.
3. Declarative approach to deploy infraestructure as a code.
4. Idempotent: if a configuration was deployed and it wasn't updated terraform won't deploy twice the same configuration.
5. Push type model.

## Deploying 1st Terraform Configuration

These are the **terraform components**:
* Terraform executable.
* Terraform files.
* Terraform plugins.
* Terraform state.

## Install Terraform CLI

1. Download: https://www.terraform.io/downloads.html (mac with brew `brew install terraform`) and check it worked with `terraform version`. Get [Terraform Demo Project](https://github.com/pabagan/Getting-Started-Terraform/tree/master/m3) and run in the folder:
2. Run `terraform init` to install plugins
2. Run `terraform plan` 

