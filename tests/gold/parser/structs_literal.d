struct ABC x;

struct S;
union U;

struct S;
S.sizeof; // error, size is not known
S s;      // error, cannot initialize unknown contents
S* p;     // ok, knowledge of members is not necessary