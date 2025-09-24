module usage;

void foo() {
    auto <resolved>myVar = [];
    foreach(myVar; <ref>myVar) {
        writeln(myVar);
    }
}
