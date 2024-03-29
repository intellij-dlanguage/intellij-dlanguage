class Foo
{
    int a, b;

    override size_t toHash() { return a + b; }

    override bool opEquals(Object o)
    {
        Foo foo = cast(Foo) o;
        return foo && a == foo.a && b == foo.b;
    }
}