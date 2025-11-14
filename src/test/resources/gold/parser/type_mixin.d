
alias fun = mixin("(){}");

void test1()
{
    foo!(mixin("ST!(int, S.T)"))();
    foo!(mixin(ST!(int, S.T)))();

    int[mixin("string")] a1;
    auto v2 = new int[mixin("x")];

    mixin(q{__traits(getMember, S, "T")}) ftv;
}

alias T0 = mixin(q{const(int)})*;

mixin("void") func22356(int) { }
mixin("int")[2] func22356_2(int) { return [1, 2]; }
mixin("int") x22356;
mixin("int")** xpp22356;
mixin("int") y22356, z22356;

void test_statements_22356()
{
    mixin("void") func22356(int) { }

    static mixin("void") func22356_s(char) { }

    mixin("int")[2] func22356_2(int) { return [1, 2]; }

    mixin("int")** xpp22356;
}

int test1(mixin("int")* p) {}