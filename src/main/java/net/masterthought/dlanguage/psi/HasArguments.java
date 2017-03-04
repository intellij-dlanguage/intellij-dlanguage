package net.masterthought.dlanguage.psi;

import java.util.List;

/**
 * Created by francis on 3/4/2017.
 * Use HasTemplateArguments for accessing template arguments
 */
public interface HasArguments {
    List<DLanguageParameter> getArguments();
}
