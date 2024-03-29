template TFoo(T) { }
alias Foo1 = TFoo!(int);     // (1) T is deduced to be int
alias Foo2 = TFoo!(char*);   // (1) T is deduced to be char*

template TBar(T : T*) { }
alias Foo3 = TBar!(char*);   // (2) T is deduced to be char

template TAbc(D, U : D[]) { }
alias Bar1 = TAbc!(int, int[]);  // (2) D is deduced to be int, U is int[]
alias Bar2 = TAbc!(char, int[]); // (4) error, D is both char and int

template TDef(D : E*, E) { }
alias Bar3 = TDef!(int*, int); // (1) E is int
                               // (3) D is int*

class A { }
class B : A { }

template TFoo(T : A) { }
alias Foo4 = TFoo!(B);     // (3) T is B

template TBar(T : U*, U : A) { }
alias Foo5 = TBar!(B*, B); // (2) T is B*
                           // (3) U is B