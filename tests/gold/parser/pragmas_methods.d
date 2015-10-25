pragma(lib, "foo.lib");

pragma(mangle, "body")
extern(C) void body_func();

pragma(msg, "compiling...", 1, 1.0);

void foo() {  }
pragma(startaddress, foo);

pragma(DigitalMars_funky_extension) { }

version (DigitalMars)
{
    pragma(DigitalMars_funky_extension)
    {  }
}