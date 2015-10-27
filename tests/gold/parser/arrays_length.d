int[4] foo;
int[]  bar = foo;
int*   p = &foo[0];

// These expressions are equivalent:
bar[]
bar[0 .. 4]
bar[0 .. $]
bar[0 .. bar.length]


p[0 .. $]      // '$' is not defined, since p is not an array
bar[0]+$            // '$' is not defined, out of scope of [ ]

bar[$-1] // retrieves last element of the array