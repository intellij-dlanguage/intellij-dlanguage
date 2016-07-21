enum X { A, B, C }  // named enum

enum size(X) = X.sizeof; // raised as bug for issue #84

enum A = 3;
enum B
{
    A = A // error, circular reference
}
enum C
{
    A = B,  // A = 4
    B = D,  // B = 4
    C = 3,  // C = 3
    D       // D = 4
}
enum E : C
{
    E1 = C.D,
    E2      // error, C.D is C.max
}

enum X { A=3, B, C }
X x;      // x is initialized to 3

enum X { A=3, B, C }
X.min    // is X.A
X.max    // is X.C
X.sizeof // is same as int.sizeof

enum { A, B, C }  // anonymous enum

enum { A, B = 5+7, C, D = 8+C, E }

enum : long { A = 3, B }

enum : string
{
    A = "hello",
    B = "betty",
    C     // error, cannot add 1 to "betty"
}

enum
{
    A = 1.2f,  // A is 1.2f of type float
    B,         // B is 2.2f of type float
    int C = 3, // C is 3 of type int
    D          // D is 4 of type int
}

enum i = 4;      // i is 4 of type int
enum long l = 3; // l is 3 of type long

enum size = __traits(classInstanceSize, Foo);  // evaluated at compile-time