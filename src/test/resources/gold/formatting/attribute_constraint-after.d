struct SomeStructName
{
    static struct InnerStruct
    {
        version (linux)
        {
            static if (condition)
            {
                void longFunctionName(AAAAAAAA)(AAAAAAAA a) @property if (someThingsAreTrue!AAAAAAAA && long_condition && is(elaborate == expression))
                {
                }
            }
        }
    }
}
