module usage;


template Foo() {
    auto i = hasTrue || <ref>i!(3);
}
