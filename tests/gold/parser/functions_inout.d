int[] foo(int[] a, int x, int y) { return a[x .. y]; }

const(int)[] foo(const(int)[] a, int x, int y) { return a[x .. y]; }

immutable(int)[] foo(immutable(int)[] a, int x, int y) { return a[x .. y]; }

inout(int)[] foo(inout(int)[] a, int x, int y) { return a[x .. y]; }

int[] ma;
const(int)[] ca;
immutable(int)[] ia;

inout(int)[] foo(inout(int)[] a) { return a; }
void test1()
{
    // inout matches to mutable, so inout(int)[] is
    // rewritten to int[]
    int[] x = foo(ma);

    // inout matches to const, so inout(int)[] is
    // rewritten to const(int)[]
    const(int)[] y = foo(ca);

    // inout matches to immutable, so inout(int)[] is
    // rewritten to immutable(int)[]
    immutable(int)[] z = foo(ia);
}

inout(const(int))[] bar(inout(int)[] a) { return a; }
void test2()
{
    // inout matches to mutable, so inout(const(int))[] is
    // rewritten to const(int)[]
    const(int)[] x = foo(ma);

    // inout matches to const, so inout(const(int))[] is
    // rewritten to const(int)[]
    const(int)[] y = foo(ca);

    // inout matches to immutable, so inout(int)[] is
    // rewritten to immutable(int)[]
    immutable(int)[] z = foo(ia);
}