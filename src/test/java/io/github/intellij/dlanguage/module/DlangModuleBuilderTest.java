package io.github.intellij.dlanguage.module;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.projectRoots.SdkType;
import com.intellij.openapi.projectRoots.impl.UnknownSdkType;
import com.intellij.openapi.roots.*;
import com.intellij.openapi.util.SystemInfo;
import io.github.intellij.dlanguage.DlangSdkType;
import io.github.intellij.dlanguage.LightDlangTestCase;
import io.github.intellij.dlanguage.sdk.DlangDmdSdkType;
import io.github.intellij.dlanguage.sdk.DlangGdcSdkType;
import io.github.intellij.dlanguage.sdk.DlangLdcSdkType;
import org.junit.Test;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class DlangModuleBuilderTest extends LightDlangTestCase {

    private DlangModuleBuilder moduleBuilder;

    public void setUp() throws Exception {
        super.setUp();

        this.moduleBuilder = new DlangModuleBuilder();
    }

    public void tearDown() throws Exception {
        this.moduleBuilder = null;

        super.tearDown();
    }

    @Test
    public void testSetupRootModel() throws ConfigurationException {
        final ModifiableRootModel rootModel = mock(ModifiableRootModel.class);

        when(rootModel.getProject()).thenReturn(getProject());

        this.moduleBuilder.setupRootModel(rootModel);

        verify(rootModel, times(1)).setSdk(any());
        verify(rootModel, times(1)).inheritSdk();
    }

    @Test
    public void testGetSourcePaths() {
        final List<String> sourcePaths = this.moduleBuilder.getSourcePaths();

        assertNotNull(sourcePaths);
        assertEquals(1, sourcePaths.size());
        assertEquals(Paths.get("null", "source").toString(), sourcePaths.get(0)); // todo: 'null/source' should probably not be the result
    }

    @Test
    public void testIsSuitableSdkType() {
        assertTrue(this.moduleBuilder.isSuitableSdkType(SdkType.findInstance(DlangDmdSdkType.class)));
        assertTrue(this.moduleBuilder.isSuitableSdkType(SdkType.findInstance(DlangLdcSdkType.class)));
        if(SystemInfo.isUnix) {
            // can only check for GDC on Unix systems
            assertTrue(this.moduleBuilder.isSuitableSdkType(SdkType.findInstance(DlangGdcSdkType.class)));
        }

        assertTrue(this.moduleBuilder.isSuitableSdkType(DlangSdkType.getInstance()));

        assertFalse(this.moduleBuilder.isSuitableSdkType(UnknownSdkType.getInstance("nonsense")));

        // make sure any available SDK's that are not of type DlangSdkType (SimpleJavaSdkType & JavaSDK) assert as false
        Arrays.stream(SdkType.getAllTypes())
            .filter(sdkType -> !DlangSdkType.class.isAssignableFrom(sdkType.getClass()))
            .forEach(sdkType -> assertFalse(this.moduleBuilder.isSuitableSdkType(sdkType)));

    }
}
