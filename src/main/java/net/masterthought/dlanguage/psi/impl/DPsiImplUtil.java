package net.masterthought.dlanguage.psi.impl;

import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import net.masterthought.dlanguage.psi.DLanguageIdentifier;
import net.masterthought.dlanguage.psi.impl.named.DLanguageIdentifierImpl;
import net.masterthought.dlanguage.stubs.DLanguageIdentifierStub;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * Source of the methods pointed out in DLanguage.bnf.
 * todo: this file is rather long and not getting shorter. Split into multiple files at a later date.
 */
public class DPsiImplUtil {


    // ------------- Identifier ------------------ //
    @NotNull
    public static String getName(@NotNull DLanguageIdentifier o) {
        DLanguageIdentifierStub stub = ((DLanguageIdentifierImpl) o).getStub();
        if (stub != null) return StringUtil.notNullize(stub.getName());
        return o.getText();
    }

    @NotNull
    public static PsiElement setName(@NotNull DLanguageIdentifier o, @NotNull String newName) {
        PsiElement e = DElementFactory.createDLanguageIdentifierFromText(o.getProject(), newName);
        if (e == null) return null;
        o.replace(e);
        return o;
    }

    @Nullable
    public static PsiElement findParentOfType(PsiElement element, Class className) {
        if (className.isInstance(element)) {
            return element;
        } else {
            try {
                return findParentOfType(element.getParent(), className);
            } catch (Exception e) {
                return null;
            }
        }

    }
/*
    // ------------- Identifier ------------------ //

    // ------------- Function Definition ------------------ //

    @NotNull
    public static String getName(@NotNull DLanguageFuncDeclaration o) {
        DLanguageFunctionDeclarationStub stub = o.getStub();
        if (stub != null) return StringUtil.notNullize(stub.getName());
        return o.getIdentifier().getText();
    }

    public static List<DLanguageTemplateParameter> getTemplateArguments(DLanguageFuncDeclaration o) {
        if (o.getTemplateParameters() != null)
            return Arrays.asList(getChildrenOfType(o.getTemplateParameters(), DLanguageTemplateParameter.class));
        return new ArrayList<>();
    }

    // ------------- Function Definition ------------------ //

    // ------------- Class Definition ------------------ //
    @NotNull
    public static String getName(@NotNull DLanguageClassDeclaration o) {
        DLanguageInterfaceOrClassStub stub = o.getStub();
        if (stub != null) return StringUtil.notNullize(stub.getName());

        return o.getIdentifier().getText();
    }


    public static List<CanInherit> whatInheritsFrom(@NotNull DLanguageClassDeclaration o) {
        final DLanguageBaseClassList baseClassList = o.getBaseClassList();
        if (baseClassList == null)
            return Collections.emptyList();
        ArrayList<CanInherit> res = new ArrayList<>();
        ArrayList<DLanguageBasicType> basicTypes = new ArrayList<>();
        basicTypes.add(baseClassList.getSuperClass().getBasicType());
        for (DLanguageInterface interface_ : findChildrenOfType(baseClassList.getInterfaces(), DLanguageInterface.class)) {
            basicTypes.add(interface_.getBasicType());
        }
        for (DLanguageBasicType basicType : basicTypes) {
            assert (basicType.getBasicTypeX() == null);
            assert (basicType.getTypeVector() == null);
//            assert (basicType.getTypeof() == null);
            final DLanguageIdentifierList identifierList = basicType.getIdentifierList();
            final List<PsiNamedElement> definitionNodesSimple = DResolveUtil.INSTANCE.findDefinitionNode(identifierList.getProject(), getEndOfIdentifierList(identifierList));
            Set<CanInherit> definitionNodes = new HashSet<>();
            for (PsiElement node : definitionNodesSimple) {
                if (definitionNodes instanceof CanInherit)
                    definitionNodes.add((CanInherit) definitionNodes);
            }
            assert (definitionNodes.size() == 1);
            res.add((CanInherit) definitionNodes.toArray()[0]);

        }

        return res;
    }

    public static Map<String, DLanguageIdentifier> getSuperClassNames(@NotNull DLanguageClassDeclaration o) {
        final DLanguageBaseClassList baseClassList = o.getBaseClassList();
        if (baseClassList == null)
            return Collections.emptyMap();
        Map<String, DLanguageIdentifier> res = new HashMap<>();
        ArrayList<DLanguageBasicType> basicTypes = new ArrayList<>();
        basicTypes.add(baseClassList.getSuperClass().getBasicType());
        for (DLanguageInterface interface_ : findChildrenOfType(baseClassList.getInterfaces(), DLanguageInterface.class)) {
            basicTypes.add(interface_.getBasicType());
        }
        for (DLanguageBasicType basicType : basicTypes) {
            assert (basicType.getBasicTypeX() == null);
            assert (basicType.getTypeVector() == null);
            assert (basicType.getTypeof() == null);
            final DLanguageIdentifierList identifierList = basicType.getIdentifierList();
            res.put(getEndOfIdentifierList(identifierList).getName(), getEndOfIdentifierList(identifierList));
        }

        return res;
    }

    // ------------- Class Definition ------------------ //

    // ------------- Struct Definition ------------------ //
    @NotNull
    public static String getName(@NotNull DLanguageStructDeclaration o) {
        DLanguageStructDeclarationStub stub = o.getStub();
        if (stub != null) return StringUtil.notNullize(stub.getName());

        if (o.getIdentifier() != null) {
            return o.getIdentifier().getText();
        } else {
            return "not found";
        }
    }

    @Nullable
    public static PsiElement setName(@NotNull DLanguageStructDeclaration o, @NotNull String newName) {
        if (o.getIdentifier() != null)
            o.getIdentifier().setName(newName);
        else
            return null;
        return o;
    }


    // ------------- Struct Definition ------------------ //

    // ------------- Enum Definition ------------------ //
    @NotNull
    public static String getName(@NotNull DLanguageEnumDeclaration o) {
        DLanguageEnumDeclarationStub stub = o.getStub();
        if (stub != null) return StringUtil.notNullize(stub.getName());

        if (o.getIdentifier() != null) {
            return o.getIdentifier().getText();
        } else {
            return "not found";
        }
    }

    @Nullable
    public static PsiElement setName(@NotNull DLanguageEnumDeclaration o, @NotNull String newName) {
        if (o.getIdentifier() != null) {
            o.getIdentifier().setName(newName);
        } else if (o.getAnonymousEnumDeclaration() != null) {
            return null;
        }
        return o;
    }


    // ------------- Enum Definition -------------------- //

    // ------------- Union Definition ------------------ //
    @NotNull
    public static String getName(@NotNull DLanguageUnionDeclaration o) {
        DLanguageUnionDeclarationStub stub = o.getStub();
        if (stub != null) return StringUtil.notNullize(stub.getName());

        if (o.getIdentifier() != null) {
            return o.getIdentifier().getText();
        } else {
            return "not found";
        }
    }

    @Nullable
    public static PsiElement setName(@NotNull DLanguageUnionDeclaration o, @NotNull String newName) {
        if (o.getIdentifier() != null) {
            o.getIdentifier().setName(newName);
        } else
            return null;
        return o;
    }

    // ------------- Union Definition -------------------- //

    // ------------- Template Definition ------------------ //

    @NotNull
    public static String getName(@NotNull DLanguageTemplateDeclaration o) {
        DLanguageTemplateDeclarationStub stub = o.getStub();
        if (stub != null) return StringUtil.notNullize(stub.getName());

        return o.getIdentifier().getText();
    }

    // ------------- Template Definition ------------------ //

    // ------------- Constructor ------------------ //

    @NotNull
    public static String getName(@NotNull DLanguageConstructor o) {
        if (DUtil.getParentClassOrStruct(o) != null)
            return DUtil.getParentClassOrStruct(o).getName();
        return "";
    }

    // ------------- Constructor ------------------ //

    // ------------ Module Declaration ----------------- //

    @NotNull
    public static String getName(@NotNull DLanguageModuleDeclaration o) {
        DLanguageModuleDeclarationStub stub = o.getStub();
        if (stub != null) return StringUtil.notNullize(stub.getName());

        if (o.getModuleFullyQualifiedName() == null) {
            Logger.getLogger(DPsiImplUtil.class).debug("this had no name: " + o.getText());
            return "";
        }

        if (o.getModuleFullyQualifiedName().getText() != null) {
            return o.getModuleFullyQualifiedName().getText();
        } else {
            return "not found";
        }
    }

    @NotNull
    public static PsiElement setName(@NotNull DLanguageModuleDeclaration o, @NotNull String newName) {
        PsiElement e = DElementFactory.createDLanguageModuleFromText(o.getProject(), newName);
        if (e == null) return null;
        o.replace(e);
        return o;
    }

    // ------------ Module Declaration ----------------- //

    // ------------- Interface Definition ------------------ //

    @NotNull
    public static String getName(@NotNull DLanguageInterfaceDeclaration o) {
        DLanguageInterfaceDeclarationStub stub = o.getStub();
        if (stub != null) return StringUtil.notNullize(stub.getName());
        if (o.getIdentifier() == null) {
            return "";
        }
        return o.getIdentifier().getText();
    }

    public static List<CanInherit> whatInheritsFrom(@NotNull DLanguageInterfaceDeclaration o) {
        DLanguageBaseInterfaceList baseInterfaceList = o.getBaseInterfaceList();
        if (baseInterfaceList == null) {
            return Collections.emptyList();
        }
        ArrayList<CanInherit> res = new ArrayList<>();
        ArrayList<DLanguageBasicType> basicTypes = new ArrayList<>();
        for (DLanguageInterface interface_ : findChildrenOfType(baseInterfaceList.getInterfaces(), DLanguageInterface.class)) {
            basicTypes.add(interface_.getBasicType());
        }
        for (DLanguageBasicType basicType : basicTypes) {
            assert (basicType.getBasicTypeX() == null);
            assert (basicType.getTypeVector() == null);
            assert (basicType.getTypeof() == null);
            final DLanguageIdentifierList identifierList = basicType.getIdentifierList();
            final List<PsiNamedElement> definitionNodesSimple = DResolveUtil.INSTANCE.findDefinitionNode(identifierList.getProject(), getEndOfIdentifierList(identifierList));
            Set<CanInherit> definitionNodes = new HashSet<>();
            for (PsiElement node : definitionNodesSimple) {
                if (definitionNodes instanceof CanInherit)
                    definitionNodes.add((CanInherit) definitionNodes);
            }
            assert (definitionNodes.size() == 1);
            res.add((CanInherit) definitionNodes.toArray()[0]);

        }

        return res;
    }

    public static Map<String, DLanguageIdentifier> getSuperClassNames(@NotNull DLanguageInterfaceDeclaration o) {
        DLanguageBaseInterfaceList baseInterfaceList = o.getBaseInterfaceList();
        if (baseInterfaceList == null) {
            return Collections.emptyMap();
        }
        Map<String, DLanguageIdentifier> res = new HashMap<>();
        ArrayList<DLanguageBasicType> basicTypes = new ArrayList<>();
        for (DLanguageInterface interface_ : findChildrenOfType(baseInterfaceList.getInterfaces(), DLanguageInterface.class)) {
            basicTypes.add(interface_.getBasicType());
        }
        for (DLanguageBasicType basicType : basicTypes) {
            assert (basicType.getBasicTypeX() == null);
            assert (basicType.getTypeVector() == null);
            assert (basicType.getTypeof() == null);
            final DLanguageIdentifierList identifierList = basicType.getIdentifierList();
            res.put(getEndOfIdentifierList(identifierList).getName(), getEndOfIdentifierList(identifierList));
        }
        return res;
    }

    // ------------- Interface Definition ------------------ //

    // ------------- Labeled Statement ------------------ //

    @NotNull
    public static String getName(@NotNull DLanguageLabeledStatement o) {
        DLanguageLabeledStatementStub stub = o.getStub();
        if (stub != null) return StringUtil.notNullize(stub.getName());
        return o.getIdentifier().getText();
    }

    // ------------- Labeled Statement ------------------ //

    // ------------- Mixin Declaration ------------------ //

    @NotNull
    public static String getName(@NotNull DLanguageTemplateMixinDeclaration o) {
        DLanguageTemplateMixinDeclarationStub stub = o.getStub();
        if (stub != null) return StringUtil.notNullize(stub.getName());
        return o.getIdentifier().getText();//doesn't have any one name??
    }

    // ------------- Mixin Declaration ------------------ //

    // -------------- Mixin Template Resolving ------------------- //

    @Nullable
    public static String getName(@NotNull DLanguageMixinDeclaration t) {
        if (t.getTemplateInstance() != null) {
            if (t.getTemplateInstance().getIdentifier() == null)
                return null;
            return t.getTemplateInstance().getIdentifier().getName();
        }
        return null;
    }

    // -------------- Mixin Template Resolving ------------------- //

    // ------------- Var Declaration ------------------ //

    @NotNull
    public static String getName(@NotNull DLanguageDeclaratorInitializer o) {
        DLanguageDeclaratorStub stub = o.getStub();
        if (stub != null) return StringUtil.notNullize(stub.getName());

        DLanguageIdentifier result1;
        if (o.getAltDeclarator() != null) {
            final DLanguageAltDeclarator altDeclarator1 = o.getAltDeclarator();
            DLanguageIdentifier result;
            if (altDeclarator1.getIdentifier() != null) result = altDeclarator1.getIdentifier();
            else
                result = getIdentifier(altDeclarator1.getAltDeclaratorX());
            result1 = result;
        } else /*if (o.getVarDeclarator() != null) *\/ {
            final DLanguageVarDeclarator varDeclarator1 = o.getVarDeclarator();
            assert varDeclarator1 != null;
            result1 = varDeclarator1.getIdentifier();
        }
        if (result1 != null) {
            DLanguageIdentifier result;
            if (o.getAltDeclarator() != null) {
                final DLanguageAltDeclarator altDeclarator = o.getAltDeclarator();
                DLanguageIdentifier result2;
                if (altDeclarator.getIdentifier() != null) result2 = altDeclarator.getIdentifier();
                else
                    result2 = getIdentifier(altDeclarator.getAltDeclaratorX());
                result = result2;
            } else /*if (o.getVarDeclarator() != null) *\/ {
                final DLanguageVarDeclarator varDeclarator = o.getVarDeclarator();
                assert varDeclarator != null;
                result = varDeclarator.getIdentifier();
            }
            return result.getText();
        } else {
            return "not found";
        }
    }

    private static DLanguageIdentifier getIdentifier(DLanguageAltDeclaratorX altDeclaratorX) {
        if (altDeclaratorX.getIdentifier() != null)
            return altDeclaratorX.getIdentifier();
        DLanguageAltDeclarator altDeclarator = altDeclaratorX.getAltDeclarator();
        if (altDeclarator.getIdentifier() != null) return altDeclarator.getIdentifier();
        else
            return getIdentifier(altDeclarator.getAltDeclaratorX());
    }

    @NotNull
    public static PsiElement setName(@NotNull DLanguageDeclaratorInitializer o, @NotNull String newName) {
        DLanguageIdentifier result1;
        if (o.getAltDeclarator() != null) {
            final DLanguageAltDeclarator altDeclarator1 = o.getAltDeclarator();
            DLanguageIdentifier result;
            if (altDeclarator1.getIdentifier() != null) result = altDeclarator1.getIdentifier();
            else
                result = getIdentifier(altDeclarator1.getAltDeclaratorX());
            result1 = result;
        } else /*if (o.getVarDeclarator() != null) *\/ {
            final DLanguageVarDeclarator varDeclarator1 = o.getVarDeclarator();
            assert varDeclarator1 != null;
            result1 = varDeclarator1.getIdentifier();
        }
        if (result1 == null)
            return null;
        DLanguageIdentifier result;
        if (o.getAltDeclarator() != null) {
            final DLanguageAltDeclarator altDeclarator = o.getAltDeclarator();
            DLanguageIdentifier result2;
            if (altDeclarator.getIdentifier() != null) result2 = altDeclarator.getIdentifier();
            else
                result2 = getIdentifier(altDeclarator.getAltDeclaratorX());
            result = result2;
        } else /*if (o.getVarDeclarator() != null) *\/ {
            final DLanguageVarDeclarator varDeclarator = o.getVarDeclarator();
            assert varDeclarator != null;
            result = varDeclarator.getIdentifier();
        }
        result.setName(newName);
        return o;
    }

    public static boolean actuallyIsDeclaration(DLanguageDeclaratorInitializer o) {
        final DLanguageVarDeclarations parentVarDeclaration = (DLanguageVarDeclarations) o.getParent().getParent();
        if (parentVarDeclaration.getStorageClasses() != null)
            return true;
        if (parentVarDeclaration.getBasicType() != null)
            return true;
        if (o.getAltDeclarator() != null) {
            final DLanguageAltDeclarator altDeclarator = o.getAltDeclarator();
            return false;//todo the majority of the time false is correct however occasionally an alt declarator might actually be a legitimate variable declaration
        }
        if (o.getVarDeclarator() != null) {
            final DLanguageVarDeclarator varDeclarator = o.getVarDeclarator();
            if (varDeclarator.getBasicType2() != null)
                return true;
        }
        return false;//default to false.
    }

    // ------------- Var Declaration ------------------ //`

    // ------------- Auto Declaration Y ------------------ //

    @NotNull
    public static String getName(@NotNull DLanguageAutoDeclarationPart o) {
        DLanguageAutoDeclarationPartStub stub = o.getStub();
        if (stub != null) return StringUtil.notNullize(stub.getName());

        if (o.getIdentifier() != null) {
            return o.getIdentifier().getText();
        } else {
            return "not found";
        }
    }

    private static PsiElement getPrevSiblingWithoutWhitespace(PsiElement element){
        if(element == null){
            return null;
        }
        if(element.getPrevSibling() instanceof PsiWhiteSpace){
            return getPrevSiblingWithoutWhitespace(element.getPrevSibling());
        }
        return element.getPrevSibling();
    }

    public static boolean actuallyIsDeclaration(DLanguageAutoDeclarationPart o) {
        DLanguageStorageClasses storageClasses = null;
        final DLanguageDeclaration parent = PsiTreeUtil.getParentOfType(o, DLanguageDeclaration.class);
        if (parent.getParent() instanceof DLanguageDeclarationStatement) {
            storageClasses = ((DLanguageDeclarationStatement) parent.getParent()).getStorageClasses();
        }
        //todo statements
        if(parent.getParent() instanceof DLanguageDeclDef){
            if (getPrevSiblingWithoutWhitespace(parent.getParent()) != null) {
                if(getPrevSiblingWithoutWhitespace(parent.getParent()) instanceof DLanguageDeclDef){
                    if (((DLanguageDeclDef) getPrevSiblingWithoutWhitespace(parent.getParent())).getDeclaration() != null) {
                        if (((DLanguageDeclDef) getPrevSiblingWithoutWhitespace(parent.getParent())).getDeclaration().getEnumDeclaration() != null) {
                            if (((DLanguageDeclDef) getPrevSiblingWithoutWhitespace(parent.getParent())).getDeclaration().getEnumDeclaration().getAnonymousEnumDeclaration() != null) {
                                if (((DLanguageDeclDef) getPrevSiblingWithoutWhitespace(parent.getParent())).getDeclaration().getEnumDeclaration().getAnonymousEnumDeclaration().getEnumMembers() == null && ((DLanguageDeclDef) getPrevSiblingWithoutWhitespace(parent.getParent())).getDeclaration().getEnumDeclaration().getAnonymousEnumDeclaration().getEnumBaseType() == null) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        if(storageClasses == null){
            return false;
        }
        for (DLanguageStorageClass dLanguageStorageClass : storageClasses.getStorageClassList()) {
            return true;
        }
        return false;
    }

    // ------------- Auto Declaration Y ------------------ //

    // ------------ Import Declaration ----------------- //

    @NotNull
    public static String getName(@NotNull DLanguageImport o) {
        DLanguageSingleImportStub stub = o.getStub();
        if (stub != null) return StringUtil.notNullize(stub.getName());

        if (o.getModuleFullyQualifiedName().getText() != null) {
            return o.getModuleFullyQualifiedName().getText();
        } else {
            return "not found";
        }
    }

    @NotNull
    public static PsiElement setName(@NotNull DLanguageImport o, @NotNull String newName) {
        PsiElement e = DElementFactory.createDLanguageImportFromText(o.getProject(), newName);
        if (e == null) return null;
        o.replace(e);
        return o;
    }

    // ------------ Import Declaration ----------------- //

    // ------------ Catch Parameter ----------------- //

    @NotNull
    public static String getName(@NotNull DLanguageCatch o) {
        DLanguageCatchStub stub = o.getStub();
        if (stub != null) return StringUtil.notNullize(stub.getName());

        if (o.getIdentifier() != null) {
            return o.getIdentifier().getName();
        }
        return "anon_catch_parameter";

    }

    // ------------ Catch Parameter ----------------- //

    // ------------ Alias Declaration Y ----------------- //

    @NotNull
    public static String getName(@NotNull DLanguageAliasDeclaration o) {
        DLanguageAliasInitializerStub stub = o.getStub();
        if (stub != null) return StringUtil.notNullize(stub.getName());
        return o.getIdentifier().getName();
    }

    // ------------ Alias Declaration Y ----------------- //

    // ------------ Alias Declaration Single ----------------- //

    @NotNull
    public static String getName(@NotNull DLanguageAliasDeclarationSingle o) {
        DLanguageAliasDeclarationSingleStub stub = o.getStub();
        if (stub != null) return StringUtil.notNullize(stub.getName());
        if (o.getIdentifier() != null) {
            return o.getIdentifier().getName();
        }
        return getIdentifier(o.getDeclarator()).getName();
    }

    @NotNull
    public static PsiElement setName(@NotNull DLanguageAliasDeclarationSingle o, @NotNull String newName) {
        if (o.getIdentifier() != null) {
            return o.getIdentifier().setName(newName);
        } else {
            getIdentifier(o.getDeclarator()).setName(newName);
        }
        return o;
    }

    // ------------ Alias Declaration Single ----------------- //

    // ------------ Condition Variable Declaration ----------------- //

    public static DLanguageIdentifier getIdentifier(DLanguageConditionVariableDeclaration o) {
        if (o.getIdentifier() != null) {
            return o.getIdentifier();
        }
        if (o.getDeclarator() != null) {
            return getIdentifier(o.getDeclarator());
        }
        throw new IllegalStateException("this should never happen");
    }

    public static DLanguageIdentifier getIdentifier(DLanguageDeclarator declarator) {
        if (declarator.getVarDeclarator() != null) {
            return declarator.getVarDeclarator().getIdentifier();
        }
        if (declarator.getAltDeclarator() != null) {
            DLanguageAltDeclarator altDeclarator = declarator.getAltDeclarator();
            if (altDeclarator.getIdentifier() != null) return altDeclarator.getIdentifier();
            else
                return getIdentifier(altDeclarator.getAltDeclaratorX());
        }
        throw new IllegalStateException("this should never happen");
    }

    @NotNull
    public static String getName(@NotNull DLanguageConditionVariableDeclaration o) {
        DLanguageIfConditionStub stub = o.getStub();
        if (stub != null) return StringUtil.notNullize(stub.getName());
        return getIdentifier(o).getName();
    }

    // ------------ Condition Variable Declaration ----------------- //

    // ------------ Foreach Type Declaration ----------------- //

    @NotNull
    public static String getName(@NotNull DLanguageForeachType o) {
        DLanguageForeachTypeStub stub = o.getStub();
        if (stub != null) return StringUtil.notNullize(stub.getName());
        return o.getIdentifier().getName();
    }

    // ------------ Foreach Type Declaration ----------------- //

    // ------------ Var Func Declaration ----------------- //

    @NotNull
    public static String getName(@NotNull DLanguageVarFuncDeclaration o) {
        DLanguageEponymousTemplateDeclarationStub stub = o.getStub();
        if (stub != null) return StringUtil.notNullize(stub.getName());
        return o.getIdentifier().getName();
    }

    // ------------ Var Func Declaration ----------------- //

    // ------------ Parameter ----------------- //

    public static @Nullable
    DLanguageIdentifier getIdentifier(DLanguageParameter o) {
        if (o.getIdentifier() != null) {
            return o.getIdentifier();
        }
        if (o.getDeclarator() != null) {
            return getIdentifier(o.getDeclarator());
        }
        if (o.getType() != null) {
            if (o.getType().getBasicType().getIdentifierList() == null) {
                return null;
            }
            return DUtil.getEndOfIdentifierList(o.getType().getBasicType().getIdentifierList());
        }
        throw new IllegalStateException("this should never happen");
    }

    @NotNull
    public static String getName(@NotNull DLanguageParameter o) {
        DLanguageParameterStub stub = o.getStub();
        if (stub != null) return StringUtil.notNullize(stub.getName());
        if (getIdentifier(o) != null) {
            return getIdentifier(o).getName();
        }
        return "unamed parameter";
    }

    // ------------ Parameter ----------------- //

    // ------------ Template Parameter ----------------- //

    @NotNull
    private static DLanguageIdentifier getIdentifier(DLanguageTemplateTypeParameter templateTypeParameter) {
        if (templateTypeParameter.getIdentifier() != null) {
            return templateTypeParameter.getIdentifier();
        }
        return DUtil.getEndOfIdentifierList(templateTypeParameter.getTypeList().get(0).getBasicType().getIdentifierList());
    }


    public static @NotNull
    DLanguageIdentifier getIdentifier(DLanguageTemplateParameter o) {
        if (o.getTemplateAliasParameter() != null) {
            if (o.getTemplateAliasParameter().getIdentifier() != null) {
                return o.getTemplateAliasParameter().getIdentifier();
            }
            return DUtil.getEndOfIdentifierList(o.getTemplateAliasParameter().getTypeList().get(0).getBasicType().getIdentifierList());
        }
        if (o.getTemplateThisParameter() != null) {
            return getIdentifier(o.getTemplateThisParameter().getTemplateTypeParameter());
        }
        if (o.getTemplateTupleParameter() != null) {
            return o.getTemplateTupleParameter().getIdentifier();
        }
        if (o.getTemplateTypeParameter() != null) {
            return getIdentifier(o.getTemplateTypeParameter());
        }
//        if (o.getTemplateValueParameter() != null) {
//            return getIdentifier(o.getTemplateValueParameter().getDeclarator());
//        }
        throw new IllegalStateException("this should never happen");
    }

    @NotNull
    public static String getName(@NotNull DLanguageTemplateParameter o) {
        DLanguageTemplateParameterStub stub = o.getStub();
        if (stub != null) return StringUtil.notNullize(stub.getName());
        return getIdentifier(o).getName();
    }

    // ------------ Template Parameter ----------------- //

    // ------------ Enum Member ----------------- //

    @NotNull
    public static String getName(@NotNull DLanguageEnumMember o) {
        DLanguageEnumMemberStub stub = o.getStub();
        if (stub != null) return StringUtil.notNullize(stub.getName());
        return o.getIdentifier().getName();
    }

    // ------------ Enum Member ----------------- //

    // -------------------- Visibility --------------------- //

    //        PsiElement parent = psiElement.getParent();
//        while (true) {
//            //default to public
//            if (containerType.isInstance(parent)) {
//                return visibility == public_;
//            }
//            // check that named element isn't explicitly marked some visibilty, eg. private gh();
//            if (parent instanceof DLanguageDeclDef && ((DLanguageDeclDef) parent).getAttributeSpecifier() != null) {
//                final DLanguageAttributeSpecifier attribute = ((DLanguageDeclDef) parent).getAttributeSpecifier();
//                if (attribute.getAttribute().getProtectionAttribute() != null) {
//                    try {
//                        return protectionToVisibilty(attribute.getAttribute().getProtectionAttribute()) == (visibility);
//                    } catch (IllegalArgumentException e) {
//                        return false;//todo
//                    }
//                }
//            }
//            if (parent instanceof DLanguageDeclDef && ((DLanguageDeclDefs) parent).getDeclDef().getAttributeSpecifier() != null) {
//                final DLanguageAttributeSpecifier attribute = ((DLanguageDeclDefs) parent).getDeclDef().getAttributeSpecifier();
//                if (attribute.getAttribute().getProtectionAttribute() != null && attribute.getOpColon() != null) {
//                    try {
//                        return protectionToVisibilty(attribute.getAttribute().getProtectionAttribute()) == (visibility);
//                    } catch (IllegalArgumentException e) {
//                        return false;
//                    }
//                }
//            }
//            if(parent instanceof DLanguageDeclDef)
//                parent = parent.getPrevSibling();
//            else
//                parent = parent.getParent();
////            //check for public: or private: or protected:
////            if (parent instanceof DLanguageDeclDefs && ((DLanguageDeclDefs) parent).getDeclDef().getAttributeSpecifier() != null) {
////                final DLanguageAttributeSpecifier attribute = ((DLanguageDeclDefs) parent).getDeclDef().getAttributeSpecifier();
////                if (attribute.getAttribute().getProtectionAttribute() != null && attribute.getOpColon() != null) {
////                    try {
////                        return protectionToVisibilty(attribute.getAttribute().getProtectionAttribute()) == (visibility);
////                    } catch (IllegalArgumentException e) {
////                        return false;
////                    }
////                }
////            }
////            parent = parent.getParent();
////        }
//    }

    // -------------------- Visibility --------------------- //

    // -------------------- Scope processing --------------- //

    /**
     * takes the elements declared in the given psi and passes them to the scope processor via the execute method. The scope processor will return false if it has found what it is "looking for". Note that certain declarations processors will not process child block statement/aggregate bodies since those have their own processor.
     * Some of the process declarations methods may return early while others will search throught the entire scope
     *
     * @param element
     * @param processor
     * @param state
     * @param lastParent todo make use of this do determine if scope statements/decldefs contained inside a element should be processed or not.
     * @param place
     * @return
     *\/
    public static boolean processDeclarations(DLanguageDeclDefs element,
                                              @NotNull PsiScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              PsiElement lastParent,
                                              @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(element, processor, state, lastParent, place);
    }

    public static boolean processDeclarations(DLanguageFunctionLiteral element,
                                              @NotNull PsiScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              PsiElement lastParent,
                                              @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(element, processor, state, lastParent, place);
    }

    public static boolean processDeclarations(DLanguageLambda element,
                                              @NotNull PsiScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              PsiElement lastParent,
                                              @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(element, processor, state, lastParent, place);
    }

    public static boolean processDeclarations(DLanguageLabeledStatement element,
                                              @NotNull PsiScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              PsiElement lastParent,
                                              @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(element, processor, state, lastParent, place);
    }

    public static boolean processDeclarations(DLanguageForeachStatement element,
                                              @NotNull PsiScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              PsiElement lastParent,
                                              @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(element, processor, state, lastParent, place);
    }

    public static boolean processDeclarations(DLanguageConstructor element,
                                              @NotNull PsiScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              PsiElement lastParent,
                                              @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(element, processor, state, lastParent, place);
    }

    public static boolean processDeclarations(DLanguageFuncDeclaration element,
                                              @NotNull PsiScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              PsiElement lastParent,
                                              @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(element, processor, state, lastParent, place);
    }

    public static boolean processDeclarations(DLanguageWhileStatement element,
                                              @NotNull PsiScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              PsiElement lastParent,
                                              @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(element, processor, state, lastParent, place);
    }

    public static boolean processDeclarations(DLanguageForStatement element,
                                              @NotNull PsiScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              PsiElement lastParent,
                                              @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(element, processor, state, lastParent, place);
    }

    public static boolean processDeclarations(DLanguageDoStatement element,
                                              @NotNull PsiScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              PsiElement lastParent,
                                              @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(element, processor, state, lastParent, place);
    }

    public static boolean processDeclarations(DLanguageIfStatement element,
                                              @NotNull PsiScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              PsiElement lastParent,
                                              @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(element, processor, state, lastParent, place);
    }

    public static boolean processDeclarations(DLanguageStatementList element,
                                              @NotNull PsiScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              PsiElement lastParent,
                                              @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(element, processor, state, lastParent, place);
    }

    public static boolean processDeclarations(DLanguageSwitchStatement element,
                                              @NotNull PsiScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              PsiElement lastParent,
                                              @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(element, processor, state, lastParent, place);
    }

    public static boolean processDeclarations(DLanguageFinalSwitchStatement element,
                                              @NotNull PsiScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              PsiElement lastParent,
                                              @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(element, processor, state, lastParent, place);
    }

    public static boolean processDeclarations(DLanguageWithStatement element,
                                              @NotNull PsiScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              PsiElement lastParent,
                                              @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(element, processor, state, lastParent, place);
    }

    public static boolean processDeclarations(DLanguageSynchronizedStatement element,
                                              @NotNull PsiScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              PsiElement lastParent,
                                              @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(element, processor, state, lastParent, place);
    }

    public static boolean processDeclarations(DLanguageCatch element,
                                              @NotNull PsiScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              PsiElement lastParent,
                                              @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(element, processor, state, lastParent, place);
    }

    public static boolean processDeclarations(DLanguageVarFuncDeclaration element,
                                              @NotNull PsiScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              PsiElement lastParent,
                                              @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(element, processor, state, lastParent, place);
    }

    public static boolean processDeclarations(DLanguageForeachRangeStatement element,
                                              @NotNull PsiScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              PsiElement lastParent,
                                              @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(element, processor, state, lastParent, place);
    }

    public static boolean processDeclarations(DLanguageAliasDeclarationSingle element,
                                              @NotNull PsiScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              PsiElement lastParent,
                                              @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(element, processor, state, lastParent, place);
    }

    public static boolean processDeclarations(DLanguageAliasDeclaration element,
                                              @NotNull PsiScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              PsiElement lastParent,
                                              @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(element, processor, state, lastParent, place);
    }

    public static boolean processDeclarations(DLanguageAutoDeclarationPart element,
                                              @NotNull PsiScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              PsiElement lastParent,
                                              @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(element, processor, state, lastParent, place);
    }

    public static boolean processDeclarations(DLanguageDeclaratorInitializer element,
                                              @NotNull PsiScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              PsiElement lastParent,
                                              @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(element, processor, state, lastParent, place);
    }

    public static boolean processDeclarations(DLanguageParameterList element,
                                              @NotNull PsiScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              PsiElement lastParent,
                                              @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(element, processor, state, lastParent, place);
    }

    public static boolean processDeclarations(DLanguageParameters element,
                                              @NotNull PsiScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              PsiElement lastParent,
                                              @NotNull PsiElement place) {
        if (element.getParameterList() != null) {
            return ScopeProcessorImpl.INSTANCE.processDeclarations(element.getParameterList(), processor, state, lastParent, place);
        }
        return true;
    }

    public static boolean processDeclarations(DLanguageConditionalStatement element,
                                              @NotNull PsiScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              PsiElement lastParent,
                                              @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(element, processor, state, lastParent, place);
    }

    public static boolean processDeclarations(DLanguageScopeStatement element,
                                              @NotNull PsiScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              PsiElement lastParent,
                                              @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(element, processor, state, lastParent, place);
    }

    public static boolean processDeclarations(DLanguageEnumMembers element,
                                              @NotNull PsiScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              PsiElement lastParent,
                                              @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(element, processor, state, lastParent, place);
    }

    public static boolean processDeclarations(DLanguageDeclDef def, @NotNull PsiScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              PsiElement lastParent,
                                              @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(def, processor, state, lastParent, place);
    }


    public static boolean processDeclarations(DLanguageConditionalDeclaration element,
                                              @NotNull PsiScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              PsiElement lastParent,
                                              @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(element, processor, state, lastParent, place);
    }

    public static boolean processDeclarations(DLanguageClassDeclaration element,
                                              @NotNull PsiScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              PsiElement lastParent,
                                              @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(element, processor, state, lastParent, place);
    }

    public static boolean processDeclarations(DLanguageTemplateDeclaration element,
                                              @NotNull PsiScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              PsiElement lastParent,
                                              @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(element, processor, state, lastParent, place);
    }

    public static boolean processDeclarations(DLanguageUnionDeclaration element,
                                              @NotNull PsiScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              PsiElement lastParent,
                                              @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(element, processor, state, lastParent, place);
    }

    public static boolean processDeclarations(DLanguageStatement element,
                                              @NotNull PsiScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              PsiElement lastParent,
                                              @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(element, processor, state, lastParent, place);
    }

    public static boolean processDeclarations(DLanguageStructDeclaration element,
                                              @NotNull PsiScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              PsiElement lastParent,
                                              @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(element, processor, state, lastParent, place);
    }

    public static boolean processDeclarations(DLanguageTemplateMixinDeclaration element,
                                              @NotNull PsiScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              PsiElement lastParent,
                                              @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(element, processor, state, lastParent, place);
    }
    // -------------------- Scope processing --------------- //


    // -------------------- Misc --------------------- //
    public static String getFullName(DNamedElement e) {
        if (e == null || e.getName() == null)
            return "";
        if (e instanceof DLanguageFile && e.getName() != null)
            return getFullName(e.getParentContainer()) + "." + e.getName().replace(".d", "");
        return getFullName(e.getParentContainer()) + "." + e.getName();
    }
    // -------------------- Misc --------------------- //
*/
}

