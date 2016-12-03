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

package be.yildizgames.web.webapp.domain.account;

import java.util.Date;

/**
 * @author Grégory Van den Borre
 */
public class Account {

    private final String id;

    private final String login;

    private final String password;

    private final String email;

    private final long lastConnectionDate;

    public Account(String id, String login, String password, String email, long lastConnectionDate) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.email = email;
        this.lastConnectionDate = lastConnectionDate;
    }


    public static Account create(AccountIdProvider provider, TemporaryAccount account) {
        String id = provider.getNewId(account);
        return new Account(id, account.getLogin(), account.getLogin(), account.getEmail(), new Date().getTime());
    }

    public Account resetPassword() {
        //FIXME do
        return null;
    }

    public Account changePassword(final String newPassword) {
        //FIXME do
        return null;
    }

    public Account changeEmail(String newEmail) {
        //FIXME do
        return null;
    }

    public String getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public long getLastConnectionDate() {
        return lastConnectionDate;
    }
}
