char[] a = new char[20];
char[] b = a[0..10];
char[] c = a[10..20];
char[] d = a;

b.length = 15; // always reallocates because extending in place would
               // overwrite other data in a.
b[11] = 'x';   // a[11] and c[1] are not affected

d.length = 1;
d.length = 20; // also reallocates, because doing this will overwrite a and
               // c

c.length = 12; // may reallocate in place if space allows, because nothing
               // was allocated after c.
c[5] = 'y';    // may affect contents of a, but not b or d because those
               // were reallocated.

a.length = 25; // This always reallocates because if c extended in place,
               // then extending a would overwrite c.  If c didn't
               // reallocate in place, it means there was not enough space,
               // which will still be true for a.
a[15] = 'z';   // does not affect c, because either a or c has reallocated.


int[] array;
while (1)
{
    c = getinput();
    if (!c)
        break;
    ++array.length;
    array[array.length - 1] = c;
}


int[] array;
array.length = 100;        // guess
for (i = 0; ; i++)
{
    c = getinput();
    if (!c)
        break;
    if (i == array.length)
        array.length *= 2;
    array[i] = c;
}
array.length = i;


int[] array;
size_t cap = array.reserve(10); // request
array ~= [1, 2, 3, 4, 5];
assert(cap >= 10); // allocated may be more than request
assert(cap == array.capacity);