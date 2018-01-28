# RxJs

* (RxJs)http://reactivex.io/rxjs/manual

## Anatomy of an Observable

### Create
```js
// Creating
var observable = Rx.Observable.create(function subscribe(observer) {
  var id = setInterval(() => {
    observer.next('hi')
  }, 1000);
});

// Subscribing
observable.subscribe(x => console.log(x));
```

### Create with complete

```js
var observable = Rx.Observable.create(function subscribe(observer) {
  try {
    observer.next(1);
    observer.next(2);
    observer.next(3);
    observer.complete();
    observer.next(4); // is not executed
  } catch (err) {
    observer.error(err); // delivers an error if it caught one
  }
});
```

### Unsubscribe
```js
var observable = Rx.Observable.interval(1000);
var subscription = observable.subscribe(x => console.log(x));
// Later:
// This cancels the ongoing Observable execution which
// was started by calling subscribe with an Observer.
subscription.unsubscribe();
```

#### Custom unsubscribe
```js
var observable = Rx.Observable.create(function subscribe(observer) {
  // Keep track of the interval resource
  var intervalID = setInterval(() => {
    observer.next('hi');
  }, 1000);

  // Provide a way of canceling and disposing the interval resource
  return function unsubscribe() {
    clearInterval(intervalID);
  };
});
```