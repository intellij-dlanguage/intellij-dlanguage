pragma(ident);        // just by itself

pragma(ident) int x = 0; // influence one declaration

pragma(ident): // influence subsequent declarations
    float y = 0;
    double a = 0;

pragma(ident)   // influence block of declarations
{
    long b = 0;
    ulong c = 0;
}

unittest{
    pragma(ident) c = 9; // influence one statement

    pragma(ident)   // influence block of statements
    {
        b = -6;
        y = 9.7;
    }
}
