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

import be.yildiz.common.authentication.AuthenticationChecker;
import be.yildiz.common.authentication.AuthenticationRules;
import be.yildiz.common.authentication.CredentialException;
import be.yildizgames.web.webapp.domain.account.exception.AccountValidationException;
import be.yildizgames.web.webapp.domain.account.exception.EmailExistsValidationException;
import be.yildizgames.web.webapp.domain.account.exception.LoginExistsValidationException;

/**
 * @author Grégory Van den Borre
 */
public class TemporaryAccount {

    private static final AuthenticationChecker checker = new AuthenticationChecker(AuthenticationRules.DEFAULT);

    private final String login;

    private final String password;

    private final String email;

    private final String uniqueToken;


    public TemporaryAccount(String login, String password, String email, String uniqueToken) {
        super();
        this.login = login;
        this.password = password;
        this.email = email;
        this.uniqueToken = uniqueToken;
    }

    public static TemporaryAccount create(TemporaryAccountProvider provider, AccountProvider accountProvider, String login, String password, String email) {
        validate(provider, accountProvider, login, password, email);
        String token = provider.getNewId(login, password, email);
        return new TemporaryAccount(login, password, email, token);
    }

    private static void validate(TemporaryAccountProvider tempProvider, AccountProvider accountProvider, String login, String password, String email) {
        try {
            checker.check(login, password);
        } catch (CredentialException e) {
            throw new AccountValidationException(e);
        }
        if(email == null) {
            throw new AccountValidationException("email.mandatory");
        }
        if(accountProvider.findByLogin(login).isPresent()) {
            throw new LoginExistsValidationException();
        }
        if(accountProvider.findByEmail(email).isPresent()) {
            throw new EmailExistsValidationException();
        }
        if(tempProvider.findByLogin(login).isPresent()) {
            throw new LoginExistsValidationException();
        }
        if(tempProvider.findByEmail(email).isPresent()) {
            throw new EmailExistsValidationException();
        }
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

    /**
     * Check if a given token is the same as the one of this account.
     * @param token Token to be tested against.
     * @return true if the provided token match this account.
     * @throws NullPointerException if token is null.
     */
    public boolean validate(String token) {
        return token.equals(this.uniqueToken);
    }
}
