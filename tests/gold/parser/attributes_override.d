class Foo
{
    int bar();
    int abc(int x);
}

class Foo2 : Foo
{
    override
    {
        int bar(char c); // error, no bar(char) in Foo
        int abc(int x);  // ok
    }
}