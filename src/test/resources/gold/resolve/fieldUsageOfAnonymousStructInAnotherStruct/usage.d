module usage;

struct Foo {
    struct {
        int <resolved>bar;
    }
}

void fun(Foo foo) {
    foo.<ref>bar;
}