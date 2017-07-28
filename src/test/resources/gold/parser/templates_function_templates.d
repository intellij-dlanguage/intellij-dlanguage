unittest{

    T Square(T)(T t)
    {
        return t * t;
    }

    void Foo(T : T*)(T t) {  }

    int x,y;
    Foo!(int*)(x);   // ok, T is not deduced from function argument
    Foo(&y);         // error, T has specialization

    void Foo(T, U=T*)(T t) { U p;  }

    int x;
    Foo(x);    // T is int, U is int*

    void foo(T)(T arg) { pragma(msg, T); }

    int[] marr;
    const(int[]) carr;
    immutable(int[]) iarr;
    foo(marr);  // T == int[]
    foo(carr);  // T == const(int)[]
    foo(iarr);  // T == immutable(int)[]

    int* mptr;
    const(int*) cptr;
    immutable(int*) iptr;
    foo(mptr);  // T == int*
    foo(cptr);  // T == const(int)*
    foo(iptr);  // T == immutable(int)*

    auto Square(T)(T t)
    {
        return t * t;
    }
}
