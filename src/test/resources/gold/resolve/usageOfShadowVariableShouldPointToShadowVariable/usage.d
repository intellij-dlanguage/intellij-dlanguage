module usage;

int foo = 3;

int fun() {
    auto <resolved>foo = foo + 1;
    return <ref>foo;
}
