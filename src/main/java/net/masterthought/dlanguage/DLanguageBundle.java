package net.masterthought.dlanguage;


import com.intellij.CommonBundle;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.PropertyKey;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.ResourceBundle;

public class DLanguageBundle {
        private static Reference<ResourceBundle> dLangBundle;

        @NonNls private static final String BUNDLE_ID = "messages.Dlanguage";

        private DLanguageBundle() {
        }

        public static String message(@PropertyKey(resourceBundle = BUNDLE_ID)String key, Object... params) {
            return CommonBundle.message(getBundle(), key, params);
        }

        private static ResourceBundle getBundle() {
            ResourceBundle bundle = null;
            if (dLangBundle != null) bundle = dLangBundle.get();
            if (bundle == null) {
                bundle = ResourceBundle.getBundle(BUNDLE_ID);
                dLangBundle = new SoftReference<ResourceBundle>(bundle);
            }
            return bundle;
        }
}
