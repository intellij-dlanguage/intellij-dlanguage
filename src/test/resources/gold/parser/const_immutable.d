immutable int z;
void test()
{
    z = 3; // error, z is immutable
}

static this()
{
    z = 3; // ok, can set immutable that doesn't
    // have static initializer
}
unittest
{
immutable int x = 3;  // x is set to 3
x = 4;        // error, x is immutable
char[x] s;    // s is an array of 3 char's

immutable y = 4; // y is of type int
y = 5;           // error, y is immutable

int foo(int f) { return f * 3; }
int i = 5;
immutable x = 3 * 4;      // ok, 12
immutable y = i + 1;      // error, cannot evaluate at compile time
immutable z = foo(2) + 1; // ok, foo(2) can be evaluated at compile time, 7

int foo(int f)
{
    immutable x = f + 1;  // evaluated at run time
    x = 3;                // error, x is immutable
}

immutable char[] s = "foo";
s[0] = 'a';  // error, s refers to immutable data
s = "bar";   // error, s is immutable

immutable(char)[] s = "hello";

s[0] = 'b';  // error, s[] is immutable
s = null;    // ok, s itself is not immutable

immutable(char*)** p = 1;
p = 1;        // ok, p is not immutable
*p = 1;       // ok, *p is not immutable
**p = 1;      // error, **p is immutable
***p = 1;     // error, ***p is immutable

immutable int x = 3;   // x is typed as immutable(int)
immutable(int) y = 3;  // y is immutable

auto s = "hello";   // s is immutable(char)[5]
char[] p = "world"; // error, cannot implicitly convert immutable
                    // to mutable

char[] s = 1;
immutable(char)[] p = cast(immutable)s;     // undefined behavior
immutable(char)[] p = cast(immutable)s.dup; // ok, unique reference

void f(const int* a);
void main()
{
    int x = 1;
    f(&x);
    assert(x == 1); // guaranteed to hold
}

struct S
{
    int x;

    void foo() immutable
    {
        x = 4;      // error, x is immutable
        this.x = 4; // error, x is immutable
    }
}


struct S
{
    immutable int[] bar()  // bar is still immutable, return type is not!
    {
    }
}

struct S
{
    immutable(int[]) bar()  // bar is now mutable, return type is immutable.
    {
    }
}

struct S
{
    immutable(int[]) bar() immutable
    {
    }
}


void main()
{
    immutable int** p = new int*(null); // ok, unique

    int x;
    immutable int** q = new int*(&x); // error, there may be other references to x

    immutable int y;
    immutable int** r = new immutable(int)*(&y); // ok, y is immutable
}
}
