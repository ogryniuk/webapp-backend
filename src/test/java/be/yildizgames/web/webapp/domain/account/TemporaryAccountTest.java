/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 * Copyright (c) 2017 Grégory Van den Borre
 *
 * More infos available: https://www.yildiz-games.be
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without
 * limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 * OR COPYRIGHT  HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE  SOFTWARE.
 */

package be.yildizgames.web.webapp.domain.account;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Grégory Van den Borre
 */
public class TemporaryAccountTest {

    public static TemporaryAccount givenATempAccount() {
        return new TemporaryAccount("me", "myPass", "me@me.com", "1234");
    }

    public static class Constructor {

        @Test
        public void happyFlow() {
            TemporaryAccount ta = givenATempAccount();
            Assert.assertEquals("me", ta.getLogin());
            Assert.assertEquals("myPass", ta.getPassword());
            Assert.assertEquals("me@me.com", ta.getEmail());
        }

        @Test(expected = AssertionError.class)
        public void withLoginNull() {
            new TemporaryAccount(null, "myPass", "me@me.com", "1234");
        }

        @Test(expected = AssertionError.class)
        public void withPasswordNull() {
            new TemporaryAccount("me", null, "me@me.com", "1234");
        }

        @Test(expected = AssertionError.class)
        public void withEmailNull() {
            new TemporaryAccount("me", "myPass", null, "1234");
        }

        @Test(expected = AssertionError.class)
        public void withTokenNull() {
            new TemporaryAccount("me", "myPass", "me@me.com", null);
        }
    }

    public static class Validate {

        @Test
        public void valid() {
            Assert.assertTrue(givenATempAccount().validate("1234"));
        }

        @Test
        public void notValid() {
            Assert.assertFalse(givenATempAccount().validate("12345"));
        }

        @Test(expected = NullPointerException.class)
        public void withNull() {
            givenATempAccount().validate(null);
        }
    }

}
