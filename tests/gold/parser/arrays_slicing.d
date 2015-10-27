int[10] a;   // declare array of 10 ints
int[] b;

b = a[1..3]; // a[1..3] is a 2 element array consisting of
             // a[1] and a[2]
foo(b[1]);   // equivalent to foo(0)
a[2] = 3;
foo(b[1]);   // equivalent to foo(3)


int[10] a;
int[] b;

b = a;
b = a[];
b = a[0 .. a.length];

int* p;
int[] b = p[0..8];