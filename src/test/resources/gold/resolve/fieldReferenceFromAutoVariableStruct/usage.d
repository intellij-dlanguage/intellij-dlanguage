module usage;

struct A {
    int <resolved>x;
    A* copy() {}
}

void foo(A* input) {
    auto a = input.copy();
    a.<ref>x = 3;
}
