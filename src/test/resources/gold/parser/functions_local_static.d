void foo()
{
    static int n;
    if (++n == 100)
        writeln("called 100 times");
}

void main()
{
    { static int x; }
    { static int x; } // error
    { int i; }
    { int i; } // ok
}