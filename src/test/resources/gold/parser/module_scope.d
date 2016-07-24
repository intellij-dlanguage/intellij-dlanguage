int x;

int foo(int x)
{
    if (y)
        return x;  // returns foo.x, not global x
    else
        return .x; // returns global x
}