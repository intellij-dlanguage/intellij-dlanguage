unittest{
assert(expression);
}

void func()
in
{
}
out (result)
{
}
body
{
}

long square_root(long x)
in
{
    assert(x >= 0);
}
out (result)
{
    assert((result * result) <= x && (result+1) * (result+1) > x);
}
body
{
    return cast(long)std.math.sqrt(cast(real)x);
}

void func()
out
{
}
body
{
}

class Date
{
    int day;
    int hour;

    this(int d, int h)
    {
        day = d;
        hour = h;
    }

    invariant
    {
        assert(1 <= day && day <= 31);
        assert(0 <= hour && hour < 24);
    }
}

class Foo
{
    public void f() { }
    private void g() { }

    invariant
    {
        f();  // error, cannot call public member function from invariant
        g();  // ok, g() is not public
    }
}

auto mydate = new Date(); //class
auto s = S();             //struct

unittest
{
    assert(mydate);           // check that class Date invariant holds
    assert(&s);               // check that struct S invariant holds
}
