
auto ies = i"Hello, $(name)";
string greeting = i"Hello, $(name)".text; // OK

auto a = i"Here is a type: $(test)";

auto ies2 = i`test $()`;
auto ies3 = iq{test $(test) {blah} blah};
