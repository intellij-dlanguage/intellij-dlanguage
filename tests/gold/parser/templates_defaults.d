template Foo(T, U = int) { ... }
Foo!(uint,long); // instantiate Foo with T as uint, and U as long
Foo!(uint);      // instantiate Foo with T as uint, and U as int

template Foo(T, U = T*) { ... }
Foo!(uint);      // instantiate Foo with T as uint, and U as uint*

template Foo(T)
{
    T Foo; // declare variable Foo of type T
}

void test()
{
    Foo!(int) = 6; // instead of Foo!(int).Foo
}

template Bar(T)
{
    class Bar
    {
        T member;
    }
}

class Bar(T)
{
    T member;
}

