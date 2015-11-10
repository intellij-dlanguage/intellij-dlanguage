template Foo(alias int x) { }
int x;
float f;

Foo!x;  // ok
Foo!f;  // fails to instantiate