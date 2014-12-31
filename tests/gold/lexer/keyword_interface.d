interface D
{
    void bar() { }  // error, implementation not allowed
    static void foo() { } // ok
    final void abc() { } // ok
}
