int* p;
int[3] s;
int[] a;
unittest
{
p.length; // error, length not known for pointer
s.length; // compile time constant 3
a.length; // runtime value

p.dup;    // error, length not known
s.dup;    // creates an array of 3 elements, copies
          // elements s into it
a.dup;    // creates an array of a.length elements, copies
          // elements of a into it
}
