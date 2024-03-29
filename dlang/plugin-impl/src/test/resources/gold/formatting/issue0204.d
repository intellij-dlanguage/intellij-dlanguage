version (Foo)
{
    version(D_Version2)
    {
        public import core.memory;
    }
    else:
}

version (Bar)
    int foo ();
else:
    int foo(int);
