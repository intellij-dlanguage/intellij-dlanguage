/*******************************************************************************
 * Copyright (c) 2013, 2013 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Bruno Medeiros - initial API and implementation
 *******************************************************************************/
package ddt.dtool.parser;

import static ddt.dtool.util.NewUtils.lazyInitArrayList;
import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertNotNull;
import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertTrue;
import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertUnreachable;
import static ddt.melnorme.utilbox.core.CoreUtil.blindCast;

import java.util.ArrayList;

import ddt.melnorme.lang.tooling.ast.SourceRange;
import ddt.melnorme.lang.tooling.ast_actual.ASTNode;
import ddt.melnorme.utilbox.collections.ArrayView;
import ddt.melnorme.utilbox.core.CoreUtil;
import ddt.dtool.ast.definitions.CStyleVarArgsParameter;
import ddt.dtool.ast.definitions.DefUnit.ProtoDefSymbol;
import ddt.dtool.ast.definitions.FunctionParameter;
import ddt.dtool.ast.definitions.IFunctionParameter;
import ddt.dtool.ast.definitions.IFunctionParameter.FunctionParamAttribKinds;
import ddt.dtool.ast.definitions.NamelessParameter;
import ddt.dtool.ast.definitions.TemplateAliasParam;
import ddt.dtool.ast.definitions.ITemplateParameter;
import ddt.dtool.ast.definitions.TemplateThisParam;
import ddt.dtool.ast.definitions.TemplateTupleParam;
import ddt.dtool.ast.definitions.TemplateTypeParam;
import ddt.dtool.ast.definitions.TemplateValueParam;
import ddt.dtool.ast.expressions.ExpInfix.InfixOpType;
import ddt.dtool.ast.expressions.Expression;
import ddt.dtool.ast.expressions.Resolvable;
import ddt.dtool.ast.references.RefTypeModifier;
import ddt.dtool.ast.references.RefTypeModifier.TypeModifierKinds;
import ddt.dtool.ast.references.Reference;
import ddt.dtool.parser.common.LexElement;

public abstract class DeeParser_Parameters extends DeeParser_RefOrExp {

	protected static enum TplOrFnMode { TPL, FN, AMBIG }
	
	/** Helper class to parse function and template parameters */
	public final class DeeParser_RuleParameters {
		
		public TplOrFnMode mode;
		public ArrayList<Object> params;
		
		public DeeParser_RuleParameters(TplOrFnMode mode) {
			this.mode = mode;
		}
		
		public boolean isAmbiguous() {
			return mode == TplOrFnMode.AMBIG;
		}
		
		public DeeParser_RuleParameters parseParameters(ParseHelper parse) {
			return parse(parse, false);
		}
		
		public DeeParser_RuleParameters parse(ParseHelper parse, boolean isOptional) {
			if(parse.consume(DeeTokens.OPEN_PARENS, isOptional, true) == false)
				return this;
			parseParameterList(true);
			parse.consumeRequired(DeeTokens.CLOSE_PARENS);
			return this;
		}
		
		public void parseParameterList(boolean first) {
			params = new ArrayList<Object>();
			
			while(true) {
				Object param = parseParameter(first && lookAhead() != DeeTokens.COMMA);
				
				if(param == null) {
					break;
				}
				params.add(param);
				first = false;
				
				if(tryConsume(DeeTokens.COMMA)) {
					continue;
				}
				break;
			}
		}
		
