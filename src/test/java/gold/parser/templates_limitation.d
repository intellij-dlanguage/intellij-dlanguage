class C
{
    int num;
    void foo(alias sym)() { num = sym * 2; }
}

void main()
{
    auto c = new C();
    int var = 10;
    c.foo!var();    // NG, foo!var requires two contexts, 'this' and 'main()'
}

int sum(alias x, alias y)() { return x + y; }

void main()
{
    int a = 10;
    void nested()
    {
        int b = 20;
        assert(sum!(a, b)() == 30);
    }
    nested();
}

class Foo
{
    template TBar(T)
    {
        T xx;               // becomes a static member of Foo
        int func(T) {  } // non-virtual

        static T yy;                        // Ok
        static int func(T t, int y) {  } // Ok
    }
}