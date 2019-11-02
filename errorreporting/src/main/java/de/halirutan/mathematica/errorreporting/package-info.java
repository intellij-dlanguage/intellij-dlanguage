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

/**
 * <p>
 * Reporting user errors by creating issues on a dedicated GitHub repository. First off, honor to whom honor is due:
 * The implementation here would not have been possible with the help of several people that provided code to show how
 * an auto-reporter can be implemented into IDEA. Please see
 * </p>
 * <ul>
 * <li>@see <a href="https://android.googlesource.com/platform/tools/adt/idea/+/master/android/src/com/android/tools/idea/diagnostics/error/ErrorReporter.java">the android implementation</a></li>
 * <li>@see <a href="http://devnet.jetbrains.com/message/5526206">a post on jetbrains</a></li>
 * </ul>
 * <p>
 * Furthermore, an earlier implementation of Jon Akhtar (https://github.com/sylvanaar) gave me the idea of finding duplicates,
 * so that there is only one issue per unique stack trace. With many users, it is very likely that several of them report
 * issue and your issue list grows infinitely.
 * </p>
 * <p>
 * This implementation collects several information about the running idea, the stack trace of the exception and the
 * details provided by the user. It creates an issue on a GitHub repo for this, including the hash of the stack trace
 * in the title. When an error is reported, it searches through existing issues and finds the first duplicate. If there
 * is no duplicate, then it will create a new issue. If there is a duplicate and the user provided further information,
 * then the information of the user will be created as new comment on the existing issue. The user is, of course, free
 * to use GitHub markdown in his description.
 * </p>
 * <p>
 * The information about the running IDEA are collected in {@link de.halirutan.mathematica.errorreporting.IdeaInformationProxy}.
 * Creating a valid GitHub issue and the communication with GitHub is done in {@link de.halirutan.mathematica.errorreporting.AnonymousFeedback}
 * which is run as background task {@link de.halirutan.mathematica.errorreporting.AnonymousFeedbackTask}.
 * The main class that is also registered in the plugin.xml and starts the whole procedure is {@link de.halirutan.mathematica.errorreporting.GitHubErrorReporter}.
 * </p>
 * <p>
 * You might consider using a subset of this functionality by not using the duplicate finding and commenting feature.
 * The advantage of this is, that you should be able to implement this without having a real fake user in the background
 * that reports for you. Creating issues can be done by anyone, commenting and labeling not. For commenting on an existing issue,
 * you need a higher level of access to GitHub and your requests need to be backed by a user/password or token. The problem
 * here is clearly that you need to give the plugin access to this token. No matter how you secure it, if the code is
 * open access it is not hard to decipher your token. Therefore, always use tokens with the lowest possible access level
 * (in this case, access to public repositories only) if you need to.
 * </p>
 */
package de.halirutan.mathematica.errorreporting;
