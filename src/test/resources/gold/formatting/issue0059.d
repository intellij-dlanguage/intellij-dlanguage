extern (C++) FuncDeclaration buildXopEquals(StructDeclaration sd, Scope* sc)
{
    if (!needOpEquals(sd))
        return null; // bitwise comparison would work
    //printf("StructDeclaration::buildXopEquals() %s\n", sd->toChars());
    if (Dsymbol eq = search_function(sd, Id.eq))
    {
        if (FuncDeclaration fd = eq.isFuncDeclaration())
        {
            TypeFunction tfeqptr;
            {
            }
        }
    }
}
