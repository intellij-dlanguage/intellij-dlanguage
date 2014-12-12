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
    if (t == CLASS_DECLARATION) {
      r = class_declaration(b, 0);
    }
    else if (t == CLASS_NAME) {
      r = class_name(b, 0);
    }
    else if (t == DOC_COMMENT) {
      r = doc_comment(b, 0);
    }
    else if (t == FIELD_DECLARATION) {
      r = field_declaration(b, 0);
    }
    else if (t == IDENTIFIER) {
      r = identifier(b, 0);
    }
    else if (t == INTERFACE_NAME) {
      r = interface_name(b, 0);
    }
    else if (t == METHOD_DECLARATION) {
      r = method_declaration(b, 0);
    }
    else if (t == MODIFIER) {
      r = modifier(b, 0);
    }
    else if (t == PACKAGE_NAME) {
      r = package_name(b, 0);
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
    else if (t == TYPE) {
      r = type(b, 0);
    }
    else if (t == TYPE_SPECIFIER) {
      r = type_specifier(b, 0);
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
  // "final"
  public static boolean field_declaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "field_declaration")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<field declaration>");
    r = consumeToken(b, "final");
    exit_section_(b, l, m, FIELD_DECLARATION, r, false, null);
    return r;
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
  // "statement"
  public static boolean statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "statement")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<statement>");
    r = consumeToken(b, "statement");
    exit_section_(b, l, m, STATEMENT, r, false, null);
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

}
