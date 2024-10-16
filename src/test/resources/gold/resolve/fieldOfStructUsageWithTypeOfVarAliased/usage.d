module usage;

struct Foo {
    int <resolved>i;
}

alias Bar = Foo;

void fun() {
    Bar b;
    b.<ref>i;
}
