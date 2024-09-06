module usage;

void foo() {
    foreach(<resolved>myVar; [1, 2, 3]) {
        writeln(<ref>myVar);
    }
}
