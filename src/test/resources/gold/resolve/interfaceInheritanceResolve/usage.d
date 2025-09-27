
interface I1 {
    void <resolved>foo();
}

interface I2 {}

interface I3 : I1, I2 {}

void foo() {
    I3 a;
    a.<ref>foo;
}