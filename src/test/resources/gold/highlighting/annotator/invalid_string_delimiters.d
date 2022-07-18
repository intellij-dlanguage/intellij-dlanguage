

enum a = <error descr="Invalid string delimiter">q"_test_"</error>;
enum a = <error descr="Delimiter cannot be whitespace">q" test "</error>;
enum a = <error descr="Delimiter cannot be whitespace">q"   test    "</error>;
enum a = <error descr="Invalid string delimiter">q"test"</error>;
enum a = <error descr="Invalid string delimiter">q"ùtestù"</error>;
enum a = q"/test/<error descr="Illegal text found after closing delimiter, expected \" character instead">foo/</error>";
