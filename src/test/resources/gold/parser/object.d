

pure /*nothrow */ unittest
{
    auto a = [ 1:"one", 2:"two", 3:"three" ];
    auto b = a.dup;
    assert(b == [ 1:"one", 2:"two", 3:"three" ]);

    int[] c;
    foreach (k; a.byKey)
    {
        c ~= k;
    }

    assert(c.length == 3);
    assert(c[0] == 1 || c[1] == 1 || c[2] == 1);
    assert(c[0] == 2 || c[1] == 2 || c[2] == 2);
    assert(c[0] == 3 || c[1] == 3 || c[2] == 3);
}

pure nothrow unittest
{
    // test for bug 5925
    const a = [4:0];
    const b = [4:0];
    assert(a == b);
}

pure nothrow unittest
{
    // test for bug 9052
    static struct Json {
        Json[string] aa;
        void opAssign(Json) {}
        size_t length() const { return aa.length; }
        // This length() instantiates AssociativeArray!(string, const(Json)) to call AA.length(), and
        // inside ref Slot opAssign(Slot p); (which is automatically generated by compiler in Slot),
        // this.value = p.value would actually fail, because both side types of the assignment
        // are const(Json).
    }
}

pure nothrow unittest
{
    // test for bug 8583: ensure Slot and aaA are on the same page wrt value alignment
    string[byte]    aa0 = [0: "zero"];
    string[uint[3]] aa1 = [[1,2,3]: "onetwothree"];
    ushort[uint[3]] aa2 = [[9,8,7]: 987];
    ushort[uint[4]] aa3 = [[1,2,3,4]: 1234];
    string[uint[5]] aa4 = [[1,2,3,4,5]: "onetwothreefourfive"];

}
