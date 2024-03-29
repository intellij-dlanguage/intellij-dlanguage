void foo(int delegate()[] dgs ...);

int delegate() dg;
unittest{

    foo(1, 3+x, dg, cast(int delegate())null);

    foo( { return 1; }, { return 3+x; }, dg, null );
}
