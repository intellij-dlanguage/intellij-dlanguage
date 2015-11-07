class Foo
{
    static int a = b + 1;
    static int b = a * 2;
}

class Foo
{
    static int a;         // default initialized to 0
    static int b = 1;
    static int c = b + a; // error, not a constant initializer

    static this()    // static constructor
    {
        a = b + 1;          // a is set to 2
        b = a * 2;          // b is set to 4
    }
}

class Foo
{
    static this() {  } // a static constructor
    static private this() {  } // not a static constructor
    static
    {
        this() {  }      // not a static constructor
    }
    static:
        this() {  }      // not a static constructor
}