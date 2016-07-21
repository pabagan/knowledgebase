# Typescript

* [Typescript](https://www.typescriptlang.org/)
* [Typescript to JS](https://www.typescriptlang.org/play/)
* [Tsconfig](http://www.typescriptlang.org/docs/handbook/tsconfig-json.html)
* [Compiler Options](http://www.typescriptlang.org/docs/handbook/tsconfig-json.html)
* [JS Module System](https://auth0.com/blog/javascript-module-systems-showdown/)
* [TS Modules](https://www.typescriptlang.org/docs/handbook/modules.html#code-generation-for-modules)

## Table of contents
- [Installation and use](#installation-and-use)
- [Types](#types)
- [Functions](#functions)
- [Rest Operator](#rest-operator)
- [Destructuring (ES6)](#destructuring-es6)
- [Template literals](#template-literals)
- [Classes](#classes)
- [Namespaces](#namespaces)
- [Modules](#modules)
- [Interfaces](#interfaces)
- [Generics](#generics)
- [Decorators](#decorators)
- [Use Javascript libraries (typings)](#use-javascript-libraries-typings)
- [Credits](#credits)

## Installation and use
```bash
npm i -g typescript
```

```bash
tsc <file-name>.ts
```

## Types

TS infers the type:

```ts
// strings
let myName = 'Pablo';
myName = 2.5; // no error

let myRealAge;
myRealAge = 27;
myRealAge = '27'; // no error since is as 'any'

let myRealAge: number;
myRealAge = 27;
myRealAge = '27'; // error yeah!

// bools
let haBool = true;
haBool = 1; // this throw error. No casting in ts;

// arrays
let hobbies = ['Cooking', 'Sports'];
hobbies = [100]; ] // error since infers as array of strings

let hobbies: any[] = ['Cooking', 'Sports'];
hobbies = [100]; // is ok maaa

// tuppes
let tupple: [string, number]  = ['Street Name', 1];

// enums
enum Color {
    Green,
    Blue = 70, // 70
    Purple
}

let myColor: Color = Color.Green;
console.log(myColor) // log -> 0;
let myColorBlue: Color = Color.Blue;
console.log(myColorBlue) // log -> 70;

// any (aka The Devil, not used)
let hobbies: any = 'ji';
hobbies = 9.9999;
hobbies = true;

```

## Functions
```ts
// voids
function setValue(value): void {
    this.value = value;
};

// returns
function sayHello(str = 'Default value'): void {
    console.log(str);
};

// arguments
function multiply(arg1: number, arg2: number): string {
    // ...
    return arg1 * arg2;
};

// object
//let userData = { // infer the first assigned object if type
let userData: {name: string, age: number} = {
    name: "Max",
    age: 27
}
```

### User functions as types
```ts
// bad stuff
let multiply = sayHello;
multiply(); // hello
multiply = multiply;
console.log(multiply(5,6)); // 30

// fix define a function type
// var: (arg1: type, ...) => return type
// ex
let multiply: (val1: number, val2: number) => number;

```

### Complex types and alias
```ts
let complex: {data: number[], output: (all: boolean) => number[]} = {
    data: [100, 3.99, 10],

    output: function(all: boolean): number[] {
        return this.data;
    }
}

```

Let types/alias:
```ts
// define alias
type Complex = {data: number[], output: (all: boolean) }

let complex: {data: number[], output: (all: boolean) => number[]} = {
    data: [100, 3.99, 10],

    output: function(all: boolean): number[] {
        return this.data;
    }
}
```

### Unions
Assign sevral optional value types:

```ts
let age: number | string = 27;
age = '27';
```

### Never types
Never return are managed with never type:

```ts
function neverReturns(): never{
    throw new Error('An error');
}
```

### Nullable types

Usefull to be aware of not defining null in the code.
```ts
let canBeNull = 12;
canBeNull = null;   // assign null to clear values
```

To activate need the tsconfig.json activate `"strictNullChecks": false`.

```ts
let canBeNull: number | null = 12;
canBeNull = null;   // assign null to clear values
```

## Rest Operator
```js
function makeArray(name: string, ...args: number[]){
    return args;
}

console.log(makeArray("Cheli", 1,2,3,4,45,77));
```

## Destructuring (ES6)

```ts
// destucturing arrays
const myHobbies = ["Cookies", "Sports"];
const [hobby1, hobby2] = myHobbies;
const hobbie1 = myHobbies[0];
const hobbie2 = myHobbies[1];
console.log(hobby1, bobby2);

// destructuring object
const userData = {userName: "Max", age: 27};
const userName = userData.userName;
const age = userData.age;
console.log(userName, age);

const {userName, age} = userData;
// using alias to rename properties
const {userName: otherName, age: year} = userData;
```

## Template literals

```ts
const userName = "Max";
const greetings = "Hello I'm " + userName;
const greetings = `Hello I'm ${userName}`;
// also multiline
const greetings = ` This is a heading!
I'm a ${username}
`;
console.log(greeting);
```


## Classes

```ts
class Person {
    // you can create properties here different
    // than es6
    name: string; // if not modifier is public
    private type: string;
    protected age: number = 27;

    // I could pass the modifgier to the constructor
    // argument to avoid declare the porperties
    // ot of the constructor
    constructor(name: string, public username: string) {
        this.name = name;
    }

    // [public, private, protected] methodName { }
    printAge(){
        console.log(this.age);    }
        this.setType("Old guy");

    setType(type: string){
        this.type = type;
        console.log(this.type);
    }
}

const person = new Person("Max", "max");
console.log(person);
console.log(person.name);
console.log(person.username);
```

### Inheritance
```ts
class MaxClass extends Person {
    name = "Max";
}

// we still need to use the old constructor with
// su argument
const max = new Max("Anna", "max");
console.log(max.name) // name = Max
```

Constructor:

```ts
class MaxClass extends Person {
    constructor(username: string){
        super("Max", username);
        this.age = 31; // can access to the protected
        console.log(this.type); // this causes an error
    }
}

// we still need to use the old constructor with
// su argument
const max = new Max("max");
console.log(max.name) // name = Max
```

### Getters and Setters

```ts
class Plant {
    // not underscores for species
    private _species: string;

    get species(){
        return "Hello!";
        return this._species;
    }
    // let declare the access property
    set species(value: string){
        if(value.length > 3){
            this._species = value;
        } else {
            this._species = "Default";
        }
    }
}

let plant = new Plant();
console.log(plant.species); // Default
plant.species = "AB";
console.log(plant.species); // AB
plant.species = "Green Plant";
console.log(plant.species); // AB
```

### Static Properties & Methods

```ts
class Helpers {
    static PI: number = 3.14;
    static calcCircumference(diameter: number): number {
        return this.PI * diameter;
    };
}

console.log(2 * Helpers.PI);
console.log(Helpers.calcCircumference(8));
```

### Abstract
```ts
abstract class Project {
    projectName: string = "Default";
    budget: number;

    abstract changeName(name: string): void;

    calcBudget(){
        return this.budget * 2;
    }
}

class ITProject extends Project {
    changeName(name: string): void {
        this.projectName = name;
    }
}

let newProject = new ITProject();
console.log(newProject); // Default
newProject.changeName("Super IT Project");
console.log(newProject); // Super IT Project
```

### Private constructor
```ts
class OnlyOne {
    private static instance: OnlyOne;

    private constructor (public name: string){}

    static getInstance(){
        if(!OnlyOne.instance){
            OnlyOne.instance = new OnlyOne('The Only One');
        }

        return OnlyOne.instance;
    }
}

let wrong = new OnlyOne('The Only One'); // error
let right = OnlyOne.getInstance();
console.log(right.name);
```

### Readonly properties

Set on a private property just the getter
```ts
class Plant {
    // not underscores for species
    private _species: string;

    get species(){
        return this._species;
    }
}
```

Or use `readonly`:

```ts
class Plant {
    // not underscores for species
    public readonly name: string;

    constructor(name: string){
        this.name = name;
    }
}
```

## Namespaces

### Namespaces
```ts
namespace MyMath {
    const PI = 3.14;

    export function calculateCircumference(diameter: number): number{
        return diameter * PI;
    }

    export function calculateRectangle(width: number, length: number): number{
        return width * length;
    }
}

const PI = 2.99;
console.log(MyMath.calculateRectangle(10,20));
console.log(MyMath.calculateCircumference(20));
console.log(PI);
```

### Namespaces and multiple files

Create new file `rectangleMath.ts`
```ts
namespace MyMath { // notice same namespace already created
    const PI = 3.14;

    export function calculateRectangle(width: number, length: number): number{
        return width * length;
    }
}
```

To compile all the ts files into one:

```sh
tsc --outFile app.js circleMath.ts rectangleMath.ts app.ts
```

### Namespaces imports

`/// <reference path="<route to the file>"/>`

```ts
// watch out order. Is important!
/// <reference path="circleMath.ts"/>
/// <reference path="rectangleMath.ts"/>
console.log(MyMath.calculateRectangle(10,20));
console.log(MyMath.calculateCircumference(20));
console.log(PI);
```

### Namespace nesting
```ts
namespace MyMath { // notice same namespace already created
    const PI = 3.14;

    export function calculateRectangle(width: number, length: number): number{
        return width * length;
    }

    export namespace Circle {
        export function calculateCircumference(diameter: number): number{
            return diameter * PI;
        }
    }
}

console.log(MyMath.calculateRectangle(10,20));
console.log(MyMath.calculateCircumference(20)); // not working anymore
console.log(MyMath.Circle.calculateCircumference(20)); // OK
console.log(PI);
```

Also could import the circle namespace by:

```ts
/// <reference path="circleMath.ts"/>
/// <reference path="rectangleMath.ts"/>
import CircleMath = MyMath.Circle;

console.log(MyMath.calculateRectangle(10,20));
console.log(CircleMath.calculateCircumference(20)); // OK
```

### Limitation of namespaces
* Not too convinient for big projects

## Modules

file: `math/rectangle.ts`:
```ts
export function calculateRectangle(width: number, length: number): number{
    return width * length;
};
```

file: `math/circle.ts`:
```ts
export const PI = 3.1416;

export function calculateCircumference(diameter: number): number{
    return diameter * PI;
}
```

Import with modules at file `app.ts`:

```ts
import {PI, calculateCircumference} from "./math/circle"
import {calculateRectangle} from "./math/rectangle"

console.log(PI, calculateCircumference(30));
console.log(calculateRectangle(30));
```

### default keyword

Tell which is the default export for a file.
```ts
export default function calculateRectangle(width: number, length: number): number{
    return width * length;
};

// import with
import anyName from "./math/rectangle"
```

### Import types

```ts
import * as Circle from "./math/circle";
import { Component } from "@angular/core"; // watch relative path
import * as Circle from "./math/circle"

console.log(Circle.Pi);
console.log(Circle.calculateCircumference(10));
console.log(calc(20, 50));
```

## Interfaces

```ts
interface NamedPerson {
    firstName: string;
    age?: number; // optional argument
    // unknown property to come
    // if do not know the name of the argument
    [propName: string]: any;
    // method
    greet(lastName: string): void;
}

function greet(person: NamedPerson) {
    console.log("Hello" + person.firstName)
}

const person = {
    firstName: "Max",
    age: 27,
    hobbies: ["Cooking", "Sports"],
    greet(lastName: string){
        console.log("Hi, I am " + this.firstName + " " + lasname)
    }
}

greet(person);
greet({firstName});
person.greet("Anything");
```

### Using with Classes

```ts
class Person implements NamedPerson {
    firstName: string;
    greet(person: NamedPerson) {
        console.log("Hello" + person.firstName)
    }
    // could add new properties to our class
    lastName: string;
}

const myPerson = new Person();
myPerson.firstName = "Maximilian";
greet(myPerson);
myPerson.greet("Anything");
```

### Interfaces and functions Types

```ts
interface DoubleValueFunc {
    (number1: number, number2: number): number
}

const myDoubleFuntion: DoubleValueFunc;
myDoubleFunction = function(value1: number, value2: number){
    return (value1 + value2 ) * 2;
}

myDoubleFunction(2,4);
```

### Interfaces Inheritance
```ts
interface AgedPerson extends NamedPerson {
    age: number;
}

const oldPerson: AgedPerson = {
    age: 27,
    firsName: "Pablo",
    greet(person: NamedPerson) {
        console.log("Hello" + this.firstName)
    }
}

console.log(oldPerson);
```

## Generics

Simple example:

```ts
function echo(data: any){
    return data;
}

console.log(echo("Max"));
console.log(echo("Max").length); // could call methods related to the type
console.log(echo(27));
console.log(echo(27).length); // undefined
console.log(echo({name: "Max", age: 27}).length); // undefined
```

Creating better generic

```ts
// <T> tell typescript this is a generic function
function betterEcho<T>(data: T) {
    return data;
}

// whith this setup I can access to
// properties and better support for
// editor complain.
console.log(betterEcho("Max"));
console.log(betterEcho("Max").length); // could call methods related to the type
console.log(betterEcho<number>(27));
console.log(betterEcho<number>("27")); // will fail
console.log(betterEcho(27).length); // undefined and editor will complain
console.log(betterEcho({name: "Max", age: 27}).length); // undefined
```

### Built-in generic

```ts
// Array
const testResults: Array<number> = [23,45,2.33];
testResutls.push(-2.99);
testResutls.push("23");// this will complain
console.log(testResults);
```

### Generic functions
```ts
function printAll<T>(args: T[]){
    args.forEach((element) => console.log(element));
};

printAll<string>(["Apple", "Banana"]);
```

### Generic Types
```ts
const echo2: <T>(data: T) => T = betterEcho;

console.log(echo2<string>("Something"));
```

### Generic Classes

Non generic:
```ts
class SimpleMath {
    baseValue;
    multiplyValue;
    calculate(){
        return this.baseValue * this.multiplyValue;
    }
}

const simpleMath = new SimpleMath();
simpleMath.baseValue = 10;
simpleMath.multiplyValue = 20;

console.log(simpleMath.calculate()); // 200

simpleMath.multiplyValue = "20";
console.log(simpleMath.calculate()); // NaN

```


Make generic:
```ts
class SimpleMath<T> {
    baseValue: T;
    multiplyValue: T;
    calculate(){
        return +this.baseValue * +this.multiplyValue;
    }
}

const simpleMath = new SimpleMath();
simpleMath.baseValue = 10;
simpleMath.multiplyValue = 20;
console.log(simpleMath.calculate()); // 200
```

### Generic constraint

```ts
class SimpleMath<T extends number | string> {
    baseValue: T;
    multiplyValue: T;
    calculate(){
        return +this.baseValue * +this.multiplyValue;
    }
}

const simpleMath = new SimpleMath();
simpleMath.baseValue = "arooooooooo"; // could be string
simpleMath.multiplyValue = 20;
console.log(simpleMath.calculate()); // 200
```

### Use more than one generic
```ts
class SimpleMath<T extends number | string, U extends number | string> {
    baseValue: T;
    multiplyValue: U;
    calculate(){
        return +this.baseValue * +this.multiplyValue;
    }
}

const simpleMath = new SimpleMath<string, number>();
simpleMath.baseValue = "10";
simpleMath.multiplyValue = 20;
console.log(simpleMath.calculate()); // 200
```

### Create a Generic Map
```ts
class MyMap<T> {
    private map: {[key: string]: T} = {};

    setItem(key: string, item: T) {
        this.map[key] = item;
    }

    getItem(key: string) {
        return this.map[key];
    }

    clear() {
        this.map = {};
    }

    printMap() {
        for (let key in this.map) {
            console.log(key, this.map[key]);
        }
    }
}

const numberMap = new MyMap<number>();
numberMap.setItem("apples", 10);
numberMap.setItem("bananas", 2);
console.log(numberMap.getItem("apples"));
numberMap.printMap();
numberMap.clear();
numberMap.printMap();

const stringMap = new MyMap<string>();
stringMap.setItem("apples", "10");
stringMap.setItem("bananas", "2");
console.log(stringMap.getItem("apples"));
stringMap.printMap();
stringMap.clear();
stringMap.printMap();
```

## Decorators
* []()
Functions that can attach to properties, methods, classes to transform then.


add a decorator to a class:

```ts
// a decorator is just a function
function logged(constructorFn: Function){
    console.log(constructoFn);
}

@logged
class Person {
    constructor(){
        console.log('Hi!')
    }
}

// Decorators Factory
function loggin(value: boolean){
    return value ? logged : null;
}

@loggin(true)
class Car {

}

// will print logged function

@loggin(false)
class Car {

}

// will NOT print logged function
```

### Advanced decorator
```ts
function printable(constructorFn: Function){
    constructorFn.prototype.print = function(){
        console.log(this);
    }
}

@loggin(true) // note multiple decorators could be used
@printable
class Plant {
    name = "Green Plant"
}

const plant = new Plant();
(<any>plant).print();
```

### Method Decorator

```ts
// method decorator
function editable(value: boolean){
    return function(target: any, propName: string, descriptor: PropertyDescriptor) {
        descriptor.writable = value;
    }
}

class Project {
    projectName: string;

    constructor(name: string){
        this.projectName  = name;
    }

    @editable(false)
    calcBudget() {
        console.log(1000);
    }
}

const project = new Project("Super Project");
project.calcBudget();
project.calcBudget = function() {
    console.log(2000);
};

project.calcBudget();
```

### Property Decorator

```ts
// method decorator
function editable(value: boolean){
    return function(target: any, propName: string, descriptor: PropertyDescriptor) {
        descriptor.writable = value;
    }
}

// property decorator
function overwritable(value: boolean) {
    return function(target: any, propName: string): any {
        const newDescriptor: PropertyDescriptor = {
            writable: value;
        };

        return newDescriptor;
    }

}

class Project {
    @overwriteble(false) // ose decorator
    projectName: string;

    constructor(name: string){
        this.projectName  = name;
    }

    @editable(false)
    calcBudget() {
        console.log(1000);
    }
}

const project = new Project("Super Project");
project.calcBudget();
project.calcBudget = function() {
    console.log(2000);
};

project.calcBudget();
```

### Parameter Decorator

```ts
function printInfo(target: any, methodName: string, paramIndex: number){
    console.log("Target: ",  target);
    console.log("methodName: ", methodName);
    console.log("paramIndex: ", paramIndex);
}

class Course {
    name: string;

    constructor(name: string){
        this.name = name;
    }

    printStudentNumbers(mode: string, @printInfo printAll: boolean) {
        if(printAll) {
            console.log(10000);
        } else {
            console.log(2000);
        }
    }
}

const course = new Course();
couser.printStudentNumbers("anything", true);
couser.printStudentNumbers("anything", false);
```

## Use Javascript libraries (typings)

* [DefinetelyType](http://definitelytyped.org/) is the repo webpage to search and get typed libraries.

There is a package to get automatically typings. Install by:

```bash
npm i -g typings
```

Install types by:

```sh
# the global here is not meaning same as for npm
# Here means that any file inside the project will
# be able to access the installed typing.
typings install dt~jquery --global --save
```

This will create a `/typings` folder within a `typing.json` into it.

### Easy type management since Typescript 2.0

Using `@types`.

```sh
npm install --save-dev @type/jquery
```

## Credits
* [Understanding Typescript Udemy](https://www.udemy.com/understanding-typescript/).
* [Michel Bitter](https://twitter.com/michelbitter) for presenting the course :)