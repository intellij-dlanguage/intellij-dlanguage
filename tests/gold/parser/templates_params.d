template foo(string s)
{
    string bar() { return s ~ " betty"; }
}

void main()
{
    writefln("%s", foo!("hello").bar()); // prints: hello betty
}

template foo(U : int, int T : 10)
{
    U x = T;
}

void main()
{
    assert(foo!(int, 10).x == 10);
}