
void foo() {
    scope doit = {
        trigger(evt, (cast(long[])m_buf)[0] > 1);
    }; // declare function without parameter
    () @trusted { (cast(void delegate() @nogc)doit)(); } (); // call the function
}