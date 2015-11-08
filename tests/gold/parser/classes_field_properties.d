class Foo
{
    int x;
}
...
void test(Foo foo)
{
    size_t o;

    o = Foo.x.offsetof; // error, Foo.x needs a 'this' reference
    o = foo.x.offsetof; // ok
}