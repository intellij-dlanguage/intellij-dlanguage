class A { }

class B : A
{
    typeof(this) x;  // x is declared to be a B
    typeof(super) y; // y is declared to be an A
}

struct C
{
    static typeof(this) z;  // z is declared to be a C

    typeof(super) q; // error, no super struct for C
}

typeof(this) r;   // error, no enclosing struct or class