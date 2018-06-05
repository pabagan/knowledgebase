import $ from 'jquery';
import Rx from 'rxjs/Rx';

const source$ = Rx.Observable.interval(1000)
.take(10)
.map( v => v * 2);

source$
  .subscribe(
    x => console.log(x),
    err => console.log(err),
    complete => console.log('Completed'),
  );
