int a;        // declare a as type int and initialize it to 0
struct S { }  // declare struct s
alias myint = int;

void foo() {
    int i = [1, 2].stringof;
}
