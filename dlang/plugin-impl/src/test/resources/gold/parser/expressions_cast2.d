
unittest
{
shared int x;
assert(is(typeof(cast(const)x) == const int));

shared int x;
assert(is(typeof(cast()x) == int));


void foo(lazy void exp) {}
void main()
{
    foo(10);            // NG - has no effect in expression '10'
    foo(cast(void)10);  // OK
}
}
