new(uint size)
{

}

class Foo
{
    this(char[] a) {  }

    new(uint size, int x, int y)
    {

    }
}

unittest
{
new(1,2) Foo(a);        // calls new(Foo.sizeof,1,2)
}
