class Foo { int x; long y; }
void test(Foo foo)
{
    foo.tupleof[0] = 1; // set foo.x to 1
    foo.tupleof[1] = 2; // set foo.y to 2
    foreach (x; foo.tupleof)
        write(x);       // prints 12
}