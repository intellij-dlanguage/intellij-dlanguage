int* p;

p = ("hello" in aa);
if (p !is null)
{
    *p = 4;  // update value associated with key
    assert(aa["hello"] == 4);
}