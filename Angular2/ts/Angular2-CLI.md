# Angular 2 CLI

Using [Angular2 CLI](https://github.com/angular/angular-cli). Install with `npm install -g angular-cli`.

## Create APP
```bash
ng new angular2-client
ng new angular2-client --style=sass 
ng new angular2-client  --routing # --> create route app
#self
ng new angular2-client --style=sass --routing

# change default css to scss for 
# an existing project
ng set defaults.styleExt scss

# see help
ng help
```


## Start APP
```bash
ng serve               # http://localhost:4200/
# hot-reloading
ng serve --host 0.0.0.0 --port 4201 --live-reload-port 49153
# using proxy
ng serve --proxy-config proxy.conf.json
```

Proxy config at `proxy.conf.json`:

```json
{
  "/api": {
    "target": "http://localhost:3000",
    "secure": false
  }
}
```

## Generate commands

```bash
# Module  
ng g module my-module
ng g module my-module --routing
# Component
ng g component new-cmp      # --> src/app/feature/new-cmp
# Directive   
ng g directive my-new-directive
# Pipe    
ng g pipe my-new-pipe
# Service     
ng g service my-new-service
# Class   
ng g class my-new-class
# Interface   
ng g interface my-new-interface
# Enum    
ng g enum my-new-enum
# Sets base tag href to /myUrl/ in your index.html
ng build --base-href /myUrl/
ng build --bh /myUrl/
```

## Deploy
Create artifacts at `dist/`.

```bash
ng serve --prod 
# Sets base tag href to /myUrl/ in your index.html
ng build
ng build -dev # same up

ng build --base-href /myUrl/
ng build --bh /myUrl/

# Build production
ng build -prod
```

Configuration file for environments on `angular-cli.json`.

note: generating routes is disabled.

## Testing
```bash
# test
ng test
ng test --watch=false or --single-run # run one time
ng test --lint # run one time
# end to end (e2e) test
ng e2e
```

## Deploy Github pages
```bash
ng github-pages:deploy --message "Optional commit message"
# deploying a user or organization page
ng github-pages:deploy --user-page --message "Optional commit message"
```

## Typescript Lint
```bash
ng lint
```

## Update CLI
```bash
# Global
npm uninstall -g angular-cli
npm cache clean
npm install -g angular-cli@latest

# Local 
rm -rf node_modules dist
npm install --save-dev angular-cli@latest
npm install
ng init
```

## Install Library. Ex Bootstrap
Config at `angular-cli.json`:
```json
// ...
    "scripts": [
      "../node_modules/jquery/dist/jquery.js",
      "../node_modules/tether/dist/js/tether.js",
      "../node_modules/bootstrap/dist/js/bootstrap.js"
    ],

    // css  apps[0].styles array:
    "styles": [
      "../node_modules/bootstrap/dist/css/bootstrap.css",
      "styles.css"
    ],
// ...
```
