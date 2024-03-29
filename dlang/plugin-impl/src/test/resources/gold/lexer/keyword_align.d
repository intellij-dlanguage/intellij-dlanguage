align (2) struct S
{
  align (1):
    byte a;   // placed at offset 0
    int b;    // placed at offset 1
    long c;   // placed at offset 5
}

auto sz = S.sizeof;  // 14







