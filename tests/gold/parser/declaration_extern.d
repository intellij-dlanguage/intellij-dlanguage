extern(C) int foo;        // variable allocated and initialized in this module with C linkage
extern extern(C) int bar; // variable allocated outside this module with C linkage
                          // (e.g. in a statically linked C library or another module)
