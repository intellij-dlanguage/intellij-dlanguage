class Abc
{
    int a;      // default initializer for a is 0
    long b = 7; // default initializer for b is 7
    float f;    // default initializer for f is NAN
}

class Foo
{
    this(int x)  // declare constructor for Foo
    {
    }
    this()
    {
    }
}

class A { this(int y) { } }

class B : A
{
    int j;
    this()
    {

        super(3);  // call base constructor A.this(3)

    }
}

class C
{
    int j;
    this()
    {

    }
    this(int i)
    {
        this();
        j = i;
    }
}

class C
{
    this();   // non-shared mutable constructor
}

// create mutable object
C m = new C();

// create const object using by mutable constructor
const C c2 = new const C();


class C
{
    this();               // non-shared mutable constructor
    this() shared;        // shared mutable constructor
    this() immutable;     // immutable constructor
}

C m = new C();
shared s = new shared C();
immutable i = new immutable C();

class C
{
    this() pure;
    // Based on the definition, this creates a mutable object. But the
    // created object cannot contain any mutable global data.
    // Then compiler can guarantee that the created object is unique.

    this(int[] arr) immutable pure;
    // Based on the definition, this creates an immutable object. But
    // the argument int[] never appears in the created object so it
    // isn't implicitly convertible to immutable. Also, it cannot store
    // any immutable global data.
    // Therefore the compiler can guarantee that the created object is
    // unique.
}

immutable i = new immutable C();           // this() pure is called
shared s = new shared C();                 // this() pure is called
C m = new C([1,2,3]);       // this(int[]) immutable pure is called