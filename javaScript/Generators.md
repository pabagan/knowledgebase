# Generators
Generators are functions which execution is stopped until calling the `next()`

```js
function* nextInt(num) {
  console.log(num);

  return yield num + 1
}

const nextIntegerReady = nextInt(2);
// next returns a object {done: boolean, value: yield value}
console.log(nextIntegerReady.next());

console.log(nextIntegerReady.next().done);
console.log(nextIntegerReady.next().value);
```

The `yield` keyowrd add breakpoints which won't be executed until next `next()`:

```js

function* aSpeech() {
  console.log('First part');
  yield;
  console.log('Second part');
  yield;
  console.log('Third part');
}

const aSpeechMan = aSpeech();
aSpeechMan.next(); // First part
aSpeechMan.next(); // Second part
aSpeechMan.next(); // Third part
```