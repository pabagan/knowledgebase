import $ from 'jquery';
import Rx from 'rxjs/Rx';

const source1$ = Rx.Observable.range(0, 5).map(v => "source1$: "+v);
const source2$ = Rx.Observable.range(6, 5).map(v => "source2$: "+v);

//
// mergeMap:
//
/*
// NOOOO: necesitamos 2 observables con herencia 2 de 1
Rx.Observable.of('Mello')
  .subscribe(x => {
    Rx.Observable.of(x + ' Everyone')
    .subscribe(x => console.log(x))
  });
  */

Rx.Observable.of('Hello')
  .mergeMap(x => {
    return Rx.Observable.of(x + ' Everyone')
  })
  .subscribe(x => console.log(x))
//
// swichtMap:
//
Rx.Observable.merge(source1$, source2$)
  .take(25)
  .subscribe(x => console.log(x));

//
// concatMap:
//
Rx.Observable.merge(source1$, source2$)
  .take(25)
  .subscribe(x => console.log(x));

