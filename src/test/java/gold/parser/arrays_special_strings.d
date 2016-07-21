char[] str1 = "abc";                // error, "abc" is not mutable
char[] str2 = "abc".dup;            // ok, make mutable copy
immutable(char)[] str3 = "abc";     // ok
immutable(char)[] str4 = str1;      // error, str4 is not mutable
immutable(char)[] str5 = str1.idup; // ok, make immutable copy

char[] str1 = "abc";     // error, "abc" is not mutable
char[] str2 = "abc".dup; // ok, make mutable copy
string str3 = "abc";     // ok
string str4 = str1;      // error, str4 is not mutable
string str5 = str1.idup; // ok, make immutable copy

str1 = str2;
if (str1 < str3) {  }
func(str3 ~ str4);
str4 ~= str1;

char* p = &str[3]; // pointer to 4th element
char* p = str;     // pointer to 1st element

str ~= "\0";

char c;
wchar w;
dchar d;

c = 'b';     // c is assigned the character 'b'
w = 'b';     // w is assigned the wchar character 'b'
//w = 'bc';  // error - only one wchar character at a time
w = "b"[0];  // w is assigned the wchar character 'b'
w = "\r"[0]; // w is assigned the carriage return wchar character
d = 'd';     // d is assigned the character 'd'
