import std.stdio;

void main()
{
    int j;
    mixin("
        int x = 3;
        for (int i = 0; i < 3; i++)
            writeln(x + i, ++j);
        ");    // ok

    const char[] s = "int y;";
    mixin(s);  // ok
    y = 4;     // ok, mixin declared y

    char[] t = "y = 3;";
    mixin(t);  // error, t is not evaluatable at compile time

    //mixin("y =") 4; // error, string must be complete statement//idk why this was included in test cases becuase it isn't valid

    mixin("y =" ~ "4;");  // ok
}
