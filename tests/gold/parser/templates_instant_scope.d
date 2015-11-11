template TFoo(T) { void bar() { func(); } }

import a;

void func() { }
alias f = TFoo!(int); // error: func not defined in module a

template TFoo(T) { void bar() { func(1); } }
void func(double d) { }

import a;

void func(int i) { }
alias f = TFoo!(int);

f.bar();  // will call a.func(double)

