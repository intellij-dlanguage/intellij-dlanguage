How to contribute to Intellij D Language
========================================

We're a small team so all contributions are appreciated be they bug reports, help with testing or providing code/translations.

## Creating Issues
Before opening a new issue please do the following:

 - Check existing issues (either open or closed) to avoid creating a duplicate issue.
 - If raising a bug please include the version of IntelliJ you are using and the version of the plugin along with any stack trace or screenshots that will help us replicate the problem. If you've cloned the repository please confirm which branch you are on.

## Prerequisite knowledge
If you want to get stuck into the code then you should already be comfortable with Java and be familiar with [Gradle](https://gradle.org/). Having some experience with [Kotlin](https://kotlinlang.org/) is useful as new features should ideally be done using Kotlin.

Make sure you can build and run the plugin locally. Simply running `./gradlew :runIde` in the project root is enough to get up and running if you're setup for doing Java development.

## Get familiar with IntelliJ Platform
The IntelliJ API can feel pretty horrible at times and [their documentation](http://www.jetbrains.org/intellij/sdk/docs/welcome.html) is lacking a lot of information. It's a good idea to look through the source of other plugins for getting an understanding of how things work. The following are some examples that I've found useful:

 - [intellij-plugins](https://github.com/JetBrains/intellij-plugins)
 - [go-lang-idea-plugin](https://github.com/go-lang-plugin-org/go-lang-idea-plugin)
 - [intellij-rust](https://github.com/intellij-rust/intellij-rust)

Jetbrains have recently provided the [Intellij Platform Explorer](https://plugins.jetbrains.com/intellij-platform-explorer) which allows plugin developers to search the plugin repo for plugins that make use of particular extension points. This helps when looking for examples of how to implement a particular feature. The platform explorer is available here:

https://plugins.jetbrains.com/intellij-platform-explorer

Any problems with the Intellij SDK should be reported to Jetbrains: https://youtrack.jetbrains.com/issue/IJSDK

# Tour of our code base
## References/Code Completion
Most Dlang IDEs use DCD to provide “goto declaration”/”information about symbols”/”code completion”.
The Intellij api combines all of this functionality in the Reference classes
(see implementations of classes `PsiReference` and `PsiPolyVariantReference`).
Each “chunk of text/identifier” that has a declaration, has its own Reference.
The `resolve` and `multiResolve` methods provide go-to declaration functionality.
These methods can either be implemented with methods that look for declarations, by looping through files etc. ,
or with ScopeProcessors(See below). PsiReference class also contains a `getVariants` method.
This method effectively returns an array of Strings/PsiElements (I will explain what those are later),
that are visible in the current scope. These Objects are then used for code completion.
Currently, the previously mentioned methods have no implementations in the develop branch.
When DCD is available its code completion output is passed to intellij via a `CompletionContributer`
(allows adding of code completion directly to the code completion dialog, instead of through `getVariants`).
Go to declaration features are provided by external tools using `DRefernceContributor`
(which manually inserts results normally provided by `resolve` and `muliResolve`).
References are also used in renaming refactoring, Safe delete and various warnings/error checkers.
A lot of the utils for interacting with DCD can be found in `io.github.intellij.dlanguage.codeinsight.dcd`.
Most of the reference features implementations can be found in `io.github.intellij.dlanguage.psi.resolve`;

## Parser/PsiElement/Ast/Lexer:
Psi classes are generated using a script written in D. You will need to have `rdmd` installed on your machine to be able to build the project.
When implementing features, you rarely interact with the file text, instead you interact with the `PsiElement` class, which provide a syntax tree of the code in question. [Here](https://github.com/intellij-dlanguage/intellij-dlanguage/blob/master/src/test/resources/gold/parser/expected/arrays_bounds_checking.txt)’s an example of what a psi syntax tree looks like. There are different types of PsiElement’s (see DlangTypes.java). The parser used to be, auto-generated  with a bnf grammar. The example syntax tree I linked too was generated from the bnf-based parser. The bnf parser definition can be found [here](https://github.com/intellij-dlanguage/intellij-dlanguage/blob/master/src/main/java/net/masterthought/dlanguage/grammar/DLanguage.bnf). Unfortunately the parser generated from bnf, was very fiddly, has lots of edge cases, does not have optimal performance, and is difficulty to create good error handling for. D is by far he most complicated language I haves seen(syntax/parser wise), so creating a good generated parser would be too complicated/time consuming, which is why the new parser is written by hand and is based of libdparse. Both parsers, accept tokens from a jflex lexer, who’s definition can be found [here](https://github.com/intellij-dlanguage/intellij-dlanguage/blob/master/src/main/java/net/masterthought/dlanguage/lexer/DLanguageLexer.flex). The lexer is generated from the previously linked file, by the Intelllij grammar-kit plugin(also needed for generating the parser from bnf). Because of a semi obscure d feature(token strings), and nesting comments, the lexer pushes the limits of what jflex can do, so we may at some point also rewrite the lexer by hand. A more in depth overview of the parser internals can be found [here](http://www.jetbrains.org/intellij/sdk/docs/reference_guide/custom_language_support/implementing_parser_and_psi.html). A more in depth overview of the generated parser and generated lexer can be found [here](http://www.jetbrains.org/intellij/sdk/docs/tutorials/custom_language_support/grammar_and_parser.html) and [here](http://www.jetbrains.org/intellij/sdk/docs/tutorials/custom_language_support/lexer_and_parser_definition.html).

## NamedElements/Stub Index/Stub elements
Certain implementations of the `PsiElement` class may have a name, aka a `PsiElement` for a class declaration returns the name of the class declaration. PsiElements that have a name implement the `DNamedElement` interface (which in turn implements the `PsiNamedElement` interface). Named elements have a `setName` method for rename refactoring. Because loading `PsiElement` syntax trees for large files use a lot of RAM, it isn’t practical to load all the syntax tress for all files. Because of this Stub trees/stub elements where created; they provide a “summary” of the file. Certain important psi elements are selected to be made into stubs elements. Currently, all named elements, unittesting blocks, and shared/static destructors are stub elements. Stub indexes are a way of search for stub elements, and are effectively in a multimap of the type <key extends Object, Collection<StubElement>>. The key can be anything, but it is most practical to make it a string. More info can be found here `http://www.jetbrains.org/intellij/sdk/docs/basics/indexing_and_psi_stubs/stub_indexes.html`. The notable indexes are `DTopLevelDeclaration`, `DImportIndex`, and `DAllNameIndex`. `DTopLevelDeclaration` contains all top level declarations, aka functions declared in a unittest do not count, global variable do. `DImportIndex`, contains top level declarations, and is used to determine which files should be searched for declarations. `DAllNameIndex`, contains all named elements. Clases defining the indexes can be found in `io.github.intellij.dlanguage.stubs.index` and `io.github.intellij.dlanguage.index`, however most actual indexing happens in `io.github.intellij.dlanguage.stubs.types.DNamedStubElementType.indexStub`

## Scope Processors
Scope Processors are effectively a structured way of recursing over the psi tree. They can be used for go to declaration features and code completion features among many others. For them to function the `processDeclarations` methods need to be implemented in relevant `PsiElement` classes. More info can be found [here](http://www.jetbrains.org/intellij/sdk/docs/reference_guide/custom_language_support/references_and_resolve.html).


## Syntax Highlighting
Syntax highlighting is done by a lexer (the same as the lexer which passes tokens to the parser). Also, an extra highlight pass is done to highlight extra element (like `AtAttribute` and string errors).


## Misc tips
If you checkout an old branch or rename the gradle project, intellij will ask you if you want to remove modules that where imported from the gradle project; don’t let it remove modules imported from gradle because it sometimes renders the project unusable.  The grammar-kit plugin can generate a parser from bnf as well as generate the lexer from a .flex file. The keyboard shortcut for this is ctrl+shift+g(assuming default keybindings etc.). Grammar-kit doesn’t always put the generated parser or lexer where you would expect, so you may have too move files around. All generated code should be in gen/.  If you use windows, dcd will not work if it is compiled with dmd, use ldc instead. A lot of features need to be added to a configuration file called plugin.xml in order to work. We also have slack channel for quick questions/discussion: https://intellij-dlang.slack.com. A lot of the information in the readme is old/out of date.


## Contributing to the code base
So assuming you've forked the repo and cloned it to your development machine, please make sure that you have the **develop** branch checked out (and up to date) before making any changes. The develop branch should always have the most recent changes and any PR should be made to this branch.

 - Commits should relate to a github issue, please make sure the issue number is in the commit message prefixed with a hash so that it can be viewed directly from the issue.
 - For small changes making them in develop will be fine but for anything substantial please make a new branch prefixed with either _feature/_ or _bugfix/_ so if adding a german translation the branch name coule be `feature/german_translation`.
 - Please keep the commits relevant to the feature/bugfix being worked on. Eg: A pull request from a branch named `bugfix/fix-nullpointer-in-DCommenter` shouldn't include commits for other unrelated changes.
 - Please keep in mind that someone will have to review the pull request before it's merged so if you are about to push multiple consecutive small commits consider squashing them (if appopriate) as it can make it easier for review.
 
