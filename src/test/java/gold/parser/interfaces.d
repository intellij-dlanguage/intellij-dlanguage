interface D
{
    void foo();
}

class A : D, D  // error, duplicate interface
{
}

interface D
{
    void bar() { }  // error, implementation not allowed
    static void foo() { } // ok
    final void abc() { } // ok
}


interface D
{
    void bar();
    static void foo() { }
    final void abc() { }
}

class C : D
{
    void bar() { } // ok
    void foo() { } // error, cannot override static D.foo()
    void abc() { } // error, cannot override final D.abc()
}

interface D
{
    void foo();
}

class A : D
{
    void foo() { }  // ok, provides implementation
}

class B : D
{
    int foo() { }   // error, no void foo() implementation
}

interface D
{
    int foo();
}

class A : D
{
    int foo() { return 1; }
}

class B : A
{
    int foo() { return 2; }
}



B b = new B();
b.foo();            // returns 2
D d = cast(D) b;    // ok since B inherits A's D implementation
d.foo();            // returns 2;

interface D
{
    int foo();
}

class A : D
{
    int foo() { return 1; }
}

class B : A, D
{
    int foo() { return 2; }
}



B b = new B();
b.foo();            // returns 2
D d = cast(D) b;
d.foo();            // returns 2
A a = cast(A) b;
D d2 = cast(D) a;
d2.foo();           // returns 2, even though it is A's D, not B's D


interface D
{
    int foo();
}

class A : D
{
    int foo() { return 1; }
}

class B : A, D
{
}       // error, no foo() for interface D

