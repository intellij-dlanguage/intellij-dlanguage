package net.masterthought.dlanguage.psi.interfaces;

/**
 * Created by francis on 3/12/2017.
 */
public class Type {
//    List<DLanguageType> types = new ArrayList<>(); // should normally be only one element
//    List<DLanguageBasicType2> basicTypes2 = new ArrayList<>();
//    List<DLanguageStorageClasses> storageClasses = new ArrayList<>();
//    List<DLanguageBasicType> basicTypes = new ArrayList<>();
//
//    public Type(List<DLanguageType> types, List<DLanguageBasicType2> basicTypes2, List<DLanguageStorageClasses> storageClasses, List<DLanguageBasicType> basicTypes) {
//        this.types = types;
//        this.basicTypes2 = basicTypes2;
//        this.storageClasses = storageClasses;
//        this.basicTypes = basicTypes;
//    }
//
//    /**
//     * todo completely broken for complicated declarations
//     *
//     * @param initializer
//     */
//    public Type(DLanguageDeclaratorInitializer initializer) {
//        types = PsiTreeUtil.getChildrenOfTypeAsList(initializer.getParent().getParent(), DLanguageType.class);
//        basicTypes2 = PsiTreeUtil.getChildrenOfTypeAsList(initializer.getParent().getParent(), DLanguageBasicType2.class);
//        storageClasses = PsiTreeUtil.getChildrenOfTypeAsList(initializer.getParent().getParent(), DLanguageStorageClasses.class);
//        basicTypes = PsiTreeUtil.getChildrenOfTypeAsList(initializer.getParent().getParent(), DLanguageBasicType.class);
//        final DLanguageAltDeclarator altDeclarator = initializer.getAltDeclarator();
//        final DLanguageVarDeclarator varDeclarator = initializer.getVarDeclarator();
//        if(altDeclarator != null){
//            final DLanguageAltDeclaratorX altDeclaratorX = altDeclarator.getAltDeclaratorX();
//            final DLanguageAltDeclaratorSuffixes altDeclaratorSuffixes = altDeclarator.getAltDeclaratorSuffixes();
//            final DLanguageAltFuncDeclaratorSuffix altFuncDeclaratorSuffix = altDeclarator.getAltFuncDeclaratorSuffix();
//            final DLanguageBasicType2 basicType2 = altDeclarator.getBasicType2();
//            if(altDeclaratorX != null){
//                if(altDeclaratorX.getAltDeclarator() != null)
//                    throw new NotImplementedException();//todo
//                final DLanguageBasicType2 basicType21 = altDeclaratorX.getBasicType2();
//            }
//            if(altDeclaratorSuffixes != null){
//                altDeclaratorSuffixes.
//            }
//        }
//        else if (varDeclarator != null){
//
//        }
//    }

    /**
     * if the type is one identifier eg. Type foo = null;, then return that identifier, instead return null
     *
     * @return
     */
//    public DLanguageIdentifier isOneIdentifier() {
//        List<DLanguageIdentifier> identifiers = new ArrayList<>();
//        for (PsiElement element : types) {
//            identifiers.addAll(PsiTreeUtil.findChildrenOfType(element, DLanguageIdentifier.class));
//        }
//        for (PsiElement element : basicTypes2) {
//            identifiers.addAll(PsiTreeUtil.findChildrenOfType(element, DLanguageIdentifier.class));
//        }
//        for (PsiElement element : storageClasses) {
//            identifiers.addAll(PsiTreeUtil.findChildrenOfType(element, DLanguageIdentifier.class));
//        }
//        for (PsiElement element : basicTypes) {
//            identifiers.addAll(PsiTreeUtil.findChildrenOfType(element, DLanguageIdentifier.class));
//        }
//        if (identifiers.size() == 1)
//            return identifiers.get(0);
//        return null;
//    }
}
