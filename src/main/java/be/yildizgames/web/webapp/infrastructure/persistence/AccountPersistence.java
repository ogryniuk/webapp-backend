//        This file is part of the Yildiz-Online project, licenced under the MIT License
//        (MIT)
//
//        Copyright (c) 2016 Grégory Van den Borre
//
//        More infos available: http://yildiz.bitbucket.org
//
//        Permission is hereby granted, free of charge, to any person obtaining a copy
//        of this software and associated documentation files (the "Software"), to deal
//        in the Software without restriction, including without limitation the rights
//        to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
//        copies of the Software, and to permit persons to whom the Software is
//        furnished to do so, subject to the following conditions:
//
//        The above copyright notice and this permission notice shall be included in all
//        copies or substantial portions of the Software.
//
//        THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//        IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//        FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//        AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//        LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
//        OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
//        SOFTWARE.

package be.yildizgames.web.webapp.infrastructure.persistence;

import be.yildiz.module.database.DataBaseConnectionProvider;
import be.yildizgames.web.webapp.domain.account.Account;
import be.yildizgames.web.webapp.domain.account.AccountProvider;
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
public class AccountPersistence implements AccountProvider {

    private final DataBaseConnectionProvider provider;

    @Autowired
    public AccountPersistence(DataBaseConnectionProvider provider) {
        this.provider = provider;
    }

    @Override
    public Optional<Account> getById(String id) {
        String sql = "SELECT * FROM account WHERE id = ?";
        return fromSQL(sql, id);
    }

    public Optional<Account> findByLogin(String name) {
            String sql = "SELECT * FROM account WHERE username = ?";
            return fromSQL(sql, name);

    }

    public Optional<Account> findByEmail(String email) {
        String sql = "SELECT * FROM account WHERE email = ?";
        return fromSQL(sql, email);
    }

    private Optional<Account> fromSQL(String sql, String param) {
        try(Connection c = this.provider.getConnection()) {
            try(PreparedStatement stmt = c.prepareStatement(sql)) {
                stmt.setString(1, param);
                ResultSet rs = stmt.executeQuery();
                if(rs.first()) {
                    Account a = fromRS(rs);
                    return Optional.of(a);
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private Account fromRS(ResultSet rs) throws SQLException {
        return new Account(
                String.valueOf(rs.getInt(1)),
                rs.getString(2),
                rs.getString(3),
                rs.getString(8),
                rs.getTimestamp(9).getTime());
    }

}
