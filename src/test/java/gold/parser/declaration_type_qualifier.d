immutable(int)   x; // typeof(x) == immutable(int)
immutable(int)[] y; // typeof(y) == immutable(int)[]
                    // typeof(y[0]) == immutable(int)

// Type constructors create new types that can be aliased:
alias ImmutableInt = immutable(int);
ImmutableInt z;     // typeof(z) == immutable(int)