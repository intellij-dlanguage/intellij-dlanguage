string thing = q{int valid_token; void foo(){return;}};

enum pretty = q{
       import std.stdio;
       void main() {
           writeln("Hello");
       }
    }.outdent();

    enum ugly = q{
import std.stdio;
void main() {
    writeln("Hello");
}
