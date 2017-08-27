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

package be.yildizgames.web.webapp.infrastructure.persistence;

import be.yildiz.module.database.DataBaseConnectionProvider;
import be.yildizgames.web.webapp.application.news.NewsProvider;
import be.yildizgames.web.webapp.domain.news.Author;
import be.yildizgames.web.webapp.domain.news.News;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import be.yildizgames.web.webapp.domain.news.exception.InvalidNewsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Persists the calls to the News class
 *
 * @author Oleksandr Gryniuk
 *
 */
@Repository
public class NewsPersistence extends AbstractPersistence<News> implements NewsProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(NewsPersistence.class);

    @Autowired
    public NewsPersistence(DataBaseConnectionProvider provider) {
        super(provider);
    }

    private Map<String, String> tableByLanguage = new HashMap<>();

    public NewsPersistence registerLanguage(String language, String tableName) {
        this.tableByLanguage.put(language, tableName);
        return this;
    }

    @Override
    public List<News> findLatest(String language, int newsNumber) throws InvalidNewsException {
        String sql = "SELECT * FROM " + this.tableByLanguage.get(language) + " JOIN NEWS N NATURAL JOIN"
                + " AUTHORS LIMIT " + newsNumber + " ORDER BY N.CREATION_DATE DESC";
        return listFromSQL(sql);
    }

    @Override
    protected News fromRS(ResultSet rs) throws SQLException {
        News singleNews = null;
        try {
            singleNews = new News(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getURL(5),
                    new Author(rs.getString(6)));
        } catch (InvalidNewsException e) {
            LOGGER.error("Invalid News", e);
        }
        return singleNews;
    }
}