struct S
{
  align:
    byte a;   // placed at offset 0
    int b;    // placed at offset 4
    long c;   // placed at offset 8
}

auto sz = S.sizeof;  // 16  );


struct S
{
  align (1):
    byte a;   // placed at offset 0
    int b;    // placed at offset 1
    long c;   // placed at offset 5
}

auto sz = S.sizeof;  // 16


align (2) struct S
{
  align (1):
    byte a;   // placed at offset 0
    int b;    // placed at offset 1
    long c;   // placed at offset 5
}

auto sz = S.sizeof;  // 14


struct S
{
  align (4):
    byte a;   // placed at offset 0
    byte b;   // placed at offset 4
    short c;  // placed at offset 8
}

auto sz = S.sizeof;  // 12