
import std.stdio;
import std.conv;

// Some examples mixins I've not seen work with other tools.

void declaration() {
	mixin("int aVar = 5;");
	writeln(aVar); // if you comment out this line, can you code complete aVar in this method?
}

void sayHi() { // does find references on this method say unused?  Or does it detect the usage in "references" method?
	writeln("Hello");
}

void reference() {
	mixin("sayHi();");
}

mixin("void wholeMethod() { writeln(\"You can do whole methods\"); }");

int five() {
	return 5;
}

void fancier() {
	mixin("writeln(\"You can't just parse the string, you can call methods too: \" ~ to!string(five()));");
}

void main(string[] args) {
	declaration();
	reference();
	fancier();
	wholeMethod(); // does this code complete for you if you delete the line?
}
