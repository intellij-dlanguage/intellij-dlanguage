int foo;            // Each thread has its own exclusive copy of foo.
__gshared int bar;  // bar is shared by all threads.

class Foo
{
    __gshared int bar;
}

int foo()
{
    __gshared int bar = 0;
    return bar++; // Not thread safe.
}