/**
 * Compiler implementation of the
 * $(LINK2 http://www.dlang.org, D programming language).
 *
 * Copyright:   Copyright (c) 1999-2016 by Digital Mars, All Rights Reserved
 * Authors:     $(LINK2 http://www.digitalmars.com, Walter Bright)
 * License:     $(LINK2 http://www.boost.org/LICENSE_1_0.txt, Boost License 1.0)
 * Source:      $(DMDSRC _expression.d)
 */

module ddmd.expression;


/***********************************************************
 */
extern (C++) final class StringExp : Expression
{

    override Expression semantic(Scope* sc)
    {
        static if (LOGSEMANTIC)
        {
            printf("StringExp::semantic() %s\n", toChars());
        }
        if (type)
            return this;

        OutBuffer buffer;
        size_t newlen = 0;
        const(char)* p;
        size_t u;
        dchar c;

        switch (postfix)
        {
        case 'd':
            for (u = 0; u < len;)
            {
                p = utf_decodeChar(string, len, u, c);
                if (p)
                {
                    error("%s", p);
                    return new ErrorExp();
                }
                else
                {
                    buffer.write4(c);
                    newlen++;
                }
            }
            buffer.write4(0);
            dstring = cast(dchar*)buffer.extractData();
            len = newlen;
            sz = 4;
            type = new TypeDArray(Type.tdchar.immutableOf());
            committed = 1;
            break;

        case 'w':
            for (u = 0; u < len;)
            {
                p = utf_decodeChar(string, len, u, c);
                if (p)
                {
                    error("%s", p);
                    return new ErrorExp();
                }
                else
                {
                    buffer.writeUTF16(c);
                    newlen++;
                    if (c >= 0x10000)
                        newlen++;
                }
            }
            buffer.writeUTF16(0);
            wstring = cast(wchar*)buffer.extractData();
            len = newlen;
            sz = 2;
            type = new TypeDArray(Type.twchar.immutableOf());
            committed = 1;
            break;

        case 'c':
            committed = 1;
            goto default;

        default:
            type = new TypeDArray(Type.tchar.immutableOf());
            break;
        }
        type = type.semantic(loc, sc);
        //type = type->immutableOf();
        //printf("type = %s\n", type->toChars());

        return this;
    }

    /**********************************
     * Return the number of code units the string would be if it were re-encoded
     * as tynto.
     * Params:
     *      tynto = code unit type of the target encoding
     * Returns:
     *      number of code units
     */
    final size_t numberOfCodeUnits(int tynto = 0) const
    {
        int encSize;
        switch (tynto)
        {
            case 0:      return len;
            case Tchar:  encSize = 1; break;
            case Twchar: encSize = 2; break;
            case Tdchar: encSize = 4; break;
            default:
                assert(0);
        }
        if (sz == encSize)
            return len;

        size_t result = 0;
        dchar c;

        switch (sz)
        {
        case 1:
            for (size_t u = 0; u < len;)
            {
                if (const p = utf_decodeChar(string, len, u, c))
                {
                    error("%s", p);
                    return 0;
                }
                result += utf_codeLength(encSize, c);
            }
            break;

        case 2:
            for (size_t u = 0; u < len;)
            {
                if (const p = utf_decodeWchar(wstring, len, u, c))
                {
                    error("%s", p);
                    return 0;
                }
                result += utf_codeLength(encSize, c);
            }
            break;

        case 4:
            foreach (u; 0 .. len)
            {
                result += utf_codeLength(encSize, dstring[u]);
            }
            break;

        default:
            assert(0);
        }
        return result;
    }
}
