auto a = short(1);  // implicitly convert an integer literal '1' to short
auto b = double(a); // implicitly convert a short variable 'a' to double
auto c = byte(128); // error, 128 cannot be represented in a byte

auto a = ushort();  // same as: ushort.init
auto b = wchar();   // same as: wchar.init
auto c = creal();   // same as: creal.init