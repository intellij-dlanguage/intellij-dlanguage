package io.github.intellij.dub.project.wizard

import com.intellij.openapi.observable.properties.GraphProperty
import com.intellij.openapi.projectRoots.Sdk
import io.github.intellij.dub.packageConfig.DubDsl

interface DubNewProjectWizardData {

    val sdkProperty: GraphProperty<Sdk?>
    val dubDslProperty: GraphProperty<DubDsl>
    val dubProjectTypeProperty: GraphProperty<DubNewProjectWizardStep.DubProjectType>

    var sdk: Sdk?

    var dubDsl: DubDsl
    var dubProjectType: DubNewProjectWizardStep.DubProjectType
}
