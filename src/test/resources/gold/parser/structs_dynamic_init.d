unite
struct S { int a; }
S t;      // default initialized
t.a = 3;
S s = t;  // s.a is set to 3

struct S
{
    int a;

    static S opCall(int v)
    {
        S s;
        s.a = v;
        return s;
    }

    static S opCall(S v)
    {
        S s;
        s.a = v.a + 1;
        return s;
    }
}

S s = 3; // sets s.a to 3
S t = s; // sets t.a to 3, S.opCall(s) is not called
