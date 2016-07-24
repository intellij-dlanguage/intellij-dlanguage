struct S
{
    // Is const here a type qualifier or a storage class?
    // Is the return value const(int), or is this a const function that returns
    // (mutable) int?
    const int func() { return 1; }
}