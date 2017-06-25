package net.masterthought.dlanguage.parser;

import com.intellij.lang.PsiBuilder;
import net.masterthought.dlanguage.psi.DLanguageIdentifier;

/**
 * Created by francis on 6/24/2017.
 */
public class ParserUtil {
    public static boolean parseType(PsiBuilder builder, int level, boolean dontLookDotIdents) {
//            AST.Type t;
//            Loc loc;
        DLanguageIdentifier id;
        //printf("parseBasicType()\n");
        switch () {
            case TOKvoid:
                t = AST.Type.tvoid;
            goto LabelX;

            case TOKint8:
                t = AST.Type.tint8;
            goto LabelX;

            case TOKuns8:
                t = AST.Type.tuns8;
            goto LabelX;

            case TOKint16:
                t = AST.Type.tint16;
            goto LabelX;

            case TOKuns16:
                t = AST.Type.tuns16;
            goto LabelX;

            case TOKint32:
                t = AST.Type.tint32;
            goto LabelX;

            case TOKuns32:
                t = AST.Type.tuns32;
            goto LabelX;

            case TOKint64:
                t = AST.Type.tint64;
            goto LabelX;

            case TOKuns64:
                t = AST.Type.tuns64;
            goto LabelX;

            case TOKint128:
                t = AST.Type.tint128;
            goto LabelX;

            case TOKuns128:
                t = AST.Type.tuns128;
            goto LabelX;

            case TOKfloat32:
                t = AST.Type.tfloat32;
            goto LabelX;

            case TOKfloat64:
                t = AST.Type.tfloat64;
            goto LabelX;

            case TOKfloat80:
                t = AST.Type.tfloat80;
            goto LabelX;

            case TOKimaginary32:
                t = AST.Type.timaginary32;
            goto LabelX;

            case TOKimaginary64:
                t = AST.Type.timaginary64;
            goto LabelX;

            case TOKimaginary80:
                t = AST.Type.timaginary80;
            goto LabelX;

            case TOKcomplex32:
                t = AST.Type.tcomplex32;
            goto LabelX;

            case TOKcomplex64:
                t = AST.Type.tcomplex64;
            goto LabelX;

            case TOKcomplex80:
                t = AST.Type.tcomplex80;
            goto LabelX;

            case TOKbool:
                t = AST.Type.tbool;
            goto LabelX;

            case TOKchar:
                t = AST.Type.tchar;
            goto LabelX;

            case TOKwchar:
                t = AST.Type.twchar;
            goto LabelX;

            case TOKdchar:
                t = AST.Type.tdchar;
            goto LabelX;
                LabelX:
                nextToken();
                break;

            case TOKthis:
            case TOKsuper:
            case TOKidentifier:
                loc = token.loc;
                id = token.ident;
                nextToken();
                if (token.value == TOKnot) {
                    // ident!(template_arguments)
                    auto tempinst = new AST.TemplateInstance(loc, id, parseTemplateArguments());
                    t = parseBasicTypeStartingAt(new AST.TypeInstance(loc, tempinst), dontLookDotIdents);
                } else {
                    t = parseBasicTypeStartingAt(new AST.TypeIdentifier(loc, id), dontLookDotIdents);
                }
                break;

            case TOKdot:
                // Leading . as in .foo
                t = parseBasicTypeStartingAt(new AST.TypeIdentifier(token.loc, Id.empty), dontLookDotIdents);
                break;

            case TOKtypeof:
                // typeof(expression)
                t = parseBasicTypeStartingAt(parseTypeof(), dontLookDotIdents);
                break;

            case TOKvector:
                t = parseVector();
                break;

            case TOKconst:
                // const(type)
                nextToken();
                check(TOKlparen);
                t = parseType().addSTC(AST.STCconst);
                check(TOKrparen);
                break;

            case TOKimmutable:
                // immutable(type)
                nextToken();
                check(TOKlparen);
                t = parseType().addSTC(AST.STCimmutable);
                check(TOKrparen);
                break;

            case TOKshared:
                // shared(type)
                nextToken();
                check(TOKlparen);
                t = parseType().addSTC(AST.STCshared);
                check(TOKrparen);
                break;

            case TOKwild:
                // wild(type)
                nextToken();
                check(TOKlparen);
                t = parseType().addSTC(AST.STCwild);
                check(TOKrparen);
                break;

            default:
                error("basic type expected, not %s", token.toChars());
                t = AST.Type.terror;
                break;
        }
        return t;
    }
}
    /*
    AST.Type parseType(Identifier* pident = null, AST.TemplateParameters** ptpl = null)
    {
        /* Take care of the storage class prefixes that
         * serve as type attributes:
         *               const type
         *           immutable type
         *              shared type
         *               inout type
         *         inout const type
         *        shared const type
         *        shared inout type
         *  shared inout const type
         */
    /*
    StorageClass stc = 0;
        while (1)
    {
        switch (token.value)
        {
            case TOKconst:
                if (peekNext() == TOKlparen)
                    break; // const as type constructor
                stc |= AST.STCconst; // const as storage class
                nextToken();
                continue;

            case TOKimmutable:
                if (peekNext() == TOKlparen)
                    break;
                stc |= AST.STCimmutable;
                nextToken();
                continue;

            case TOKshared:
                if (peekNext() == TOKlparen)
                    break;
                stc |= AST.STCshared;
                nextToken();
                continue;

            case TOKwild:
                if (peekNext() == TOKlparen)
                    break;
                stc |= AST.STCwild;
                nextToken();
                continue;

            default:
                break;
        }
        break;
    }

        const typeLoc = token.loc;

    AST.Type t;
    t = parseBasicType();

    int alt = 0;
    t = parseDeclarator(t, &alt, pident, ptpl);
    checkCstyleTypeSyntax(typeLoc, t, alt, pident ? *pident : null);

    t = t.addSTC(stc);
        return t;
}
     */
}
