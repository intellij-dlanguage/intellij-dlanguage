// Only one copy of func needs to be written
void func(T)(T x)
{
    writeln(x);
}

void main()
{
    func!(int)(1); // pass an int
    func(1);    // pass an int, inferring T = int
    func("x");  // pass a string
    func(1.0);  // pass a float

    struct S {}
    S s;
    func(s);    // pass a struct
}

static int y = 0;

int countTen(int x)
{
    if (x > 10)
        ++y;
    return x;
}

static assert(countTen(6) == 6); // OK
static assert(countTen(12) == 12);  // invalid, modifies y.


template eval( A... )
{
    const typeof(A[0]) eval = A[0];
}

int square(int i)
{
    return i * i;
}

void foo()
{
    static j = square(3);     // compile time
    writeln(j);
    writeln(square(4));      // run time
    writeln(eval!(square(5))); // compile time
}