struct S
{
    int[] a;    // array is privately owned by this instance
    this(this)
    {
        a = a.dup;
    }
}

struct T
{
    @disable this(this);  // disabling makes T not copyable
}
struct S
{
    T t;   // uncopyable member makes S also not copyable
}

void main()
{
    S s;
    S t = s; // error, S is not copyable
}