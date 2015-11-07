immutable long[string] aa;

static this()
{
    import std.exception : assumeUnique;
    import std.conv : to;

    long[string] temp; // mutable buffer
    foreach(i; 0 .. 10)
    {
        temp[to!string(i)] = i;
    }
    temp.rehash; // for faster lookups

    aa = assumeUnique(temp);
}

unittest
{
    assert(aa["1"] == 1);
    assert(aa["5"] == 5);
    assert(aa["9"] == 9);
}