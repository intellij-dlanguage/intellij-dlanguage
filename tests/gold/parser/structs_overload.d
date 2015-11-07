struct S { ... }  // S has postblit or destructor
S s;      // default construction of s
S t = s;  // t is copy-constructed from s
t = s;    // t is assigned from s

t.opAssign(s);

ref S opAssign(S s)
{
    S tmp = this;   // bitcopy this into tmp
    this = s;       // bitcopy s into this
    tmp.__dtor();   // call destructor on tmp
    return this;
}

struct S
{
    int[] buf;
    int a;

    ref S opAssign(ref const S s)
    {
        a = s.a;
        return this;
    }

    this(this)
    {
        buf = buf.dup;
    }
}