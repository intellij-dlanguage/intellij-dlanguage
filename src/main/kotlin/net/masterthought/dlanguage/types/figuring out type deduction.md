### Basic Types ###
```kotlin
val tvoid: Type? = null
val tint8: Type? = null
val tuns8: Type? = null
val tint16: Type? = null
val tuns16: Type? = null
val tint32: Type? = null
val tuns32: Type? = null
val tint64: Type? = null
val tuns64: Type? = null
val tint128: Type? = null
val tuns128: Type? = null
val tfloat32: Type? = null
val tfloat64: Type? = null
val tfloat80: Type? = null
val timaginary32: Type? = null
val timaginary64: Type? = null
val timaginary80: Type? = null
val tcomplex32: Type? = null
val tcomplex64: Type? = null
val tcomplex80: Type? = null
val tbool: Type? = null
val tchar: Type? = null
val twchar: Type? = null
val tdchar: Type? = null
val tshiftcnt: Type? = null
val tvoidptr: Type? = null    // void*
val tnull: Type? = null       // for null type
```

### Creating new Types from basic types

- pointers aka `*`
- static array aka `[n]`
- array aka `[]`
- reference types aka `ref int x`
- Tuples aka compile time argument lists

### Custom types
- class/union/struct

### Complex creation of new types

- functions/delegates
- associative arrays
### Templates
 - do the substitutions?
 
### Autos
 - needs working find usages? Needs find Usages that works well. Cache PsiScopeProcessor symbols?
   - Templates are instantiated in the scope that they are declared in. Aka a `template_.foo()`, could call a different `foo` even if there was a `foo` declared right next to it. see https://dlang.org/spec/template.html
   
