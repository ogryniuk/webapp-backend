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

package be.yildizgames.web.webapp.infrastructure.services;

import be.yildizgames.web.webapp.domain.account.Account;
import be.yildizgames.web.webapp.application.account.AccountProvider;
import be.yildizgames.web.webapp.domain.account.exception.AccountNoFoundException;
import be.yildizgames.web.webapp.infrastructure.persistence.AccountPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Grégory Van den Borre
 */
@Service
public class AccountService implements AccountProvider {


    private final AccountPersistence persistence;

    @Autowired
    public AccountService(AccountPersistence persistence) {
        super();
        assert persistence != null;
        this.persistence = persistence;
    }

    @Override
    public Account getById(String id) {
        return persistence.getById(id).orElseThrow(AccountNoFoundException::new);
    }

    @Override
    public Optional<Account> findByLogin(String login) {
        return persistence.findByLogin(login);
    }

    @Override
    public Optional<Account> findByEmail(String email) {
        return persistence.findByEmail(email);
    }
}
