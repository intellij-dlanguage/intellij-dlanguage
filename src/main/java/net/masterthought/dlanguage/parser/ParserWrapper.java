package net.masterthought.dlanguage.parser;

import com.intellij.lang.ASTNode;
import com.intellij.lang.LightPsiParser;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

import static com.intellij.lang.parser.GeneratedParserUtilBase.*;
import static net.masterthought.dlanguage.psi.DLanguageTypes.*;

/**
 * Created by francis on 7/2/2017.
 */
public class ParserWrapper implements LightPsiParser, PsiParser {
    @Override
    public void parseLight(IElementType t, PsiBuilder b) {
        boolean r = false;
        b = adapt_builder_(t, b, this, null);
        PsiBuilder.Marker m = enter_section_(b, 0, _COLLAPSE_, null);
        if (t == ADD_EXPRESSION_) {
//            r = AddExpression_(b, 0);
        } else if (t == AGGREGATE_BODY) {
//            r = AggregateBody(b, 0);
        } else if (t == AGGREGATE_DECLARATION) {
//            r = AggregateDeclaration(b, 0);
        } else if (t == ALIAS_DECLARATION) {
//            r = AliasDeclaration(b, 0);
        } else if (t == ALIAS_DECLARATION_SINGLE) {
//            r = AliasDeclarationSingle(b, 0);
        } else if (t == ALIAS_DECLARATION_X) {
//            r = AliasDeclarationX(b, 0);
        } else if (t == ALIAS_DECLARATION_Y) {
//            r = AliasDeclarationY(b, 0);
        } else if (t == ALIAS_THIS) {
//            r = AliasThis(b, 0);
        } else if (t == ALIGN_ATTRIBUTE) {
//            r = AlignAttribute(b, 0);
        } else if (t == ALLOCATOR) {
//            r = Allocator(b, 0);
        } else if (t == ALLOCATOR_ARGUMENTS) {
//            r = AllocatorArguments(b, 0);
        } else if (t == ALT_DECLARATOR) {
//            r = AltDeclarator(b, 0);
        } else if (t == ALT_DECLARATOR_IDENTIFIER) {
//            r = AltDeclaratorIdentifier(b, 0);
        } else if (t == ALT_DECLARATOR_SUFFIX) {
//            r = AltDeclaratorSuffix(b, 0);
        } else if (t == ALT_DECLARATOR_SUFFIXES) {
//            r = AltDeclaratorSuffixes(b, 0);
        } else if (t == ALT_DECLARATOR_X) {
//            r = AltDeclaratorX(b, 0);
        } else if (t == ALT_FUNC_DECLARATOR_SUFFIX) {
//            r = AltFuncDeclaratorSuffix(b, 0);
        } else if (t == AND_EXPRESSION_) {
//            r = AndExxpression_(b, 0);
        } else if (t == ANONYMOUS_ENUM_DECLARATION) {
//            r = AnonymousEnumDeclaration(b, 0);
        } else if (t == ARGUMENT_LIST) {
//            r = ArgumentList(b, 0);
        } else if (t == ARRAY_INITIALIZER) {
//            r = ArrayInitializer(b, 0);
        } else if (t == ARRAY_LITERAL) {
//            r = ArrayLiteral(b, 0);
        } else if (t == ARRAY_MEMBER_INITIALIZATION) {
//            r = ArrayMemberInitialization(b, 0);
        } else if (t == ARRAY_MEMBER_INITIALIZATIONS) {
//            r = ArrayMemberInitializations(b, 0);
        } else if (t == ASM_ADD_EXP) {
//            r = AsmAddExp(b, 0);
        } else if (t == ASM_AND_EXP) {
//            r = AsmAndExp(b, 0);
        } else if (t == ASM_BR_EXP) {
//            r = AsmBrExp(b, 0);
        } else if (t == ASM_EQUAL_EXP) {
//            r = AsmEqualExp(b, 0);
        } else if (t == ASM_EXP) {
//            r = AsmExp(b, 0);
        } else if (t == ASM_INSTRUCTION) {
//            r = AsmInstruction(b, 0);
        } else if (t == ASM_INSTRUCTION_LIST) {
//            r = AsmInstructionList(b, 0);
        } else if (t == ASM_LOG_AND_EXP) {
//            r = AsmLogAndExp(b, 0);
        } else if (t == ASM_LOG_OR_EXP) {
//            r = AsmLogOrExp(b, 0);
        } else if (t == ASM_MUL_EXP) {
//            r = AsmMulExp(b, 0);
        } else if (t == ASM_OR_EXP) {
//            r = AsmOrExp(b, 0);
        } else if (t == ASM_PRIMARY_EXP) {
//            r = AsmPrimaryExp(b, 0);
        } else if (t == ASM_REL_EXP) {
//            r = AsmRelExp(b, 0);
        } else if (t == ASM_SHIFT_EXP) {
//            r = AsmShiftExp(b, 0);
        } else if (t == ASM_STATEMENT) {
//            r = AsmStatement(b, 0);
        } else if (t == ASM_TYPE_PREFIX) {
//            r = AsmTypePrefix(b, 0);
        } else if (t == ASM_UNA_EXP) {
//            r = AsmUnaExp(b, 0);
        } else if (t == ASM_XOR_EXP) {
//            r = AsmXorExp(b, 0);
        } else if (t == ASSERT_EXPRESSION) {
//            r = AssertExpression(b, 0);
        } else if (t == ASSIGN_EXPRESSION) {
//            r = AssignExpression(b, 0);
        } else if (t == ASSOC_ARRAY_LITERAL) {
//            r = AssocArrayLiteral(b, 0);
        } else if (t == ATTRIBUTE) {
//            r = Attribute(b, 0);
        } else if (t == ATTRIBUTE_SPECIFIER) {
//            r = AttributeSpecifier(b, 0);
        } else if (t == AUTO_DECLARATION) {
//            r = AutoDeclaration(b, 0);
        } else if (t == AUTO_DECLARATION_X) {
//            r = AutoDeclarationX(b, 0);
        } else if (t == AUTO_DECLARATION_Y) {
//            r = AutoDeclarationY(b, 0);
        } else if (t == BASE_CLASS_LIST) {
//            r = BaseClassList(b, 0);
        } else if (t == BASE_INTERFACE_LIST) {
//            r = BaseInterfaceList(b, 0);
        } else if (t == BASIC_TYPE) {
//            r = BasicType(b, 0);
        } else if (t == BASIC_TYPE_2) {
//            r = BasicType2(b, 0);
        } else if (t == BASIC_TYPE_2_X) {
//            r = BasicType2X(b, 0);
        } else if (t == BASIC_TYPE_X) {
//            r = BasicTypeX(b, 0);
        } else if (t == BLOCK_STATEMENT) {
//            r = BlockStatement(b, 0);
        } else if (t == BODY_STATEMENT) {
//            r = BodyStatement(b, 0);
        } else if (t == BREAK_STATEMENT) {
//            r = BreakStatement(b, 0);
        } else if (t == CASE_RANGE_STATEMENT) {
//            r = CaseRangeStatement(b, 0);
        } else if (t == CASE_STATEMENT) {
//            r = CaseStatement(b, 0);
        } else if (t == CAST_EXPRESSION) {
//            r = CastExpression(b, 0);
        } else if (t == CATCH) {
//            r = Catch(b, 0);
        } else if (t == CATCH_PARAMETER) {
//            r = CatchParameter(b, 0);
        } else if (t == CATCHES) {
//            r = Catches(b, 0);
        } else if (t == CLASS_ARGUMENTS) {
//            r = ClassArguments(b, 0);
        } else if (t == CLASS_DECLARATION) {
//            r = ClassDeclaration(b, 0);
        } else if (t == COMMA_EXPRESSION) {
//            r = CommaExpression(b, 0);
        } else if (t == CONDITION) {
//            r = Condition(b, 0);
        } else if (t == CONDITION_VARIABLE_DECLARATION) {
//            r = ConditionVariableDeclaration(b, 0);
        } else if (t == CONDITIONAL_DECLARATION) {
//            r = ConditionalDeclaration(b, 0);
        } else if (t == CONDITIONAL_EXPRESSION_) {
//            r = ConditionalExpression_(b, 0);
        } else if (t == CONDITIONAL_STATEMENT) {
//            r = ConditionalStatement(b, 0);
        } else if (t == CONSTRAINT) {
//            r = Constraint(b, 0);
        } else if (t == CONSTRUCTOR) {
//            r = Constructor(b, 0);
        } else if (t == CONTINUE_STATEMENT) {
//            r = ContinueStatement(b, 0);
        } else if (t == DEALLOCATOR) {
//            r = Deallocator(b, 0);
        } else if (t == DEBUG_CONDITION) {
//            r = DebugCondition(b, 0);
        } else if (t == DEBUG_SPECIFICATION) {
//            r = DebugSpecification(b, 0);
        } else if (t == DECL_DEF) {
//            r = DeclDef(b, 0);
        } else if (t == DECL_DEFS) {
//            r = DeclDefs(b, 0);
        } else if (t == DECLARATION) {
//            r = Declaration(b, 0);
        } else if (t == DECLARATION_BLOCK) {
//            r = DeclarationBlock(b, 0);
        } else if (t == DECLARATION_STATEMENT) {
//            r = DeclarationStatement(b, 0);
        } else if (t == DECLARATOR) {
//            r = Declarator(b, 0);
        } else if (t == DECLARATOR_IDENTIFIER) {
//            r = DeclaratorIdentifier(b, 0);
        } else if (t == DECLARATOR_IDENTIFIER_LIST) {
//            r = DeclaratorIdentifierList(b, 0);
        } else if (t == DECLARATOR_INITIALIZER) {
//            r = DeclaratorInitializer(b, 0);
        } else if (t == DECLARATORS) {
//            r = Declarators(b, 0);
        } else if (t == DEFAULT_STATEMENT) {
//            r = DefaultStatement(b, 0);
        } else if (t == DELETE_EXPRESSION) {
//            r = DeleteExpression(b, 0);
        } else if (t == DEPRECATED_ATTRIBUTE) {
//            r = DeprecatedAttribute(b, 0);
        } else if (t == DESTRUCTOR) {
//            r = Destructor(b, 0);
        } else if (t == DO_STATEMENT) {
//            r = DoStatement(b, 0);
        } else if (t == DOT_IDENTIFIER) {
//            r = DotIdentifier(b, 0);
        } else if (t == ELSE_STATEMENT) {
//            r = ElseStatement(b, 0);
        } else if (t == ENUM_BASE_TYPE) {
//            r = EnumBaseType(b, 0);
        } else if (t == ENUM_BODY) {
//            r = EnumBody(b, 0);
        } else if (t == ENUM_DECLARATION) {
//            r = EnumDeclaration(b, 0);
        } else if (t == ENUM_MEMBER) {
//            r = EnumMember(b, 0);
        } else if (t == ENUM_MEMBERS) {
//            r = EnumMembers(b, 0);
        } else if (t == EQUAL_EXPRESSION) {
//            r = EqualExpression(b, 0);
        } else if (t == EXPRESSION_STATEMENT) {
//            r = ExpressionStatement(b, 0);
        } else if (t == FINAL_SWITCH_STATEMENT) {
//            r = FinalSwitchStatement(b, 0);
        } else if (t == FINALLY_STATEMENT) {
//            r = FinallyStatement(b, 0);
        } else if (t == FIRST_EXP) {
//            r = FirstExp(b, 0);
        } else if (t == FOR_STATEMENT) {
//            r = ForStatement(b, 0);
        } else if (t == FOREACH) {
//            r = Foreach(b, 0);
        } else if (t == FOREACH_AGGREGATE) {
//            r = ForeachAggregate(b, 0);
        } else if (t == FOREACH_RANGE_STATEMENT) {
//            r = ForeachRangeStatement(b, 0);
        } else if (t == FOREACH_STATEMENT) {
//            r = ForeachStatement(b, 0);
        } else if (t == FOREACH_TYPE) {
//            r = ForeachType(b, 0);
        } else if (t == FOREACH_TYPE_ATTRIBUTE) {
//            r = ForeachTypeAttribute(b, 0);
        } else if (t == FOREACH_TYPE_ATTRIBUTES) {
//            r = ForeachTypeAttributes(b, 0);
        } else if (t == FOREACH_TYPE_LIST) {
//            r = ForeachTypeList(b, 0);
        } else if (t == FUNC_DECLARATION) {
//            r = FuncDeclaration(b, 0);
        } else if (t == FUNCTION_ATTRIBUTE) {
//            r = FunctionAttribute(b, 0);
        } else if (t == FUNCTION_ATTRIBUTES) {
//            r = FunctionAttributes(b, 0);
        } else if (t == FUNCTION_BODY) {
//            r = FunctionBody(b, 0);
        } else if (t == FUNCTION_CONTRACTS) {
//            r = FunctionContracts(b, 0);
        } else if (t == FUNCTION_LITERAL) {
//            r = FunctionLiteral(b, 0);
        } else if (t == FUNCTION_LITERAL_BODY) {
//            r = FunctionLiteralBody(b, 0);
        } else if (t == GOTO_STATEMENT) {
//            r = GotoStatement(b, 0);
        } else if (t == IDENTIFIER) {
//            r = Identifier(b, 0);
        } else if (t == IDENTIFIER_LIST) {
//            r = IdentifierList(b, 0);
        } else if (t == IDENTITY_EXPRESSION) {
//            r = IdentityExpression(b, 0);
        } else if (t == IF_CONDITION) {
//            r = IfCondition(b, 0);
        } else if (t == IF_STATEMENT) {
//            r = IfStatement(b, 0);
        } else if (t == IMPORT) {
//            r = Import(b, 0);
        } else if (t == IMPORT_BIND) {
//            r = ImportBind(b, 0);
        } else if (t == IMPORT_BIND_LIST) {
//            r = ImportBindList(b, 0);
        } else if (t == IMPORT_DECLARATION) {
//            r = ImportDeclaration(b, 0);
        } else if (t == IMPORT_EXPRESSION) {
//            r = ImportExpression(b, 0);
        } else if (t == IMPORT_LIST) {
//            r = ImportList(b, 0);
        } else if (t == IN_EXPRESSION) {
//            r = InExpression(b, 0);
        } else if (t == IN_OUT) {
//            r = InOut(b, 0);
        } else if (t == IN_OUT_X) {
//            r = InOutX(b, 0);
        } else if (t == IN_STATEMENT) {
//            r = InStatement(b, 0);
        } else if (t == INCREMENT) {
//            r = Increment(b, 0);
        } else if (t == INDEX_EXPRESSION) {
//            r = IndexExpression(b, 0);
        } else if (t == INITIALIZE) {
//            r = Initialize(b, 0);
        } else if (t == INITIALIZER) {
//            r = Initializer(b, 0);
        } else if (t == INTEGER_EXPRESSION) {
//            r = IntegerExpression(b, 0);
        } else if (t == INTERFACE) {
//            r = Interface(b, 0);
        } else if (t == INTERFACE_DECLARATION) {
//            r = InterfaceDeclaration(b, 0);
        } else if (t == INTERFACES) {
//            r = Interfaces(b, 0);
        } else if (t == INVARIANT) {
//            r = Invariant(b, 0);
        } else if (t == IS_EXPRESSION) {
//            r = IsExpression(b, 0);
        } else if (t == KEY_VALUE_PAIR) {
//            r = KeyValuePair(b, 0);
        } else if (t == KEY_VALUE_PAIRS) {
//            r = KeyValuePairs(b, 0);
        } else if (t == LABELED_STATEMENT) {
//            r = LabeledStatement(b, 0);
        } else if (t == LAMBDA) {
//            r = Lambda(b, 0);
        } else if (t == LAST_CATCH) {
//            r = LastCatch(b, 0);
        } else if (t == LAST_EXP) {
//            r = LastExp(b, 0);
        } else if (t == LINKAGE_ATTRIBUTE) {
//            r = LinkageAttribute(b, 0);
        } else if (t == LINKAGE_TYPE) {
//            r = LinkageType(b, 0);
        } else if (t == LWR_EXPRESSION) {
//            r = LwrExpression(b, 0);
        } else if (t == MEMBER_FUNCTION_ATTRIBUTE) {
//            r = MemberFunctionAttribute(b, 0);
        } else if (t == MEMBER_FUNCTION_ATTRIBUTES) {
//            r = MemberFunctionAttributes(b, 0);
        } else if (t == MIXIN_DECLARATION) {
//            r = MixinDeclaration(b, 0);
        } else if (t == MIXIN_EXPRESSION) {
//            r = MixinExpression(b, 0);
        } else if (t == MIXIN_STATEMENT) {
//            r = MixinStatement(b, 0);
        } else if (t == MIXIN_TEMPLATE_NAME) {
//            r = MixinTemplateName(b, 0);
        } else if (t == MODULE_DECLARATION) {
//            r = ModuleDeclaration(b, 0);
        } else if (t == MODULE_FULLY_QUALIFIED_NAME) {
//            r = ModuleFullyQualifiedName(b, 0);
        } else if (t == MUL_EXPRESSION_) {
//            r = MulExpression_(b, 0);
        } else if (t == MULTIPLE_ASSIGN) {
//            r = MultipleAssign(b, 0);
        } else if (t == NEW_ANON_CLASS_EXPRESSION) {
//            r = NewAnonClassExpression(b, 0);
        } else if (t == NEW_EXPRESSION) {
//            r = NewExpression(b, 0);
        } else if (t == NEW_EXPRESSION_WITH_ARGS) {
//            r = NewExpressionWithArgs(b, 0);
        } else if (t == NON_EMPTY_STATEMENT) {
//            r = NonEmptyStatement(b, 0);
        } else if (t == NON_VOID_INITIALIZER) {
//            r = NonVoidInitializer(b, 0);
        } else if (t == OPCODE) {
//            r = Opcode(b, 0);
        } else if (t == OPERAND) {
//            r = Operand(b, 0);
        } else if (t == OPERANDS) {
//            r = Operands(b, 0);
        } else if (t == OR_OR_EXPRESSION) {
//            r = OrOrExpression(b, 0);
        } else if (t == OUT_STATEMENT) {
//            r = OutStatement(b, 0);
        } else if (t == PARAMETER) {
//            r = Parameter(b, 0);
        } else if (t == PARAMETER_ATTRIBUTES) {
//            r = ParameterAttributes(b, 0);
        } else if (t == PARAMETER_LIST) {
//            r = ParameterList(b, 0);
        } else if (t == PARAMETER_MEMBER_ATTRIBUTES) {
//            r = ParameterMemberAttributes(b, 0);
        } else if (t == PARAMETERS) {
//            r = Parameters(b, 0);
        } else if (t == POSTBLIT) {
//            r = Postblit(b, 0);
        } else if (t == POSTFIX_EXPRESSION) {
//            r = PostfixExpression(b, 0);
        } else if (t == POW_EXPRESSION_) {
//            r = PowExpression_(b, 0);
        } else if (t == PRAGMA) {
//            r = Pragma(b, 0);
        } else if (t == PRAGMA_STATEMENT) {
//            r = PragmaStatement(b, 0);
        } else if (t == PRIMARY_EXPRESSION) {
//            r = PrimaryExpression(b, 0);
        } else if (t == PROPERTY) {
//            r = Property(b, 0);
        } else if (t == PROPERTY_IDENTIFIER) {
//            r = PropertyIdentifier(b, 0);
        } else if (t == PROTECTION_ATTRIBUTE) {
//            r = ProtectionAttribute(b, 0);
        } else if (t == QUALIFIED_IDENTIFIER_LIST) {
//            r = QualifiedIdentifierList(b, 0);
        } else if (t == REGISTER) {
//            r = Register(b, 0);
        } else if (t == REGISTER_64) {
//            r = Register64(b, 0);
        } else if (t == REL_EXPRESSION) {
//            r = RelExpression(b, 0);
        } else if (t == RETURN_STATEMENT) {
//            r = ReturnStatement(b, 0);
        } else if (t == SCOPE_GUARD_STATEMENT) {
//            r = ScopeGuardStatement(b, 0);
        } else if (t == SCOPE_STATEMENT) {
//            r = ScopeStatement(b, 0);
        } else if (t == SCOPE_STATEMENT_LIST) {
//            r = ScopeStatementList(b, 0);
        } else if (t == SHARED_STATIC_CONSTRUCTOR) {
//            r = SharedStaticConstructor(b, 0);
        } else if (t == SHARED_STATIC_DESTRUCTOR) {
//            r = SharedStaticDestructor(b, 0);
        } else if (t == SHIFT_EXPRESSION_) {
//            r = ShiftExpression_(b, 0);
        } else if (t == SLICE_EXPRESSION) {
//            r = SliceExpression(b, 0);
        } else if (t == SPECIAL_KEYWORD) {
//            r = SpecialKeyword(b, 0);
        } else if (t == STATEMENT) {
//            r = Statement(b, 0);
        } else if (t == STATEMENT_LIST) {
//            r = StatementList(b, 0);
        } else if (t == STATEMENT_LIST_NO_CASE_NO_DEFAULT) {
//            r = StatementListNoCaseNoDefault(b, 0);
        } else if (t == STATEMENT_NO_CASE_NO_DEFAULT) {
//            r = StatementNoCaseNoDefault(b, 0);
        } else if (t == STATIC_ASSERT) {
//            r = StaticAssert(b, 0);
        } else if (t == STATIC_CONSTRUCTOR) {
//            r = StaticConstructor(b, 0);
        } else if (t == STATIC_DESTRUCTOR) {
//            r = StaticDestructor(b, 0);
        } else if (t == STATIC_ELSE_CONDITION) {
//            r = StaticElseCondition(b, 0);
        } else if (t == STATIC_IF_CONDITION) {
//            r = StaticIfCondition(b, 0);
        } else if (t == STORAGE_CLASS) {
//            r = StorageClass(b, 0);
        } else if (t == STORAGE_CLASSES) {
//            r = StorageClasses(b, 0);
        } else if (t == STRING_LITERAL) {
//            r = StringLiteral(b, 0);
        } else if (t == STRING_LITERALS) {
//            r = StringLiterals(b, 0);
        } else if (t == STRUCT_DECLARATION) {
//            r = StructDeclaration(b, 0);
        } else if (t == STRUCT_INITIALIZER) {
//            r = StructInitializer(b, 0);
        } else if (t == STRUCT_MEMBER_INITIALIZER) {
//            r = StructMemberInitializer(b, 0);
        } else if (t == STRUCT_MEMBER_INITIALIZERS) {
//            r = StructMemberInitializers(b, 0);
        } else if (t == SUPER_CLASS) {
//            r = SuperClass(b, 0);
        } else if (t == SWITCH_STATEMENT) {
//            r = SwitchStatement(b, 0);
        } else if (t == SYMBOL) {
//            r = Symbol(b, 0);
        } else if (t == SYMBOL_TAIL) {
//            r = SymbolTail(b, 0);
        } else if (t == SYNCHRONIZED_STATEMENT) {
//            r = SynchronizedStatement(b, 0);
        } else if (t == TEMPLATE_ALIAS_PARAMETER) {
//            r = TemplateAliasParameter(b, 0);
        } else if (t == TEMPLATE_ARGUMENT) {
//            r = TemplateArgument(b, 0);
        } else if (t == TEMPLATE_ARGUMENT_LIST) {
//            r = TemplateArgumentList(b, 0);
        } else if (t == TEMPLATE_ARGUMENTS) {
//            r = TemplateArguments(b, 0);
        } else if (t == TEMPLATE_DECLARATION) {
//            r = TemplateDeclaration(b, 0);
        } else if (t == TEMPLATE_INSTANCE) {
//            r = TemplateInstance(b, 0);
        } else if (t == TEMPLATE_MIXIN) {
//            r = TemplateMixin(b, 0);
        } else if (t == TEMPLATE_MIXIN_DECLARATION) {
//            r = TemplateMixinDeclaration(b, 0);
        } else if (t == TEMPLATE_PARAMETER) {
//            r = TemplateParameter(b, 0);
        } else if (t == TEMPLATE_PARAMETER_LIST) {
//            r = TemplateParameterList(b, 0);
        } else if (t == TEMPLATE_PARAMETERS) {
//            r = TemplateParameters(b, 0);
        } else if (t == TEMPLATE_SINGLE_ARGUMENT) {
//            r = TemplateSingleArgument(b, 0);
        } else if (t == TEMPLATE_THIS_PARAMETER) {
//            r = TemplateThisParameter(b, 0);
        } else if (t == TEMPLATE_TUPLE_PARAMETER) {
//            r = TemplateTupleParameter(b, 0);
        } else if (t == TEMPLATE_TYPE_PARAMETER) {
//            r = TemplateTypeParameter(b, 0);
        } else if (t == TEMPLATE_VALUE_PARAMETER) {
//            r = TemplateValueParameter(b, 0);
        } else if (t == TEST) {
//            r = Test(b, 0);
        } else if (t == THEN_STATEMENT) {
//            r = ThenStatement(b, 0);
        } else if (t == THROW_STATEMENT) {
//            r = ThrowStatement(b, 0);
        } else if (t == TRAITS_ARGUMENT) {
//            r = TraitsArgument(b, 0);
        } else if (t == TRAITS_ARGUMENTS) {
//            r = TraitsArguments(b, 0);
        } else if (t == TRAITS_EXPRESSION) {
//            r = TraitsExpression(b, 0);
        } else if (t == TRAITS_KEYWORD) {
//            r = TraitsKeyword(b, 0);
        } else if (t == TRY_STATEMENT) {
//            r = TryStatement(b, 0);
        } else if (t == TYPE) {
//            r = Type(b, 0);
        } else if (t == TYPE_CTOR) {
//            r = TypeCtor(b, 0);
        } else if (t == TYPE_CTORS) {
//            r = TypeCtors(b, 0);
        } else if (t == TYPE_SPECIALIZATION) {
//            r = TypeSpecialization(b, 0);
        } else if (t == TYPE_VECTOR) {
//            r = TypeVector(b, 0);
        } else if (t == TYPEID_EXPRESSION) {
//            r = TypeidExpression(b, 0);
        } else if (t == TYPEOF) {
//            r = Typeof(b, 0);
        } else if (t == UNARY_EXPRESSION) {
//            r = UnaryExpression(b, 0);
        } else if (t == UNION_DECLARATION) {
//            r = UnionDeclaration(b, 0);
        } else if (t == UNIT_TESTING) {
//            r = UnitTesting(b, 0);
        } else if (t == UPR_EXPRESSION) {
//            r = UprExpression(b, 0);
        } else if (t == USER_DEFINED_ATTRIBUTE) {
//            r = UserDefinedAttribute(b, 0);
        } else if (t == VAR_DECLARATIONS) {
//            r = VarDeclarations(b, 0);
        } else if (t == VAR_DECLARATOR) {
//            r = VarDeclarator(b, 0);
        } else if (t == VAR_DECLARATOR_IDENTIFIER) {
//            r = VarDeclaratorIdentifier(b, 0);
        } else if (t == VAR_FUNC_DECLARATION) {
//            r = VarFuncDeclaration(b, 0);
        } else if (t == VERSION_CONDITION) {
//            r = VersionCondition(b, 0);
        } else if (t == VERSION_SPECIFICATION) {
//            r = VersionSpecification(b, 0);
        } else if (t == VOID_INITIALIZER) {
//            r = VoidInitializer(b, 0);
        } else if (t == WHILE_STATEMENT) {
//            r = WhileStatement(b, 0);
        } else if (t == WITH_STATEMENT) {
//            r = WithStatement(b, 0);
        } else if (t == XOR_EXPRESSION_) {
//            r = XorExpression_(b, 0);
        } else {
//            b.setDebugMode(true);
            final DLangParser dLangParser = new DLangParser(b);
            r = dLangParser.parseModule();
//            r = parse_root_(t, b, 0);
        }
        exit_section_(b, 0, m, t, r, true, TRUE_CONDITION);
//        exit_section_(adapt_builder_(,));
    }

    @NotNull
    @Override
    public ASTNode parse(@NotNull IElementType root, @NotNull PsiBuilder builder) {
        parseLight(root, builder);
        return builder.getTreeBuilt();
    }
}
