module usage;

alias <resolved>Foo = int;

struct Bar {
    union {
        struct A {}
    }
    <ref>Foo i;
}