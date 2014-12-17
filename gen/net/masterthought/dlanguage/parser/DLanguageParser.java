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
    if (t == COMMENT) {
      r = Comment(b, 0);
    }
    else if (t == FLOAT_LITERAL) {
      r = FloatLiteral(b, 0);
    }
    else if (t == INTEGER_LITERAL) {
      r = IntegerLiteral(b, 0);
    }
    else if (t == SPECIAL_TOKEN_SEQUENCE) {
      r = SpecialTokenSequence(b, 0);
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
    else if (t == INDEX_EXPRESSION) {
      r = indexExpression(b, 0);
    }
    else if (t == INITIALIZE) {
      r = initialize(b, 0);
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
    else if (t == MODULE) {
      r = module(b, 0);
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
    else if (t == POST_INC_DEC_EXPRESSION) {
      r = postIncDecExpression(b, 0);
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
    else if (t == PRE_INC_DEC_EXPRESSION) {
      r = preIncDecExpression(b, 0);
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
    else if (t == SLICE_EXPRESSION) {
      r = sliceExpression(b, 0);
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
  // '`' "regexp:.*?" '`' StringPostfix?
  static boolean AlternativeWysiwygString(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "AlternativeWysiwygString")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "`");
    r = r && consumeToken(b, "regexp:.*?");
    r = r && consumeToken(b, "`");
    r = r && AlternativeWysiwygString_3(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // StringPostfix?
  private static boolean AlternativeWysiwygString_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "AlternativeWysiwygString_3")) return false;
    StringPostfix(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // "regexp:[01]"
  static boolean BinDigit(PsiBuilder b, int l) {
    return consumeToken(b, "regexp:[01]");
  }

  /* ********************************************************** */
  // ('0b' | '0B') BinDigit (BinDigit | '_')*
  static boolean BinaryInteger(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "BinaryInteger")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = BinaryInteger_0(b, l + 1);
    r = r && BinDigit(b, l + 1);
    r = r && BinaryInteger_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '0b' | '0B'
  private static boolean BinaryInteger_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "BinaryInteger_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "0b");
    if (!r) r = consumeToken(b, "0B");
    exit_section_(b, m, null, r);
    return r;
  }

  // (BinDigit | '_')*
  private static boolean BinaryInteger_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "BinaryInteger_2")) return false;
    int c = current_position_(b);
    while (true) {
      if (!BinaryInteger_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "BinaryInteger_2", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // BinDigit | '_'
  private static boolean BinaryInteger_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "BinaryInteger_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = BinDigit(b, l + 1);
    if (!r) r = consumeToken(b, "_");
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // "regexp:\/*.*? *\/"
  static boolean BlockComment(PsiBuilder b, int l) {
    return consumeToken(b, "regexp:\\/*.*? *\\/");
  }

  /* ********************************************************** */
  // "regexp:[\u0001-\uffff]"
  static boolean Character(PsiBuilder b, int l) {
    return consumeToken(b, "regexp:[\u0001-\uffff]");
  }

  /* ********************************************************** */
  // (BlockComment | LineComment | NestingBlockComment)+
  public static boolean Comment(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Comment")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<comment>");
    r = Comment_0(b, l + 1);
    int c = current_position_(b);
    while (r) {
      if (!Comment_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "Comment", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, l, m, COMMENT, r, false, null);
    return r;
  }

  // BlockComment | LineComment | NestingBlockComment
  private static boolean Comment_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Comment_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = BlockComment(b, l + 1);
    if (!r) r = LineComment(b, l + 1);
    if (!r) r = NestingBlockComment(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // "regexp:[0-9]"
  static boolean DecimalDigit(PsiBuilder b, int l) {
    return consumeToken(b, "regexp:[0-9]");
  }

  /* ********************************************************** */
  // ('e' | 'E' | 'e+' | 'E+' | 'e-' | 'E-') DecimalInteger
  static boolean DecimalExponent(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "DecimalExponent")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = DecimalExponent_0(b, l + 1);
    r = r && DecimalInteger(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // 'e' | 'E' | 'e+' | 'E+' | 'e-' | 'E-'
  private static boolean DecimalExponent_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "DecimalExponent_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "e");
    if (!r) r = consumeToken(b, "E");
    if (!r) r = consumeToken(b, "e+");
    if (!r) r = consumeToken(b, "E+");
    if (!r) r = consumeToken(b, "e-");
    if (!r) r = consumeToken(b, "E-");
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // DecimalInteger '.' (DecimalDigit (DecimalDigit | '_')* DecimalExponent?)?  // BUG: can't lex a[0..1] properly
  //       | '.' DecimalInteger DecimalExponent?
  //       | DecimalInteger DecimalExponent
  static boolean DecimalFloat(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "DecimalFloat")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = DecimalFloat_0(b, l + 1);
    if (!r) r = DecimalFloat_1(b, l + 1);
    if (!r) r = DecimalFloat_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // DecimalInteger '.' (DecimalDigit (DecimalDigit | '_')* DecimalExponent?)?
  private static boolean DecimalFloat_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "DecimalFloat_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = DecimalInteger(b, l + 1);
    r = r && consumeToken(b, DOT);
    r = r && DecimalFloat_0_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (DecimalDigit (DecimalDigit | '_')* DecimalExponent?)?
  private static boolean DecimalFloat_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "DecimalFloat_0_2")) return false;
    DecimalFloat_0_2_0(b, l + 1);
    return true;
  }

  // DecimalDigit (DecimalDigit | '_')* DecimalExponent?
  private static boolean DecimalFloat_0_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "DecimalFloat_0_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = DecimalDigit(b, l + 1);
    r = r && DecimalFloat_0_2_0_1(b, l + 1);
    r = r && DecimalFloat_0_2_0_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (DecimalDigit | '_')*
  private static boolean DecimalFloat_0_2_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "DecimalFloat_0_2_0_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!DecimalFloat_0_2_0_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "DecimalFloat_0_2_0_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // DecimalDigit | '_'
  private static boolean DecimalFloat_0_2_0_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "DecimalFloat_0_2_0_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = DecimalDigit(b, l + 1);
    if (!r) r = consumeToken(b, "_");
    exit_section_(b, m, null, r);
    return r;
  }

  // DecimalExponent?
  private static boolean DecimalFloat_0_2_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "DecimalFloat_0_2_0_2")) return false;
    DecimalExponent(b, l + 1);
    return true;
  }

  // '.' DecimalInteger DecimalExponent?
  private static boolean DecimalFloat_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "DecimalFloat_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, DOT);
    r = r && DecimalInteger(b, l + 1);
    r = r && DecimalFloat_1_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // DecimalExponent?
  private static boolean DecimalFloat_1_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "DecimalFloat_1_2")) return false;
    DecimalExponent(b, l + 1);
    return true;
  }

  // DecimalInteger DecimalExponent
  private static boolean DecimalFloat_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "DecimalFloat_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = DecimalInteger(b, l + 1);
    r = r && DecimalExponent(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // DecimalDigit (DecimalDigit | '_')*
  static boolean DecimalInteger(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "DecimalInteger")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = DecimalDigit(b, l + 1);
    r = r && DecimalInteger_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (DecimalDigit | '_')*
  private static boolean DecimalInteger_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "DecimalInteger_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!DecimalInteger_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "DecimalInteger_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // DecimalDigit | '_'
  private static boolean DecimalInteger_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "DecimalInteger_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = DecimalDigit(b, l + 1);
    if (!r) r = consumeToken(b, "_");
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // EscapeSequence | "regexp:~('\"' | '\\' )"
  static boolean DoubleQuotedCharacter(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "DoubleQuotedCharacter")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = EscapeSequence(b, l + 1);
    if (!r) r = consumeToken(b, "regexp:~('\"' | '\\' )");
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // '"' DoubleQuotedCharacter* '"'  StringPostfix?
  static boolean DoubleQuotedString(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "DoubleQuotedString")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "\"");
    r = r && DoubleQuotedString_1(b, l + 1);
    r = r && consumeToken(b, "\"");
    r = r && DoubleQuotedString_3(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // DoubleQuotedCharacter*
  private static boolean DoubleQuotedString_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "DoubleQuotedString_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!DoubleQuotedCharacter(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "DoubleQuotedString_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // StringPostfix?
  private static boolean DoubleQuotedString_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "DoubleQuotedString_3")) return false;
    StringPostfix(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  static boolean EndOfLine(PsiBuilder b, int l) {
    return consumeToken(b, "regexp:(\\u000d|\\u000a|\\u000d\\u000a|\\u2028|\\u2029)");
  }

  /* ********************************************************** */
  // "\\\'"
  //       | '\\"'
  //       | '\\\\'
  //       | '\\?'
  //       | '\\0'
  //       | '\\a'
  //       | '\\b'
  //       | '\\f'
  //       | '\\n'
  //       | '\\r'
  //       | '\\t'
  //       | '\\v'
  //       | '\\x' HexDigit HexDigit
  //       | '\\' OctalDigit OctalDigit? OctalDigit?
  //       | '\\u' HexDigit HexDigit HexDigit HexDigit
  //       | '\\U' HexDigit HexDigit HexDigit HexDigit HexDigit HexDigit HexDigit HexDigit
  //       | '\\&' Identifier ';'
  static boolean EscapeSequence(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "EscapeSequence")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "\\\'");
    if (!r) r = consumeToken(b, "\\\\\"");
    if (!r) r = consumeToken(b, "\\\\\\\\");
    if (!r) r = consumeToken(b, "\\\\?");
    if (!r) r = consumeToken(b, "\\\\0");
    if (!r) r = consumeToken(b, "\\\\a");
    if (!r) r = consumeToken(b, "\\\\b");
    if (!r) r = consumeToken(b, "\\\\f");
    if (!r) r = consumeToken(b, "\\\\n");
    if (!r) r = consumeToken(b, "\\\\r");
    if (!r) r = consumeToken(b, "\\\\t");
    if (!r) r = consumeToken(b, "\\\\v");
    if (!r) r = EscapeSequence_12(b, l + 1);
    if (!r) r = EscapeSequence_13(b, l + 1);
    if (!r) r = EscapeSequence_14(b, l + 1);
    if (!r) r = EscapeSequence_15(b, l + 1);
    if (!r) r = EscapeSequence_16(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '\\x' HexDigit HexDigit
  private static boolean EscapeSequence_12(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "EscapeSequence_12")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "\\\\x");
    r = r && HexDigit(b, l + 1);
    r = r && HexDigit(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '\\' OctalDigit OctalDigit? OctalDigit?
  private static boolean EscapeSequence_13(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "EscapeSequence_13")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "\\\\");
    r = r && OctalDigit(b, l + 1);
    r = r && EscapeSequence_13_2(b, l + 1);
    r = r && EscapeSequence_13_3(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // OctalDigit?
  private static boolean EscapeSequence_13_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "EscapeSequence_13_2")) return false;
    OctalDigit(b, l + 1);
    return true;
  }

  // OctalDigit?
  private static boolean EscapeSequence_13_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "EscapeSequence_13_3")) return false;
    OctalDigit(b, l + 1);
    return true;
  }

  // '\\u' HexDigit HexDigit HexDigit HexDigit
  private static boolean EscapeSequence_14(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "EscapeSequence_14")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "\\\\u");
    r = r && HexDigit(b, l + 1);
    r = r && HexDigit(b, l + 1);
    r = r && HexDigit(b, l + 1);
    r = r && HexDigit(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '\\U' HexDigit HexDigit HexDigit HexDigit HexDigit HexDigit HexDigit HexDigit
  private static boolean EscapeSequence_15(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "EscapeSequence_15")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "\\\\U");
    r = r && HexDigit(b, l + 1);
    r = r && HexDigit(b, l + 1);
    r = r && HexDigit(b, l + 1);
    r = r && HexDigit(b, l + 1);
    r = r && HexDigit(b, l + 1);
    r = r && HexDigit(b, l + 1);
    r = r && HexDigit(b, l + 1);
    r = r && HexDigit(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '\\&' Identifier ';'
  private static boolean EscapeSequence_16(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "EscapeSequence_16")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "\\\\&");
    r = r && Identifier(b, l + 1);
    r = r && consumeToken(b, ";");
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // (FloatOption (FloatSuffix | RealSuffix)?) | (Integer (FloatSuffix | RealSuffix)? ImaginarySuffix)
  public static boolean FloatLiteral(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FloatLiteral")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<float literal>");
    r = FloatLiteral_0(b, l + 1);
    if (!r) r = FloatLiteral_1(b, l + 1);
    exit_section_(b, l, m, FLOAT_LITERAL, r, false, null);
    return r;
  }

  // FloatOption (FloatSuffix | RealSuffix)?
  private static boolean FloatLiteral_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FloatLiteral_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = FloatOption(b, l + 1);
    r = r && FloatLiteral_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (FloatSuffix | RealSuffix)?
  private static boolean FloatLiteral_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FloatLiteral_0_1")) return false;
    FloatLiteral_0_1_0(b, l + 1);
    return true;
  }

  // FloatSuffix | RealSuffix
  private static boolean FloatLiteral_0_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FloatLiteral_0_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = FloatSuffix(b, l + 1);
    if (!r) r = RealSuffix(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // Integer (FloatSuffix | RealSuffix)? ImaginarySuffix
  private static boolean FloatLiteral_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FloatLiteral_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Integer(b, l + 1);
    r = r && FloatLiteral_1_1(b, l + 1);
    r = r && ImaginarySuffix(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (FloatSuffix | RealSuffix)?
  private static boolean FloatLiteral_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FloatLiteral_1_1")) return false;
    FloatLiteral_1_1_0(b, l + 1);
    return true;
  }

  // FloatSuffix | RealSuffix
  private static boolean FloatLiteral_1_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FloatLiteral_1_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = FloatSuffix(b, l + 1);
    if (!r) r = RealSuffix(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // DecimalFloat | HexFloat
  static boolean FloatOption(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FloatOption")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = DecimalFloat(b, l + 1);
    if (!r) r = HexFloat(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'F' | 'f'
  static boolean FloatSuffix(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FloatSuffix")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "F");
    if (!r) r = consumeToken(b, "f");
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // "regexp:[a-fA-F0-9]"
  static boolean HexDigit(PsiBuilder b, int l) {
    return consumeToken(b, "regexp:[a-fA-F0-9]");
  }

  /* ********************************************************** */
  // ('p' | 'P' | 'p+' | 'P+' | 'p-' | 'P-') DecimalDigit (DecimalDigit | '_')*
  static boolean HexExponent(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "HexExponent")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = HexExponent_0(b, l + 1);
    r = r && DecimalDigit(b, l + 1);
    r = r && HexExponent_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // 'p' | 'P' | 'p+' | 'P+' | 'p-' | 'P-'
  private static boolean HexExponent_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "HexExponent_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "p");
    if (!r) r = consumeToken(b, "P");
    if (!r) r = consumeToken(b, "p+");
    if (!r) r = consumeToken(b, "P+");
    if (!r) r = consumeToken(b, "p-");
    if (!r) r = consumeToken(b, "P-");
    exit_section_(b, m, null, r);
    return r;
  }

  // (DecimalDigit | '_')*
  private static boolean HexExponent_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "HexExponent_2")) return false;
    int c = current_position_(b);
    while (true) {
      if (!HexExponent_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "HexExponent_2", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // DecimalDigit | '_'
  private static boolean HexExponent_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "HexExponent_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = DecimalDigit(b, l + 1);
    if (!r) r = consumeToken(b, "_");
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // ('0x' | '0X') ((HexDigit (HexDigit | '_')* '.' HexDigit (HexDigit | '_')*) | ('.' HexDigit (HexDigit | '_')*) | (HexDigit (HexDigit | '_')*)) HexExponent
  static boolean HexFloat(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "HexFloat")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = HexFloat_0(b, l + 1);
    r = r && HexFloat_1(b, l + 1);
    r = r && HexExponent(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '0x' | '0X'
  private static boolean HexFloat_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "HexFloat_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "0x");
    if (!r) r = consumeToken(b, "0X");
    exit_section_(b, m, null, r);
    return r;
  }

  // (HexDigit (HexDigit | '_')* '.' HexDigit (HexDigit | '_')*) | ('.' HexDigit (HexDigit | '_')*) | (HexDigit (HexDigit | '_')*)
  private static boolean HexFloat_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "HexFloat_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = HexFloat_1_0(b, l + 1);
    if (!r) r = HexFloat_1_1(b, l + 1);
    if (!r) r = HexFloat_1_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // HexDigit (HexDigit | '_')* '.' HexDigit (HexDigit | '_')*
  private static boolean HexFloat_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "HexFloat_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = HexDigit(b, l + 1);
    r = r && HexFloat_1_0_1(b, l + 1);
    r = r && consumeToken(b, DOT);
    r = r && HexDigit(b, l + 1);
    r = r && HexFloat_1_0_4(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (HexDigit | '_')*
  private static boolean HexFloat_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "HexFloat_1_0_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!HexFloat_1_0_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "HexFloat_1_0_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // HexDigit | '_'
  private static boolean HexFloat_1_0_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "HexFloat_1_0_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = HexDigit(b, l + 1);
    if (!r) r = consumeToken(b, "_");
    exit_section_(b, m, null, r);
    return r;
  }

  // (HexDigit | '_')*
  private static boolean HexFloat_1_0_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "HexFloat_1_0_4")) return false;
    int c = current_position_(b);
    while (true) {
      if (!HexFloat_1_0_4_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "HexFloat_1_0_4", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // HexDigit | '_'
  private static boolean HexFloat_1_0_4_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "HexFloat_1_0_4_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = HexDigit(b, l + 1);
    if (!r) r = consumeToken(b, "_");
    exit_section_(b, m, null, r);
    return r;
  }

  // '.' HexDigit (HexDigit | '_')*
  private static boolean HexFloat_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "HexFloat_1_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, DOT);
    r = r && HexDigit(b, l + 1);
    r = r && HexFloat_1_1_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (HexDigit | '_')*
  private static boolean HexFloat_1_1_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "HexFloat_1_1_2")) return false;
    int c = current_position_(b);
    while (true) {
      if (!HexFloat_1_1_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "HexFloat_1_1_2", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // HexDigit | '_'
  private static boolean HexFloat_1_1_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "HexFloat_1_1_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = HexDigit(b, l + 1);
    if (!r) r = consumeToken(b, "_");
    exit_section_(b, m, null, r);
    return r;
  }

  // HexDigit (HexDigit | '_')*
  private static boolean HexFloat_1_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "HexFloat_1_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = HexDigit(b, l + 1);
    r = r && HexFloat_1_2_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (HexDigit | '_')*
  private static boolean HexFloat_1_2_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "HexFloat_1_2_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!HexFloat_1_2_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "HexFloat_1_2_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // HexDigit | '_'
  private static boolean HexFloat_1_2_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "HexFloat_1_2_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = HexDigit(b, l + 1);
    if (!r) r = consumeToken(b, "_");
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'x"' HexStringChar* '"' StringPostfix?
  static boolean HexString(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "HexString")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "x\"");
    r = r && HexString_1(b, l + 1);
    r = r && consumeToken(b, "\"");
    r = r && HexString_3(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // HexStringChar*
  private static boolean HexString_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "HexString_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!HexStringChar(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "HexString_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // StringPostfix?
  private static boolean HexString_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "HexString_3")) return false;
    StringPostfix(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // "regexp:[0-9a-fA-F]" | Whitespace | EndOfLine
  static boolean HexStringChar(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "HexStringChar")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "regexp:[0-9a-fA-F]");
    if (!r) r = Whitespace(b, l + 1);
    if (!r) r = EndOfLine(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // ('0x' | '0X') HexDigit (HexDigit | '_')*
  static boolean HexadecimalInteger(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "HexadecimalInteger")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = HexadecimalInteger_0(b, l + 1);
    r = r && HexDigit(b, l + 1);
    r = r && HexadecimalInteger_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '0x' | '0X'
  private static boolean HexadecimalInteger_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "HexadecimalInteger_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "0x");
    if (!r) r = consumeToken(b, "0X");
    exit_section_(b, m, null, r);
    return r;
  }

  // (HexDigit | '_')*
  private static boolean HexadecimalInteger_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "HexadecimalInteger_2")) return false;
    int c = current_position_(b);
    while (true) {
      if (!HexadecimalInteger_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "HexadecimalInteger_2", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // HexDigit | '_'
  private static boolean HexadecimalInteger_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "HexadecimalInteger_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = HexDigit(b, l + 1);
    if (!r) r = consumeToken(b, "_");
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // "regexp:([a-zA-Z_])([a-zA-Z0-9_])"*
  static boolean Identifier(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Identifier")) return false;
    int c = current_position_(b);
    while (true) {
      if (!consumeToken(b, "regexp:([a-zA-Z_])([a-zA-Z0-9_])")) break;
      if (!empty_element_parsed_guard_(b, "Identifier", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  /* ********************************************************** */
  // 'i'
  static boolean ImaginarySuffix(PsiBuilder b, int l) {
    return consumeToken(b, "i");
  }

  /* ********************************************************** */
  // BinaryInteger | DecimalInteger | HexadecimalInteger
  static boolean Integer(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Integer")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = BinaryInteger(b, l + 1);
    if (!r) r = DecimalInteger(b, l + 1);
    if (!r) r = HexadecimalInteger(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // Integer IntegerSuffix?
  public static boolean IntegerLiteral(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "IntegerLiteral")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<integer literal>");
    r = Integer(b, l + 1);
    r = r && IntegerLiteral_1(b, l + 1);
    exit_section_(b, l, m, INTEGER_LITERAL, r, false, null);
    return r;
  }

  // IntegerSuffix?
  private static boolean IntegerLiteral_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "IntegerLiteral_1")) return false;
    IntegerSuffix(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // 'L' | 'u' | 'U' | 'Lu' | 'LU' | 'uL' | 'UL'
  static boolean IntegerSuffix(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "IntegerSuffix")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "L");
    if (!r) r = consumeToken(b, "u");
    if (!r) r = consumeToken(b, "U");
    if (!r) r = consumeToken(b, "Lu");
    if (!r) r = consumeToken(b, "LU");
    if (!r) r = consumeToken(b, "uL");
    if (!r) r = consumeToken(b, "UL");
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  static boolean LineComment(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "LineComment")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "//");
    r = r && LineComment_1(b, l + 1);
    r = r && LineComment_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  private static boolean LineComment_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "LineComment_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!LineComment_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "LineComment_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  private static boolean LineComment_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "LineComment_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "regexp:~[\\u000D\\u000A\\u2028\\u2029]");
    exit_section_(b, m, null, r);
    return r;
  }

  // EndOfLine | EOF
  private static boolean LineComment_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "LineComment_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = EndOfLine(b, l + 1);
    if (!r) r = consumeToken(b, EOF);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // '/+' (NestingBlockComment | "regexp:.")* '+/'
  static boolean NestingBlockComment(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "NestingBlockComment")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "/+");
    r = r && NestingBlockComment_1(b, l + 1);
    r = r && consumeToken(b, "+/");
    exit_section_(b, m, null, r);
    return r;
  }

  // (NestingBlockComment | "regexp:.")*
  private static boolean NestingBlockComment_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "NestingBlockComment_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!NestingBlockComment_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "NestingBlockComment_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // NestingBlockComment | "regexp:."
  private static boolean NestingBlockComment_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "NestingBlockComment_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = NestingBlockComment(b, l + 1);
    if (!r) r = consumeToken(b, "regexp:.");
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // "regexp:[0-7]"
  static boolean OctalDigit(PsiBuilder b, int l) {
    return consumeToken(b, "regexp:[0-7]");
  }

  /* ********************************************************** */
  // 'L'
  static boolean RealSuffix(PsiBuilder b, int l) {
    return consumeToken(b, "L");
  }

  /* ********************************************************** */
  // "regexp:[\u0020\u0009\u000B\u000C]"
  static boolean Space(PsiBuilder b, int l) {
    return consumeToken(b, "regexp:[\u0020\u0009\u000B\u000C]");
  }

  /* ********************************************************** */
  // '#line' Space+ IntegerLiteral Space* ('"' "regexp:.*?" '"' Space*)? (EndOfLine | EOF)+
  public static boolean SpecialTokenSequence(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "SpecialTokenSequence")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<special token sequence>");
    r = consumeToken(b, "#line");
    r = r && SpecialTokenSequence_1(b, l + 1);
    r = r && IntegerLiteral(b, l + 1);
    r = r && SpecialTokenSequence_3(b, l + 1);
    r = r && SpecialTokenSequence_4(b, l + 1);
    r = r && SpecialTokenSequence_5(b, l + 1);
    exit_section_(b, l, m, SPECIAL_TOKEN_SEQUENCE, r, false, null);
    return r;
  }

  // Space+
  private static boolean SpecialTokenSequence_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "SpecialTokenSequence_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Space(b, l + 1);
    int c = current_position_(b);
    while (r) {
      if (!Space(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "SpecialTokenSequence_1", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // Space*
  private static boolean SpecialTokenSequence_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "SpecialTokenSequence_3")) return false;
    int c = current_position_(b);
    while (true) {
      if (!Space(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "SpecialTokenSequence_3", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // ('"' "regexp:.*?" '"' Space*)?
  private static boolean SpecialTokenSequence_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "SpecialTokenSequence_4")) return false;
    SpecialTokenSequence_4_0(b, l + 1);
    return true;
  }

  // '"' "regexp:.*?" '"' Space*
  private static boolean SpecialTokenSequence_4_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "SpecialTokenSequence_4_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "\"");
    r = r && consumeToken(b, "regexp:.*?");
    r = r && consumeToken(b, "\"");
    r = r && SpecialTokenSequence_4_0_3(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // Space*
  private static boolean SpecialTokenSequence_4_0_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "SpecialTokenSequence_4_0_3")) return false;
    int c = current_position_(b);
    while (true) {
      if (!Space(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "SpecialTokenSequence_4_0_3", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // (EndOfLine | EOF)+
  private static boolean SpecialTokenSequence_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "SpecialTokenSequence_5")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = SpecialTokenSequence_5_0(b, l + 1);
    int c = current_position_(b);
    while (r) {
      if (!SpecialTokenSequence_5_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "SpecialTokenSequence_5", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // EndOfLine | EOF
  private static boolean SpecialTokenSequence_5_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "SpecialTokenSequence_5_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = EndOfLine(b, l + 1);
    if (!r) r = consumeToken(b, EOF);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // WysiwygString | AlternativeWysiwygString | DoubleQuotedString | HexString
  static boolean StringFragment(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "StringFragment")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = WysiwygString(b, l + 1);
    if (!r) r = AlternativeWysiwygString(b, l + 1);
    if (!r) r = DoubleQuotedString(b, l + 1);
    if (!r) r = HexString(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // StringFragment (Whitespace? StringFragment)*
  static boolean StringLiteral(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "StringLiteral")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = StringFragment(b, l + 1);
    r = r && StringLiteral_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (Whitespace? StringFragment)*
  private static boolean StringLiteral_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "StringLiteral_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!StringLiteral_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "StringLiteral_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // Whitespace? StringFragment
  private static boolean StringLiteral_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "StringLiteral_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = StringLiteral_1_0_0(b, l + 1);
    r = r && StringFragment(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // Whitespace?
  private static boolean StringLiteral_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "StringLiteral_1_0_0")) return false;
    Whitespace(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // "regexp:[dwc]"
  static boolean StringPostfix(PsiBuilder b, int l) {
    return consumeToken(b, "regexp:[dwc]");
  }

  /* ********************************************************** */
  static boolean Whitespace(PsiBuilder b, int l) {
    return consumeToken(b, "regexp:[\\u0020\\u0009\\u000b\\u000c\\u000a\\u000d]+");
  }

  /* ********************************************************** */
  // Character | Whitespace
  static boolean WysiwygCharacter(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "WysiwygCharacter")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Character(b, l + 1);
    if (!r) r = Whitespace(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'r"' "regexp:.*?" '"' StringPostfix?
  static boolean WysiwygString(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "WysiwygString")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "r\"");
    r = r && consumeToken(b, "regexp:.*?");
    r = r && consumeToken(b, "\"");
    r = r && WysiwygString_3(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // StringPostfix?
  private static boolean WysiwygString_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "WysiwygString_3")) return false;
    StringPostfix(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // mulExpression
  //      | addExpression ('+' | '-' | '~') mulExpression
  public static boolean addExpression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "addExpression")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<add expression>");
    r = mulExpression(b, l + 1);
    if (!r) r = addExpression_1(b, l + 1);
    exit_section_(b, l, m, ADD_EXPRESSION, r, false, null);
    return r;
  }

  // addExpression ('+' | '-' | '~') mulExpression
  private static boolean addExpression_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "addExpression_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = addExpression(b, l + 1);
    r = r && addExpression_1_1(b, l + 1);
    r = r && mulExpression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '+' | '-' | '~'
  private static boolean addExpression_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "addExpression_1_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PLUS);
    if (!r) r = consumeToken(b, MINUS);
    if (!r) r = consumeToken(b, TILDE);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'alias' aliasInitializer (',' aliasInitializer)* ';'
  //      | 'alias' linkageAttribute? type Identifier ';'
  public static boolean aliasDeclaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "aliasDeclaration")) return false;
    if (!nextTokenIs(b, ALIAS)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = aliasDeclaration_0(b, l + 1);
    if (!r) r = aliasDeclaration_1(b, l + 1);
    exit_section_(b, m, ALIAS_DECLARATION, r);
    return r;
  }

  // 'alias' aliasInitializer (',' aliasInitializer)* ';'
  private static boolean aliasDeclaration_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "aliasDeclaration_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ALIAS);
    r = r && aliasInitializer(b, l + 1);
    r = r && aliasDeclaration_0_2(b, l + 1);
    r = r && consumeToken(b, ";");
    exit_section_(b, m, null, r);
    return r;
  }

  // (',' aliasInitializer)*
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
    r = consumeToken(b, COMMA);
    r = r && aliasInitializer(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // 'alias' linkageAttribute? type Identifier ';'
  private static boolean aliasDeclaration_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "aliasDeclaration_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ALIAS);
    r = r && aliasDeclaration_1_1(b, l + 1);
    r = r && type(b, l + 1);
    r = r && Identifier(b, l + 1);
    r = r && consumeToken(b, ";");
    exit_section_(b, m, null, r);
    return r;
  }

  // linkageAttribute?
  private static boolean aliasDeclaration_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "aliasDeclaration_1_1")) return false;
    linkageAttribute(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // Identifier '=' type
  public static boolean aliasInitializer(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "aliasInitializer")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<alias initializer>");
    r = Identifier(b, l + 1);
    r = r && consumeToken(b, ASSIGN);
    r = r && type(b, l + 1);
    exit_section_(b, l, m, ALIAS_INITIALIZER, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // 'alias' Identifier 'this' ';'
  public static boolean aliasThisDeclaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "aliasThisDeclaration")) return false;
    if (!nextTokenIs(b, ALIAS)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ALIAS);
    r = r && Identifier(b, l + 1);
    r = r && consumeToken(b, THIS);
    r = r && consumeToken(b, ";");
    exit_section_(b, m, ALIAS_THIS_DECLARATION, r);
    return r;
  }

  /* ********************************************************** */
  // 'align' ('(' IntegerLiteral ')')?
  public static boolean alignAttribute(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "alignAttribute")) return false;
    if (!nextTokenIs(b, ALIGN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ALIGN);
    r = r && alignAttribute_1(b, l + 1);
    exit_section_(b, m, ALIGN_ATTRIBUTE, r);
    return r;
  }

  // ('(' IntegerLiteral ')')?
  private static boolean alignAttribute_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "alignAttribute_1")) return false;
    alignAttribute_1_0(b, l + 1);
    return true;
  }

  // '(' IntegerLiteral ')'
  private static boolean alignAttribute_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "alignAttribute_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LPAREN);
    r = r && IntegerLiteral(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // orExpression
  //      | andAndExpression '&&' orExpression
  public static boolean andAndExpression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "andAndExpression")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<and and expression>");
    r = orExpression(b, l + 1);
    if (!r) r = andAndExpression_1(b, l + 1);
    exit_section_(b, l, m, AND_AND_EXPRESSION, r, false, null);
    return r;
  }

  // andAndExpression '&&' orExpression
  private static boolean andAndExpression_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "andAndExpression_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = andAndExpression(b, l + 1);
    r = r && consumeToken(b, LOGICAND);
    r = r && orExpression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // cmpExpression
  //      | andExpression '&' cmpExpression
  public static boolean andExpression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "andExpression")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<and expression>");
    r = cmpExpression(b, l + 1);
    if (!r) r = andExpression_1(b, l + 1);
    exit_section_(b, l, m, AND_EXPRESSION, r, false, null);
    return r;
  }

  // andExpression '&' cmpExpression
  private static boolean andExpression_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "andExpression_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = andExpression(b, l + 1);
    r = r && consumeToken(b, BITAND);
    r = r && cmpExpression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // assignExpression (',' assignExpression?)*
  public static boolean argumentList(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "argumentList")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<argument list>");
    r = assignExpression(b, l + 1);
    r = r && argumentList_1(b, l + 1);
    exit_section_(b, l, m, ARGUMENT_LIST, r, false, null);
    return r;
  }

  // (',' assignExpression?)*
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
    r = consumeToken(b, COMMA);
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
    if (!nextTokenIs(b, LPAREN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LPAREN);
    r = r && arguments_1(b, l + 1);
    r = r && consumeToken(b, RPAREN);
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
  // '[' ']'
  //      | '[' arrayMemberInitialization (',' arrayMemberInitialization?)* ']'
  public static boolean arrayInitializer(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "arrayInitializer")) return false;
    if (!nextTokenIs(b, LBRACKET)) return false;
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
    r = consumeToken(b, LBRACKET);
    r = r && consumeToken(b, RBRACKET);
    exit_section_(b, m, null, r);
    return r;
  }

  // '[' arrayMemberInitialization (',' arrayMemberInitialization?)* ']'
  private static boolean arrayInitializer_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "arrayInitializer_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LBRACKET);
    r = r && arrayMemberInitialization(b, l + 1);
    r = r && arrayInitializer_1_2(b, l + 1);
    r = r && consumeToken(b, RBRACKET);
    exit_section_(b, m, null, r);
    return r;
  }

  // (',' arrayMemberInitialization?)*
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
    r = consumeToken(b, COMMA);
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
    if (!nextTokenIs(b, LBRACKET)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LBRACKET);
    r = r && arrayLiteral_1(b, l + 1);
    r = r && consumeToken(b, RBRACKET);
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
  // (assignExpression ':')? nonVoidInitializer
  public static boolean arrayMemberInitialization(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "arrayMemberInitialization")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<array member initialization>");
    r = arrayMemberInitialization_0(b, l + 1);
    r = r && nonVoidInitializer(b, l + 1);
    exit_section_(b, l, m, ARRAY_MEMBER_INITIALIZATION, r, false, null);
    return r;
  }

  // (assignExpression ':')?
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
    r = r && consumeToken(b, ":");
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // asmMulExp
  //      | asmAddExp ('+' | '-') asmMulExp
  public static boolean asmAddExp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmAddExp")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<asm add exp>");
    r = asmMulExp(b, l + 1);
    if (!r) r = asmAddExp_1(b, l + 1);
    exit_section_(b, l, m, ASM_ADD_EXP, r, false, null);
    return r;
  }

  // asmAddExp ('+' | '-') asmMulExp
  private static boolean asmAddExp_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmAddExp_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = asmAddExp(b, l + 1);
    r = r && asmAddExp_1_1(b, l + 1);
    r = r && asmMulExp(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '+' | '-'
  private static boolean asmAddExp_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmAddExp_1_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PLUS);
    if (!r) r = consumeToken(b, MINUS);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // asmEqualExp ('&' asmEqualExp)?
  public static boolean asmAndExp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmAndExp")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<asm and exp>");
    r = asmEqualExp(b, l + 1);
    r = r && asmAndExp_1(b, l + 1);
    exit_section_(b, l, m, ASM_AND_EXP, r, false, null);
    return r;
  }

  // ('&' asmEqualExp)?
  private static boolean asmAndExp_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmAndExp_1")) return false;
    asmAndExp_1_0(b, l + 1);
    return true;
  }

  // '&' asmEqualExp
  private static boolean asmAndExp_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmAndExp_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, BITAND);
    r = r && asmEqualExp(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // asmUnaExp
  //      | asmBrExp '[' asmExp ']'
  public static boolean asmBrExp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmBrExp")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<asm br exp>");
    r = asmUnaExp(b, l + 1);
    if (!r) r = asmBrExp_1(b, l + 1);
    exit_section_(b, l, m, ASM_BR_EXP, r, false, null);
    return r;
  }

  // asmBrExp '[' asmExp ']'
  private static boolean asmBrExp_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmBrExp_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = asmBrExp(b, l + 1);
    r = r && consumeToken(b, LBRACKET);
    r = r && asmExp(b, l + 1);
    r = r && consumeToken(b, RBRACKET);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // asmRelExp (('==' | '!=') asmRelExp)?
  public static boolean asmEqualExp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmEqualExp")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<asm equal exp>");
    r = asmRelExp(b, l + 1);
    r = r && asmEqualExp_1(b, l + 1);
    exit_section_(b, l, m, ASM_EQUAL_EXP, r, false, null);
    return r;
  }

  // (('==' | '!=') asmRelExp)?
  private static boolean asmEqualExp_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmEqualExp_1")) return false;
    asmEqualExp_1_0(b, l + 1);
    return true;
  }

  // ('==' | '!=') asmRelExp
  private static boolean asmEqualExp_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmEqualExp_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = asmEqualExp_1_0_0(b, l + 1);
    r = r && asmRelExp(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '==' | '!='
  private static boolean asmEqualExp_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmEqualExp_1_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, EQUAL);
    if (!r) r = consumeToken(b, NOTEQUAL);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // asmLogOrExp ('?' asmExp ':' asmExp)?
  public static boolean asmExp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmExp")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<asm exp>");
    r = asmLogOrExp(b, l + 1);
    r = r && asmExp_1(b, l + 1);
    exit_section_(b, l, m, ASM_EXP, r, false, null);
    return r;
  }

  // ('?' asmExp ':' asmExp)?
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
    r = consumeToken(b, TERNARY);
    r = r && asmExp(b, l + 1);
    r = r && consumeToken(b, ":");
    r = r && asmExp(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // Identifier
  //      | 'align' IntegerLiteral
  //      | 'align' Identifier
  //      | Identifier ':' asmInstruction
  //      | Identifier asmExp
  //      | Identifier operands
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
    exit_section_(b, l, m, ASM_INSTRUCTION, r, false, null);
    return r;
  }

  // 'align' IntegerLiteral
  private static boolean asmInstruction_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmInstruction_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ALIGN);
    r = r && IntegerLiteral(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // 'align' Identifier
  private static boolean asmInstruction_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmInstruction_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ALIGN);
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
    r = r && consumeToken(b, ":");
    r = r && asmInstruction(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // Identifier asmExp
  private static boolean asmInstruction_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmInstruction_4")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Identifier(b, l + 1);
    r = r && asmExp(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // Identifier operands
  private static boolean asmInstruction_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmInstruction_5")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Identifier(b, l + 1);
    r = r && operands(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // asmOrExp ('&&' asmOrExp)?
  public static boolean asmLogAndExp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmLogAndExp")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<asm log and exp>");
    r = asmOrExp(b, l + 1);
    r = r && asmLogAndExp_1(b, l + 1);
    exit_section_(b, l, m, ASM_LOG_AND_EXP, r, false, null);
    return r;
  }

  // ('&&' asmOrExp)?
  private static boolean asmLogAndExp_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmLogAndExp_1")) return false;
    asmLogAndExp_1_0(b, l + 1);
    return true;
  }

  // '&&' asmOrExp
  private static boolean asmLogAndExp_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmLogAndExp_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LOGICAND);
    r = r && asmOrExp(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // asmLogAndExp ('||' asmLogAndExp)?
  public static boolean asmLogOrExp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmLogOrExp")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<asm log or exp>");
    r = asmLogAndExp(b, l + 1);
    r = r && asmLogOrExp_1(b, l + 1);
    exit_section_(b, l, m, ASM_LOG_OR_EXP, r, false, null);
    return r;
  }

  // ('||' asmLogAndExp)?
  private static boolean asmLogOrExp_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmLogOrExp_1")) return false;
    asmLogOrExp_1_0(b, l + 1);
    return true;
  }

  // '||' asmLogAndExp
  private static boolean asmLogOrExp_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmLogOrExp_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LOGICOR);
    r = r && asmLogAndExp(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // asmBrExp (('*' | '/' | '%') asmBrExp)?
  public static boolean asmMulExp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmMulExp")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<asm mul exp>");
    r = asmBrExp(b, l + 1);
    r = r && asmMulExp_1(b, l + 1);
    exit_section_(b, l, m, ASM_MUL_EXP, r, false, null);
    return r;
  }

  // (('*' | '/' | '%') asmBrExp)?
  private static boolean asmMulExp_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmMulExp_1")) return false;
    asmMulExp_1_0(b, l + 1);
    return true;
  }

  // ('*' | '/' | '%') asmBrExp
  private static boolean asmMulExp_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmMulExp_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = asmMulExp_1_0_0(b, l + 1);
    r = r && asmBrExp(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '*' | '/' | '%'
  private static boolean asmMulExp_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmMulExp_1_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, STAR);
    if (!r) r = consumeToken(b, DIV);
    if (!r) r = consumeToken(b, MOD);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // asmXorExp ('|' asmXorExp)?
  public static boolean asmOrExp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmOrExp")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<asm or exp>");
    r = asmXorExp(b, l + 1);
    r = r && asmOrExp_1(b, l + 1);
    exit_section_(b, l, m, ASM_OR_EXP, r, false, null);
    return r;
  }

  // ('|' asmXorExp)?
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
    r = consumeToken(b, BITOR);
    r = r && asmXorExp(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // IntegerLiteral
  //      | FloatLiteral
  //      | register
  //      | identifierChain
  //      | '$'
  public static boolean asmPrimaryExp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmPrimaryExp")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<asm primary exp>");
    r = IntegerLiteral(b, l + 1);
    if (!r) r = FloatLiteral(b, l + 1);
    if (!r) r = register(b, l + 1);
    if (!r) r = identifierChain(b, l + 1);
    if (!r) r = consumeToken(b, DOLLAR);
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
    r = consumeToken(b, LESS);
    if (!r) r = consumeToken(b, LESSEQUAL);
    if (!r) r = consumeToken(b, GREATER);
    if (!r) r = consumeToken(b, GREATEREQUAL);
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
    r = consumeToken(b, SHIFTLEFT);
    if (!r) r = consumeToken(b, SHIFTRIGHT);
    if (!r) r = consumeToken(b, UNSIGNEDSHIFTRIGHT);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'asm' '{' asmInstruction+ '}'
  public static boolean asmStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmStatement")) return false;
    if (!nextTokenIs(b, ASM)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ASM);
    r = r && consumeToken(b, LBRACE);
    r = r && asmStatement_2(b, l + 1);
    r = r && consumeToken(b, RBRACE);
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
  // Identifier Identifier
  //      | 'byte' Identifier
  //      | 'short' Identifier
  //      | 'int' Identifier
  //      | 'float' Identifier
  //      | 'double' Identifier
  //      | 'real' Identifier
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

  // Identifier Identifier
  private static boolean asmTypePrefix_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmTypePrefix_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Identifier(b, l + 1);
    r = r && Identifier(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // 'byte' Identifier
  private static boolean asmTypePrefix_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmTypePrefix_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, BYTE);
    r = r && Identifier(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // 'short' Identifier
  private static boolean asmTypePrefix_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmTypePrefix_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, SHORT);
    r = r && Identifier(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // 'int' Identifier
  private static boolean asmTypePrefix_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmTypePrefix_3")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, INT);
    r = r && Identifier(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // 'float' Identifier
  private static boolean asmTypePrefix_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmTypePrefix_4")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, FLOAT);
    r = r && Identifier(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // 'double' Identifier
  private static boolean asmTypePrefix_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmTypePrefix_5")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, DOUBLE);
    r = r && Identifier(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // 'real' Identifier
  private static boolean asmTypePrefix_6(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmTypePrefix_6")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, REAL);
    r = r && Identifier(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // asmTypePrefix asmExp
  //      | Identifier asmExp
  //      | '+' asmUnaExp
  //      | '-' asmUnaExp
  //      | '!' asmUnaExp
  //      | '~' asmUnaExp
  //      | asmPrimaryExp
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
    r = consumeToken(b, PLUS);
    r = r && asmUnaExp(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '-' asmUnaExp
  private static boolean asmUnaExp_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmUnaExp_3")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, MINUS);
    r = r && asmUnaExp(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '!' asmUnaExp
  private static boolean asmUnaExp_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmUnaExp_4")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, NOT);
    r = r && asmUnaExp(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '~' asmUnaExp
  private static boolean asmUnaExp_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmUnaExp_5")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, TILDE);
    r = r && asmUnaExp(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // asmAndExp ('^' asmAndExp)?
  public static boolean asmXorExp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmXorExp")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<asm xor exp>");
    r = asmAndExp(b, l + 1);
    r = r && asmXorExp_1(b, l + 1);
    exit_section_(b, l, m, ASM_XOR_EXP, r, false, null);
    return r;
  }

  // ('^' asmAndExp)?
  private static boolean asmXorExp_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmXorExp_1")) return false;
    asmXorExp_1_0(b, l + 1);
    return true;
  }

  // '^' asmAndExp
  private static boolean asmXorExp_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asmXorExp_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, XOR);
    r = r && asmAndExp(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'assert' '(' assignExpression (',' assignExpression)? ')'
  public static boolean assertExpression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "assertExpression")) return false;
    if (!nextTokenIs(b, ASSERT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ASSERT);
    r = r && consumeToken(b, LPAREN);
    r = r && assignExpression(b, l + 1);
    r = r && assertExpression_3(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    exit_section_(b, m, ASSERT_EXPRESSION, r);
    return r;
  }

  // (',' assignExpression)?
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
    r = consumeToken(b, COMMA);
    r = r && assignExpression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // ternaryExpression (assignOperator assignExpression)?
  public static boolean assignExpression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "assignExpression")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<assign expression>");
    r = ternaryExpression(b, l + 1);
    r = r && assignExpression_1(b, l + 1);
    exit_section_(b, l, m, ASSIGN_EXPRESSION, r, false, null);
    return r;
  }

  // (assignOperator assignExpression)?
  private static boolean assignExpression_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "assignExpression_1")) return false;
    assignExpression_1_0(b, l + 1);
    return true;
  }

  // assignOperator assignExpression
  private static boolean assignExpression_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "assignExpression_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = assignOperator(b, l + 1);
    r = r && assignExpression(b, l + 1);
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
    r = consumeToken(b, ASSIGN);
    if (!r) r = consumeToken(b, UNSIGNEDSHIFTRIGHTEQUAL);
    if (!r) r = consumeToken(b, SHIFTRIGHTEQUAL);
    if (!r) r = consumeToken(b, SHIFTLEFTEQUAL);
    if (!r) r = consumeToken(b, PLUSEQUAL);
    if (!r) r = consumeToken(b, MINUSEQUAL);
    if (!r) r = consumeToken(b, MULEQUAL);
    if (!r) r = consumeToken(b, MODEQUAL);
    if (!r) r = consumeToken(b, BITANDEQUAL);
    if (!r) r = consumeToken(b, DIVEQUAL);
    if (!r) r = consumeToken(b, BITOREQUAL);
    if (!r) r = consumeToken(b, POWEQUAL);
    if (!r) r = consumeToken(b, XOREQUAL);
    if (!r) r = consumeToken(b, CATEQUAL);
    exit_section_(b, l, m, ASSIGN_OPERATOR, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // '[' keyValuePairs ']'
  public static boolean assocArrayLiteral(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "assocArrayLiteral")) return false;
    if (!nextTokenIs(b, LBRACKET)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LBRACKET);
    r = r && keyValuePairs(b, l + 1);
    r = r && consumeToken(b, RBRACKET);
    exit_section_(b, m, ASSOC_ARRAY_LITERAL, r);
    return r;
  }

  /* ********************************************************** */
  // '@' (Identifier | '(' argumentList ')' | functionCallExpression)
  public static boolean atAttribute(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "atAttribute")) return false;
    if (!nextTokenIs(b, AT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, AT);
    r = r && atAttribute_1(b, l + 1);
    exit_section_(b, m, AT_ATTRIBUTE, r);
    return r;
  }

  // Identifier | '(' argumentList ')' | functionCallExpression
  private static boolean atAttribute_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "atAttribute_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Identifier(b, l + 1);
    if (!r) r = atAttribute_1_1(b, l + 1);
    if (!r) r = functionCallExpression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '(' argumentList ')'
  private static boolean atAttribute_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "atAttribute_1_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LPAREN);
    r = r && argumentList(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // alignAttribute
  //      | linkageAttribute
  //      | pragmaExpression
  //      | storageClass
  //      | 'export'
  //      | 'package'
  //      | 'private'
  //      | 'protected'
  //      | 'public'
  public static boolean attribute(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "attribute")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<attribute>");
    r = alignAttribute(b, l + 1);
    if (!r) r = linkageAttribute(b, l + 1);
    if (!r) r = pragmaExpression(b, l + 1);
    if (!r) r = storageClass(b, l + 1);
    if (!r) r = consumeToken(b, EXPORT);
    if (!r) r = consumeToken(b, PACKAGE);
    if (!r) r = consumeToken(b, PRIVATE);
    if (!r) r = consumeToken(b, PROTECTED);
    if (!r) r = consumeToken(b, PUBLIC);
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
    r = r && consumeToken(b, ":");
    exit_section_(b, l, m, ATTRIBUTE_DECLARATION, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // storageClass Identifier '=' initializer (',' Identifier '=' initializer)* ';'
  public static boolean autoDeclaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "autoDeclaration")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<auto declaration>");
    r = storageClass(b, l + 1);
    r = r && Identifier(b, l + 1);
    r = r && consumeToken(b, ASSIGN);
    r = r && initializer(b, l + 1);
    r = r && autoDeclaration_4(b, l + 1);
    r = r && consumeToken(b, ";");
    exit_section_(b, l, m, AUTO_DECLARATION, r, false, null);
    return r;
  }

  // (',' Identifier '=' initializer)*
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
    r = consumeToken(b, COMMA);
    r = r && Identifier(b, l + 1);
    r = r && consumeToken(b, ASSIGN);
    r = r && initializer(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // (typeofExpression '.')? identifierOrTemplateChain
  public static boolean baseClass(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "baseClass")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<base class>");
    r = baseClass_0(b, l + 1);
    r = r && identifierOrTemplateChain(b, l + 1);
    exit_section_(b, l, m, BASE_CLASS, r, false, null);
    return r;
  }

  // (typeofExpression '.')?
  private static boolean baseClass_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "baseClass_0")) return false;
    baseClass_0_0(b, l + 1);
    return true;
  }

  // typeofExpression '.'
  private static boolean baseClass_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "baseClass_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = typeofExpression(b, l + 1);
    r = r && consumeToken(b, DOT);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // baseClass (',' baseClass)*
  public static boolean baseClassList(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "baseClassList")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<base class list>");
    r = baseClass(b, l + 1);
    r = r && baseClassList_1(b, l + 1);
    exit_section_(b, l, m, BASE_CLASS_LIST, r, false, null);
    return r;
  }

  // (',' baseClass)*
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
    r = consumeToken(b, COMMA);
    r = r && baseClass(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // '{' declarationsAndStatements? '}'
  public static boolean blockStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "blockStatement")) return false;
    if (!nextTokenIs(b, LBRACE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LBRACE);
    r = r && blockStatement_1(b, l + 1);
    r = r && consumeToken(b, RBRACE);
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
    if (!nextTokenIs(b, BODY)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, BODY);
    r = r && blockStatement(b, l + 1);
    exit_section_(b, m, BODY_STATEMENT, r);
    return r;
  }

  /* ********************************************************** */
  // 'break' Identifier? ';'
  public static boolean breakStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "breakStatement")) return false;
    if (!nextTokenIs(b, BREAK)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, BREAK);
    r = r && breakStatement_1(b, l + 1);
    r = r && consumeToken(b, ";");
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
  // 'bool'
  //     | 'byte'
  //     | 'ubyte'
  //     | 'short'
  //     | 'ushort'
  //     | 'int'
  //     | 'uint'
  //     | 'long'
  //     | 'ulong'
  //     | 'char'
  //     | 'wchar'
  //     | 'dchar'
  //     | 'float'
  //     | 'double'
  //     | 'real'
  //     | 'ifloat'
  //     | 'idouble'
  //     | 'ireal'
  //     | 'cfloat'
  //     | 'cdouble'
  //     | 'creal'
  //     | 'void'
  public static boolean builtinType(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "builtinType")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<builtin type>");
    r = consumeToken(b, BOOL);
    if (!r) r = consumeToken(b, BYTE);
    if (!r) r = consumeToken(b, UBYTE);
    if (!r) r = consumeToken(b, SHORT);
    if (!r) r = consumeToken(b, USHORT);
    if (!r) r = consumeToken(b, INT);
    if (!r) r = consumeToken(b, UINT);
    if (!r) r = consumeToken(b, LONG);
    if (!r) r = consumeToken(b, ULONG);
    if (!r) r = consumeToken(b, CHAR);
    if (!r) r = consumeToken(b, WCHAR);
    if (!r) r = consumeToken(b, DCHAR);
    if (!r) r = consumeToken(b, FLOAT);
    if (!r) r = consumeToken(b, DOUBLE);
    if (!r) r = consumeToken(b, REAL);
    if (!r) r = consumeToken(b, IFLOAT);
    if (!r) r = consumeToken(b, IDOUBLE);
    if (!r) r = consumeToken(b, IREAL);
    if (!r) r = consumeToken(b, CFLOAT);
    if (!r) r = consumeToken(b, CDOUBLE);
    if (!r) r = consumeToken(b, CREAL);
    if (!r) r = consumeToken(b, VOID);
    exit_section_(b, l, m, BUILTIN_TYPE, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // 'case' assignExpression ':' '...' 'case' assignExpression ':' declarationsAndStatements
  public static boolean caseRangeStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "caseRangeStatement")) return false;
    if (!nextTokenIs(b, CASE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, CASE);
    r = r && assignExpression(b, l + 1);
    r = r && consumeToken(b, ":");
    r = r && consumeToken(b, VARARG);
    r = r && consumeToken(b, CASE);
    r = r && assignExpression(b, l + 1);
    r = r && consumeToken(b, ":");
    r = r && declarationsAndStatements(b, l + 1);
    exit_section_(b, m, CASE_RANGE_STATEMENT, r);
    return r;
  }

  /* ********************************************************** */
  // 'case' argumentList ':' declarationsAndStatements
  public static boolean caseStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "caseStatement")) return false;
    if (!nextTokenIs(b, CASE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, CASE);
    r = r && argumentList(b, l + 1);
    r = r && consumeToken(b, ":");
    r = r && declarationsAndStatements(b, l + 1);
    exit_section_(b, m, CASE_STATEMENT, r);
    return r;
  }

  /* ********************************************************** */
  // 'cast' '(' (type | castQualifier)? ')' unaryExpression
  public static boolean castExpression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "castExpression")) return false;
    if (!nextTokenIs(b, CAST)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, CAST);
    r = r && consumeToken(b, LPAREN);
    r = r && castExpression_2(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    r = r && unaryExpression(b, l + 1);
    exit_section_(b, m, CAST_EXPRESSION, r);
    return r;
  }

  // (type | castQualifier)?
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
  // 'const'
  //     | 'const' 'shared'
  //     | 'immutable'
  //     | 'inout'
  //     | 'inout' 'shared'
  //     | 'shared'
  //     | 'shared' 'const'
  //     | 'shared' 'inout'
  public static boolean castQualifier(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "castQualifier")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<cast qualifier>");
    r = consumeToken(b, CONST);
    if (!r) r = castQualifier_1(b, l + 1);
    if (!r) r = consumeToken(b, IMMUTABLE);
    if (!r) r = consumeToken(b, INOUT);
    if (!r) r = castQualifier_4(b, l + 1);
    if (!r) r = consumeToken(b, SHARED);
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
    r = consumeToken(b, CONST);
    r = r && consumeToken(b, SHARED);
    exit_section_(b, m, null, r);
    return r;
  }

  // 'inout' 'shared'
  private static boolean castQualifier_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "castQualifier_4")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, INOUT);
    r = r && consumeToken(b, SHARED);
    exit_section_(b, m, null, r);
    return r;
  }

  // 'shared' 'const'
  private static boolean castQualifier_6(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "castQualifier_6")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, SHARED);
    r = r && consumeToken(b, CONST);
    exit_section_(b, m, null, r);
    return r;
  }

  // 'shared' 'inout'
  private static boolean castQualifier_7(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "castQualifier_7")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, SHARED);
    r = r && consumeToken(b, INOUT);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'catch' '(' type Identifier? ')' declarationOrStatement
  public static boolean catch_(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "catch_")) return false;
    if (!nextTokenIs(b, TCATCH)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, TCATCH);
    r = r && consumeToken(b, LPAREN);
    r = r && type(b, l + 1);
    r = r && catch__3(b, l + 1);
    r = r && consumeToken(b, RPAREN);
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
  // catch_+
  //      | catch_* lastCatch
  public static boolean catches(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "catches")) return false;
    if (!nextTokenIs(b, TCATCH)) return false;
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
  // 'class' Identifier (':' baseClassList)? ';'
  //      | 'class' Identifier (':' baseClassList)? structBody
  //      | 'class' Identifier templateParameters constraint? (':' baseClassList)? structBody
  //      | 'class' Identifier templateParameters (':' baseClassList)? constraint? structBody
  public static boolean classDeclaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "classDeclaration")) return false;
    if (!nextTokenIs(b, CLASS)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = classDeclaration_0(b, l + 1);
    if (!r) r = classDeclaration_1(b, l + 1);
    if (!r) r = classDeclaration_2(b, l + 1);
    if (!r) r = classDeclaration_3(b, l + 1);
    exit_section_(b, m, CLASS_DECLARATION, r);
    return r;
  }

  // 'class' Identifier (':' baseClassList)? ';'
  private static boolean classDeclaration_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "classDeclaration_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, CLASS);
    r = r && Identifier(b, l + 1);
    r = r && classDeclaration_0_2(b, l + 1);
    r = r && consumeToken(b, ";");
    exit_section_(b, m, null, r);
    return r;
  }

  // (':' baseClassList)?
  private static boolean classDeclaration_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "classDeclaration_0_2")) return false;
    classDeclaration_0_2_0(b, l + 1);
    return true;
  }

  // ':' baseClassList
  private static boolean classDeclaration_0_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "classDeclaration_0_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ":");
    r = r && baseClassList(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // 'class' Identifier (':' baseClassList)? structBody
  private static boolean classDeclaration_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "classDeclaration_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, CLASS);
    r = r && Identifier(b, l + 1);
    r = r && classDeclaration_1_2(b, l + 1);
    r = r && structBody(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (':' baseClassList)?
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
    r = consumeToken(b, ":");
    r = r && baseClassList(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // 'class' Identifier templateParameters constraint? (':' baseClassList)? structBody
  private static boolean classDeclaration_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "classDeclaration_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, CLASS);
    r = r && Identifier(b, l + 1);
    r = r && templateParameters(b, l + 1);
    r = r && classDeclaration_2_3(b, l + 1);
    r = r && classDeclaration_2_4(b, l + 1);
    r = r && structBody(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // constraint?
  private static boolean classDeclaration_2_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "classDeclaration_2_3")) return false;
    constraint(b, l + 1);
    return true;
  }

  // (':' baseClassList)?
  private static boolean classDeclaration_2_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "classDeclaration_2_4")) return false;
    classDeclaration_2_4_0(b, l + 1);
    return true;
  }

  // ':' baseClassList
  private static boolean classDeclaration_2_4_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "classDeclaration_2_4_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ":");
    r = r && baseClassList(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // 'class' Identifier templateParameters (':' baseClassList)? constraint? structBody
  private static boolean classDeclaration_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "classDeclaration_3")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, CLASS);
    r = r && Identifier(b, l + 1);
    r = r && templateParameters(b, l + 1);
    r = r && classDeclaration_3_3(b, l + 1);
    r = r && classDeclaration_3_4(b, l + 1);
    r = r && structBody(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (':' baseClassList)?
  private static boolean classDeclaration_3_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "classDeclaration_3_3")) return false;
    classDeclaration_3_3_0(b, l + 1);
    return true;
  }

  // ':' baseClassList
  private static boolean classDeclaration_3_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "classDeclaration_3_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ":");
    r = r && baseClassList(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // constraint?
  private static boolean classDeclaration_3_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "classDeclaration_3_4")) return false;
    constraint(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // shiftExpression
  //      | equalExpression
  //      | identityExpression
  //      | relExpression
  //      | inExpression
  public static boolean cmpExpression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "cmpExpression")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<cmp expression>");
    r = shiftExpression(b, l + 1);
    if (!r) r = equalExpression(b, l + 1);
    if (!r) r = identityExpression(b, l + 1);
    if (!r) r = relExpression(b, l + 1);
    if (!r) r = inExpression(b, l + 1);
    exit_section_(b, l, m, CMP_EXPRESSION, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // versionCondition
  //      | debugCondition
  //      | staticIfCondition
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
  // compileCondition declaration
  //      | compileCondition ':' declaration+
  //      | compileCondition declaration ('else' declaration)?
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

  // compileCondition ':' declaration+
  private static boolean conditionalDeclaration_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "conditionalDeclaration_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = compileCondition(b, l + 1);
    r = r && consumeToken(b, ":");
    r = r && conditionalDeclaration_1_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // declaration+
  private static boolean conditionalDeclaration_1_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "conditionalDeclaration_1_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = declaration(b, l + 1);
    int c = current_position_(b);
    while (r) {
      if (!declaration(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "conditionalDeclaration_1_2", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // compileCondition declaration ('else' declaration)?
  private static boolean conditionalDeclaration_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "conditionalDeclaration_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = compileCondition(b, l + 1);
    r = r && declaration(b, l + 1);
    r = r && conditionalDeclaration_2_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ('else' declaration)?
  private static boolean conditionalDeclaration_2_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "conditionalDeclaration_2_2")) return false;
    conditionalDeclaration_2_2_0(b, l + 1);
    return true;
  }

  // 'else' declaration
  private static boolean conditionalDeclaration_2_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "conditionalDeclaration_2_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ELSE);
    r = r && declaration(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // compileCondition declarationOrStatement ('else' declarationOrStatement)?
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

  // ('else' declarationOrStatement)?
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
    r = consumeToken(b, ELSE);
    r = r && declarationOrStatement(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'if' '(' expression ')'
  public static boolean constraint(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "constraint")) return false;
    if (!nextTokenIs(b, IF)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IF);
    r = r && consumeToken(b, LPAREN);
    r = r && expression(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    exit_section_(b, m, CONSTRAINT, r);
    return r;
  }

  /* ********************************************************** */
  // 'this' templateParameters parameters memberFunctionAttribute* constraint? (functionBody | ';')
  public static boolean constructor(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "constructor")) return false;
    if (!nextTokenIs(b, THIS)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, THIS);
    r = r && templateParameters(b, l + 1);
    r = r && parameters(b, l + 1);
    r = r && constructor_3(b, l + 1);
    r = r && constructor_4(b, l + 1);
    r = r && constructor_5(b, l + 1);
    exit_section_(b, m, CONSTRUCTOR, r);
    return r;
  }

  // memberFunctionAttribute*
  private static boolean constructor_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "constructor_3")) return false;
    int c = current_position_(b);
    while (true) {
      if (!memberFunctionAttribute(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "constructor_3", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // constraint?
  private static boolean constructor_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "constructor_4")) return false;
    constraint(b, l + 1);
    return true;
  }

  // functionBody | ';'
  private static boolean constructor_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "constructor_5")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = functionBody(b, l + 1);
    if (!r) r = consumeToken(b, ";");
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'continue' Identifier? ';'
  public static boolean continueStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "continueStatement")) return false;
    if (!nextTokenIs(b, CONTINUE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, CONTINUE);
    r = r && continueStatement_1(b, l + 1);
    r = r && consumeToken(b, ";");
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
  // 'debug' ('(' (IntegerLiteral | Identifier) ')')?
  public static boolean debugCondition(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "debugCondition")) return false;
    if (!nextTokenIs(b, DEBUG)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, DEBUG);
    r = r && debugCondition_1(b, l + 1);
    exit_section_(b, m, DEBUG_CONDITION, r);
    return r;
  }

  // ('(' (IntegerLiteral | Identifier) ')')?
  private static boolean debugCondition_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "debugCondition_1")) return false;
    debugCondition_1_0(b, l + 1);
    return true;
  }

  // '(' (IntegerLiteral | Identifier) ')'
  private static boolean debugCondition_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "debugCondition_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LPAREN);
    r = r && debugCondition_1_0_1(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    exit_section_(b, m, null, r);
    return r;
  }

  // IntegerLiteral | Identifier
  private static boolean debugCondition_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "debugCondition_1_0_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = IntegerLiteral(b, l + 1);
    if (!r) r = Identifier(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'debug' '=' (Identifier | IntegerLiteral) ';'
  public static boolean debugSpecification(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "debugSpecification")) return false;
    if (!nextTokenIs(b, DEBUG)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, DEBUG);
    r = r && consumeToken(b, ASSIGN);
    r = r && debugSpecification_2(b, l + 1);
    r = r && consumeToken(b, ";");
    exit_section_(b, m, DEBUG_SPECIFICATION, r);
    return r;
  }

  // Identifier | IntegerLiteral
  private static boolean debugSpecification_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "debugSpecification_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Identifier(b, l + 1);
    if (!r) r = IntegerLiteral(b, l + 1);
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
  //    | aliasThisDeclaration
  //    | classDeclaration
  //    | conditionalDeclaration
  //    | constructor
  //    | destructor
  //    | enumDeclaration
  //    | functionDeclaration
  //    | importDeclaration
  //    | interfaceDeclaration
  //    | mixinDeclaration
  //    | mixinTemplateDeclaration
  //    | pragmaDeclaration
  //    | sharedStaticConstructor
  //    | sharedStaticDestructor
  //    | staticAssertDeclaration
  //    | staticConstructor
  //    | staticDestructor
  //    | structDeclaration
  //    | templateDeclaration
  //    | unionDeclaration
  //    | unittest
  //    | variableDeclaration
  //    | attributeDeclaration
  //    | invariant
  //    | '{' declaration+ '}'
  public static boolean declaration2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "declaration2")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<declaration 2>");
    r = aliasDeclaration(b, l + 1);
    if (!r) r = aliasThisDeclaration(b, l + 1);
    if (!r) r = classDeclaration(b, l + 1);
    if (!r) r = conditionalDeclaration(b, l + 1);
    if (!r) r = constructor(b, l + 1);
    if (!r) r = destructor(b, l + 1);
    if (!r) r = enumDeclaration(b, l + 1);
    if (!r) r = functionDeclaration(b, l + 1);
    if (!r) r = importDeclaration(b, l + 1);
    if (!r) r = interfaceDeclaration(b, l + 1);
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
    if (!r) r = attributeDeclaration(b, l + 1);
    if (!r) r = invariant(b, l + 1);
    if (!r) r = declaration2_25(b, l + 1);
    exit_section_(b, l, m, DECLARATION_2, r, false, null);
    return r;
  }

  // '{' declaration+ '}'
  private static boolean declaration2_25(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "declaration2_25")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LBRACE);
    r = r && declaration2_25_1(b, l + 1);
    r = r && consumeToken(b, RBRACE);
    exit_section_(b, m, null, r);
    return r;
  }

  // declaration+
  private static boolean declaration2_25_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "declaration2_25_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = declaration(b, l + 1);
    int c = current_position_(b);
    while (r) {
      if (!declaration(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "declaration2_25_1", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // declaration
  //      | statement
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
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<declarator>");
    r = Identifier(b, l + 1);
    r = r && declarator_1(b, l + 1);
    exit_section_(b, l, m, DECLARATOR, r, false, null);
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
    r = consumeToken(b, ASSIGN);
    r = r && initializer(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'default' ' ::=' declarationsAndStatements
  public static boolean defaultStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "defaultStatement")) return false;
    if (!nextTokenIs(b, DEFAULT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, DEFAULT);
    r = r && consumeToken(b, " ::=");
    r = r && declarationsAndStatements(b, l + 1);
    exit_section_(b, m, DEFAULT_STATEMENT, r);
    return r;
  }

  /* ********************************************************** */
  // 'delete' unaryExpression
  public static boolean deleteExpression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "deleteExpression")) return false;
    if (!nextTokenIs(b, DELETE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, DELETE);
    r = r && unaryExpression(b, l + 1);
    exit_section_(b, m, DELETE_EXPRESSION, r);
    return r;
  }

  /* ********************************************************** */
  // 'deprecated' ('(' assignExpression ')')?
  public static boolean deprecated(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "deprecated")) return false;
    if (!nextTokenIs(b, TDEPRECATED)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, TDEPRECATED);
    r = r && deprecated_1(b, l + 1);
    exit_section_(b, m, DEPRECATED, r);
    return r;
  }

  // ('(' assignExpression ')')?
  private static boolean deprecated_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "deprecated_1")) return false;
    deprecated_1_0(b, l + 1);
    return true;
  }

  // '(' assignExpression ')'
  private static boolean deprecated_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "deprecated_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LPAREN);
    r = r && assignExpression(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // '~' 'this' '(' ')' memberFunctionAttribute* (functionBody | ';')
  public static boolean destructor(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "destructor")) return false;
    if (!nextTokenIs(b, TILDE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, TILDE);
    r = r && consumeToken(b, THIS);
    r = r && consumeToken(b, LPAREN);
    r = r && consumeToken(b, RPAREN);
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
    if (!r) r = consumeToken(b, ";");
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'do' statementNoCaseNoDefault 'while' '(' expression ')' ';'
  public static boolean doStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "doStatement")) return false;
    if (!nextTokenIs(b, DO)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, DO);
    r = r && statementNoCaseNoDefault(b, l + 1);
    r = r && consumeToken(b, WHILE);
    r = r && consumeToken(b, LPAREN);
    r = r && expression(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    r = r && consumeToken(b, ";");
    exit_section_(b, m, DO_STATEMENT, r);
    return r;
  }

  /* ********************************************************** */
  // ';'
  //      | '{' enumMember (',' enumMember?)* '}'
  public static boolean enumBody(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "enumBody")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<enum body>");
    r = consumeToken(b, ";");
    if (!r) r = enumBody_1(b, l + 1);
    exit_section_(b, l, m, ENUM_BODY, r, false, null);
    return r;
  }

  // '{' enumMember (',' enumMember?)* '}'
  private static boolean enumBody_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "enumBody_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LBRACE);
    r = r && enumMember(b, l + 1);
    r = r && enumBody_1_2(b, l + 1);
    r = r && consumeToken(b, RBRACE);
    exit_section_(b, m, null, r);
    return r;
  }

  // (',' enumMember?)*
  private static boolean enumBody_1_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "enumBody_1_2")) return false;
    int c = current_position_(b);
    while (true) {
      if (!enumBody_1_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "enumBody_1_2", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // ',' enumMember?
  private static boolean enumBody_1_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "enumBody_1_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && enumBody_1_2_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // enumMember?
  private static boolean enumBody_1_2_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "enumBody_1_2_0_1")) return false;
    enumMember(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // 'enum' Identifier? (':' type)? enumBody
  public static boolean enumDeclaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "enumDeclaration")) return false;
    if (!nextTokenIs(b, ENUM)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ENUM);
    r = r && enumDeclaration_1(b, l + 1);
    r = r && enumDeclaration_2(b, l + 1);
    r = r && enumBody(b, l + 1);
    exit_section_(b, m, ENUM_DECLARATION, r);
    return r;
  }

  // Identifier?
  private static boolean enumDeclaration_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "enumDeclaration_1")) return false;
    Identifier(b, l + 1);
    return true;
  }

  // (':' type)?
  private static boolean enumDeclaration_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "enumDeclaration_2")) return false;
    enumDeclaration_2_0(b, l + 1);
    return true;
  }

  // ':' type
  private static boolean enumDeclaration_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "enumDeclaration_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ":");
    r = r && type(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // Identifier
  //      | (Identifier | type) '=' assignExpression
  public static boolean enumMember(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "enumMember")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<enum member>");
    r = Identifier(b, l + 1);
    if (!r) r = enumMember_1(b, l + 1);
    exit_section_(b, l, m, ENUM_MEMBER, r, false, null);
    return r;
  }

  // (Identifier | type) '=' assignExpression
  private static boolean enumMember_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "enumMember_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = enumMember_1_0(b, l + 1);
    r = r && consumeToken(b, ASSIGN);
    r = r && assignExpression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // Identifier | type
  private static boolean enumMember_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "enumMember_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Identifier(b, l + 1);
    if (!r) r = type(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'enum' Identifier templateParameters '=' assignExpression ';'
  public static boolean eponymousTemplateDeclaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "eponymousTemplateDeclaration")) return false;
    if (!nextTokenIs(b, ENUM)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ENUM);
    r = r && Identifier(b, l + 1);
    r = r && templateParameters(b, l + 1);
    r = r && consumeToken(b, ASSIGN);
    r = r && assignExpression(b, l + 1);
    r = r && consumeToken(b, ";");
    exit_section_(b, m, EPONYMOUS_TEMPLATE_DECLARATION, r);
    return r;
  }

  /* ********************************************************** */
  // shiftExpression ('==' | '!=') shiftExpression
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
    r = consumeToken(b, EQUAL);
    if (!r) r = consumeToken(b, NOTEQUAL);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // assignExpression (',' assignExpression)*
  public static boolean expression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "expression")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<expression>");
    r = assignExpression(b, l + 1);
    r = r && expression_1(b, l + 1);
    exit_section_(b, l, m, EXPRESSION, r, false, null);
    return r;
  }

  // (',' assignExpression)*
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
    r = consumeToken(b, COMMA);
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
    r = r && consumeToken(b, ";");
    exit_section_(b, l, m, EXPRESSION_STATEMENT, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // 'final' switchStatement
  public static boolean finalSwitchStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "finalSwitchStatement")) return false;
    if (!nextTokenIs(b, FINAL)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, FINAL);
    r = r && switchStatement(b, l + 1);
    exit_section_(b, m, FINAL_SWITCH_STATEMENT, r);
    return r;
  }

  /* ********************************************************** */
  // 'finally' declarationOrStatement
  public static boolean finally_(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "finally_")) return false;
    if (!nextTokenIs(b, TFINALLY)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, TFINALLY);
    r = r && declarationOrStatement(b, l + 1);
    exit_section_(b, m, FINALLY, r);
    return r;
  }

  /* ********************************************************** */
  // 'for' '(' declarationOrStatement expression? ';' expression? ')' declarationOrStatement
  public static boolean forStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "forStatement")) return false;
    if (!nextTokenIs(b, FOR)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, FOR);
    r = r && consumeToken(b, LPAREN);
    r = r && declarationOrStatement(b, l + 1);
    r = r && forStatement_3(b, l + 1);
    r = r && consumeToken(b, ";");
    r = r && forStatement_5(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    r = r && declarationOrStatement(b, l + 1);
    exit_section_(b, m, FOR_STATEMENT, r);
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
  // ('foreach' | 'foreach_reverse') '(' foreachTypeList ';' expression ')' declarationOrStatement
  //      | ('foreach' | 'foreach_reverse') '(' foreachType ';' expression '..' expression ')' declarationOrStatement
  public static boolean foreachStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "foreachStatement")) return false;
    if (!nextTokenIs(b, "<foreach statement>", FOREACH, FOREACH_REVERSE)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<foreach statement>");
    r = foreachStatement_0(b, l + 1);
    if (!r) r = foreachStatement_1(b, l + 1);
    exit_section_(b, l, m, FOREACH_STATEMENT, r, false, null);
    return r;
  }

  // ('foreach' | 'foreach_reverse') '(' foreachTypeList ';' expression ')' declarationOrStatement
  private static boolean foreachStatement_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "foreachStatement_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = foreachStatement_0_0(b, l + 1);
    r = r && consumeToken(b, LPAREN);
    r = r && foreachTypeList(b, l + 1);
    r = r && consumeToken(b, ";");
    r = r && expression(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    r = r && declarationOrStatement(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // 'foreach' | 'foreach_reverse'
  private static boolean foreachStatement_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "foreachStatement_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, FOREACH);
    if (!r) r = consumeToken(b, FOREACH_REVERSE);
    exit_section_(b, m, null, r);
    return r;
  }

  // ('foreach' | 'foreach_reverse') '(' foreachType ';' expression '..' expression ')' declarationOrStatement
  private static boolean foreachStatement_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "foreachStatement_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = foreachStatement_1_0(b, l + 1);
    r = r && consumeToken(b, LPAREN);
    r = r && foreachType(b, l + 1);
    r = r && consumeToken(b, ";");
    r = r && expression(b, l + 1);
    r = r && consumeToken(b, SLICE);
    r = r && expression(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    r = r && declarationOrStatement(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // 'foreach' | 'foreach_reverse'
  private static boolean foreachStatement_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "foreachStatement_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, FOREACH);
    if (!r) r = consumeToken(b, FOREACH_REVERSE);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // typeConstructors? type? Identifier
  public static boolean foreachType(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "foreachType")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<foreach type>");
    r = foreachType_0(b, l + 1);
    r = r && foreachType_1(b, l + 1);
    r = r && Identifier(b, l + 1);
    exit_section_(b, l, m, FOREACH_TYPE, r, false, null);
    return r;
  }

  // typeConstructors?
  private static boolean foreachType_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "foreachType_0")) return false;
    typeConstructors(b, l + 1);
    return true;
  }

  // type?
  private static boolean foreachType_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "foreachType_1")) return false;
    type(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // foreachType (',' foreachType)*
  public static boolean foreachTypeList(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "foreachTypeList")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<foreach type list>");
    r = foreachType(b, l + 1);
    r = r && foreachTypeList_1(b, l + 1);
    exit_section_(b, l, m, FOREACH_TYPE_LIST, r, false, null);
    return r;
  }

  // (',' foreachType)*
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
    r = consumeToken(b, COMMA);
    r = r && foreachType(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // atAttribute
  //      | 'pure'
  //      | 'nothrow'
  public static boolean functionAttribute(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "functionAttribute")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<function attribute>");
    r = atAttribute(b, l + 1);
    if (!r) r = consumeToken(b, PURE);
    if (!r) r = consumeToken(b, NOTHROW);
    exit_section_(b, l, m, FUNCTION_ATTRIBUTE, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // blockStatement
  //      | (inStatement | outStatement | outStatement inStatement | inStatement outStatement)? bodyStatement
  public static boolean functionBody(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "functionBody")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<function body>");
    r = blockStatement(b, l + 1);
    if (!r) r = functionBody_1(b, l + 1);
    exit_section_(b, l, m, FUNCTION_BODY, r, false, null);
    return r;
  }

  // (inStatement | outStatement | outStatement inStatement | inStatement outStatement)? bodyStatement
  private static boolean functionBody_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "functionBody_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = functionBody_1_0(b, l + 1);
    r = r && bodyStatement(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (inStatement | outStatement | outStatement inStatement | inStatement outStatement)?
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
  // unaryExpression templateArguments? arguments
  //      | type arguments
  public static boolean functionCallExpression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "functionCallExpression")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<function call expression>");
    r = functionCallExpression_0(b, l + 1);
    if (!r) r = functionCallExpression_1(b, l + 1);
    exit_section_(b, l, m, FUNCTION_CALL_EXPRESSION, r, false, null);
    return r;
  }

  // unaryExpression templateArguments? arguments
  private static boolean functionCallExpression_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "functionCallExpression_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = unaryExpression(b, l + 1);
    r = r && functionCallExpression_0_1(b, l + 1);
    r = r && arguments(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // templateArguments?
  private static boolean functionCallExpression_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "functionCallExpression_0_1")) return false;
    templateArguments(b, l + 1);
    return true;
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
    r = r && consumeToken(b, ";");
    exit_section_(b, l, m, FUNCTION_CALL_STATEMENT, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // (storageClass | type) Identifier templateParameters? parameters memberFunctionAttribute* constraint? (functionBody | ';')
  public static boolean functionDeclaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "functionDeclaration")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<function declaration>");
    r = functionDeclaration_0(b, l + 1);
    r = r && Identifier(b, l + 1);
    r = r && functionDeclaration_2(b, l + 1);
    r = r && parameters(b, l + 1);
    r = r && functionDeclaration_4(b, l + 1);
    r = r && functionDeclaration_5(b, l + 1);
    r = r && functionDeclaration_6(b, l + 1);
    exit_section_(b, l, m, FUNCTION_DECLARATION, r, false, null);
    return r;
  }

  // storageClass | type
  private static boolean functionDeclaration_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "functionDeclaration_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = storageClass(b, l + 1);
    if (!r) r = type(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // templateParameters?
  private static boolean functionDeclaration_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "functionDeclaration_2")) return false;
    templateParameters(b, l + 1);
    return true;
  }

  // memberFunctionAttribute*
  private static boolean functionDeclaration_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "functionDeclaration_4")) return false;
    int c = current_position_(b);
    while (true) {
      if (!memberFunctionAttribute(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "functionDeclaration_4", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // constraint?
  private static boolean functionDeclaration_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "functionDeclaration_5")) return false;
    constraint(b, l + 1);
    return true;
  }

  // functionBody | ';'
  private static boolean functionDeclaration_6(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "functionDeclaration_6")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = functionBody(b, l + 1);
    if (!r) r = consumeToken(b, ";");
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // (('function' | 'delegate') type?)? (parameters functionAttribute*)? functionBody
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

  // (('function' | 'delegate') type?)?
  private static boolean functionLiteralExpression_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "functionLiteralExpression_0")) return false;
    functionLiteralExpression_0_0(b, l + 1);
    return true;
  }

  // ('function' | 'delegate') type?
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
    r = consumeToken(b, FUNCTION);
    if (!r) r = consumeToken(b, DELEGATE);
    exit_section_(b, m, null, r);
    return r;
  }

  // type?
  private static boolean functionLiteralExpression_0_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "functionLiteralExpression_0_0_1")) return false;
    type(b, l + 1);
    return true;
  }

  // (parameters functionAttribute*)?
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
  // 'goto' (Identifier | 'default' | 'case' expression?) ';'
  public static boolean gotoStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "gotoStatement")) return false;
    if (!nextTokenIs(b, GOTO)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, GOTO);
    r = r && gotoStatement_1(b, l + 1);
    r = r && consumeToken(b, ";");
    exit_section_(b, m, GOTO_STATEMENT, r);
    return r;
  }

  // Identifier | 'default' | 'case' expression?
  private static boolean gotoStatement_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "gotoStatement_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Identifier(b, l + 1);
    if (!r) r = consumeToken(b, DEFAULT);
    if (!r) r = gotoStatement_1_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // 'case' expression?
  private static boolean gotoStatement_1_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "gotoStatement_1_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, CASE);
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
  // Identifier ('.' Identifier)*
  public static boolean identifierChain(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "identifierChain")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<identifier chain>");
    r = Identifier(b, l + 1);
    r = r && identifierChain_1(b, l + 1);
    exit_section_(b, l, m, IDENTIFIER_CHAIN, r, false, null);
    return r;
  }

  // ('.' Identifier)*
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
    r = consumeToken(b, DOT);
    r = r && Identifier(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // Identifier (',' Identifier)*
  public static boolean identifierList(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "identifierList")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<identifier list>");
    r = Identifier(b, l + 1);
    r = r && identifierList_1(b, l + 1);
    exit_section_(b, l, m, IDENTIFIER_LIST, r, false, null);
    return r;
  }

  // (',' Identifier)*
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
    r = consumeToken(b, COMMA);
    r = r && Identifier(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // identifierOrTemplateInstance ('.' identifierOrTemplateInstance)*
  public static boolean identifierOrTemplateChain(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "identifierOrTemplateChain")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<identifier or template chain>");
    r = identifierOrTemplateInstance(b, l + 1);
    r = r && identifierOrTemplateChain_1(b, l + 1);
    exit_section_(b, l, m, IDENTIFIER_OR_TEMPLATE_CHAIN, r, false, null);
    return r;
  }

  // ('.' identifierOrTemplateInstance)*
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
    r = consumeToken(b, DOT);
    r = r && identifierOrTemplateInstance(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // Identifier
  //      | templateInstance
  public static boolean identifierOrTemplateInstance(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "identifierOrTemplateInstance")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<identifier or template instance>");
    r = Identifier(b, l + 1);
    if (!r) r = templateInstance(b, l + 1);
    exit_section_(b, l, m, IDENTIFIER_OR_TEMPLATE_INSTANCE, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // shiftExpression ('is' | '!' 'is') shiftExpression
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

  // 'is' | '!' 'is'
  private static boolean identityExpression_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "identityExpression_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IS);
    if (!r) r = identityExpression_1_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '!' 'is'
  private static boolean identityExpression_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "identityExpression_1_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, NOT);
    r = r && consumeToken(b, IS);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'auto' Identifier '=' expression
  //      | type Identifier '=' expression
  //      | expression
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
    r = consumeToken(b, AUTO);
    r = r && Identifier(b, l + 1);
    r = r && consumeToken(b, ASSIGN);
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
    r = r && consumeToken(b, ASSIGN);
    r = r && expression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'if' '(' ifCondition ')' declarationOrStatement ('else' declarationOrStatement)?
  public static boolean ifStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ifStatement")) return false;
    if (!nextTokenIs(b, IF)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IF);
    r = r && consumeToken(b, LPAREN);
    r = r && ifCondition(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    r = r && declarationOrStatement(b, l + 1);
    r = r && ifStatement_5(b, l + 1);
    exit_section_(b, m, IF_STATEMENT, r);
    return r;
  }

  // ('else' declarationOrStatement)?
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
    r = consumeToken(b, ELSE);
    r = r && declarationOrStatement(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // Identifier ('=' Identifier)?
  public static boolean importBind(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "importBind")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<import bind>");
    r = Identifier(b, l + 1);
    r = r && importBind_1(b, l + 1);
    exit_section_(b, l, m, IMPORT_BIND, r, false, null);
    return r;
  }

  // ('=' Identifier)?
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
    r = consumeToken(b, ASSIGN);
    r = r && Identifier(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // singleImport ':' importBind (',' importBind)*
  public static boolean importBindings(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "importBindings")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<import bindings>");
    r = singleImport(b, l + 1);
    r = r && consumeToken(b, ":");
    r = r && importBind(b, l + 1);
    r = r && importBindings_3(b, l + 1);
    exit_section_(b, l, m, IMPORT_BINDINGS, r, false, null);
    return r;
  }

  // (',' importBind)*
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
    r = consumeToken(b, COMMA);
    r = r && importBind(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'import' singleImport (',' singleImport)* (',' importBindings)? ';'
  //      | 'import' importBindings ';'
  public static boolean importDeclaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "importDeclaration")) return false;
    if (!nextTokenIs(b, IMPORT)) return false;
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
    r = consumeToken(b, IMPORT);
    r = r && singleImport(b, l + 1);
    r = r && importDeclaration_0_2(b, l + 1);
    r = r && importDeclaration_0_3(b, l + 1);
    r = r && consumeToken(b, ";");
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
    r = consumeToken(b, COMMA);
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
    r = consumeToken(b, COMMA);
    r = r && importBindings(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // 'import' importBindings ';'
  private static boolean importDeclaration_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "importDeclaration_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IMPORT);
    r = r && importBindings(b, l + 1);
    r = r && consumeToken(b, ";");
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'import' '(' assignExpression ')'
  public static boolean importExpression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "importExpression")) return false;
    if (!nextTokenIs(b, IMPORT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IMPORT);
    r = r && consumeToken(b, LPAREN);
    r = r && assignExpression(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    exit_section_(b, m, IMPORT_EXPRESSION, r);
    return r;
  }

  /* ********************************************************** */
  // shiftExpression ('in' | '!' 'in') shiftExpression
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

  // 'in' | '!' 'in'
  private static boolean inExpression_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "inExpression_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IN);
    if (!r) r = inExpression_1_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '!' 'in'
  private static boolean inExpression_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "inExpression_1_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, NOT);
    r = r && consumeToken(b, IN);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'in' blockStatement
  public static boolean inStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "inStatement")) return false;
    if (!nextTokenIs(b, IN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IN);
    r = r && blockStatement(b, l + 1);
    exit_section_(b, m, IN_STATEMENT, r);
    return r;
  }

  /* ********************************************************** */
  // unaryExpression '[' argumentList ']'
  public static boolean indexExpression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "indexExpression")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<index expression>");
    r = unaryExpression(b, l + 1);
    r = r && consumeToken(b, LBRACKET);
    r = r && argumentList(b, l + 1);
    r = r && consumeToken(b, RBRACKET);
    exit_section_(b, l, m, INDEX_EXPRESSION, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // ';'
  //      | statementNoCaseNoDefault
  public static boolean initialize(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "initialize")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<initialize>");
    r = consumeToken(b, ";");
    if (!r) r = statementNoCaseNoDefault(b, l + 1);
    exit_section_(b, l, m, INITIALIZE, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // 'void'
  //      | nonVoidInitializer
  public static boolean initializer(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "initializer")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<initializer>");
    r = consumeToken(b, VOID);
    if (!r) r = nonVoidInitializer(b, l + 1);
    exit_section_(b, l, m, INITIALIZER, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // 'interface' Identifier (';' | (templateParameters constraint?)? (':' baseClassList)? structBody)
  public static boolean interfaceDeclaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "interfaceDeclaration")) return false;
    if (!nextTokenIs(b, INTERFACE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, INTERFACE);
    r = r && Identifier(b, l + 1);
    r = r && interfaceDeclaration_2(b, l + 1);
    exit_section_(b, m, INTERFACE_DECLARATION, r);
    return r;
  }

  // ';' | (templateParameters constraint?)? (':' baseClassList)? structBody
  private static boolean interfaceDeclaration_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "interfaceDeclaration_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ";");
    if (!r) r = interfaceDeclaration_2_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (templateParameters constraint?)? (':' baseClassList)? structBody
  private static boolean interfaceDeclaration_2_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "interfaceDeclaration_2_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = interfaceDeclaration_2_1_0(b, l + 1);
    r = r && interfaceDeclaration_2_1_1(b, l + 1);
    r = r && structBody(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (templateParameters constraint?)?
  private static boolean interfaceDeclaration_2_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "interfaceDeclaration_2_1_0")) return false;
    interfaceDeclaration_2_1_0_0(b, l + 1);
    return true;
  }

  // templateParameters constraint?
  private static boolean interfaceDeclaration_2_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "interfaceDeclaration_2_1_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = templateParameters(b, l + 1);
    r = r && interfaceDeclaration_2_1_0_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // constraint?
  private static boolean interfaceDeclaration_2_1_0_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "interfaceDeclaration_2_1_0_0_1")) return false;
    constraint(b, l + 1);
    return true;
  }

  // (':' baseClassList)?
  private static boolean interfaceDeclaration_2_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "interfaceDeclaration_2_1_1")) return false;
    interfaceDeclaration_2_1_1_0(b, l + 1);
    return true;
  }

  // ':' baseClassList
  private static boolean interfaceDeclaration_2_1_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "interfaceDeclaration_2_1_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ":");
    r = r && baseClassList(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'invariant' ('(' ')')? blockStatement
  public static boolean invariant(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "invariant")) return false;
    if (!nextTokenIs(b, TINVARIANT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, TINVARIANT);
    r = r && invariant_1(b, l + 1);
    r = r && blockStatement(b, l + 1);
    exit_section_(b, m, INVARIANT, r);
    return r;
  }

  // ('(' ')')?
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
    r = consumeToken(b, LPAREN);
    r = r && consumeToken(b, RPAREN);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'is' '(' type Identifier? ((':' | '==') typeSpecialization (',' templateParameterList)?)? ')'
  public static boolean isExpression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "isExpression")) return false;
    if (!nextTokenIs(b, IS)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IS);
    r = r && consumeToken(b, LPAREN);
    r = r && type(b, l + 1);
    r = r && isExpression_3(b, l + 1);
    r = r && isExpression_4(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    exit_section_(b, m, IS_EXPRESSION, r);
    return r;
  }

  // Identifier?
  private static boolean isExpression_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "isExpression_3")) return false;
    Identifier(b, l + 1);
    return true;
  }

  // ((':' | '==') typeSpecialization (',' templateParameterList)?)?
  private static boolean isExpression_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "isExpression_4")) return false;
    isExpression_4_0(b, l + 1);
    return true;
  }

  // (':' | '==') typeSpecialization (',' templateParameterList)?
  private static boolean isExpression_4_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "isExpression_4_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = isExpression_4_0_0(b, l + 1);
    r = r && typeSpecialization(b, l + 1);
    r = r && isExpression_4_0_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ':' | '=='
  private static boolean isExpression_4_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "isExpression_4_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ":");
    if (!r) r = consumeToken(b, EQUAL);
    exit_section_(b, m, null, r);
    return r;
  }

  // (',' templateParameterList)?
  private static boolean isExpression_4_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "isExpression_4_0_2")) return false;
    isExpression_4_0_2_0(b, l + 1);
    return true;
  }

  // ',' templateParameterList
  private static boolean isExpression_4_0_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "isExpression_4_0_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && templateParameterList(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // module
  // | addExpression
  // | aliasDeclaration
  // | aliasInitializer
  // | aliasThisDeclaration
  // | alignAttribute
  // | andAndExpression
  // | andExpression
  // | argumentList
  // | arguments
  // | arrayInitializer
  // | arrayLiteral
  // | arrayMemberInitialization
  // | asmAddExp
  // | asmAndExp
  // | asmBrExp
  // | asmEqualExp
  // | asmExp
  // | asmInstruction
  // | asmLogAndExp
  // | asmLogOrExp
  // | asmMulExp
  // | asmOrExp
  // | asmPrimaryExp
  // | asmRelExp
  // | asmShiftExp
  // | asmStatement
  // | asmTypePrefix
  // | asmUnaExp
  // | asmXorExp
  // | assertExpression
  // | assignExpression
  // | assignOperator
  // | assocArrayLiteral
  // | atAttribute
  // | attribute
  // | attributeDeclaration
  // | autoDeclaration
  // | blockStatement
  // | bodyStatement
  // | breakStatement
  // | baseClass
  // | baseClassList
  // | builtinType
  // | caseRangeStatement
  // | caseStatement
  // | castExpression
  // | castQualifier
  // | catch_
  // | catches
  // | classDeclaration
  // | cmpExpression
  // | compileCondition
  // | conditionalDeclaration
  // | conditionalStatement
  // | constraint
  // | constructor
  // | continueStatement
  // | debugCondition
  // | debugSpecification
  // | declaration
  // | declaration2
  // | declarationsAndStatements
  // | declarationOrStatement
  // | declarator
  // | defaultStatement
  // | deleteExpression
  // | deprecated
  // | destructor
  // | doStatement
  // | enumBody
  // | enumDeclaration
  // | enumMember
  // | equalExpression
  // | expression
  // | expressionStatement
  // | finalSwitchStatement
  // | finally_
  // | forStatement
  // | foreachStatement
  // | foreachType
  // | foreachTypeList
  // | functionAttribute
  // | functionBody
  // | functionCallExpression
  // | functionCallStatement
  // | functionDeclaration
  // | functionLiteralExpression
  // | gotoStatement
  // | identifierChain
  // | identifierList
  // | identifierOrTemplateChain
  // | identifierOrTemplateInstance
  // | identityExpression
  // | ifStatement
  // | ifCondition
  // | importBind
  // | importBindings
  // | importDeclaration
  // | importExpression
  // | indexExpression
  // | inExpression
  // | inStatement
  // | initialize
  // | initializer
  // | interfaceDeclaration
  // | invariant
  // | isExpression
  // | keyValuePair
  // | keyValuePairs
  // | labeledStatement
  // | lambdaExpression
  // | lastCatch
  // | linkageAttribute
  // | memberFunctionAttribute
  // | mixinDeclaration
  // | mixinExpression
  // | mixinTemplateDeclaration
  // | mixinTemplateName
  // | moduleDeclaration
  // | mulExpression
  // | newAnonClassExpression
  // | newExpression
  // | statementNoCaseNoDefault
  // | nonVoidInitializer
  // | operands
  // | orExpression
  // | orOrExpression
  // | outStatement
  // | parameter
  // | parameterAttribute
  // | parameters
  // | postblit
  // | postIncDecExpression
  // | powExpression
  // | pragmaDeclaration
  // | pragmaExpression
  // | preIncDecExpression
  // | primaryExpression
  // | register
  // | relExpression
  // | relOperator
  // | returnStatement
  // | scopeGuardStatement
  // | sharedStaticConstructor
  // | sharedStaticDestructor
  // | shiftExpression
  // | singleImport
  // | sliceExpression
  // | statement
  // | staticAssertDeclaration
  // | staticAssertStatement
  // | staticConstructor
  // | staticDestructor
  // | staticIfCondition
  // | storageClass
  // | structBody
  // | structDeclaration
  // | structInitializer
  // | structMemberInitializer
  // | structMemberInitializers
  // | switchStatement
  // | symbol
  // | synchronizedStatement
  // | templateAliasParameter
  // | templateArgument
  // | templateArgumentList
  // | templateArguments
  // | templateDeclaration
  // | eponymousTemplateDeclaration
  // | templateInstance
  // | templateMixinExpression
  // | templateParameter
  // | templateParameterList
  // | templateParameters
  // | templateSingleArgument
  // | templateThisParameter
  // | templateTupleParameter
  // | templateTypeParameter
  // | templateValueParameter
  // | templateValueParameterDefault
  // | ternaryExpression
  // | throwStatement
  // | traitsExpression
  // | tryStatement
  // | type
  // | type2
  // | typeConstructor
  // | typeConstructors
  // | typeSpecialization
  // | typeSuffix
  // | typeidExpression
  // | typeofExpression
  // | unaryExpression
  // | unionDeclaration
  // | unittest
  // | variableDeclaration
  // | vector
  // | versionCondition
  // | versionSpecification
  // | whileStatement
  // | withStatement
  // | xorExpression
  static boolean item_(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "item_")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = module(b, l + 1);
    if (!r) r = addExpression(b, l + 1);
    if (!r) r = aliasDeclaration(b, l + 1);
    if (!r) r = aliasInitializer(b, l + 1);
    if (!r) r = aliasThisDeclaration(b, l + 1);
    if (!r) r = alignAttribute(b, l + 1);
    if (!r) r = andAndExpression(b, l + 1);
    if (!r) r = andExpression(b, l + 1);
    if (!r) r = argumentList(b, l + 1);
    if (!r) r = arguments(b, l + 1);
    if (!r) r = arrayInitializer(b, l + 1);
    if (!r) r = arrayLiteral(b, l + 1);
    if (!r) r = arrayMemberInitialization(b, l + 1);
    if (!r) r = asmAddExp(b, l + 1);
    if (!r) r = asmAndExp(b, l + 1);
    if (!r) r = asmBrExp(b, l + 1);
    if (!r) r = asmEqualExp(b, l + 1);
    if (!r) r = asmExp(b, l + 1);
    if (!r) r = asmInstruction(b, l + 1);
    if (!r) r = asmLogAndExp(b, l + 1);
    if (!r) r = asmLogOrExp(b, l + 1);
    if (!r) r = asmMulExp(b, l + 1);
    if (!r) r = asmOrExp(b, l + 1);
    if (!r) r = asmPrimaryExp(b, l + 1);
    if (!r) r = asmRelExp(b, l + 1);
    if (!r) r = asmShiftExp(b, l + 1);
    if (!r) r = asmStatement(b, l + 1);
    if (!r) r = asmTypePrefix(b, l + 1);
    if (!r) r = asmUnaExp(b, l + 1);
    if (!r) r = asmXorExp(b, l + 1);
    if (!r) r = assertExpression(b, l + 1);
    if (!r) r = assignExpression(b, l + 1);
    if (!r) r = assignOperator(b, l + 1);
    if (!r) r = assocArrayLiteral(b, l + 1);
    if (!r) r = atAttribute(b, l + 1);
    if (!r) r = attribute(b, l + 1);
    if (!r) r = attributeDeclaration(b, l + 1);
    if (!r) r = autoDeclaration(b, l + 1);
    if (!r) r = blockStatement(b, l + 1);
    if (!r) r = bodyStatement(b, l + 1);
    if (!r) r = breakStatement(b, l + 1);
    if (!r) r = baseClass(b, l + 1);
    if (!r) r = baseClassList(b, l + 1);
    if (!r) r = builtinType(b, l + 1);
    if (!r) r = caseRangeStatement(b, l + 1);
    if (!r) r = caseStatement(b, l + 1);
    if (!r) r = castExpression(b, l + 1);
    if (!r) r = castQualifier(b, l + 1);
    if (!r) r = catch_(b, l + 1);
    if (!r) r = catches(b, l + 1);
    if (!r) r = classDeclaration(b, l + 1);
    if (!r) r = cmpExpression(b, l + 1);
    if (!r) r = compileCondition(b, l + 1);
    if (!r) r = conditionalDeclaration(b, l + 1);
    if (!r) r = conditionalStatement(b, l + 1);
    if (!r) r = constraint(b, l + 1);
    if (!r) r = constructor(b, l + 1);
    if (!r) r = continueStatement(b, l + 1);
    if (!r) r = debugCondition(b, l + 1);
    if (!r) r = debugSpecification(b, l + 1);
    if (!r) r = declaration(b, l + 1);
    if (!r) r = declaration2(b, l + 1);
    if (!r) r = declarationsAndStatements(b, l + 1);
    if (!r) r = declarationOrStatement(b, l + 1);
    if (!r) r = declarator(b, l + 1);
    if (!r) r = defaultStatement(b, l + 1);
    if (!r) r = deleteExpression(b, l + 1);
    if (!r) r = deprecated(b, l + 1);
    if (!r) r = destructor(b, l + 1);
    if (!r) r = doStatement(b, l + 1);
    if (!r) r = enumBody(b, l + 1);
    if (!r) r = enumDeclaration(b, l + 1);
    if (!r) r = enumMember(b, l + 1);
    if (!r) r = equalExpression(b, l + 1);
    if (!r) r = expression(b, l + 1);
    if (!r) r = expressionStatement(b, l + 1);
    if (!r) r = finalSwitchStatement(b, l + 1);
    if (!r) r = finally_(b, l + 1);
    if (!r) r = forStatement(b, l + 1);
    if (!r) r = foreachStatement(b, l + 1);
    if (!r) r = foreachType(b, l + 1);
    if (!r) r = foreachTypeList(b, l + 1);
    if (!r) r = functionAttribute(b, l + 1);
    if (!r) r = functionBody(b, l + 1);
    if (!r) r = functionCallExpression(b, l + 1);
    if (!r) r = functionCallStatement(b, l + 1);
    if (!r) r = functionDeclaration(b, l + 1);
    if (!r) r = functionLiteralExpression(b, l + 1);
    if (!r) r = gotoStatement(b, l + 1);
    if (!r) r = identifierChain(b, l + 1);
    if (!r) r = identifierList(b, l + 1);
    if (!r) r = identifierOrTemplateChain(b, l + 1);
    if (!r) r = identifierOrTemplateInstance(b, l + 1);
    if (!r) r = identityExpression(b, l + 1);
    if (!r) r = ifStatement(b, l + 1);
    if (!r) r = ifCondition(b, l + 1);
    if (!r) r = importBind(b, l + 1);
    if (!r) r = importBindings(b, l + 1);
    if (!r) r = importDeclaration(b, l + 1);
    if (!r) r = importExpression(b, l + 1);
    if (!r) r = indexExpression(b, l + 1);
    if (!r) r = inExpression(b, l + 1);
    if (!r) r = inStatement(b, l + 1);
    if (!r) r = initialize(b, l + 1);
    if (!r) r = initializer(b, l + 1);
    if (!r) r = interfaceDeclaration(b, l + 1);
    if (!r) r = invariant(b, l + 1);
    if (!r) r = isExpression(b, l + 1);
    if (!r) r = keyValuePair(b, l + 1);
    if (!r) r = keyValuePairs(b, l + 1);
    if (!r) r = labeledStatement(b, l + 1);
    if (!r) r = lambdaExpression(b, l + 1);
    if (!r) r = lastCatch(b, l + 1);
    if (!r) r = linkageAttribute(b, l + 1);
    if (!r) r = memberFunctionAttribute(b, l + 1);
    if (!r) r = mixinDeclaration(b, l + 1);
    if (!r) r = mixinExpression(b, l + 1);
    if (!r) r = mixinTemplateDeclaration(b, l + 1);
    if (!r) r = mixinTemplateName(b, l + 1);
    if (!r) r = moduleDeclaration(b, l + 1);
    if (!r) r = mulExpression(b, l + 1);
    if (!r) r = newAnonClassExpression(b, l + 1);
    if (!r) r = newExpression(b, l + 1);
    if (!r) r = statementNoCaseNoDefault(b, l + 1);
    if (!r) r = nonVoidInitializer(b, l + 1);
    if (!r) r = operands(b, l + 1);
    if (!r) r = orExpression(b, l + 1);
    if (!r) r = orOrExpression(b, l + 1);
    if (!r) r = outStatement(b, l + 1);
    if (!r) r = parameter(b, l + 1);
    if (!r) r = parameterAttribute(b, l + 1);
    if (!r) r = parameters(b, l + 1);
    if (!r) r = postblit(b, l + 1);
    if (!r) r = postIncDecExpression(b, l + 1);
    if (!r) r = powExpression(b, l + 1);
    if (!r) r = pragmaDeclaration(b, l + 1);
    if (!r) r = pragmaExpression(b, l + 1);
    if (!r) r = preIncDecExpression(b, l + 1);
    if (!r) r = primaryExpression(b, l + 1);
    if (!r) r = register(b, l + 1);
    if (!r) r = relExpression(b, l + 1);
    if (!r) r = relOperator(b, l + 1);
    if (!r) r = returnStatement(b, l + 1);
    if (!r) r = scopeGuardStatement(b, l + 1);
    if (!r) r = sharedStaticConstructor(b, l + 1);
    if (!r) r = sharedStaticDestructor(b, l + 1);
    if (!r) r = shiftExpression(b, l + 1);
    if (!r) r = singleImport(b, l + 1);
    if (!r) r = sliceExpression(b, l + 1);
    if (!r) r = statement(b, l + 1);
    if (!r) r = staticAssertDeclaration(b, l + 1);
    if (!r) r = staticAssertStatement(b, l + 1);
    if (!r) r = staticConstructor(b, l + 1);
    if (!r) r = staticDestructor(b, l + 1);
    if (!r) r = staticIfCondition(b, l + 1);
    if (!r) r = storageClass(b, l + 1);
    if (!r) r = structBody(b, l + 1);
    if (!r) r = structDeclaration(b, l + 1);
    if (!r) r = structInitializer(b, l + 1);
    if (!r) r = structMemberInitializer(b, l + 1);
    if (!r) r = structMemberInitializers(b, l + 1);
    if (!r) r = switchStatement(b, l + 1);
    if (!r) r = symbol(b, l + 1);
    if (!r) r = synchronizedStatement(b, l + 1);
    if (!r) r = templateAliasParameter(b, l + 1);
    if (!r) r = templateArgument(b, l + 1);
    if (!r) r = templateArgumentList(b, l + 1);
    if (!r) r = templateArguments(b, l + 1);
    if (!r) r = templateDeclaration(b, l + 1);
    if (!r) r = eponymousTemplateDeclaration(b, l + 1);
    if (!r) r = templateInstance(b, l + 1);
    if (!r) r = templateMixinExpression(b, l + 1);
    if (!r) r = templateParameter(b, l + 1);
    if (!r) r = templateParameterList(b, l + 1);
    if (!r) r = templateParameters(b, l + 1);
    if (!r) r = templateSingleArgument(b, l + 1);
    if (!r) r = templateThisParameter(b, l + 1);
    if (!r) r = templateTupleParameter(b, l + 1);
    if (!r) r = templateTypeParameter(b, l + 1);
    if (!r) r = templateValueParameter(b, l + 1);
    if (!r) r = templateValueParameterDefault(b, l + 1);
    if (!r) r = ternaryExpression(b, l + 1);
    if (!r) r = throwStatement(b, l + 1);
    if (!r) r = traitsExpression(b, l + 1);
    if (!r) r = tryStatement(b, l + 1);
    if (!r) r = type(b, l + 1);
    if (!r) r = type2(b, l + 1);
    if (!r) r = typeConstructor(b, l + 1);
    if (!r) r = typeConstructors(b, l + 1);
    if (!r) r = typeSpecialization(b, l + 1);
    if (!r) r = typeSuffix(b, l + 1);
    if (!r) r = typeidExpression(b, l + 1);
    if (!r) r = typeofExpression(b, l + 1);
    if (!r) r = unaryExpression(b, l + 1);
    if (!r) r = unionDeclaration(b, l + 1);
    if (!r) r = unittest(b, l + 1);
    if (!r) r = variableDeclaration(b, l + 1);
    if (!r) r = vector(b, l + 1);
    if (!r) r = versionCondition(b, l + 1);
    if (!r) r = versionSpecification(b, l + 1);
    if (!r) r = whileStatement(b, l + 1);
    if (!r) r = withStatement(b, l + 1);
    if (!r) r = xorExpression(b, l + 1);
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
    r = r && consumeToken(b, ":");
    r = r && assignExpression(b, l + 1);
    exit_section_(b, l, m, KEY_VALUE_PAIR, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // keyValuePair (',' keyValuePair)* ','?
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

  // (',' keyValuePair)*
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
    r = consumeToken(b, COMMA);
    r = r && keyValuePair(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ','?
  private static boolean keyValuePairs_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "keyValuePairs_2")) return false;
    consumeToken(b, COMMA);
    return true;
  }

  /* ********************************************************** */
  // Identifier ':' declarationOrStatement
  public static boolean labeledStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "labeledStatement")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<labeled statement>");
    r = Identifier(b, l + 1);
    r = r && consumeToken(b, ":");
    r = r && declarationOrStatement(b, l + 1);
    exit_section_(b, l, m, LABELED_STATEMENT, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // Identifier '=>' assignExpression
  //      | 'function' parameters functionAttribute* '=>' assignExpression
  //      | 'delegate' parameters functionAttribute* '=>' assignExpression
  //      | parameters functionAttribute* '=>' assignExpression
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
    r = r && consumeToken(b, GOESTO);
    r = r && assignExpression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // 'function' parameters functionAttribute* '=>' assignExpression
  private static boolean lambdaExpression_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "lambdaExpression_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, FUNCTION);
    r = r && parameters(b, l + 1);
    r = r && lambdaExpression_1_2(b, l + 1);
    r = r && consumeToken(b, GOESTO);
    r = r && assignExpression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // functionAttribute*
  private static boolean lambdaExpression_1_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "lambdaExpression_1_2")) return false;
    int c = current_position_(b);
    while (true) {
      if (!functionAttribute(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "lambdaExpression_1_2", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // 'delegate' parameters functionAttribute* '=>' assignExpression
  private static boolean lambdaExpression_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "lambdaExpression_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, DELEGATE);
    r = r && parameters(b, l + 1);
    r = r && lambdaExpression_2_2(b, l + 1);
    r = r && consumeToken(b, GOESTO);
    r = r && assignExpression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // functionAttribute*
  private static boolean lambdaExpression_2_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "lambdaExpression_2_2")) return false;
    int c = current_position_(b);
    while (true) {
      if (!functionAttribute(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "lambdaExpression_2_2", c)) break;
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
    r = r && consumeToken(b, GOESTO);
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
    if (!nextTokenIs(b, TCATCH)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, TCATCH);
    r = r && statementNoCaseNoDefault(b, l + 1);
    exit_section_(b, m, LAST_CATCH, r);
    return r;
  }

  /* ********************************************************** */
  // 'extern' '(' Identifier ('++' (',' identifierChain)?)? ')'
  public static boolean linkageAttribute(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "linkageAttribute")) return false;
    if (!nextTokenIs(b, EXTERN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, EXTERN);
    r = r && consumeToken(b, LPAREN);
    r = r && Identifier(b, l + 1);
    r = r && linkageAttribute_3(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    exit_section_(b, m, LINKAGE_ATTRIBUTE, r);
    return r;
  }

  // ('++' (',' identifierChain)?)?
  private static boolean linkageAttribute_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "linkageAttribute_3")) return false;
    linkageAttribute_3_0(b, l + 1);
    return true;
  }

  // '++' (',' identifierChain)?
  private static boolean linkageAttribute_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "linkageAttribute_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, INCREMENT);
    r = r && linkageAttribute_3_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (',' identifierChain)?
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
    r = consumeToken(b, COMMA);
    r = r && identifierChain(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // functionAttribute
  //      | 'immutable'
  //      | 'inout'
  //      | 'shared'
  //      | 'const'
  public static boolean memberFunctionAttribute(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "memberFunctionAttribute")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<member function attribute>");
    r = functionAttribute(b, l + 1);
    if (!r) r = consumeToken(b, IMMUTABLE);
    if (!r) r = consumeToken(b, INOUT);
    if (!r) r = consumeToken(b, SHARED);
    if (!r) r = consumeToken(b, CONST);
    exit_section_(b, l, m, MEMBER_FUNCTION_ATTRIBUTE, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // mixinExpression ';'
  //      | templateMixinExpression ';'
  public static boolean mixinDeclaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "mixinDeclaration")) return false;
    if (!nextTokenIs(b, MIXIN)) return false;
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
    r = r && consumeToken(b, ";");
    exit_section_(b, m, null, r);
    return r;
  }

  // templateMixinExpression ';'
  private static boolean mixinDeclaration_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "mixinDeclaration_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = templateMixinExpression(b, l + 1);
    r = r && consumeToken(b, ";");
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'mixin' '(' assignExpression ')'
  public static boolean mixinExpression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "mixinExpression")) return false;
    if (!nextTokenIs(b, MIXIN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, MIXIN);
    r = r && consumeToken(b, LPAREN);
    r = r && assignExpression(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    exit_section_(b, m, MIXIN_EXPRESSION, r);
    return r;
  }

  /* ********************************************************** */
  // 'mixin' templateDeclaration
  public static boolean mixinTemplateDeclaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "mixinTemplateDeclaration")) return false;
    if (!nextTokenIs(b, MIXIN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, MIXIN);
    r = r && templateDeclaration(b, l + 1);
    exit_section_(b, m, MIXIN_TEMPLATE_DECLARATION, r);
    return r;
  }

  /* ********************************************************** */
  // symbol
  //      | typeofExpression '.' identifierOrTemplateChain
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
    r = r && consumeToken(b, DOT);
    r = r && identifierOrTemplateChain(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // moduleDeclaration? declaration*
  public static boolean module(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "module")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<module>");
    r = module_0(b, l + 1);
    r = r && module_1(b, l + 1);
    exit_section_(b, l, m, MODULE, r, false, null);
    return r;
  }

  // moduleDeclaration?
  private static boolean module_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "module_0")) return false;
    moduleDeclaration(b, l + 1);
    return true;
  }

  // declaration*
  private static boolean module_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "module_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!declaration(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "module_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  /* ********************************************************** */
  // 'module' identifierChain ';'
  public static boolean moduleDeclaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "moduleDeclaration")) return false;
    if (!nextTokenIs(b, TMODULE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, TMODULE);
    r = r && identifierChain(b, l + 1);
    r = r && consumeToken(b, ";");
    exit_section_(b, m, MODULE_DECLARATION, r);
    return r;
  }

  /* ********************************************************** */
  // powExpression
  //      | mulExpression ('*' | '/' | '%') powExpression
  public static boolean mulExpression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "mulExpression")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<mul expression>");
    r = powExpression(b, l + 1);
    if (!r) r = mulExpression_1(b, l + 1);
    exit_section_(b, l, m, MUL_EXPRESSION, r, false, null);
    return r;
  }

  // mulExpression ('*' | '/' | '%') powExpression
  private static boolean mulExpression_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "mulExpression_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = mulExpression(b, l + 1);
    r = r && mulExpression_1_1(b, l + 1);
    r = r && powExpression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '*' | '/' | '%'
  private static boolean mulExpression_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "mulExpression_1_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, STAR);
    if (!r) r = consumeToken(b, DIV);
    if (!r) r = consumeToken(b, MOD);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'new' arguments? 'class' arguments? baseClassList? structBody
  public static boolean newAnonClassExpression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "newAnonClassExpression")) return false;
    if (!nextTokenIs(b, NEW)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, NEW);
    r = r && newAnonClassExpression_1(b, l + 1);
    r = r && consumeToken(b, CLASS);
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
  // 'new' type ('[' assignExpression ']' | arguments)?
  //      | newAnonClassExpression
  public static boolean newExpression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "newExpression")) return false;
    if (!nextTokenIs(b, NEW)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = newExpression_0(b, l + 1);
    if (!r) r = newAnonClassExpression(b, l + 1);
    exit_section_(b, m, NEW_EXPRESSION, r);
    return r;
  }

  // 'new' type ('[' assignExpression ']' | arguments)?
  private static boolean newExpression_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "newExpression_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, NEW);
    r = r && type(b, l + 1);
    r = r && newExpression_0_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ('[' assignExpression ']' | arguments)?
  private static boolean newExpression_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "newExpression_0_2")) return false;
    newExpression_0_2_0(b, l + 1);
    return true;
  }

  // '[' assignExpression ']' | arguments
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
    r = consumeToken(b, LBRACKET);
    r = r && assignExpression(b, l + 1);
    r = r && consumeToken(b, RBRACKET);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // assignExpression
  //      | arrayInitializer
  //      | structInitializer
  //      | functionBody
  public static boolean nonVoidInitializer(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "nonVoidInitializer")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<non void initializer>");
    r = assignExpression(b, l + 1);
    if (!r) r = arrayInitializer(b, l + 1);
    if (!r) r = structInitializer(b, l + 1);
    if (!r) r = functionBody(b, l + 1);
    exit_section_(b, l, m, NON_VOID_INITIALIZER, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // asmExp+
  public static boolean operands(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "operands")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<operands>");
    r = asmExp(b, l + 1);
    int c = current_position_(b);
    while (r) {
      if (!asmExp(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "operands", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, l, m, OPERANDS, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // xorExpression
  //      | orExpression '|' xorExpression
  public static boolean orExpression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "orExpression")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<or expression>");
    r = xorExpression(b, l + 1);
    if (!r) r = orExpression_1(b, l + 1);
    exit_section_(b, l, m, OR_EXPRESSION, r, false, null);
    return r;
  }

  // orExpression '|' xorExpression
  private static boolean orExpression_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "orExpression_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = orExpression(b, l + 1);
    r = r && consumeToken(b, BITOR);
    r = r && xorExpression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // andAndExpression
  //      | orOrExpression '||' andAndExpression
  public static boolean orOrExpression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "orOrExpression")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<or or expression>");
    r = andAndExpression(b, l + 1);
    if (!r) r = orOrExpression_1(b, l + 1);
    exit_section_(b, l, m, OR_OR_EXPRESSION, r, false, null);
    return r;
  }

  // orOrExpression '||' andAndExpression
  private static boolean orOrExpression_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "orOrExpression_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = orOrExpression(b, l + 1);
    r = r && consumeToken(b, LOGICOR);
    r = r && andAndExpression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'out' ('(' Identifier ')')? blockStatement
  public static boolean outStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "outStatement")) return false;
    if (!nextTokenIs(b, OUT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OUT);
    r = r && outStatement_1(b, l + 1);
    r = r && blockStatement(b, l + 1);
    exit_section_(b, m, OUT_STATEMENT, r);
    return r;
  }

  // ('(' Identifier ')')?
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
    r = consumeToken(b, LPAREN);
    r = r && Identifier(b, l + 1);
    r = r && consumeToken(b, RPAREN);
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
    r = r && consumeToken(b, VARARG);
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
    r = consumeToken(b, ASSIGN);
    r = r && assignExpression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // typeConstructor
  //      | 'final'
  //      | 'in'
  //      | 'lazy'
  //      | 'out'
  //      | 'ref'
  //      | 'scope'
  //      | 'auto'
  public static boolean parameterAttribute(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parameterAttribute")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<parameter attribute>");
    r = typeConstructor(b, l + 1);
    if (!r) r = consumeToken(b, FINAL);
    if (!r) r = consumeToken(b, IN);
    if (!r) r = consumeToken(b, LAZY);
    if (!r) r = consumeToken(b, OUT);
    if (!r) r = consumeToken(b, REF);
    if (!r) r = consumeToken(b, SCOPE);
    if (!r) r = consumeToken(b, AUTO);
    exit_section_(b, l, m, PARAMETER_ATTRIBUTE, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // '(' parameter (',' parameter)* (',' '...')? ')'
  //      | '(' '...' ')'
  //      | '(' ')'
  public static boolean parameters(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parameters")) return false;
    if (!nextTokenIs(b, LPAREN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = parameters_0(b, l + 1);
    if (!r) r = parameters_1(b, l + 1);
    if (!r) r = parameters_2(b, l + 1);
    exit_section_(b, m, PARAMETERS, r);
    return r;
  }

  // '(' parameter (',' parameter)* (',' '...')? ')'
  private static boolean parameters_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parameters_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LPAREN);
    r = r && parameter(b, l + 1);
    r = r && parameters_0_2(b, l + 1);
    r = r && parameters_0_3(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    exit_section_(b, m, null, r);
    return r;
  }

  // (',' parameter)*
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
    r = consumeToken(b, COMMA);
    r = r && parameter(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (',' '...')?
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
    r = consumeToken(b, COMMA);
    r = r && consumeToken(b, VARARG);
    exit_section_(b, m, null, r);
    return r;
  }

  // '(' '...' ')'
  private static boolean parameters_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parameters_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LPAREN);
    r = r && consumeToken(b, VARARG);
    r = r && consumeToken(b, RPAREN);
    exit_section_(b, m, null, r);
    return r;
  }

  // '(' ')'
  private static boolean parameters_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parameters_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LPAREN);
    r = r && consumeToken(b, RPAREN);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // unaryExpression ('++' | '--')
  public static boolean postIncDecExpression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "postIncDecExpression")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<post inc dec expression>");
    r = unaryExpression(b, l + 1);
    r = r && postIncDecExpression_1(b, l + 1);
    exit_section_(b, l, m, POST_INC_DEC_EXPRESSION, r, false, null);
    return r;
  }

  // '++' | '--'
  private static boolean postIncDecExpression_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "postIncDecExpression_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, INCREMENT);
    if (!r) r = consumeToken(b, DECREMENT);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'this' '(' 'this' ')' memberFunctionAttribute* (functionBody | ';')
  public static boolean postblit(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "postblit")) return false;
    if (!nextTokenIs(b, THIS)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, THIS);
    r = r && consumeToken(b, LPAREN);
    r = r && consumeToken(b, THIS);
    r = r && consumeToken(b, RPAREN);
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
    if (!r) r = consumeToken(b, ";");
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // unaryExpression
  //      | powExpression '^^' unaryExpression
  public static boolean powExpression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "powExpression")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<pow expression>");
    r = unaryExpression(b, l + 1);
    if (!r) r = powExpression_1(b, l + 1);
    exit_section_(b, l, m, POW_EXPRESSION, r, false, null);
    return r;
  }

  // powExpression '^^' unaryExpression
  private static boolean powExpression_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "powExpression_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = powExpression(b, l + 1);
    r = r && consumeToken(b, POW);
    r = r && unaryExpression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // pragmaExpression ';'
  public static boolean pragmaDeclaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "pragmaDeclaration")) return false;
    if (!nextTokenIs(b, PRAGMA)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = pragmaExpression(b, l + 1);
    r = r && consumeToken(b, ";");
    exit_section_(b, m, PRAGMA_DECLARATION, r);
    return r;
  }

  /* ********************************************************** */
  // 'pragma' '(' Identifier (',' argumentList)? ')'
  public static boolean pragmaExpression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "pragmaExpression")) return false;
    if (!nextTokenIs(b, PRAGMA)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PRAGMA);
    r = r && consumeToken(b, LPAREN);
    r = r && Identifier(b, l + 1);
    r = r && pragmaExpression_3(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    exit_section_(b, m, PRAGMA_EXPRESSION, r);
    return r;
  }

  // (',' argumentList)?
  private static boolean pragmaExpression_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "pragmaExpression_3")) return false;
    pragmaExpression_3_0(b, l + 1);
    return true;
  }

  // ',' argumentList
  private static boolean pragmaExpression_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "pragmaExpression_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && argumentList(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // ('++' | '--') unaryExpression
  public static boolean preIncDecExpression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "preIncDecExpression")) return false;
    if (!nextTokenIs(b, "<pre inc dec expression>", INCREMENT, DECREMENT)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<pre inc dec expression>");
    r = preIncDecExpression_0(b, l + 1);
    r = r && unaryExpression(b, l + 1);
    exit_section_(b, l, m, PRE_INC_DEC_EXPRESSION, r, false, null);
    return r;
  }

  // '++' | '--'
  private static boolean preIncDecExpression_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "preIncDecExpression_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, INCREMENT);
    if (!r) r = consumeToken(b, DECREMENT);
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
  //      | IntegerLiteral
  //      | FloatLiteral
  //      | StringLiteral+
  //      | CharacterLiteral
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
    if (!r) r = consumeToken(b, DOLLAR);
    if (!r) r = consumeToken(b, THIS);
    if (!r) r = consumeToken(b, SUPER);
    if (!r) r = consumeToken(b, NULL);
    if (!r) r = consumeToken(b, TRUE);
    if (!r) r = consumeToken(b, FALSE);
    if (!r) r = consumeToken(b, SPECIALDATE);
    if (!r) r = consumeToken(b, SPECIALTIME);
    if (!r) r = consumeToken(b, SPECIALIMESTAMP);
    if (!r) r = consumeToken(b, SPECIALVENDOR);
    if (!r) r = consumeToken(b, SPECIALVERSION);
    if (!r) r = consumeToken(b, SPECIALFILE);
    if (!r) r = consumeToken(b, SPECIALLINE);
    if (!r) r = consumeToken(b, SPECIALMODULE);
    if (!r) r = consumeToken(b, SPECIALFUNCTION);
    if (!r) r = consumeToken(b, SPECIALPRETTYFUNCTION);
    if (!r) r = IntegerLiteral(b, l + 1);
    if (!r) r = FloatLiteral(b, l + 1);
    if (!r) r = primaryExpression_33(b, l + 1);
    if (!r) r = consumeToken(b, CHARACTERLITERAL);
    exit_section_(b, l, m, PRIMARY_EXPRESSION, r, false, null);
    return r;
  }

  // '.' identifierOrTemplateInstance
  private static boolean primaryExpression_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "primaryExpression_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, DOT);
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
    r = r && consumeToken(b, DOT);
    r = r && Identifier(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '(' expression ')'
  private static boolean primaryExpression_8(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "primaryExpression_8")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LPAREN);
    r = r && expression(b, l + 1);
    r = r && consumeToken(b, RPAREN);
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
  // Identifier
  //      | Identifier '(' IntegerLiteral ')'
  public static boolean register(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "register")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<register>");
    r = Identifier(b, l + 1);
    if (!r) r = register_1(b, l + 1);
    exit_section_(b, l, m, REGISTER, r, false, null);
    return r;
  }

  // Identifier '(' IntegerLiteral ')'
  private static boolean register_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "register_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Identifier(b, l + 1);
    r = r && consumeToken(b, LPAREN);
    r = r && IntegerLiteral(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // shiftExpression
  //      | relExpression relOperator shiftExpression
  public static boolean relExpression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "relExpression")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<rel expression>");
    r = shiftExpression(b, l + 1);
    if (!r) r = relExpression_1(b, l + 1);
    exit_section_(b, l, m, REL_EXPRESSION, r, false, null);
    return r;
  }

  // relExpression relOperator shiftExpression
  private static boolean relExpression_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "relExpression_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = relExpression(b, l + 1);
    r = r && relOperator(b, l + 1);
    r = r && shiftExpression(b, l + 1);
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
    r = consumeToken(b, LESS);
    if (!r) r = consumeToken(b, LESSEQUAL);
    if (!r) r = consumeToken(b, GREATER);
    if (!r) r = consumeToken(b, GREATEREQUAL);
    if (!r) r = consumeToken(b, UNORDERED);
    if (!r) r = consumeToken(b, NOTLESSEQUALGREATER);
    if (!r) r = consumeToken(b, LESSORGREATER);
    if (!r) r = consumeToken(b, LESSEQUALGREATER);
    if (!r) r = consumeToken(b, NOTGREATER);
    if (!r) r = consumeToken(b, NOTGREATEREQUAL);
    if (!r) r = consumeToken(b, NOTLESS);
    if (!r) r = consumeToken(b, NOTLESSEQUAL);
    exit_section_(b, l, m, REL_OPERATOR, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // 'return' expression? ';'
  public static boolean returnStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "returnStatement")) return false;
    if (!nextTokenIs(b, RETURN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, RETURN);
    r = r && returnStatement_1(b, l + 1);
    r = r && consumeToken(b, ";");
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
    if (!nextTokenIs(b, SCOPE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, SCOPE);
    r = r && consumeToken(b, LPAREN);
    r = r && Identifier(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    r = r && statementNoCaseNoDefault(b, l + 1);
    exit_section_(b, m, SCOPE_GUARD_STATEMENT, r);
    return r;
  }

  /* ********************************************************** */
  // 'shared' 'static' 'this' '(' ')' functionBody
  public static boolean sharedStaticConstructor(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "sharedStaticConstructor")) return false;
    if (!nextTokenIs(b, SHARED)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, SHARED);
    r = r && consumeToken(b, STATIC);
    r = r && consumeToken(b, THIS);
    r = r && consumeToken(b, LPAREN);
    r = r && consumeToken(b, RPAREN);
    r = r && functionBody(b, l + 1);
    exit_section_(b, m, SHARED_STATIC_CONSTRUCTOR, r);
    return r;
  }

  /* ********************************************************** */
  // 'shared' 'static' '~' 'this' '(' ')' functionBody
  public static boolean sharedStaticDestructor(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "sharedStaticDestructor")) return false;
    if (!nextTokenIs(b, SHARED)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, SHARED);
    r = r && consumeToken(b, STATIC);
    r = r && consumeToken(b, TILDE);
    r = r && consumeToken(b, THIS);
    r = r && consumeToken(b, LPAREN);
    r = r && consumeToken(b, RPAREN);
    r = r && functionBody(b, l + 1);
    exit_section_(b, m, SHARED_STATIC_DESTRUCTOR, r);
    return r;
  }

  /* ********************************************************** */
  // addExpression
  //      | shiftExpression ('<<' | '>>' | '>>>') addExpression
  public static boolean shiftExpression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "shiftExpression")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<shift expression>");
    r = addExpression(b, l + 1);
    if (!r) r = shiftExpression_1(b, l + 1);
    exit_section_(b, l, m, SHIFT_EXPRESSION, r, false, null);
    return r;
  }

  // shiftExpression ('<<' | '>>' | '>>>') addExpression
  private static boolean shiftExpression_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "shiftExpression_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = shiftExpression(b, l + 1);
    r = r && shiftExpression_1_1(b, l + 1);
    r = r && addExpression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '<<' | '>>' | '>>>'
  private static boolean shiftExpression_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "shiftExpression_1_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, SHIFTLEFT);
    if (!r) r = consumeToken(b, SHIFTRIGHT);
    if (!r) r = consumeToken(b, UNSIGNEDSHIFTRIGHT);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // (Identifier '=')? identifierChain
  public static boolean singleImport(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "singleImport")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<single import>");
    r = singleImport_0(b, l + 1);
    r = r && identifierChain(b, l + 1);
    exit_section_(b, l, m, SINGLE_IMPORT, r, false, null);
    return r;
  }

  // (Identifier '=')?
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
    r = r && consumeToken(b, ASSIGN);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // unaryExpression '[' assignExpression '..' assignExpression ']'
  //      | unaryExpression '[' ']'
  public static boolean sliceExpression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "sliceExpression")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<slice expression>");
    r = sliceExpression_0(b, l + 1);
    if (!r) r = sliceExpression_1(b, l + 1);
    exit_section_(b, l, m, SLICE_EXPRESSION, r, false, null);
    return r;
  }

  // unaryExpression '[' assignExpression '..' assignExpression ']'
  private static boolean sliceExpression_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "sliceExpression_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = unaryExpression(b, l + 1);
    r = r && consumeToken(b, LBRACKET);
    r = r && assignExpression(b, l + 1);
    r = r && consumeToken(b, SLICE);
    r = r && assignExpression(b, l + 1);
    r = r && consumeToken(b, RBRACKET);
    exit_section_(b, m, null, r);
    return r;
  }

  // unaryExpression '[' ']'
  private static boolean sliceExpression_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "sliceExpression_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = unaryExpression(b, l + 1);
    r = r && consumeToken(b, LBRACKET);
    r = r && consumeToken(b, RBRACKET);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // statementNoCaseNoDefault
  //      | caseStatement
  //      | caseRangeStatement
  //      | defaultStatement
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
  //      | blockStatement
  //      | ifStatement
  //      | whileStatement
  //      | doStatement
  //      | forStatement
  //      | foreachStatement
  //      | switchStatement
  //      | finalSwitchStatement
  //      | continueStatement
  //      | breakStatement
  //      | returnStatement
  //      | gotoStatement
  //      | withStatement
  //      | synchronizedStatement
  //      | tryStatement
  //      | throwStatement
  //      | scopeGuardStatement
  //      | asmStatement
  //      | conditionalStatement
  //      | staticAssertStatement
  //      | versionSpecification
  //      | debugSpecification
  //      | expressionStatement
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
    exit_section_(b, l, m, STATEMENT_NO_CASE_NO_DEFAULT, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // staticAssertStatement
  public static boolean staticAssertDeclaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "staticAssertDeclaration")) return false;
    if (!nextTokenIs(b, STATIC)) return false;
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
    if (!nextTokenIs(b, STATIC)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, STATIC);
    r = r && assertExpression(b, l + 1);
    r = r && consumeToken(b, ";");
    exit_section_(b, m, STATIC_ASSERT_STATEMENT, r);
    return r;
  }

  /* ********************************************************** */
  // 'static' 'this' '(' ')' functionBody
  public static boolean staticConstructor(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "staticConstructor")) return false;
    if (!nextTokenIs(b, STATIC)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, STATIC);
    r = r && consumeToken(b, THIS);
    r = r && consumeToken(b, LPAREN);
    r = r && consumeToken(b, RPAREN);
    r = r && functionBody(b, l + 1);
    exit_section_(b, m, STATIC_CONSTRUCTOR, r);
    return r;
  }

  /* ********************************************************** */
  // 'static' '~' 'this' '(' ')' functionBody
  public static boolean staticDestructor(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "staticDestructor")) return false;
    if (!nextTokenIs(b, STATIC)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, STATIC);
    r = r && consumeToken(b, TILDE);
    r = r && consumeToken(b, THIS);
    r = r && consumeToken(b, LPAREN);
    r = r && consumeToken(b, RPAREN);
    r = r && functionBody(b, l + 1);
    exit_section_(b, m, STATIC_DESTRUCTOR, r);
    return r;
  }

  /* ********************************************************** */
  // 'static' 'if' '(' assignExpression ')'
  public static boolean staticIfCondition(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "staticIfCondition")) return false;
    if (!nextTokenIs(b, STATIC)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, STATIC);
    r = r && consumeToken(b, IF);
    r = r && consumeToken(b, LPAREN);
    r = r && assignExpression(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    exit_section_(b, m, STATIC_IF_CONDITION, r);
    return r;
  }

  /* ********************************************************** */
  // atAttribute
  //      | typeConstructor
  //      | deprecated
  //      | 'abstract'
  //      | 'auto'
  //      | 'enum'
  //      | 'extern'
  //      | 'final'
  //      | 'nothrow'
  //      | 'override'
  //      | 'pure'
  //      | 'ref'
  //      | '__gshared'
  //      | 'scope'
  //      | 'static'
  //      | 'synchronized'
  public static boolean storageClass(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "storageClass")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<storage class>");
    r = atAttribute(b, l + 1);
    if (!r) r = typeConstructor(b, l + 1);
    if (!r) r = deprecated(b, l + 1);
    if (!r) r = consumeToken(b, ABSTRACT);
    if (!r) r = consumeToken(b, AUTO);
    if (!r) r = consumeToken(b, ENUM);
    if (!r) r = consumeToken(b, EXTERN);
    if (!r) r = consumeToken(b, FINAL);
    if (!r) r = consumeToken(b, NOTHROW);
    if (!r) r = consumeToken(b, OVERRIDE);
    if (!r) r = consumeToken(b, PURE);
    if (!r) r = consumeToken(b, REF);
    if (!r) r = consumeToken(b, GSHARED);
    if (!r) r = consumeToken(b, SCOPE);
    if (!r) r = consumeToken(b, STATIC);
    if (!r) r = consumeToken(b, SYNCHRONIZED);
    exit_section_(b, l, m, STORAGE_CLASS, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // '{' declaration* '}'
  public static boolean structBody(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "structBody")) return false;
    if (!nextTokenIs(b, LBRACE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LBRACE);
    r = r && structBody_1(b, l + 1);
    r = r && consumeToken(b, RBRACE);
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
  // 'struct' Identifier? (templateParameters constraint? structBody | (structBody | ';'))
  public static boolean structDeclaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "structDeclaration")) return false;
    if (!nextTokenIs(b, STRUCT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, STRUCT);
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

  // templateParameters constraint? structBody | (structBody | ';')
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
    if (!r) r = consumeToken(b, ";");
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // '{' structMemberInitializers? '}'
  public static boolean structInitializer(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "structInitializer")) return false;
    if (!nextTokenIs(b, LBRACE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LBRACE);
    r = r && structInitializer_1(b, l + 1);
    r = r && consumeToken(b, RBRACE);
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
  // (Identifier ':')? nonVoidInitializer
  public static boolean structMemberInitializer(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "structMemberInitializer")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<struct member initializer>");
    r = structMemberInitializer_0(b, l + 1);
    r = r && nonVoidInitializer(b, l + 1);
    exit_section_(b, l, m, STRUCT_MEMBER_INITIALIZER, r, false, null);
    return r;
  }

  // (Identifier ':')?
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
    r = r && consumeToken(b, ":");
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // structMemberInitializer (',' structMemberInitializer?)*
  public static boolean structMemberInitializers(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "structMemberInitializers")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<struct member initializers>");
    r = structMemberInitializer(b, l + 1);
    r = r && structMemberInitializers_1(b, l + 1);
    exit_section_(b, l, m, STRUCT_MEMBER_INITIALIZERS, r, false, null);
    return r;
  }

  // (',' structMemberInitializer?)*
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
    r = consumeToken(b, COMMA);
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
    if (!nextTokenIs(b, SWITCH)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, SWITCH);
    r = r && consumeToken(b, LPAREN);
    r = r && expression(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    r = r && statement(b, l + 1);
    exit_section_(b, m, SWITCH_STATEMENT, r);
    return r;
  }

  /* ********************************************************** */
  // '.'? identifierOrTemplateChain
  public static boolean symbol(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "symbol")) return false;
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
    consumeToken(b, DOT);
    return true;
  }

  /* ********************************************************** */
  // 'synchronized' ('(' expression ')')? statementNoCaseNoDefault
  public static boolean synchronizedStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "synchronizedStatement")) return false;
    if (!nextTokenIs(b, SYNCHRONIZED)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, SYNCHRONIZED);
    r = r && synchronizedStatement_1(b, l + 1);
    r = r && statementNoCaseNoDefault(b, l + 1);
    exit_section_(b, m, SYNCHRONIZED_STATEMENT, r);
    return r;
  }

  // ('(' expression ')')?
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
    r = consumeToken(b, LPAREN);
    r = r && expression(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'alias' type? Identifier (' ::=' (type | assignExpression))? ('=' (type | assignExpression))?
  public static boolean templateAliasParameter(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "templateAliasParameter")) return false;
    if (!nextTokenIs(b, ALIAS)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ALIAS);
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

  // (' ::=' (type | assignExpression))?
  private static boolean templateAliasParameter_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "templateAliasParameter_3")) return false;
    templateAliasParameter_3_0(b, l + 1);
    return true;
  }

  // ' ::=' (type | assignExpression)
  private static boolean templateAliasParameter_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "templateAliasParameter_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, " ::=");
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

  // ('=' (type | assignExpression))?
  private static boolean templateAliasParameter_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "templateAliasParameter_4")) return false;
    templateAliasParameter_4_0(b, l + 1);
    return true;
  }

  // '=' (type | assignExpression)
  private static boolean templateAliasParameter_4_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "templateAliasParameter_4_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ASSIGN);
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
  // type
  //      | assignExpression
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
  // templateArgument (',' templateArgument?)*
  public static boolean templateArgumentList(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "templateArgumentList")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<template argument list>");
    r = templateArgument(b, l + 1);
    r = r && templateArgumentList_1(b, l + 1);
    exit_section_(b, l, m, TEMPLATE_ARGUMENT_LIST, r, false, null);
    return r;
  }

  // (',' templateArgument?)*
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
    r = consumeToken(b, COMMA);
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
  // '!' ('(' templateArgumentList? ')' | templateSingleArgument)
  public static boolean templateArguments(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "templateArguments")) return false;
    if (!nextTokenIs(b, NOT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, NOT);
    r = r && templateArguments_1(b, l + 1);
    exit_section_(b, m, TEMPLATE_ARGUMENTS, r);
    return r;
  }

  // '(' templateArgumentList? ')' | templateSingleArgument
  private static boolean templateArguments_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "templateArguments_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = templateArguments_1_0(b, l + 1);
    if (!r) r = templateSingleArgument(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '(' templateArgumentList? ')'
  private static boolean templateArguments_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "templateArguments_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LPAREN);
    r = r && templateArguments_1_0_1(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    exit_section_(b, m, null, r);
    return r;
  }

  // templateArgumentList?
  private static boolean templateArguments_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "templateArguments_1_0_1")) return false;
    templateArgumentList(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // 'template' Identifier templateParameters constraint? '{' declaration* '}'
  //      | eponymousTemplateDeclaration
  public static boolean templateDeclaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "templateDeclaration")) return false;
    if (!nextTokenIs(b, "<template declaration>", ENUM, TEMPLATE)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<template declaration>");
    r = templateDeclaration_0(b, l + 1);
    if (!r) r = eponymousTemplateDeclaration(b, l + 1);
    exit_section_(b, l, m, TEMPLATE_DECLARATION, r, false, null);
    return r;
  }

  // 'template' Identifier templateParameters constraint? '{' declaration* '}'
  private static boolean templateDeclaration_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "templateDeclaration_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, TEMPLATE);
    r = r && Identifier(b, l + 1);
    r = r && templateParameters(b, l + 1);
    r = r && templateDeclaration_0_3(b, l + 1);
    r = r && consumeToken(b, LBRACE);
    r = r && templateDeclaration_0_5(b, l + 1);
    r = r && consumeToken(b, RBRACE);
    exit_section_(b, m, null, r);
    return r;
  }

  // constraint?
  private static boolean templateDeclaration_0_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "templateDeclaration_0_3")) return false;
    constraint(b, l + 1);
    return true;
  }

  // declaration*
  private static boolean templateDeclaration_0_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "templateDeclaration_0_5")) return false;
    int c = current_position_(b);
    while (true) {
      if (!declaration(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "templateDeclaration_0_5", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  /* ********************************************************** */
  // Identifier templateArguments
  public static boolean templateInstance(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "templateInstance")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<template instance>");
    r = Identifier(b, l + 1);
    r = r && templateArguments(b, l + 1);
    exit_section_(b, l, m, TEMPLATE_INSTANCE, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // 'mixin' mixinTemplateName templateArguments? Identifier?
  public static boolean templateMixinExpression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "templateMixinExpression")) return false;
    if (!nextTokenIs(b, MIXIN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, MIXIN);
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
  // templateTypeParameter
  //      | templateValueParameter
  //      | templateAliasParameter
  //      | templateTupleParameter
  //      | templateThisParameter
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
  // templateParameter (',' templateParameter?)*
  public static boolean templateParameterList(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "templateParameterList")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<template parameter list>");
    r = templateParameter(b, l + 1);
    r = r && templateParameterList_1(b, l + 1);
    exit_section_(b, l, m, TEMPLATE_PARAMETER_LIST, r, false, null);
    return r;
  }

  // (',' templateParameter?)*
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
    r = consumeToken(b, COMMA);
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
    if (!nextTokenIs(b, LPAREN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LPAREN);
    r = r && templateParameters_1(b, l + 1);
    r = r && consumeToken(b, RPAREN);
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
  // builtinType
  //      | Identifier
  //      | CharacterLiteral
  //      | StringLiteral
  //      | IntegerLiteral
  //      | FloatLiteral
  //      | 'true'
  //      | 'false'
  //      | 'null'
  //      | 'this'
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
  public static boolean templateSingleArgument(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "templateSingleArgument")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<template single argument>");
    r = builtinType(b, l + 1);
    if (!r) r = Identifier(b, l + 1);
    if (!r) r = consumeToken(b, CHARACTERLITERAL);
    if (!r) r = StringLiteral(b, l + 1);
    if (!r) r = IntegerLiteral(b, l + 1);
    if (!r) r = FloatLiteral(b, l + 1);
    if (!r) r = consumeToken(b, TRUE);
    if (!r) r = consumeToken(b, FALSE);
    if (!r) r = consumeToken(b, NULL);
    if (!r) r = consumeToken(b, THIS);
    if (!r) r = consumeToken(b, SPECIALDATE);
    if (!r) r = consumeToken(b, SPECIALTIME);
    if (!r) r = consumeToken(b, SPECIALIMESTAMP);
    if (!r) r = consumeToken(b, SPECIALVENDOR);
    if (!r) r = consumeToken(b, SPECIALVERSION);
    if (!r) r = consumeToken(b, SPECIALFILE);
    if (!r) r = consumeToken(b, SPECIALLINE);
    if (!r) r = consumeToken(b, SPECIALMODULE);
    if (!r) r = consumeToken(b, SPECIALFUNCTION);
    if (!r) r = consumeToken(b, SPECIALPRETTYFUNCTION);
    exit_section_(b, l, m, TEMPLATE_SINGLE_ARGUMENT, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // 'this' templateTypeParameter
  public static boolean templateThisParameter(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "templateThisParameter")) return false;
    if (!nextTokenIs(b, THIS)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, THIS);
    r = r && templateTypeParameter(b, l + 1);
    exit_section_(b, m, TEMPLATE_THIS_PARAMETER, r);
    return r;
  }

  /* ********************************************************** */
  // Identifier '...'
  public static boolean templateTupleParameter(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "templateTupleParameter")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<template tuple parameter>");
    r = Identifier(b, l + 1);
    r = r && consumeToken(b, VARARG);
    exit_section_(b, l, m, TEMPLATE_TUPLE_PARAMETER, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // Identifier (':' type)? ('=' type)?
  public static boolean templateTypeParameter(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "templateTypeParameter")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<template type parameter>");
    r = Identifier(b, l + 1);
    r = r && templateTypeParameter_1(b, l + 1);
    r = r && templateTypeParameter_2(b, l + 1);
    exit_section_(b, l, m, TEMPLATE_TYPE_PARAMETER, r, false, null);
    return r;
  }

  // (':' type)?
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
    r = consumeToken(b, ":");
    r = r && type(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ('=' type)?
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
    r = consumeToken(b, ASSIGN);
    r = r && type(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // type Identifier (':' expression)? templateValueParameterDefault?
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

  // (':' expression)?
  private static boolean templateValueParameter_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "templateValueParameter_2")) return false;
    templateValueParameter_2_0(b, l + 1);
    return true;
  }

  // ':' expression
  private static boolean templateValueParameter_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "templateValueParameter_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ":");
    r = r && expression(b, l + 1);
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
  // '=' ('__FILE__' | '__MODULE__' | '__LINE__' | '__FUNCTION__' | '__PRETTY_FUNCTION__' | assignExpression)
  public static boolean templateValueParameterDefault(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "templateValueParameterDefault")) return false;
    if (!nextTokenIs(b, ASSIGN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ASSIGN);
    r = r && templateValueParameterDefault_1(b, l + 1);
    exit_section_(b, m, TEMPLATE_VALUE_PARAMETER_DEFAULT, r);
    return r;
  }

  // '__FILE__' | '__MODULE__' | '__LINE__' | '__FUNCTION__' | '__PRETTY_FUNCTION__' | assignExpression
  private static boolean templateValueParameterDefault_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "templateValueParameterDefault_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, SPECIALFILE);
    if (!r) r = consumeToken(b, SPECIALMODULE);
    if (!r) r = consumeToken(b, SPECIALLINE);
    if (!r) r = consumeToken(b, SPECIALFUNCTION);
    if (!r) r = consumeToken(b, SPECIALPRETTYFUNCTION);
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
    r = consumeToken(b, TERNARY);
    r = r && expression(b, l + 1);
    r = r && consumeToken(b, ":");
    r = r && ternaryExpression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'throw' expression ';'
  public static boolean throwStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "throwStatement")) return false;
    if (!nextTokenIs(b, THROW)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, THROW);
    r = r && expression(b, l + 1);
    r = r && consumeToken(b, ";");
    exit_section_(b, m, THROW_STATEMENT, r);
    return r;
  }

  /* ********************************************************** */
  // '__traits' '(' Identifier ',' TemplateArgumentList ')'
  public static boolean traitsExpression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "traitsExpression")) return false;
    if (!nextTokenIs(b, TRAITS)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, TRAITS);
    r = r && consumeToken(b, LPAREN);
    r = r && Identifier(b, l + 1);
    r = r && consumeToken(b, COMMA);
    r = r && consumeToken(b, TEMPLATEARGUMENTLIST);
    r = r && consumeToken(b, RPAREN);
    exit_section_(b, m, TRAITS_EXPRESSION, r);
    return r;
  }

  /* ********************************************************** */
  // 'try' declarationOrStatement ((catches finally_?) | finally_)
  public static boolean tryStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tryStatement")) return false;
    if (!nextTokenIs(b, TRY)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, TRY);
    r = r && declarationOrStatement(b, l + 1);
    r = r && tryStatement_2(b, l + 1);
    exit_section_(b, m, TRY_STATEMENT, r);
    return r;
  }

  // (catches finally_?) | finally_
  private static boolean tryStatement_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tryStatement_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = tryStatement_2_0(b, l + 1);
    if (!r) r = finally_(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // catches finally_?
  private static boolean tryStatement_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tryStatement_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = catches(b, l + 1);
    r = r && tryStatement_2_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // finally_?
  private static boolean tryStatement_2_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tryStatement_2_0_1")) return false;
    finally_(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // attribute? type2 typeSuffix*
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

  // attribute?
  private static boolean type_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_0")) return false;
    attribute(b, l + 1);
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
  // builtinType
  //      | symbol
  //      | typeofExpression ('.' identifierOrTemplateChain)?
  //      | typeConstructor '(' type ')'
  public static boolean type2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type2")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<type 2>");
    r = builtinType(b, l + 1);
    if (!r) r = symbol(b, l + 1);
    if (!r) r = type2_2(b, l + 1);
    if (!r) r = type2_3(b, l + 1);
    exit_section_(b, l, m, TYPE_2, r, false, null);
    return r;
  }

  // typeofExpression ('.' identifierOrTemplateChain)?
  private static boolean type2_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type2_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = typeofExpression(b, l + 1);
    r = r && type2_2_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ('.' identifierOrTemplateChain)?
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
    r = consumeToken(b, DOT);
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
    r = r && consumeToken(b, LPAREN);
    r = r && type(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'const'
  //      | 'immutable'
  //      | 'inout'
  //      | 'shared'
  //      | 'scope'
  public static boolean typeConstructor(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "typeConstructor")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<type constructor>");
    r = consumeToken(b, CONST);
    if (!r) r = consumeToken(b, IMMUTABLE);
    if (!r) r = consumeToken(b, INOUT);
    if (!r) r = consumeToken(b, SHARED);
    if (!r) r = consumeToken(b, SCOPE);
    exit_section_(b, l, m, TYPE_CONSTRUCTOR, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // typeConstructor+
  public static boolean typeConstructors(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "typeConstructors")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<type constructors>");
    r = typeConstructor(b, l + 1);
    int c = current_position_(b);
    while (r) {
      if (!typeConstructor(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "typeConstructors", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, l, m, TYPE_CONSTRUCTORS, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // type
  //      | 'struct'
  //      | 'union'
  //      | 'class'
  //      | 'interface'
  //      | 'enum'
  //      | 'function'
  //      | 'delegate'
  //      | 'super'
  //      | 'const'
  //      | 'immutable'
  //      | 'inout'
  //      | 'shared'
  //      | 'return'
  //      | 'typedef'
  //      | '__parameters'
  public static boolean typeSpecialization(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "typeSpecialization")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<type specialization>");
    r = type(b, l + 1);
    if (!r) r = consumeToken(b, STRUCT);
    if (!r) r = consumeToken(b, UNION);
    if (!r) r = consumeToken(b, CLASS);
    if (!r) r = consumeToken(b, INTERFACE);
    if (!r) r = consumeToken(b, ENUM);
    if (!r) r = consumeToken(b, FUNCTION);
    if (!r) r = consumeToken(b, DELEGATE);
    if (!r) r = consumeToken(b, SUPER);
    if (!r) r = consumeToken(b, CONST);
    if (!r) r = consumeToken(b, IMMUTABLE);
    if (!r) r = consumeToken(b, INOUT);
    if (!r) r = consumeToken(b, SHARED);
    if (!r) r = consumeToken(b, RETURN);
    if (!r) r = consumeToken(b, TYPEDEF);
    if (!r) r = consumeToken(b, TPARAMETERS);
    exit_section_(b, l, m, TYPE_SPECIALIZATION, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // '*'
  //      | '[' type? ']'
  //      | '[' assignExpression ']'
  //      | '[' assignExpression '..'  assignExpression ']'
  //      | ('delegate' | 'function') parameters memberFunctionAttribute*
  public static boolean typeSuffix(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "typeSuffix")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<type suffix>");
    r = consumeToken(b, STAR);
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
    r = consumeToken(b, LBRACKET);
    r = r && typeSuffix_1_1(b, l + 1);
    r = r && consumeToken(b, RBRACKET);
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
    r = consumeToken(b, LBRACKET);
    r = r && assignExpression(b, l + 1);
    r = r && consumeToken(b, RBRACKET);
    exit_section_(b, m, null, r);
    return r;
  }

  // '[' assignExpression '..'  assignExpression ']'
  private static boolean typeSuffix_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "typeSuffix_3")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LBRACKET);
    r = r && assignExpression(b, l + 1);
    r = r && consumeToken(b, SLICE);
    r = r && assignExpression(b, l + 1);
    r = r && consumeToken(b, RBRACKET);
    exit_section_(b, m, null, r);
    return r;
  }

  // ('delegate' | 'function') parameters memberFunctionAttribute*
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
    r = consumeToken(b, DELEGATE);
    if (!r) r = consumeToken(b, FUNCTION);
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
  // 'typeid' '(' (type | expression) ')'
  public static boolean typeidExpression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "typeidExpression")) return false;
    if (!nextTokenIs(b, TYPEID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, TYPEID);
    r = r && consumeToken(b, LPAREN);
    r = r && typeidExpression_2(b, l + 1);
    r = r && consumeToken(b, RPAREN);
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
  // 'typeof' '(' (expression | 'return') ')'
  public static boolean typeofExpression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "typeofExpression")) return false;
    if (!nextTokenIs(b, TYPEOF)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, TYPEOF);
    r = r && consumeToken(b, LPAREN);
    r = r && typeofExpression_2(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    exit_section_(b, m, TYPEOF_EXPRESSION, r);
    return r;
  }

  // expression | 'return'
  private static boolean typeofExpression_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "typeofExpression_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = expression(b, l + 1);
    if (!r) r = consumeToken(b, RETURN);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // primaryExpression
  //     | '&' unaryExpression
  //     | '!' unaryExpression
  //     | '*' unaryExpression
  //     | '+' unaryExpression
  //     | '-' unaryExpression
  //     | '~' unaryExpression
  //     | '++' unaryExpression
  //     | '--' unaryExpression
  //     | unaryExpression templateArguments? arguments
  //     | unaryExpression '[' assignExpression '..' assignExpression ']'
  //     | unaryExpression '[' ']'
  //     | unaryExpression '[' argumentList ']'
  //     | newExpression
  //     | deleteExpression
  //     | castExpression
  //     | assertExpression
  //     | '(' type ')' '.' identifierOrTemplateInstance
  //     | unaryExpression '.' identifierOrTemplateInstance
  //     | unaryExpression '--'
  //     | unaryExpression '++'
  public static boolean unaryExpression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unaryExpression")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<unary expression>");
    r = primaryExpression(b, l + 1);
    if (!r) r = unaryExpression_1(b, l + 1);
    if (!r) r = unaryExpression_2(b, l + 1);
    if (!r) r = unaryExpression_3(b, l + 1);
    if (!r) r = unaryExpression_4(b, l + 1);
    if (!r) r = unaryExpression_5(b, l + 1);
    if (!r) r = unaryExpression_6(b, l + 1);
    if (!r) r = unaryExpression_7(b, l + 1);
    if (!r) r = unaryExpression_8(b, l + 1);
    if (!r) r = unaryExpression_9(b, l + 1);
    if (!r) r = unaryExpression_10(b, l + 1);
    if (!r) r = unaryExpression_11(b, l + 1);
    if (!r) r = unaryExpression_12(b, l + 1);
    if (!r) r = newExpression(b, l + 1);
    if (!r) r = deleteExpression(b, l + 1);
    if (!r) r = castExpression(b, l + 1);
    if (!r) r = assertExpression(b, l + 1);
    if (!r) r = unaryExpression_17(b, l + 1);
    if (!r) r = unaryExpression_18(b, l + 1);
    if (!r) r = unaryExpression_19(b, l + 1);
    if (!r) r = unaryExpression_20(b, l + 1);
    exit_section_(b, l, m, UNARY_EXPRESSION, r, false, null);
    return r;
  }

  // '&' unaryExpression
  private static boolean unaryExpression_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unaryExpression_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, BITAND);
    r = r && unaryExpression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '!' unaryExpression
  private static boolean unaryExpression_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unaryExpression_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, NOT);
    r = r && unaryExpression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '*' unaryExpression
  private static boolean unaryExpression_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unaryExpression_3")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, STAR);
    r = r && unaryExpression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '+' unaryExpression
  private static boolean unaryExpression_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unaryExpression_4")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PLUS);
    r = r && unaryExpression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '-' unaryExpression
  private static boolean unaryExpression_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unaryExpression_5")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, MINUS);
    r = r && unaryExpression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '~' unaryExpression
  private static boolean unaryExpression_6(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unaryExpression_6")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, TILDE);
    r = r && unaryExpression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '++' unaryExpression
  private static boolean unaryExpression_7(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unaryExpression_7")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, INCREMENT);
    r = r && unaryExpression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '--' unaryExpression
  private static boolean unaryExpression_8(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unaryExpression_8")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, DECREMENT);
    r = r && unaryExpression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // unaryExpression templateArguments? arguments
  private static boolean unaryExpression_9(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unaryExpression_9")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = unaryExpression(b, l + 1);
    r = r && unaryExpression_9_1(b, l + 1);
    r = r && arguments(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // templateArguments?
  private static boolean unaryExpression_9_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unaryExpression_9_1")) return false;
    templateArguments(b, l + 1);
    return true;
  }

  // unaryExpression '[' assignExpression '..' assignExpression ']'
  private static boolean unaryExpression_10(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unaryExpression_10")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = unaryExpression(b, l + 1);
    r = r && consumeToken(b, LBRACKET);
    r = r && assignExpression(b, l + 1);
    r = r && consumeToken(b, SLICE);
    r = r && assignExpression(b, l + 1);
    r = r && consumeToken(b, RBRACKET);
    exit_section_(b, m, null, r);
    return r;
  }

  // unaryExpression '[' ']'
  private static boolean unaryExpression_11(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unaryExpression_11")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = unaryExpression(b, l + 1);
    r = r && consumeToken(b, LBRACKET);
    r = r && consumeToken(b, RBRACKET);
    exit_section_(b, m, null, r);
    return r;
  }

  // unaryExpression '[' argumentList ']'
  private static boolean unaryExpression_12(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unaryExpression_12")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = unaryExpression(b, l + 1);
    r = r && consumeToken(b, LBRACKET);
    r = r && argumentList(b, l + 1);
    r = r && consumeToken(b, RBRACKET);
    exit_section_(b, m, null, r);
    return r;
  }

  // '(' type ')' '.' identifierOrTemplateInstance
  private static boolean unaryExpression_17(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unaryExpression_17")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LPAREN);
    r = r && type(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    r = r && consumeToken(b, DOT);
    r = r && identifierOrTemplateInstance(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // unaryExpression '.' identifierOrTemplateInstance
  private static boolean unaryExpression_18(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unaryExpression_18")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = unaryExpression(b, l + 1);
    r = r && consumeToken(b, DOT);
    r = r && identifierOrTemplateInstance(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // unaryExpression '--'
  private static boolean unaryExpression_19(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unaryExpression_19")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = unaryExpression(b, l + 1);
    r = r && consumeToken(b, DECREMENT);
    exit_section_(b, m, null, r);
    return r;
  }

  // unaryExpression '++'
  private static boolean unaryExpression_20(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unaryExpression_20")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = unaryExpression(b, l + 1);
    r = r && consumeToken(b, INCREMENT);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'union' Identifier templateParameters constraint? structBody
  //      | 'union' Identifier (structBody | ';')
  //      | 'union' structBody
  public static boolean unionDeclaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unionDeclaration")) return false;
    if (!nextTokenIs(b, UNION)) return false;
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
    r = consumeToken(b, UNION);
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

  // 'union' Identifier (structBody | ';')
  private static boolean unionDeclaration_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unionDeclaration_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, UNION);
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
    if (!r) r = consumeToken(b, ";");
    exit_section_(b, m, null, r);
    return r;
  }

  // 'union' structBody
  private static boolean unionDeclaration_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unionDeclaration_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, UNION);
    r = r && structBody(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'unittest' blockStatement
  public static boolean unittest(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unittest")) return false;
    if (!nextTokenIs(b, TUNITTEST)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, TUNITTEST);
    r = r && blockStatement(b, l + 1);
    exit_section_(b, m, UNITTEST, r);
    return r;
  }

  /* ********************************************************** */
  // type declarator (',' declarator)* ';'
  //      | type declarator '=' functionBody
  //      | autoDeclaration
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

  // type declarator (',' declarator)* ';'
  private static boolean variableDeclaration_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "variableDeclaration_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = type(b, l + 1);
    r = r && declarator(b, l + 1);
    r = r && variableDeclaration_0_2(b, l + 1);
    r = r && consumeToken(b, ";");
    exit_section_(b, m, null, r);
    return r;
  }

  // (',' declarator)*
  private static boolean variableDeclaration_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "variableDeclaration_0_2")) return false;
    int c = current_position_(b);
    while (true) {
      if (!variableDeclaration_0_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "variableDeclaration_0_2", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // ',' declarator
  private static boolean variableDeclaration_0_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "variableDeclaration_0_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && declarator(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // type declarator '=' functionBody
  private static boolean variableDeclaration_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "variableDeclaration_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = type(b, l + 1);
    r = r && declarator(b, l + 1);
    r = r && consumeToken(b, ASSIGN);
    r = r && functionBody(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // '__vector' '(' type ')'
  public static boolean vector(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "vector")) return false;
    if (!nextTokenIs(b, TVECTOR)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, TVECTOR);
    r = r && consumeToken(b, LPAREN);
    r = r && type(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    exit_section_(b, m, VECTOR, r);
    return r;
  }

  /* ********************************************************** */
  // 'version' '(' (IntegerLiteral | Identifier | 'unittest' | 'assert') ')'
  public static boolean versionCondition(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "versionCondition")) return false;
    if (!nextTokenIs(b, VERSION)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, VERSION);
    r = r && consumeToken(b, LPAREN);
    r = r && versionCondition_2(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    exit_section_(b, m, VERSION_CONDITION, r);
    return r;
  }

  // IntegerLiteral | Identifier | 'unittest' | 'assert'
  private static boolean versionCondition_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "versionCondition_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = IntegerLiteral(b, l + 1);
    if (!r) r = Identifier(b, l + 1);
    if (!r) r = consumeToken(b, TUNITTEST);
    if (!r) r = consumeToken(b, ASSERT);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'version' '=' (Identifier | IntegerLiteral) ';'
  public static boolean versionSpecification(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "versionSpecification")) return false;
    if (!nextTokenIs(b, VERSION)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, VERSION);
    r = r && consumeToken(b, ASSIGN);
    r = r && versionSpecification_2(b, l + 1);
    r = r && consumeToken(b, ";");
    exit_section_(b, m, VERSION_SPECIFICATION, r);
    return r;
  }

  // Identifier | IntegerLiteral
  private static boolean versionSpecification_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "versionSpecification_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Identifier(b, l + 1);
    if (!r) r = IntegerLiteral(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'while' '(' expression ')' declarationOrStatement
  public static boolean whileStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "whileStatement")) return false;
    if (!nextTokenIs(b, WHILE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, WHILE);
    r = r && consumeToken(b, LPAREN);
    r = r && expression(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    r = r && declarationOrStatement(b, l + 1);
    exit_section_(b, m, WHILE_STATEMENT, r);
    return r;
  }

  /* ********************************************************** */
  // 'with' '(' expression ')' statementNoCaseNoDefault
  public static boolean withStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "withStatement")) return false;
    if (!nextTokenIs(b, WITH)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, WITH);
    r = r && consumeToken(b, LPAREN);
    r = r && expression(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    r = r && statementNoCaseNoDefault(b, l + 1);
    exit_section_(b, m, WITH_STATEMENT, r);
    return r;
  }

  /* ********************************************************** */
  // andExpression
  //      | xorExpression '^' andExpression
  public static boolean xorExpression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "xorExpression")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<xor expression>");
    r = andExpression(b, l + 1);
    if (!r) r = xorExpression_1(b, l + 1);
    exit_section_(b, l, m, XOR_EXPRESSION, r, false, null);
    return r;
  }

  // xorExpression '^' andExpression
  private static boolean xorExpression_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "xorExpression_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = xorExpression(b, l + 1);
    r = r && consumeToken(b, XOR);
    r = r && andExpression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

}
