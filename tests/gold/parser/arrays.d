int* p;
int[3] s;
int[] a;

int* q;
int[3] t;
int[] b;

p = q;     // p points to the same thing q does.
p = s.ptr; // p points to the first element of the array s.
p = a.ptr; // p points to the first element of the array a.

a = s;     // a is initialized to point to the s array
a = b;     // a points to the same array as b does