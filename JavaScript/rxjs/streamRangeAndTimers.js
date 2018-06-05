import $ from 'jquery';
import Rx from 'rxjs/Rx';

const sourceIntval$ = Rx.Observable.interval(100)
  .take(5); // repeat just 5 times

sourceIntval$
  .subscribe(
  x => console.log('sourceIntval$', Sx),
    err => console.log(err),
    complete => console.log('Completed'),
  );

const sourceTimer$ = Rx.Observable.timer(1000, 1000) // (amount untin start, repeat intval)
  .take(5); // repeat just 5 times

sourceTimer$
  .subscribe(
    x => console.log('sourceTimer$', x),
    err => console.log(err),
    complete => console.log('Completed'),
  );

const sourceRange$ = Rx.Observable.range(0, 1000); // (start, until)

sourceRange$
  .subscribe(
  x => console.log('sourceRange$', x),
    err => console.log(err),
    complete => console.log('Completed'),
  );