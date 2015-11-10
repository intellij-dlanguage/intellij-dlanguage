auto foo(int x) { return x + 3; }          // inferred to be int
auto foo(int x) { return x; return 2.5; }  // inferred to be double