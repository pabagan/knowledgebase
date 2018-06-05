# Test Driven Development NodeJs, Mocha and Chai

* [Express](http://expressjs.com/).
* [Mocha](https://mochajs.org/) testing framework for [Node.js](https://nodejs.org/).
* [Chai](http://chaijs.com/) for assertions.

## Resources
* [Express API](http://expressjs.com/es/api.html#express).
* [MochaJS Library](https://mochajs.org/#table-of-contents).
* [Chai Assertion Library](http://chaijs.com/api/bdd/).

## Install
```bash
npm install --save --save-exact express
npm install --save-dev --save-exact mocha chai superagent
```

## Mocha assertion
Main [MochaJS](https://mochajs.org/#table-of-contents) assertions:

* equal
* scrictEqual
* deepEqual
* ok
* isNull
* true
* fail
* match
* throws

#### negations

* notEqual
* notOk
* notDeepEqual
* isNotNull
* notMatch


```js
describe('Array', function() {
    describe('#indexOf()', function() {
        it('should return -1 when the value is not present', function() {
            assert.equal(-1, [1,2,3].indexOf(4));
        });
    });
});
```

## Credit
Following Tommy Stanton's video [here](https://www.youtube.com/watch?v=E9Fmewoe5L4).

Jordan Kasper's video [here](https://www.youtube.com/watch?v=u2XCdkL4bWI) and GIT [here](https://github.com/jakerella/node-unit-tests). Great shoot about how hangle node modules for testing https://youtu.be/u2XCdkL4bWI?t=15m6s.

