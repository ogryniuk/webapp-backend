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

import be.yildizgames.web.webapp.domain.account.Account;
import be.yildizgames.web.webapp.domain.account.AccountIdProvider;
import be.yildizgames.web.webapp.domain.account.AccountProvider;
import be.yildizgames.web.webapp.domain.account.TemporaryAccount;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

/**
 * @author Grégory Van den Borre
 */
@Repository
public class AccountPersistence implements AccountIdProvider, AccountProvider {

    private final Map<String, Account> accounts = new TreeMap<>();

    private int currentIndex = 0;

    @Override
    public String getNewId(TemporaryAccount account) {
        String index = String.valueOf(currentIndex);
        currentIndex++;
        this.accounts.put(index, new Account(index, account.getLogin(), account.getPassword(), account.getEmail(), new Date().getTime()));
        return index;
    }

    @Override
    public Account getById(String id) {
        return this.accounts.get(id);
    }

    public Optional<Account> findByLogin(String name) {
        return Optional.empty();
    }

    public Optional<Account> findByEmail(String email) {
        return Optional.empty();
    }
}
