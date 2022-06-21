import std.stdio;

void main(){
  string a = r"hello";
  string b = r"ab\n"; // string is 4 characters: 'a', 'b', '\', 'n'
  string c = r"c:\games\Sudoku.exe";
  string d = `the Great and Powerful.`;
  string e = `c:\games\Empire.exe`;
  string f = `The "lazy" dog`;
  string g = `a"b\n`;  // string is 5 characters, 'a', '"', 'b', '\', 'n'
  wstring h = r""w;
  dstring i = ``d;
}
