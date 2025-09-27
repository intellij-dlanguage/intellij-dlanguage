

enum a = <error descr="Invalid string delimiter">q"_test_"</error>;
enum a = <error descr="Delimiter cannot be whitespace">q" test "</error>;
enum a = <error descr="Delimiter cannot be whitespace">q"   test    "</error>;
enum a = <error descr="Invalid string delimiter">q"test"</error>;
enum a = <error descr="Invalid string delimiter">q"ùtestù"</error>;
enum a = q"/test/<error descr="Illegal text found after closing delimiter, expected \" character instead">foo/</error>";
enum a = q"/test/<error descr="Illegal text found after closing delimiter, expected \" character instead">foo/</error>"d;

auto a = q"TEST
blah
 TEST this one is valid as thereis a space before
TEST<error descr="Illegal text found after closing delimiter, expected \" character instead">
invalid text part of error
TEST</error>";


enum a = q"(foo(xxxx))"; // this is a legal string
