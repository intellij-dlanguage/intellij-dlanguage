ubyte  u1 = cast(byte)-1;  // error, -1 cannot be represented in a ubyte
ushort u2 = cast(short)-1; // error, -1 cannot be represented in a ushort
uint   u3 = cast(int)-1;   // ok, -1 can be represented in a uint
ulong  u4 = cast(long)-1; // ok, -1 can be represented in a ulong