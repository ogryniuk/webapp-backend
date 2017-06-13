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

package be.yildizgames.web.webapp.domain.news;

import be.yildizgames.web.webapp.domain.news.exception.InvalidNewsException;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

/**
 * @author Grégory Van den Borre
 */
@RunWith(Enclosed.class)
public class NewsTest {

    private static final String TITLE_OK = "aTitle";

    private static final String CONTENT_OK = "aContent";

    private static final String IMAGE_OK = "NewsTest.class";

    private static final String ACCOUNT_OK = "me";

    public static class Constructor {

        @Test
        public void happyFlow() throws InvalidNewsException {
            new News(TITLE_OK, CONTENT_OK, IMAGE_OK, ACCOUNT_OK);
        }

        @Test(expected = NullPointerException.class)
        public void withTitleNull() throws InvalidNewsException {
            new News(null, CONTENT_OK, IMAGE_OK, ACCOUNT_OK);
        }

        @Test(expected = InvalidNewsException.class)
        public void withTitleTooShort() throws InvalidNewsException {
            new News("", CONTENT_OK, IMAGE_OK, ACCOUNT_OK);
        }

        @Test(expected = InvalidNewsException.class)
        public void withTitleTooLong() throws InvalidNewsException {
            String title = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
            new News(title, CONTENT_OK, IMAGE_OK, ACCOUNT_OK);
        }

        @Test(expected = NullPointerException.class)
        public void withContentNull() throws InvalidNewsException {
            new News(TITLE_OK, null, IMAGE_OK, ACCOUNT_OK);
        }

        @Test(expected = InvalidNewsException.class)
        public void withContentTooShort() throws InvalidNewsException {
            new News(TITLE_OK, "", IMAGE_OK, ACCOUNT_OK);
        }

        @Test(expected = InvalidNewsException.class)
        public void withContentTooLong() throws InvalidNewsException {
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i <= News.CONTENT_MAX; i++) {
                sb.append('a');
            }

            new News(TITLE_OK, sb.toString(), IMAGE_OK, ACCOUNT_OK);
        }

        @Test(expected = NullPointerException.class)
        public void withNullImage() throws InvalidNewsException {
            new News(TITLE_OK, CONTENT_OK, null, ACCOUNT_OK);
        }

        @Test(expected = NullPointerException.class)
        public void withNullAuthor() throws InvalidNewsException {
            new News(TITLE_OK, CONTENT_OK, IMAGE_OK, null);
        }
    }
}
