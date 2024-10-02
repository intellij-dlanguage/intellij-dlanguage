module usage;

struct A {
    void <resolved>foo() {}
}

void bar(A a) {
    a.<ref>foo();
}
