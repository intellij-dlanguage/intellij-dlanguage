module usage;

void foo() {
    version(A) {
        uint <resolved>count = 3;
    }

    int i = <ref>count;
}