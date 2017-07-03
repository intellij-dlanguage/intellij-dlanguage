int test()
{
    return sum(1, 2, 3) + sum(); // returns 6+0
}

int func()
{
    int[3] ii = [4, 5, 6];
    return sum(ii);             // returns 15
}

int sum(int[] ar ...)
{
    int s;
    foreach (int x; ar)
        s += x;
    return s;
}

int test()
{
    return sum(2, 3);   // error, need 3 values for array
    return sum(1, 2, 3); // returns 6
}

int func()
{
    int[3] ii = [4, 5, 6];
    int[] jj = ii;
    return sum(ii); // returns 15
    return sum(jj); // error, type mismatch
}

int sum(int[3] ar ...)
{
    int s;
    foreach (int x; ar)
        s += x;
    return s;
}

class Foo
{
    int x;
    string s;

    this(int x, string s)
    {
        this.x = x;
        this.s = s;
    }
}

void test(int x, Foo f ...);

unittest
{
Foo g = new Foo(3, "abc");
test(1, g);         // ok, since g is an instance of Foo
test(1, 4, "def");  // ok
test(1, 5);         // error, no matching constructor for Foo

Foo test(Foo f ...)
{
    return f;   // error, f instance contents invalid after return
}

int[] test(int[] a ...)
{
    return a;       // error, array contents invalid after return
    return a[0..1]; // error, array contents invalid after return
    return a.dup;   // ok, since copy is made
}

int test(int i ...)
{
    return i;
}


test(3);    // returns 3
test(3, 4); // error, too many arguments
int[] x;
test(x);    // error, type mismatch

}
