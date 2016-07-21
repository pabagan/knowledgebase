# Angular 2 Tests

* [Angular2 Testing Guide](https://angular.io/docs/ts/latest/guide/testing.html#!#atu-apis).
* [https://jasmine.github.io/](https://jasmine.github.io/).

## Testing with CLI

```bash
# test
ng test
ng test --watch=false or --single-run # run one time
ng test --lint # run one time
# end to end (e2e) test
ng e2e
```

## Jasmine Matchers

```ts
expect(fn).toThrow(e);
expect(instance).toBe(instance);
expect(mixed).toBeDefined();
expect(mixed).toBeFalsy();
expect(number).toBeGreaterThan(number);
expect(number).toBeLessThan(number);
expect(mixed).toBeNull();
expect(mixed).toBeTruthy();
expect(mixed).toBeUndefined();
expect(array).toContain(member);
expect(string).toContain(substring);
expect(mixed).toEqual(mixed);
expect(mixed).toMatch(pattern);
```

## Custom Matchers
```ts
let John = 17, Mary = 16;

it("should allow John to drive", () => {
  expect(John).toBeAllowedToDrive();
  // replaces 
  expect(John).toBeGreaterThan(16);
});

it("should not allow Mary to drive", () => {
  expect(Mary).not.toBeAllowedToDrive();
  // replaces 
  expect(Mary).not.toBeGreaterThan(16);
});
```
