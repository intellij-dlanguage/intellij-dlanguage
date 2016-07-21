with (expression)
{
    ident;
}


struct Foo
{
    alias Y = int;
}

Y y;        // error, Y undefined
with (Foo)
{
    Y y;    // same as Foo.Y y;
}

struct S
{
    float x;
}

void main()
{
    int x;
    S s;
    with (s)
    {
        x++;  // error, shadows the int x declaration
    }
}