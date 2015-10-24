struct S
{
    @property int foo() { return 1; }
}
typeof(S.foo) n;  // n is declared to be an int