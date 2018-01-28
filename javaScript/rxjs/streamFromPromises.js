import $ from 'jquery';
import Rx from 'rxjs/Rx';

const promise = new Promise((resolve, reject) => {
  console.log('Creating promise');
  setTimeout(() => {
    resolve('Hello from promise');
  }, 3000);
});

//promise.then(x => console.log(x));

const source$ = Rx.Observable.fromPromise(promise);

source$
  .subscribe(
  x => console.log(x),
  err => console.log(err),
  complete => console.log('Completed'),
);

function getUser(username) {
  return $.ajax({
    url: 'https:///api.github.com/users/' + username,
    dataType: "jsonp"
  }).promise();
}

Rx.Observable.fromPromise(getUser('pabagan'))
  .map(r => r.data)
  .subscribe(x => console.log(x));