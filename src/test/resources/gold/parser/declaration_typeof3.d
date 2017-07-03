struct S
{
    @property int foo() { return 1; }
}
unittest
{
typeof(S.foo) n;  // n is declared to be an int
}
