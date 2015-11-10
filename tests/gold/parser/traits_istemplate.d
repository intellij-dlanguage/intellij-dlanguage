void foo(T)(){}
static assert(__traits(isTemplate,foo));
static assert(!__traits(isTemplate,foo!int()));
static assert(!__traits(isTemplate,"string"));