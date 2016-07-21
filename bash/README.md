# Bash

```bash
# 
# Functions
# 
function ut () {
    UT_LOCATION=~/Dev/www/UT

    cd $UT_LOCATION
    subl ut.sublime-workspace
    gulp
}

# Parameters
function run(){
    echo "With Parameter #1 is $1"
}

#
# Alias
# 
alias jel='run hola' # -> Parameter #1 is hola
```