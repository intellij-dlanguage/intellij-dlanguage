module contains_public_import;

private:
int i;

class A {
    public:

    void foo() {}
    import contains_declaration;
}
