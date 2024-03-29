auto a = array(immutable(S).init.repeat(5));

struct S9
{
    void delegate() shared a;
    void delegate() immutable b;
    void delegate() shared const c;
    shared(void delegate()) d;
    shared(void delegate() shared) e;
    shared(void delegate() immutable) f;
    shared(void delegate() shared const) g;
    immutable(void delegate()) h;
    immutable(void delegate() shared) i;
    immutable(void delegate() immutable) j;
    immutable(void delegate() shared const) k;
    shared(const(void delegate())) l;
    shared(const(void delegate() shared)) m;
    shared(const(void delegate() immutable)) n;
    shared(const(void delegate() shared const)) o;
}
