/*******************************************************************************
 * Copyright (c) 2013, 2014 Bruno Medeiros and other Contributors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Bruno Medeiros - initial API and implementation
 *******************************************************************************/
package ddt.melnorme.lang.tooling.engine.intrinsics;

import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertFail;
import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertNotNull;
import ddt.melnorme.lang.tooling.ast_actual.ElementDoc;
import ddt.melnorme.lang.tooling.context.ISemanticContext;
import ddt.melnorme.lang.tooling.engine.PickedElement;
import ddt.melnorme.lang.tooling.engine.resolver.IResolvable;
import ddt.melnorme.lang.tooling.engine.resolver.TypeSemantics;
import ddt.melnorme.lang.tooling.engine.resolver.VarSemantics;
import ddt.melnorme.lang.tooling.engine.scoping.CommonScopeLookup;
import ddt.melnorme.lang.tooling.symbols.IConcreteNamedElement;
import ddt.melnorme.lang.tooling.symbols.INamedElement;
import ddt.melnorme.utilbox.misc.CollectionUtil;
import ddt.dtool.ast.definitions.EArcheType;
import ddt.dtool.ast.expressions.Resolvable;

public interface CommonLanguageIntrinsics {

	public abstract class IntrinsicTypeDefUnit extends IntrinsicNamedElement implements IConcreteNamedElement {
		
		protected InstrinsicsScope membersScope;
		
		public IntrinsicTypeDefUnit(String name, ElementDoc doc) {
			super(name, doc);
		}
		
		public InstrinsicsScope getMembersScope() {
			return membersScope;
		}
		
		@Override
		public String toString() {
			return "intrinsic_type#" + getName();
		}
		
		public abstract void createMembers(IntrinsicNamedElement... members);
		
		@Override
		public TypeSemantics doCreateSemantics(PickedElement<?> pickedElement) {
			return new TypeSemantics(this, pickedElement) {
			
				@Override
				public void resolveSearchInMembersScope(CommonScopeLookup search) {
					assertNotNull(membersScope);
					membersScope.resolveSearchInScope(search);
				}
				
			};
		}
		
	}
	
	public abstract class AbstractIntrinsicProperty extends IntrinsicNamedElement implements IConcreteNamedElement {
		
		public AbstractIntrinsicProperty(String name, ElementDoc doc) {
			super(name, doc);
		}
		
		@Override
		public EArcheType getArcheType() {
			return EArcheType.Variable;
		}
		
		@Override
		public String toString() {
			return "intrinsic_property#" + getName();
		}
		
		protected abstract INamedElement resolveType(ISemanticContext mr);
		
		@Override
		public VarSemantics doCreateSemantics(PickedElement<?> pickedElement) {
			return new VarSemantics(this, pickedElement) {
				
				@Override
				public INamedElement resolveTypeForValueContext() {
					return resolveType(context);
				};
				
				@Override
				protected Resolvable getTypeReference() {
					throw assertFail();
				};
				
			};
		}
		
	}
	
	public class IntrinsicProperty extends AbstractIntrinsicProperty {
		
		public final INamedElement type;
		
		public IntrinsicProperty(String name, INamedElement type, ElementDoc doc) {
			super(name, doc);
			this.type = assertNotNull(type);
		}
		
		@Override
		protected INamedElement resolveType(ISemanticContext mr) {
			return type;
		}
		
	}
	
	public interface IPrimitiveDefUnit { }
	
	/* ----------------- refs ----------------- */
	
	public class IntrinsicProperty2 extends AbstractIntrinsicProperty {
		
		public final IResolvable typeRef;
		
		public IntrinsicProperty2(String name, IResolvable typeRef, ElementDoc doc) {
			super(name, doc);
			this.typeRef = assertNotNull(typeRef);
		}
		
		@Override
		protected INamedElement resolveType(ISemanticContext mr) {
			return CollectionUtil.getFirstElementOrNull(typeRef.findTargetDefElements(mr, true));
		}
		
	}
	
}