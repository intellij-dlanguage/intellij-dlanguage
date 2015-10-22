class A { this(int y) { } }

class B : A
{
    int j;
    this()
    {
        super(3);  // call base constructor A.this(3)
    }
}
