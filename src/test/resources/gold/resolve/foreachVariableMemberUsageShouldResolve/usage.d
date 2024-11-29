module usage;

struct Foo {
    int <resolved>x;
}

void bar() {
    foreach (Foo foo; []) {
        foo.<ref>x = 3;
    }
}
