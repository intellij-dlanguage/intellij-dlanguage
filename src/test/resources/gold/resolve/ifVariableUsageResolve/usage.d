module usage;

void foo() {
    if(ref <resolved>myVar = 3) {
        writeln(<ref>myVar);
    }
}
