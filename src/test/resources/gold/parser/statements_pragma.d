void foo()
{
    pragma(bar) baz = 8;

    pragma(bar)
    {
        baz = 8;
    }
}