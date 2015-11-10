mixin template Foo()
{
    int x = 5;
}

mixin Foo;

struct Bar
{
    mixin Foo;
}

void test()
{
    writefln("x = %d", x);         // prints 5
    {
        Bar b;
        int x = 3;

        writefln("b.x = %d", b.x); // prints 5
        writefln("x = %d", x);     // prints 3
        {
            mixin Foo;
            writefln("x = %d", x); // prints 5
            x = 4;
            writefln("x = %d", x); // prints 4
        }
        writefln("x = %d", x);     // prints 3
    }
    writefln("x = %d", x);         // prints 5
}

mixin template Foo(T)
{
    T x = 5;
}

mixin Foo!(int);           // create x of type int

mixin template Foo()
{
    void func() { writeln("Foo.func()"); }
}

class Bar
{
    mixin Foo;
}

class Code : Bar
{
    override void func() { writeln("Code.func()"); }
}

void test()
{
    Bar b = new Bar();
    b.func();      // calls Foo.func()

    b = new Code();
    b.func();      // calls Code.func()
}

int y = 3;

mixin template Foo()
{
    int abc() { return y; }
}

void test()
{
    int y = 8;
    mixin Foo; // local y is picked up, not global y
    assert(abc() == 8);
}

mixin template Foo(alias b)
{
    int abc() { return b; }
}

void test()
{
    int y = 8;
    mixin Foo!(y);
    assert(abc() == 8);
}

mixin template duffs_device(alias id1, alias id2, alias s)
{
    void duff_loop()
    {
        if (id1 < id2)
        {
            typeof(id1) n = (id2 - id1 + 7) / 8;
            switch ((id2 - id1) % 8)
            {
                case 0: do { s(); goto case;
                case 7:      s(); goto case;
                case 6:      s(); goto case;
                case 5:      s(); goto case;
                case 4:      s(); goto case;
                case 3:      s(); goto case;
                case 2:      s(); goto case;
                case 1:      s(); continue;
                default:     assert(0, "Impossible");
                        } while (--n > 0);
            }
        }
    }
}

void foo() { writeln("foo"); }

void test()
{
    int i = 1;
    int j = 11;

    mixin duffs_device!(i, j, delegate { foo(); });
    duff_loop();  // executes foo() 10 times
}