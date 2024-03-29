module usage;

enum E {
    A,
    <resolved>X,
}

void main() {
    auto a = E.<ref>X;
}
