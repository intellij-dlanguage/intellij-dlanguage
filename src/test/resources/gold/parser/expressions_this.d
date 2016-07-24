class A
{
    char get() { return 'A'; }

    char foo() { return typeof(this).get(); }
    char bar() { return this.get(); }
}

class B : A
{
    override char get() { return 'B'; }
}

void main()
{
    B b = new B();

    assert(b.foo() == 'A');
    assert(b.bar() == 'B');
}