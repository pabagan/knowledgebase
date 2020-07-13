# GO

* [Go 101s](https://go101.org/article/101.html)
* [Go Wiki](https://github.com/golang/go/wiki/CodeReview)

## Explore Go libraries
* https://github.com/avelino/awesome-go

## Install deps

```sh
# install dependency
go get -u [route]
go get -u github.com/tonyalaribe/todoapi/basestructure/features/todo
```

## Go toolchain

* Step 1: download [go](https://golang.org/dl/) for your system.
* Step 2: test installation
```go
package main

import "fmt"

func main() {
	fmt.Printf("hello, world\n")
}
```

```sh
# build
go build hello.go

# run 
$ ./hello
hello, world
```

### Intalling extra versions

```sh
go get golang.org/dl/go1.10.7

go1.10.7 download

# Use new version
go1.10.7 version
go version go1.10.7 linux/amd64
```

## Import packages

```go
package main

import (
	"fmt"
	"math/rand"
	"time"
)

func main() {
	fmt.Println("My favorite number is", rand.Intn(10))
	fmt.Println("The time is", time.Now())
}
```

Can also import  using:

```go
import "fmt"
import "math"
```

## Functions

```go
package main

import "fmt"

// simple function
func add(x int, y int) int {
	return x + y
}

// multiple result
func swap(x, y string) (string, string) {
	return y, x
}

// Naked return 
//A return statement without arguments returns the named return values
func split(sum int) (x, y int) {
	x = sum * 4 / 9
	y = sum - x
	return
}

func main() {
  fmt.Println(add(42, 13))
  
  a, b := swap("hello", "world")
  fmt.Println(a, b)
  
  fmt.Println(split(17))
}
```

## Variables

```go
package main

import "fmt"

var c, python, java bool

func main() {
	// Zero value variables
  var i int
	var f float64
	var b bool
  var s string
  
  fmt.Printf("%v %v %v %q\n", i, f, b, s)
  
  var i int

  fmt.Println(i, c, python, java)
  
  // Variables with initializers
  var i, j int = 1, 2

  // Short variable declarations
	k := 3
	c, python, java := true, false, "no!"

  fmt.Println(i, j, k, c, python, java)
  
  // Variables with initializers
	var c, python, java = true, false, "no!"
	fmt.Println(i, j, c, python, java)
}
```

## Go basic types
```go
bool

string

int  int8  int16  int32  int64
uint uint8 uint16 uint32 uint64 uintptr

byte // alias for uint8

rune // alias for int32
     // represents a Unicode code point

float32 float64

complex64 complex128
```

Eg
```go
package main

import (
	"fmt"
	"math/cmplx"
)

var (
	ToBe   bool       = false
	MaxInt uint64     = 1<<64 - 1
	z      complex128 = cmplx.Sqrt(-5 + 12i)
)

func main() {
	fmt.Printf("Type: %T Value: %v\n", ToBe, ToBe)
	fmt.Printf("Type: %T Value: %v\n", MaxInt, MaxInt)
	fmt.Printf("Type: %T Value: %v\n", z, z)
}
```

Basic types:

```go
package main

import (
	"fmt"
	"math/cmplx"
)

var (
	ToBe   bool       = false
	MaxInt uint64     = 1<<64 - 1
	z      complex128 = cmplx.Sqrt(-5 + 12i)
)

func main() {
	fmt.Printf("Type: %T Value: %v\n", ToBe, ToBe)
	fmt.Printf("Type: %T Value: %v\n", MaxInt, MaxInt)
	fmt.Printf("Type: %T Value: %v\n", z, z)
}
```

Casting:

```go
package main

import (
	"fmt"
	"math"
)

func main() {
	var x, y int = 3, 4
	var f float64 = math.Sqrt(float64(x*x + y*y))
	var z uint = uint(f)
	fmt.Println(x, y, z)
}
```

Type inference: 

```go
var i int
j := i            // j is an int
i := 42           // int
f := 3.142        // float64
g := 0.867 + 0.5i // complex128
```

## Constants

```go
package main

import "fmt"

const Pi = 3.14

func main() {
	const World = "世界"
	fmt.Println("Hello", World)
	fmt.Println("Happy", Pi, "Day")

	const Truth = true
	fmt.Println("Go rules?", Truth)
}
```

Numeric constants

```go
package main

import "fmt"

const (
	// Create a huge number by shifting a 1 bit left 100 places.
	// In other words, the binary number that is 1 followed by 100 zeroes.
	Big = 1 << 100
	// Shift it right again 99 places, so we end up with 1<<1, or 2.
	Small = Big >> 99
)

func needInt(x int) int { return x*10 + 1 }
func needFloat(x float64) float64 {
	return x * 0.1
}

func main() {
	fmt.Println(needInt(Small))
	fmt.Println(needFloat(Small))
	fmt.Println(needFloat(Big))
}
```
## Loops
```go
package main

import "fmt"

func main() {
	sum := 0
	for i := 0; i < 10; i++ {
		sum += i
	}
  fmt.Println(sum)
  
  // for continued
  sum := 1
	for ; sum < 1000; {
		sum += sum
	}
  fmt.Println(sum)
  
  // while in go
  sum := 1
	for sum < 1000 {
		sum += sum
	}
	fmt.Println(sum)
}
```

## Contiditionals
```go
package main

import (
	"fmt"
	"math"
)

func sqrt(x float64) string {
	if x < 0 {
		return sqrt(-x) + "i"
	}
	return fmt.Sprint(math.Sqrt(x))
}
```

Shorthand if:

```go
func main() {
	fmt.Println(sqrt(2), sqrt(-4))
}

func pow(x, n, lim float64) float64 {
	if v := math.Pow(x, n); v < lim {
		return v
	} else {
    return lim
  }
}
```

## Switch
```go
package main

import (
	"fmt"
	"runtime"
)

func main() {
	fmt.Print("Go runs on ")
	switch os := runtime.GOOS; os {
	case "darwin":
		fmt.Println("OS X.")
	case "linux":
		fmt.Println("Linux.")
	default:
		// freebsd, openbsd,
		// plan9, windows...
		fmt.Printf("%s.\n", os)
  }
  
  // switch without condition
  	t := time.Now()
	switch {
	case t.Hour() < 12:
		fmt.Println("Good morning!")
	case t.Hour() < 17:
		fmt.Println("Good afternoon.")
	default:
		fmt.Println("Good evening.")
	}
}
```

## Defer
Defers the execution of a function until the surrounding function returns.

```go
package main

import "fmt"

func main() {
	defer fmt.Println("world")

	fmt.Println("hello")
}
```

## Pointers

## Credit

* https://tour.golang.org