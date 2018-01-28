import $ from 'jquery';
import Rx from 'rxjs/Rx';

const source$ = new Rx.Observable(observer => {
  console.log('Creating Observable');

  observer.next('Hello worldie!');


  setTimeout(() => {
    observer.next('Yet another one');
    observer.complete();
  }, 3000);

  // error
  observer.error(new Error("Error: Something went wrong"))
});

source$
  .catch( err => Rx.Observable.of(err)) // catch here to execute the complete function
  .subscribe(
    x => console.log(x),
    err => console.log(err),
    complete => console.log('Completed'),
  );
