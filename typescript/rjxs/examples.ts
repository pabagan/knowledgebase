import { Observable, of, from, timer } from "rxjs";
//
// Simple
//
interface Plant {
  id: number;
  image: string;
}
const getPlants = (): Observable<Plant[]> => {
  let mocked: Plant[] = [
    {
      id: 1,
      image: "hello.png"
    },
    {
      id: 2,
      image: "dui.png"
    }
  ];

  return of(mocked);
};

const observable = getPlants();


observable.subscribe({
  next: x => x.map(item => {
    console.log(`got value x ${item.id} and image ${item.image}`);
  }),
  error: err => console.error('something wrong occurred: ' + err),
  complete: () => console.log('done'),
});

//
// From Promise
//
type APIHomeResponse = string;

// const getHome = (): Observable<Promise<APIHomeResponse>> => {
//     return of(klikkieApi.reqHome());
// };

const home: Observable<APIHomeResponse> = from(klikkieApi.reqHome());

home.subscribe({
  next: response => console.log(response),
  error: err => console.error('something wrong occurred: ', err),
  complete: () => console.log('done'),
});


//
// Timer
//
const timer$ = timer(5000, 3500);

timer$.subscribe(() => klikkieApi.reqHome());