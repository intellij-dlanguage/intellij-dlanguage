template Foo2(T) { alias t = T; }
alias t1 = Foo2!(int);
alias t2 = Foo2!(int).t;
alias t3 = t1.t;
alias t4 = t2;

t1.t v1;  // v1 is type int
t2 v2;    // v2 is type int
t3 v3;    // v3 is type int
t4 v4;    // v4 is type int