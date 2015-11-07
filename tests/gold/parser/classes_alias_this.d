struct S
{
    int x;
    alias x this;
}

int foo(int i) { return i * 2; }

void test()
{
    S s;
    s.x = 7;
    int i = -s;  // i == -7
    i = s + 8;   // i == 15
    i = s + s;   // i == 14
    i = 9 + s;   // i == 16
    i = foo(s);  // implicit conversion to int
}

struct Foo
{
    int baz = 4;
    int get() { return 7; }
}

class Bar
{
    Foo foo;
    alias foo this;
}

void test()
{
    auto bar = new Bar;
    int i = bar.baz; // i == 4
    i = bar.get(); // i == 7
}

struct S
{
    int x;
    @property int get()
    {
        return x * 2;
    }
    alias get this;
}

void test()
{
    S s;
    s.x = 2;
    int i = s; // i == 4
}