// ref declares the parameter x to be passed by reference
void func(ref int x)
{
    x++; // so modifications to x will be visible in the caller
}

void main()
{
    auto x = 1;
    func(x);
    assert(x == 2);

    // However, ref is not a type qualifier, so the following is illegal:
    //ref(int) y; // Error: ref is not a type qualifier.
}
