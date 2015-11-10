void foo() {}   // no arguments
void fun(int x = 10) { }
void bar(int[] arr) {}  // for UFCS

void main()
{
    foo();      // OK
    foo;        // also OK
fun;	// OK

    int[] arr;
    arr.bar();  // OK
    arr.bar;    // also OK
}

void main()
{
    int function() fp;

    assert(fp == 6);	// Error, incompatible types int function() and int
    assert(*fp == 6);	// Error, incompatible types int() and int

    int delegate() dg;
    assert(dg == 6);	// Error, incompatible types int delegate() and int
}

struct S {
    int function() callfp() { return &numfp; }
    int delegate() calldg() { return &numdg; }
    int numdg() { return 6; }
}

int numfp() { return 6; }

void main()
{
    S s;
    int function() fp;

    fp = s.callfp;
    assert(fp() == 6);

    fp = s.callfp();
    assert(fp() == 6);

    int x = s.callfp()();
    assert(x == 6);

    int delegate() dg;

    dg = s.calldg;
    assert(dg() == 6);

    dg = s.calldg();
    assert(dg() == 6);

    int y = s.calldg()();
    assert(y == 6);
}