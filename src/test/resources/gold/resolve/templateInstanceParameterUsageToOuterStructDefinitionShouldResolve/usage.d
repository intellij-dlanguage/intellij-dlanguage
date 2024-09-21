module usage;

template Foo(T) {}

struct <resolved>Bar {
    void foobar() {
        Foo!<ref>Bar x;
    }
}