template GenStruct(string Name, string M1)
{
    const char[] GenStruct = "struct " ~ Name ~ "{ int " ~ M1 ~ "; }";
}

mixin(GenStruct!("Foo", "bar"));
