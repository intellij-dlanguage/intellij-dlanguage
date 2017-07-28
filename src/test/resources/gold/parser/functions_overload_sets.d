//module A;
//void foo() { }
//void foo(long i) { }

module B;
class C { }
void foo(C) { }
void foo(int i) { }

import A;
import B;

void bar(C c)
{
    foo();    // calls A.foo()
    foo(1L);  // calls A.foo(long)
    foo(c);   // calls B.foo(C)
    foo(1,2); // error, does not match any foo
    foo(1);   // error, matches A.foo(long) and B.foo(int)
    A.foo(1); // calls A.foo(long)
}

import A;
import B;

alias foo = A.foo;
alias foo = B.foo;

void bar(C c)
{
    foo();    // calls A.foo()
    foo(1L);  // calls A.foo(long)
    foo(c);   // calls B.foo(C)
    foo(1,2); // error, does not match any foo
    foo(1);   // calls B.foo(int)
    A.foo(1); // calls A.foo(long)
}
