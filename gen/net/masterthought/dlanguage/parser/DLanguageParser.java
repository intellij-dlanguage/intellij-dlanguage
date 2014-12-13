// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static net.masterthought.dlanguage.psi.DLanguageTypes.*;
import static com.intellij.lang.parser.GeneratedParserUtilBase.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import com.intellij.lang.PsiParser;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class DLanguageParser implements PsiParser {

  public ASTNode parse(IElementType t, PsiBuilder b) {
    parseLight(t, b);
    return b.getTreeBuilt();
  }

  public void parseLight(IElementType t, PsiBuilder b) {
    boolean r;
    b = adapt_builder_(t, b, this, null);
    Marker m = enter_section_(b, 0, _COLLAPSE_, null);
    if (t == ARGLIST) {
      r = arglist(b, 0);
    }
    else if (t == BIT_EXPRESSION) {
      r = bit_expression(b, 0);
    }
    else if (t == CASTING_EXPRESSION) {
      r = casting_expression(b, 0);
    }
    else if (t == CHARACTER) {
      r = character(b, 0);
    }
    else if (t == CLASS_DECLARATION) {
      r = class_declaration(b, 0);
    }
    else if (t == CLASS_NAME) {
      r = class_name(b, 0);
    }
    else if (t == COMPILATION_UNIT) {
      r = compilation_unit(b, 0);
    }
    else if (t == CONSTRUCTOR_DECLARATION) {
      r = constructor_declaration(b, 0);
    }
    else if (t == CREATING_EXPRESSION) {
      r = creating_expression(b, 0);
    }
    else if (t == DECIMAL_DIGITS) {
      r = decimal_digits(b, 0);
    }
    else if (t == DO_STATEMENT) {
      r = do_statement(b, 0);
    }
    else if (t == DOC_COMMENT) {
      r = doc_comment(b, 0);
    }
    else if (t == EXPONENT_PART) {
      r = exponent_part(b, 0);
    }
    else if (t == EXPRESSION) {
      r = expression(b, 0);
    }
    else if (t == FIELD_DECLARATION) {
      r = field_declaration(b, 0);
    }
    else if (t == FLOAT_LITERAL) {
      r = float_literal(b, 0);
    }
    else if (t == FLOAT_TYPE_SUFFIX) {
      r = float_type_suffix(b, 0);
    }
    else if (t == FOR_STATEMENT) {
      r = for_statement(b, 0);
    }
    else if (t == IDENTIFIER) {
      r = identifier(b, 0);
    }
    else if (t == IF_STATEMENT) {
      r = if_statement(b, 0);
    }
    else if (t == IMPORT_STATEMENT) {
      r = import_statement(b, 0);
    }
    else if (t == INTEGER_LITERAL) {
      r = integer_literal(b, 0);
    }
    else if (t == INTERFACE_DECLARATION) {
      r = interface_declaration(b, 0);
    }
    else if (t == INTERFACE_NAME) {
      r = interface_name(b, 0);
    }
    else if (t == LITERAL_EXPRESSION) {
      r = literal_expression(b, 0);
    }
    else if (t == LOGICAL_EXPRESSION) {
      r = logical_expression(b, 0);
    }
    else if (t == METHOD_DECLARATION) {
      r = method_declaration(b, 0);
    }
    else if (t == MODIFIER) {
      r = modifier(b, 0);
    }
    else if (t == NUMERIC_EXPRESSION) {
      r = numeric_expression(b, 0);
    }
    else if (t == PACKAGE_NAME) {
      r = package_name(b, 0);
    }
    else if (t == PACKAGE_STATEMENT) {
      r = package_statement(b, 0);
    }
    else if (t == PARAMETER) {
      r = parameter(b, 0);
    }
    else if (t == PARAMETER_LIST) {
      r = parameter_list(b, 0);
    }
    else if (t == STATEMENT) {
      r = statement(b, 0);
    }
    else if (t == STATEMENT_BLOCK) {
      r = statement_block(b, 0);
    }
    else if (t == STATIC_INITIALIZER) {
      r = static_initializer(b, 0);
    }
    else if (t == STRING) {
      r = string(b, 0);
    }
    else if (t == STRING_EXPRESSION) {
      r = string_expression(b, 0);
    }
    else if (t == SWITCH_STATEMENT) {
      r = switch_statement(b, 0);
    }
    else if (t == TESTING_EXPRESSION) {
      r = testing_expression(b, 0);
    }
    else if (t == TRY_STATEMENT) {
      r = try_statement(b, 0);
    }
    else if (t == TYPE) {
      r = type(b, 0);
    }
    else if (t == TYPE_DECLARATION) {
      r = type_declaration(b, 0);
    }
    else if (t == TYPE_SPECIFIER) {
      r = type_specifier(b, 0);
    }
    else if (t == VARIABLE_DECLARATION) {
      r = variable_declaration(b, 0);
    }
    else if (t == VARIABLE_DECLARATOR) {
      r = variable_declarator(b, 0);
    }
    else if (t == VARIABLE_INITIALIZER) {
      r = variable_initializer(b, 0);
    }
    else if (t == WHILE_STATEMENT) {
      r = while_statement(b, 0);
    }
    else {
      r = parse_root_(t, b, 0);
    }
    exit_section_(b, 0, m, t, r, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType t, PsiBuilder b, int l) {
    return dFile(b, l + 1);
  }

  /* ********************************************************** */
  // expression { "," expression }
  public static boolean arglist(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "arglist")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<arglist>");
    r = expression(b, l + 1);
    r = r && arglist_1(b, l + 1);
    exit_section_(b, l, m, ARGLIST, r, false, null);
    return r;
  }

  // "," expression
  private static boolean arglist_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "arglist_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ",");
    r = r && expression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // ( "~" expression )
  //       | ( expression
  //       ( ">>="
  //       | "<<"
  //       | ">>"
  //       | ">>>" )
  //       expression )
  public static boolean bit_expression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bit_expression")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<bit expression>");
    r = bit_expression_0(b, l + 1);
    if (!r) r = bit_expression_1(b, l + 1);
    exit_section_(b, l, m, BIT_EXPRESSION, r, false, null);
    return r;
  }

  // "~" expression
  private static boolean bit_expression_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bit_expression_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "~");
    r = r && expression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // expression
  //       ( ">>="
  //       | "<<"
  //       | ">>"
  //       | ">>>" )
  //       expression
  private static boolean bit_expression_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bit_expression_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = expression(b, l + 1);
    r = r && bit_expression_1_1(b, l + 1);
    r = r && expression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ">>="
  //       | "<<"
  //       | ">>"
  //       | ">>>"
  private static boolean bit_expression_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bit_expression_1_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ">>=");
    if (!r) r = consumeToken(b, "<<");
    if (!r) r = consumeToken(b, ">>");
    if (!r) r = consumeToken(b, ">>>");
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // "(" type ")" expression
  public static boolean casting_expression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "casting_expression")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<casting expression>");
    r = consumeToken(b, "(");
    r = r && type(b, l + 1);
    r = r && consumeToken(b, ")");
    r = r && expression(b, l + 1);
    exit_section_(b, l, m, CASTING_EXPRESSION, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // "based on the unicode character set"
  public static boolean character(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "character")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<character>");
    r = consumeToken(b, "based on the unicode character set");
    exit_section_(b, l, m, CHARACTER, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // { modifier } "class" identifier
  //       [ "extends" class_name ]
  //       [ "implements" interface_name { "," interface_name } ]
  //       "{" { field_declaration } "}"
  public static boolean class_declaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "class_declaration")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<class declaration>");
    r = class_declaration_0(b, l + 1);
    r = r && consumeToken(b, "class");
    r = r && identifier(b, l + 1);
    r = r && class_declaration_3(b, l + 1);
    r = r && class_declaration_4(b, l + 1);
    r = r && consumeToken(b, "{");
    r = r && class_declaration_6(b, l + 1);
    r = r && consumeToken(b, "}");
    exit_section_(b, l, m, CLASS_DECLARATION, r, false, null);
    return r;
  }

  // { modifier }
  private static boolean class_declaration_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "class_declaration_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = modifier(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // [ "extends" class_name ]
  private static boolean class_declaration_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "class_declaration_3")) return false;
    class_declaration_3_0(b, l + 1);
    return true;
  }

  // "extends" class_name
  private static boolean class_declaration_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "class_declaration_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "extends");
    r = r && class_name(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // [ "implements" interface_name { "," interface_name } ]
  private static boolean class_declaration_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "class_declaration_4")) return false;
    class_declaration_4_0(b, l + 1);
    return true;
  }

  // "implements" interface_name { "," interface_name }
  private static boolean class_declaration_4_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "class_declaration_4_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "implements");
    r = r && interface_name(b, l + 1);
    r = r && class_declaration_4_0_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // "," interface_name
  private static boolean class_declaration_4_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "class_declaration_4_0_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ",");
    r = r && interface_name(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // { field_declaration }
  private static boolean class_declaration_6(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "class_declaration_6")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = field_declaration(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // identifier
  //       | ( package_name "." identifier )
  public static boolean class_name(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "class_name")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<class name>");
    r = identifier(b, l + 1);
    if (!r) r = class_name_1(b, l + 1);
    exit_section_(b, l, m, CLASS_NAME, r, false, null);
    return r;
  }

  // package_name "." identifier
  private static boolean class_name_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "class_name_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = package_name(b, l + 1);
    r = r && consumeToken(b, ".");
    r = r && identifier(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // [ package_statement ]
  //       { import_statement }
  //       { type_declaration }
  public static boolean compilation_unit(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "compilation_unit")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<compilation unit>");
    r = compilation_unit_0(b, l + 1);
    r = r && compilation_unit_1(b, l + 1);
    r = r && compilation_unit_2(b, l + 1);
    exit_section_(b, l, m, COMPILATION_UNIT, r, false, null);
    return r;
  }

  // [ package_statement ]
  private static boolean compilation_unit_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "compilation_unit_0")) return false;
    package_statement(b, l + 1);
    return true;
  }

  // { import_statement }
  private static boolean compilation_unit_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "compilation_unit_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = import_statement(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // { type_declaration }
  private static boolean compilation_unit_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "compilation_unit_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = type_declaration(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // { modifier } identifier "(" [ parameter_list ] ")"
  //       statement_block
  public static boolean constructor_declaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "constructor_declaration")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<constructor declaration>");
    r = constructor_declaration_0(b, l + 1);
    r = r && identifier(b, l + 1);
    r = r && consumeToken(b, "(");
    r = r && constructor_declaration_3(b, l + 1);
    r = r && consumeToken(b, ")");
    r = r && statement_block(b, l + 1);
    exit_section_(b, l, m, CONSTRUCTOR_DECLARATION, r, false, null);
    return r;
  }

  // { modifier }
  private static boolean constructor_declaration_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "constructor_declaration_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = modifier(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // [ parameter_list ]
  private static boolean constructor_declaration_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "constructor_declaration_3")) return false;
    parameter_list(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // "new" ( ( class_name "(" [ arglist ] ")" )
  //       | ( type_specifier [ "[" expression "]" ] { "[" "]" } )
  //       | ( "(" expression ")" ) )
  public static boolean creating_expression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "creating_expression")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<creating expression>");
    r = consumeToken(b, "new");
    r = r && creating_expression_1(b, l + 1);
    exit_section_(b, l, m, CREATING_EXPRESSION, r, false, null);
    return r;
  }

  // ( class_name "(" [ arglist ] ")" )
  //       | ( type_specifier [ "[" expression "]" ] { "[" "]" } )
  //       | ( "(" expression ")" )
  private static boolean creating_expression_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "creating_expression_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = creating_expression_1_0(b, l + 1);
    if (!r) r = creating_expression_1_1(b, l + 1);
    if (!r) r = creating_expression_1_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // class_name "(" [ arglist ] ")"
  private static boolean creating_expression_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "creating_expression_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = class_name(b, l + 1);
    r = r && consumeToken(b, "(");
    r = r && creating_expression_1_0_2(b, l + 1);
    r = r && consumeToken(b, ")");
    exit_section_(b, m, null, r);
    return r;
  }

  // [ arglist ]
  private static boolean creating_expression_1_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "creating_expression_1_0_2")) return false;
    arglist(b, l + 1);
    return true;
  }

  // type_specifier [ "[" expression "]" ] { "[" "]" }
  private static boolean creating_expression_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "creating_expression_1_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = type_specifier(b, l + 1);
    r = r && creating_expression_1_1_1(b, l + 1);
    r = r && creating_expression_1_1_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // [ "[" expression "]" ]
  private static boolean creating_expression_1_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "creating_expression_1_1_1")) return false;
    creating_expression_1_1_1_0(b, l + 1);
    return true;
  }

  // "[" expression "]"
  private static boolean creating_expression_1_1_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "creating_expression_1_1_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "[");
    r = r && expression(b, l + 1);
    r = r && consumeToken(b, "]");
    exit_section_(b, m, null, r);
    return r;
  }

  // "[" "]"
  private static boolean creating_expression_1_1_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "creating_expression_1_1_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "[");
    r = r && consumeToken(b, "]");
    exit_section_(b, m, null, r);
    return r;
  }

  // "(" expression ")"
  private static boolean creating_expression_1_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "creating_expression_1_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "(");
    r = r && expression(b, l + 1);
    r = r && consumeToken(b, ")");
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // item_*
  static boolean dFile(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "dFile")) return false;
    int c = current_position_(b);
    while (true) {
      if (!item_(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "dFile", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  /* ********************************************************** */
  // "0..9" { "0..9" }
  public static boolean decimal_digits(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "decimal_digits")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<decimal digits>");
    r = consumeToken(b, "0..9");
    r = r && decimal_digits_1(b, l + 1);
    exit_section_(b, l, m, DECIMAL_DIGITS, r, false, null);
    return r;
  }

  // { "0..9" }
  private static boolean decimal_digits_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "decimal_digits_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "0..9");
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // "do" statement "while" "(" expression ")" ";"
  public static boolean do_statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "do_statement")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<do statement>");
    r = consumeToken(b, "do");
    r = r && statement(b, l + 1);
    r = r && consumeToken(b, "while");
    r = r && consumeToken(b, "(");
    r = r && expression(b, l + 1);
    r = r && consumeToken(b, ")");
    r = r && consumeToken(b, ";");
    exit_section_(b, l, m, DO_STATEMENT, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // "/**" "... text ..." "*/"
  public static boolean doc_comment(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "doc_comment")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<doc comment>");
    r = consumeToken(b, "/**");
    r = r && consumeToken(b, "... text ...");
    r = r && consumeToken(b, "*/");
    exit_section_(b, l, m, DOC_COMMENT, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // "e" [ "+" | "-" ] decimal_digits
  public static boolean exponent_part(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "exponent_part")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<exponent part>");
    r = consumeToken(b, "e");
    r = r && exponent_part_1(b, l + 1);
    r = r && decimal_digits(b, l + 1);
    exit_section_(b, l, m, EXPONENT_PART, r, false, null);
    return r;
  }

  // [ "+" | "-" ]
  private static boolean exponent_part_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "exponent_part_1")) return false;
    exponent_part_1_0(b, l + 1);
    return true;
  }

  // "+" | "-"
  private static boolean exponent_part_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "exponent_part_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "+");
    if (!r) r = consumeToken(b, "-");
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // numeric_expression
  //       | testing_expression
  //       | logical_expression
  //       | string_expression
  //       | bit_expression
  //       | casting_expression
  //       | creating_expression
  //       | literal_expression
  //       | "null"
  //       | "super"
  //       | "this"
  //       | identifier
  //       | ( "(" expression ")" )
  //       | ( expression
  //       ( ( "(" [ arglist ] ")" )
  //       | ( "[" expression "]" )
  //       | ( "." expression )
  //       | ( "," expression )
  //       | ( "instanceof" ( class_name | interface_name ) )
  //       ) )
  public static boolean expression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "expression")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _COLLAPSE_, "<expression>");
    r = numeric_expression(b, l + 1);
    if (!r) r = testing_expression(b, l + 1);
    if (!r) r = logical_expression(b, l + 1);
    if (!r) r = string_expression(b, l + 1);
    if (!r) r = bit_expression(b, l + 1);
    if (!r) r = casting_expression(b, l + 1);
    if (!r) r = creating_expression(b, l + 1);
    if (!r) r = literal_expression(b, l + 1);
    if (!r) r = consumeToken(b, "null");
    if (!r) r = consumeToken(b, "super");
    if (!r) r = consumeToken(b, "this");
    if (!r) r = identifier(b, l + 1);
    if (!r) r = expression_12(b, l + 1);
    if (!r) r = expression_13(b, l + 1);
    exit_section_(b, l, m, EXPRESSION, r, false, null);
    return r;
  }

  // "(" expression ")"
  private static boolean expression_12(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "expression_12")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "(");
    r = r && expression(b, l + 1);
    r = r && consumeToken(b, ")");
    exit_section_(b, m, null, r);
    return r;
  }

  // expression
  //       ( ( "(" [ arglist ] ")" )
  //       | ( "[" expression "]" )
  //       | ( "." expression )
  //       | ( "," expression )
  //       | ( "instanceof" ( class_name | interface_name ) )
  //       )
  private static boolean expression_13(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "expression_13")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = expression(b, l + 1);
    r = r && expression_13_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ( "(" [ arglist ] ")" )
  //       | ( "[" expression "]" )
  //       | ( "." expression )
  //       | ( "," expression )
  //       | ( "instanceof" ( class_name | interface_name ) )
  private static boolean expression_13_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "expression_13_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = expression_13_1_0(b, l + 1);
    if (!r) r = expression_13_1_1(b, l + 1);
    if (!r) r = expression_13_1_2(b, l + 1);
    if (!r) r = expression_13_1_3(b, l + 1);
    if (!r) r = expression_13_1_4(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // "(" [ arglist ] ")"
  private static boolean expression_13_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "expression_13_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "(");
    r = r && expression_13_1_0_1(b, l + 1);
    r = r && consumeToken(b, ")");
    exit_section_(b, m, null, r);
    return r;
  }

  // [ arglist ]
  private static boolean expression_13_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "expression_13_1_0_1")) return false;
    arglist(b, l + 1);
    return true;
  }

  // "[" expression "]"
  private static boolean expression_13_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "expression_13_1_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "[");
    r = r && expression(b, l + 1);
    r = r && consumeToken(b, "]");
    exit_section_(b, m, null, r);
    return r;
  }

  // "." expression
  private static boolean expression_13_1_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "expression_13_1_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ".");
    r = r && expression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // "," expression
  private static boolean expression_13_1_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "expression_13_1_3")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ",");
    r = r && expression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // "instanceof" ( class_name | interface_name )
  private static boolean expression_13_1_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "expression_13_1_4")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "instanceof");
    r = r && expression_13_1_4_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // class_name | interface_name
  private static boolean expression_13_1_4_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "expression_13_1_4_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = class_name(b, l + 1);
    if (!r) r = interface_name(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // ( [ doc_comment ] ( method_declaration
  //       | constructor_declaration
  //       | variable_declaration ) )
  //       | static_initializer
  //       | ";"
  public static boolean field_declaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "field_declaration")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<field declaration>");
    r = field_declaration_0(b, l + 1);
    if (!r) r = static_initializer(b, l + 1);
    if (!r) r = consumeToken(b, ";");
    exit_section_(b, l, m, FIELD_DECLARATION, r, false, null);
    return r;
  }

  // [ doc_comment ] ( method_declaration
  //       | constructor_declaration
  //       | variable_declaration )
  private static boolean field_declaration_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "field_declaration_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = field_declaration_0_0(b, l + 1);
    r = r && field_declaration_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // [ doc_comment ]
  private static boolean field_declaration_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "field_declaration_0_0")) return false;
    doc_comment(b, l + 1);
    return true;
  }

  // method_declaration
  //       | constructor_declaration
  //       | variable_declaration
  private static boolean field_declaration_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "field_declaration_0_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = method_declaration(b, l + 1);
    if (!r) r = constructor_declaration(b, l + 1);
    if (!r) r = variable_declaration(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // ( decimal_digits "." [ decimal_digits ] [ exponent_part ] [ float_type_suffix ] )
  //       | ( "." decimal_digits [ exponent_part ] [ float_type_suffix ] )
  //       | ( decimal_digits [ exponent_part ] [ float_type_suffix ] )
  public static boolean float_literal(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "float_literal")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<float literal>");
    r = float_literal_0(b, l + 1);
    if (!r) r = float_literal_1(b, l + 1);
    if (!r) r = float_literal_2(b, l + 1);
    exit_section_(b, l, m, FLOAT_LITERAL, r, false, null);
    return r;
  }

  // decimal_digits "." [ decimal_digits ] [ exponent_part ] [ float_type_suffix ]
  private static boolean float_literal_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "float_literal_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = decimal_digits(b, l + 1);
    r = r && consumeToken(b, ".");
    r = r && float_literal_0_2(b, l + 1);
    r = r && float_literal_0_3(b, l + 1);
    r = r && float_literal_0_4(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // [ decimal_digits ]
  private static boolean float_literal_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "float_literal_0_2")) return false;
    decimal_digits(b, l + 1);
    return true;
  }

  // [ exponent_part ]
  private static boolean float_literal_0_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "float_literal_0_3")) return false;
    exponent_part(b, l + 1);
    return true;
  }

  // [ float_type_suffix ]
  private static boolean float_literal_0_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "float_literal_0_4")) return false;
    float_type_suffix(b, l + 1);
    return true;
  }

  // "." decimal_digits [ exponent_part ] [ float_type_suffix ]
  private static boolean float_literal_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "float_literal_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ".");
    r = r && decimal_digits(b, l + 1);
    r = r && float_literal_1_2(b, l + 1);
    r = r && float_literal_1_3(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // [ exponent_part ]
  private static boolean float_literal_1_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "float_literal_1_2")) return false;
    exponent_part(b, l + 1);
    return true;
  }

  // [ float_type_suffix ]
  private static boolean float_literal_1_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "float_literal_1_3")) return false;
    float_type_suffix(b, l + 1);
    return true;
  }

  // decimal_digits [ exponent_part ] [ float_type_suffix ]
  private static boolean float_literal_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "float_literal_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = decimal_digits(b, l + 1);
    r = r && float_literal_2_1(b, l + 1);
    r = r && float_literal_2_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // [ exponent_part ]
  private static boolean float_literal_2_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "float_literal_2_1")) return false;
    exponent_part(b, l + 1);
    return true;
  }

  // [ float_type_suffix ]
  private static boolean float_literal_2_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "float_literal_2_2")) return false;
    float_type_suffix(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // "f" | "d"
  public static boolean float_type_suffix(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "float_type_suffix")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<float type suffix>");
    r = consumeToken(b, "f");
    if (!r) r = consumeToken(b, "d");
    exit_section_(b, l, m, FLOAT_TYPE_SUFFIX, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // "for" "(" ( variable_declaration | ( expression ";" ) | ";" )
  //       [ expression ] ";"
  //       [ expression ] ";"
  //       ")" statement
  public static boolean for_statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "for_statement")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<for statement>");
    r = consumeToken(b, "for");
    r = r && consumeToken(b, "(");
    r = r && for_statement_2(b, l + 1);
    r = r && for_statement_3(b, l + 1);
    r = r && consumeToken(b, ";");
    r = r && for_statement_5(b, l + 1);
    r = r && consumeToken(b, ";");
    r = r && consumeToken(b, ")");
    r = r && statement(b, l + 1);
    exit_section_(b, l, m, FOR_STATEMENT, r, false, null);
    return r;
  }

  // variable_declaration | ( expression ";" ) | ";"
  private static boolean for_statement_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "for_statement_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = variable_declaration(b, l + 1);
    if (!r) r = for_statement_2_1(b, l + 1);
    if (!r) r = consumeToken(b, ";");
    exit_section_(b, m, null, r);
    return r;
  }

  // expression ";"
  private static boolean for_statement_2_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "for_statement_2_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = expression(b, l + 1);
    r = r && consumeToken(b, ";");
    exit_section_(b, m, null, r);
    return r;
  }

  // [ expression ]
  private static boolean for_statement_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "for_statement_3")) return false;
    expression(b, l + 1);
    return true;
  }

  // [ expression ]
  private static boolean for_statement_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "for_statement_5")) return false;
    expression(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // "a..z,$,_" { "a..z,$,_,0..9,unicode character over 00C0" }
  public static boolean identifier(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "identifier")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<identifier>");
    r = consumeToken(b, "a..z,$,_");
    r = r && identifier_1(b, l + 1);
    exit_section_(b, l, m, IDENTIFIER, r, false, null);
    return r;
  }

  // { "a..z,$,_,0..9,unicode character over 00C0" }
  private static boolean identifier_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "identifier_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "a..z,$,_,0..9,unicode character over 00C0");
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // "if" "(" expression ")" statement
  //       [ "else" statement ]
  public static boolean if_statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "if_statement")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<if statement>");
    r = consumeToken(b, "if");
    r = r && consumeToken(b, "(");
    r = r && expression(b, l + 1);
    r = r && consumeToken(b, ")");
    r = r && statement(b, l + 1);
    r = r && if_statement_5(b, l + 1);
    exit_section_(b, l, m, IF_STATEMENT, r, false, null);
    return r;
  }

  // [ "else" statement ]
  private static boolean if_statement_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "if_statement_5")) return false;
    if_statement_5_0(b, l + 1);
    return true;
  }

  // "else" statement
  private static boolean if_statement_5_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "if_statement_5_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "else");
    r = r && statement(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // "import" ( ( package_name "." "*" ";" )
  //       | ( class_name | interface_name ) ) ";"
  public static boolean import_statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "import_statement")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<import statement>");
    r = consumeToken(b, "import");
    r = r && import_statement_1(b, l + 1);
    r = r && consumeToken(b, ";");
    exit_section_(b, l, m, IMPORT_STATEMENT, r, false, null);
    return r;
  }

  // ( package_name "." "*" ";" )
  //       | ( class_name | interface_name )
  private static boolean import_statement_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "import_statement_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = import_statement_1_0(b, l + 1);
    if (!r) r = import_statement_1_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // package_name "." "*" ";"
  private static boolean import_statement_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "import_statement_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = package_name(b, l + 1);
    r = r && consumeToken(b, ".");
    r = r && consumeToken(b, "*");
    r = r && consumeToken(b, ";");
    exit_section_(b, m, null, r);
    return r;
  }

  // class_name | interface_name
  private static boolean import_statement_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "import_statement_1_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = class_name(b, l + 1);
    if (!r) r = interface_name(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // ( ( "1..9" { "0..9" } )
  //       | { "0..7" }
  //       | ( "0" "x" "0..9a..f" { "0..9a..f" } ) )
  //       [ "l" ]
  public static boolean integer_literal(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "integer_literal")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<integer literal>");
    r = integer_literal_0(b, l + 1);
    r = r && integer_literal_1(b, l + 1);
    exit_section_(b, l, m, INTEGER_LITERAL, r, false, null);
    return r;
  }

  // ( "1..9" { "0..9" } )
  //       | { "0..7" }
  //       | ( "0" "x" "0..9a..f" { "0..9a..f" } )
  private static boolean integer_literal_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "integer_literal_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = integer_literal_0_0(b, l + 1);
    if (!r) r = integer_literal_0_1(b, l + 1);
    if (!r) r = integer_literal_0_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // "1..9" { "0..9" }
  private static boolean integer_literal_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "integer_literal_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "1..9");
    r = r && integer_literal_0_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // { "0..9" }
  private static boolean integer_literal_0_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "integer_literal_0_0_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "0..9");
    exit_section_(b, m, null, r);
    return r;
  }

  // { "0..7" }
  private static boolean integer_literal_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "integer_literal_0_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "0..7");
    exit_section_(b, m, null, r);
    return r;
  }

  // "0" "x" "0..9a..f" { "0..9a..f" }
  private static boolean integer_literal_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "integer_literal_0_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "0");
    r = r && consumeToken(b, "x");
    r = r && consumeToken(b, "0..9a..f");
    r = r && integer_literal_0_2_3(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // { "0..9a..f" }
  private static boolean integer_literal_0_2_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "integer_literal_0_2_3")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "0..9a..f");
    exit_section_(b, m, null, r);
    return r;
  }

  // [ "l" ]
  private static boolean integer_literal_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "integer_literal_1")) return false;
    consumeToken(b, "l");
    return true;
  }

  /* ********************************************************** */
  // { modifier } "interface" identifier
  //       [ "extends" interface_name { "," interface_name } ]
  //       "{" { field_declaration } "}"
  public static boolean interface_declaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "interface_declaration")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<interface declaration>");
    r = interface_declaration_0(b, l + 1);
    r = r && consumeToken(b, "interface");
    r = r && identifier(b, l + 1);
    r = r && interface_declaration_3(b, l + 1);
    r = r && consumeToken(b, "{");
    r = r && interface_declaration_5(b, l + 1);
    r = r && consumeToken(b, "}");
    exit_section_(b, l, m, INTERFACE_DECLARATION, r, false, null);
    return r;
  }

  // { modifier }
  private static boolean interface_declaration_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "interface_declaration_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = modifier(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // [ "extends" interface_name { "," interface_name } ]
  private static boolean interface_declaration_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "interface_declaration_3")) return false;
    interface_declaration_3_0(b, l + 1);
    return true;
  }

  // "extends" interface_name { "," interface_name }
  private static boolean interface_declaration_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "interface_declaration_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "extends");
    r = r && interface_name(b, l + 1);
    r = r && interface_declaration_3_0_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // "," interface_name
  private static boolean interface_declaration_3_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "interface_declaration_3_0_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ",");
    r = r && interface_name(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // { field_declaration }
  private static boolean interface_declaration_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "interface_declaration_5")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = field_declaration(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // identifier
  //       | ( package_name "." identifier )
  public static boolean interface_name(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "interface_name")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<interface name>");
    r = identifier(b, l + 1);
    if (!r) r = interface_name_1(b, l + 1);
    exit_section_(b, l, m, INTERFACE_NAME, r, false, null);
    return r;
  }

  // package_name "." identifier
  private static boolean interface_name_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "interface_name_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = package_name(b, l + 1);
    r = r && consumeToken(b, ".");
    r = r && identifier(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // identifier|
  //                    modifier|
  //                    package_name|
  //                    class_name|
  //                    interface_name|
  //                    class_declaration|
  //                    doc_comment|
  //                    type|
  //                    type_specifier|
  //                    parameter|
  //                    parameter_list|
  //                    statement|
  //                    statement_block|
  //                    method_declaration|
  //                    field_declaration|
  //                    COMMENT|
  //                    CRLF
  static boolean item_(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "item_")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = identifier(b, l + 1);
    if (!r) r = modifier(b, l + 1);
    if (!r) r = package_name(b, l + 1);
    if (!r) r = class_name(b, l + 1);
    if (!r) r = interface_name(b, l + 1);
    if (!r) r = class_declaration(b, l + 1);
    if (!r) r = doc_comment(b, l + 1);
    if (!r) r = type(b, l + 1);
    if (!r) r = type_specifier(b, l + 1);
    if (!r) r = parameter(b, l + 1);
    if (!r) r = parameter_list(b, l + 1);
    if (!r) r = statement(b, l + 1);
    if (!r) r = statement_block(b, l + 1);
    if (!r) r = method_declaration(b, l + 1);
    if (!r) r = field_declaration(b, l + 1);
    if (!r) r = consumeToken(b, COMMENT);
    if (!r) r = consumeToken(b, CRLF);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // integer_literal
  //       | float_literal
  //       | string
  //       | character
  public static boolean literal_expression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "literal_expression")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<literal expression>");
    r = integer_literal(b, l + 1);
    if (!r) r = float_literal(b, l + 1);
    if (!r) r = string(b, l + 1);
    if (!r) r = character(b, l + 1);
    exit_section_(b, l, m, LITERAL_EXPRESSION, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // ( "!" expression )
  //       | ( expression
  //       ( "ampersand"
  //       | "ampersand="
  //       | "|"
  //       | "|="
  //       | "^"
  //       | "^="
  //       | ( "ampersand" "ampersand" )
  //       | "||="
  //       | "%"
  //       | "%=" )
  //       expression )
  //       | ( expression "?" expression ":" expression )
  //       | "true"
  //       | "false"
  public static boolean logical_expression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "logical_expression")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<logical expression>");
    r = logical_expression_0(b, l + 1);
    if (!r) r = logical_expression_1(b, l + 1);
    if (!r) r = logical_expression_2(b, l + 1);
    if (!r) r = consumeToken(b, "true");
    if (!r) r = consumeToken(b, "false");
    exit_section_(b, l, m, LOGICAL_EXPRESSION, r, false, null);
    return r;
  }

  // "!" expression
  private static boolean logical_expression_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "logical_expression_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "!");
    r = r && expression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // expression
  //       ( "ampersand"
  //       | "ampersand="
  //       | "|"
  //       | "|="
  //       | "^"
  //       | "^="
  //       | ( "ampersand" "ampersand" )
  //       | "||="
  //       | "%"
  //       | "%=" )
  //       expression
  private static boolean logical_expression_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "logical_expression_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = expression(b, l + 1);
    r = r && logical_expression_1_1(b, l + 1);
    r = r && expression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // "ampersand"
  //       | "ampersand="
  //       | "|"
  //       | "|="
  //       | "^"
  //       | "^="
  //       | ( "ampersand" "ampersand" )
  //       | "||="
  //       | "%"
  //       | "%="
  private static boolean logical_expression_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "logical_expression_1_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "ampersand");
    if (!r) r = consumeToken(b, "ampersand=");
    if (!r) r = consumeToken(b, "|");
    if (!r) r = consumeToken(b, "|=");
    if (!r) r = consumeToken(b, "^");
    if (!r) r = consumeToken(b, "^=");
    if (!r) r = logical_expression_1_1_6(b, l + 1);
    if (!r) r = consumeToken(b, "||=");
    if (!r) r = consumeToken(b, "%");
    if (!r) r = consumeToken(b, "%=");
    exit_section_(b, m, null, r);
    return r;
  }

  // "ampersand" "ampersand"
  private static boolean logical_expression_1_1_6(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "logical_expression_1_1_6")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "ampersand");
    r = r && consumeToken(b, "ampersand");
    exit_section_(b, m, null, r);
    return r;
  }

  // expression "?" expression ":" expression
  private static boolean logical_expression_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "logical_expression_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = expression(b, l + 1);
    r = r && consumeToken(b, "?");
    r = r && expression(b, l + 1);
    r = r && consumeToken(b, ":");
    r = r && expression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // { modifier } type identifier
  //       "(" [ parameter_list ] ")" { "[" "]" }
  //       ( statement_block | ";" )
  public static boolean method_declaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "method_declaration")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<method declaration>");
    r = method_declaration_0(b, l + 1);
    r = r && type(b, l + 1);
    r = r && identifier(b, l + 1);
    r = r && consumeToken(b, "(");
    r = r && method_declaration_4(b, l + 1);
    r = r && consumeToken(b, ")");
    r = r && method_declaration_6(b, l + 1);
    r = r && method_declaration_7(b, l + 1);
    exit_section_(b, l, m, METHOD_DECLARATION, r, false, null);
    return r;
  }

  // { modifier }
  private static boolean method_declaration_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "method_declaration_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = modifier(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // [ parameter_list ]
  private static boolean method_declaration_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "method_declaration_4")) return false;
    parameter_list(b, l + 1);
    return true;
  }

  // "[" "]"
  private static boolean method_declaration_6(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "method_declaration_6")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "[");
    r = r && consumeToken(b, "]");
    exit_section_(b, m, null, r);
    return r;
  }

  // statement_block | ";"
  private static boolean method_declaration_7(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "method_declaration_7")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = statement_block(b, l + 1);
    if (!r) r = consumeToken(b, ";");
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // "public"
  //       | "private"
  //       | "protected"
  //       | "static"
  //       | "final"
  //       | "native"
  //       | "synchronized"
  //       | "abstract"
  //       | "threadsafe"
  //       | "transient"
  public static boolean modifier(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "modifier")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<modifier>");
    r = consumeToken(b, "public");
    if (!r) r = consumeToken(b, "private");
    if (!r) r = consumeToken(b, "protected");
    if (!r) r = consumeToken(b, "static");
    if (!r) r = consumeToken(b, "final");
    if (!r) r = consumeToken(b, "native");
    if (!r) r = consumeToken(b, "synchronized");
    if (!r) r = consumeToken(b, "abstract");
    if (!r) r = consumeToken(b, "threadsafe");
    if (!r) r = consumeToken(b, "transient");
    exit_section_(b, l, m, MODIFIER, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // ( ( "-"
  //       | "++"
  //       | "--" )
  //       expression )
  //       | ( expression
  //       ( "++"
  //       | "--" ) )
  //       | ( expression
  //       ( "+"
  //       | "+="
  //       | "-"
  //       | "-="
  //       | "*"
  //       | "*="
  //       | "/"
  //       | "/="
  //       | "%"
  //       | "%=" )
  //       expression )
  public static boolean numeric_expression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "numeric_expression")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<numeric expression>");
    r = numeric_expression_0(b, l + 1);
    if (!r) r = numeric_expression_1(b, l + 1);
    if (!r) r = numeric_expression_2(b, l + 1);
    exit_section_(b, l, m, NUMERIC_EXPRESSION, r, false, null);
    return r;
  }

  // ( "-"
  //       | "++"
  //       | "--" )
  //       expression
  private static boolean numeric_expression_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "numeric_expression_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = numeric_expression_0_0(b, l + 1);
    r = r && expression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // "-"
  //       | "++"
  //       | "--"
  private static boolean numeric_expression_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "numeric_expression_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "-");
    if (!r) r = consumeToken(b, "++");
    if (!r) r = consumeToken(b, "--");
    exit_section_(b, m, null, r);
    return r;
  }

  // expression
  //       ( "++"
  //       | "--" )
  private static boolean numeric_expression_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "numeric_expression_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = expression(b, l + 1);
    r = r && numeric_expression_1_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // "++"
  //       | "--"
  private static boolean numeric_expression_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "numeric_expression_1_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "++");
    if (!r) r = consumeToken(b, "--");
    exit_section_(b, m, null, r);
    return r;
  }

  // expression
  //       ( "+"
  //       | "+="
  //       | "-"
  //       | "-="
  //       | "*"
  //       | "*="
  //       | "/"
  //       | "/="
  //       | "%"
  //       | "%=" )
  //       expression
  private static boolean numeric_expression_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "numeric_expression_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = expression(b, l + 1);
    r = r && numeric_expression_2_1(b, l + 1);
    r = r && expression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // "+"
  //       | "+="
  //       | "-"
  //       | "-="
  //       | "*"
  //       | "*="
  //       | "/"
  //       | "/="
  //       | "%"
  //       | "%="
  private static boolean numeric_expression_2_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "numeric_expression_2_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "+");
    if (!r) r = consumeToken(b, "+=");
    if (!r) r = consumeToken(b, "-");
    if (!r) r = consumeToken(b, "-=");
    if (!r) r = consumeToken(b, "*");
    if (!r) r = consumeToken(b, "*=");
    if (!r) r = consumeToken(b, "/");
    if (!r) r = consumeToken(b, "/=");
    if (!r) r = consumeToken(b, "%");
    if (!r) r = consumeToken(b, "%=");
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // identifier
  //       | ( package_name "." identifier )
  public static boolean package_name(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "package_name")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<package name>");
    r = identifier(b, l + 1);
    if (!r) r = package_name_1(b, l + 1);
    exit_section_(b, l, m, PACKAGE_NAME, r, false, null);
    return r;
  }

  // package_name "." identifier
  private static boolean package_name_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "package_name_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = package_name(b, l + 1);
    r = r && consumeToken(b, ".");
    r = r && identifier(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // "package" package_name ";"
  public static boolean package_statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "package_statement")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<package statement>");
    r = consumeToken(b, "package");
    r = r && package_name(b, l + 1);
    r = r && consumeToken(b, ";");
    exit_section_(b, l, m, PACKAGE_STATEMENT, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // type identifier { "[" "]" }
  public static boolean parameter(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parameter")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<parameter>");
    r = type(b, l + 1);
    r = r && identifier(b, l + 1);
    r = r && parameter_2(b, l + 1);
    exit_section_(b, l, m, PARAMETER, r, false, null);
    return r;
  }

  // "[" "]"
  private static boolean parameter_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parameter_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "[");
    r = r && consumeToken(b, "]");
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // parameter { "," parameter }
  public static boolean parameter_list(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parameter_list")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<parameter list>");
    r = parameter(b, l + 1);
    r = r && parameter_list_1(b, l + 1);
    exit_section_(b, l, m, PARAMETER_LIST, r, false, null);
    return r;
  }

  // "," parameter
  private static boolean parameter_list_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parameter_list_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ",");
    r = r && parameter(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // variable_declaration
  //       | ( expression ";" )
  //       | ( statement_block )
  //       | ( if_statement )
  //       | ( do_statement )
  //       | ( while_statement )
  //       | ( for_statement )
  //       | ( try_statement )
  //       | ( switch_statement )
  //       | ( "synchronized" "(" expression ")" statement )
  //       | ( "return" [ expression ] ";" )
  //       | ( "throw" expression ";" )
  //       | ( identifier ":" statement )
  //       | ( "break" [ identifier ] ";" )
  //       | ( "continue" [ identifier ] ";" )
  //       | ( ";" )
  public static boolean statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "statement")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<statement>");
    r = variable_declaration(b, l + 1);
    if (!r) r = statement_1(b, l + 1);
    if (!r) r = statement_2(b, l + 1);
    if (!r) r = statement_3(b, l + 1);
    if (!r) r = statement_4(b, l + 1);
    if (!r) r = statement_5(b, l + 1);
    if (!r) r = statement_6(b, l + 1);
    if (!r) r = statement_7(b, l + 1);
    if (!r) r = statement_8(b, l + 1);
    if (!r) r = statement_9(b, l + 1);
    if (!r) r = statement_10(b, l + 1);
    if (!r) r = statement_11(b, l + 1);
    if (!r) r = statement_12(b, l + 1);
    if (!r) r = statement_13(b, l + 1);
    if (!r) r = statement_14(b, l + 1);
    if (!r) r = statement_15(b, l + 1);
    exit_section_(b, l, m, STATEMENT, r, false, null);
    return r;
  }

  // expression ";"
  private static boolean statement_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "statement_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = expression(b, l + 1);
    r = r && consumeToken(b, ";");
    exit_section_(b, m, null, r);
    return r;
  }

  // ( statement_block )
  private static boolean statement_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "statement_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = statement_block(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ( if_statement )
  private static boolean statement_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "statement_3")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = if_statement(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ( do_statement )
  private static boolean statement_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "statement_4")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = do_statement(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ( while_statement )
  private static boolean statement_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "statement_5")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = while_statement(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ( for_statement )
  private static boolean statement_6(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "statement_6")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = for_statement(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ( try_statement )
  private static boolean statement_7(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "statement_7")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = try_statement(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ( switch_statement )
  private static boolean statement_8(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "statement_8")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = switch_statement(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // "synchronized" "(" expression ")" statement
  private static boolean statement_9(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "statement_9")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "synchronized");
    r = r && consumeToken(b, "(");
    r = r && expression(b, l + 1);
    r = r && consumeToken(b, ")");
    r = r && statement(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // "return" [ expression ] ";"
  private static boolean statement_10(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "statement_10")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "return");
    r = r && statement_10_1(b, l + 1);
    r = r && consumeToken(b, ";");
    exit_section_(b, m, null, r);
    return r;
  }

  // [ expression ]
  private static boolean statement_10_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "statement_10_1")) return false;
    expression(b, l + 1);
    return true;
  }

  // "throw" expression ";"
  private static boolean statement_11(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "statement_11")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "throw");
    r = r && expression(b, l + 1);
    r = r && consumeToken(b, ";");
    exit_section_(b, m, null, r);
    return r;
  }

  // identifier ":" statement
  private static boolean statement_12(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "statement_12")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = identifier(b, l + 1);
    r = r && consumeToken(b, ":");
    r = r && statement(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // "break" [ identifier ] ";"
  private static boolean statement_13(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "statement_13")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "break");
    r = r && statement_13_1(b, l + 1);
    r = r && consumeToken(b, ";");
    exit_section_(b, m, null, r);
    return r;
  }

  // [ identifier ]
  private static boolean statement_13_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "statement_13_1")) return false;
    identifier(b, l + 1);
    return true;
  }

  // "continue" [ identifier ] ";"
  private static boolean statement_14(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "statement_14")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "continue");
    r = r && statement_14_1(b, l + 1);
    r = r && consumeToken(b, ";");
    exit_section_(b, m, null, r);
    return r;
  }

  // [ identifier ]
  private static boolean statement_14_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "statement_14_1")) return false;
    identifier(b, l + 1);
    return true;
  }

  // ( ";" )
  private static boolean statement_15(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "statement_15")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ";");
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // "{" { statement } "}"
  public static boolean statement_block(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "statement_block")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<statement block>");
    r = consumeToken(b, "{");
    r = r && statement_block_1(b, l + 1);
    r = r && consumeToken(b, "}");
    exit_section_(b, l, m, STATEMENT_BLOCK, r, false, null);
    return r;
  }

  // { statement }
  private static boolean statement_block_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "statement_block_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = statement(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // "static" statement_block
  public static boolean static_initializer(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "static_initializer")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<static initializer>");
    r = consumeToken(b, "static");
    r = r && statement_block(b, l + 1);
    exit_section_(b, l, m, STATIC_INITIALIZER, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // "''" { character } "''"
  public static boolean string(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "string")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<string>");
    r = consumeToken(b, "''");
    r = r && string_1(b, l + 1);
    r = r && consumeToken(b, "''");
    exit_section_(b, l, m, STRING, r, false, null);
    return r;
  }

  // { character }
  private static boolean string_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "string_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = character(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // expression
  //       ( "+"
  //       | "+=" )
  //       expression
  public static boolean string_expression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "string_expression")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<string expression>");
    r = expression(b, l + 1);
    r = r && string_expression_1(b, l + 1);
    r = r && expression(b, l + 1);
    exit_section_(b, l, m, STRING_EXPRESSION, r, false, null);
    return r;
  }

  // "+"
  //       | "+="
  private static boolean string_expression_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "string_expression_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "+");
    if (!r) r = consumeToken(b, "+=");
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // "switch" "(" expression ")" "{"
  //       { ( "case" expression ":" )
  //       | ( "default" ":" )
  //       | statement }
  //       "}"
  public static boolean switch_statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "switch_statement")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<switch statement>");
    r = consumeToken(b, "switch");
    r = r && consumeToken(b, "(");
    r = r && expression(b, l + 1);
    r = r && consumeToken(b, ")");
    r = r && consumeToken(b, "{");
    r = r && switch_statement_5(b, l + 1);
    r = r && consumeToken(b, "}");
    exit_section_(b, l, m, SWITCH_STATEMENT, r, false, null);
    return r;
  }

  // ( "case" expression ":" )
  //       | ( "default" ":" )
  //       | statement
  private static boolean switch_statement_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "switch_statement_5")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = switch_statement_5_0(b, l + 1);
    if (!r) r = switch_statement_5_1(b, l + 1);
    if (!r) r = statement(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // "case" expression ":"
  private static boolean switch_statement_5_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "switch_statement_5_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "case");
    r = r && expression(b, l + 1);
    r = r && consumeToken(b, ":");
    exit_section_(b, m, null, r);
    return r;
  }

  // "default" ":"
  private static boolean switch_statement_5_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "switch_statement_5_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "default");
    r = r && consumeToken(b, ":");
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // expression
  //       ( ">"
  //       | "<"
  //       | ">="
  //       | "<="
  //       | "=="
  //       | "!=" )
  //       expression
  public static boolean testing_expression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "testing_expression")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<testing expression>");
    r = expression(b, l + 1);
    r = r && testing_expression_1(b, l + 1);
    r = r && expression(b, l + 1);
    exit_section_(b, l, m, TESTING_EXPRESSION, r, false, null);
    return r;
  }

  // ">"
  //       | "<"
  //       | ">="
  //       | "<="
  //       | "=="
  //       | "!="
  private static boolean testing_expression_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "testing_expression_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ">");
    if (!r) r = consumeToken(b, "<");
    if (!r) r = consumeToken(b, ">=");
    if (!r) r = consumeToken(b, "<=");
    if (!r) r = consumeToken(b, "==");
    if (!r) r = consumeToken(b, "!=");
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // "try" statement
  //       { "catch" "(" parameter ")" statement }
  //       [ "finally" statement ]
  public static boolean try_statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "try_statement")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<try statement>");
    r = consumeToken(b, "try");
    r = r && statement(b, l + 1);
    r = r && try_statement_2(b, l + 1);
    r = r && try_statement_3(b, l + 1);
    exit_section_(b, l, m, TRY_STATEMENT, r, false, null);
    return r;
  }

  // "catch" "(" parameter ")" statement
  private static boolean try_statement_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "try_statement_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "catch");
    r = r && consumeToken(b, "(");
    r = r && parameter(b, l + 1);
    r = r && consumeToken(b, ")");
    r = r && statement(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // [ "finally" statement ]
  private static boolean try_statement_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "try_statement_3")) return false;
    try_statement_3_0(b, l + 1);
    return true;
  }

  // "finally" statement
  private static boolean try_statement_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "try_statement_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "finally");
    r = r && statement(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // type_specifier { "[" "]" }
  public static boolean type(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<type>");
    r = type_specifier(b, l + 1);
    r = r && type_1(b, l + 1);
    exit_section_(b, l, m, TYPE, r, false, null);
    return r;
  }

  // "[" "]"
  private static boolean type_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "[");
    r = r && consumeToken(b, "]");
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // [ doc_comment ] ( class_declaration | interface_declaration ) ";"
  public static boolean type_declaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_declaration")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<type declaration>");
    r = type_declaration_0(b, l + 1);
    r = r && type_declaration_1(b, l + 1);
    r = r && consumeToken(b, ";");
    exit_section_(b, l, m, TYPE_DECLARATION, r, false, null);
    return r;
  }

  // [ doc_comment ]
  private static boolean type_declaration_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_declaration_0")) return false;
    doc_comment(b, l + 1);
    return true;
  }

  // class_declaration | interface_declaration
  private static boolean type_declaration_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_declaration_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = class_declaration(b, l + 1);
    if (!r) r = interface_declaration(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // "boolean"
  //       | "byte"
  //       | "char"
  //       | "short"
  //       | "int"
  //       | "float"
  //       | "long"
  //       | "double"
  //       | class_name
  //       | interface_name
  public static boolean type_specifier(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_specifier")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<type specifier>");
    r = consumeToken(b, "boolean");
    if (!r) r = consumeToken(b, "byte");
    if (!r) r = consumeToken(b, "char");
    if (!r) r = consumeToken(b, "short");
    if (!r) r = consumeToken(b, "int");
    if (!r) r = consumeToken(b, "float");
    if (!r) r = consumeToken(b, "long");
    if (!r) r = consumeToken(b, "double");
    if (!r) r = class_name(b, l + 1);
    if (!r) r = interface_name(b, l + 1);
    exit_section_(b, l, m, TYPE_SPECIFIER, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // { modifier } type variable_declarator
  //       { "," variable_declarator } ";"
  public static boolean variable_declaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "variable_declaration")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<variable declaration>");
    r = variable_declaration_0(b, l + 1);
    r = r && type(b, l + 1);
    r = r && variable_declarator(b, l + 1);
    r = r && variable_declaration_3(b, l + 1);
    r = r && consumeToken(b, ";");
    exit_section_(b, l, m, VARIABLE_DECLARATION, r, false, null);
    return r;
  }

  // { modifier }
  private static boolean variable_declaration_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "variable_declaration_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = modifier(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // "," variable_declarator
  private static boolean variable_declaration_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "variable_declaration_3")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ",");
    r = r && variable_declarator(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // identifier { "[" "]" } [ "=" variable_initializer ]
  public static boolean variable_declarator(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "variable_declarator")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<variable declarator>");
    r = identifier(b, l + 1);
    r = r && variable_declarator_1(b, l + 1);
    r = r && variable_declarator_2(b, l + 1);
    exit_section_(b, l, m, VARIABLE_DECLARATOR, r, false, null);
    return r;
  }

  // "[" "]"
  private static boolean variable_declarator_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "variable_declarator_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "[");
    r = r && consumeToken(b, "]");
    exit_section_(b, m, null, r);
    return r;
  }

  // [ "=" variable_initializer ]
  private static boolean variable_declarator_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "variable_declarator_2")) return false;
    variable_declarator_2_0(b, l + 1);
    return true;
  }

  // "=" variable_initializer
  private static boolean variable_declarator_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "variable_declarator_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "=");
    r = r && variable_initializer(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // expression
  //       | ( "{" [ variable_initializer
  //       { "," variable_initializer } [ "," ] ] "}" )
  public static boolean variable_initializer(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "variable_initializer")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<variable initializer>");
    r = expression(b, l + 1);
    if (!r) r = variable_initializer_1(b, l + 1);
    exit_section_(b, l, m, VARIABLE_INITIALIZER, r, false, null);
    return r;
  }

  // "{" [ variable_initializer
  //       { "," variable_initializer } [ "," ] ] "}"
  private static boolean variable_initializer_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "variable_initializer_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "{");
    r = r && variable_initializer_1_1(b, l + 1);
    r = r && consumeToken(b, "}");
    exit_section_(b, m, null, r);
    return r;
  }

  // [ variable_initializer
  //       { "," variable_initializer } [ "," ] ]
  private static boolean variable_initializer_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "variable_initializer_1_1")) return false;
    variable_initializer_1_1_0(b, l + 1);
    return true;
  }

  // variable_initializer
  //       { "," variable_initializer } [ "," ]
  private static boolean variable_initializer_1_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "variable_initializer_1_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = variable_initializer(b, l + 1);
    r = r && variable_initializer_1_1_0_1(b, l + 1);
    r = r && variable_initializer_1_1_0_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // "," variable_initializer
  private static boolean variable_initializer_1_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "variable_initializer_1_1_0_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ",");
    r = r && variable_initializer(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // [ "," ]
  private static boolean variable_initializer_1_1_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "variable_initializer_1_1_0_2")) return false;
    consumeToken(b, ",");
    return true;
  }

  /* ********************************************************** */
  // "while" "(" expression ")" statement
  public static boolean while_statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "while_statement")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<while statement>");
    r = consumeToken(b, "while");
    r = r && consumeToken(b, "(");
    r = r && expression(b, l + 1);
    r = r && consumeToken(b, ")");
    r = r && statement(b, l + 1);
    exit_section_(b, l, m, WHILE_STATEMENT, r, false, null);
    return r;
  }

}
