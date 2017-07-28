int[3] s;
int[3] t;

unittest
{

s[] = t;           // the 3 elements of t[3] are copied into s[3]
s[] = t[];         // the 3 elements of t[3] are copied into s[3]
s[1..2] = t[0..1]; // same as s[1] = t[0]
s[0..2] = t[1..3]; // same as s[0] = t[1], s[1] = t[2]
}
