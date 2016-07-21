# Angular2 TDD

## Better tests

### Cleaner console test messages
```bash
# nice test reports at terminal
npm install --save-dev karma-spec-reporter
```

At `karma.conf.js`:

```js
// Angular 2 CLI Karma configuration
// https://karma-runner.github.io/0.13/config/configuration-file.html
module.exports = function (config) {
  config.set({
    //...
    plugins: [
      // ...
      require('karma-spec-reporter'),
      // ...
    ],

    // ...
    
    reporters: config.angularCli && config.angularCli.codeCoverage
    //          ? ['progress', 'karma-remap-istanbul']
    //          : ['progress'],
              ? ['spec', 'karma-remap-istanbul']
              : ['spec'],                            
    // ...
  });
};
```

### Headless test with PhantomJS

```bash
npm install --save-dev phantomjs-prebuilt karma-phantomjs-launcher
```

At `karma.conf.js`:
```js
// Karma configuration file, see link for more information
// https://karma-runner.github.io/0.13/config/configuration-file.html

module.exports = function (config) {
  config.set({
    //...
    plugins: [
      //...
      //require('karma-chrome-launcher'),
      require('phantomjs-prebuilt'),
      require('karma-phantomjs-launcher'),
      //...
    ],

    //...
    //browsers: ['Chrome'],
    browsers: ['PhantomJS'],
    //...
  });
};

```
