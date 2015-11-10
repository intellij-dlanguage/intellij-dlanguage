template Foo(T)         { ... }  // #1
template Foo(int n)     { ... }  // #2
template Foo(alias sym) { ... }  // #3

struct S {}
int var;

alias foo1  = Foo!(S);      // instantiates #1
alias foo2  = Foo!(1);      // instantiates #2
alias foo3a = Foo!([1,2]);  // instantiates #3
alias foo3b = Foo!(var);    // instantiates #3

template Bar(alias A) { ... }                 // #4
template Bar(T : U!V, alias U, V...) { ... }  // #5

class C(T) {}
alias bar = Bar!(C!int);    // instantiates #5