const struct S { int a; int b = 2; }

void main()
{
    S s = S(3); // initializes s.a to 3
    S t;        // initializes t.a to 0
    t = s;      // error, t.a and t.b are const, so cannot modify them.
    t.a = 4;    // error, t.a is const
}