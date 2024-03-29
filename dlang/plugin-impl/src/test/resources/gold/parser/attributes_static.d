class Foo
{
    static int bar() { return 6; }
    int foobar() { return 7; }
}

unittest
{
Foo f = new Foo;
Foo.bar();      // produces 6
Foo.foobar();   // error, no instance of Foo
f.bar();        // produces 6;
f.foobar();     // produces 7;
}
