extern (C++, N.M) { extern (C++) { extern (C++, R) { void foo(); } } }
unittest
{

N.M.R.foo();
}

namespace N { namespace M { namespace R { void foo(); } } }
