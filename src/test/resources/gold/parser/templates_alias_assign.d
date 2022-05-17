
template Gorgon(T)
{
    alias A = long;
    A = T; // assign new value to A
    alias Gorgon = A;
}
pragma(msg, Gorgon!int); // prints int

template Reverse(T...)
{
    alias A = AliasSeq!();
    static foreach (t; T)
        A = AliasSeq!(t, A); // Alias Assign
    alias Reverse = A;
}

enum X = 3;
alias TK = Reverse!(int, const uint, X);
pragma(msg, TK); // prints tuple(3, (const(uint)), (int))