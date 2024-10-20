module usage;

int <resolved>bar = 3;

void foo() {
    auto bar = <ref>bar;
}