import std.stdio;

struct S
{
    const void foo(this T)(int i)
    {
        writeln(typeid(T));
    }
}

void main()
{
    const(S) s;
    (&s).foo(1);
    S s2;
    s2.foo(2);
    immutable(S) s3;
    s3.foo(3);
}

interface Addable(T)
{
    final auto add(T t)
    {
        return this;
    }
}

class List(T) : Addable!T
{
    List remove(T t)
    {
        return this;
    }
}

void main()
{
    auto list = new List!int;
    list.add(1).remove(1);  // error: no 'remove' method for Addable!int
}

interface Addable(T)
{
    final R add(this R)(T t)
    {
        return cast(R)this;  // cast is necessary, but safe
    }
}

class List(T) : Addable!T
{
    List remove(T t)
    {
        return this;
    }
}

void main()
{
    auto list = new List!int;
    list.add(1).remove(1);  // ok
}
