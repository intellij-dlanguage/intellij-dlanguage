
// invalid type declared in enum member
enum X {
    int a
}

// invalid type declared in enum member
enum : long {
    short a = 3
}

// 2 successive commas
enum A {
    a,,
}

// deprecated with no name
enum A {
	a,
	deprecated,
}

// attribute with no name
enum A {
	c,
	@test,
}
