extern (C++, N) { void foo(); void bar(); }
extern (C++, M) { void foo(); }
//no longer valid d:
//namespace N { void foo(); }
unittest
{
bar();   // ok
foo();   // error - N.foo() or M.foo() ?
M.foo(); // ok
}
