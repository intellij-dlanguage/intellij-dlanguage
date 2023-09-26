module usage;

enum {
    A,
    <resolved>X,
}

void main() {
    auto a = E.<ref>X;
}
