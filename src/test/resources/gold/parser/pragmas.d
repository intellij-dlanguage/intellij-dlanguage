pragma(msg);        // just by itself

pragma(msg) int x = 0; // influence one declaration

pragma(msg): // influence subsequent declarations
    float y = 0;
    double a = 0;

pragma(msg)   // influence block of declarations
{
    long b = 0;
    ulong c = 0;
}


//todo libdparse can't parse this correctly, so temporarily excluded from the test cases.
//unittest{
//    pragma(msg) c = 9; // influence one statement
//
//    pragma(msg)   // influence block of statements
//    {
//        b = -6;
//        y = 9.7;
//    }
//}
