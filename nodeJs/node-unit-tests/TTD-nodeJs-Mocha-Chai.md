# TTD-nodeJs-Express-Mocha-Chai
Following Jordan Kasper's video [here](https://www.youtube.com/watch?v=u2XCdkL4bWI). Thanks Jordan!

## Using
* [Mocha](https://mochajs.org/) testing framework for [Node.js](https://nodejs.org/).
* [Chai](http://chaijs.com/) for assertions.

## Resources
* [MochaJS Library](https://mochajs.org/#table-of-contents).
* [Chai Assertion Library](http://chaijs.com/api/bdd/).

## Install
```bash
npm install --save-dev --save-exact mocha chai superagent
```

## Mocha assertion
```js
describe('Array', function() {
    describe('#indexOf()', function() {
        it('should return -1 when the value is not present', function() {
            assert.equal(-1, [1,2,3].indexOf(4));
        });
    });
});
```
