pragma(inline):
int foo(int x) // foo() is never inlined
{
    pragma(inline, true);
    ++x;
    pragma(inline, false); // supercedes the others
    return x + 3;
}