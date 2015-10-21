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
    if (t == IDENTIFIER) {
      r = Identifier(b, 0);
    }
    else if (t == STRING_LITERAL) {
      r = StringLiteral(b, 0);
    }
    else if (t == ADD_EXPRESSION) {
      r = addExpression(b, 0);
    }
    else if (t == ALIAS_DECLARATION) {
      r = aliasDeclaration(b, 0);
    }
    else if (t == ALIAS_INITIALIZER) {
      r = aliasInitializer(b, 0);
    }
    else if (t == ALIAS_THIS_DECLARATION) {
      r = aliasThisDeclaration(b, 0);
    }
    else if (t == ALIGN_ATTRIBUTE) {
      r = alignAttribute(b, 0);
    }
    else if (t == AND_AND_EXPRESSION) {
      r = andAndExpression(b, 0);
    }
    else if (t == AND_EXPRESSION) {
      r = andExpression(b, 0);
    }
    else if (t == ANONYMOUS_ENUM_DECLARATION) {
      r = anonymousEnumDeclaration(b, 0);
    }
    else if (t == ANONYMOUS_ENUM_MEMBER) {
      r = anonymousEnumMember(b, 0);
    }
    else if (t == ARGUMENT_LIST) {
      r = argumentList(b, 0);
    }
    else if (t == ARGUMENTS) {
      r = arguments(b, 0);
    }
    else if (t == ARRAY_INITIALIZER) {
      r = arrayInitializer(b, 0);
    }
    else if (t == ARRAY_LITERAL) {
      r = arrayLiteral(b, 0);
    }
    else if (t == ARRAY_MEMBER_INITIALIZATION) {
      r = arrayMemberInitialization(b, 0);
    }
    else if (t == ASM_ADD_EXP) {
      r = asmAddExp(b, 0);
    }
    else if (t == ASM_AND_EXP) {
      r = asmAndExp(b, 0);
    }
    else if (t == ASM_BR_EXP) {
      r = asmBrExp(b, 0);
    }
    else if (t == ASM_EQUAL_EXP) {
      r = asmEqualExp(b, 0);
    }
    else if (t == ASM_EXP) {
      r = asmExp(b, 0);
    }
    else if (t == ASM_INSTRUCTION) {
      r = asmInstruction(b, 0);
    }
    else if (t == ASM_LOG_AND_EXP) {
      r = asmLogAndExp(b, 0);
    }
    else if (t == ASM_LOG_OR_EXP) {
      r = asmLogOrExp(b, 0);
    }
    else if (t == ASM_MUL_EXP) {
      r = asmMulExp(b, 0);
    }
    else if (t == ASM_OR_EXP) {
      r = asmOrExp(b, 0);
    }
    else if (t == ASM_PRIMARY_EXP) {
      r = asmPrimaryExp(b, 0);
    }
    else if (t == ASM_REL_EXP) {
      r = asmRelExp(b, 0);
    }
    else if (t == ASM_SHIFT_EXP) {
      r = asmShiftExp(b, 0);
    }
    else if (t == ASM_STATEMENT) {
      r = asmStatement(b, 0);
    }
    else if (t == ASM_TYPE_PREFIX) {
      r = asmTypePrefix(b, 0);
    }
    else if (t == ASM_UNA_EXP) {
      r = asmUnaExp(b, 0);
    }
    else if (t == ASM_XOR_EXP) {
      r = asmXorExp(b, 0);
    }
    else if (t == ASSERT_EXPRESSION) {
      r = assertExpression(b, 0);
    }
    else if (t == ASSIGN_EXPRESSION) {
      r = assignExpression(b, 0);
    }
    else if (t == ASSIGN_OPERATOR) {
      r = assignOperator(b, 0);
    }
    else if (t == ASSOC_ARRAY_LITERAL) {
      r = assocArrayLiteral(b, 0);
    }
    else if (t == AT_ATTRIBUTE) {
      r = atAttribute(b, 0);
    }
    else if (t == ATTRIBUTE) {
      r = attribute(b, 0);
    }
    else if (t == ATTRIBUTE_DECLARATION) {
      r = attributeDeclaration(b, 0);
    }
    else if (t == AUTO_DECLARATION) {
      r = autoDeclaration(b, 0);
    }
    else if (t == BASE_CLASS) {
      r = baseClass(b, 0);
    }
    else if (t == BASE_CLASS_LIST) {
      r = baseClassList(b, 0);
    }
    else if (t == BLOCK_STATEMENT) {
      r = blockStatement(b, 0);
    }
    else if (t == BODY_STATEMENT) {
      r = bodyStatement(b, 0);
    }
    else if (t == BREAK_STATEMENT) {
      r = breakStatement(b, 0);
    }
    else if (t == BUILTIN_TYPE) {
      r = builtinType(b, 0);
    }
    else if (t == CASE_RANGE_STATEMENT) {
      r = caseRangeStatement(b, 0);
    }
    else if (t == CASE_STATEMENT) {
      r = caseStatement(b, 0);
    }
    else if (t == CAST_EXPRESSION) {
      r = castExpression(b, 0);
    }
    else if (t == CAST_QUALIFIER) {
      r = castQualifier(b, 0);
    }
    else if (t == CATCH) {
      r = catch_(b, 0);
    }
    else if (t == CATCHES) {
      r = catches(b, 0);
    }
    else if (t == CLASS_DECLARATION) {
      r = classDeclaration(b, 0);
    }
    else if (t == CMP_EXPRESSION) {
      r = cmpExpression(b, 0);
    }
    else if (t == COMPILE_CONDITION) {
      r = compileCondition(b, 0);
    }
    else if (t == CONDITIONAL_DECLARATION) {
      r = conditionalDeclaration(b, 0);
    }
    else if (t == CONDITIONAL_STATEMENT) {
      r = conditionalStatement(b, 0);
    }
    else if (t == CONSTRAINT) {
      r = constraint(b, 0);
    }
    else if (t == CONSTRUCTOR) {
      r = constructor(b, 0);
    }
    else if (t == CONTINUE_STATEMENT) {
      r = continueStatement(b, 0);
    }
    else if (t == DEBUG_CONDITION) {
      r = debugCondition(b, 0);
    }
    else if (t == DEBUG_SPECIFICATION) {
      r = debugSpecification(b, 0);
    }
    else if (t == DECLARATION) {
      r = declaration(b, 0);
    }
    else if (t == DECLARATION_2) {
      r = declaration2(b, 0);
    }
    else if (t == DECLARATION_OR_STATEMENT) {
      r = declarationOrStatement(b, 0);
    }
    else if (t == DECLARATIONS_AND_STATEMENTS) {
      r = declarationsAndStatements(b, 0);
    }
    else if (t == DECLARATOR) {
      r = declarator(b, 0);
    }
    else if (t == DEFAULT_STATEMENT) {
      r = defaultStatement(b, 0);
    }
    else if (t == DELETE_EXPRESSION) {
      r = deleteExpression(b, 0);
    }
    else if (t == DEPRECATED) {
      r = deprecated(b, 0);
    }
    else if (t == DESTRUCTOR) {
      r = destructor(b, 0);
    }
    else if (t == DO_STATEMENT) {
      r = doStatement(b, 0);
    }
    else if (t == ENUM_BODY) {
      r = enumBody(b, 0);
    }
    else if (t == ENUM_DECLARATION) {
      r = enumDeclaration(b, 0);
    }
    else if (t == ENUM_MEMBER) {
      r = enumMember(b, 0);
    }
    else if (t == EPONYMOUS_TEMPLATE_DECLARATION) {
      r = eponymousTemplateDeclaration(b, 0);
    }
    else if (t == EQUAL_EXPRESSION) {
      r = equalExpression(b, 0);
    }
    else if (t == EXPRESSION) {
      r = expression(b, 0);
    }
    else if (t == EXPRESSION_STATEMENT) {
      r = expressionStatement(b, 0);
    }
    else if (t == FINAL_SWITCH_STATEMENT) {
      r = finalSwitchStatement(b, 0);
    }
    else if (t == FINALLY) {
      r = finally_(b, 0);
    }
    else if (t == FOR_STATEMENT) {
      r = forStatement(b, 0);
    }
    else if (t == FOREACH_STATEMENT) {
      r = foreachStatement(b, 0);
    }
    else if (t == FOREACH_TYPE) {
      r = foreachType(b, 0);
    }
    else if (t == FOREACH_TYPE_LIST) {
      r = foreachTypeList(b, 0);
    }
    else if (t == FUNCTION_ATTRIBUTE) {
      r = functionAttribute(b, 0);
    }
    else if (t == FUNCTION_BODY) {
      r = functionBody(b, 0);
    }
    else if (t == FUNCTION_CALL_EXPRESSION) {
      r = functionCallExpression(b, 0);
    }
    else if (t == FUNCTION_CALL_STATEMENT) {
      r = functionCallStatement(b, 0);
    }
    else if (t == FUNCTION_DECLARATION) {
      r = functionDeclaration(b, 0);
    }
    else if (t == FUNCTION_LITERAL_EXPRESSION) {
      r = functionLiteralExpression(b, 0);
    }
    else if (t == GOTO_STATEMENT) {
      r = gotoStatement(b, 0);
    }
    else if (t == IDENTIFIER_CHAIN) {
      r = identifierChain(b, 0);
    }
    else if (t == IDENTIFIER_LIST) {
      r = identifierList(b, 0);
    }
    else if (t == IDENTIFIER_OR_TEMPLATE_CHAIN) {
      r = identifierOrTemplateChain(b, 0);
    }
    else if (t == IDENTIFIER_OR_TEMPLATE_INSTANCE) {
      r = identifierOrTemplateInstance(b, 0);
    }
    else if (t == IDENTITY_EXPRESSION) {
      r = identityExpression(b, 0);
    }
    else if (t == IF_CONDITION) {
      r = ifCondition(b, 0);
    }
    else if (t == IF_STATEMENT) {
      r = ifStatement(b, 0);
    }
    else if (t == IMPORT_BIND) {
      r = importBind(b, 0);
    }
    else if (t == IMPORT_BINDINGS) {
      r = importBindings(b, 0);
    }
    else if (t == IMPORT_DECLARATION) {
      r = importDeclaration(b, 0);
    }
    else if (t == IMPORT_EXPRESSION) {
      r = importExpression(b, 0);
    }
    else if (t == IN_EXPRESSION) {
      r = inExpression(b, 0);
    }
    else if (t == IN_STATEMENT) {
      r = inStatement(b, 0);
    }
    else if (t == INITIALIZER) {
      r = initializer(b, 0);
    }
    else if (t == INTERFACE_DECLARATION) {
      r = interfaceDeclaration(b, 0);
    }
    else if (t == INVARIANT) {
      r = invariant(b, 0);
    }
    else if (t == IS_EXPRESSION) {
      r = isExpression(b, 0);
    }
    else if (t == KEY_VALUE_PAIR) {
      r = keyValuePair(b, 0);
    }
    else if (t == KEY_VALUE_PAIRS) {
      r = keyValuePairs(b, 0);
    }
    else if (t == LABELED_STATEMENT) {
      r = labeledStatement(b, 0);
    }
    else if (t == LAMBDA_EXPRESSION) {
      r = lambdaExpression(b, 0);
    }
    else if (t == LAST_CATCH) {
      r = lastCatch(b, 0);
    }
    else if (t == LINKAGE_ATTRIBUTE) {
      r = linkageAttribute(b, 0);
    }
    else if (t == MEMBER_FUNCTION_ATTRIBUTE) {
      r = memberFunctionAttribute(b, 0);
    }
    else if (t == MIXIN_DECLARATION) {
      r = mixinDeclaration(b, 0);
    }
    else if (t == MIXIN_EXPRESSION) {
      r = mixinExpression(b, 0);
    }
    else if (t == MIXIN_TEMPLATE_DECLARATION) {
      r = mixinTemplateDeclaration(b, 0);
    }
    else if (t == MIXIN_TEMPLATE_NAME) {
      r = mixinTemplateName(b, 0);
    }
    else if (t == MODULE_DECLARATION) {
      r = moduleDeclaration(b, 0);
    }
    else if (t == MUL_EXPRESSION) {
      r = mulExpression(b, 0);
    }
    else if (t == NEW_ANON_CLASS_EXPRESSION) {
      r = newAnonClassExpression(b, 0);
    }
    else if (t == NEW_EXPRESSION) {
      r = newExpression(b, 0);
    }
    else if (t == NON_VOID_INITIALIZER) {
      r = nonVoidInitializer(b, 0);
    }
    else if (t == OPERANDS) {
      r = operands(b, 0);
    }
    else if (t == OR_EXPRESSION) {
      r = orExpression(b, 0);
    }
    else if (t == OR_OR_EXPRESSION) {
      r = orOrExpression(b, 0);
    }
    else if (t == OUT_STATEMENT) {
      r = outStatement(b, 0);
    }
    else if (t == PARAMETER) {
      r = parameter(b, 0);
    }
    else if (t == PARAMETER_ATTRIBUTE) {
      r = parameterAttribute(b, 0);
    }
    else if (t == PARAMETERS) {
      r = parameters(b, 0);
    }
    else if (t == POSTBLIT) {
      r = postblit(b, 0);
    }
    else if (t == POW_EXPRESSION) {
      r = powExpression(b, 0);
    }
    else if (t == PRAGMA_DECLARATION) {
      r = pragmaDeclaration(b, 0);
    }
    else if (t == PRAGMA_EXPRESSION) {
      r = pragmaExpression(b, 0);
    }
    else if (t == PRIMARY_EXPRESSION) {
      r = primaryExpression(b, 0);
    }
    else if (t == REGISTER) {
      r = register(b, 0);
    }
    else if (t == REL_EXPRESSION) {
      r = relExpression(b, 0);
    }
    else if (t == REL_OPERATOR) {
      r = relOperator(b, 0);
    }
    else if (t == RETURN_STATEMENT) {
      r = returnStatement(b, 0);
    }
    else if (t == SCOPE_GUARD_STATEMENT) {
      r = scopeGuardStatement(b, 0);
    }
    else if (t == SHARED_STATIC_CONSTRUCTOR) {
      r = sharedStaticConstructor(b, 0);
    }
    else if (t == SHARED_STATIC_DESTRUCTOR) {
      r = sharedStaticDestructor(b, 0);
    }
    else if (t == SHIFT_EXPRESSION) {
      r = shiftExpression(b, 0);
    }
    else if (t == SINGLE_IMPORT) {
      r = singleImport(b, 0);
    }
    else if (t == STATEMENT) {
      r = statement(b, 0);
    }
    else if (t == STATEMENT_NO_CASE_NO_DEFAULT) {
      r = statementNoCaseNoDefault(b, 0);
    }
    else if (t == STATIC_ASSERT_DECLARATION) {
      r = staticAssertDeclaration(b, 0);
    }
    else if (t == STATIC_ASSERT_STATEMENT) {
      r = staticAssertStatement(b, 0);
    }
    else if (t == STATIC_CONSTRUCTOR) {
      r = staticConstructor(b, 0);
    }
    else if (t == STATIC_DESTRUCTOR) {
      r = staticDestructor(b, 0);
    }
    else if (t == STATIC_IF_CONDITION) {
      r = staticIfCondition(b, 0);
    }
    else if (t == STORAGE_CLASS) {
      r = storageClass(b, 0);
    }
    else if (t == STRUCT_BODY) {
      r = structBody(b, 0);
    }
    else if (t == STRUCT_DECLARATION) {
      r = structDeclaration(b, 0);
    }
    else if (t == STRUCT_INITIALIZER) {
      r = structInitializer(b, 0);
    }
    else if (t == STRUCT_MEMBER_INITIALIZER) {
      r = structMemberInitializer(b, 0);
    }
    else if (t == STRUCT_MEMBER_INITIALIZERS) {
      r = structMemberInitializers(b, 0);
    }
    else if (t == SWITCH_STATEMENT) {
      r = switchStatement(b, 0);
    }
    else if (t == SYMBOL) {
      r = symbol(b, 0);
    }
    else if (t == SYNCHRONIZED_STATEMENT) {
      r = synchronizedStatement(b, 0);
    }
    else if (t == TEMPLATE_ALIAS_PARAMETER) {
      r = templateAliasParameter(b, 0);
    }
    else if (t == TEMPLATE_ARGUMENT) {
      r = templateArgument(b, 0);
    }
    else if (t == TEMPLATE_ARGUMENT_LIST) {
      r = templateArgumentList(b, 0);
    }
    else if (t == TEMPLATE_ARGUMENTS) {
      r = templateArguments(b, 0);
    }
    else if (t == TEMPLATE_DECLARATION) {
      r = templateDeclaration(b, 0);
    }
    else if (t == TEMPLATE_INSTANCE) {
      r = templateInstance(b, 0);
    }
    else if (t == TEMPLATE_MIXIN_EXPRESSION) {
      r = templateMixinExpression(b, 0);
    }
    else if (t == TEMPLATE_PARAMETER) {
      r = templateParameter(b, 0);
    }
    else if (t == TEMPLATE_PARAMETER_LIST) {
      r = templateParameterList(b, 0);
    }
    else if (t == TEMPLATE_PARAMETERS) {
      r = templateParameters(b, 0);
    }
    else if (t == TEMPLATE_SINGLE_ARGUMENT) {
      r = templateSingleArgument(b, 0);
    }
    else if (t == TEMPLATE_THIS_PARAMETER) {
      r = templateThisParameter(b, 0);
    }
    else if (t == TEMPLATE_TUPLE_PARAMETER) {
      r = templateTupleParameter(b, 0);
    }
    else if (t == TEMPLATE_TYPE_PARAMETER) {
      r = templateTypeParameter(b, 0);
    }
    else if (t == TEMPLATE_VALUE_PARAMETER) {
      r = templateValueParameter(b, 0);
    }
    else if (t == TEMPLATE_VALUE_PARAMETER_DEFAULT) {
      r = templateValueParameterDefault(b, 0);
    }
    else if (t == TERNARY_EXPRESSION) {
      r = ternaryExpression(b, 0);
    }
    else if (t == THROW_STATEMENT) {
      r = throwStatement(b, 0);
    }
    else if (t == TRAITS_EXPRESSION) {
      r = traitsExpression(b, 0);
    }
    else if (t == TRY_STATEMENT) {
      r = tryStatement(b, 0);
    }
    else if (t == TYPE) {
      r = type(b, 0);
    }
    else if (t == TYPE_2) {
      r = type2(b, 0);
    }
    else if (t == TYPE_CONSTRUCTOR) {
      r = typeConstructor(b, 0);
    }
    else if (t == TYPE_CONSTRUCTORS) {
      r = typeConstructors(b, 0);
    }
    else if (t == TYPE_SPECIALIZATION) {
      r = typeSpecialization(b, 0);
    }
    else if (t == TYPE_SUFFIX) {
      r = typeSuffix(b, 0);
    }
    else if (t == TYPEID_EXPRESSION) {
      r = typeidExpression(b, 0);
    }
    else if (t == TYPEOF_EXPRESSION) {
      r = typeofExpression(b, 0);
    }
    else if (t == UNARY_EXPRESSION) {
      r = unaryExpression(b, 0);
    }
    else if (t == UNION_DECLARATION) {
      r = unionDeclaration(b, 0);
    }
    else if (t == UNITTEST) {
      r = unittest(b, 0);
    }
    else if (t == VARIABLE_DECLARATION) {
      r = variableDeclaration(b, 0);
    }
    else if (t == VECTOR) {
      r = vector(b, 0);
    }
    else if (t == VERSION_CONDITION) {
      r = versionCondition(b, 0);
    }
    else if (t == VERSION_SPECIFICATION) {
      r = versionSpecification(b, 0);
    }
    else if (t == WHILE_STATEMENT) {
      r = whileStatement(b, 0);
    }
    else if (t == WITH_STATEMENT) {
      r = withStatement(b, 0);
    }
    else if (t == XOR_EXPRESSION) {
      r = xorExpression(b, 0);
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
  // ID
  public static boolean Identifier(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Identifier")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ID);
    exit_section_(b, m, IDENTIFIER, r);
    return r;
  }

  /* ********************************************************** */
  // WYSIWYG_STRING|ALTERNATE_WYSIWYG_STRING|DOUBLE_QUOTED_STRING|HEX_STRING|DELIMITED_STRING
  public static boolean StringLiteral(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "StringLiteral")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<string literal>");
    r = consumeToken(b, WYSIWYG_STRING);
    if (!r) r = consumeToken(b, ALTERNATE_WYSIWYG_STRING);
    if (!r) r = consumeToken(b, DOUBLE_QUOTED_STRING);
    if (!r) r = consumeToken(b, HEX_STRING);
    if (!r) r = consumeToken(b, DELIMITED_STRING);
    exit_section_(b, l, m, STRING_LITERAL, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // mulExpression [('+' | '-' | '~') addExpression]
  public static boolean addExpression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "addExpression")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<add expression>");
    r = mulExpression(b, l + 1);
    r = r && addExpression_1(b, l + 1);
    exit_section_(b, l, m, ADD_EXPRESSION, r, false, null);
    return r;
  }

  // [('+' | '-' | '~') addExpression]
  private static boolean addExpression_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "addExpression_1")) return false;
    addExpression_1_0(b, l + 1);
    return true;
  }

  // ('+' | '-' | '~') addExpression
  private static boolean addExpression_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "addExpression_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = addExpression_1_0_0(b, l + 1);
    r = r && addExpression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '+' | '-' | '~'
  private static boolean addExpression_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "addExpression_1_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_PLUS);
    if (!r) r = consumeToken(b, OP_MINUS);
    if (!r) r = consumeToken(b, OP_TILDA);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'alias' aliasInitializer ( ',' aliasInitializer )* ';' | 'alias' storageClass* type identifierList ';'
  public static boolean aliasDeclaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "aliasDeclaration")) return false;
    if (!nextTokenIs(b, KW_ALIAS)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = aliasDeclaration_0(b, l + 1);
    if (!r) r = aliasDeclaration_1(b, l + 1);
    exit_section_(b, m, ALIAS_DECLARATION, r);
    return r;
  }

  // 'alias' aliasInitializer ( ',' aliasInitializer )* ';'
  private static boolean aliasDeclaration_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "aliasDeclaration_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_ALIAS);
    r = r && aliasInitializer(b, l + 1);
    r = r && aliasDeclaration_0_2(b, l + 1);
    r = r && consumeToken(b, OP_SCOLON);
    exit_section_(b, m, null, r);
    return r;
  }

  // ( ',' aliasInitializer )*
  private static boolean aliasDeclaration_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "aliasDeclaration_0_2")) return false;
    int c = current_position_(b);
    while (true) {
      if (!aliasDeclaration_0_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "aliasDeclaration_0_2", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // ',' aliasInitializer
  private static boolean aliasDeclaration_0_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "aliasDeclaration_0_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_COMMA);
    r = r && aliasInitializer(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // 'alias' storageClass* type identifierList ';'
  private static boolean aliasDeclaration_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "aliasDeclaration_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_ALIAS);
    r = r && aliasDeclaration_1_1(b, l + 1);
    r = r && type(b, l + 1);
    r = r && identifierList(b, l + 1);
    r = r && consumeToken(b, OP_SCOLON);
    exit_section_(b, m, null, r);
    return r;
  }

  // storageClass*
  private static boolean aliasDeclaration_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "aliasDeclaration_1_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!storageClass(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "aliasDeclaration_1_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  /* ********************************************************** */
  // Identifier templateParameters? '=' storageClass* type
  public static boolean aliasInitializer(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "aliasInitializer")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Identifier(b, l + 1);
    r = r && aliasInitializer_1(b, l + 1);
    r = r && consumeToken(b, OP_EQ);
    r = r && aliasInitializer_3(b, l + 1);
    r = r && type(b, l + 1);
    exit_section_(b, m, ALIAS_INITIALIZER, r);
    return r;
  }

  // templateParameters?
  private static boolean aliasInitializer_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "aliasInitializer_1")) return false;
    templateParameters(b, l + 1);
    return true;
  }

  // storageClass*
  private static boolean aliasInitializer_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "aliasInitializer_3")) return false;
    int c = current_position_(b);
    while (true) {
      if (!storageClass(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "aliasInitializer_3", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  /* ********************************************************** */
  // 'alias' Identifier 'this' ';'
  public static boolean aliasThisDeclaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "aliasThisDeclaration")) return false;
    if (!nextTokenIs(b, KW_ALIAS)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_ALIAS);
    r = r && Identifier(b, l + 1);
    r = r && consumeToken(b, KW_THIS);
    r = r && consumeToken(b, OP_SCOLON);
    exit_section_(b, m, ALIAS_THIS_DECLARATION, r);
    return r;
  }

  /* ********************************************************** */
  // 'align' ( '(' INTEGER_LITERAL ')' )?
  public static boolean alignAttribute(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "alignAttribute")) return false;
    if (!nextTokenIs(b, KW_ALIGN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_ALIGN);
    r = r && alignAttribute_1(b, l + 1);
    exit_section_(b, m, ALIGN_ATTRIBUTE, r);
    return r;
  }

  // ( '(' INTEGER_LITERAL ')' )?
  private static boolean alignAttribute_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "alignAttribute_1")) return false;
    alignAttribute_1_0(b, l + 1);
    return true;
  }

  // '(' INTEGER_LITERAL ')'
  private static boolean alignAttribute_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "alignAttribute_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_PAR_LEFT);
    r = r && consumeToken(b, INTEGER_LITERAL);
    r = r && consumeToken(b, OP_PAR_RIGHT);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // orExpression ['&&' andAndExpression]
  public static boolean andAndExpression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "andAndExpression")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<and and expression>");
    r = orExpression(b, l + 1);
    r = r && andAndExpression_1(b, l + 1);
    exit_section_(b, l, m, AND_AND_EXPRESSION, r, false, null);
    return r;
  }

  // ['&&' andAndExpression]
  private static boolean andAndExpression_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "andAndExpression_1")) return false;
    andAndExpression_1_0(b, l + 1);
    return true;
  }

  // '&&' andAndExpression
  private static boolean andAndExpression_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "andAndExpression_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_BOOL_AND);
    r = r && andAndExpression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // cmpExpression ['&' andExpression]
  public static boolean andExpression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "andExpression")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<and expression>");
    r = cmpExpression(b, l + 1);
    r = r && andExpression_1(b, l + 1);
    exit_section_(b, l, m, AND_EXPRESSION, r, false, null);
    return r;
  }

  // ['&' andExpression]
  private static boolean andExpression_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "andExpression_1")) return false;
    andExpression_1_0(b, l + 1);
    return true;
  }

  // '&' andExpression
  private static boolean andExpression_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "andExpression_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_AND);
    r = r && andExpression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'enum' ( ':' type )? '{' anonymousEnumMember '}'
  public static boolean anonymousEnumDeclaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "anonymousEnumDeclaration")) return false;
    if (!nextTokenIs(b, KW_ENUM)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_ENUM);
    r = r && anonymousEnumDeclaration_1(b, l + 1);
    r = r && consumeToken(b, OP_BRACES_LEFT);
    r = r && anonymousEnumMember(b, l + 1);
    r = r && consumeToken(b, OP_BRACES_RIGHT);
    exit_section_(b, m, ANONYMOUS_ENUM_DECLARATION, r);
    return r;
  }

  // ( ':' type )?
  private static boolean anonymousEnumDeclaration_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "anonymousEnumDeclaration_1")) return false;
    anonymousEnumDeclaration_1_0(b, l + 1);
    return true;
  }

  // ':' type
  private static boolean anonymousEnumDeclaration_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "anonymousEnumDeclaration_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_COLON);
    r = r && type(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // type Identifier '=' assignExpression | Identifier '=' assignExpression | Identifier
  public static boolean anonymousEnumMember(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "anonymousEnumMember")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<anonymous enum member>");
    r = anonymousEnumMember_0(b, l + 1);
    if (!r) r = anonymousEnumMember_1(b, l + 1);
    if (!r) r = Identifier(b, l + 1);
    exit_section_(b, l, m, ANONYMOUS_ENUM_MEMBER, r, false, null);
    return r;
  }

  // type Identifier '=' assignExpression
  private static boolean anonymousEnumMember_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "anonymousEnumMember_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = type(b, l + 1);
    r = r && Identifier(b, l + 1);
    r = r && consumeToken(b, OP_EQ);
    r = r && assignExpression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // Identifier '=' assignExpression
  private static boolean anonymousEnumMember_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "anonymousEnumMember_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Identifier(b, l + 1);
    r = r && consumeToken(b, OP_EQ);
    r = r && assignExpression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // assignExpression ( ',' assignExpression? )*
  public static boolean argumentList(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "argumentList")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<argument list>");
    r = assignExpression(b, l + 1);
    r = r && argumentList_1(b, l + 1);
    exit_section_(b, l, m, ARGUMENT_LIST, r, false, null);
    return r;
  }

  // ( ',' assignExpression? )*
  private static boolean argumentList_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "argumentList_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!argumentList_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "argumentList_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // ',' assignExpression?
  private static boolean argumentList_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "argumentList_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_COMMA);
    r = r && argumentList_1_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // assignExpression?
  private static boolean argumentList_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "argumentList_1_0_1")) return false;
    assignExpression(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // '(' argumentList? ')'
  public static boolean arguments(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "arguments")) return false;
    if (!nextTokenIs(b, OP_PAR_LEFT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_PAR_LEFT);
    r = r && arguments_1(b, l + 1);
    r = r && consumeToken(b, OP_PAR_RIGHT);
    exit_section_(b, m, ARGUMENTS, r);
    return r;
  }

  // argumentList?
  private static boolean arguments_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "arguments_1")) return false;
    argumentList(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // '[' ']' | '[' arrayMemberInitialization ( ',' arrayMemberInitialization? )* ']'
  public static boolean arrayInitializer(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "arrayInitializer")) return false;
    if (!nextTokenIs(b, OP_BRACKET_LEFT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = arrayInitializer_0(b, l + 1);
    if (!r) r = arrayInitializer_1(b, l + 1);
    exit_section_(b, m, ARRAY_INITIALIZER, r);
    return r;
  }

  // '[' ']'
  private static boolean arrayInitializer_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "arrayInitializer_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_BRACKET_LEFT);
    r = r && consumeToken(b, OP_BRACKET_RIGHT);
    exit_section_(b, m, null, r);
    return r;
  }

  // '[' arrayMemberInitialization ( ',' arrayMemberInitialization? )* ']'
  private static boolean arrayInitializer_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "arrayInitializer_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_BRACKET_LEFT);
    r = r && arrayMemberInitialization(b, l + 1);
    r = r && arrayInitializer_1_2(b, l + 1);
    r = r && consumeToken(b, OP_BRACKET_RIGHT);
    exit_section_(b, m, null, r);
    return r;
  }

  // ( ',' arrayMemberInitialization? )*
  private static boolean arrayInitializer_1_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "arrayInitializer_1_2")) return false;
    int c = current_position_(b);
    while (true) {
      if (!arrayInitializer_1_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "arrayInitializer_1_2", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // ',' arrayMemberInitialization?
  private static boolean arrayInitializer_1_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "arrayInitializer_1_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_COMMA);
    r = r && arrayInitializer_1_2_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // arrayMemberInitialization?
  private static boolean arrayInitializer_1_2_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "arrayInitializer_1_2_0_1")) return false;
    arrayMemberInitialization(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // '[' argumentList? ']'
  public static boolean arrayLiteral(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "arrayLiteral")) return false;
    if (!nextTokenIs(b, OP_BRACKET_LEFT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_BRACKET_LEFT);
    r = r && arrayLiteral_1(b, l + 1);
    r = r && consumeToken(b, OP_BRACKET_RIGHT);
    exit_section_(b, m, ARRAY_LITERAL, r);
    return r;
  }

  // argumentList?
  private static boolean arrayLiteral_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "arrayLiteral_1")) return false;
    argumentList(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // ( assignExpression ':' )? nonVoidInitializer
  public static boolean arrayMemberInitialization(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "arrayMemberInitialization")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<array member initialization>");
    r = arrayMemberInitialization_0(b, l + 1);
    r = r && nonVoidInitializer(b, l + 1);
    exit_section_(b, l, m, ARRAY_MEMBER_INITIALIZATION, r, false, null);
    return r;
  }

  // ( assignExpression ':' )?
  private static boolean arrayMemberInitialization_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "arrayMemberInitialization_0")) return false;
    arrayMemberInitialization_0_0(b, l + 1);
    return true;
  }

  // assignExpression ':'
  private static boolean arrayMemberInitialization_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "arrayMemberInitialization_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = assignExpression(b, l + 1);
    r = r && consumeToken(b, OP_COLON);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // asmMulExp [( '+' | '-' ) asmAddExp]
  public static boolean asmAddExp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmAddExp")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<asm add exp>");
    r = asmMulExp(b, l + 1);
    r = r && asmAddExp_1(b, l + 1);
    exit_section_(b, l, m, ASM_ADD_EXP, r, false, null);
    return r;
  }

  // [( '+' | '-' ) asmAddExp]
  private static boolean asmAddExp_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmAddExp_1")) return false;
    asmAddExp_1_0(b, l + 1);
    return true;
  }

  // ( '+' | '-' ) asmAddExp
  private static boolean asmAddExp_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmAddExp_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = asmAddExp_1_0_0(b, l + 1);
    r = r && asmAddExp(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '+' | '-'
  private static boolean asmAddExp_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmAddExp_1_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_PLUS);
    if (!r) r = consumeToken(b, OP_MINUS);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // asmEqualExp ['&' asmAndExp]
  public static boolean asmAndExp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmAndExp")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<asm and exp>");
    r = asmEqualExp(b, l + 1);
    r = r && asmAndExp_1(b, l + 1);
    exit_section_(b, l, m, ASM_AND_EXP, r, false, null);
    return r;
  }

  // ['&' asmAndExp]
  private static boolean asmAndExp_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmAndExp_1")) return false;
    asmAndExp_1_0(b, l + 1);
    return true;
  }

  // '&' asmAndExp
  private static boolean asmAndExp_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmAndExp_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_AND);
    r = r && asmAndExp(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // asmUnaExp [ '[' asmExp ']' asmBrExp?]
  public static boolean asmBrExp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmBrExp")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<asm br exp>");
    r = asmUnaExp(b, l + 1);
    r = r && asmBrExp_1(b, l + 1);
    exit_section_(b, l, m, ASM_BR_EXP, r, false, null);
    return r;
  }

  // [ '[' asmExp ']' asmBrExp?]
  private static boolean asmBrExp_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmBrExp_1")) return false;
    asmBrExp_1_0(b, l + 1);
    return true;
  }

  // '[' asmExp ']' asmBrExp?
  private static boolean asmBrExp_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmBrExp_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_BRACKET_LEFT);
    r = r && asmExp(b, l + 1);
    r = r && consumeToken(b, OP_BRACKET_RIGHT);
    r = r && asmBrExp_1_0_3(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // asmBrExp?
  private static boolean asmBrExp_1_0_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmBrExp_1_0_3")) return false;
    asmBrExp(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // asmRelExp | ('==' | '!=') asmRelExp
  public static boolean asmEqualExp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmEqualExp")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<asm equal exp>");
    r = asmRelExp(b, l + 1);
    if (!r) r = asmEqualExp_1(b, l + 1);
    exit_section_(b, l, m, ASM_EQUAL_EXP, r, false, null);
    return r;
  }

  // ('==' | '!=') asmRelExp
  private static boolean asmEqualExp_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmEqualExp_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = asmEqualExp_1_0(b, l + 1);
    r = r && asmRelExp(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '==' | '!='
  private static boolean asmEqualExp_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmEqualExp_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_EQ_EQ);
    if (!r) r = consumeToken(b, OP_NOT_EQ);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // asmLogOrExp ( '?' asmExp ':' asmExp )?
  public static boolean asmExp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmExp")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<asm exp>");
    r = asmLogOrExp(b, l + 1);
    r = r && asmExp_1(b, l + 1);
    exit_section_(b, l, m, ASM_EXP, r, false, null);
    return r;
  }

  // ( '?' asmExp ':' asmExp )?
  private static boolean asmExp_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmExp_1")) return false;
    asmExp_1_0(b, l + 1);
    return true;
  }

  // '?' asmExp ':' asmExp
  private static boolean asmExp_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmExp_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_QUEST);
    r = r && asmExp(b, l + 1);
    r = r && consumeToken(b, OP_COLON);
    r = r && asmExp(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // Identifier | 'align' INTEGER_LITERAL | 'align' Identifier | Identifier ':' asmInstruction | Identifier operands | 'in' operands | 'out' operands | 'int' operands
  public static boolean asmInstruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmInstruction")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<asm instruction>");
    r = Identifier(b, l + 1);
    if (!r) r = asmInstruction_1(b, l + 1);
    if (!r) r = asmInstruction_2(b, l + 1);
    if (!r) r = asmInstruction_3(b, l + 1);
    if (!r) r = asmInstruction_4(b, l + 1);
    if (!r) r = asmInstruction_5(b, l + 1);
    if (!r) r = asmInstruction_6(b, l + 1);
    if (!r) r = asmInstruction_7(b, l + 1);
    exit_section_(b, l, m, ASM_INSTRUCTION, r, false, null);
    return r;
  }

  // 'align' INTEGER_LITERAL
  private static boolean asmInstruction_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmInstruction_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_ALIGN);
    r = r && consumeToken(b, INTEGER_LITERAL);
    exit_section_(b, m, null, r);
    return r;
  }

  // 'align' Identifier
  private static boolean asmInstruction_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmInstruction_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_ALIGN);
    r = r && Identifier(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // Identifier ':' asmInstruction
  private static boolean asmInstruction_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmInstruction_3")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Identifier(b, l + 1);
    r = r && consumeToken(b, OP_COLON);
    r = r && asmInstruction(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // Identifier operands
  private static boolean asmInstruction_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmInstruction_4")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Identifier(b, l + 1);
    r = r && operands(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // 'in' operands
  private static boolean asmInstruction_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmInstruction_5")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_IN);
    r = r && operands(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // 'out' operands
  private static boolean asmInstruction_6(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmInstruction_6")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_OUT);
    r = r && operands(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // 'int' operands
  private static boolean asmInstruction_7(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmInstruction_7")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_INT);
    r = r && operands(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // asmOrExp asmLogAndExp '&&' asmOrExp
  public static boolean asmLogAndExp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmLogAndExp")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<asm log and exp>");
    r = asmOrExp(b, l + 1);
    r = r && asmLogAndExp(b, l + 1);
    r = r && consumeToken(b, OP_BOOL_AND);
    r = r && asmOrExp(b, l + 1);
    exit_section_(b, l, m, ASM_LOG_AND_EXP, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // asmLogAndExp [ '||' asmLogOrExp ]
  public static boolean asmLogOrExp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmLogOrExp")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<asm log or exp>");
    r = asmLogAndExp(b, l + 1);
    r = r && asmLogOrExp_1(b, l + 1);
    exit_section_(b, l, m, ASM_LOG_OR_EXP, r, false, null);
    return r;
  }

  // [ '||' asmLogOrExp ]
  private static boolean asmLogOrExp_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmLogOrExp_1")) return false;
    asmLogOrExp_1_0(b, l + 1);
    return true;
  }

  // '||' asmLogOrExp
  private static boolean asmLogOrExp_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmLogOrExp_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_BOOL_OR);
    r = r && asmLogOrExp(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // asmBrExp [( '*' | '/' | '%' ) asmMulExp]
  public static boolean asmMulExp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmMulExp")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<asm mul exp>");
    r = asmBrExp(b, l + 1);
    r = r && asmMulExp_1(b, l + 1);
    exit_section_(b, l, m, ASM_MUL_EXP, r, false, null);
    return r;
  }

  // [( '*' | '/' | '%' ) asmMulExp]
  private static boolean asmMulExp_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmMulExp_1")) return false;
    asmMulExp_1_0(b, l + 1);
    return true;
  }

  // ( '*' | '/' | '%' ) asmMulExp
  private static boolean asmMulExp_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmMulExp_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = asmMulExp_1_0_0(b, l + 1);
    r = r && asmMulExp(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '*' | '/' | '%'
  private static boolean asmMulExp_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmMulExp_1_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_ASTERISK);
    if (!r) r = consumeToken(b, OP_DIV);
    if (!r) r = consumeToken(b, OP_MOD);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // asmXorExp ['|' asmXorExp]
  public static boolean asmOrExp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmOrExp")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<asm or exp>");
    r = asmXorExp(b, l + 1);
    r = r && asmOrExp_1(b, l + 1);
    exit_section_(b, l, m, ASM_OR_EXP, r, false, null);
    return r;
  }

  // ['|' asmXorExp]
  private static boolean asmOrExp_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmOrExp_1")) return false;
    asmOrExp_1_0(b, l + 1);
    return true;
  }

  // '|' asmXorExp
  private static boolean asmOrExp_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmOrExp_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_OR);
    r = r && asmXorExp(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // INTEGER_LITERAL | FLOAT_LITERAL | StringLiteral | register | identifierChain | '$'
  public static boolean asmPrimaryExp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmPrimaryExp")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<asm primary exp>");
    r = consumeToken(b, INTEGER_LITERAL);
    if (!r) r = consumeToken(b, FLOAT_LITERAL);
    if (!r) r = StringLiteral(b, l + 1);
    if (!r) r = register(b, l + 1);
    if (!r) r = identifierChain(b, l + 1);
    if (!r) r = consumeToken(b, OP_DOLLAR);
    exit_section_(b, l, m, ASM_PRIMARY_EXP, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // asmShiftExp (('<' | '<=' | '>' | '>=') asmShiftExp)?
  public static boolean asmRelExp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmRelExp")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<asm rel exp>");
    r = asmShiftExp(b, l + 1);
    r = r && asmRelExp_1(b, l + 1);
    exit_section_(b, l, m, ASM_REL_EXP, r, false, null);
    return r;
  }

  // (('<' | '<=' | '>' | '>=') asmShiftExp)?
  private static boolean asmRelExp_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmRelExp_1")) return false;
    asmRelExp_1_0(b, l + 1);
    return true;
  }

  // ('<' | '<=' | '>' | '>=') asmShiftExp
  private static boolean asmRelExp_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmRelExp_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = asmRelExp_1_0_0(b, l + 1);
    r = r && asmShiftExp(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '<' | '<=' | '>' | '>='
  private static boolean asmRelExp_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmRelExp_1_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_LESS);
    if (!r) r = consumeToken(b, OP_LESS_EQ);
    if (!r) r = consumeToken(b, OP_GT);
    if (!r) r = consumeToken(b, OP_GT_EQ);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // asmAddExp (('<<' | '>>' | '>>>') asmAddExp)?
  public static boolean asmShiftExp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmShiftExp")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<asm shift exp>");
    r = asmAddExp(b, l + 1);
    r = r && asmShiftExp_1(b, l + 1);
    exit_section_(b, l, m, ASM_SHIFT_EXP, r, false, null);
    return r;
  }

  // (('<<' | '>>' | '>>>') asmAddExp)?
  private static boolean asmShiftExp_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmShiftExp_1")) return false;
    asmShiftExp_1_0(b, l + 1);
    return true;
  }

  // ('<<' | '>>' | '>>>') asmAddExp
  private static boolean asmShiftExp_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmShiftExp_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = asmShiftExp_1_0_0(b, l + 1);
    r = r && asmAddExp(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '<<' | '>>' | '>>>'
  private static boolean asmShiftExp_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmShiftExp_1_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_SH_LEFT);
    if (!r) r = consumeToken(b, OP_SH_RIGHT);
    if (!r) r = consumeToken(b, OP_USH_RIGHT);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'asm' '{' asmInstruction+ '}'
  public static boolean asmStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmStatement")) return false;
    if (!nextTokenIs(b, KW_ASM)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_ASM);
    r = r && consumeToken(b, OP_BRACES_LEFT);
    r = r && asmStatement_2(b, l + 1);
    r = r && consumeToken(b, OP_BRACES_RIGHT);
    exit_section_(b, m, ASM_STATEMENT, r);
    return r;
  }

  // asmInstruction+
  private static boolean asmStatement_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmStatement_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = asmInstruction(b, l + 1);
    int c = current_position_(b);
    while (r) {
      if (!asmInstruction(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "asmStatement_2", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // Identifier Identifier? | 'byte' Identifier? | 'short' Identifier? | 'int' Identifier? | 'float' Identifier? | 'double' Identifier? | 'real' Identifier?
  public static boolean asmTypePrefix(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmTypePrefix")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<asm type prefix>");
    r = asmTypePrefix_0(b, l + 1);
    if (!r) r = asmTypePrefix_1(b, l + 1);
    if (!r) r = asmTypePrefix_2(b, l + 1);
    if (!r) r = asmTypePrefix_3(b, l + 1);
    if (!r) r = asmTypePrefix_4(b, l + 1);
    if (!r) r = asmTypePrefix_5(b, l + 1);
    if (!r) r = asmTypePrefix_6(b, l + 1);
    exit_section_(b, l, m, ASM_TYPE_PREFIX, r, false, null);
    return r;
  }

  // Identifier Identifier?
  private static boolean asmTypePrefix_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmTypePrefix_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Identifier(b, l + 1);
    r = r && asmTypePrefix_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // Identifier?
  private static boolean asmTypePrefix_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmTypePrefix_0_1")) return false;
    Identifier(b, l + 1);
    return true;
  }

  // 'byte' Identifier?
  private static boolean asmTypePrefix_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmTypePrefix_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_BYTE);
    r = r && asmTypePrefix_1_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // Identifier?
  private static boolean asmTypePrefix_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmTypePrefix_1_1")) return false;
    Identifier(b, l + 1);
    return true;
  }

  // 'short' Identifier?
  private static boolean asmTypePrefix_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmTypePrefix_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_SHORT);
    r = r && asmTypePrefix_2_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // Identifier?
  private static boolean asmTypePrefix_2_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmTypePrefix_2_1")) return false;
    Identifier(b, l + 1);
    return true;
  }

  // 'int' Identifier?
  private static boolean asmTypePrefix_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmTypePrefix_3")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_INT);
    r = r && asmTypePrefix_3_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // Identifier?
  private static boolean asmTypePrefix_3_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmTypePrefix_3_1")) return false;
    Identifier(b, l + 1);
    return true;
  }

  // 'float' Identifier?
  private static boolean asmTypePrefix_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmTypePrefix_4")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_FLOAT);
    r = r && asmTypePrefix_4_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // Identifier?
  private static boolean asmTypePrefix_4_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmTypePrefix_4_1")) return false;
    Identifier(b, l + 1);
    return true;
  }

  // 'double' Identifier?
  private static boolean asmTypePrefix_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmTypePrefix_5")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_DOUBLE);
    r = r && asmTypePrefix_5_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // Identifier?
  private static boolean asmTypePrefix_5_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmTypePrefix_5_1")) return false;
    Identifier(b, l + 1);
    return true;
  }

  // 'real' Identifier?
  private static boolean asmTypePrefix_6(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmTypePrefix_6")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_REAL);
    r = r && asmTypePrefix_6_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // Identifier?
  private static boolean asmTypePrefix_6_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmTypePrefix_6_1")) return false;
    Identifier(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // asmTypePrefix asmExp | Identifier asmExp | '+' asmUnaExp | '-' asmUnaExp | '!' asmUnaExp | '~' asmUnaExp | asmPrimaryExp
  public static boolean asmUnaExp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmUnaExp")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<asm una exp>");
    r = asmUnaExp_0(b, l + 1);
    if (!r) r = asmUnaExp_1(b, l + 1);
    if (!r) r = asmUnaExp_2(b, l + 1);
    if (!r) r = asmUnaExp_3(b, l + 1);
    if (!r) r = asmUnaExp_4(b, l + 1);
    if (!r) r = asmUnaExp_5(b, l + 1);
    if (!r) r = asmPrimaryExp(b, l + 1);
    exit_section_(b, l, m, ASM_UNA_EXP, r, false, null);
    return r;
  }

  // asmTypePrefix asmExp
  private static boolean asmUnaExp_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmUnaExp_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = asmTypePrefix(b, l + 1);
    r = r && asmExp(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // Identifier asmExp
  private static boolean asmUnaExp_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmUnaExp_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Identifier(b, l + 1);
    r = r && asmExp(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '+' asmUnaExp
  private static boolean asmUnaExp_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmUnaExp_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_PLUS);
    r = r && asmUnaExp(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '-' asmUnaExp
  private static boolean asmUnaExp_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmUnaExp_3")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_MINUS);
    r = r && asmUnaExp(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '!' asmUnaExp
  private static boolean asmUnaExp_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmUnaExp_4")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_NOT);
    r = r && asmUnaExp(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '~' asmUnaExp
  private static boolean asmUnaExp_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmUnaExp_5")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_TILDA);
    r = r && asmUnaExp(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // asmAndExp ['^' asmXorExp]
  public static boolean asmXorExp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmXorExp")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<asm xor exp>");
    r = asmAndExp(b, l + 1);
    r = r && asmXorExp_1(b, l + 1);
    exit_section_(b, l, m, ASM_XOR_EXP, r, false, null);
    return r;
  }

  // ['^' asmXorExp]
  private static boolean asmXorExp_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmXorExp_1")) return false;
    asmXorExp_1_0(b, l + 1);
    return true;
  }

  // '^' asmXorExp
  private static boolean asmXorExp_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmXorExp_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_XOR);
    r = r && asmXorExp(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'assert' '(' assignExpression ( ',' assignExpression )? ')'
  public static boolean assertExpression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "assertExpression")) return false;
    if (!nextTokenIs(b, KW_ASSERT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_ASSERT);
    r = r && consumeToken(b, OP_PAR_LEFT);
    r = r && assignExpression(b, l + 1);
    r = r && assertExpression_3(b, l + 1);
    r = r && consumeToken(b, OP_PAR_RIGHT);
    exit_section_(b, m, ASSERT_EXPRESSION, r);
    return r;
  }

  // ( ',' assignExpression )?
  private static boolean assertExpression_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "assertExpression_3")) return false;
    assertExpression_3_0(b, l + 1);
    return true;
  }

  // ',' assignExpression
  private static boolean assertExpression_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "assertExpression_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_COMMA);
    r = r && assignExpression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // ternaryExpression ( assignOperator expression )?
  public static boolean assignExpression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "assignExpression")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<assign expression>");
    r = ternaryExpression(b, l + 1);
    r = r && assignExpression_1(b, l + 1);
    exit_section_(b, l, m, ASSIGN_EXPRESSION, r, false, null);
    return r;
  }

  // ( assignOperator expression )?
  private static boolean assignExpression_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "assignExpression_1")) return false;
    assignExpression_1_0(b, l + 1);
    return true;
  }

  // assignOperator expression
  private static boolean assignExpression_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "assignExpression_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = assignOperator(b, l + 1);
    r = r && expression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // '='
  //      | '>>>='
  //      | '>>='
  //      | '<<='
  //      | '+='
  //      | '-='
  //      | '*='
  //      | '%='
  //      | '&='
  //      | '/='
  //      | '|='
  //      | '^^='
  //      | '^='
  //      | '~='
  public static boolean assignOperator(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "assignOperator")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<assign operator>");
    r = consumeToken(b, OP_EQ);
    if (!r) r = consumeToken(b, OP_USH_RIGHT_EQ);
    if (!r) r = consumeToken(b, OP_SH_RIGHT_EQ);
    if (!r) r = consumeToken(b, OP_SH_LEFT_EQ);
    if (!r) r = consumeToken(b, OP_PLUS_EQ);
    if (!r) r = consumeToken(b, OP_MINUS_EQ);
    if (!r) r = consumeToken(b, OP_MUL_EQ);
    if (!r) r = consumeToken(b, OP_MOD_EQ);
    if (!r) r = consumeToken(b, OP_AND_EQ);
    if (!r) r = consumeToken(b, OP_DIV_EQ);
    if (!r) r = consumeToken(b, OP_OR_EQ);
    if (!r) r = consumeToken(b, OP_POW_EQ);
    if (!r) r = consumeToken(b, OP_XOR_EQ);
    if (!r) r = consumeToken(b, OP_TILDA_EQ);
    exit_section_(b, l, m, ASSIGN_OPERATOR, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // '[' keyValuePairs ']'
  public static boolean assocArrayLiteral(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "assocArrayLiteral")) return false;
    if (!nextTokenIs(b, OP_BRACKET_LEFT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_BRACKET_LEFT);
    r = r && keyValuePairs(b, l + 1);
    r = r && consumeToken(b, OP_BRACKET_RIGHT);
    exit_section_(b, m, ASSOC_ARRAY_LITERAL, r);
    return r;
  }

  /* ********************************************************** */
  // '@' Identifier | '@' Identifier '(' argumentList? ')' | '@' '(' argumentList ')'
  public static boolean atAttribute(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "atAttribute")) return false;
    if (!nextTokenIs(b, OP_AT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = atAttribute_0(b, l + 1);
    if (!r) r = atAttribute_1(b, l + 1);
    if (!r) r = atAttribute_2(b, l + 1);
    exit_section_(b, m, AT_ATTRIBUTE, r);
    return r;
  }

  // '@' Identifier
  private static boolean atAttribute_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "atAttribute_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_AT);
    r = r && Identifier(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '@' Identifier '(' argumentList? ')'
  private static boolean atAttribute_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "atAttribute_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_AT);
    r = r && Identifier(b, l + 1);
    r = r && consumeToken(b, OP_PAR_LEFT);
    r = r && atAttribute_1_3(b, l + 1);
    r = r && consumeToken(b, OP_PAR_RIGHT);
    exit_section_(b, m, null, r);
    return r;
  }

  // argumentList?
  private static boolean atAttribute_1_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "atAttribute_1_3")) return false;
    argumentList(b, l + 1);
    return true;
  }

  // '@' '(' argumentList ')'
  private static boolean atAttribute_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "atAttribute_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_AT);
    r = r && consumeToken(b, OP_PAR_LEFT);
    r = r && argumentList(b, l + 1);
    r = r && consumeToken(b, OP_PAR_RIGHT);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // pragmaExpression
  // | alignAttribute
  // | deprecated
  // | atAttribute
  // | linkageAttribute
  // | 'export'
  // | 'package'
  // | 'private'
  // | 'protected'
  // | 'public'
  // | 'static'
  // | 'extern'
  // | 'abstract'
  // | 'final'
  // | 'override'
  // | 'synchronized'
  // | 'auto'
  // | 'scope'
  // | 'const'
  // | 'immutable'
  // | 'inout'
  // | 'shared'
  // | '_gshared'
  // | 'nothrow'
  // | 'pure'
  // | 'ref'
  public static boolean attribute(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "attribute")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<attribute>");
    r = pragmaExpression(b, l + 1);
    if (!r) r = alignAttribute(b, l + 1);
    if (!r) r = deprecated(b, l + 1);
    if (!r) r = atAttribute(b, l + 1);
    if (!r) r = linkageAttribute(b, l + 1);
    if (!r) r = consumeToken(b, KW_EXPORT);
    if (!r) r = consumeToken(b, KW_PACKAGE);
    if (!r) r = consumeToken(b, KW_PRIVATE);
    if (!r) r = consumeToken(b, KW_PROTECTED);
    if (!r) r = consumeToken(b, KW_PUBLIC);
    if (!r) r = consumeToken(b, KW_STATIC);
    if (!r) r = consumeToken(b, KW_EXTERN);
    if (!r) r = consumeToken(b, KW_ABSTRACT);
    if (!r) r = consumeToken(b, KW_FINAL);
    if (!r) r = consumeToken(b, KW_OVERRIDE);
    if (!r) r = consumeToken(b, KW_SYNCHRONIZED);
    if (!r) r = consumeToken(b, KW_AUTO);
    if (!r) r = consumeToken(b, KW_SCOPE);
    if (!r) r = consumeToken(b, KW_CONST);
    if (!r) r = consumeToken(b, KW_IMMUTABLE);
    if (!r) r = consumeToken(b, KW_INOUT);
    if (!r) r = consumeToken(b, KW_SHARED);
    if (!r) r = consumeToken(b, "_gshared");
    if (!r) r = consumeToken(b, KW_NOTHROW);
    if (!r) r = consumeToken(b, KW_PURE);
    if (!r) r = consumeToken(b, KW_REF);
    exit_section_(b, l, m, ATTRIBUTE, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // attribute ':'
  public static boolean attributeDeclaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "attributeDeclaration")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<attribute declaration>");
    r = attribute(b, l + 1);
    r = r && consumeToken(b, OP_COLON);
    exit_section_(b, l, m, ATTRIBUTE_DECLARATION, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // storageClass Identifier '=' initializer ( ',' Identifier '=' initializer )* ';'
  public static boolean autoDeclaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "autoDeclaration")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<auto declaration>");
    r = storageClass(b, l + 1);
    r = r && Identifier(b, l + 1);
    r = r && consumeToken(b, OP_EQ);
    r = r && initializer(b, l + 1);
    r = r && autoDeclaration_4(b, l + 1);
    r = r && consumeToken(b, OP_SCOLON);
    exit_section_(b, l, m, AUTO_DECLARATION, r, false, null);
    return r;
  }

  // ( ',' Identifier '=' initializer )*
  private static boolean autoDeclaration_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "autoDeclaration_4")) return false;
    int c = current_position_(b);
    while (true) {
      if (!autoDeclaration_4_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "autoDeclaration_4", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // ',' Identifier '=' initializer
  private static boolean autoDeclaration_4_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "autoDeclaration_4_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_COMMA);
    r = r && Identifier(b, l + 1);
    r = r && consumeToken(b, OP_EQ);
    r = r && initializer(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // type2
  public static boolean baseClass(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "baseClass")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<base class>");
    r = type2(b, l + 1);
    exit_section_(b, l, m, BASE_CLASS, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // baseClass ( ',' baseClass )*
  public static boolean baseClassList(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "baseClassList")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<base class list>");
    r = baseClass(b, l + 1);
    r = r && baseClassList_1(b, l + 1);
    exit_section_(b, l, m, BASE_CLASS_LIST, r, false, null);
    return r;
  }

  // ( ',' baseClass )*
  private static boolean baseClassList_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "baseClassList_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!baseClassList_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "baseClassList_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // ',' baseClass
  private static boolean baseClassList_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "baseClassList_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_COMMA);
    r = r && baseClass(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // '{' declarationsAndStatements? '}'
  public static boolean blockStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "blockStatement")) return false;
    if (!nextTokenIs(b, OP_BRACES_LEFT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_BRACES_LEFT);
    r = r && blockStatement_1(b, l + 1);
    r = r && consumeToken(b, OP_BRACES_RIGHT);
    exit_section_(b, m, BLOCK_STATEMENT, r);
    return r;
  }

  // declarationsAndStatements?
  private static boolean blockStatement_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "blockStatement_1")) return false;
    declarationsAndStatements(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // 'body' blockStatement
  public static boolean bodyStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bodyStatement")) return false;
    if (!nextTokenIs(b, KW_BODY)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_BODY);
    r = r && blockStatement(b, l + 1);
    exit_section_(b, m, BODY_STATEMENT, r);
    return r;
  }

  /* ********************************************************** */
  // 'break' Identifier? ';'
  public static boolean breakStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "breakStatement")) return false;
    if (!nextTokenIs(b, KW_BREAK)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_BREAK);
    r = r && breakStatement_1(b, l + 1);
    r = r && consumeToken(b, OP_SCOLON);
    exit_section_(b, m, BREAK_STATEMENT, r);
    return r;
  }

  // Identifier?
  private static boolean breakStatement_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "breakStatement_1")) return false;
    Identifier(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // 'bool' | 'byte' | 'ubyte' | 'short' | 'ushort' | 'int' | 'uint' | 'long' | 'ulong' | 'char' | 'wchar' | 'dchar' | 'float' | 'double' | 'real' | 'ifloat' | 'idouble' | 'ireal' | 'cfloat' | 'cdouble' | 'creal' | 'void'
  public static boolean builtinType(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "builtinType")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<builtin type>");
    r = consumeToken(b, KW_BOOL);
    if (!r) r = consumeToken(b, KW_BYTE);
    if (!r) r = consumeToken(b, KW_UBYTE);
    if (!r) r = consumeToken(b, KW_SHORT);
    if (!r) r = consumeToken(b, KW_USHORT);
    if (!r) r = consumeToken(b, KW_INT);
    if (!r) r = consumeToken(b, KW_UINT);
    if (!r) r = consumeToken(b, KW_LONG);
    if (!r) r = consumeToken(b, KW_ULONG);
    if (!r) r = consumeToken(b, KW_CHAR);
    if (!r) r = consumeToken(b, KW_WCHAR);
    if (!r) r = consumeToken(b, KW_DCHAR);
    if (!r) r = consumeToken(b, KW_FLOAT);
    if (!r) r = consumeToken(b, KW_DOUBLE);
    if (!r) r = consumeToken(b, KW_REAL);
    if (!r) r = consumeToken(b, KW_IFLOAT);
    if (!r) r = consumeToken(b, KW_IDOUBLE);
    if (!r) r = consumeToken(b, KW_IREAL);
    if (!r) r = consumeToken(b, KW_CFLOAT);
    if (!r) r = consumeToken(b, KW_CDOUBLE);
    if (!r) r = consumeToken(b, KW_CREAL);
    if (!r) r = consumeToken(b, KW_VOID);
    exit_section_(b, l, m, BUILTIN_TYPE, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // 'case' assignExpression ':' '...' 'case' assignExpression ':' declarationsAndStatements
  public static boolean caseRangeStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "caseRangeStatement")) return false;
    if (!nextTokenIs(b, KW_CASE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_CASE);
    r = r && assignExpression(b, l + 1);
    r = r && consumeToken(b, OP_COLON);
    r = r && consumeToken(b, OP_TRIPLEDOT);
    r = r && consumeToken(b, KW_CASE);
    r = r && assignExpression(b, l + 1);
    r = r && consumeToken(b, OP_COLON);
    r = r && declarationsAndStatements(b, l + 1);
    exit_section_(b, m, CASE_RANGE_STATEMENT, r);
    return r;
  }

  /* ********************************************************** */
  // 'case' argumentList ':' declarationsAndStatements
  public static boolean caseStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "caseStatement")) return false;
    if (!nextTokenIs(b, KW_CASE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_CASE);
    r = r && argumentList(b, l + 1);
    r = r && consumeToken(b, OP_COLON);
    r = r && declarationsAndStatements(b, l + 1);
    exit_section_(b, m, CASE_STATEMENT, r);
    return r;
  }

  /* ********************************************************** */
  // 'cast' '(' ( type | castQualifier )? ')' unaryExpression
  public static boolean castExpression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "castExpression")) return false;
    if (!nextTokenIs(b, KW_CAST)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_CAST);
    r = r && consumeToken(b, OP_PAR_LEFT);
    r = r && castExpression_2(b, l + 1);
    r = r && consumeToken(b, OP_PAR_RIGHT);
    r = r && unaryExpression(b, l + 1);
    exit_section_(b, m, CAST_EXPRESSION, r);
    return r;
  }

  // ( type | castQualifier )?
  private static boolean castExpression_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "castExpression_2")) return false;
    castExpression_2_0(b, l + 1);
    return true;
  }

  // type | castQualifier
  private static boolean castExpression_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "castExpression_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = type(b, l + 1);
    if (!r) r = castQualifier(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'const' | 'const' 'shared' | 'immutable' | 'inout' | 'inout' 'shared' | 'shared' | 'shared' 'const' | 'shared' 'inout'
  public static boolean castQualifier(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "castQualifier")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<cast qualifier>");
    r = consumeToken(b, KW_CONST);
    if (!r) r = castQualifier_1(b, l + 1);
    if (!r) r = consumeToken(b, KW_IMMUTABLE);
    if (!r) r = consumeToken(b, KW_INOUT);
    if (!r) r = castQualifier_4(b, l + 1);
    if (!r) r = consumeToken(b, KW_SHARED);
    if (!r) r = castQualifier_6(b, l + 1);
    if (!r) r = castQualifier_7(b, l + 1);
    exit_section_(b, l, m, CAST_QUALIFIER, r, false, null);
    return r;
  }

  // 'const' 'shared'
  private static boolean castQualifier_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "castQualifier_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_CONST);
    r = r && consumeToken(b, KW_SHARED);
    exit_section_(b, m, null, r);
    return r;
  }

  // 'inout' 'shared'
  private static boolean castQualifier_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "castQualifier_4")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_INOUT);
    r = r && consumeToken(b, KW_SHARED);
    exit_section_(b, m, null, r);
    return r;
  }

  // 'shared' 'const'
  private static boolean castQualifier_6(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "castQualifier_6")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_SHARED);
    r = r && consumeToken(b, KW_CONST);
    exit_section_(b, m, null, r);
    return r;
  }

  // 'shared' 'inout'
  private static boolean castQualifier_7(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "castQualifier_7")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_SHARED);
    r = r && consumeToken(b, KW_INOUT);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'catch' '(' type Identifier? ')' declarationOrStatement
  public static boolean catch_(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "catch_")) return false;
    if (!nextTokenIs(b, KW_CATCH)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_CATCH);
    r = r && consumeToken(b, OP_PAR_LEFT);
    r = r && type(b, l + 1);
    r = r && catch__3(b, l + 1);
    r = r && consumeToken(b, OP_PAR_RIGHT);
    r = r && declarationOrStatement(b, l + 1);
    exit_section_(b, m, CATCH, r);
    return r;
  }

  // Identifier?
  private static boolean catch__3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "catch__3")) return false;
    Identifier(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // catch_+ | catch_* lastCatch
  public static boolean catches(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "catches")) return false;
    if (!nextTokenIs(b, KW_CATCH)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = catches_0(b, l + 1);
    if (!r) r = catches_1(b, l + 1);
    exit_section_(b, m, CATCHES, r);
    return r;
  }

  // catch_+
  private static boolean catches_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "catches_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = catch_(b, l + 1);
    int c = current_position_(b);
    while (r) {
      if (!catch_(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "catches_0", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // catch_* lastCatch
  private static boolean catches_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "catches_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = catches_1_0(b, l + 1);
    r = r && lastCatch(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // catch_*
  private static boolean catches_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "catches_1_0")) return false;
    int c = current_position_(b);
    while (true) {
      if (!catch_(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "catches_1_0", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  /* ********************************************************** */
  // 'class' Identifier ';' | 'class' Identifier ( ':' baseClassList )? structBody | 'class' Identifier templateParameters constraint? ( structBody | ';' ) | 'class' Identifier templateParameters constraint? ( ':' baseClassList )? structBody | 'class' Identifier templateParameters ( ':' baseClassList )? constraint? structBody
  public static boolean classDeclaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "classDeclaration")) return false;
    if (!nextTokenIs(b, KW_CLASS)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = classDeclaration_0(b, l + 1);
    if (!r) r = classDeclaration_1(b, l + 1);
    if (!r) r = classDeclaration_2(b, l + 1);
    if (!r) r = classDeclaration_3(b, l + 1);
    if (!r) r = classDeclaration_4(b, l + 1);
    exit_section_(b, m, CLASS_DECLARATION, r);
    return r;
  }

  // 'class' Identifier ';'
  private static boolean classDeclaration_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "classDeclaration_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_CLASS);
    r = r && Identifier(b, l + 1);
    r = r && consumeToken(b, OP_SCOLON);
    exit_section_(b, m, null, r);
    return r;
  }

  // 'class' Identifier ( ':' baseClassList )? structBody
  private static boolean classDeclaration_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "classDeclaration_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_CLASS);
    r = r && Identifier(b, l + 1);
    r = r && classDeclaration_1_2(b, l + 1);
    r = r && structBody(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ( ':' baseClassList )?
  private static boolean classDeclaration_1_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "classDeclaration_1_2")) return false;
    classDeclaration_1_2_0(b, l + 1);
    return true;
  }

  // ':' baseClassList
  private static boolean classDeclaration_1_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "classDeclaration_1_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_COLON);
    r = r && baseClassList(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // 'class' Identifier templateParameters constraint? ( structBody | ';' )
  private static boolean classDeclaration_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "classDeclaration_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_CLASS);
    r = r && Identifier(b, l + 1);
    r = r && templateParameters(b, l + 1);
    r = r && classDeclaration_2_3(b, l + 1);
    r = r && classDeclaration_2_4(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // constraint?
  private static boolean classDeclaration_2_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "classDeclaration_2_3")) return false;
    constraint(b, l + 1);
    return true;
  }

  // structBody | ';'
  private static boolean classDeclaration_2_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "classDeclaration_2_4")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = structBody(b, l + 1);
    if (!r) r = consumeToken(b, OP_SCOLON);
    exit_section_(b, m, null, r);
    return r;
  }

  // 'class' Identifier templateParameters constraint? ( ':' baseClassList )? structBody
  private static boolean classDeclaration_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "classDeclaration_3")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_CLASS);
    r = r && Identifier(b, l + 1);
    r = r && templateParameters(b, l + 1);
    r = r && classDeclaration_3_3(b, l + 1);
    r = r && classDeclaration_3_4(b, l + 1);
    r = r && structBody(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // constraint?
  private static boolean classDeclaration_3_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "classDeclaration_3_3")) return false;
    constraint(b, l + 1);
    return true;
  }

  // ( ':' baseClassList )?
  private static boolean classDeclaration_3_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "classDeclaration_3_4")) return false;
    classDeclaration_3_4_0(b, l + 1);
    return true;
  }

  // ':' baseClassList
  private static boolean classDeclaration_3_4_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "classDeclaration_3_4_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_COLON);
    r = r && baseClassList(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // 'class' Identifier templateParameters ( ':' baseClassList )? constraint? structBody
  private static boolean classDeclaration_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "classDeclaration_4")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_CLASS);
    r = r && Identifier(b, l + 1);
    r = r && templateParameters(b, l + 1);
    r = r && classDeclaration_4_3(b, l + 1);
    r = r && classDeclaration_4_4(b, l + 1);
    r = r && structBody(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ( ':' baseClassList )?
  private static boolean classDeclaration_4_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "classDeclaration_4_3")) return false;
    classDeclaration_4_3_0(b, l + 1);
    return true;
  }

  // ':' baseClassList
  private static boolean classDeclaration_4_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "classDeclaration_4_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_COLON);
    r = r && baseClassList(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // constraint?
  private static boolean classDeclaration_4_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "classDeclaration_4_4")) return false;
    constraint(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // equalExpression
  // | identityExpression 
  // | inExpression
  // | relExpression
  public static boolean cmpExpression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "cmpExpression")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<cmp expression>");
    r = equalExpression(b, l + 1);
    if (!r) r = identityExpression(b, l + 1);
    if (!r) r = inExpression(b, l + 1);
    if (!r) r = relExpression(b, l + 1);
    exit_section_(b, l, m, CMP_EXPRESSION, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // versionCondition | debugCondition | staticIfCondition
  public static boolean compileCondition(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "compileCondition")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<compile condition>");
    r = versionCondition(b, l + 1);
    if (!r) r = debugCondition(b, l + 1);
    if (!r) r = staticIfCondition(b, l + 1);
    exit_section_(b, l, m, COMPILE_CONDITION, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // compileCondition declaration | compileCondition ':' declaration | compileCondition declaration 'else' declaration
  public static boolean conditionalDeclaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "conditionalDeclaration")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<conditional declaration>");
    r = conditionalDeclaration_0(b, l + 1);
    if (!r) r = conditionalDeclaration_1(b, l + 1);
    if (!r) r = conditionalDeclaration_2(b, l + 1);
    exit_section_(b, l, m, CONDITIONAL_DECLARATION, r, false, null);
    return r;
  }

  // compileCondition declaration
  private static boolean conditionalDeclaration_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "conditionalDeclaration_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = compileCondition(b, l + 1);
    r = r && declaration(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // compileCondition ':' declaration
  private static boolean conditionalDeclaration_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "conditionalDeclaration_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = compileCondition(b, l + 1);
    r = r && consumeToken(b, OP_COLON);
    r = r && declaration(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // compileCondition declaration 'else' declaration
  private static boolean conditionalDeclaration_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "conditionalDeclaration_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = compileCondition(b, l + 1);
    r = r && declaration(b, l + 1);
    r = r && consumeToken(b, KW_ELSE);
    r = r && declaration(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // compileCondition declarationOrStatement ( 'else' declarationOrStatement )?
  public static boolean conditionalStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "conditionalStatement")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<conditional statement>");
    r = compileCondition(b, l + 1);
    r = r && declarationOrStatement(b, l + 1);
    r = r && conditionalStatement_2(b, l + 1);
    exit_section_(b, l, m, CONDITIONAL_STATEMENT, r, false, null);
    return r;
  }

  // ( 'else' declarationOrStatement )?
  private static boolean conditionalStatement_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "conditionalStatement_2")) return false;
    conditionalStatement_2_0(b, l + 1);
    return true;
  }

  // 'else' declarationOrStatement
  private static boolean conditionalStatement_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "conditionalStatement_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_ELSE);
    r = r && declarationOrStatement(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'if' '(' expression ')'
  public static boolean constraint(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "constraint")) return false;
    if (!nextTokenIs(b, KW_IF)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_IF);
    r = r && consumeToken(b, OP_PAR_LEFT);
    r = r && expression(b, l + 1);
    r = r && consumeToken(b, OP_PAR_RIGHT);
    exit_section_(b, m, CONSTRAINT, r);
    return r;
  }

  /* ********************************************************** */
  // 'this' parameters memberFunctionAttribute* constraint? ( functionBody | ';' )
  //             | 'this' templateParameters? parameters memberFunctionAttribute* constraint? ( functionBody | ';' )
  public static boolean constructor(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "constructor")) return false;
    if (!nextTokenIs(b, KW_THIS)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = constructor_0(b, l + 1);
    if (!r) r = constructor_1(b, l + 1);
    exit_section_(b, m, CONSTRUCTOR, r);
    return r;
  }

  // 'this' parameters memberFunctionAttribute* constraint? ( functionBody | ';' )
  private static boolean constructor_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "constructor_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_THIS);
    r = r && parameters(b, l + 1);
    r = r && constructor_0_2(b, l + 1);
    r = r && constructor_0_3(b, l + 1);
    r = r && constructor_0_4(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // memberFunctionAttribute*
  private static boolean constructor_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "constructor_0_2")) return false;
    int c = current_position_(b);
    while (true) {
      if (!memberFunctionAttribute(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "constructor_0_2", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // constraint?
  private static boolean constructor_0_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "constructor_0_3")) return false;
    constraint(b, l + 1);
    return true;
  }

  // functionBody | ';'
  private static boolean constructor_0_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "constructor_0_4")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = functionBody(b, l + 1);
    if (!r) r = consumeToken(b, OP_SCOLON);
    exit_section_(b, m, null, r);
    return r;
  }

  // 'this' templateParameters? parameters memberFunctionAttribute* constraint? ( functionBody | ';' )
  private static boolean constructor_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "constructor_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_THIS);
    r = r && constructor_1_1(b, l + 1);
    r = r && parameters(b, l + 1);
    r = r && constructor_1_3(b, l + 1);
    r = r && constructor_1_4(b, l + 1);
    r = r && constructor_1_5(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // templateParameters?
  private static boolean constructor_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "constructor_1_1")) return false;
    templateParameters(b, l + 1);
    return true;
  }

  // memberFunctionAttribute*
  private static boolean constructor_1_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "constructor_1_3")) return false;
    int c = current_position_(b);
    while (true) {
      if (!memberFunctionAttribute(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "constructor_1_3", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // constraint?
  private static boolean constructor_1_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "constructor_1_4")) return false;
    constraint(b, l + 1);
    return true;
  }

  // functionBody | ';'
  private static boolean constructor_1_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "constructor_1_5")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = functionBody(b, l + 1);
    if (!r) r = consumeToken(b, OP_SCOLON);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'continue' Identifier? ';'
  public static boolean continueStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "continueStatement")) return false;
    if (!nextTokenIs(b, KW_CONTINUE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_CONTINUE);
    r = r && continueStatement_1(b, l + 1);
    r = r && consumeToken(b, OP_SCOLON);
    exit_section_(b, m, CONTINUE_STATEMENT, r);
    return r;
  }

  // Identifier?
  private static boolean continueStatement_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "continueStatement_1")) return false;
    Identifier(b, l + 1);
    return true;
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
  // 'debug' ( '(' ( INTEGER_LITERAL | Identifier ) ')' )?
  public static boolean debugCondition(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "debugCondition")) return false;
    if (!nextTokenIs(b, KW_DEBUG)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_DEBUG);
    r = r && debugCondition_1(b, l + 1);
    exit_section_(b, m, DEBUG_CONDITION, r);
    return r;
  }

  // ( '(' ( INTEGER_LITERAL | Identifier ) ')' )?
  private static boolean debugCondition_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "debugCondition_1")) return false;
    debugCondition_1_0(b, l + 1);
    return true;
  }

  // '(' ( INTEGER_LITERAL | Identifier ) ')'
  private static boolean debugCondition_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "debugCondition_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_PAR_LEFT);
    r = r && debugCondition_1_0_1(b, l + 1);
    r = r && consumeToken(b, OP_PAR_RIGHT);
    exit_section_(b, m, null, r);
    return r;
  }

  // INTEGER_LITERAL | Identifier
  private static boolean debugCondition_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "debugCondition_1_0_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, INTEGER_LITERAL);
    if (!r) r = Identifier(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'debug' '=' ( Identifier | INTEGER_LITERAL ) ';'
  public static boolean debugSpecification(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "debugSpecification")) return false;
    if (!nextTokenIs(b, KW_DEBUG)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_DEBUG);
    r = r && consumeToken(b, OP_EQ);
    r = r && debugSpecification_2(b, l + 1);
    r = r && consumeToken(b, OP_SCOLON);
    exit_section_(b, m, DEBUG_SPECIFICATION, r);
    return r;
  }

  // Identifier | INTEGER_LITERAL
  private static boolean debugSpecification_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "debugSpecification_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Identifier(b, l + 1);
    if (!r) r = consumeToken(b, INTEGER_LITERAL);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // attribute* declaration2
  public static boolean declaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "declaration")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<declaration>");
    r = declaration_0(b, l + 1);
    r = r && declaration2(b, l + 1);
    exit_section_(b, l, m, DECLARATION, r, false, null);
    return r;
  }

  // attribute*
  private static boolean declaration_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "declaration_0")) return false;
    int c = current_position_(b);
    while (true) {
      if (!attribute(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "declaration_0", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  /* ********************************************************** */
  // aliasDeclaration
  // | aliasThisDeclaration
  // | anonymousEnumDeclaration
  // | attributeDeclaration
  // | classDeclaration
  // | conditionalDeclaration
  // | constructor
  // | debugSpecification
  // | destructor
  // | enumDeclaration
  // | eponymousTemplateDeclaration
  // | functionDeclaration
  // | importDeclaration
  // | interfaceDeclaration
  // | invariant
  // | mixinDeclaration
  // | mixinTemplateDeclaration
  // | pragmaDeclaration
  // | sharedStaticConstructor
  // | sharedStaticDestructor
  // | staticAssertDeclaration
  // | staticConstructor
  // | staticDestructor
  // | structDeclaration
  // | templateDeclaration
  // | unionDeclaration
  // | unittest
  // | variableDeclaration
  // | versionSpecification
  // | '{' declaration '}'
  public static boolean declaration2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "declaration2")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<declaration 2>");
    r = aliasDeclaration(b, l + 1);
    if (!r) r = aliasThisDeclaration(b, l + 1);
    if (!r) r = anonymousEnumDeclaration(b, l + 1);
    if (!r) r = attributeDeclaration(b, l + 1);
    if (!r) r = classDeclaration(b, l + 1);
    if (!r) r = conditionalDeclaration(b, l + 1);
    if (!r) r = constructor(b, l + 1);
    if (!r) r = debugSpecification(b, l + 1);
    if (!r) r = destructor(b, l + 1);
    if (!r) r = enumDeclaration(b, l + 1);
    if (!r) r = eponymousTemplateDeclaration(b, l + 1);
    if (!r) r = functionDeclaration(b, l + 1);
    if (!r) r = importDeclaration(b, l + 1);
    if (!r) r = interfaceDeclaration(b, l + 1);
    if (!r) r = invariant(b, l + 1);
    if (!r) r = mixinDeclaration(b, l + 1);
    if (!r) r = mixinTemplateDeclaration(b, l + 1);
    if (!r) r = pragmaDeclaration(b, l + 1);
    if (!r) r = sharedStaticConstructor(b, l + 1);
    if (!r) r = sharedStaticDestructor(b, l + 1);
    if (!r) r = staticAssertDeclaration(b, l + 1);
    if (!r) r = staticConstructor(b, l + 1);
    if (!r) r = staticDestructor(b, l + 1);
    if (!r) r = structDeclaration(b, l + 1);
    if (!r) r = templateDeclaration(b, l + 1);
    if (!r) r = unionDeclaration(b, l + 1);
    if (!r) r = unittest(b, l + 1);
    if (!r) r = variableDeclaration(b, l + 1);
    if (!r) r = versionSpecification(b, l + 1);
    if (!r) r = declaration2_29(b, l + 1);
    exit_section_(b, l, m, DECLARATION_2, r, false, null);
    return r;
  }

  // '{' declaration '}'
  private static boolean declaration2_29(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "declaration2_29")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_BRACES_LEFT);
    r = r && declaration(b, l + 1);
    r = r && consumeToken(b, OP_BRACES_RIGHT);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // declaration | statement
  public static boolean declarationOrStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "declarationOrStatement")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<declaration or statement>");
    r = declaration(b, l + 1);
    if (!r) r = statement(b, l + 1);
    exit_section_(b, l, m, DECLARATION_OR_STATEMENT, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // declarationOrStatement+
  public static boolean declarationsAndStatements(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "declarationsAndStatements")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<declarations and statements>");
    r = declarationOrStatement(b, l + 1);
    int c = current_position_(b);
    while (r) {
      if (!declarationOrStatement(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "declarationsAndStatements", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, l, m, DECLARATIONS_AND_STATEMENTS, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // Identifier ('=' initializer)?
  public static boolean declarator(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "declarator")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Identifier(b, l + 1);
    r = r && declarator_1(b, l + 1);
    exit_section_(b, m, DECLARATOR, r);
    return r;
  }

  // ('=' initializer)?
  private static boolean declarator_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "declarator_1")) return false;
    declarator_1_0(b, l + 1);
    return true;
  }

  // '=' initializer
  private static boolean declarator_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "declarator_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_EQ);
    r = r && initializer(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'default' ':' declarationsAndStatements
  public static boolean defaultStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "defaultStatement")) return false;
    if (!nextTokenIs(b, KW_DEFAULT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_DEFAULT);
    r = r && consumeToken(b, OP_COLON);
    r = r && declarationsAndStatements(b, l + 1);
    exit_section_(b, m, DEFAULT_STATEMENT, r);
    return r;
  }

  /* ********************************************************** */
  // 'delete' unaryExpression
  public static boolean deleteExpression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "deleteExpression")) return false;
    if (!nextTokenIs(b, KW_DELETE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_DELETE);
    r = r && unaryExpression(b, l + 1);
    exit_section_(b, m, DELETE_EXPRESSION, r);
    return r;
  }

  /* ********************************************************** */
  // 'deprecated' ( '(' StringLiteral ')' )?
  public static boolean deprecated(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "deprecated")) return false;
    if (!nextTokenIs(b, KW_DEPRECATED)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_DEPRECATED);
    r = r && deprecated_1(b, l + 1);
    exit_section_(b, m, DEPRECATED, r);
    return r;
  }

  // ( '(' StringLiteral ')' )?
  private static boolean deprecated_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "deprecated_1")) return false;
    deprecated_1_0(b, l + 1);
    return true;
  }

  // '(' StringLiteral ')'
  private static boolean deprecated_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "deprecated_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_PAR_LEFT);
    r = r && StringLiteral(b, l + 1);
    r = r && consumeToken(b, OP_PAR_RIGHT);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // '~' 'this' '(' ')' memberFunctionAttribute* ( functionBody | ';' )
  public static boolean destructor(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "destructor")) return false;
    if (!nextTokenIs(b, OP_TILDA)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_TILDA);
    r = r && consumeToken(b, KW_THIS);
    r = r && consumeToken(b, OP_PAR_LEFT);
    r = r && consumeToken(b, OP_PAR_RIGHT);
    r = r && destructor_4(b, l + 1);
    r = r && destructor_5(b, l + 1);
    exit_section_(b, m, DESTRUCTOR, r);
    return r;
  }

  // memberFunctionAttribute*
  private static boolean destructor_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "destructor_4")) return false;
    int c = current_position_(b);
    while (true) {
      if (!memberFunctionAttribute(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "destructor_4", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // functionBody | ';'
  private static boolean destructor_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "destructor_5")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = functionBody(b, l + 1);
    if (!r) r = consumeToken(b, OP_SCOLON);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'do' statementNoCaseNoDefault 'while' '(' expression ')' ';'
  public static boolean doStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "doStatement")) return false;
    if (!nextTokenIs(b, KW_DO)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_DO);
    r = r && statementNoCaseNoDefault(b, l + 1);
    r = r && consumeToken(b, KW_WHILE);
    r = r && consumeToken(b, OP_PAR_LEFT);
    r = r && expression(b, l + 1);
    r = r && consumeToken(b, OP_PAR_RIGHT);
    r = r && consumeToken(b, OP_SCOLON);
    exit_section_(b, m, DO_STATEMENT, r);
    return r;
  }

  /* ********************************************************** */
  // '{' enumMember ( ',' enumMember? )* '}'
  public static boolean enumBody(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "enumBody")) return false;
    if (!nextTokenIs(b, OP_BRACES_LEFT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_BRACES_LEFT);
    r = r && enumMember(b, l + 1);
    r = r && enumBody_2(b, l + 1);
    r = r && consumeToken(b, OP_BRACES_RIGHT);
    exit_section_(b, m, ENUM_BODY, r);
    return r;
  }

  // ( ',' enumMember? )*
  private static boolean enumBody_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "enumBody_2")) return false;
    int c = current_position_(b);
    while (true) {
      if (!enumBody_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "enumBody_2", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // ',' enumMember?
  private static boolean enumBody_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "enumBody_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_COMMA);
    r = r && enumBody_2_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // enumMember?
  private static boolean enumBody_2_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "enumBody_2_0_1")) return false;
    enumMember(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // 'enum' Identifier ( ':' type )? ';' | 'enum' Identifier ( ':' type )? enumBody
  public static boolean enumDeclaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "enumDeclaration")) return false;
    if (!nextTokenIs(b, KW_ENUM)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = enumDeclaration_0(b, l + 1);
    if (!r) r = enumDeclaration_1(b, l + 1);
    exit_section_(b, m, ENUM_DECLARATION, r);
    return r;
  }

  // 'enum' Identifier ( ':' type )? ';'
  private static boolean enumDeclaration_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "enumDeclaration_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_ENUM);
    r = r && Identifier(b, l + 1);
    r = r && enumDeclaration_0_2(b, l + 1);
    r = r && consumeToken(b, OP_SCOLON);
    exit_section_(b, m, null, r);
    return r;
  }

  // ( ':' type )?
  private static boolean enumDeclaration_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "enumDeclaration_0_2")) return false;
    enumDeclaration_0_2_0(b, l + 1);
    return true;
  }

  // ':' type
  private static boolean enumDeclaration_0_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "enumDeclaration_0_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_COLON);
    r = r && type(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // 'enum' Identifier ( ':' type )? enumBody
  private static boolean enumDeclaration_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "enumDeclaration_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_ENUM);
    r = r && Identifier(b, l + 1);
    r = r && enumDeclaration_1_2(b, l + 1);
    r = r && enumBody(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ( ':' type )?
  private static boolean enumDeclaration_1_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "enumDeclaration_1_2")) return false;
    enumDeclaration_1_2_0(b, l + 1);
    return true;
  }

  // ':' type
  private static boolean enumDeclaration_1_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "enumDeclaration_1_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_COLON);
    r = r && type(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // Identifier | Identifier '=' assignExpression
  public static boolean enumMember(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "enumMember")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Identifier(b, l + 1);
    if (!r) r = enumMember_1(b, l + 1);
    exit_section_(b, m, ENUM_MEMBER, r);
    return r;
  }

  // Identifier '=' assignExpression
  private static boolean enumMember_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "enumMember_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Identifier(b, l + 1);
    r = r && consumeToken(b, OP_EQ);
    r = r && assignExpression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'enum' Identifier templateParameters '=' assignExpression ';'
  public static boolean eponymousTemplateDeclaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "eponymousTemplateDeclaration")) return false;
    if (!nextTokenIs(b, KW_ENUM)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_ENUM);
    r = r && Identifier(b, l + 1);
    r = r && templateParameters(b, l + 1);
    r = r && consumeToken(b, OP_EQ);
    r = r && assignExpression(b, l + 1);
    r = r && consumeToken(b, OP_SCOLON);
    exit_section_(b, m, EPONYMOUS_TEMPLATE_DECLARATION, r);
    return r;
  }

  /* ********************************************************** */
  // shiftExpression ( '==' | '!=' ) shiftExpression
  public static boolean equalExpression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "equalExpression")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<equal expression>");
    r = shiftExpression(b, l + 1);
    r = r && equalExpression_1(b, l + 1);
    r = r && shiftExpression(b, l + 1);
    exit_section_(b, l, m, EQUAL_EXPRESSION, r, false, null);
    return r;
  }

  // '==' | '!='
  private static boolean equalExpression_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "equalExpression_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_EQ_EQ);
    if (!r) r = consumeToken(b, OP_NOT_EQ);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // assignExpression ( ',' assignExpression )*
  public static boolean expression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "expression")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<expression>");
    r = assignExpression(b, l + 1);
    r = r && expression_1(b, l + 1);
    exit_section_(b, l, m, EXPRESSION, r, false, null);
    return r;
  }

  // ( ',' assignExpression )*
  private static boolean expression_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "expression_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!expression_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "expression_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // ',' assignExpression
  private static boolean expression_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "expression_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_COMMA);
    r = r && assignExpression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // expression ';'
  public static boolean expressionStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "expressionStatement")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<expression statement>");
    r = expression(b, l + 1);
    r = r && consumeToken(b, OP_SCOLON);
    exit_section_(b, l, m, EXPRESSION_STATEMENT, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // 'final' switchStatement
  public static boolean finalSwitchStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "finalSwitchStatement")) return false;
    if (!nextTokenIs(b, KW_FINAL)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_FINAL);
    r = r && switchStatement(b, l + 1);
    exit_section_(b, m, FINAL_SWITCH_STATEMENT, r);
    return r;
  }

  /* ********************************************************** */
  // 'finally' declarationOrStatement
  public static boolean finally_(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "finally_")) return false;
    if (!nextTokenIs(b, KW_FINALLY)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_FINALLY);
    r = r && declarationOrStatement(b, l + 1);
    exit_section_(b, m, FINALLY, r);
    return r;
  }

  /* ********************************************************** */
  // 'for' '(' ( declaration | statementNoCaseNoDefault ) expression? ';' expression? ')' declarationOrStatement
  public static boolean forStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "forStatement")) return false;
    if (!nextTokenIs(b, KW_FOR)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_FOR);
    r = r && consumeToken(b, OP_PAR_LEFT);
    r = r && forStatement_2(b, l + 1);
    r = r && forStatement_3(b, l + 1);
    r = r && consumeToken(b, OP_SCOLON);
    r = r && forStatement_5(b, l + 1);
    r = r && consumeToken(b, OP_PAR_RIGHT);
    r = r && declarationOrStatement(b, l + 1);
    exit_section_(b, m, FOR_STATEMENT, r);
    return r;
  }

  // declaration | statementNoCaseNoDefault
  private static boolean forStatement_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "forStatement_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = declaration(b, l + 1);
    if (!r) r = statementNoCaseNoDefault(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // expression?
  private static boolean forStatement_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "forStatement_3")) return false;
    expression(b, l + 1);
    return true;
  }

  // expression?
  private static boolean forStatement_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "forStatement_5")) return false;
    expression(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // ( 'foreach' | 'foreach_reverse' ) '(' foreachTypeList ';' expression ')' declarationOrStatement | ( 'foreach' | 'foreach_reverse' ) '(' foreachType ';' expression '..' expression ')' declarationOrStatement
  public static boolean foreachStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "foreachStatement")) return false;
    if (!nextTokenIs(b, "<foreach statement>", KW_FOREACH, KW_FOREACH_REVERSE)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<foreach statement>");
    r = foreachStatement_0(b, l + 1);
    if (!r) r = foreachStatement_1(b, l + 1);
    exit_section_(b, l, m, FOREACH_STATEMENT, r, false, null);
    return r;
  }

  // ( 'foreach' | 'foreach_reverse' ) '(' foreachTypeList ';' expression ')' declarationOrStatement
  private static boolean foreachStatement_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "foreachStatement_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = foreachStatement_0_0(b, l + 1);
    r = r && consumeToken(b, OP_PAR_LEFT);
    r = r && foreachTypeList(b, l + 1);
    r = r && consumeToken(b, OP_SCOLON);
    r = r && expression(b, l + 1);
    r = r && consumeToken(b, OP_PAR_RIGHT);
    r = r && declarationOrStatement(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // 'foreach' | 'foreach_reverse'
  private static boolean foreachStatement_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "foreachStatement_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_FOREACH);
    if (!r) r = consumeToken(b, KW_FOREACH_REVERSE);
    exit_section_(b, m, null, r);
    return r;
  }

  // ( 'foreach' | 'foreach_reverse' ) '(' foreachType ';' expression '..' expression ')' declarationOrStatement
  private static boolean foreachStatement_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "foreachStatement_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = foreachStatement_1_0(b, l + 1);
    r = r && consumeToken(b, OP_PAR_LEFT);
    r = r && foreachType(b, l + 1);
    r = r && consumeToken(b, OP_SCOLON);
    r = r && expression(b, l + 1);
    r = r && consumeToken(b, OP_DDOT);
    r = r && expression(b, l + 1);
    r = r && consumeToken(b, OP_PAR_RIGHT);
    r = r && declarationOrStatement(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // 'foreach' | 'foreach_reverse'
  private static boolean foreachStatement_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "foreachStatement_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_FOREACH);
    if (!r) r = consumeToken(b, KW_FOREACH_REVERSE);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'ref'? typeConstructors? type? Identifier | typeConstructors? 'ref'? type? Identifier
  public static boolean foreachType(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "foreachType")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<foreach type>");
    r = foreachType_0(b, l + 1);
    if (!r) r = foreachType_1(b, l + 1);
    exit_section_(b, l, m, FOREACH_TYPE, r, false, null);
    return r;
  }

  // 'ref'? typeConstructors? type? Identifier
  private static boolean foreachType_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "foreachType_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = foreachType_0_0(b, l + 1);
    r = r && foreachType_0_1(b, l + 1);
    r = r && foreachType_0_2(b, l + 1);
    r = r && Identifier(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // 'ref'?
  private static boolean foreachType_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "foreachType_0_0")) return false;
    consumeToken(b, KW_REF);
    return true;
  }

  // typeConstructors?
  private static boolean foreachType_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "foreachType_0_1")) return false;
    typeConstructors(b, l + 1);
    return true;
  }

  // type?
  private static boolean foreachType_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "foreachType_0_2")) return false;
    type(b, l + 1);
    return true;
  }

  // typeConstructors? 'ref'? type? Identifier
  private static boolean foreachType_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "foreachType_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = foreachType_1_0(b, l + 1);
    r = r && foreachType_1_1(b, l + 1);
    r = r && foreachType_1_2(b, l + 1);
    r = r && Identifier(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // typeConstructors?
  private static boolean foreachType_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "foreachType_1_0")) return false;
    typeConstructors(b, l + 1);
    return true;
  }

  // 'ref'?
  private static boolean foreachType_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "foreachType_1_1")) return false;
    consumeToken(b, KW_REF);
    return true;
  }

  // type?
  private static boolean foreachType_1_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "foreachType_1_2")) return false;
    type(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // foreachType ( ',' foreachType )*
  public static boolean foreachTypeList(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "foreachTypeList")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<foreach type list>");
    r = foreachType(b, l + 1);
    r = r && foreachTypeList_1(b, l + 1);
    exit_section_(b, l, m, FOREACH_TYPE_LIST, r, false, null);
    return r;
  }

  // ( ',' foreachType )*
  private static boolean foreachTypeList_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "foreachTypeList_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!foreachTypeList_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "foreachTypeList_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // ',' foreachType
  private static boolean foreachTypeList_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "foreachTypeList_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_COMMA);
    r = r && foreachType(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // atAttribute | 'pure' | 'nothrow'
  public static boolean functionAttribute(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "functionAttribute")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<function attribute>");
    r = atAttribute(b, l + 1);
    if (!r) r = consumeToken(b, KW_PURE);
    if (!r) r = consumeToken(b, KW_NOTHROW);
    exit_section_(b, l, m, FUNCTION_ATTRIBUTE, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // blockStatement | ( inStatement | outStatement | outStatement inStatement | inStatement outStatement )? bodyStatement
  public static boolean functionBody(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "functionBody")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<function body>");
    r = blockStatement(b, l + 1);
    if (!r) r = functionBody_1(b, l + 1);
    exit_section_(b, l, m, FUNCTION_BODY, r, false, null);
    return r;
  }

  // ( inStatement | outStatement | outStatement inStatement | inStatement outStatement )? bodyStatement
  private static boolean functionBody_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "functionBody_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = functionBody_1_0(b, l + 1);
    r = r && bodyStatement(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ( inStatement | outStatement | outStatement inStatement | inStatement outStatement )?
  private static boolean functionBody_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "functionBody_1_0")) return false;
    functionBody_1_0_0(b, l + 1);
    return true;
  }

  // inStatement | outStatement | outStatement inStatement | inStatement outStatement
  private static boolean functionBody_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "functionBody_1_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = inStatement(b, l + 1);
    if (!r) r = outStatement(b, l + 1);
    if (!r) r = functionBody_1_0_0_2(b, l + 1);
    if (!r) r = functionBody_1_0_0_3(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // outStatement inStatement
  private static boolean functionBody_1_0_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "functionBody_1_0_0_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = outStatement(b, l + 1);
    r = r && inStatement(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // inStatement outStatement
  private static boolean functionBody_1_0_0_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "functionBody_1_0_0_3")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = inStatement(b, l + 1);
    r = r && outStatement(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // symbol arguments unaryExpression arguments | type arguments
  public static boolean functionCallExpression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "functionCallExpression")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<function call expression>");
    r = functionCallExpression_0(b, l + 1);
    if (!r) r = functionCallExpression_1(b, l + 1);
    exit_section_(b, l, m, FUNCTION_CALL_EXPRESSION, r, false, null);
    return r;
  }

  // symbol arguments unaryExpression arguments
  private static boolean functionCallExpression_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "functionCallExpression_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = symbol(b, l + 1);
    r = r && arguments(b, l + 1);
    r = r && unaryExpression(b, l + 1);
    r = r && arguments(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // type arguments
  private static boolean functionCallExpression_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "functionCallExpression_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = type(b, l + 1);
    r = r && arguments(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // functionCallExpression ';'
  public static boolean functionCallStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "functionCallStatement")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<function call statement>");
    r = functionCallExpression(b, l + 1);
    r = r && consumeToken(b, OP_SCOLON);
    exit_section_(b, l, m, FUNCTION_CALL_STATEMENT, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // ( storageClass | type ) Identifier parameters memberFunctionAttribute* ( functionBody | ';' )
  // | ( storageClass | type ) Identifier templateParameters parameters memberFunctionAttribute* constraint? ( functionBody | ';' )
  public static boolean functionDeclaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "functionDeclaration")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<function declaration>");
    r = functionDeclaration_0(b, l + 1);
    if (!r) r = functionDeclaration_1(b, l + 1);
    exit_section_(b, l, m, FUNCTION_DECLARATION, r, false, null);
    return r;
  }

  // ( storageClass | type ) Identifier parameters memberFunctionAttribute* ( functionBody | ';' )
  private static boolean functionDeclaration_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "functionDeclaration_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = functionDeclaration_0_0(b, l + 1);
    r = r && Identifier(b, l + 1);
    r = r && parameters(b, l + 1);
    r = r && functionDeclaration_0_3(b, l + 1);
    r = r && functionDeclaration_0_4(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // storageClass | type
  private static boolean functionDeclaration_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "functionDeclaration_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = storageClass(b, l + 1);
    if (!r) r = type(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // memberFunctionAttribute*
  private static boolean functionDeclaration_0_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "functionDeclaration_0_3")) return false;
    int c = current_position_(b);
    while (true) {
      if (!memberFunctionAttribute(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "functionDeclaration_0_3", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // functionBody | ';'
  private static boolean functionDeclaration_0_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "functionDeclaration_0_4")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = functionBody(b, l + 1);
    if (!r) r = consumeToken(b, OP_SCOLON);
    exit_section_(b, m, null, r);
    return r;
  }

  // ( storageClass | type ) Identifier templateParameters parameters memberFunctionAttribute* constraint? ( functionBody | ';' )
  private static boolean functionDeclaration_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "functionDeclaration_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = functionDeclaration_1_0(b, l + 1);
    r = r && Identifier(b, l + 1);
    r = r && templateParameters(b, l + 1);
    r = r && parameters(b, l + 1);
    r = r && functionDeclaration_1_4(b, l + 1);
    r = r && functionDeclaration_1_5(b, l + 1);
    r = r && functionDeclaration_1_6(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // storageClass | type
  private static boolean functionDeclaration_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "functionDeclaration_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = storageClass(b, l + 1);
    if (!r) r = type(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // memberFunctionAttribute*
  private static boolean functionDeclaration_1_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "functionDeclaration_1_4")) return false;
    int c = current_position_(b);
    while (true) {
      if (!memberFunctionAttribute(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "functionDeclaration_1_4", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // constraint?
  private static boolean functionDeclaration_1_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "functionDeclaration_1_5")) return false;
    constraint(b, l + 1);
    return true;
  }

  // functionBody | ';'
  private static boolean functionDeclaration_1_6(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "functionDeclaration_1_6")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = functionBody(b, l + 1);
    if (!r) r = consumeToken(b, OP_SCOLON);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // ( ( 'function' | 'delegate' ) type? )? ( parameters functionAttribute* )? functionBody
  public static boolean functionLiteralExpression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "functionLiteralExpression")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<function literal expression>");
    r = functionLiteralExpression_0(b, l + 1);
    r = r && functionLiteralExpression_1(b, l + 1);
    r = r && functionBody(b, l + 1);
    exit_section_(b, l, m, FUNCTION_LITERAL_EXPRESSION, r, false, null);
    return r;
  }

  // ( ( 'function' | 'delegate' ) type? )?
  private static boolean functionLiteralExpression_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "functionLiteralExpression_0")) return false;
    functionLiteralExpression_0_0(b, l + 1);
    return true;
  }

  // ( 'function' | 'delegate' ) type?
  private static boolean functionLiteralExpression_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "functionLiteralExpression_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = functionLiteralExpression_0_0_0(b, l + 1);
    r = r && functionLiteralExpression_0_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // 'function' | 'delegate'
  private static boolean functionLiteralExpression_0_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "functionLiteralExpression_0_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_FUNCTION);
    if (!r) r = consumeToken(b, KW_DELEGATE);
    exit_section_(b, m, null, r);
    return r;
  }

  // type?
  private static boolean functionLiteralExpression_0_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "functionLiteralExpression_0_0_1")) return false;
    type(b, l + 1);
    return true;
  }

  // ( parameters functionAttribute* )?
  private static boolean functionLiteralExpression_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "functionLiteralExpression_1")) return false;
    functionLiteralExpression_1_0(b, l + 1);
    return true;
  }

  // parameters functionAttribute*
  private static boolean functionLiteralExpression_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "functionLiteralExpression_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = parameters(b, l + 1);
    r = r && functionLiteralExpression_1_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // functionAttribute*
  private static boolean functionLiteralExpression_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "functionLiteralExpression_1_0_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!functionAttribute(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "functionLiteralExpression_1_0_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  /* ********************************************************** */
  // 'goto' ( Identifier | 'default' | 'case' expression? ) ';'
  public static boolean gotoStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "gotoStatement")) return false;
    if (!nextTokenIs(b, KW_GOTO)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_GOTO);
    r = r && gotoStatement_1(b, l + 1);
    r = r && consumeToken(b, OP_SCOLON);
    exit_section_(b, m, GOTO_STATEMENT, r);
    return r;
  }

  // Identifier | 'default' | 'case' expression?
  private static boolean gotoStatement_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "gotoStatement_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Identifier(b, l + 1);
    if (!r) r = consumeToken(b, KW_DEFAULT);
    if (!r) r = gotoStatement_1_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // 'case' expression?
  private static boolean gotoStatement_1_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "gotoStatement_1_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_CASE);
    r = r && gotoStatement_1_2_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // expression?
  private static boolean gotoStatement_1_2_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "gotoStatement_1_2_1")) return false;
    expression(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // Identifier ( '.' Identifier )*
  public static boolean identifierChain(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "identifierChain")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Identifier(b, l + 1);
    r = r && identifierChain_1(b, l + 1);
    exit_section_(b, m, IDENTIFIER_CHAIN, r);
    return r;
  }

  // ( '.' Identifier )*
  private static boolean identifierChain_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "identifierChain_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!identifierChain_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "identifierChain_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // '.' Identifier
  private static boolean identifierChain_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "identifierChain_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_DOT);
    r = r && Identifier(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // Identifier ( ',' Identifier )*
  public static boolean identifierList(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "identifierList")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Identifier(b, l + 1);
    r = r && identifierList_1(b, l + 1);
    exit_section_(b, m, IDENTIFIER_LIST, r);
    return r;
  }

  // ( ',' Identifier )*
  private static boolean identifierList_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "identifierList_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!identifierList_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "identifierList_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // ',' Identifier
  private static boolean identifierList_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "identifierList_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_COMMA);
    r = r && Identifier(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // identifierOrTemplateInstance ( '.' identifierOrTemplateInstance )*
  public static boolean identifierOrTemplateChain(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "identifierOrTemplateChain")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = identifierOrTemplateInstance(b, l + 1);
    r = r && identifierOrTemplateChain_1(b, l + 1);
    exit_section_(b, m, IDENTIFIER_OR_TEMPLATE_CHAIN, r);
    return r;
  }

  // ( '.' identifierOrTemplateInstance )*
  private static boolean identifierOrTemplateChain_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "identifierOrTemplateChain_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!identifierOrTemplateChain_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "identifierOrTemplateChain_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // '.' identifierOrTemplateInstance
  private static boolean identifierOrTemplateChain_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "identifierOrTemplateChain_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_DOT);
    r = r && identifierOrTemplateInstance(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // Identifier | templateInstance
  public static boolean identifierOrTemplateInstance(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "identifierOrTemplateInstance")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Identifier(b, l + 1);
    if (!r) r = templateInstance(b, l + 1);
    exit_section_(b, m, IDENTIFIER_OR_TEMPLATE_INSTANCE, r);
    return r;
  }

  /* ********************************************************** */
  // shiftExpression ( 'is' | ( '!' 'is' ) ) shiftExpression
  public static boolean identityExpression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "identityExpression")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<identity expression>");
    r = shiftExpression(b, l + 1);
    r = r && identityExpression_1(b, l + 1);
    r = r && shiftExpression(b, l + 1);
    exit_section_(b, l, m, IDENTITY_EXPRESSION, r, false, null);
    return r;
  }

  // 'is' | ( '!' 'is' )
  private static boolean identityExpression_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "identityExpression_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_IS);
    if (!r) r = identityExpression_1_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '!' 'is'
  private static boolean identityExpression_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "identityExpression_1_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_NOT);
    r = r && consumeToken(b, KW_IS);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'auto' Identifier '=' expression
  //   | type Identifier '=' expression
  //   | expression
  public static boolean ifCondition(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ifCondition")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<if condition>");
    r = ifCondition_0(b, l + 1);
    if (!r) r = ifCondition_1(b, l + 1);
    if (!r) r = expression(b, l + 1);
    exit_section_(b, l, m, IF_CONDITION, r, false, null);
    return r;
  }

  // 'auto' Identifier '=' expression
  private static boolean ifCondition_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ifCondition_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_AUTO);
    r = r && Identifier(b, l + 1);
    r = r && consumeToken(b, OP_EQ);
    r = r && expression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // type Identifier '=' expression
  private static boolean ifCondition_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ifCondition_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = type(b, l + 1);
    r = r && Identifier(b, l + 1);
    r = r && consumeToken(b, OP_EQ);
    r = r && expression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'if' '(' ifCondition ')' declarationOrStatement ( 'else' declarationOrStatement )?
  public static boolean ifStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ifStatement")) return false;
    if (!nextTokenIs(b, KW_IF)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_IF);
    r = r && consumeToken(b, OP_PAR_LEFT);
    r = r && ifCondition(b, l + 1);
    r = r && consumeToken(b, OP_PAR_RIGHT);
    r = r && declarationOrStatement(b, l + 1);
    r = r && ifStatement_5(b, l + 1);
    exit_section_(b, m, IF_STATEMENT, r);
    return r;
  }

  // ( 'else' declarationOrStatement )?
  private static boolean ifStatement_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ifStatement_5")) return false;
    ifStatement_5_0(b, l + 1);
    return true;
  }

  // 'else' declarationOrStatement
  private static boolean ifStatement_5_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ifStatement_5_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_ELSE);
    r = r && declarationOrStatement(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // Identifier ( '=' Identifier )?
  public static boolean importBind(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "importBind")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Identifier(b, l + 1);
    r = r && importBind_1(b, l + 1);
    exit_section_(b, m, IMPORT_BIND, r);
    return r;
  }

  // ( '=' Identifier )?
  private static boolean importBind_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "importBind_1")) return false;
    importBind_1_0(b, l + 1);
    return true;
  }

  // '=' Identifier
  private static boolean importBind_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "importBind_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_EQ);
    r = r && Identifier(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // singleImport ':' importBind ( ',' importBind )*
  public static boolean importBindings(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "importBindings")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = singleImport(b, l + 1);
    r = r && consumeToken(b, OP_COLON);
    r = r && importBind(b, l + 1);
    r = r && importBindings_3(b, l + 1);
    exit_section_(b, m, IMPORT_BINDINGS, r);
    return r;
  }

  // ( ',' importBind )*
  private static boolean importBindings_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "importBindings_3")) return false;
    int c = current_position_(b);
    while (true) {
      if (!importBindings_3_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "importBindings_3", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // ',' importBind
  private static boolean importBindings_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "importBindings_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_COMMA);
    r = r && importBind(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'import' singleImport (',' singleImport)* (',' importBindings)? ';'
  //       | 'import' importBindings ';'
  public static boolean importDeclaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "importDeclaration")) return false;
    if (!nextTokenIs(b, KW_IMPORT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = importDeclaration_0(b, l + 1);
    if (!r) r = importDeclaration_1(b, l + 1);
    exit_section_(b, m, IMPORT_DECLARATION, r);
    return r;
  }

  // 'import' singleImport (',' singleImport)* (',' importBindings)? ';'
  private static boolean importDeclaration_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "importDeclaration_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_IMPORT);
    r = r && singleImport(b, l + 1);
    r = r && importDeclaration_0_2(b, l + 1);
    r = r && importDeclaration_0_3(b, l + 1);
    r = r && consumeToken(b, OP_SCOLON);
    exit_section_(b, m, null, r);
    return r;
  }

  // (',' singleImport)*
  private static boolean importDeclaration_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "importDeclaration_0_2")) return false;
    int c = current_position_(b);
    while (true) {
      if (!importDeclaration_0_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "importDeclaration_0_2", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // ',' singleImport
  private static boolean importDeclaration_0_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "importDeclaration_0_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_COMMA);
    r = r && singleImport(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (',' importBindings)?
  private static boolean importDeclaration_0_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "importDeclaration_0_3")) return false;
    importDeclaration_0_3_0(b, l + 1);
    return true;
  }

  // ',' importBindings
  private static boolean importDeclaration_0_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "importDeclaration_0_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_COMMA);
    r = r && importBindings(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // 'import' importBindings ';'
  private static boolean importDeclaration_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "importDeclaration_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_IMPORT);
    r = r && importBindings(b, l + 1);
    r = r && consumeToken(b, OP_SCOLON);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'import' '(' assignExpression ')'
  public static boolean importExpression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "importExpression")) return false;
    if (!nextTokenIs(b, KW_IMPORT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_IMPORT);
    r = r && consumeToken(b, OP_PAR_LEFT);
    r = r && assignExpression(b, l + 1);
    r = r && consumeToken(b, OP_PAR_RIGHT);
    exit_section_(b, m, IMPORT_EXPRESSION, r);
    return r;
  }

  /* ********************************************************** */
  // shiftExpression ( 'in' | ( '!' 'in' ) ) shiftExpression
  public static boolean inExpression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "inExpression")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<in expression>");
    r = shiftExpression(b, l + 1);
    r = r && inExpression_1(b, l + 1);
    r = r && shiftExpression(b, l + 1);
    exit_section_(b, l, m, IN_EXPRESSION, r, false, null);
    return r;
  }

  // 'in' | ( '!' 'in' )
  private static boolean inExpression_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "inExpression_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_IN);
    if (!r) r = inExpression_1_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '!' 'in'
  private static boolean inExpression_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "inExpression_1_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_NOT);
    r = r && consumeToken(b, KW_IN);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'in' blockStatement
  public static boolean inStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "inStatement")) return false;
    if (!nextTokenIs(b, KW_IN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_IN);
    r = r && blockStatement(b, l + 1);
    exit_section_(b, m, IN_STATEMENT, r);
    return r;
  }

  /* ********************************************************** */
  // 'void' | nonVoidInitializer
  public static boolean initializer(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "initializer")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<initializer>");
    r = consumeToken(b, KW_VOID);
    if (!r) r = nonVoidInitializer(b, l + 1);
    exit_section_(b, l, m, INITIALIZER, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // 'interface' Identifier ';' | 'interface' Identifier ( ':' baseClassList )? structBody | 'interface' Identifier templateParameters constraint? ( ':' baseClassList )? structBody | 'interface' Identifier templateParameters ( ':' baseClassList )? constraint? structBody
  public static boolean interfaceDeclaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "interfaceDeclaration")) return false;
    if (!nextTokenIs(b, KW_INTERFACE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = interfaceDeclaration_0(b, l + 1);
    if (!r) r = interfaceDeclaration_1(b, l + 1);
    if (!r) r = interfaceDeclaration_2(b, l + 1);
    if (!r) r = interfaceDeclaration_3(b, l + 1);
    exit_section_(b, m, INTERFACE_DECLARATION, r);
    return r;
  }

  // 'interface' Identifier ';'
  private static boolean interfaceDeclaration_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "interfaceDeclaration_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_INTERFACE);
    r = r && Identifier(b, l + 1);
    r = r && consumeToken(b, OP_SCOLON);
    exit_section_(b, m, null, r);
    return r;
  }

  // 'interface' Identifier ( ':' baseClassList )? structBody
  private static boolean interfaceDeclaration_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "interfaceDeclaration_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_INTERFACE);
    r = r && Identifier(b, l + 1);
    r = r && interfaceDeclaration_1_2(b, l + 1);
    r = r && structBody(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ( ':' baseClassList )?
  private static boolean interfaceDeclaration_1_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "interfaceDeclaration_1_2")) return false;
    interfaceDeclaration_1_2_0(b, l + 1);
    return true;
  }

  // ':' baseClassList
  private static boolean interfaceDeclaration_1_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "interfaceDeclaration_1_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_COLON);
    r = r && baseClassList(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // 'interface' Identifier templateParameters constraint? ( ':' baseClassList )? structBody
  private static boolean interfaceDeclaration_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "interfaceDeclaration_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_INTERFACE);
    r = r && Identifier(b, l + 1);
    r = r && templateParameters(b, l + 1);
    r = r && interfaceDeclaration_2_3(b, l + 1);
    r = r && interfaceDeclaration_2_4(b, l + 1);
    r = r && structBody(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // constraint?
  private static boolean interfaceDeclaration_2_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "interfaceDeclaration_2_3")) return false;
    constraint(b, l + 1);
    return true;
  }

  // ( ':' baseClassList )?
  private static boolean interfaceDeclaration_2_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "interfaceDeclaration_2_4")) return false;
    interfaceDeclaration_2_4_0(b, l + 1);
    return true;
  }

  // ':' baseClassList
  private static boolean interfaceDeclaration_2_4_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "interfaceDeclaration_2_4_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_COLON);
    r = r && baseClassList(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // 'interface' Identifier templateParameters ( ':' baseClassList )? constraint? structBody
  private static boolean interfaceDeclaration_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "interfaceDeclaration_3")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_INTERFACE);
    r = r && Identifier(b, l + 1);
    r = r && templateParameters(b, l + 1);
    r = r && interfaceDeclaration_3_3(b, l + 1);
    r = r && interfaceDeclaration_3_4(b, l + 1);
    r = r && structBody(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ( ':' baseClassList )?
  private static boolean interfaceDeclaration_3_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "interfaceDeclaration_3_3")) return false;
    interfaceDeclaration_3_3_0(b, l + 1);
    return true;
  }

  // ':' baseClassList
  private static boolean interfaceDeclaration_3_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "interfaceDeclaration_3_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_COLON);
    r = r && baseClassList(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // constraint?
  private static boolean interfaceDeclaration_3_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "interfaceDeclaration_3_4")) return false;
    constraint(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // 'invariant' ( '(' ')' )? blockStatement
  public static boolean invariant(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "invariant")) return false;
    if (!nextTokenIs(b, KW_INVARIANT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_INVARIANT);
    r = r && invariant_1(b, l + 1);
    r = r && blockStatement(b, l + 1);
    exit_section_(b, m, INVARIANT, r);
    return r;
  }

  // ( '(' ')' )?
  private static boolean invariant_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "invariant_1")) return false;
    invariant_1_0(b, l + 1);
    return true;
  }

  // '(' ')'
  private static boolean invariant_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "invariant_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_PAR_LEFT);
    r = r && consumeToken(b, OP_PAR_RIGHT);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'is' '(' type Identifier? ')' 'is' '(' type Identifier? ':' typeSpecialization ')' 'is' '(' type Identifier? '=' typeSpecialization ')' 'is' '(' type Identifier? ':' typeSpecialization ',' templateParameterList ')' 'is' '(' type Identifier? '=' typeSpecialization ',' templateParameterList ')'
  public static boolean isExpression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "isExpression")) return false;
    if (!nextTokenIs(b, KW_IS)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_IS);
    r = r && consumeToken(b, OP_PAR_LEFT);
    r = r && type(b, l + 1);
    r = r && isExpression_3(b, l + 1);
    r = r && consumeToken(b, OP_PAR_RIGHT);
    r = r && consumeToken(b, KW_IS);
    r = r && consumeToken(b, OP_PAR_LEFT);
    r = r && type(b, l + 1);
    r = r && isExpression_8(b, l + 1);
    r = r && consumeToken(b, OP_COLON);
    r = r && typeSpecialization(b, l + 1);
    r = r && consumeToken(b, OP_PAR_RIGHT);
    r = r && consumeToken(b, KW_IS);
    r = r && consumeToken(b, OP_PAR_LEFT);
    r = r && type(b, l + 1);
    r = r && isExpression_15(b, l + 1);
    r = r && consumeToken(b, OP_EQ);
    r = r && typeSpecialization(b, l + 1);
    r = r && consumeToken(b, OP_PAR_RIGHT);
    r = r && consumeToken(b, KW_IS);
    r = r && consumeToken(b, OP_PAR_LEFT);
    r = r && type(b, l + 1);
    r = r && isExpression_22(b, l + 1);
    r = r && consumeToken(b, OP_COLON);
    r = r && typeSpecialization(b, l + 1);
    r = r && consumeToken(b, OP_COMMA);
    r = r && templateParameterList(b, l + 1);
    r = r && consumeToken(b, OP_PAR_RIGHT);
    r = r && consumeToken(b, KW_IS);
    r = r && consumeToken(b, OP_PAR_LEFT);
    r = r && type(b, l + 1);
    r = r && isExpression_31(b, l + 1);
    r = r && consumeToken(b, OP_EQ);
    r = r && typeSpecialization(b, l + 1);
    r = r && consumeToken(b, OP_COMMA);
    r = r && templateParameterList(b, l + 1);
    r = r && consumeToken(b, OP_PAR_RIGHT);
    exit_section_(b, m, IS_EXPRESSION, r);
    return r;
  }

  // Identifier?
  private static boolean isExpression_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "isExpression_3")) return false;
    Identifier(b, l + 1);
    return true;
  }

  // Identifier?
  private static boolean isExpression_8(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "isExpression_8")) return false;
    Identifier(b, l + 1);
    return true;
  }

  // Identifier?
  private static boolean isExpression_15(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "isExpression_15")) return false;
    Identifier(b, l + 1);
    return true;
  }

  // Identifier?
  private static boolean isExpression_22(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "isExpression_22")) return false;
    Identifier(b, l + 1);
    return true;
  }

  // Identifier?
  private static boolean isExpression_31(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "isExpression_31")) return false;
    Identifier(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // moduleDeclaration | declaration | statement
  static boolean item_(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "item_")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = moduleDeclaration(b, l + 1);
    if (!r) r = declaration(b, l + 1);
    if (!r) r = statement(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // assignExpression ':' assignExpression
  public static boolean keyValuePair(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "keyValuePair")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<key value pair>");
    r = assignExpression(b, l + 1);
    r = r && consumeToken(b, OP_COLON);
    r = r && assignExpression(b, l + 1);
    exit_section_(b, l, m, KEY_VALUE_PAIR, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // keyValuePair ( ',' keyValuePair )* ','?
  public static boolean keyValuePairs(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "keyValuePairs")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<key value pairs>");
    r = keyValuePair(b, l + 1);
    r = r && keyValuePairs_1(b, l + 1);
    r = r && keyValuePairs_2(b, l + 1);
    exit_section_(b, l, m, KEY_VALUE_PAIRS, r, false, null);
    return r;
  }

  // ( ',' keyValuePair )*
  private static boolean keyValuePairs_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "keyValuePairs_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!keyValuePairs_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "keyValuePairs_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // ',' keyValuePair
  private static boolean keyValuePairs_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "keyValuePairs_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_COMMA);
    r = r && keyValuePair(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ','?
  private static boolean keyValuePairs_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "keyValuePairs_2")) return false;
    consumeToken(b, OP_COMMA);
    return true;
  }

  /* ********************************************************** */
  // Identifier ':' declarationOrStatement?
  public static boolean labeledStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "labeledStatement")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Identifier(b, l + 1);
    r = r && consumeToken(b, OP_COLON);
    r = r && labeledStatement_2(b, l + 1);
    exit_section_(b, m, LABELED_STATEMENT, r);
    return r;
  }

  // declarationOrStatement?
  private static boolean labeledStatement_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "labeledStatement_2")) return false;
    declarationOrStatement(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // Identifier '=>' assignExpression | 'function' type? parameters functionAttribute* '=>' assignExpression | 'delegate' type? parameters functionAttribute* '=>' assignExpression | parameters functionAttribute* '=>' assignExpression
  public static boolean lambdaExpression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "lambdaExpression")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<lambda expression>");
    r = lambdaExpression_0(b, l + 1);
    if (!r) r = lambdaExpression_1(b, l + 1);
    if (!r) r = lambdaExpression_2(b, l + 1);
    if (!r) r = lambdaExpression_3(b, l + 1);
    exit_section_(b, l, m, LAMBDA_EXPRESSION, r, false, null);
    return r;
  }

  // Identifier '=>' assignExpression
  private static boolean lambdaExpression_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "lambdaExpression_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Identifier(b, l + 1);
    r = r && consumeToken(b, OP_LAMBDA_ARROW);
    r = r && assignExpression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // 'function' type? parameters functionAttribute* '=>' assignExpression
  private static boolean lambdaExpression_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "lambdaExpression_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_FUNCTION);
    r = r && lambdaExpression_1_1(b, l + 1);
    r = r && parameters(b, l + 1);
    r = r && lambdaExpression_1_3(b, l + 1);
    r = r && consumeToken(b, OP_LAMBDA_ARROW);
    r = r && assignExpression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // type?
  private static boolean lambdaExpression_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "lambdaExpression_1_1")) return false;
    type(b, l + 1);
    return true;
  }

  // functionAttribute*
  private static boolean lambdaExpression_1_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "lambdaExpression_1_3")) return false;
    int c = current_position_(b);
    while (true) {
      if (!functionAttribute(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "lambdaExpression_1_3", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // 'delegate' type? parameters functionAttribute* '=>' assignExpression
  private static boolean lambdaExpression_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "lambdaExpression_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_DELEGATE);
    r = r && lambdaExpression_2_1(b, l + 1);
    r = r && parameters(b, l + 1);
    r = r && lambdaExpression_2_3(b, l + 1);
    r = r && consumeToken(b, OP_LAMBDA_ARROW);
    r = r && assignExpression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // type?
  private static boolean lambdaExpression_2_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "lambdaExpression_2_1")) return false;
    type(b, l + 1);
    return true;
  }

  // functionAttribute*
  private static boolean lambdaExpression_2_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "lambdaExpression_2_3")) return false;
    int c = current_position_(b);
    while (true) {
      if (!functionAttribute(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "lambdaExpression_2_3", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // parameters functionAttribute* '=>' assignExpression
  private static boolean lambdaExpression_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "lambdaExpression_3")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = parameters(b, l + 1);
    r = r && lambdaExpression_3_1(b, l + 1);
    r = r && consumeToken(b, OP_LAMBDA_ARROW);
    r = r && assignExpression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // functionAttribute*
  private static boolean lambdaExpression_3_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "lambdaExpression_3_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!functionAttribute(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "lambdaExpression_3_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  /* ********************************************************** */
  // 'catch' statementNoCaseNoDefault
  public static boolean lastCatch(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "lastCatch")) return false;
    if (!nextTokenIs(b, KW_CATCH)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_CATCH);
    r = r && statementNoCaseNoDefault(b, l + 1);
    exit_section_(b, m, LAST_CATCH, r);
    return r;
  }

  /* ********************************************************** */
  // 'extern' '(' Identifier ( '++' ( ',' identifierChain )? )? ')'
  public static boolean linkageAttribute(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "linkageAttribute")) return false;
    if (!nextTokenIs(b, KW_EXTERN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_EXTERN);
    r = r && consumeToken(b, OP_PAR_LEFT);
    r = r && Identifier(b, l + 1);
    r = r && linkageAttribute_3(b, l + 1);
    r = r && consumeToken(b, OP_PAR_RIGHT);
    exit_section_(b, m, LINKAGE_ATTRIBUTE, r);
    return r;
  }

  // ( '++' ( ',' identifierChain )? )?
  private static boolean linkageAttribute_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "linkageAttribute_3")) return false;
    linkageAttribute_3_0(b, l + 1);
    return true;
  }

  // '++' ( ',' identifierChain )?
  private static boolean linkageAttribute_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "linkageAttribute_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_PLUS_PLUS);
    r = r && linkageAttribute_3_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ( ',' identifierChain )?
  private static boolean linkageAttribute_3_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "linkageAttribute_3_0_1")) return false;
    linkageAttribute_3_0_1_0(b, l + 1);
    return true;
  }

  // ',' identifierChain
  private static boolean linkageAttribute_3_0_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "linkageAttribute_3_0_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_COMMA);
    r = r && identifierChain(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // functionAttribute | 'immutable' | 'inout' | 'shared' | 'const' | 'return'
  public static boolean memberFunctionAttribute(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "memberFunctionAttribute")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<member function attribute>");
    r = functionAttribute(b, l + 1);
    if (!r) r = consumeToken(b, KW_IMMUTABLE);
    if (!r) r = consumeToken(b, KW_INOUT);
    if (!r) r = consumeToken(b, KW_SHARED);
    if (!r) r = consumeToken(b, KW_CONST);
    if (!r) r = consumeToken(b, KW_RETURN);
    exit_section_(b, l, m, MEMBER_FUNCTION_ATTRIBUTE, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // mixinExpression ';' | templateMixinExpression ';'
  public static boolean mixinDeclaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "mixinDeclaration")) return false;
    if (!nextTokenIs(b, KW_MIXIN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = mixinDeclaration_0(b, l + 1);
    if (!r) r = mixinDeclaration_1(b, l + 1);
    exit_section_(b, m, MIXIN_DECLARATION, r);
    return r;
  }

  // mixinExpression ';'
  private static boolean mixinDeclaration_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "mixinDeclaration_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = mixinExpression(b, l + 1);
    r = r && consumeToken(b, OP_SCOLON);
    exit_section_(b, m, null, r);
    return r;
  }

  // templateMixinExpression ';'
  private static boolean mixinDeclaration_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "mixinDeclaration_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = templateMixinExpression(b, l + 1);
    r = r && consumeToken(b, OP_SCOLON);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'mixin' '(' assignExpression ')'
  public static boolean mixinExpression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "mixinExpression")) return false;
    if (!nextTokenIs(b, KW_MIXIN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_MIXIN);
    r = r && consumeToken(b, OP_PAR_LEFT);
    r = r && assignExpression(b, l + 1);
    r = r && consumeToken(b, OP_PAR_RIGHT);
    exit_section_(b, m, MIXIN_EXPRESSION, r);
    return r;
  }

  /* ********************************************************** */
  // 'mixin' templateDeclaration
  public static boolean mixinTemplateDeclaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "mixinTemplateDeclaration")) return false;
    if (!nextTokenIs(b, KW_MIXIN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_MIXIN);
    r = r && templateDeclaration(b, l + 1);
    exit_section_(b, m, MIXIN_TEMPLATE_DECLARATION, r);
    return r;
  }

  /* ********************************************************** */
  // symbol | typeofExpression '.' identifierOrTemplateChain
  public static boolean mixinTemplateName(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "mixinTemplateName")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<mixin template name>");
    r = symbol(b, l + 1);
    if (!r) r = mixinTemplateName_1(b, l + 1);
    exit_section_(b, l, m, MIXIN_TEMPLATE_NAME, r, false, null);
    return r;
  }

  // typeofExpression '.' identifierOrTemplateChain
  private static boolean mixinTemplateName_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "mixinTemplateName_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = typeofExpression(b, l + 1);
    r = r && consumeToken(b, OP_DOT);
    r = r && identifierOrTemplateChain(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // deprecated? 'module' identifierChain ';'
  public static boolean moduleDeclaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "moduleDeclaration")) return false;
    if (!nextTokenIs(b, "<module declaration>", KW_DEPRECATED, KW_MODULE)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<module declaration>");
    r = moduleDeclaration_0(b, l + 1);
    r = r && consumeToken(b, KW_MODULE);
    r = r && identifierChain(b, l + 1);
    r = r && consumeToken(b, OP_SCOLON);
    exit_section_(b, l, m, MODULE_DECLARATION, r, false, null);
    return r;
  }

  // deprecated?
  private static boolean moduleDeclaration_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "moduleDeclaration_0")) return false;
    deprecated(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // powExpression [('*' | '/' | '%') mulExpression]
  public static boolean mulExpression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "mulExpression")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<mul expression>");
    r = powExpression(b, l + 1);
    r = r && mulExpression_1(b, l + 1);
    exit_section_(b, l, m, MUL_EXPRESSION, r, false, null);
    return r;
  }

  // [('*' | '/' | '%') mulExpression]
  private static boolean mulExpression_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "mulExpression_1")) return false;
    mulExpression_1_0(b, l + 1);
    return true;
  }

  // ('*' | '/' | '%') mulExpression
  private static boolean mulExpression_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "mulExpression_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = mulExpression_1_0_0(b, l + 1);
    r = r && mulExpression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '*' | '/' | '%'
  private static boolean mulExpression_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "mulExpression_1_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_ASTERISK);
    if (!r) r = consumeToken(b, OP_DIV);
    if (!r) r = consumeToken(b, OP_MOD);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'new' arguments? 'class' arguments? baseClassList? structBody
  public static boolean newAnonClassExpression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "newAnonClassExpression")) return false;
    if (!nextTokenIs(b, KW_NEW)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_NEW);
    r = r && newAnonClassExpression_1(b, l + 1);
    r = r && consumeToken(b, KW_CLASS);
    r = r && newAnonClassExpression_3(b, l + 1);
    r = r && newAnonClassExpression_4(b, l + 1);
    r = r && structBody(b, l + 1);
    exit_section_(b, m, NEW_ANON_CLASS_EXPRESSION, r);
    return r;
  }

  // arguments?
  private static boolean newAnonClassExpression_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "newAnonClassExpression_1")) return false;
    arguments(b, l + 1);
    return true;
  }

  // arguments?
  private static boolean newAnonClassExpression_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "newAnonClassExpression_3")) return false;
    arguments(b, l + 1);
    return true;
  }

  // baseClassList?
  private static boolean newAnonClassExpression_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "newAnonClassExpression_4")) return false;
    baseClassList(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // 'new' type ( ( '[' assignExpression ']' ) | arguments )? | newAnonClassExpression
  public static boolean newExpression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "newExpression")) return false;
    if (!nextTokenIs(b, KW_NEW)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = newExpression_0(b, l + 1);
    if (!r) r = newAnonClassExpression(b, l + 1);
    exit_section_(b, m, NEW_EXPRESSION, r);
    return r;
  }

  // 'new' type ( ( '[' assignExpression ']' ) | arguments )?
  private static boolean newExpression_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "newExpression_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_NEW);
    r = r && type(b, l + 1);
    r = r && newExpression_0_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ( ( '[' assignExpression ']' ) | arguments )?
  private static boolean newExpression_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "newExpression_0_2")) return false;
    newExpression_0_2_0(b, l + 1);
    return true;
  }

  // ( '[' assignExpression ']' ) | arguments
  private static boolean newExpression_0_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "newExpression_0_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = newExpression_0_2_0_0(b, l + 1);
    if (!r) r = arguments(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '[' assignExpression ']'
  private static boolean newExpression_0_2_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "newExpression_0_2_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_BRACKET_LEFT);
    r = r && assignExpression(b, l + 1);
    r = r && consumeToken(b, OP_BRACKET_RIGHT);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // assignExpression | arrayInitializer | structInitializer
  public static boolean nonVoidInitializer(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "nonVoidInitializer")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<non void initializer>");
    r = assignExpression(b, l + 1);
    if (!r) r = arrayInitializer(b, l + 1);
    if (!r) r = structInitializer(b, l + 1);
    exit_section_(b, l, m, NON_VOID_INITIALIZER, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // asmExp | asmExp ',' operands
  public static boolean operands(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "operands")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<operands>");
    r = asmExp(b, l + 1);
    if (!r) r = operands_1(b, l + 1);
    exit_section_(b, l, m, OPERANDS, r, false, null);
    return r;
  }

  // asmExp ',' operands
  private static boolean operands_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "operands_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = asmExp(b, l + 1);
    r = r && consumeToken(b, OP_COMMA);
    r = r && operands(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // xorExpression ['|' orExpression]
  public static boolean orExpression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "orExpression")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<or expression>");
    r = xorExpression(b, l + 1);
    r = r && orExpression_1(b, l + 1);
    exit_section_(b, l, m, OR_EXPRESSION, r, false, null);
    return r;
  }

  // ['|' orExpression]
  private static boolean orExpression_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "orExpression_1")) return false;
    orExpression_1_0(b, l + 1);
    return true;
  }

  // '|' orExpression
  private static boolean orExpression_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "orExpression_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_OR);
    r = r && orExpression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // andAndExpression ['||' orOrExpression]
  public static boolean orOrExpression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "orOrExpression")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<or or expression>");
    r = andAndExpression(b, l + 1);
    r = r && orOrExpression_1(b, l + 1);
    exit_section_(b, l, m, OR_OR_EXPRESSION, r, false, null);
    return r;
  }

  // ['||' orOrExpression]
  private static boolean orOrExpression_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "orOrExpression_1")) return false;
    orOrExpression_1_0(b, l + 1);
    return true;
  }

  // '||' orOrExpression
  private static boolean orOrExpression_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "orOrExpression_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_BOOL_OR);
    r = r && orOrExpression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'out' ( '(' Identifier ')' )? blockStatement
  public static boolean outStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "outStatement")) return false;
    if (!nextTokenIs(b, KW_OUT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_OUT);
    r = r && outStatement_1(b, l + 1);
    r = r && blockStatement(b, l + 1);
    exit_section_(b, m, OUT_STATEMENT, r);
    return r;
  }

  // ( '(' Identifier ')' )?
  private static boolean outStatement_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "outStatement_1")) return false;
    outStatement_1_0(b, l + 1);
    return true;
  }

  // '(' Identifier ')'
  private static boolean outStatement_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "outStatement_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_PAR_LEFT);
    r = r && Identifier(b, l + 1);
    r = r && consumeToken(b, OP_PAR_RIGHT);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // parameterAttribute* type (Identifier? '...' | (Identifier? ('=' assignExpression)?))?
  public static boolean parameter(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parameter")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<parameter>");
    r = parameter_0(b, l + 1);
    r = r && type(b, l + 1);
    r = r && parameter_2(b, l + 1);
    exit_section_(b, l, m, PARAMETER, r, false, null);
    return r;
  }

  // parameterAttribute*
  private static boolean parameter_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parameter_0")) return false;
    int c = current_position_(b);
    while (true) {
      if (!parameterAttribute(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "parameter_0", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // (Identifier? '...' | (Identifier? ('=' assignExpression)?))?
  private static boolean parameter_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parameter_2")) return false;
    parameter_2_0(b, l + 1);
    return true;
  }

  // Identifier? '...' | (Identifier? ('=' assignExpression)?)
  private static boolean parameter_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parameter_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = parameter_2_0_0(b, l + 1);
    if (!r) r = parameter_2_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // Identifier? '...'
  private static boolean parameter_2_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parameter_2_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = parameter_2_0_0_0(b, l + 1);
    r = r && consumeToken(b, OP_TRIPLEDOT);
    exit_section_(b, m, null, r);
    return r;
  }

  // Identifier?
  private static boolean parameter_2_0_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parameter_2_0_0_0")) return false;
    Identifier(b, l + 1);
    return true;
  }

  // Identifier? ('=' assignExpression)?
  private static boolean parameter_2_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parameter_2_0_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = parameter_2_0_1_0(b, l + 1);
    r = r && parameter_2_0_1_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // Identifier?
  private static boolean parameter_2_0_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parameter_2_0_1_0")) return false;
    Identifier(b, l + 1);
    return true;
  }

  // ('=' assignExpression)?
  private static boolean parameter_2_0_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parameter_2_0_1_1")) return false;
    parameter_2_0_1_1_0(b, l + 1);
    return true;
  }

  // '=' assignExpression
  private static boolean parameter_2_0_1_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parameter_2_0_1_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_EQ);
    r = r && assignExpression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // typeConstructor | 'final' | 'in' | 'lazy' | 'out' | 'ref' | 'scope' | 'auto' | 'return'
  public static boolean parameterAttribute(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parameterAttribute")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<parameter attribute>");
    r = typeConstructor(b, l + 1);
    if (!r) r = consumeToken(b, KW_FINAL);
    if (!r) r = consumeToken(b, KW_IN);
    if (!r) r = consumeToken(b, KW_LAZY);
    if (!r) r = consumeToken(b, KW_OUT);
    if (!r) r = consumeToken(b, KW_REF);
    if (!r) r = consumeToken(b, KW_SCOPE);
    if (!r) r = consumeToken(b, KW_AUTO);
    if (!r) r = consumeToken(b, KW_RETURN);
    exit_section_(b, l, m, PARAMETER_ATTRIBUTE, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // '(' parameter ( ',' parameter )* ( ',' '...' )? ')' | '(' '...' ')' | '(' ')'
  public static boolean parameters(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parameters")) return false;
    if (!nextTokenIs(b, OP_PAR_LEFT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = parameters_0(b, l + 1);
    if (!r) r = parameters_1(b, l + 1);
    if (!r) r = parameters_2(b, l + 1);
    exit_section_(b, m, PARAMETERS, r);
    return r;
  }

  // '(' parameter ( ',' parameter )* ( ',' '...' )? ')'
  private static boolean parameters_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parameters_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_PAR_LEFT);
    r = r && parameter(b, l + 1);
    r = r && parameters_0_2(b, l + 1);
    r = r && parameters_0_3(b, l + 1);
    r = r && consumeToken(b, OP_PAR_RIGHT);
    exit_section_(b, m, null, r);
    return r;
  }

  // ( ',' parameter )*
  private static boolean parameters_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parameters_0_2")) return false;
    int c = current_position_(b);
    while (true) {
      if (!parameters_0_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "parameters_0_2", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // ',' parameter
  private static boolean parameters_0_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parameters_0_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_COMMA);
    r = r && parameter(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ( ',' '...' )?
  private static boolean parameters_0_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parameters_0_3")) return false;
    parameters_0_3_0(b, l + 1);
    return true;
  }

  // ',' '...'
  private static boolean parameters_0_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parameters_0_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_COMMA);
    r = r && consumeToken(b, OP_TRIPLEDOT);
    exit_section_(b, m, null, r);
    return r;
  }

  // '(' '...' ')'
  private static boolean parameters_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parameters_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_PAR_LEFT);
    r = r && consumeToken(b, OP_TRIPLEDOT);
    r = r && consumeToken(b, OP_PAR_RIGHT);
    exit_section_(b, m, null, r);
    return r;
  }

  // '(' ')'
  private static boolean parameters_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parameters_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_PAR_LEFT);
    r = r && consumeToken(b, OP_PAR_RIGHT);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'this' '(' 'this' ')' memberFunctionAttribute* ( functionBody | ';' )
  public static boolean postblit(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "postblit")) return false;
    if (!nextTokenIs(b, KW_THIS)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_THIS);
    r = r && consumeToken(b, OP_PAR_LEFT);
    r = r && consumeToken(b, KW_THIS);
    r = r && consumeToken(b, OP_PAR_RIGHT);
    r = r && postblit_4(b, l + 1);
    r = r && postblit_5(b, l + 1);
    exit_section_(b, m, POSTBLIT, r);
    return r;
  }

  // memberFunctionAttribute*
  private static boolean postblit_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "postblit_4")) return false;
    int c = current_position_(b);
    while (true) {
      if (!memberFunctionAttribute(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "postblit_4", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // functionBody | ';'
  private static boolean postblit_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "postblit_5")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = functionBody(b, l + 1);
    if (!r) r = consumeToken(b, OP_SCOLON);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // unaryExpression ['^^' powExpression]
  public static boolean powExpression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "powExpression")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<pow expression>");
    r = unaryExpression(b, l + 1);
    r = r && powExpression_1(b, l + 1);
    exit_section_(b, l, m, POW_EXPRESSION, r, false, null);
    return r;
  }

  // ['^^' powExpression]
  private static boolean powExpression_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "powExpression_1")) return false;
    powExpression_1_0(b, l + 1);
    return true;
  }

  // '^^' powExpression
  private static boolean powExpression_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "powExpression_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_POW);
    r = r && powExpression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // pragmaExpression ';'
  public static boolean pragmaDeclaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "pragmaDeclaration")) return false;
    if (!nextTokenIs(b, OP_PAR_LEFT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = pragmaExpression(b, l + 1);
    r = r && consumeToken(b, OP_SCOLON);
    exit_section_(b, m, PRAGMA_DECLARATION, r);
    return r;
  }

  /* ********************************************************** */
  // '(' Identifier ( ',' argumentList )? ')'
  public static boolean pragmaExpression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "pragmaExpression")) return false;
    if (!nextTokenIs(b, OP_PAR_LEFT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_PAR_LEFT);
    r = r && Identifier(b, l + 1);
    r = r && pragmaExpression_2(b, l + 1);
    r = r && consumeToken(b, OP_PAR_RIGHT);
    exit_section_(b, m, PRAGMA_EXPRESSION, r);
    return r;
  }

  // ( ',' argumentList )?
  private static boolean pragmaExpression_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "pragmaExpression_2")) return false;
    pragmaExpression_2_0(b, l + 1);
    return true;
  }

  // ',' argumentList
  private static boolean pragmaExpression_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "pragmaExpression_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_COMMA);
    r = r && argumentList(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // identifierOrTemplateInstance
  //      | '.' identifierOrTemplateInstance
  //      | builtinType '.' Identifier
  //      | typeofExpression
  //      | typeidExpression
  //      | vector
  //      | arrayLiteral
  //      | assocArrayLiteral
  //      | '(' expression ')'
  //      | isExpression
  //      | lambdaExpression
  //      | functionLiteralExpression
  //      | traitsExpression
  //      | mixinExpression
  //      | importExpression
  //      | '$'
  //      | 'this'
  //      | 'super'
  //      | 'null'
  //      | 'true'
  //      | 'false'
  //      | '__DATE__'
  //      | '__TIME__'
  //      | '__TIMESTAMP__'
  //      | '__VENDOR__'
  //      | '__VERSION__'
  //      | '__FILE__'
  //      | '__LINE__'
  //      | '__MODULE__'
  //      | '__FUNCTION__'
  //      | '__PRETTY_FUNCTION__'
  //      | INTEGER_LITERAL
  //      | FLOAT_LITERAL
  //      | StringLiteral+
  //      | CHARACTER_LITERAL
  public static boolean primaryExpression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "primaryExpression")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<primary expression>");
    r = identifierOrTemplateInstance(b, l + 1);
    if (!r) r = primaryExpression_1(b, l + 1);
    if (!r) r = primaryExpression_2(b, l + 1);
    if (!r) r = typeofExpression(b, l + 1);
    if (!r) r = typeidExpression(b, l + 1);
    if (!r) r = vector(b, l + 1);
    if (!r) r = arrayLiteral(b, l + 1);
    if (!r) r = assocArrayLiteral(b, l + 1);
    if (!r) r = primaryExpression_8(b, l + 1);
    if (!r) r = isExpression(b, l + 1);
    if (!r) r = lambdaExpression(b, l + 1);
    if (!r) r = functionLiteralExpression(b, l + 1);
    if (!r) r = traitsExpression(b, l + 1);
    if (!r) r = mixinExpression(b, l + 1);
    if (!r) r = importExpression(b, l + 1);
    if (!r) r = consumeToken(b, OP_DOLLAR);
    if (!r) r = consumeToken(b, KW_THIS);
    if (!r) r = consumeToken(b, KW_SUPER);
    if (!r) r = consumeToken(b, KW_NULL);
    if (!r) r = consumeToken(b, KW_TRUE);
    if (!r) r = consumeToken(b, KW_FALSE);
    if (!r) r = consumeToken(b, "__DATE__");
    if (!r) r = consumeToken(b, "__TIME__");
    if (!r) r = consumeToken(b, "__TIMESTAMP__");
    if (!r) r = consumeToken(b, "__VENDOR__");
    if (!r) r = consumeToken(b, "__VERSION__");
    if (!r) r = consumeToken(b, KW___FILE__);
    if (!r) r = consumeToken(b, KW___LINE__);
    if (!r) r = consumeToken(b, KW___MODULE__);
    if (!r) r = consumeToken(b, KW___FUNCTION__);
    if (!r) r = consumeToken(b, KW___PRETTY_FUNCTION__);
    if (!r) r = consumeToken(b, INTEGER_LITERAL);
    if (!r) r = consumeToken(b, FLOAT_LITERAL);
    if (!r) r = primaryExpression_33(b, l + 1);
    if (!r) r = consumeToken(b, CHARACTER_LITERAL);
    exit_section_(b, l, m, PRIMARY_EXPRESSION, r, false, null);
    return r;
  }

  // '.' identifierOrTemplateInstance
  private static boolean primaryExpression_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "primaryExpression_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_DOT);
    r = r && identifierOrTemplateInstance(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // builtinType '.' Identifier
  private static boolean primaryExpression_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "primaryExpression_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = builtinType(b, l + 1);
    r = r && consumeToken(b, OP_DOT);
    r = r && Identifier(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '(' expression ')'
  private static boolean primaryExpression_8(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "primaryExpression_8")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_PAR_LEFT);
    r = r && expression(b, l + 1);
    r = r && consumeToken(b, OP_PAR_RIGHT);
    exit_section_(b, m, null, r);
    return r;
  }

  // StringLiteral+
  private static boolean primaryExpression_33(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "primaryExpression_33")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = StringLiteral(b, l + 1);
    int c = current_position_(b);
    while (r) {
      if (!StringLiteral(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "primaryExpression_33", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // Identifier | Identifier '(' INTEGER_LITERAL ')'
  public static boolean register(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "register")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Identifier(b, l + 1);
    if (!r) r = register_1(b, l + 1);
    exit_section_(b, m, REGISTER, r);
    return r;
  }

  // Identifier '(' INTEGER_LITERAL ')'
  private static boolean register_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "register_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Identifier(b, l + 1);
    r = r && consumeToken(b, OP_PAR_LEFT);
    r = r && consumeToken(b, INTEGER_LITERAL);
    r = r && consumeToken(b, OP_PAR_RIGHT);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // shiftExpression [relOperator relExpression]
  public static boolean relExpression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "relExpression")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<rel expression>");
    r = shiftExpression(b, l + 1);
    r = r && relExpression_1(b, l + 1);
    exit_section_(b, l, m, REL_EXPRESSION, r, false, null);
    return r;
  }

  // [relOperator relExpression]
  private static boolean relExpression_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "relExpression_1")) return false;
    relExpression_1_0(b, l + 1);
    return true;
  }

  // relOperator relExpression
  private static boolean relExpression_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "relExpression_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = relOperator(b, l + 1);
    r = r && relExpression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // '<'
  //      | '<='
  //      | '>'
  //      | '>='
  //      | '!<>='
  //      | '!<>'
  //      | '<>'
  //      | '<>='
  //      | '!>'
  //      | '!>='
  //      | '!<'
  //      | '!<='
  public static boolean relOperator(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "relOperator")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<rel operator>");
    r = consumeToken(b, OP_LESS);
    if (!r) r = consumeToken(b, OP_LESS_EQ);
    if (!r) r = consumeToken(b, OP_GT);
    if (!r) r = consumeToken(b, OP_GT_EQ);
    if (!r) r = consumeToken(b, OP_UNORD);
    if (!r) r = consumeToken(b, OP_UNORD_EQ);
    if (!r) r = consumeToken(b, OP_LESS_GR);
    if (!r) r = consumeToken(b, OP_LESS_GR_EQ);
    if (!r) r = consumeToken(b, OP_NOT_GR);
    if (!r) r = consumeToken(b, OP_NOT_GR_EQ);
    if (!r) r = consumeToken(b, OP_NOT_LESS);
    if (!r) r = consumeToken(b, OP_NOT_LESS_EQ);
    exit_section_(b, l, m, REL_OPERATOR, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // 'return' expression? ';'
  public static boolean returnStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "returnStatement")) return false;
    if (!nextTokenIs(b, KW_RETURN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_RETURN);
    r = r && returnStatement_1(b, l + 1);
    r = r && consumeToken(b, OP_SCOLON);
    exit_section_(b, m, RETURN_STATEMENT, r);
    return r;
  }

  // expression?
  private static boolean returnStatement_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "returnStatement_1")) return false;
    expression(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // 'scope' '(' Identifier ')' statementNoCaseNoDefault
  public static boolean scopeGuardStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "scopeGuardStatement")) return false;
    if (!nextTokenIs(b, KW_SCOPE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_SCOPE);
    r = r && consumeToken(b, OP_PAR_LEFT);
    r = r && Identifier(b, l + 1);
    r = r && consumeToken(b, OP_PAR_RIGHT);
    r = r && statementNoCaseNoDefault(b, l + 1);
    exit_section_(b, m, SCOPE_GUARD_STATEMENT, r);
    return r;
  }

  /* ********************************************************** */
  // 'shared' 'static' 'this' '(' ')' functionBody
  public static boolean sharedStaticConstructor(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "sharedStaticConstructor")) return false;
    if (!nextTokenIs(b, KW_SHARED)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_SHARED);
    r = r && consumeToken(b, KW_STATIC);
    r = r && consumeToken(b, KW_THIS);
    r = r && consumeToken(b, OP_PAR_LEFT);
    r = r && consumeToken(b, OP_PAR_RIGHT);
    r = r && functionBody(b, l + 1);
    exit_section_(b, m, SHARED_STATIC_CONSTRUCTOR, r);
    return r;
  }

  /* ********************************************************** */
  // 'shared' 'static' '~' 'this' '(' ')' functionBody
  public static boolean sharedStaticDestructor(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "sharedStaticDestructor")) return false;
    if (!nextTokenIs(b, KW_SHARED)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_SHARED);
    r = r && consumeToken(b, KW_STATIC);
    r = r && consumeToken(b, OP_TILDA);
    r = r && consumeToken(b, KW_THIS);
    r = r && consumeToken(b, OP_PAR_LEFT);
    r = r && consumeToken(b, OP_PAR_RIGHT);
    r = r && functionBody(b, l + 1);
    exit_section_(b, m, SHARED_STATIC_DESTRUCTOR, r);
    return r;
  }

  /* ********************************************************** */
  // addExpression [('<<' | '>>' | '>>>') shiftExpression]
  public static boolean shiftExpression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "shiftExpression")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<shift expression>");
    r = addExpression(b, l + 1);
    r = r && shiftExpression_1(b, l + 1);
    exit_section_(b, l, m, SHIFT_EXPRESSION, r, false, null);
    return r;
  }

  // [('<<' | '>>' | '>>>') shiftExpression]
  private static boolean shiftExpression_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "shiftExpression_1")) return false;
    shiftExpression_1_0(b, l + 1);
    return true;
  }

  // ('<<' | '>>' | '>>>') shiftExpression
  private static boolean shiftExpression_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "shiftExpression_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = shiftExpression_1_0_0(b, l + 1);
    r = r && shiftExpression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '<<' | '>>' | '>>>'
  private static boolean shiftExpression_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "shiftExpression_1_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_SH_LEFT);
    if (!r) r = consumeToken(b, OP_SH_RIGHT);
    if (!r) r = consumeToken(b, OP_USH_RIGHT);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // ( Identifier '=' )? identifierChain
  public static boolean singleImport(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "singleImport")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = singleImport_0(b, l + 1);
    r = r && identifierChain(b, l + 1);
    exit_section_(b, m, SINGLE_IMPORT, r);
    return r;
  }

  // ( Identifier '=' )?
  private static boolean singleImport_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "singleImport_0")) return false;
    singleImport_0_0(b, l + 1);
    return true;
  }

  // Identifier '='
  private static boolean singleImport_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "singleImport_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Identifier(b, l + 1);
    r = r && consumeToken(b, OP_EQ);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // statementNoCaseNoDefault | caseStatement | caseRangeStatement | defaultStatement
  public static boolean statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "statement")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<statement>");
    r = statementNoCaseNoDefault(b, l + 1);
    if (!r) r = caseStatement(b, l + 1);
    if (!r) r = caseRangeStatement(b, l + 1);
    if (!r) r = defaultStatement(b, l + 1);
    exit_section_(b, l, m, STATEMENT, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // labeledStatement
  // | blockStatement
  // | ifStatement
  // | whileStatement
  // | doStatement
  // | forStatement
  // | foreachStatement
  // | switchStatement
  // | finalSwitchStatement
  // | continueStatement
  // | breakStatement
  // | returnStatement
  // | gotoStatement
  // | withStatement
  // | synchronizedStatement
  // | tryStatement
  // | throwStatement
  // | scopeGuardStatement
  // | asmStatement
  // | conditionalStatement
  // | staticAssertStatement
  // | versionSpecification
  // | debugSpecification
  // | expressionStatement
  // | functionCallStatement
  public static boolean statementNoCaseNoDefault(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "statementNoCaseNoDefault")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<statement no case no default>");
    r = labeledStatement(b, l + 1);
    if (!r) r = blockStatement(b, l + 1);
    if (!r) r = ifStatement(b, l + 1);
    if (!r) r = whileStatement(b, l + 1);
    if (!r) r = doStatement(b, l + 1);
    if (!r) r = forStatement(b, l + 1);
    if (!r) r = foreachStatement(b, l + 1);
    if (!r) r = switchStatement(b, l + 1);
    if (!r) r = finalSwitchStatement(b, l + 1);
    if (!r) r = continueStatement(b, l + 1);
    if (!r) r = breakStatement(b, l + 1);
    if (!r) r = returnStatement(b, l + 1);
    if (!r) r = gotoStatement(b, l + 1);
    if (!r) r = withStatement(b, l + 1);
    if (!r) r = synchronizedStatement(b, l + 1);
    if (!r) r = tryStatement(b, l + 1);
    if (!r) r = throwStatement(b, l + 1);
    if (!r) r = scopeGuardStatement(b, l + 1);
    if (!r) r = asmStatement(b, l + 1);
    if (!r) r = conditionalStatement(b, l + 1);
    if (!r) r = staticAssertStatement(b, l + 1);
    if (!r) r = versionSpecification(b, l + 1);
    if (!r) r = debugSpecification(b, l + 1);
    if (!r) r = expressionStatement(b, l + 1);
    if (!r) r = functionCallStatement(b, l + 1);
    exit_section_(b, l, m, STATEMENT_NO_CASE_NO_DEFAULT, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // staticAssertStatement
  public static boolean staticAssertDeclaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "staticAssertDeclaration")) return false;
    if (!nextTokenIs(b, KW_STATIC)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = staticAssertStatement(b, l + 1);
    exit_section_(b, m, STATIC_ASSERT_DECLARATION, r);
    return r;
  }

  /* ********************************************************** */
  // 'static' assertExpression ';'
  public static boolean staticAssertStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "staticAssertStatement")) return false;
    if (!nextTokenIs(b, KW_STATIC)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_STATIC);
    r = r && assertExpression(b, l + 1);
    r = r && consumeToken(b, OP_SCOLON);
    exit_section_(b, m, STATIC_ASSERT_STATEMENT, r);
    return r;
  }

  /* ********************************************************** */
  // 'static' 'this' '(' ')' functionBody
  public static boolean staticConstructor(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "staticConstructor")) return false;
    if (!nextTokenIs(b, KW_STATIC)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_STATIC);
    r = r && consumeToken(b, KW_THIS);
    r = r && consumeToken(b, OP_PAR_LEFT);
    r = r && consumeToken(b, OP_PAR_RIGHT);
    r = r && functionBody(b, l + 1);
    exit_section_(b, m, STATIC_CONSTRUCTOR, r);
    return r;
  }

  /* ********************************************************** */
  // 'static' '~' 'this' '(' ')' functionBody
  public static boolean staticDestructor(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "staticDestructor")) return false;
    if (!nextTokenIs(b, KW_STATIC)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_STATIC);
    r = r && consumeToken(b, OP_TILDA);
    r = r && consumeToken(b, KW_THIS);
    r = r && consumeToken(b, OP_PAR_LEFT);
    r = r && consumeToken(b, OP_PAR_RIGHT);
    r = r && functionBody(b, l + 1);
    exit_section_(b, m, STATIC_DESTRUCTOR, r);
    return r;
  }

  /* ********************************************************** */
  // 'static' 'if' '(' assignExpression ')'
  public static boolean staticIfCondition(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "staticIfCondition")) return false;
    if (!nextTokenIs(b, KW_STATIC)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_STATIC);
    r = r && consumeToken(b, KW_IF);
    r = r && consumeToken(b, OP_PAR_LEFT);
    r = r && assignExpression(b, l + 1);
    r = r && consumeToken(b, OP_PAR_RIGHT);
    exit_section_(b, m, STATIC_IF_CONDITION, r);
    return r;
  }

  /* ********************************************************** */
  // alignAttribute | linkageAttribute | atAttribute | typeConstructor | deprecated | 'abstract' | 'auto' | 'enum' | 'extern' | 'final' | 'nothrow' | 'override' | 'pure' | 'ref' | '__gshared' | 'scope' | 'static' | 'synchronized'
  public static boolean storageClass(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "storageClass")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<storage class>");
    r = alignAttribute(b, l + 1);
    if (!r) r = linkageAttribute(b, l + 1);
    if (!r) r = atAttribute(b, l + 1);
    if (!r) r = typeConstructor(b, l + 1);
    if (!r) r = deprecated(b, l + 1);
    if (!r) r = consumeToken(b, KW_ABSTRACT);
    if (!r) r = consumeToken(b, KW_AUTO);
    if (!r) r = consumeToken(b, KW_ENUM);
    if (!r) r = consumeToken(b, KW_EXTERN);
    if (!r) r = consumeToken(b, KW_FINAL);
    if (!r) r = consumeToken(b, KW_NOTHROW);
    if (!r) r = consumeToken(b, KW_OVERRIDE);
    if (!r) r = consumeToken(b, KW_PURE);
    if (!r) r = consumeToken(b, KW_REF);
    if (!r) r = consumeToken(b, KW___GSHARED);
    if (!r) r = consumeToken(b, KW_SCOPE);
    if (!r) r = consumeToken(b, KW_STATIC);
    if (!r) r = consumeToken(b, KW_SYNCHRONIZED);
    exit_section_(b, l, m, STORAGE_CLASS, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // '{' declaration* '}'
  public static boolean structBody(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "structBody")) return false;
    if (!nextTokenIs(b, OP_BRACES_LEFT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_BRACES_LEFT);
    r = r && structBody_1(b, l + 1);
    r = r && consumeToken(b, OP_BRACES_RIGHT);
    exit_section_(b, m, STRUCT_BODY, r);
    return r;
  }

  // declaration*
  private static boolean structBody_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "structBody_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!declaration(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "structBody_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  /* ********************************************************** */
  // 'struct' Identifier? ( templateParameters constraint? structBody | ( structBody | ';' ) )
  public static boolean structDeclaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "structDeclaration")) return false;
    if (!nextTokenIs(b, KW_STRUCT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_STRUCT);
    r = r && structDeclaration_1(b, l + 1);
    r = r && structDeclaration_2(b, l + 1);
    exit_section_(b, m, STRUCT_DECLARATION, r);
    return r;
  }

  // Identifier?
  private static boolean structDeclaration_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "structDeclaration_1")) return false;
    Identifier(b, l + 1);
    return true;
  }

  // templateParameters constraint? structBody | ( structBody | ';' )
  private static boolean structDeclaration_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "structDeclaration_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = structDeclaration_2_0(b, l + 1);
    if (!r) r = structDeclaration_2_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // templateParameters constraint? structBody
  private static boolean structDeclaration_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "structDeclaration_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = templateParameters(b, l + 1);
    r = r && structDeclaration_2_0_1(b, l + 1);
    r = r && structBody(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // constraint?
  private static boolean structDeclaration_2_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "structDeclaration_2_0_1")) return false;
    constraint(b, l + 1);
    return true;
  }

  // structBody | ';'
  private static boolean structDeclaration_2_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "structDeclaration_2_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = structBody(b, l + 1);
    if (!r) r = consumeToken(b, OP_SCOLON);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // '{' structMemberInitializers? '}'
  public static boolean structInitializer(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "structInitializer")) return false;
    if (!nextTokenIs(b, OP_BRACES_LEFT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_BRACES_LEFT);
    r = r && structInitializer_1(b, l + 1);
    r = r && consumeToken(b, OP_BRACES_RIGHT);
    exit_section_(b, m, STRUCT_INITIALIZER, r);
    return r;
  }

  // structMemberInitializers?
  private static boolean structInitializer_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "structInitializer_1")) return false;
    structMemberInitializers(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // ( Identifier ':' )? nonVoidInitializer
  public static boolean structMemberInitializer(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "structMemberInitializer")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<struct member initializer>");
    r = structMemberInitializer_0(b, l + 1);
    r = r && nonVoidInitializer(b, l + 1);
    exit_section_(b, l, m, STRUCT_MEMBER_INITIALIZER, r, false, null);
    return r;
  }

  // ( Identifier ':' )?
  private static boolean structMemberInitializer_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "structMemberInitializer_0")) return false;
    structMemberInitializer_0_0(b, l + 1);
    return true;
  }

  // Identifier ':'
  private static boolean structMemberInitializer_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "structMemberInitializer_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Identifier(b, l + 1);
    r = r && consumeToken(b, OP_COLON);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // structMemberInitializer ( ',' structMemberInitializer? )*
  public static boolean structMemberInitializers(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "structMemberInitializers")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<struct member initializers>");
    r = structMemberInitializer(b, l + 1);
    r = r && structMemberInitializers_1(b, l + 1);
    exit_section_(b, l, m, STRUCT_MEMBER_INITIALIZERS, r, false, null);
    return r;
  }

  // ( ',' structMemberInitializer? )*
  private static boolean structMemberInitializers_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "structMemberInitializers_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!structMemberInitializers_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "structMemberInitializers_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // ',' structMemberInitializer?
  private static boolean structMemberInitializers_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "structMemberInitializers_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_COMMA);
    r = r && structMemberInitializers_1_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // structMemberInitializer?
  private static boolean structMemberInitializers_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "structMemberInitializers_1_0_1")) return false;
    structMemberInitializer(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // 'switch' '(' expression ')' statement
  public static boolean switchStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "switchStatement")) return false;
    if (!nextTokenIs(b, KW_SWITCH)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_SWITCH);
    r = r && consumeToken(b, OP_PAR_LEFT);
    r = r && expression(b, l + 1);
    r = r && consumeToken(b, OP_PAR_RIGHT);
    r = r && statement(b, l + 1);
    exit_section_(b, m, SWITCH_STATEMENT, r);
    return r;
  }

  /* ********************************************************** */
  // '.'? identifierOrTemplateChain
  public static boolean symbol(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "symbol")) return false;
    if (!nextTokenIs(b, "<symbol>", OP_DOT, ID)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<symbol>");
    r = symbol_0(b, l + 1);
    r = r && identifierOrTemplateChain(b, l + 1);
    exit_section_(b, l, m, SYMBOL, r, false, null);
    return r;
  }

  // '.'?
  private static boolean symbol_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "symbol_0")) return false;
    consumeToken(b, OP_DOT);
    return true;
  }

  /* ********************************************************** */
  // 'synchronized' ( '(' expression ')' )? statementNoCaseNoDefault
  public static boolean synchronizedStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "synchronizedStatement")) return false;
    if (!nextTokenIs(b, KW_SYNCHRONIZED)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_SYNCHRONIZED);
    r = r && synchronizedStatement_1(b, l + 1);
    r = r && statementNoCaseNoDefault(b, l + 1);
    exit_section_(b, m, SYNCHRONIZED_STATEMENT, r);
    return r;
  }

  // ( '(' expression ')' )?
  private static boolean synchronizedStatement_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "synchronizedStatement_1")) return false;
    synchronizedStatement_1_0(b, l + 1);
    return true;
  }

  // '(' expression ')'
  private static boolean synchronizedStatement_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "synchronizedStatement_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_PAR_LEFT);
    r = r && expression(b, l + 1);
    r = r && consumeToken(b, OP_PAR_RIGHT);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'alias' type? Identifier ( ':' ( type | assignExpression ) )? ( '=' ( type | assignExpression ) )?
  public static boolean templateAliasParameter(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "templateAliasParameter")) return false;
    if (!nextTokenIs(b, KW_ALIAS)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_ALIAS);
    r = r && templateAliasParameter_1(b, l + 1);
    r = r && Identifier(b, l + 1);
    r = r && templateAliasParameter_3(b, l + 1);
    r = r && templateAliasParameter_4(b, l + 1);
    exit_section_(b, m, TEMPLATE_ALIAS_PARAMETER, r);
    return r;
  }

  // type?
  private static boolean templateAliasParameter_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "templateAliasParameter_1")) return false;
    type(b, l + 1);
    return true;
  }

  // ( ':' ( type | assignExpression ) )?
  private static boolean templateAliasParameter_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "templateAliasParameter_3")) return false;
    templateAliasParameter_3_0(b, l + 1);
    return true;
  }

  // ':' ( type | assignExpression )
  private static boolean templateAliasParameter_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "templateAliasParameter_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_COLON);
    r = r && templateAliasParameter_3_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // type | assignExpression
  private static boolean templateAliasParameter_3_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "templateAliasParameter_3_0_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = type(b, l + 1);
    if (!r) r = assignExpression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ( '=' ( type | assignExpression ) )?
  private static boolean templateAliasParameter_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "templateAliasParameter_4")) return false;
    templateAliasParameter_4_0(b, l + 1);
    return true;
  }

  // '=' ( type | assignExpression )
  private static boolean templateAliasParameter_4_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "templateAliasParameter_4_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_EQ);
    r = r && templateAliasParameter_4_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // type | assignExpression
  private static boolean templateAliasParameter_4_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "templateAliasParameter_4_0_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = type(b, l + 1);
    if (!r) r = assignExpression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // type | assignExpression
  public static boolean templateArgument(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "templateArgument")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<template argument>");
    r = type(b, l + 1);
    if (!r) r = assignExpression(b, l + 1);
    exit_section_(b, l, m, TEMPLATE_ARGUMENT, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // templateArgument ( ',' templateArgument? )*
  public static boolean templateArgumentList(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "templateArgumentList")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<template argument list>");
    r = templateArgument(b, l + 1);
    r = r && templateArgumentList_1(b, l + 1);
    exit_section_(b, l, m, TEMPLATE_ARGUMENT_LIST, r, false, null);
    return r;
  }

  // ( ',' templateArgument? )*
  private static boolean templateArgumentList_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "templateArgumentList_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!templateArgumentList_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "templateArgumentList_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // ',' templateArgument?
  private static boolean templateArgumentList_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "templateArgumentList_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_COMMA);
    r = r && templateArgumentList_1_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // templateArgument?
  private static boolean templateArgumentList_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "templateArgumentList_1_0_1")) return false;
    templateArgument(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // '!' ( '(' templateArgumentList? ')' ) | templateSingleArgument
  public static boolean templateArguments(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "templateArguments")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<template arguments>");
    r = templateArguments_0(b, l + 1);
    if (!r) r = templateSingleArgument(b, l + 1);
    exit_section_(b, l, m, TEMPLATE_ARGUMENTS, r, false, null);
    return r;
  }

  // '!' ( '(' templateArgumentList? ')' )
  private static boolean templateArguments_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "templateArguments_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_NOT);
    r = r && templateArguments_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '(' templateArgumentList? ')'
  private static boolean templateArguments_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "templateArguments_0_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_PAR_LEFT);
    r = r && templateArguments_0_1_1(b, l + 1);
    r = r && consumeToken(b, OP_PAR_RIGHT);
    exit_section_(b, m, null, r);
    return r;
  }

  // templateArgumentList?
  private static boolean templateArguments_0_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "templateArguments_0_1_1")) return false;
    templateArgumentList(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // 'template' Identifier templateParameters constraint? '{' declaration* '}'
  public static boolean templateDeclaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "templateDeclaration")) return false;
    if (!nextTokenIs(b, KW_TEMPLATE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_TEMPLATE);
    r = r && Identifier(b, l + 1);
    r = r && templateParameters(b, l + 1);
    r = r && templateDeclaration_3(b, l + 1);
    r = r && consumeToken(b, OP_BRACES_LEFT);
    r = r && templateDeclaration_5(b, l + 1);
    r = r && consumeToken(b, OP_BRACES_RIGHT);
    exit_section_(b, m, TEMPLATE_DECLARATION, r);
    return r;
  }

  // constraint?
  private static boolean templateDeclaration_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "templateDeclaration_3")) return false;
    constraint(b, l + 1);
    return true;
  }

  // declaration*
  private static boolean templateDeclaration_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "templateDeclaration_5")) return false;
    int c = current_position_(b);
    while (true) {
      if (!declaration(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "templateDeclaration_5", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  /* ********************************************************** */
  // Identifier templateArguments
  public static boolean templateInstance(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "templateInstance")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Identifier(b, l + 1);
    r = r && templateArguments(b, l + 1);
    exit_section_(b, m, TEMPLATE_INSTANCE, r);
    return r;
  }

  /* ********************************************************** */
  // 'mixin' mixinTemplateName templateArguments? Identifier?
  public static boolean templateMixinExpression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "templateMixinExpression")) return false;
    if (!nextTokenIs(b, KW_MIXIN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_MIXIN);
    r = r && mixinTemplateName(b, l + 1);
    r = r && templateMixinExpression_2(b, l + 1);
    r = r && templateMixinExpression_3(b, l + 1);
    exit_section_(b, m, TEMPLATE_MIXIN_EXPRESSION, r);
    return r;
  }

  // templateArguments?
  private static boolean templateMixinExpression_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "templateMixinExpression_2")) return false;
    templateArguments(b, l + 1);
    return true;
  }

  // Identifier?
  private static boolean templateMixinExpression_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "templateMixinExpression_3")) return false;
    Identifier(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // templateTypeParameter | templateValueParameter | templateAliasParameter | templateTupleParameter | templateThisParameter
  public static boolean templateParameter(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "templateParameter")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<template parameter>");
    r = templateTypeParameter(b, l + 1);
    if (!r) r = templateValueParameter(b, l + 1);
    if (!r) r = templateAliasParameter(b, l + 1);
    if (!r) r = templateTupleParameter(b, l + 1);
    if (!r) r = templateThisParameter(b, l + 1);
    exit_section_(b, l, m, TEMPLATE_PARAMETER, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // templateParameter ( ',' templateParameter? )*
  public static boolean templateParameterList(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "templateParameterList")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<template parameter list>");
    r = templateParameter(b, l + 1);
    r = r && templateParameterList_1(b, l + 1);
    exit_section_(b, l, m, TEMPLATE_PARAMETER_LIST, r, false, null);
    return r;
  }

  // ( ',' templateParameter? )*
  private static boolean templateParameterList_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "templateParameterList_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!templateParameterList_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "templateParameterList_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // ',' templateParameter?
  private static boolean templateParameterList_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "templateParameterList_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_COMMA);
    r = r && templateParameterList_1_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // templateParameter?
  private static boolean templateParameterList_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "templateParameterList_1_0_1")) return false;
    templateParameter(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // '(' templateParameterList? ')'
  public static boolean templateParameters(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "templateParameters")) return false;
    if (!nextTokenIs(b, OP_PAR_LEFT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_PAR_LEFT);
    r = r && templateParameters_1(b, l + 1);
    r = r && consumeToken(b, OP_PAR_RIGHT);
    exit_section_(b, m, TEMPLATE_PARAMETERS, r);
    return r;
  }

  // templateParameterList?
  private static boolean templateParameters_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "templateParameters_1")) return false;
    templateParameterList(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // builtinType | Identifier | CHARACTER_LITERAL | StringLiteral | INTEGER_LITERAL | FLOAT_LITERAL | 'true' | 'false' | 'null' | 'this' | '__DATE__' | '__TIME__' | '__TIMESTAMP__' | '__VENDOR__' | '__VERSION__' | '__FILE__' | '__LINE__' | '__MODULE__' | '__FUNCTION__' | '__PRETTY_FUNCTION__'
  public static boolean templateSingleArgument(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "templateSingleArgument")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<template single argument>");
    r = builtinType(b, l + 1);
    if (!r) r = Identifier(b, l + 1);
    if (!r) r = consumeToken(b, CHARACTER_LITERAL);
    if (!r) r = StringLiteral(b, l + 1);
    if (!r) r = consumeToken(b, INTEGER_LITERAL);
    if (!r) r = consumeToken(b, FLOAT_LITERAL);
    if (!r) r = consumeToken(b, KW_TRUE);
    if (!r) r = consumeToken(b, KW_FALSE);
    if (!r) r = consumeToken(b, KW_NULL);
    if (!r) r = consumeToken(b, KW_THIS);
    if (!r) r = consumeToken(b, "__DATE__");
    if (!r) r = consumeToken(b, "__TIME__");
    if (!r) r = consumeToken(b, "__TIMESTAMP__");
    if (!r) r = consumeToken(b, "__VENDOR__");
    if (!r) r = consumeToken(b, "__VERSION__");
    if (!r) r = consumeToken(b, KW___FILE__);
    if (!r) r = consumeToken(b, KW___LINE__);
    if (!r) r = consumeToken(b, KW___MODULE__);
    if (!r) r = consumeToken(b, KW___FUNCTION__);
    if (!r) r = consumeToken(b, KW___PRETTY_FUNCTION__);
    exit_section_(b, l, m, TEMPLATE_SINGLE_ARGUMENT, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // 'this' templateTypeParameter
  public static boolean templateThisParameter(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "templateThisParameter")) return false;
    if (!nextTokenIs(b, KW_THIS)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_THIS);
    r = r && templateTypeParameter(b, l + 1);
    exit_section_(b, m, TEMPLATE_THIS_PARAMETER, r);
    return r;
  }

  /* ********************************************************** */
  // Identifier '...'
  public static boolean templateTupleParameter(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "templateTupleParameter")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Identifier(b, l + 1);
    r = r && consumeToken(b, OP_TRIPLEDOT);
    exit_section_(b, m, TEMPLATE_TUPLE_PARAMETER, r);
    return r;
  }

  /* ********************************************************** */
  // Identifier ( ':' type )? ( '=' type )?
  public static boolean templateTypeParameter(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "templateTypeParameter")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Identifier(b, l + 1);
    r = r && templateTypeParameter_1(b, l + 1);
    r = r && templateTypeParameter_2(b, l + 1);
    exit_section_(b, m, TEMPLATE_TYPE_PARAMETER, r);
    return r;
  }

  // ( ':' type )?
  private static boolean templateTypeParameter_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "templateTypeParameter_1")) return false;
    templateTypeParameter_1_0(b, l + 1);
    return true;
  }

  // ':' type
  private static boolean templateTypeParameter_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "templateTypeParameter_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_COLON);
    r = r && type(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ( '=' type )?
  private static boolean templateTypeParameter_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "templateTypeParameter_2")) return false;
    templateTypeParameter_2_0(b, l + 1);
    return true;
  }

  // '=' type
  private static boolean templateTypeParameter_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "templateTypeParameter_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_EQ);
    r = r && type(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // type Identifier ( ':' assignExpression )? templateValueParameterDefault?
  public static boolean templateValueParameter(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "templateValueParameter")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<template value parameter>");
    r = type(b, l + 1);
    r = r && Identifier(b, l + 1);
    r = r && templateValueParameter_2(b, l + 1);
    r = r && templateValueParameter_3(b, l + 1);
    exit_section_(b, l, m, TEMPLATE_VALUE_PARAMETER, r, false, null);
    return r;
  }

  // ( ':' assignExpression )?
  private static boolean templateValueParameter_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "templateValueParameter_2")) return false;
    templateValueParameter_2_0(b, l + 1);
    return true;
  }

  // ':' assignExpression
  private static boolean templateValueParameter_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "templateValueParameter_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_COLON);
    r = r && assignExpression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // templateValueParameterDefault?
  private static boolean templateValueParameter_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "templateValueParameter_3")) return false;
    templateValueParameterDefault(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // '=' ( '__FILE__' | '__MODULE__' | '__LINE__' | '__FUNCTION__' | '__PRETTY_FUNCTION__' | assignExpression )
  public static boolean templateValueParameterDefault(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "templateValueParameterDefault")) return false;
    if (!nextTokenIs(b, OP_EQ)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_EQ);
    r = r && templateValueParameterDefault_1(b, l + 1);
    exit_section_(b, m, TEMPLATE_VALUE_PARAMETER_DEFAULT, r);
    return r;
  }

  // '__FILE__' | '__MODULE__' | '__LINE__' | '__FUNCTION__' | '__PRETTY_FUNCTION__' | assignExpression
  private static boolean templateValueParameterDefault_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "templateValueParameterDefault_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW___FILE__);
    if (!r) r = consumeToken(b, KW___MODULE__);
    if (!r) r = consumeToken(b, KW___LINE__);
    if (!r) r = consumeToken(b, KW___FUNCTION__);
    if (!r) r = consumeToken(b, KW___PRETTY_FUNCTION__);
    if (!r) r = assignExpression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // orOrExpression ('?' expression ':' ternaryExpression)?
  public static boolean ternaryExpression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ternaryExpression")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<ternary expression>");
    r = orOrExpression(b, l + 1);
    r = r && ternaryExpression_1(b, l + 1);
    exit_section_(b, l, m, TERNARY_EXPRESSION, r, false, null);
    return r;
  }

  // ('?' expression ':' ternaryExpression)?
  private static boolean ternaryExpression_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ternaryExpression_1")) return false;
    ternaryExpression_1_0(b, l + 1);
    return true;
  }

  // '?' expression ':' ternaryExpression
  private static boolean ternaryExpression_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ternaryExpression_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_QUEST);
    r = r && expression(b, l + 1);
    r = r && consumeToken(b, OP_COLON);
    r = r && ternaryExpression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'throw' expression ';'
  public static boolean throwStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "throwStatement")) return false;
    if (!nextTokenIs(b, KW_THROW)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_THROW);
    r = r && expression(b, l + 1);
    r = r && consumeToken(b, OP_SCOLON);
    exit_section_(b, m, THROW_STATEMENT, r);
    return r;
  }

  /* ********************************************************** */
  // '__traits' '(' Identifier ',' templateArgumentList ')'
  public static boolean traitsExpression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "traitsExpression")) return false;
    if (!nextTokenIs(b, KW___TRAITS)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW___TRAITS);
    r = r && consumeToken(b, OP_PAR_LEFT);
    r = r && Identifier(b, l + 1);
    r = r && consumeToken(b, OP_COMMA);
    r = r && templateArgumentList(b, l + 1);
    r = r && consumeToken(b, OP_PAR_RIGHT);
    exit_section_(b, m, TRAITS_EXPRESSION, r);
    return r;
  }

  /* ********************************************************** */
  // 'try' declarationOrStatement ( catches | catches finally_ | finally_ )
  public static boolean tryStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tryStatement")) return false;
    if (!nextTokenIs(b, KW_TRY)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_TRY);
    r = r && declarationOrStatement(b, l + 1);
    r = r && tryStatement_2(b, l + 1);
    exit_section_(b, m, TRY_STATEMENT, r);
    return r;
  }

  // catches | catches finally_ | finally_
  private static boolean tryStatement_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tryStatement_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = catches(b, l + 1);
    if (!r) r = tryStatement_2_1(b, l + 1);
    if (!r) r = finally_(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // catches finally_
  private static boolean tryStatement_2_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tryStatement_2_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = catches(b, l + 1);
    r = r && finally_(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // typeConstructors? type2 typeSuffix*
  public static boolean type(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<type>");
    r = type_0(b, l + 1);
    r = r && type2(b, l + 1);
    r = r && type_2(b, l + 1);
    exit_section_(b, l, m, TYPE, r, false, null);
    return r;
  }

  // typeConstructors?
  private static boolean type_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_0")) return false;
    typeConstructors(b, l + 1);
    return true;
  }

  // typeSuffix*
  private static boolean type_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_2")) return false;
    int c = current_position_(b);
    while (true) {
      if (!typeSuffix(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "type_2", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  /* ********************************************************** */
  // builtinType | symbol | typeofExpression ( '.' identifierOrTemplateChain )? | typeConstructor '(' type ')' | vector
  public static boolean type2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type2")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<type 2>");
    r = builtinType(b, l + 1);
    if (!r) r = symbol(b, l + 1);
    if (!r) r = type2_2(b, l + 1);
    if (!r) r = type2_3(b, l + 1);
    if (!r) r = vector(b, l + 1);
    exit_section_(b, l, m, TYPE_2, r, false, null);
    return r;
  }

  // typeofExpression ( '.' identifierOrTemplateChain )?
  private static boolean type2_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type2_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = typeofExpression(b, l + 1);
    r = r && type2_2_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ( '.' identifierOrTemplateChain )?
  private static boolean type2_2_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type2_2_1")) return false;
    type2_2_1_0(b, l + 1);
    return true;
  }

  // '.' identifierOrTemplateChain
  private static boolean type2_2_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type2_2_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_DOT);
    r = r && identifierOrTemplateChain(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // typeConstructor '(' type ')'
  private static boolean type2_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type2_3")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = typeConstructor(b, l + 1);
    r = r && consumeToken(b, OP_PAR_LEFT);
    r = r && type(b, l + 1);
    r = r && consumeToken(b, OP_PAR_RIGHT);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'const' | 'immutable' | 'inout' | 'shared'
  public static boolean typeConstructor(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "typeConstructor")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<type constructor>");
    r = consumeToken(b, KW_CONST);
    if (!r) r = consumeToken(b, KW_IMMUTABLE);
    if (!r) r = consumeToken(b, KW_INOUT);
    if (!r) r = consumeToken(b, KW_SHARED);
    exit_section_(b, l, m, TYPE_CONSTRUCTOR, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // typeConstructor
  public static boolean typeConstructors(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "typeConstructors")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<type constructors>");
    r = typeConstructor(b, l + 1);
    exit_section_(b, l, m, TYPE_CONSTRUCTORS, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // type | 'struct' | 'union' | 'class' | 'interface' | 'enum' | 'function' | 'delegate' | 'super' | 'const' | 'immutable' | 'inout' | 'shared' | 'return' | 'typedef' | '__parameters'
  public static boolean typeSpecialization(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "typeSpecialization")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<type specialization>");
    r = type(b, l + 1);
    if (!r) r = consumeToken(b, KW_STRUCT);
    if (!r) r = consumeToken(b, KW_UNION);
    if (!r) r = consumeToken(b, KW_CLASS);
    if (!r) r = consumeToken(b, KW_INTERFACE);
    if (!r) r = consumeToken(b, KW_ENUM);
    if (!r) r = consumeToken(b, KW_FUNCTION);
    if (!r) r = consumeToken(b, KW_DELEGATE);
    if (!r) r = consumeToken(b, KW_SUPER);
    if (!r) r = consumeToken(b, KW_CONST);
    if (!r) r = consumeToken(b, KW_IMMUTABLE);
    if (!r) r = consumeToken(b, KW_INOUT);
    if (!r) r = consumeToken(b, KW_SHARED);
    if (!r) r = consumeToken(b, KW_RETURN);
    if (!r) r = consumeToken(b, "typedef");
    if (!r) r = consumeToken(b, KW___PARAMETERS);
    exit_section_(b, l, m, TYPE_SPECIALIZATION, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // '*' | '[' type? ']' | '[' assignExpression ']' | '[' assignExpression '..' assignExpression ']' | ( 'delegate' | 'function' ) parameters memberFunctionAttribute*
  public static boolean typeSuffix(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "typeSuffix")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<type suffix>");
    r = consumeToken(b, OP_ASTERISK);
    if (!r) r = typeSuffix_1(b, l + 1);
    if (!r) r = typeSuffix_2(b, l + 1);
    if (!r) r = typeSuffix_3(b, l + 1);
    if (!r) r = typeSuffix_4(b, l + 1);
    exit_section_(b, l, m, TYPE_SUFFIX, r, false, null);
    return r;
  }

  // '[' type? ']'
  private static boolean typeSuffix_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "typeSuffix_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_BRACKET_LEFT);
    r = r && typeSuffix_1_1(b, l + 1);
    r = r && consumeToken(b, OP_BRACKET_RIGHT);
    exit_section_(b, m, null, r);
    return r;
  }

  // type?
  private static boolean typeSuffix_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "typeSuffix_1_1")) return false;
    type(b, l + 1);
    return true;
  }

  // '[' assignExpression ']'
  private static boolean typeSuffix_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "typeSuffix_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_BRACKET_LEFT);
    r = r && assignExpression(b, l + 1);
    r = r && consumeToken(b, OP_BRACKET_RIGHT);
    exit_section_(b, m, null, r);
    return r;
  }

  // '[' assignExpression '..' assignExpression ']'
  private static boolean typeSuffix_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "typeSuffix_3")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_BRACKET_LEFT);
    r = r && assignExpression(b, l + 1);
    r = r && consumeToken(b, OP_DDOT);
    r = r && assignExpression(b, l + 1);
    r = r && consumeToken(b, OP_BRACKET_RIGHT);
    exit_section_(b, m, null, r);
    return r;
  }

  // ( 'delegate' | 'function' ) parameters memberFunctionAttribute*
  private static boolean typeSuffix_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "typeSuffix_4")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = typeSuffix_4_0(b, l + 1);
    r = r && parameters(b, l + 1);
    r = r && typeSuffix_4_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // 'delegate' | 'function'
  private static boolean typeSuffix_4_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "typeSuffix_4_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_DELEGATE);
    if (!r) r = consumeToken(b, KW_FUNCTION);
    exit_section_(b, m, null, r);
    return r;
  }

  // memberFunctionAttribute*
  private static boolean typeSuffix_4_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "typeSuffix_4_2")) return false;
    int c = current_position_(b);
    while (true) {
      if (!memberFunctionAttribute(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "typeSuffix_4_2", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  /* ********************************************************** */
  // 'typeid' '(' ( type | expression ) ')'
  public static boolean typeidExpression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "typeidExpression")) return false;
    if (!nextTokenIs(b, KW_TYPEID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_TYPEID);
    r = r && consumeToken(b, OP_PAR_LEFT);
    r = r && typeidExpression_2(b, l + 1);
    r = r && consumeToken(b, OP_PAR_RIGHT);
    exit_section_(b, m, TYPEID_EXPRESSION, r);
    return r;
  }

  // type | expression
  private static boolean typeidExpression_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "typeidExpression_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = type(b, l + 1);
    if (!r) r = expression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'typeof' '(' ( expression | 'return' ) ')'
  public static boolean typeofExpression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "typeofExpression")) return false;
    if (!nextTokenIs(b, KW_TYPEOF)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_TYPEOF);
    r = r && consumeToken(b, OP_PAR_LEFT);
    r = r && typeofExpression_2(b, l + 1);
    r = r && consumeToken(b, OP_PAR_RIGHT);
    exit_section_(b, m, TYPEOF_EXPRESSION, r);
    return r;
  }

  // expression | 'return'
  private static boolean typeofExpression_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "typeofExpression_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = expression(b, l + 1);
    if (!r) r = consumeToken(b, KW_RETURN);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // primaryExpression
  // | '&' unaryExpression
  // | '!' unaryExpression
  // | '*' unaryExpression
  // | '+' unaryExpression
  // | '-' unaryExpression
  // | '~' unaryExpression
  // | '++' unaryExpression
  // | '--' unaryExpression
  // | newExpression
  // | deleteExpression
  // | castExpression
  // | assertExpression
  // | functionCallExpression
  // | '(' type ')' '.' identifierOrTemplateInstance
  // | [
  //     '++' unaryExpression
  //   | '--' unaryExpression
  //   | '.' identifierOrTemplateInstance unaryExpression
  //   | '[' argumentList ']' unaryExpression
  //   | '[' assignExpression '..' assignExpression ']' unaryExpression
  //   | '[' ']' unaryExpression
  //   ]
  public static boolean unaryExpression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unaryExpression")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _COLLAPSE_, "<unary expression>");
    r = primaryExpression(b, l + 1);
    if (!r) r = unaryExpression_1(b, l + 1);
    if (!r) r = unaryExpression_2(b, l + 1);
    if (!r) r = unaryExpression_3(b, l + 1);
    if (!r) r = unaryExpression_4(b, l + 1);
    if (!r) r = unaryExpression_5(b, l + 1);
    if (!r) r = unaryExpression_6(b, l + 1);
    if (!r) r = unaryExpression_7(b, l + 1);
    if (!r) r = unaryExpression_8(b, l + 1);
    if (!r) r = newExpression(b, l + 1);
    if (!r) r = deleteExpression(b, l + 1);
    if (!r) r = castExpression(b, l + 1);
    if (!r) r = assertExpression(b, l + 1);
    if (!r) r = functionCallExpression(b, l + 1);
    if (!r) r = unaryExpression_14(b, l + 1);
    if (!r) r = unaryExpression_15(b, l + 1);
    exit_section_(b, l, m, UNARY_EXPRESSION, r, false, null);
    return r;
  }

  // '&' unaryExpression
  private static boolean unaryExpression_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unaryExpression_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_AND);
    r = r && unaryExpression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '!' unaryExpression
  private static boolean unaryExpression_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unaryExpression_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_NOT);
    r = r && unaryExpression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '*' unaryExpression
  private static boolean unaryExpression_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unaryExpression_3")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_ASTERISK);
    r = r && unaryExpression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '+' unaryExpression
  private static boolean unaryExpression_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unaryExpression_4")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_PLUS);
    r = r && unaryExpression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '-' unaryExpression
  private static boolean unaryExpression_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unaryExpression_5")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_MINUS);
    r = r && unaryExpression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '~' unaryExpression
  private static boolean unaryExpression_6(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unaryExpression_6")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_TILDA);
    r = r && unaryExpression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '++' unaryExpression
  private static boolean unaryExpression_7(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unaryExpression_7")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_PLUS_PLUS);
    r = r && unaryExpression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '--' unaryExpression
  private static boolean unaryExpression_8(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unaryExpression_8")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_MINUS_MINUS);
    r = r && unaryExpression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '(' type ')' '.' identifierOrTemplateInstance
  private static boolean unaryExpression_14(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unaryExpression_14")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_PAR_LEFT);
    r = r && type(b, l + 1);
    r = r && consumeToken(b, OP_PAR_RIGHT);
    r = r && consumeToken(b, OP_DOT);
    r = r && identifierOrTemplateInstance(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // [
  //     '++' unaryExpression
  //   | '--' unaryExpression
  //   | '.' identifierOrTemplateInstance unaryExpression
  //   | '[' argumentList ']' unaryExpression
  //   | '[' assignExpression '..' assignExpression ']' unaryExpression
  //   | '[' ']' unaryExpression
  //   ]
  private static boolean unaryExpression_15(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unaryExpression_15")) return false;
    unaryExpression_15_0(b, l + 1);
    return true;
  }

  // '++' unaryExpression
  //   | '--' unaryExpression
  //   | '.' identifierOrTemplateInstance unaryExpression
  //   | '[' argumentList ']' unaryExpression
  //   | '[' assignExpression '..' assignExpression ']' unaryExpression
  //   | '[' ']' unaryExpression
  private static boolean unaryExpression_15_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unaryExpression_15_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = unaryExpression_15_0_0(b, l + 1);
    if (!r) r = unaryExpression_15_0_1(b, l + 1);
    if (!r) r = unaryExpression_15_0_2(b, l + 1);
    if (!r) r = unaryExpression_15_0_3(b, l + 1);
    if (!r) r = unaryExpression_15_0_4(b, l + 1);
    if (!r) r = unaryExpression_15_0_5(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '++' unaryExpression
  private static boolean unaryExpression_15_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unaryExpression_15_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_PLUS_PLUS);
    r = r && unaryExpression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '--' unaryExpression
  private static boolean unaryExpression_15_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unaryExpression_15_0_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_MINUS_MINUS);
    r = r && unaryExpression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '.' identifierOrTemplateInstance unaryExpression
  private static boolean unaryExpression_15_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unaryExpression_15_0_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_DOT);
    r = r && identifierOrTemplateInstance(b, l + 1);
    r = r && unaryExpression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '[' argumentList ']' unaryExpression
  private static boolean unaryExpression_15_0_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unaryExpression_15_0_3")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_BRACKET_LEFT);
    r = r && argumentList(b, l + 1);
    r = r && consumeToken(b, OP_BRACKET_RIGHT);
    r = r && unaryExpression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '[' assignExpression '..' assignExpression ']' unaryExpression
  private static boolean unaryExpression_15_0_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unaryExpression_15_0_4")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_BRACKET_LEFT);
    r = r && assignExpression(b, l + 1);
    r = r && consumeToken(b, OP_DDOT);
    r = r && assignExpression(b, l + 1);
    r = r && consumeToken(b, OP_BRACKET_RIGHT);
    r = r && unaryExpression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '[' ']' unaryExpression
  private static boolean unaryExpression_15_0_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unaryExpression_15_0_5")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_BRACKET_LEFT);
    r = r && consumeToken(b, OP_BRACKET_RIGHT);
    r = r && unaryExpression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'union' Identifier templateParameters constraint? structBody | 'union' Identifier ( structBody | ';' ) | 'union' structBody
  public static boolean unionDeclaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unionDeclaration")) return false;
    if (!nextTokenIs(b, KW_UNION)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = unionDeclaration_0(b, l + 1);
    if (!r) r = unionDeclaration_1(b, l + 1);
    if (!r) r = unionDeclaration_2(b, l + 1);
    exit_section_(b, m, UNION_DECLARATION, r);
    return r;
  }

  // 'union' Identifier templateParameters constraint? structBody
  private static boolean unionDeclaration_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unionDeclaration_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_UNION);
    r = r && Identifier(b, l + 1);
    r = r && templateParameters(b, l + 1);
    r = r && unionDeclaration_0_3(b, l + 1);
    r = r && structBody(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // constraint?
  private static boolean unionDeclaration_0_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unionDeclaration_0_3")) return false;
    constraint(b, l + 1);
    return true;
  }

  // 'union' Identifier ( structBody | ';' )
  private static boolean unionDeclaration_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unionDeclaration_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_UNION);
    r = r && Identifier(b, l + 1);
    r = r && unionDeclaration_1_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // structBody | ';'
  private static boolean unionDeclaration_1_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unionDeclaration_1_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = structBody(b, l + 1);
    if (!r) r = consumeToken(b, OP_SCOLON);
    exit_section_(b, m, null, r);
    return r;
  }

  // 'union' structBody
  private static boolean unionDeclaration_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unionDeclaration_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_UNION);
    r = r && structBody(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'unittest' blockStatement
  public static boolean unittest(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unittest")) return false;
    if (!nextTokenIs(b, KW_UNITTEST)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_UNITTEST);
    r = r && blockStatement(b, l + 1);
    exit_section_(b, m, UNITTEST, r);
    return r;
  }

  /* ********************************************************** */
  // storageClass* type declarator ( ',' declarator )* ';'
  //    | storageClass* type Identifier '=' functionBody ';'
  //    | autoDeclaration
  public static boolean variableDeclaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "variableDeclaration")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<variable declaration>");
    r = variableDeclaration_0(b, l + 1);
    if (!r) r = variableDeclaration_1(b, l + 1);
    if (!r) r = autoDeclaration(b, l + 1);
    exit_section_(b, l, m, VARIABLE_DECLARATION, r, false, null);
    return r;
  }

  // storageClass* type declarator ( ',' declarator )* ';'
  private static boolean variableDeclaration_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "variableDeclaration_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = variableDeclaration_0_0(b, l + 1);
    r = r && type(b, l + 1);
    r = r && declarator(b, l + 1);
    r = r && variableDeclaration_0_3(b, l + 1);
    r = r && consumeToken(b, OP_SCOLON);
    exit_section_(b, m, null, r);
    return r;
  }

  // storageClass*
  private static boolean variableDeclaration_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "variableDeclaration_0_0")) return false;
    int c = current_position_(b);
    while (true) {
      if (!storageClass(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "variableDeclaration_0_0", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // ( ',' declarator )*
  private static boolean variableDeclaration_0_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "variableDeclaration_0_3")) return false;
    int c = current_position_(b);
    while (true) {
      if (!variableDeclaration_0_3_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "variableDeclaration_0_3", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // ',' declarator
  private static boolean variableDeclaration_0_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "variableDeclaration_0_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_COMMA);
    r = r && declarator(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // storageClass* type Identifier '=' functionBody ';'
  private static boolean variableDeclaration_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "variableDeclaration_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = variableDeclaration_1_0(b, l + 1);
    r = r && type(b, l + 1);
    r = r && Identifier(b, l + 1);
    r = r && consumeToken(b, OP_EQ);
    r = r && functionBody(b, l + 1);
    r = r && consumeToken(b, OP_SCOLON);
    exit_section_(b, m, null, r);
    return r;
  }

  // storageClass*
  private static boolean variableDeclaration_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "variableDeclaration_1_0")) return false;
    int c = current_position_(b);
    while (true) {
      if (!storageClass(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "variableDeclaration_1_0", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  /* ********************************************************** */
  // '__vector' '(' type ')'
  public static boolean vector(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "vector")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<vector>");
    r = consumeToken(b, "__vector");
    r = r && consumeToken(b, OP_PAR_LEFT);
    r = r && type(b, l + 1);
    r = r && consumeToken(b, OP_PAR_RIGHT);
    exit_section_(b, l, m, VECTOR, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // 'version' '(' ( INTEGER_LITERAL | Identifier | 'unittest' | 'assert' ) ')'
  public static boolean versionCondition(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "versionCondition")) return false;
    if (!nextTokenIs(b, KW_VERSION)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_VERSION);
    r = r && consumeToken(b, OP_PAR_LEFT);
    r = r && versionCondition_2(b, l + 1);
    r = r && consumeToken(b, OP_PAR_RIGHT);
    exit_section_(b, m, VERSION_CONDITION, r);
    return r;
  }

  // INTEGER_LITERAL | Identifier | 'unittest' | 'assert'
  private static boolean versionCondition_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "versionCondition_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, INTEGER_LITERAL);
    if (!r) r = Identifier(b, l + 1);
    if (!r) r = consumeToken(b, KW_UNITTEST);
    if (!r) r = consumeToken(b, KW_ASSERT);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'version' '=' ( Identifier | INTEGER_LITERAL ) ';'
  public static boolean versionSpecification(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "versionSpecification")) return false;
    if (!nextTokenIs(b, KW_VERSION)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_VERSION);
    r = r && consumeToken(b, OP_EQ);
    r = r && versionSpecification_2(b, l + 1);
    r = r && consumeToken(b, OP_SCOLON);
    exit_section_(b, m, VERSION_SPECIFICATION, r);
    return r;
  }

  // Identifier | INTEGER_LITERAL
  private static boolean versionSpecification_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "versionSpecification_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Identifier(b, l + 1);
    if (!r) r = consumeToken(b, INTEGER_LITERAL);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'while' '(' expression ')' declarationOrStatement
  public static boolean whileStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "whileStatement")) return false;
    if (!nextTokenIs(b, KW_WHILE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_WHILE);
    r = r && consumeToken(b, OP_PAR_LEFT);
    r = r && expression(b, l + 1);
    r = r && consumeToken(b, OP_PAR_RIGHT);
    r = r && declarationOrStatement(b, l + 1);
    exit_section_(b, m, WHILE_STATEMENT, r);
    return r;
  }

  /* ********************************************************** */
  // 'with' '(' expression ')' statementNoCaseNoDefault
  public static boolean withStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "withStatement")) return false;
    if (!nextTokenIs(b, KW_WITH)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KW_WITH);
    r = r && consumeToken(b, OP_PAR_LEFT);
    r = r && expression(b, l + 1);
    r = r && consumeToken(b, OP_PAR_RIGHT);
    r = r && statementNoCaseNoDefault(b, l + 1);
    exit_section_(b, m, WITH_STATEMENT, r);
    return r;
  }

  /* ********************************************************** */
  // andExpression ['^' xorExpression]
  public static boolean xorExpression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "xorExpression")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<xor expression>");
    r = andExpression(b, l + 1);
    r = r && xorExpression_1(b, l + 1);
    exit_section_(b, l, m, XOR_EXPRESSION, r, false, null);
    return r;
  }

  // ['^' xorExpression]
  private static boolean xorExpression_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "xorExpression_1")) return false;
    xorExpression_1_0(b, l + 1);
    return true;
  }

  // '^' xorExpression
  private static boolean xorExpression_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "xorExpression_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_XOR);
    r = r && xorExpression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

}
