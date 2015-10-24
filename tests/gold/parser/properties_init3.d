struct S
{
    int a;
    @disable this();
    this(int n) { a = n; }
    invariant { assert(a > 0); }
    void check() {}
}
void main()
{
  //S s1;           // Error: variable s1 initializer required for type S
  //S s2 = S();     // Error: constructor S.this is not callable
                    // because it is annotated with @disable
    S s3 = S.init;  // Bad. s3.a == 0, and it violates the invariant of S.
    s3.check();     // Assertion failure.
}