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

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Provides functionality to encode and decode secret tokens to make them not directly readable. Let me be clear:
 * THIS IS THE OPPOSITE OF SECURITY!
 *
 * @author patrick (20.06.17).
 */
public class GitHubAccessTokenScrambler {

    private static final String myInitVector = "RandomInitVector";
    private static final String myKey = "GitHubErrorToken";

    public static void main(final String[] args) {
        if (args.length != 2) {
            return;
        }
        final String horse = args[0];
        final String outputFile = args[1];
        try {
            final String e = encrypt(horse);
            final ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(outputFile));
            o.writeObject(e);
            o.close();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    private static String encrypt(final String value) {
        try {
            final IvParameterSpec iv = new IvParameterSpec(myInitVector.getBytes("UTF-8"));
            final SecretKeySpec keySpec = new SecretKeySpec(myKey.getBytes("UTF-8"), "AES");

            final Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);

            final byte[] encrypted = cipher.doFinal(value.getBytes());
            return Base64.encodeBase64String(encrypted);
        } catch (final Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    static String decrypt(final String file) throws Exception {
        final String in;
        final ObjectInputStream o = new ObjectInputStream(new FileInputStream(file));
        in = (String) o.readObject();
        final IvParameterSpec iv = new IvParameterSpec(myInitVector.getBytes("UTF-8"));
        final SecretKeySpec keySpec = new SecretKeySpec(myKey.getBytes("UTF-8"), "AES");

        final Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE, keySpec, iv);

        final byte[] original = cipher.doFinal(Base64.decodeBase64(in));
        return new String(original);
    }
}
