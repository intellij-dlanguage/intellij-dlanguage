module usage;

struct A {
    void <resolved>foo() {}
}

void bar() {
    A var = A();
    var.<ref>foo();
}
