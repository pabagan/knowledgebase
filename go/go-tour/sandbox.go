package main

import (
	"fmt"
	"io"
	"math"
	"math/cmplx"
	"math/rand"
	"runtime"
	"strings"
	"time"
)

func add(x int, y int) int {
	return x + y
}

func swap(x, y string) (string, string) {
	return y, x
}

func split(sum int) (x, y int) {
	x = sum * 4 / 9
	y = sum - x
	return
}

func needInt(x int) int { return x*10 + 1 }
func needFloat(x float64) float64 {
	return x * 0.1
}

func sqrt(x float64) string {
	if x < 0 {
		return sqrt(-x) + "i"
	}
	return fmt.Sprint(math.Sqrt(x))
}

func pow(x, n, lim float64) float64 {
	if v := math.Pow(x, n); v < lim {
		return v
	} else {
		fmt.Printf("Pow function: %g >= %g\n", v, lim)
	}
	// can't use v here, though
	return lim
}

func Sqrt(x float64) float64 {
	var z float64 = 1
	for math.Sqrt(z) < x {
		fmt.Println(
			"Number",
			z,
			"Square root",
			int(math.Sqrt(z)),
		)

		z += 1
	}

	return z
}

type Vertex struct {
	X int
	Y int

	// could use struct literals
	// X, Y int
}

func printSlice(s []int) {
	fmt.Printf("Arrays len and capacity: len=%d cap=%d %v\n", len(s), cap(s), s)
}

func compute(fn func(float64, float64) float64) float64 {
	return fn(3, 4)
}

func adder() func(int) int {
	sum := 0
	return func(x int) int {
		sum += x
		return sum
	}
}

type VertexFloat struct {
	X, Y float64
}

func Abs(v VertexFloat) float64 {
	return math.Sqrt(v.X*v.X + v.Y*v.Y)
}

type MyFloat float64

func (f MyFloat) Abs() float64 {
	if f < 0 {
		return float64(-f)
	}
	return float64(f)
}

func (v VertexFloat) Abs() float64 {
	return math.Sqrt(v.X*v.X + v.Y*v.Y)
}

func (v *VertexFloat) Scale(f float64) {
	v.X = v.X * f
	v.Y = v.Y * f
}

func ScaleFunc(v *VertexFloat, f float64) {
	v.X = v.X * f
	v.Y = v.Y * f
}

type Abser interface {
	Abs() float64
}

type Igo interface {
	M()
}

type Tgo struct {
	S string
}

// This method means type T implements the interface I,
// but we don't need to explicitly declare that it does so.
func (t Tgo) M() {
	fmt.Println(t.S)
}

type IWithMethod interface {
	M()
}

type IEmpty interface{}

func describe_interface(i IEmpty) {
	fmt.Printf("Interfaces( %v, %T)\n", i, i)
}

type Person struct {
	Name string
	Age  int
}

func (p Person) String() string {
	return fmt.Sprintf("%v (%v years)", p.Name, p.Age)
}

type IPAddr [4]byte

// TODO: Add a "String() string" method to IPAddr.
func (p IPAddr) String() string {
	return fmt.Sprintf("%v.%v.%v.%v", p[0], p[1], p[2], p[3])
}

type MyError struct {
	When time.Time
	What string
}

func (e *MyError) Error() string {
	return fmt.Sprintf("at %v, %s",
		e.When, e.What)
}

func run() error {
	return &MyError{
		time.Now(),
		"it didn't work",
	}
}

func say(s string) {
	for i := 0; i < 5; i++ {
		time.Sleep(100 * time.Millisecond)
		fmt.Println(s)
	}
}

func sum(s []int, c chan int) {
	sum := 0
	for _, v := range s {
		sum += v
	}
	c <- sum // send sum to c
}

func fibonacci(n int, c chan int) {
	x, y := 0, 1
	for i := 0; i < n; i++ {
		c <- x
		x, y = y, x+y
	}
	close(c)
}
func fibonacciSelect(c, quit chan int) {
	x, y := 0, 1
	for {
		select {
		case c <- x:
			x, y = y, x+y
		case <-quit:
			fmt.Println("quit")
			return
		}
	}
}

