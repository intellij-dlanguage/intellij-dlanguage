struct S
{
    int m;

    int opUnary(string s)() if (s == "-")
    {
        return -m;
    }
}

int foo(S s)
{
    return -s;
}

T opBinary(string op)(T rhs)
{
    static if (op == "+") return data + rhs.data;
    else static if (op == "-") return data - rhs.data;
    else static assert(0, "Operator "~op~" not implemented");
}

T opBinary(string op)(T rhs)
{
    return mixin("data "~op~" rhs.data");
}

bool opEquals(Object a, Object b)
{
    if (a is b) return true;
    if (a is null || b is null) return false;
    if (typeid(a) == typeid(b)) return a.opEquals(b);
    return a.opEquals(b) && b.opEquals(a);
}

struct S
{
    // lhs should be mutable object
    bool opEquals(const S s) {  }        // for r-values (e.g. temporaries)
    bool opEquals(ref const S s) {  }    // for l-values (e.g. variables)

    // both hand side can be const object
    bool opEquals(const S s) const {  }  // for r-values (e.g. temporaries)
}

struct S
{
    // for l-values and r-values,
    // with converting both hand side implicitly to const
    bool opEquals()(auto ref const S s) const {  }
}

struct F
{
    int opCall();
    int opCall(int x, int y, int z);
}

void test()
{
    F f;
    int i;

    i = f();      // same as i = f.opCall();
    i = f(3,4,5); // same as i = f.opCall(3,4,5);
}

struct Multiplier
{
    int factor;
    this(int num) { factor = num; }
    int opCall(int value) { return value * factor; }
}
void test()
{
    Multiplier m = Multiplier(10);  // invoke constructor
    int result = m(5);              // invoke opCall
    assert(result == 50);
}

struct Double
{
    static int opCall(int x) { return x * 2; }
}
void test()
{
    int i = Double(2);
    assert(i == 4);
}

struct S
{
    this(int i) {}
    static S opCall()  // disallowed due to constructor
    {
        return S.init;
    }
}

struct S
{
    // identiy assignment, allowed.
    void opAssign(S rhs);

    // not identity assignment, also allowed.
    void opAssign(int);
}

unittest{
    S s;
    s = S();      // Rewritten to s.opAssign(S());
    s = 1;        // Rewritten to s.opAssign(1);
}
class C
{
    // If X is the same type as C or the type which is
    // implicitly convertible to C, then opAssign would
    // accept identity assignment, which is disallowed.
    // C opAssign(...);
    // C opAssign(X);
    // C opAssign(X, ...);
    // C opAssign(X ...);
    // C opAssign(X, U = defaultValue, etc.);

    // not an identity assignment - allowed
    void opAssign(int);
}
unittest{
C c = new C();
c = new C();  // Rebinding referencee
c = 1;        // Rewritten to c.opAssign(1);
}
struct A
{
    int opIndexAssign(int value, size_t i1, size_t i2);
}

void test()
{
    A a;
    a[i,3] = 7;  // same as a.opIndexAssign(7,i,3);
}

struct A
{
    int opIndexAssign(int v);  // overloads a[] = v
    int opIndexAssign(int v, size_t[2] x);  // overloads a[i .. j] = v
    int[2] opSlice(size_t x, size_t y);     // overloads i .. j
}

void test()
{
    A a;
    int v;

    a[] = v;  // same as a.opIndexAssign(v);
    a[3..4] = v;  // same as a.opIndexAssign(v, a.opSlice(3,4));
}

struct Array2D(E)
{
    E[] impl;
    int stride;
    int width, height;

    this(int width, int height, E[] initialData = [])
    {
        impl = initialData;
        this.stride = this.width = width;
        this.height = height;
        impl.length = width * height;
    }

    // Index a single element, e.g., arr[0, 1]
    ref E opIndex(int i, int j) { return impl[i + stride*j]; }

