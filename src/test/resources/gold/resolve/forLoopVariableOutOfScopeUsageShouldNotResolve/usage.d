module usage;

void foo() {
    for(int i; i < 3; i++) {
        writeln(i);
    }
    writeln(<ref>i);
}
