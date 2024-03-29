template Foo(alias int x) { }
int x;
float f;

unittest{
    Foo!x;  // ok
    Foo!f;  // fails to instantiate
}