		public Object parseParameter() {
			return parseParameter(false);
		}
		public Object parseParameter(boolean returnNullOnMissing) {
			ParseHelper parse = new ParseHelper(lookAheadElement());
			
			if(mode != TplOrFnMode.TPL && tryConsume(DeeTokens.TRIPLE_DOT)) {
				setMode(TplOrFnMode.FN);
				return parse.conclude(new CStyleVarArgsParameter());
			}
			
			if(mode != TplOrFnMode.FN && lookAhead() == DeeTokens.KW_ALIAS) {
				setMode(TplOrFnMode.TPL);
				return parseTemplateAliasParameter_start();
			}
			
			if(mode != TplOrFnMode.FN && tryConsume(DeeTokens.KW_THIS)) {
				setMode(TplOrFnMode.TPL);
				ProtoDefSymbol defId = parseDefId();
				return parse.conclude(new TemplateThisParam(defId));
			}
			
			ArrayList<LexElement> attribs = null;
			if(mode != TplOrFnMode.TPL) {
				while(true) {
					FunctionParamAttribKinds paramAttrib = FunctionParamAttribKinds.fromToken(lookAhead());
					if(paramAttrib == null || isTypeModifier(lookAhead()) && lookAhead(1) == DeeTokens.OPEN_PARENS)
						break;
					
					LexElement attribToken = consumeLookAhead();
					attribs = lazyInitArrayList(attribs);
					attribs.add(attribToken);
					
					if(!isTypeModifier(attribToken.type)) {
						setMode(TplOrFnMode.FN);
					}
				}
			}
			
			return new AmbiguousParameter().parseAmbiguousParam(this, returnNullOnMissing, parse.nodeStart, attribs);
		}
		
		
		protected void setMode(TplOrFnMode newMode) {
			if(mode == newMode)
				return;
			assertTrue(mode == TplOrFnMode.AMBIG);
			
			mode = newMode;
			if(params == null)
				return;
			
			ArrayList<AmbiguousParameter> oldParams = blindCast(params);
			params = new ArrayList<Object>();
			for (AmbiguousParameter param : oldParams) {
				params.add(mode == TplOrFnMode.FN ? param.convertToFunction() : param.convertToTemplate());
			} 
		}
		
		public final ArrayView<IFunctionParameter> getAsFunctionParameters() {
			assertTrue(mode == TplOrFnMode.FN);
			return arrayView(CoreUtil.<ArrayList<IFunctionParameter>>blindCast(params));
		}
		
		public final ArrayView<IFunctionParameter> toFunctionParameters() {
			assertTrue(isAmbiguous());
			setMode(TplOrFnMode.FN);
			return getAsFunctionParameters();
		}
		
		public final ArrayView<ITemplateParameter> getAsTemplateParameters() {
			assertTrue(mode == TplOrFnMode.TPL);
			return arrayView(CoreUtil.<ArrayList<ITemplateParameter>>blindCast(params));
		}
		
		public final ArrayView<ITemplateParameter> toTemplateParameters() {
			assertTrue(isAmbiguous());
			setMode(TplOrFnMode.TPL);
			return getAsTemplateParameters();
		}
	}
	
	protected class AmbiguousParameter {
		
		ArrayList<LexElement> attribs;
		
		Reference ref;
		ProtoDefSymbol defId = null;
		Reference typeSpecialization = null;
		Expression valueSpecialization = null;
		TypeOrExpResult paramDefault = new TypeOrExpResult(null, null);
		boolean isVariadic = false;
		
		SourceRange sr;
		
		public Object parseAmbiguousParam(DeeParser_RuleParameters params, boolean returnNullOnMissing, 
			int nodeStart, ArrayList<LexElement> attribs) {
			this.attribs = attribs;
			
			// Possible outcomes from this point
			// NamelessParam or TemplateTypeParam
			// NamelessParam(variadic) or TemplateTupleParam
			// FunctionParameter or TemplateValueParam (isValueParam = true) 
			
			parsing: {
				NodeResult<Reference> refResult = parseTypeReference();
				ref = refResult.node;
				if(refResult.ruleBroken) {
					break parsing;
				}
				if(ref == null) {
					if(attribs == null && returnNullOnMissing) { // No Parameter at all
						return null;
					}
					ref = parseMissingTypeReference(true);
					break parsing;
				}
				
				if(lookAhead() == DeeTokens.IDENTIFIER) {
					defId = parseDefId(); 
				} else {
					if(!(couldHaveBeenParsedAsId(ref) && attribs == null)) {
						if(params.mode == TplOrFnMode.TPL) {
							assertTrue(attribs == null);
							defId = parseDefId(); // will create a missing defId;
						} else {
							params.setMode(TplOrFnMode.FN); // Can only be NamelessParam
						}
					}
				}
				
				if((defId == null) || (defId != null && params.mode != TplOrFnMode.TPL) ) {
					if(tryConsume(DeeTokens.TRIPLE_DOT)) {
						if(defId != null) {
							params.setMode(TplOrFnMode.FN); //FunctionParameter
						}
						isVariadic = true;
						break parsing;
					}
				}
				
				if(params.mode != TplOrFnMode.FN && tryConsume(DeeTokens.COLON)) {
					params.setMode(TplOrFnMode.TPL); // TemplateTypeParam or TemplateValueParam
					if(defId == null) { 
						typeSpecialization = parseTypeReference_ToMissing().node;
					} else {
						valueSpecialization = parseExpression_toMissing(InfixOpType.CONDITIONAL);
					}
				}
				if(tryConsume(DeeTokens.ASSIGN)) {
					parseParamDefault(params);
				}
			}
			
			assertTrue(defId == null ? valueSpecialization == null : typeSpecialization == null);
			assertTrue(defId != null ? !paramDefault.isRefOnly() : true);
			
			sr = SourceRange.srStartToEnd(nodeStart, getSourcePosition());
			switch (params.mode) { default: throw assertUnreachable();
			case AMBIG: return this;
			case TPL: return convertToTemplate();
			case FN: return convertToFunction();
			}
		}
		
