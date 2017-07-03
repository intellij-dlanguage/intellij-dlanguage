int i;

enum Foo { E }
unittest{
    Foo f;
    i = f;           // OK
    f = i;           // error
    f = cast(Foo)i;  // OK
    f = 0;           // error
    f = Foo.E;       // OK
}
