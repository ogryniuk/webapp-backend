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

import be.yildiz.common.log.Logger;
import be.yildiz.module.database.DataBaseConnectionProvider;
import be.yildizgames.web.webapp.domain.account.TemporaryAccount;
import be.yildizgames.web.webapp.infrastructure.TechnicalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * @author Grégory Van den Borre
 */
@Repository
public class TemporaryAccountPersistence extends AbstractPersistence<TemporaryAccount>{

    private final DataBaseConnectionProvider provider;

    @Autowired
    public TemporaryAccountPersistence(DataBaseConnectionProvider provider) {
        super(provider);
        this.provider = provider;
    }

    @Override
    protected TemporaryAccount fromRS(ResultSet rs) throws SQLException {
        return new TemporaryAccount(
                rs.getString(1),
                rs.getString(2),
                rs.getString(3),
                rs.getString(4));
    }

    public void save(String login, String password, String email, String token) {
        String sql = "INSERT INTO temp_account " +
                "(login, password, email, check_value)" +
                " VALUES (?,?,?,?)";
        try(Connection c = this.provider.getConnection()) {
            try(PreparedStatement stmt = c.prepareStatement(sql)) {
                stmt.setString(1, login);
                stmt.setString(2, password);
                stmt.setString(3, email);
                stmt.setString(4, token);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            Logger.error(e);
            throw new TechnicalException();
        }
    }

    public Optional<TemporaryAccount> findByEmail(String email) {
        String sql = "SELECT * FROM temp_account WHERE email = ?";
        return fromSQL(sql, email);
    }

    public Optional<TemporaryAccount> findByLogin(String login) {
        String sql = "SELECT * FROM temp_account WHERE login = ?";
        return fromSQL(sql, login);
    }

    public void confirm(String login) {
        String sql = "UPDATE temp_account SET complete=1 WHERE login=?";
        try(Connection c = this.provider.getConnection()) {
            try(PreparedStatement stmt = c.prepareStatement(sql)) {
                stmt.setString(1, login);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            Logger.error(e);
            throw new TechnicalException();
        }
    }
}
