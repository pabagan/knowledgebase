# Firebase
(working on...)

## Firebase Functions

### Install dependencies
```sh
# install global
npm install -g firebase-tools

# install deps
npm install firebase firebase-functions@latest firebase-admin@latest --save
```

### Init Firebase project
```sh
firebase login

# on project directory create /function
firebase init functions
```

### Deploy Functions
```sh
# deploy all functions
firebase deploy --only functions
# deploy a concrete functions
firebase deploy --only functions:helloWorld
```

## Execute in Local
```sh
firebase serve --only functions
firebase experimental:functions:shell

# ... both methods are hot-reloading on code change.. coooooooooool!
```
* See [how to invoque from cons type of function](https://firebase.google.com/docs/functions/local-emulator?authuser=0).


### Linting
Firebase `firebase init functions` already provides linter config. I will stick to it.

## Dealing with Environmnet
```bash
# set
firebase functions:config:set someservice.key="THE API KEY"
# get
firebase functions:config:get
# unset
firebase functions:config:unset [key1] [key2...]
# clone
firebase functions:config:clone --from <fromProject>

# eg
firebase functions:config:unset gcs.keyfilename
firebase functions:config:set gcs.keyfilename="./config/certs/development-cert.json"
```

### Reading environment from APP
```js
const functions = require('firebase-functions');
const request = require('request-promise');
console.log(functions.config().someservice.key); // THE API KEY
```

## See logs

* `console.log()` --> registry level INFO.
* `console.info()` --> registry level INFO.
* `console.warn()` --> registry level ERROR.
* `console.error()`--> registry level ERROR.

```sh
# functions registry
firebase functions:log

# one function registry
firebase functions:log --only <FUNCTION_NAME>

# help
firebase help functions:log
```

## Testing

### No HTTPs functions


### Emulating high load tests
Could use [Artillery](https://artillery.io/) to test functions in high load:
```bash
artillery quick -d 300 -r 30 <URL> # take URL 30 Queries Per Second por 300 segundos.
```