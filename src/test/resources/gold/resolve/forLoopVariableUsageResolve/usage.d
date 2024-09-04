module usage;

void foo() {
    for(int <resolved>i; i < 3; i++) {
        writeln(<ref>i);
    }
}
