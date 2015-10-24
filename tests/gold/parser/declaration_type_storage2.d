// Functions can also be declared as 'ref', meaning their return value is
// passed by reference:
ref int func2()
{
    static int y = 0;
    return y;
}

void main()
{
    func2() = 2; // The return value of func2() can be modified.
    assert(func2() == 2);

    // However, the reference returned by func2() does not propagate to
    // variables, because the 'ref' only applies to the return value itself,
    // not to any subsequent variable created from it:
    auto x = func2();
    static assert(typeof(x) == int); // N.B.: *not* ref(int);
                                     // there is no such type as ref(int).
    x++;
    assert(x == 3);
    assert(func2() == 2); // x is not a reference to what func2() returned; it
                          // does not inherit the ref storage class from func2().
}