func main() {
	fmt.Println("Print lines: Welcome to the playground!")
	fmt.Println("Print lines: The time is", time.Now())
	fmt.Println("Print lines: My fauvorite random number is", rand.Intn(10))
	fmt.Printf("Print lines: Now you have %g problems.\n", math.Sqrt(7))
	fmt.Println("Print lines: Pi value: ", math.Pi)
	fmt.Println("Print lines: Run function: ", add(42, 13))
	// fmt.Println("Naked return values", split(17))

	a, b := swap("hello", "world")
	fmt.Println("Print lines: swap function", a, b)

	// variables
	var c, python, java bool
	var i int
	fmt.Println("Variables:", i, c, python, java)

	// variables: one line initializers
	var d, other, another = true, false, "no!"
	fmt.Println("Variables:", d, other, another)

	// Short variable declarations
	k := 3
	fmt.Println("Variables:", k)

	// Basic Types
	var (
		ToBe   bool       = false
		MaxInt uint64     = 1<<64 - 1
		z      complex128 = cmplx.Sqrt(-5 + 12i)
	)

	fmt.Printf("Basic types: %T Value: %v\n", ToBe, ToBe)
	fmt.Printf("Basic types: %T Value: %v\n", MaxInt, MaxInt)
	fmt.Printf("Basic types: %T Value: %v\n", z, z)

	// Type conversion
	// Some numeric conversions:

	var TypeA int = 42
	var TypeB float64 = float64(i)
	var TypeC uint = uint(TypeA)

	// Or, put more simply:
	TypeD := 42
	TypeE := float64(i)
	TypeF := uint(TypeA)

	fmt.Println("Basic types conversion", TypeA, TypeB, TypeC, TypeD, TypeE, TypeF)

	// Type inference
	v := 42 // change me!
	fmt.Printf("Basic types inference: v is of type %T\n", v)

	// Contants
	const World = "世界"
	fmt.Println("Constants", "Hello", World)

	const Truth = true
	fmt.Println("Constants", "Go rules?", Truth)

	// Numeric constants
	const (
		// Create a huge number by shifting a 1 bit left 100 places.
		// In other words, the binary number that is 1 followed by 100 zeroes.
		Big = 1 << 100
		// Shift it right again 99 places, so we end up with 1<<1, or 2.
		Small = Big >> 99
	)

	fmt.Println("Constants", needInt(Small))
	fmt.Println("Constants", needFloat(Small))
	fmt.Println("Constants", needFloat(Big))

	// Loops

	// for
	loop_sum := 0
	for i := 0; i < 10; i++ {
		loop_sum += i
	}
	fmt.Println("Loop loop_sum", loop_sum)

	// for continued
	for_continued := 1
	for for_continued < 1000 {
		for_continued += for_continued
	}
	fmt.Println("Loop for_continued", for_continued)

	// while is spelled for in Go
	while_shape := 1
	for while_shape < 1000 {
		while_shape += while_shape
	}
	fmt.Println("Loop while shape", while_shape)

	// Conditionals
	fmt.Println("Conditionals functions", sqrt(2), sqrt(-4))
	fmt.Println(
		"Conditionals: short declaration",
		pow(3, 2, 10),
		pow(3, 3, 20),
	)

	fmt.Println("Conditionals: Square root", Sqrt(2))

	// Switch
	fmt.Print("Switch: Go runs on ")
	switch os := runtime.GOOS; os {
	case "darwin":
		fmt.Println("Switch: OS X.")
	case "linux":
		fmt.Println("Switch: Linux.")
	default:
		// freebsd, openbsd,
		// plan9, windows...
		fmt.Printf("Switch other systems: %s.\n", os)
	}

	fmt.Println("Switch: When's Saturday?")
	today := time.Now().Weekday()
	switch time.Saturday {
	case today + 0:
		fmt.Println("Switch print day: Today.")
	case today + 1:
		fmt.Println("Switch print day: Tomorrow.")
	case today + 2:
		fmt.Println("Switch print day: In two days.")
	default:
		fmt.Println("Switch print day: Too far away.")
	}

	// Switch no condition
	t := time.Now()
	switch {
	case t.Hour() < 12:
		fmt.Println("Swiths print: Good morning!")
	case t.Hour() < 17:
		fmt.Println("Swiths print: Good afternoon.")
	default:
		fmt.Println("Swiths print: Good evening.")
	}

	// Defer functions
	// defer fmt.Println("Defer function")

	// fmt.Println("hello")

	// fmt.Println("Defer counting")

	// for i := 0; i < 10; i++ {
	// 	defer fmt.Println(i)
	// }

	// fmt.Println("Defer counting done")

	// Pointers
	pointer_i, pointer_j := 42, 2701

	p := &pointer_i                                        // point to i
	fmt.Println("Pointer operation with p pointing p", *p) // read i through the pointer
	*p = 21                                                // set i through the pointer
	fmt.Println("Pointer i", pointer_i)                    // see the new value of i

	p = &pointer_j                      // point to j
	*p = *p / 37                        // divide j through the pointer
	fmt.Println("Pointer j", pointer_j) // see the new value of j

	// Struct
	fmt.Println("Struct", Vertex{1, 2})

	vertex := Vertex{1, 2}
	vertex.X = 4
	fmt.Println("Struct reassign", vertex.X)

	struct_pointers_v := Vertex{1, 2}
	struct_pointers_p := &struct_pointers_v
	struct_pointers_p.X = 1e9
	fmt.Println("Pointers to struct", struct_pointers_v)

	var (
		v1 = Vertex{1, 2}  // has type Vertex
		v2 = Vertex{X: 1}  // Y:0 is implicit
		v3 = Vertex{}      // X:0 and Y:0
		v4 = &Vertex{1, 2} // has type *Vertex
	)

	fmt.Println("Pointers assign", v1, v4, v2, v3)

	// Arrays
	var go_array [2]string
	go_array[0] = "Hello"
	go_array[1] = "World"
	fmt.Println("Arrays", go_array[0], go_array[1])
	fmt.Println("Arrays", go_array)

	primes := [6]int{2, 3, 5, 7, 11, 13}
	fmt.Println("Arrays", primes)

	var array_slice []int = primes[1:4]
	fmt.Println("Arrays array_slice", array_slice)

	names := [4]string{
		"John",
		"Paul",
		"George",
		"Ringo",
	}
	fmt.Println("Arrays references to arrays", names)

	print_a := names[0:2]
	print_b := names[1:3]
	fmt.Println("Arrays references to arrays", print_a, print_b)

	print_b[0] = "XXX"
	fmt.Println("Arrays references to arrays", print_a, print_b)
	fmt.Println("Arrays references to arrays", names)

	print_q := []int{2, 3, 5, 7, 11, 13}
	fmt.Println("Arrays slice literals", print_q)

	print_r := []bool{true, false, true, true, false, true}
	fmt.Println("Arrays slice literals", print_r)

	print_s := []struct {
		i int
		b bool
	}{
		{2, true},
		{3, false},
		{5, true},
		{7, true},
		{11, false},
		{13, true},
	}
	fmt.Println("Arrays slice literals", print_s)

	array_slice_defaults := []int{2, 3, 5, 7, 11, 13}
	array_slice_defaults = array_slice_defaults[:2]
	fmt.Println("Arrays array_slice_defaults", array_slice_defaults)

	array_slice_defaults = array_slice_defaults[1:]
	fmt.Println("Arrays array_slice_defaults", array_slice_defaults)

	array_len_and_capacity := []int{2, 3, 5, 7, 11, 13}
	printSlice(array_len_and_capacity)

	array_len_and_capacity = array_len_and_capacity[:0]
	printSlice(array_len_and_capacity)

	array_len_and_capacity = array_len_and_capacity[:4]
	printSlice(array_len_and_capacity)

	array_len_and_capacity = array_len_and_capacity[2:]
	printSlice(array_len_and_capacity)

	var s []int
	fmt.Println("Arrays empty print length and capacity: ", s, len(s), cap(s))
	if s == nil {
		fmt.Println("Arrays empty is equal nil!")
	}

	// Create a tic-tac-toe board.
	board := [][]string{
		[]string{"_", "_", "_"},
		[]string{"_", "_", "_"},
		[]string{"_", "_", "_"},
	}

	// The players take turns.
	board[0][0] = "X"
	board[2][2] = "O"
	board[1][2] = "X"
	board[1][0] = "O"
	board[0][2] = "X"

	for i := 0; i < len(board); i++ {
		fmt.Printf("Arrays 2 dimensions: %s\n", strings.Join(board[i], " "))
	}

	var append_slice []int
	printSlice(append_slice)

	append_slice = append(append_slice, 0)
	printSlice(append_slice)

	append_slice = append(append_slice, 1)
	printSlice(append_slice)

	append_slice = append(append_slice, 2, 3, 4)
	printSlice(append_slice)

	// Range
	var pow = []int{1, 2, 4, 8, 16, 32, 64, 128}

	for i, v := range pow {
		fmt.Printf("Ranges 2**%d = %d\n", i, v)
	}

	range_continued := make([]int, 10)
	for i := range range_continued {
		range_continued[i] = 1 << uint(i) // == 2**i
	}
	for _, value := range range_continued {
		fmt.Printf("Ranges continued %d\n", value)
	}

	// Maps
	type LatLong struct {
		Lat, Long float64
	}

	var m map[string]LatLong

	m = make(map[string]LatLong)
	m["Bell Labs"] = LatLong{
		40.68433, -74.39967,
	}
	fmt.Println("Maps", m["Bell Labs"])

	var map_literal = map[string]LatLong{
		"Bell Labs": LatLong{
			40.68433, -74.39967,
		},
		"Google": LatLong{
			37.42202, -122.08408,
		},
	}
	var map_literal_short = map[string]LatLong{
		"Bell Labs": {40.68433, -74.39967},
		"Google":    {37.42202, -122.08408},
	}

	fmt.Println("Maps literal", map_literal)
	fmt.Println("Maps literal short", map_literal_short)

	map_mutate := make(map[string]int)

	map_mutate["Answer"] = 42
	fmt.Println("Maps mutate:", map_mutate["Answer"])

	map_mutate["Answer"] = 48
	fmt.Println("Maps mutate:", map_mutate["Answer"])

	delete(map_mutate, "Answer")
	fmt.Println("Maps mutate:", map_mutate["Answer"])

	v, ok := map_mutate["Answer"]
	fmt.Println("Maps mutate:", v, "Present?", ok)

	// Function values
	hypot := func(x, y float64) float64 {
		return math.Sqrt(x*x + y*y)
	}

	fmt.Println("Functions values", hypot(5, 12))

	fmt.Println("Functions values", compute(hypot))
	fmt.Println("Functions values", compute(math.Pow))

	pos, neg := adder(), adder()
	for i := 0; i < 10; i++ {
		fmt.Println(
			"Functions closures",
			pos(i),
			neg(-2*i),
		)
	}

	vertex_float := VertexFloat{3, 4}
	fmt.Println("Methods", Abs(vertex_float))

	f := MyFloat(-math.Sqrt2)
	fmt.Println("Methods", f.Abs())

	vertex_method_pointer := VertexFloat{3, 4}
	vertex_method_pointer.Scale(10)
	fmt.Println("Methods pointers", vertex_method_pointer.Abs())

	p_method_pointer := &VertexFloat{4, 3}
	p_method_pointer.Scale(3)
	ScaleFunc(p_method_pointer, 8)

	fmt.Println("Methods pointers", vertex_method_pointer, p_method_pointer)

	fmt.Printf("Method pointers: Before scaling: %+v, Abs: %v\n", vertex_float, f.Abs())
	vertex_float.Scale(5)
	fmt.Printf("Method pointers: After scaling: %+v, Abs: %v\n", vertex_float, f.Abs())

	// Interfaces
	var abser_interface Abser
	abser_interface_f := MyFloat(-math.Sqrt2)
	abser_interface_v := VertexFloat{3, 4}

	abser_interface = abser_interface_f  // a MyFloat implements Abser
	abser_interface = &abser_interface_v // a *Vertex implements Abser

	// In the following line, v is a Vertex (not *Vertex)
	// and does NOT implement Abser.
	abser_interface = abser_interface_v

	fmt.Println("Interfaces", abser_interface.Abs())

	var interfaces_i Igo = Tgo{"Interfaces implemented implicitly"}
	interfaces_i.M()

	var nil_interface IWithMethod
	describe_interface(nil_interface)
	// error since nil_interface is nil
	// nil_interface.M()

	var empty_interface IEmpty
	describe_interface(empty_interface)

	empty_interface = 42
	describe_interface(empty_interface)

	empty_interface = "hello"
	describe_interface(empty_interface)

	// Type assertions
	var typeAssertion interface{} = "hello"

	typeAssertion_s := typeAssertion.(string)
	fmt.Println("Type assertions", s)

	typeAssertion_s, typeAssertion_ok := typeAssertion.(string)
	fmt.Println("Type assertions", typeAssertion_s, typeAssertion_ok)

	typeAssertion_f, typeAssertion_ok := typeAssertion.(float64)
	fmt.Println("Type assertions", typeAssertion_f, typeAssertion_ok)

	// will trigger a panic mode
	// typeAssertion_f = typeAssertion.(float64) // panic
	// fmt.Println(typeAssertion_f)

	// Type switches
	var type_switch interface{} = "hola"

	switch v := type_switch.(type) {
	case int:
		fmt.Printf("Type switches: Twice %v is %v\n", v, v*2)
	case string:
		fmt.Printf("Type switches: %q is %v bytes long\n", v, len(v))
	default:
		fmt.Printf("Type switches: I don't know about type %T!\n", v)
	}

	// Stringers
	stringers_a := Person{"Arthur Dent", 42}
	stringers_z := Person{"Zaphod Beeblebrox", 9001}
	fmt.Println("Stringers", stringers_a, stringers_z)

	hosts := map[string]IPAddr{
		"loopback":  {127, 0, 0, 1},
		"googleDNS": {8, 8, 8, 8},
	}
	for name, ip := range hosts {
		fmt.Printf("Stringers exersise: %v: %v\n", name, ip)
	}

	// Errors
	if err := run(); err != nil {
		fmt.Println("Errors", err)
	}

	// Readers
	reader := strings.NewReader("Readers Hello, Reader!")

	reader_bytes := make([]byte, 8)
	for {
		n, err := reader.Read(reader_bytes)
		fmt.Printf("Readers n = %v err = %v b = %v\n", n, err, reader_bytes)
		fmt.Printf("Readers b[:n] = %q\n", reader_bytes[:n])
		if err == io.EOF {
			break
		}
	}

	// Go routines
	go say("Goroutines world")
	say("Goroutines hello")

	channels_s := []int{7, 2, 8, -9, 4, 0}

	channels_c := make(chan int)
	go sum(channels_s[:len(channels_s)/2], channels_c)
	go sum(channels_s[len(channels_s)/2:], channels_c)
	channels_x, channels_y := <-channels_c, <-channels_c // receive from c

	fmt.Println("Goroutines channels", channels_x, channels_y, channels_x+channels_y)

	ch := make(chan int, 2)
	ch <- 1
	ch <- 2
	fmt.Println("Buffered channels", <-ch)
	fmt.Println("Buffered channels", <-ch)

	// Go routines: range and close
	routine_c := make(chan int, 10)
	go fibonacci(cap(routine_c), routine_c)
	for i := range routine_c {
		fmt.Println("Goroutines range and close", i)
	}

	routine_d := make(chan int)
	quit := make(chan int)
	go func() {
		for i := 0; i < 10; i++ {
			fmt.Println("Goroutines select", <-routine_d)
		}
		quit <- 0
	}()
	fibonacciSelect(routine_d, quit)

	tick := time.Tick(100 * time.Millisecond)
	boom := time.After(500 * time.Millisecond)
	for {
		select {
		case <-tick:
			fmt.Println("Goroutines select with default: tick.")
		case <-boom:
			fmt.Println("Goroutines select with default: BOOM!")
			return
		default:
			fmt.Println("Goroutines select with default:     .")
			time.Sleep(50 * time.Millisecond)
		}
	}
}
