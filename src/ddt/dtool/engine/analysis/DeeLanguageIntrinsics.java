/*******************************************************************************
 * Copyright (c) 2011, 2014 Bruno Medeiros and other Contributors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Bruno Medeiros - initial API and implementation
 *******************************************************************************/
package ddt.dtool.engine.analysis;

import static ddt.melnorme.utilbox.core.CoreUtil.array;

import java.util.ArrayList;
import java.util.Arrays;

import ddt.melnorme.lang.tooling.ast_actual.ElementDoc;
import ddt.melnorme.lang.tooling.engine.intrinsics.CommonLanguageIntrinsics;
import ddt.melnorme.lang.tooling.engine.intrinsics.InstrinsicsScope;
import ddt.melnorme.lang.tooling.engine.intrinsics.IntrinsicNamedElement;
import ddt.melnorme.lang.tooling.engine.intrinsics.ModuleQualifiedReference;
import ddt.melnorme.lang.tooling.symbols.INamedElement;
import ddt.melnorme.utilbox.misc.CollectionUtil;
import ddt.descent.core.ddoc.DdocParser;
import ddt.dtool.ddoc.TextUI;

/** 
 * Aggregator for all language intrinsics 
 */
public class DeeLanguageIntrinsics implements CommonLanguageIntrinsics {
	
	// helper method
	public static ElementDoc parseDDoc(String ddocSource) {
		return new DdocParser("/**" + ddocSource + "*/").parse();
	}
	
	public static DeeLanguageIntrinsics D2_063_intrinsics = new DeeLanguageIntrinsics();
	
	public DeeLanguageIntrinsics() {
		
	}
	
	public class DeePrimitiveType extends DeeIntrinsicType implements IPrimitiveDefUnit {
		
		// TODO DDOC
		public DeePrimitiveType(String name) {
			super(name, null);
		}
		
		public DeePrimitiveType(String name, ElementDoc doc) {
			super(name, doc);
		}
		
	}
	
	public final DeePrimitiveType void_type = new DeePrimitiveType("void");
	
	public final DeePrimitiveType bool_type = new DeePrimitiveType("bool");
	
	
	public final DeePrimitiveType byte_type = new DeePrimitiveType("byte");
	public final DeePrimitiveType ubyte_type = new DeePrimitiveType("ubyte");
	public final DeePrimitiveType short_type = new DeePrimitiveType("short");
	public final DeePrimitiveType ushort_type = new DeePrimitiveType("ushort");
	public final DeePrimitiveType int_type = new DeePrimitiveType("int");
	public final DeePrimitiveType uint_type = new DeePrimitiveType("uint");
	public final DeePrimitiveType long_type = new DeePrimitiveType("long");
	public final DeePrimitiveType ulong_type = new DeePrimitiveType("ulong");
	
	public final DeePrimitiveType char_type = new DeePrimitiveType("char");
	public final DeePrimitiveType wchar_type = new DeePrimitiveType("wchar");
	public final DeePrimitiveType dchar_type = new DeePrimitiveType("dchar");
	
	public final DeePrimitiveType float_type = new DeePrimitiveType("float");
	public final DeePrimitiveType double_type = new DeePrimitiveType("double");
	public final DeePrimitiveType real_type = new DeePrimitiveType("real");
	
	public final DeePrimitiveType ifloat_type = new DeePrimitiveType("ifloat");
	public final DeePrimitiveType idouble_type = new DeePrimitiveType("idouble");
	public final DeePrimitiveType ireal_type = new DeePrimitiveType("ireal");
	public final DeePrimitiveType cfloat_type = new DeePrimitiveType("cfloat");
	public final DeePrimitiveType cdouble_type = new DeePrimitiveType("cdouble");
	public final DeePrimitiveType creal_type = new DeePrimitiveType("creal");
	
	public final ModuleQualifiedReference string_type = new ModuleQualifiedReference("object", "string");
	
	
	public abstract class DeeIntrinsicType extends IntrinsicTypeDefUnit {
		
		public DeeIntrinsicType(String name, ElementDoc doc) {
			super(name, doc);
		}
		
		@Override
		public void createMembers(IntrinsicNamedElement... members) {
			this.membersScope = new InstrinsicsScope(members);
			
			membersScope.members.addAll(createCommonProperties(this));
			membersScope.members.addAll(Arrays.asList(members));
		}
		
	}
	
