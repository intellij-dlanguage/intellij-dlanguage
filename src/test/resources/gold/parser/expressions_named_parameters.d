module gold.parser.expressions_named_parameters;

void foo() {
    auto _ = const A(i: 3);
    auto _ = foo(i: 3);
}
