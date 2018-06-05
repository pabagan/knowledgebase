import $ from 'jquery';
import Rx from 'rxjs/Rx';

const source1$ = Rx.Observable.range(0, 5).map(v => "source1$: "+v);
const source2$ = Rx.Observable.range(6, 5).map(v => "source2$: "+v);

//
// Merge: 2 same time
//
Rx.Observable.merge(source1$, source2$)
  .take(25)
  .subscribe(x => console.log(x));


//
// Concat: 1 and then the other
//
Rx.Observable.concat(source1$, source2$)
  .take(25)
  .subscribe(x => console.log(x));