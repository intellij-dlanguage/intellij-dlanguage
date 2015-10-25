const int foo = 7;
static assert(is(typeof(foo) == const(int)));

const
{
    double bar = foo + 6;
}
static assert(is(typeof(bar) == const(double)));

class C
{
    const void foo();
    const
    {
        void bar();
    }
    void baz() const;
}
pragma(msg, typeof(C.foo)); // const void()
pragma(msg, typeof(C.bar)); // const void()
pragma(msg, typeof(C.baz)); // const void()
static assert(is(typeof(C.foo) == typeof(C.bar)) &&
              is(typeof(C.bar) == typeof(C.baz)));