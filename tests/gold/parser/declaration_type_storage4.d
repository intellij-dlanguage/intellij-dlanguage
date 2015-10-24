struct S
{
    // Now it is clear that the 'const' here applies to the return type:
    const(int) func1() { return 1; }

    // And it is clear that the 'const' here applies to the function:
    int func2() const { return 1; }
}