class Foo { int x; this() { x = 1; } }
unittest
{
    Foo foo = new Foo();
    destroy(foo);
    assert(foo.x == int.init);  // object is still accessible

//below syntax is deprecated in dmd
//    delete(void *p)
//    {
//    }
}
