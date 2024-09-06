module usage;

enum {
    A,
    <resolved>X,
}

void main() {
    auto a = <ref>X;
}
