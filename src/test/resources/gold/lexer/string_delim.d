import std.stdio;

void main(){
  string a = q"<*>";
  string b = q"(foo(xxx))";   // "foo(xxx)"
  string c = q"[foo{]";       // "foo{"
  string d = q"EOS
This
is a multi-line
heredoc string
EOS";
  string e = q"/foo]/";          // "foo]"
}