	public ArrayList<IntrinsicNamedElement> createCommonProperties(INamedElement type) {
		return CollectionUtil.<IntrinsicNamedElement>createArrayList( 
			new IntrinsicProperty("init", type, parseDDoc("initializer")),
			new IntrinsicProperty("sizeof", int_type, 
				parseDDoc("size in bytes (equivalent to C's $(D sizeof(type)))")),
			new IntrinsicProperty("alignof", int_type, parseDDoc("alignment size")),
			new IntrinsicProperty2("mangleof", string_type,
				parseDDoc("string representing the ‘mangled’ representation of the type")),
			new IntrinsicProperty2("stringof", string_type, 
				parseDDoc("string representing the source representation of the type"))
		);
	}
	
	{
		
		void_type.createMembers();
		
		bool_type.createMembers();
		
		byte_type.createMembers(createIntegralProperties());
		ubyte_type.createMembers(createIntegralProperties());
		short_type.createMembers(createIntegralProperties());
		ushort_type.createMembers(createIntegralProperties());
		int_type.createMembers(createIntegralProperties());
		uint_type.createMembers(createIntegralProperties());
		long_type.createMembers(createIntegralProperties());
		ulong_type.createMembers(createIntegralProperties());
		
		char_type.createMembers(createIntegralProperties());
		wchar_type.createMembers(createIntegralProperties());
		dchar_type.createMembers(createIntegralProperties());
		
		float_type.createMembers(createFloatProperties());
		double_type.createMembers(createFloatProperties());
		real_type.createMembers(createFloatProperties());
		ifloat_type.createMembers(createFloatProperties());
		idouble_type.createMembers(createFloatProperties());
		ireal_type.createMembers(createFloatProperties());
		cfloat_type.createMembers(createFloatProperties());
		cdouble_type.createMembers(createFloatProperties());
		creal_type.createMembers(createFloatProperties());
	}
	
	protected IntrinsicProperty[] createIntegralProperties() {
		return array(
			new IntrinsicProperty("max", int_type, parseDDoc("maximum value")),
			new IntrinsicProperty("min", int_type, parseDDoc("minimum value"))
		);
	}
	
	protected IntrinsicProperty[] createFloatProperties() {
		return array(
			new IntrinsicProperty("max", float_type, parseDDoc("maximum value")),
			new IntrinsicProperty("infinity", float_type, parseDDoc("infinity value")),
			new IntrinsicProperty("nan", float_type, parseDDoc("NaN value")),
			new IntrinsicProperty("dig", int_type, parseDDoc("number of decimal digits of precision")),
			new IntrinsicProperty("epsilon", float_type, parseDDoc("smallest increment to the value 1")),
			new IntrinsicProperty("mant_dig", int_type, parseDDoc("number of bits in mantissa")),
			new IntrinsicProperty("max_10_exp", int_type, 
				parseDDoc("maximum int value such that 10⊃ is representable")),
			new IntrinsicProperty("max_exp", int_type, 
				parseDDoc("maximum int value such that 2⊃ is representable")),
			new IntrinsicProperty("min_10_exp", int_type,  
				parseDDoc("minimum int value such that 10⊃ is representable as a normalized value")),
			new IntrinsicProperty("min_exp", int_type, 
				parseDDoc("minimum int value such that 2⊃ is representable as a normalized value")),
			new IntrinsicProperty("max", float_type, 
				parseDDoc("largest representable value that's not infinity")),
			new IntrinsicProperty("min_normal", float_type, 
				parseDDoc("smallest representable normalized value that's not 0")),
			new IntrinsicProperty("re", float_type, parseDDoc("real part")),
			new IntrinsicProperty("im", float_type, parseDDoc("imaginary part"))
		);
	}
	
	
	public final InstrinsicsScope primitivesScope = new InstrinsicsScope(
		void_type,
		
		bool_type,
		
		byte_type,
		ubyte_type,
		short_type,
		ushort_type,
		int_type,
		uint_type,
		long_type,
		ulong_type,
		
		char_type,
		wchar_type,
		dchar_type,
		
		float_type,
		double_type,
		real_type,
		
		ifloat_type,
		idouble_type,
		ireal_type,
		cfloat_type,
		cdouble_type,
		creal_type
	);
	
