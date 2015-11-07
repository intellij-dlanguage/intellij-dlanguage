class Foo
{
    static ~this() // static destructor
    {
    }
}

class Foo
{
    static ~this() {  }  // a static destructor
    static private ~this() {  } // not a static destructor
    static
    {
        ~this() {  }  // not a static destructor
    }
    static:
        ~this() {  }  // not a static destructor
}