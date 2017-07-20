auto fun = function() {  };
auto fun = () {  };
auto fun = {};

auto fun = { int i; };

auto fun = { int i; int i; int i; int i; };

unittest
{
	callFunc({ int i = 10; return i; });
	callFunc({int i = 10; foo(alpha, bravo, charlie, delta, echo, foxtrot, golf, echo); doStuff(withThings, andOtherStuff); return i; });
	callFunc({int i = 10; foo(alpha_longVarName, bravo_longVarName, charlie_longVarName, delta_longVarName, echo_longVarName, foxtrot_longVarName, golf_longVarName, echo_longVarName); doStuff(withThings, andOtherStuff); return i; }, more_stuff);
}
