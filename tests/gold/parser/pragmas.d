pragma(ident);        // just by itself

pragma(ident) declaration; // influence one declaration

pragma(ident): // influence subsequent declarations
    declaration;
    declaration;

pragma(ident)   // influence block of declarations
{
    declaration;
    declaration;
}

pragma(ident) statement; // influence one statement

pragma(ident)   // influence block of statements
{
    statement;
    statement;
}