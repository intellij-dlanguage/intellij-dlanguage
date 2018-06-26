/*
 * (These files where modified from: https://bitbucket.org/spencercw/ideagdb/src
 * Original Copyright:
 * Copyright (c) 2013 Chris Spencer <spencercw@gmail.com>
 *
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
 * THE SOFTWARE.)
 */

package uk.co.cwspencer.ideagdb.debug.utils;

import com.intellij.openapi.util.SystemInfo;

public class SdkUtil {
    public static boolean isHostOsWindows() {
        return SystemInfo.isWindows;
    }

    public static boolean isWslPath(String posixPath) { return SystemInfo.isWindows && posixPath.startsWith("/mnt/"); }

    public static String translateWslPosixToWindowsPath(String posixPath) {
        return posixPath.substring(5,6) + ":/" + posixPath.substring(7);
    }

    public static String translateWslWindowsToPosixPath(String windowsPath) {
        return "/mnt/" + windowsPath.substring(0,1).toLowerCase() + windowsPath.substring(2).replace("\\", "/");
    }
}
