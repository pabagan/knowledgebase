# GO

* [Go 101s](https://go101.org/article/101.html)
* [Go tour](https://tour.golang.org/list)
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

# Explore language

Check main at [sandbox.go](https://github.com/pabagan/knowledgebase/blob/chore/learn-go/go/go-tour/sandbox.go#L218) at `/go-tour`


## Credit

* https://tour.golang.org