	public static final ModuleQualifiedReference OBJECT_CLASS_REF = new ModuleQualifiedReference("object", "Object");
	
	/* ----------------- ----------------- */
	
	public final IntrinsicTypePointer pointerType = new IntrinsicTypePointer();
	public final IntrinsicDynArray dynArrayType = new IntrinsicDynArray();
	public final IntrinsicStaticArray staticArrayType = new IntrinsicStaticArray();

	
	public class IntrinsicTypePointer extends DeeIntrinsicType {
		
		public static final String POINTER_NAME = "<pointer>";
		
		public IntrinsicTypePointer() {
			super(POINTER_NAME, null);
		}
		
	}
	
	public class IntrinsicDynArray extends DeeIntrinsicType {
		
		public static final String DYNAMIC_ARRAY_NAME = "<dynamic_array>";
		
		public IntrinsicDynArray() {
			super(DYNAMIC_ARRAY_NAME, 
				parseDDoc("See "+ TextUI.href("http://dlang.org/arrays.html#dynamic-arrays")));
			
			this.createMembers(
				new IntrinsicProperty("init", this, parseDDoc("Returns null.")), 
				new IntrinsicProperty("sizeof", int_type, 
					parseDDoc("Returns the size of the dynamic array reference, " +
					"which is 8 in 32-bit builds and 16 on 64-bit builds.")), 
				new IntrinsicProperty("length", int_type, parseDDoc("Get/set number of elements in the array. " +
					"It is of type $(D size_t).")), 
				new IntrinsicProperty("ptr", pointerType, 
					parseDDoc("Returns a pointer to the first element of the array.")), 
				new IntrinsicProperty("dup", this, 
					parseDDoc("Create a dynamic array of the same size and copy the contents of the array into it.")),
				new IntrinsicProperty("idup", this, 
					parseDDoc("Create a dynamic array of the same size and copy the contents of the array into it. " +
					"The copy is typed as being immutable.")), 
				new IntrinsicProperty("reverse", this, 
					parseDDoc("Reverses in place the order of the elements in the array. Returns the array.")), 
				new IntrinsicProperty("sort", this, 
					parseDDoc("Sorts in place the order of the elements in the array. Returns the array.")));
		}
		
	}
	
	public class IntrinsicStaticArray extends DeeIntrinsicType {
		
		public static final String STATIC_ARRAY_NAME = "<static_array>";
		
		public IntrinsicStaticArray() {
			super(STATIC_ARRAY_NAME,
				parseDDoc("See " + TextUI.href("http://dlang.org/arrays.html#static-arrays")));
			
			this.createMembers(
				new IntrinsicProperty("init", this, 
					parseDDoc("	Returns an array literal with each element of the literal being the .init property " +
					"of the array element type.")), 
				new IntrinsicProperty("sizeof", int_type, 
					parseDDoc("Returns the array length multiplied by the number of bytes per array element.")), 
				new IntrinsicProperty("length", int_type, 
					parseDDoc("Returns the number of elements in the array. This is a fixed quantity for static "+
					"arrays. It is of type $(D size_t).")), 
				new IntrinsicProperty("ptr", pointerType, 
					parseDDoc("Returns a pointer to the first element of the array.")), 
				new IntrinsicProperty("dup", dynArrayType, 
					parseDDoc("Create a dynamic array of the same size and copy the contents of the array into it.")),
				new IntrinsicProperty("idup", dynArrayType, 
					parseDDoc("Create a dynamic array of the same size and copy the contents of the array into it. " +
						"The copy is typed as being immutable.")), 
				new IntrinsicProperty("reverse", this, 
					parseDDoc("Reverses in place the order of the elements in the array. Returns the array.")), 
				new IntrinsicProperty("sort", this, 
					parseDDoc("Sorts in place the order of the elements in the array. Returns the array.")));
		}
		
	}
	
	public final IntrinsicTypeDefUnit object_type = new DeeIntrinsicType("___", null) { };
	
	public InstrinsicsScope createObjectPropertiesScope(INamedElement type) {
		
		return new InstrinsicsScope(CollectionUtil.addAll(
			createCommonProperties(type), 
			Arrays.asList(
				new IntrinsicProperty2("classinfo", new ModuleQualifiedReference("object", "TypeInfo_Class"), 
					parseDDoc("Information about the dynamic type of the class")
			)))
		);
	}
	
}