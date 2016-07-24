struct S
{
    int x, y;

    this()  // error, cannot implement default ctor for structs
    {
    }

    this(int a, int b)
    {
        x = a;
        y = b;
    }
}

void main()
{
    S a = S(4, 5);
    auto b = S();  // same as auto b = S.init;
}

struct S1
{
    int[] a;
    this(int n) { a = new int[](n); }
}
struct S2
{
    int[] a;
    this(int n) immutable { a = new int[](n); }
}
void main()
{
    // Mutable constructor creates mutable object.
    S1 m1 = S1(1);

    // Constructed mutable object is implicitly convertible to const.
    const S1 c1 = S1(1);

    // Constructed mutable object is not implicitly convertible to immutable.
    // immutable i1 = S1(1);

    // Mutable constructor cannot construct immutable object.
    // auto x1 = immutable S1(1);

    // Immutable constructor cannot construct mutable object.
    // auto x2 = S2(1);

    // Constructed immutable object is not implicitly convertible to mutable.
    // S2 m2 = immutable S2(1);

    // Constructed immutable object is implicitly convertible to const.
    const S2 c2 = immutable S2(1);

    // Immutable constructor creates immutable object.
    immutable i2 = immutable S2(1);
}

struct S
{
    int x;

    // Disables default construction, function body can be empty.
    @disable this();

    this(int v) { x = v; }
}
void main()
{
    //S s;        // default construction is disabled
    //S s = S();  // also disabled
    S s = S(1);   // construction with calling constructor
}