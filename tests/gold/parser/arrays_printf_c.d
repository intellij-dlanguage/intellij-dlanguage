str ~= "\0";
printf("the string is '%s'\n", cast(char*)str);

import std.string;
printf("the string is '%s'\n", std.string.toStringz(str));

printf("the string is '%s'\n", cast(char*)"string literal");

printf("the string is '%.*s'\n", str.length, str.ptr);

import std.stdio;
writefln("the string is '%s'", str);