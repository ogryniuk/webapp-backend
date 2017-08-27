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
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Grégory Van den Borre
 */
@RunWith(Enclosed.class)
public class NewsTest {

    private static final Integer NEWSID_OK = 1;

    private static final String TITLE_OK = "aTitle";

    private static final String CONTENT_OK = "aContent";

    private static final Integer TAGID_OK = 1;

    private static final Author AUTHOR_OK = new Author("name");

    public static class Constructor {

        @Test
        public void happyFlow() throws InvalidNewsException, MalformedURLException {

            final URL IMAGE_OK = new URL("https://www.yildiz-games.be/yildiz-engine/");
            News news = new News(NEWSID_OK, TITLE_OK, CONTENT_OK, TAGID_OK, IMAGE_OK, AUTHOR_OK);
            Assert.assertEquals(NEWSID_OK, news.getNewsId());
            Assert.assertEquals(TITLE_OK, news.getTitle());
            Assert.assertEquals(CONTENT_OK, news.getContent());
            Assert.assertEquals(TAGID_OK, news.getTagId());
            Assert.assertEquals(IMAGE_OK, news.getImage());
            Assert.assertEquals(AUTHOR_OK, news.getAuthor());
        }

        @Test(expected = NullPointerException.class)
        public void withTitleNull() throws InvalidNewsException, MalformedURLException {
            final URL IMAGE_OK = new URL("https://www.yildiz-games.be/yildiz-engine/");
            new News(NEWSID_OK, null, CONTENT_OK, TAGID_OK, IMAGE_OK, AUTHOR_OK);
        }

        @Test(expected = NullPointerException.class)
        public void withContentNull() throws InvalidNewsException, MalformedURLException {
            final URL IMAGE_OK = new URL("https://www.yildiz-games.be/yildiz-engine/");
            new News(NEWSID_OK, TITLE_OK, null, TAGID_OK, IMAGE_OK, AUTHOR_OK);
        }

        @Test(expected = InvalidNewsException.class)
        public void withContentTooShort() throws InvalidNewsException, MalformedURLException {
            final URL IMAGE_OK = new URL("https://www.yildiz-games.be/yildiz-engine/");
            new News(NEWSID_OK, TITLE_OK, "", TAGID_OK, IMAGE_OK, AUTHOR_OK);
        }

        @Test(expected = InvalidNewsException.class)
        public void withContentTooLong() throws InvalidNewsException, MalformedURLException {
            final URL IMAGE_OK = new URL("https://www.yildiz-games.be/yildiz-engine/");
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i <= News.CONTENT_MAX; i++) {
                sb.append('a');
            }

            new News(NEWSID_OK, TITLE_OK, sb.toString(), TAGID_OK, IMAGE_OK, AUTHOR_OK);
        }

        @Test(expected = NullPointerException.class)
        public void withNullImage() throws InvalidNewsException {
            new News(NEWSID_OK, TITLE_OK, CONTENT_OK, TAGID_OK, null, AUTHOR_OK);
        }

        @Test(expected = NullPointerException.class)
        public void withNullAuthor() throws InvalidNewsException, MalformedURLException {
            final URL IMAGE_OK = new URL("https://www.yildiz-games.be/yildiz-engine/");
            new News(NEWSID_OK, TITLE_OK, CONTENT_OK, TAGID_OK, IMAGE_OK, null);
        }
    }

    public static class UpdateTitle {

        @Test
        public void happyFlow() throws InvalidNewsException, MalformedURLException {
            final URL IMAGE_OK = new URL("https://www.yildiz-games.be/yildiz-engine/");
            News news = new News(NEWSID_OK, TITLE_OK, CONTENT_OK, TAGID_OK, IMAGE_OK, AUTHOR_OK);
            news = news.updateTitle(TITLE_OK + "a");
            Assert.assertEquals(TITLE_OK + "a", news.getTitle());
        }
    }
}
