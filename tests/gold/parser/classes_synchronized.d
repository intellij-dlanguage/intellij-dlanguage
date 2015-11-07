synchronized class Foo
{
    void bar() {  }
}

synchronized class Foo
{
    void bar()
    {
        synchronized (this) {  }
    }
}

class Foo
{
    synchronized void foo() { }  // disallowed!
}

synchronized class Bar
{
    void bar() { }  // bar is synchronized
}

synchronized class Foo
{
    int foo;  // disallowed: public field
}

synchronized class Bar
{
    private int bar;  // ok
}

