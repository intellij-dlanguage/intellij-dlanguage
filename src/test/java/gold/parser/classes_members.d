class C
{
    int a;
    const void foo()
    {
        a = 3; // error, 'this' is const
    }
    void foo() immutable
    {
        a = 3; // error, 'this' is immutable
    }
}