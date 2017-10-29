/*
 * Copyright (c) 2017 Patrick Scheibe
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package de.halirutan.mathematica.errorreporting;

import com.intellij.openapi.diagnostic.SubmittedReportInfo;
import com.intellij.openapi.diagnostic.SubmittedReportInfo.SubmissionStatus;
import net.masterthought.dlanguage.DLanguageBundle;
import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.Label;
import org.eclipse.egit.github.core.RepositoryId;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.client.PageIterator;
import org.eclipse.egit.github.core.service.IssueService;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

/**
 * Provides functionality to create and send GitHub issues when an exception is thrown by a plugin.
 */
class AnonymousFeedback {
    //    private final static String testingToken = "1c963385f559c4e9cb6c2031feada99d3d9b0f2c";
    private final static String testingToken = "e23c999b968c1510b6d9b09659fb9902f2e132b7";
    //    private final static String tokenFile = "de/halirutan/mathematica/errorreporting/ScrambledToken.bin";
//    private final static String gitRepoUser = "Mathematica-IntelliJ-Plugin";
    private final static String gitRepoUrl = "https://github.com/Auto-Generated-Error-Reporter/DIntellijPluginAutoGeneratedIssues";
//    private final static String gitRepo = "Auto-generated-issues-for-the-Mathematica-Plugin";

    private final static String issueLabel = "auto-generated";

    private AnonymousFeedback() {
    }

    /**
     * Makes a connection to GitHub. Checks if there is an issue that is a duplicate and based on this, creates either a
     * new issue or comments on the duplicate (if the user provided additional information).
     *
     * @param environmentDetails Information collected by {@link IdeaInformationProxy}
     * @return The report info that is then used in {@link GitHubErrorReporter} to show the user a balloon with the link
     * of the created issue.
     */
    static SubmittedReportInfo sendFeedback(final LinkedHashMap<String, String> environmentDetails) {

        final SubmittedReportInfo result;
        try {
/*
            final URL resource = AnonymousFeedback.class.getClassLoader().getResource(tokenFile);
            if (resource == null) {
                throw new IOException("Could not decrypt access token");
            }
            final String gitAccessToken = GitHubAccessTokenScrambler.decrypt(resource.getFile());
*/
            final GitHubClient client = new GitHubClient();
            client.setOAuth2Token(testingToken);
//            client.setOAuth2Token(gitAccessToken);
            final RepositoryId repoID = RepositoryId.createFromUrl(gitRepoUrl);
            final IssueService issueService = new IssueService(client);

            final String errorDescription = environmentDetails.get("error.description");

            Issue newGibHubIssue = createNewGibHubIssue(environmentDetails);
//            Issue duplicate = findFirstDuplicate(newGibHubIssue.getTitle(), issueService, repoID);
            final boolean isNewIssue = true;
            /*if (duplicate != null) {
                errorDescription = errorDescription == null ? "Me too!" : errorDescription;
                issueService.createComment(repoID, duplicate.getNumber(), errorDescription);
                newGibHubIssue = duplicate;
                isNewIssue = false;
            } else {
                newGibHubIssue = issueService.createIssue(repoID, newGibHubIssue);
            }*/
            newGibHubIssue = issueService.createIssue(repoID, newGibHubIssue);

            final long id = newGibHubIssue.getNumber();
            final String htmlUrl = newGibHubIssue.getHtmlUrl();
            final String message = DLanguageBundle.INSTANCE.message("git.issue.text", htmlUrl, id) + " Error submitted at " + htmlUrl;
            result = new SubmittedReportInfo(htmlUrl, message, SubmissionStatus.NEW_ISSUE);
            return result;
        } catch (final Exception e) {
            e.printStackTrace();
            return new SubmittedReportInfo(null, DLanguageBundle.INSTANCE.message("report.error.connection.failure"), SubmissionStatus.FAILED);
        }
    }

    /**
     * Collects all issues on the repo and finds the first duplicate that has the same title. For this to work, the title
     * contains the hash of the stack trace.
     *
     * @param uniqueTitle Title of the newly created issue. Since for auto-reported issues the title is always the same,
     *                    it includes the hash of the stack trace. The title is used so that I don't have to match
     *                    something in the whole body of the issue.
     * @param service     Issue-service of the GitHub lib that lets you access all issues
     * @param repo        The repository that should be used
     * @return The duplicate if one is found or null
     * @throws IOException Problems when connecting to GitHub
     */
    @Nullable
    private static Issue findFirstDuplicate(final String uniqueTitle, final IssueService service, final RepositoryId repo) throws IOException {
        final Map<String, String> searchParameters = new HashMap<>(2);
        searchParameters.put(IssueService.FILTER_STATE, IssueService.STATE_OPEN);
        final PageIterator<Issue> pages = service.pageIssues(repo, searchParameters);
        for (final Collection<Issue> page : pages) {
            for (final Issue issue : page) {
                if (issue.getTitle().equals(uniqueTitle)) {
                    return issue;
                }
            }
        }
        return null;
    }

    /**
     * Turns collected information of an error into a new (offline) GitHub issue
     *
     * @param details A map of the information. Note that I remove items from there when they should not go in the issue
     *                body as well. When creating the body, all remaining items are iterated.
     * @return The new issue
     */
    private static Issue createNewGibHubIssue(final LinkedHashMap<String, String> details) {
        String errorMessage = details.get("error.message");
        if (errorMessage == null || errorMessage.isEmpty()) {
            errorMessage = "Unspecified error";
        }
        details.remove("error.message");

        String errorHash = details.get("error.hash");
        if (errorHash == null) {
            errorHash = "";
        }
        details.remove("error.hash");

        final Issue gitHubIssue = new Issue();
        final String body = generateGitHubIssueBody(details);
        gitHubIssue.setTitle(DLanguageBundle.INSTANCE.message("git.issue.title", errorHash, errorMessage));
        gitHubIssue.setBody(body);
        final Label label = new Label();
        label.setName(issueLabel);
        gitHubIssue.setLabels(Collections.singletonList(label));
        return gitHubIssue;
    }

    /**
     * Creates the body of the GitHub issue. It will contain information about the system, details provided by the user
     * and the full stack trace. Everything is formatted using markdown.
     *
     * @param details Details provided by {@link IdeaInformationProxy}
     * @return A markdown string representing the GitHub issue body.
     */
    private static String generateGitHubIssueBody(final LinkedHashMap<String, String> details) {
        String errorDescription = details.get("error.description");
        if (errorDescription == null) {
            errorDescription = "";
        }
        details.remove("error.description");


        String stackTrace = details.get("error.stacktrace");
        if (stackTrace == null || stackTrace.isEmpty()) {
            stackTrace = "invalid stacktrace";
        }
        details.remove("error.stacktrace");

        final StringBuilder result = new StringBuilder();

        if (!errorDescription.isEmpty()) {
            result.append(errorDescription);
            result.append("\n\n----------------------\n\n");
        }

        for (final Entry<String, String> entry : details.entrySet()) {
            result.append("- ");
            result.append(entry.getKey());
            result.append(": ");
            result.append(entry.getValue());
            result.append("\n");
        }

        result.append("\n```\n");
        result.append(stackTrace);
        result.append("\n```\n");

        return result.toString();
    }
}
