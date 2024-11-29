module usage;

struct FooBar(T) {

    static struct Bar {
        int <resolved>x;
    }
}


struct Test(T) {

    struct InternalTest {
        // Due to a parser bug FooBar!T.Bar was considered as a whole and so resolution was
        // looping until a stack overflow
        alias Bar = FooBar!T.Bar;
        Bar* bar;
    }

    unittest {
        InternalTest.Bar* a;
        a.<ref>x = 3;
    }
}

