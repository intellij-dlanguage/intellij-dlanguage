module usage;

struct Foo {
    union {
        int <resolved>bar;
    }
}

void fun(Foo foo) {
    foo.<ref>bar;
}