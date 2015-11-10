auto ref foo(int x)     { return x; }  // value return
auto ref foo()          { return 3; }  // value return
auto ref foo(ref int x) { return x; }  // ref return
auto ref foo(out int x) { return x; }  // ref return
auto ref foo() { static int x; return x; }  // ref return

auto ref foo(ref int x) { return 3; return x; }  // ok, value return
auto ref foo(ref int x) { return x; return 3; }  // ok, value return
auto ref foo(ref int x, ref double y)
{
    return x; return y;
    // The return type is deduced to double, but cast(double)x is not an lvalue,
    // then become a value return.
}

auto ref int foo(ref int x) { return x; }  // ok, ref return
auto ref int foo(double x) { return x; }   // error, cannot convert double to int