		public void parseParamDefault(DeeParser_RuleParameters params) {
			if(params.mode == TplOrFnMode.FN || defId != null) {
				paramDefault = new TypeOrExpResult(null, result(false, parseAssignExpression_toMissing()));
			} else if(params.mode == TplOrFnMode.TPL) {
				if(defId == null) {
					paramDefault = new TypeOrExpResult(parseTypeReference_ToMissing(), null); 
				} else {
					paramDefault = new TypeOrExpResult(null, result(false, parseAssignExpression_toMissing()));
				}
			} else {
				paramDefault = parseTypeOrExpression(InfixOpType.ASSIGN);
				if(paramDefault.isNull()) {
					paramDefault = new TypeOrExpResult(
						parseTypeReference_ToMissing(), 
						result(false, parseAssignExpression_toMissing()));
				} else if(paramDefault.isExpOnly() && defId == null) {
					params.setMode(TplOrFnMode.FN); //NamelessParameter
				}  else if(paramDefault.isRefOnly()) {
					params.setMode(TplOrFnMode.TPL);
				}
			}
		}
		
		public IFunctionParameter convertToFunction() {
			if(defId == null) {
				return conclude(sr,
					new NamelessParameter(arrayViewG(attribs), ref, paramDefault.toExpression().node, isVariadic));
			}
			return conclude(sr,
				new FunctionParameter(arrayViewG(attribs), ref, defId, paramDefault.toExpression().node, isVariadic));
		}
		
		public ITemplateParameter convertToTemplate() {
			if(attribs != null)  {
				
				for (int i = attribs.size()-1; i >= 0 ; i--) {
					LexElement attribToken = attribs.get(i);
					TypeModifierKinds modifier = assertNotNull(determineTypeModifier(attribToken.type));
					ref = concludeNode(srBounds(attribToken.getStartPos(), ref.getEndPos(), 
						new RefTypeModifier(modifier, ref, false)));
				}
			}
			if(defId == null && couldHaveBeenParsedAsId(ref)) {
				defId = convertRefIdToDef(ref);
				return conclude(sr, isVariadic ? 
					new TemplateTupleParam(defId) :
					new TemplateTypeParam(defId, typeSpecialization, paramDefault.toReference().node));
			} else {
				defId = defId != null ? defId : createEmptyDefSymbol(ref.getEndPos());
				return conclude(sr, 
					new TemplateValueParam(ref, defId, valueSpecialization, paramDefault.toExpression().node));
			}
		}
	}
	
	public static ProtoDefSymbol createEmptyDefSymbol(int position) {
		return new ProtoDefSymbol("", srAt(position), null);
	}
	
	protected ASTNode parseTemplateAliasParameter_start() {
		consumeLookAhead(DeeTokens.KW_ALIAS);
		ParseHelper parse = new ParseHelper();
		
		ProtoDefSymbol defId;
		Resolvable init = null;
		Resolvable specialization = null;
		
		parsing: {
			defId = parse.checkResult(parseDefId());
			if(parse.ruleBroken) break parsing;
			
			if(tryConsume(DeeTokens.COLON)) {
				NodeResult<Resolvable> typeOrCondExp = parseTypeOrExpression(InfixOpType.CONDITIONAL, true);
				specialization = nullTypeOrExpToParseMissing(typeOrCondExp.node);
			}
			if(tryConsume(DeeTokens.ASSIGN)) {
				init = nullTypeOrExpToParseMissing(parseTypeOrAssignExpression(true).node);
			}
		}
		
		return parse.conclude(new TemplateAliasParam(defId, specialization, init));
	}
	
}