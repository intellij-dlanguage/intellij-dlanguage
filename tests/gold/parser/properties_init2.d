void main()
{
    int a;
    struct S
    {
        void foo() { a = 1; }  // access a variable in enclosing scope
    }
    S s1;           // OK. S() correctly initialize its frame pointer.
    S s2 = S();     // OK. same as s1
    S s3 = S.init;  // Bad. the frame pointer in s3 is null
    s3.foo();       // Access violation
}