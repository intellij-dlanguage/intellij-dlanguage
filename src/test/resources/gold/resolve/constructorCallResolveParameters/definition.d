module definition;

class A {
    this(int i) {}
}

void main() {
    int <resolved>x = 3;
    A a = new A(<ref>x);
}