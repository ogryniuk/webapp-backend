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

package be.yildizgames.web.webapp.infrastructure.services;

import be.yildizgames.web.webapp.domain.account.TemporaryAccount;
import be.yildizgames.web.webapp.domain.account.TemporaryAccountProvider;
import be.yildizgames.web.webapp.infrastructure.io.EmailService;
import be.yildizgames.web.webapp.infrastructure.io.account.TemporaryAccountEmail;
import be.yildizgames.web.webapp.infrastructure.persistence.TemporaryAccountPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Grégory Van den Borre
 */
@Service
public class TemporaryAccountService implements TemporaryAccountProvider {

    private final EmailService emailService;

    private final TemporaryAccountPersistence persistence;

    @Autowired
    public TemporaryAccountService(EmailService emailService, TemporaryAccountPersistence persistence) {
        this.emailService = emailService;
        this.persistence = persistence;
    }


    @Override
    public String getNewId(String login, String password, String email) {
        String token = UUID.randomUUID().toString();
        //FIXME ensure transactional
        this.persistence.save(login, password, email, token);
        this.emailService.send(new TemporaryAccountEmail("fr", login, email, token));
        return token;
    }

    @Override
    public Optional<TemporaryAccount> findByEmail(String email) {
        return this.persistence.findByEmail(email);
    }

    @Override
    public Optional<TemporaryAccount> findByLogin(String login) {
        return this.persistence.findByLogin(login);
    }
}
