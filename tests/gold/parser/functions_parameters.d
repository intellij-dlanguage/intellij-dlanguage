int foo(in int x, out int y, ref int z, int q);

void foo(out int x)
{
    // x is set to int.init,
    // which is 0, at start of foo()
}

int a = 3;
foo(a);
// a is now 0

void abc(out int x)
{
    x = 2;
}

int y = 3;
abc(y);
// y is now 2

void def(ref int x)
{
    x += 1;
}

int z = 3;
def(z);
// z is now 4

void dotimes(int n, lazy void exp)
{
    while (n--)
        exp();
}

void test()
{
    int x;
    dotimes(3, writeln(x++));
}