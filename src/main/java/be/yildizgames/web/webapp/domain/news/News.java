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

import java.util.Date;


/**
 * @author Grégory Van den Borre
 */
public class News implements NewsDto {

    public static final int TITLE_MIN = 1;

    public static final int TITLE_MAX = 20;

    public static final int CONTENT_MIN = 1;

    public static final int CONTENT_MAX = 255;

    private static final String TITLE_MIN_VALID = "Title must be at least " + TITLE_MIN + " chars.";

    private static final String CONTENT_MIN_VALID = "Content must be at least " + CONTENT_MIN + " chars.";

    private static final String TITLE_MAX_VALID = "Title cannot have more than " + TITLE_MAX + " chars.";

    private static final String CONTENT_MAX_VALID = "Content cannot have more than " + CONTENT_MAX + " chars.";

    private final String title;

    private final String content;

    private final String image;

    private final long creationDate;

    private final long lastModificationDate;

    private final String author;


    public News(String title, String content, String image, String author) throws InvalidNewsException {
        this(title, content, image, author, new Date().getTime());
    }

    private News(String title, String content, String image, String author, long creationDate) throws InvalidNewsException {
        super();

        if(title.length() < TITLE_MIN) {
            throw new InvalidNewsException(TITLE_MIN_VALID);
        } else if(title.length() > TITLE_MAX) {
            throw new InvalidNewsException(TITLE_MAX_VALID);
        }
        if(content.length() < CONTENT_MIN) {
            throw new InvalidNewsException(CONTENT_MIN_VALID);
        } else if(content.length() > CONTENT_MAX) {
            throw new InvalidNewsException(CONTENT_MAX_VALID);
        }
        if(image == null) {
            throw new NullPointerException("Image is null");
        }
        if(author == null) {
            throw new NullPointerException("Author is null");
        }
        this.title = title;
        this.content = content;
        this.image = image;
        this.author = author;
        this.creationDate = creationDate;
        this.lastModificationDate = new Date().getTime();
    }

    public News updateTitle(String newTitle) throws InvalidNewsException {
        return new News(newTitle, this.content, this.image, this.author, this.creationDate);
    }

    public News updateContent(String newContent) throws InvalidNewsException {
        return new News(this.title, newContent, this.image, this.author, this.creationDate);
    }

    public News updateImage(String newImage) throws InvalidNewsException {
        return new News(this.title, this.content, newImage, this.author, this.creationDate);
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public String getImage() {
        return image;
    }

    public long getCreationDate() {
        return creationDate;
    }

    public long getLastModificationDate() {
        return lastModificationDate;
    }

    public String getAuthor() {
        return author;
    }
}
