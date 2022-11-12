package io.github.intellij.dlanguage.features.formatter.impl

import com.intellij.formatting.SpacingBuilder
import com.intellij.psi.codeStyle.CodeStyleSettings
import io.github.intellij.dlanguage.DLanguage
import io.github.intellij.dlanguage.psi.DlangTypes.*

fun createSpacingBuilder(settings: CodeStyleSettings): SpacingBuilder {
        return SpacingBuilder(settings, DLanguage)
            //.aroundInside(OP_COLON, IMPORT_DECLARATION).spaces(1) // import std.stdio : stderr, writeln;
            //.afterInside(COMMA, IMPORT_DECLARATION).spaces(1) // import std.stdio : stderr, writeln;
            .between(KW_CASE, ARGUMENT_LIST).spaces(1)
            .between(ARGUMENT_LIST, CASE_STATEMENT).spacing(0, 0, 0, false, 0)
            .afterInside(OP_COLON, CASE_STATEMENT).lineBreakInCode()
            .before(OP_COMMA).spaceIf(false)
            .after(OP_COMMA).spaceIf(true)
            .before(OP_SCOLON).spaceIf(false)
            .after(OP_SCOLON).spaceIf(true)
            .around(OP_DOT).none()
            .before(ARGUMENT_LIST).none()
            .afterInside(OP_PAR_LEFT, ARGUMENT_LIST).none()
            .beforeInside(OP_PAR_RIGHT, ARGUMENT_LIST).none()
            .afterInside(OP_PAR_LEFT, PARAMETERS).none()
            .beforeInside(OP_PAR_RIGHT, PARAMETERS).none()
            .after(INTERFACE_DECLARATION).spaces(1)
            .after(KW_RETURN).spaces(1)
            .after(KW_CONTINUE).spaces(1)
            .after(KW_BREAK).spaces(1)
            .after(KW_FOREACH).spaces(1)
            .after(KW_FOR).spaces(1)
            .after(KW_IF).spaces(1)
            .after(KW_ELSE).spaces(1)
            .after(KW_SWITCH).spaces(1)
            .after(LINE_COMMENT).lineBreakInCode()
    }