    // Array slicing, e.g., arr[1..2, 1..2], arr[2, 0..$], arr[0..$, 1].
    Array2D opIndex(int[2] r1, int[2] r2)
    {
        Array2D result;

        auto startOffset = r1[0] + r2[0]*stride;
        auto endOffset = r1[1] + (r2[1] - 1)*stride;
        result.impl = this.impl[startOffset .. endOffset];

        result.stride = this.stride;
        result.width = r1[1] - r1[0];
        result.height = r2[1] - r2[0];

        return result;
    }
    auto opIndex(int[2] r1, int j) { return opIndex(r1, [j, j+1]); }
    auto opIndex(int i, int[2] r2) { return opIndex([i, i+1], r2); }

    // Support for `x..y` notation in slicing operator for the given dimension.
    int[2] opSlice(size_t dim)(int start, int end)
        if (dim >= 0 && dim < 2)
    in { assert(start >= 0 && end <= this.opDollar!dim); }
    do
    {
        return [start, end];
    }

    // Support `$` in slicing notation, e.g., arr[1..$, 0..$-1].
    @property int opDollar(size_t dim : 0)() { return width; }
    @property int opDollar(size_t dim : 1)() { return height; }
}

unittest
{
    auto arr = Array2D!int(4, 3, [
        0, 1, 2,  3,
        4, 5, 6,  7,
        8, 9, 10, 11
    ]);

    // Basic indexing
    assert(arr[0, 0] == 0);
    assert(arr[1, 0] == 1);
    assert(arr[0, 1] == 4);

    // Use of opDollar
    assert(arr[$-1, 0] == 3);
    assert(arr[0, $-1] == 8);   // Note the value of $ differs by dimension
    assert(arr[$-1, $-1] == 11);

    // Slicing
    auto slice1 = arr[1..$, 0..$];
    assert(slice1[0, 0] == 1 && slice1[1, 0] == 2  && slice1[2, 0] == 3 &&
           slice1[0, 1] == 5 && slice1[1, 1] == 6  && slice1[2, 1] == 7 &&
           slice1[0, 2] == 9 && slice1[1, 2] == 10 && slice1[2, 2] == 11);

    auto slice2 = slice1[0..2, 1..$];
    assert(slice2[0, 0] == 5 && slice2[1, 0] == 6 &&
           slice2[0, 1] == 9 && slice2[1, 1] == 10);

    // Thin slices
    auto slice3 = arr[2, 0..$];
    assert(slice3[0, 0] == 2 &&
           slice3[0, 1] == 6 &&
           slice3[0, 2] == 10);

    auto slice4 = arr[0..3, 2];
    assert(slice4[0, 0] == 8 && slice4[1, 0] == 9 && slice4[2, 0] == 10);
}

struct A
{
    int opIndex(size_t i1, size_t i2, size_t i3);
}

void test()
{
    A a;
    int i;
    i = a[5,6,7];  // same as i = a.opIndex(5,6,7);
}

struct S
{
    int[] impl;
    int[] opIndex()
    {
        return impl[];
    }
}
void test()
{
    auto s = S([1,2,3]);
    auto t = s[]; // calls s.opIndex()
    assert(t == [1,2,3]);
}

struct Rectangle
{
    int width, height;
    int[][] impl;
    this(int w, int h)
    {
        width = w;
        height = h;
        impl = new int[w][h];
    }
    int opIndex(size_t i1, size_t i2)
    {
        return impl[i1][i2];
    }
    int opDollar(size_t pos)()
    {
        static if (pos==0)
            return width;
        else
            return height;
    }
}

void test()
{
    auto r = Rectangle(10,20);
    int i = r[$-1, 0];    // same as: r.opIndex(r.opDollar!0, 0),
                          // which is r.opIndex(r.width-1, 0)
    int j = r[0, $-1];    // same as: r.opIndex(0, r.opDollar!1)
                          // which is r.opIndex(0, r.height-1)
}

import std.stdio;

struct S
{
    void opDispatch(string s, T)(T i)
    {
        writefln("S.opDispatch('%s', %s)", s, i);
    }
}

class C
{
    void opDispatch(string s)(int i)
    {
        writefln("C.opDispatch('%s', %s)", s, i);
    }
}

struct D
{
    template opDispatch(string s)
    {
        enum int opDispatch = 8;
    }
}

void main()
{
    S s;
    s.opDispatch!("hello")(7);
    s.foo(7);

    auto c = new C();
    c.foo(8);

    D d;
    writefln("d.foo = %s", d.foo);
    assert(d.foo == 8);
}

