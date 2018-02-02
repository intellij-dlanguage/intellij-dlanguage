package io.github.intellij.dlanguage.project;

import com.intellij.testFramework.LightPlatformTestCase;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author singingbush
 */
public class DubConfigurationParserTest extends LightPlatformTestCase {

    @Override
    public void setUp() throws Exception {
        super.setUp();
    }

    @Override
    public void tearDown() throws Exception {
        final String baseDir = ourProject.getBaseDir().getCanonicalPath();
        if(baseDir != null && !baseDir.isEmpty()) {
            final File directory = new File(Paths.get(baseDir).toUri());
            if(directory.isDirectory()) {
                final File[] files = directory.listFiles();
                if(files != null && files.length > 0) {
                    //noinspection ResultOfMethodCallIgnored
                    Stream.of(files).forEach(File::delete);
                }
            }
        }
        super.tearDown();
    }

    public void testDubParserWithVibed() throws Exception {
        loadVibedTestFiles();

        System.out.println(" ----------------" + ourProject.getProjectFilePath());
        final DubConfigurationParser dubConfigurationParser = new DubConfigurationParser(ourProject,
            "dub",
            false);

        final Optional<DubPackage> optRootPackage = dubConfigurationParser.getDubPackage();
        assertTrue("The root package should have been parsed", optRootPackage.isPresent());
        final DubPackage rootPackage = optRootPackage.get();
        assertEquals("The root dub.sdl for vibe-d has 13 dependencies", 13, rootPackage.getDependencies().size());

        final List<DubPackage> allDubPackages = dubConfigurationParser.getDubPackageDependencies();
        assertNotEmpty(allDubPackages);
        assertEquals("there should be 21 dependencies", 21, allDubPackages.size());

//        final Map<String, List<String>> targets = dubConfigurationParser.getTargets();
//        assertNotEmpty(targets.keySet());

        final TreeNode packageTree = dubConfigurationParser.getPackageTree();
        assertEquals(13, packageTree.getChildCount());

        final Enumeration enumeration = packageTree.children();
        while (enumeration.hasMoreElements()) {
            final DefaultMutableTreeNode node = (DefaultMutableTreeNode) enumeration.nextElement();
            final DubPackage dubPackage = DubPackage.class.cast(node.getUserObject());

            if ("vibe-d:utils".equals(dubPackage.getName())) {
                assertTrue(dubPackage.getName(), dubPackage.getDependencies().isEmpty()); // utils module has no dependencies
            } else {
                assertFalse(dubPackage.getName(), dubPackage.getDependencies().isEmpty());
            }
        }
    }

    public void testDubParserWithHibernated() throws Exception {
        loadTestFile("dub/hibernated/dub.json", "dub.json");

        final DubConfigurationParser dubConfigurationParser = new DubConfigurationParser(ourProject,
            "dub",
            false);

        final Optional<DubPackage> optRootPackage = dubConfigurationParser.getDubPackage();
        assertTrue("The root package should have been parsed", optRootPackage.isPresent());
        final DubPackage rootPackage = optRootPackage.get();
        assertEquals("The root dub.json for hibernated has 1 dependency", 1, rootPackage.getDependencies().size());
    }

    private void loadVibedTestFiles() throws IOException, URISyntaxException {
        // I'm using the dub.sdl from Vibe-d as test data as it's a big multi module project
        loadTestFile("dub/vibe-d/dub.sdl", "dub.sdl");
        loadTestFile("dub/vibe-d/core/dub.sdl", "core/dub.sdl");
        loadTestFile("dub/vibe-d/crypto/dub.sdl", "crypto/dub.sdl");
        loadTestFile("dub/vibe-d/data/dub.sdl", "data/dub.sdl");
        loadTestFile("dub/vibe-d/diet/dub.sdl", "diet/dub.sdl");
        loadTestFile("dub/vibe-d/http/dub.sdl", "http/dub.sdl");
        loadTestFile("dub/vibe-d/inet/dub.sdl", "inet/dub.sdl");
        loadTestFile("dub/vibe-d/mail/dub.sdl", "mail/dub.sdl");
        loadTestFile("dub/vibe-d/mongodb/dub.sdl", "mongodb/dub.sdl");
        loadTestFile("dub/vibe-d/redis/dub.sdl", "redis/dub.sdl");
        loadTestFile("dub/vibe-d/stream/dub.sdl", "stream/dub.sdl");
        loadTestFile("dub/vibe-d/textfilter/dub.sdl", "textfilter/dub.sdl");
        loadTestFile("dub/vibe-d/utils/dub.sdl", "utils/dub.sdl");
        loadTestFile("dub/vibe-d/web/dub.sdl", "web/dub.sdl");
    }

    private void loadTestFile(final String resourceLocation, final String destination) throws URISyntaxException, IOException {
        final URL testResource = this.getClass().getClassLoader().getResource(resourceLocation);
        final Path resourcePath = Paths.get(testResource.toURI());
        final String baseDir = ourProject.getBaseDir().getCanonicalPath();
        if (destination.contains("/")) {
            final String[] path = destination.split("/");
            final File subFolder = new File(baseDir, path[0]);
            subFolder.mkdir();
            Files.copy(resourcePath, Paths.get(baseDir, path[0], path[1]), StandardCopyOption.REPLACE_EXISTING);
            new File(subFolder.getCanonicalPath(), "source").mkdir(); // they need source folder
            new File(subFolder.getCanonicalPath(), "source/dummy.d").createNewFile(); // with at least 1 D file
        } else {
            Files.copy(resourcePath, Paths.get(baseDir, destination), StandardCopyOption.REPLACE_EXISTING);
            new File(baseDir, "source").mkdir(); // needs source folder
            new File(baseDir, "lib").mkdir(); // also the root dub file references a lib directory
        }
    }

}
