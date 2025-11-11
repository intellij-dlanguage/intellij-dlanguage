
struct Foo {
    struct <resolved>Resolved {}
}

struct A {

    struct Foo {}
    void foo(.Foo.<ref>Resolved a) {}
}
