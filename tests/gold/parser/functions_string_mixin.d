int foo(char[] s)
{
    return mixin(s);
}

const int x = foo("1");