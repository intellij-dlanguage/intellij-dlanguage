import std.stdio;

int foo()
{
    write("foo");
    return 10;
}

void main()
{
    foreach (i; 0 .. foo())
    {
        write(i);
    }
}