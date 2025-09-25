module usage;

void foo() {
    if (auto i = 3) {
        writeln(i);
    }
    writeln(<ref>i);
}
