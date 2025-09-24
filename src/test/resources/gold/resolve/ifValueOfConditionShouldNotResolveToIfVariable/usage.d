module usage;

void foo() {
    int <resolved>i;
    if (auto i = <ref>i) {
        writeln(i);
    }
}
