int x = 3;

mixin template Foo()
{
    int x = 5;
    int y = 5;
}

mixin Foo;
int y = 3;

void test()
{
    writefln("x = %d", x);  // prints 3
    writefln("y = %d", y);  // prints 3
}

mixin template Foo()
{
    int x = 5;
    void func(int x) { }
}

mixin template Bar()
{
    int x = 4;
    void func(long x) { }
}

mixin Foo;
mixin Bar;

void test()
{
    import std.stdio : writefln;
    writefln("x = %d", x); // error, x is ambiguous
    func(1);               // error, func is ambiguous
}

int x = 6;

mixin template Foo()
{
    int x = 5;
    int y = 7;
    void func() { }
}

mixin template Bar()
{
    int x = 4;
    void func() { }
}

mixin Foo F;
mixin Bar B;

void test()
{
    writefln("y = %d", y);     // prints 7
    writefln("x = %d", x);     // prints 6
    writefln("F.x = %d", F.x); // prints 5
    writefln("B.x = %d", B.x); // prints 4
    F.func();                  // calls Foo.func
    B.func();                  // calls Bar.func
}

mixin template Foo()
{
    void func(int x) {  }
}

mixin template Bar()
{
    void func(long x) {  }
}

mixin Foo!() F;
mixin Bar!() B;

alias func = F.func;
alias func = B.func;

void main()
{
    func(1);  // calls B.func
    func(1L); // calls F.func
}


int x = 4;

mixin template Foo()
{
    int x = 5;
    int bar() { return x; }
}

mixin Foo;

void test()
{
    writefln("x = %d", x);         // prints 4
    writefln("bar() = %d", bar()); // prints 5
}
