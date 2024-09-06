module usage;

void foo() {
    foreach(myVar; [1, 2, 3]) {
        writeln(myVar);
    }
    writeln(<ref>myVar);
}
