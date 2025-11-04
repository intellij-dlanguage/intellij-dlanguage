string a = i"";
string b = i"test\"";
string c = i"$b"; // equals "$b"
string d = i"$(test)";
string e = i"test $(test)";
string f = i"test $(test.foo())";
string g = i"test $([1, 2])";
string h = i"$(b) $("$" ~ ')' ~ `"`)";

string i = i""d;

string j = i"\$";
string k = i"$";

string l = i`$(test)"`;
string m = iq{int $(test) {} }d;
