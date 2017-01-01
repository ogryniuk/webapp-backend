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

package be.yildizgames.web.webapp.infrastructure.controller;

import be.yildizgames.web.webapp.domain.account.AccountTest;
import be.yildizgames.web.webapp.domain.account.TemporaryAccountTest;
import be.yildizgames.web.webapp.domain.account.exception.AccountValidationException;
import be.yildizgames.web.webapp.infrastructure.controller.account.AccountController;
import be.yildizgames.web.webapp.infrastructure.controller.account.AccountForm;
import be.yildizgames.web.webapp.infrastructure.services.AccountService;
import be.yildizgames.web.webapp.infrastructure.services.TemporaryAccountService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

/**
 * @author Grégory Van den Borre
 */
@RunWith(Enclosed.class)
public class AccountControllerTest {

    private static final String AVAILABLE = "available";

    private static final String NOT_AVAILABLE = "not-available";

    private static final String NOT_AVAILABLE_IN_TEMP = "not-available-in-temp";

    private static final String EMAIL_AVAILABLE = "available@test.com";

    private static final String EMAIL_NOT_AVAILABLE = "notavailable@test.com";

    private static final String EMAIL_NOT_AVAILABLE_IN_TEMP = "notavailabletemp@test.com";

    public static class IsLoginAvailable {

        @Test
        public void happyFlow() {
            givenAnAccountController().isLoginAvailable(AVAILABLE);
        }

        @Test
        public void available() {
            AccountController a = givenAnAccountController();

            ResponseEntity<Void> r = a.isLoginAvailable(AVAILABLE);

            Assert.assertEquals(200, r.getStatusCode().value());
        }

        @Test
        public void notAvailableInTempAccount() {
            AccountController a = givenAnAccountController();

            ResponseEntity<Void> r = a.isLoginAvailable(NOT_AVAILABLE_IN_TEMP);

            Assert.assertEquals(400, r.getStatusCode().value());
        }

        @Test
        public void notAvailableInAccount() {
            AccountController a = givenAnAccountController();

            ResponseEntity<Void> r = a.isLoginAvailable(NOT_AVAILABLE);

            Assert.assertEquals(400, r.getStatusCode().value());
        }

    }

    public static class IsEmailAvailable {

        @Test
        public void happyFlow() {
            givenAnAccountController().isEmailAvailable(EMAIL_AVAILABLE);
        }

        @Test
        public void available() {
            AccountController a = givenAnAccountController();

            ResponseEntity<Void> r = a.isEmailAvailable(EMAIL_AVAILABLE);

            Assert.assertEquals(200, r.getStatusCode().value());
        }

        @Test
        public void notAvailableInTempAccount() {
            AccountController a = givenAnAccountController();

            ResponseEntity<Void> r = a.isEmailAvailable(EMAIL_NOT_AVAILABLE_IN_TEMP);

            Assert.assertEquals(400, r.getStatusCode().value());
        }

        @Test
        public void notAvailableInAccount() {
            AccountController a = givenAnAccountController();

            ResponseEntity<Void> r = a.isEmailAvailable(EMAIL_NOT_AVAILABLE);

            Assert.assertEquals(400, r.getStatusCode().value());
        }

    }

    public static class Create {

        @Test
        public void happyFlow() {
            AccountController a = givenAnAccountController();
            AccountForm f = new AccountForm();
            f.setLogin(AVAILABLE);
            f.setPassword("azerty");
            f.setEmail(EMAIL_AVAILABLE);

            a.create(f);
        }

        @Test(expected = AccountValidationException.class)
        public void WithNullLogin() {
            AccountController a = givenAnAccountController();
            AccountForm f = new AccountForm();
            f.setLogin(null);
            f.setPassword("azerty");
            f.setEmail(EMAIL_AVAILABLE);

            a.create(f);
        }

        @Test(expected = AccountValidationException.class)
        public void withNullPassword() {
            AccountController a = givenAnAccountController();
            AccountForm f = new AccountForm();
            f.setLogin(AVAILABLE);
            f.setPassword(null);
            f.setEmail(EMAIL_AVAILABLE);

            a.create(f);
        }

        @Test(expected = AccountValidationException.class)
        public void withNullEmail() {
            AccountController a = givenAnAccountController();
            AccountForm f = new AccountForm();
            f.setLogin(AVAILABLE);
            f.setPassword("azerty");
            f.setEmail(null);

            a.create(f);
        }

        @Test(expected = AccountValidationException.class)
        public void withInvalidLogin() {
            AccountController a = givenAnAccountController();
            AccountForm f = new AccountForm();
            f.setLogin("ab");
            f.setPassword("azerty");
            f.setEmail(EMAIL_AVAILABLE);

            a.create(f);
        }

        @Test(expected = AccountValidationException.class)
        public void withInvalidPassword() {
            AccountController a = givenAnAccountController();
            AccountForm f = new AccountForm();
            f.setLogin(AVAILABLE);
            f.setPassword("az");
            f.setEmail(EMAIL_AVAILABLE);

            a.create(f);
        }
    }

    private static AccountController givenAnAccountController() {
        return new AccountController(givenAnAccountService(), givenATempAccountService());
    }

    private static AccountService givenAnAccountService() {
        AccountService as = Mockito.mock(AccountService.class);
        Mockito.when(as.findByLogin(AVAILABLE)).thenReturn(Optional.empty());
        Mockito.when(as.findByLogin(NOT_AVAILABLE)).thenReturn(Optional.of(AccountTest.givenAnAccount()));
        Mockito.when(as.findByLogin(NOT_AVAILABLE_IN_TEMP)).thenReturn(Optional.empty());

        Mockito.when(as.findByEmail(EMAIL_AVAILABLE)).thenReturn(Optional.empty());
        Mockito.when(as.findByEmail(EMAIL_NOT_AVAILABLE)).thenReturn(Optional.of(AccountTest.givenAnAccount()));
        Mockito.when(as.findByEmail(EMAIL_NOT_AVAILABLE_IN_TEMP)).thenReturn(Optional.empty());
        return as;
    }

    private static TemporaryAccountService givenATempAccountService() {
        TemporaryAccountService tas = Mockito.mock(TemporaryAccountService.class);
        Mockito.when(tas.findByLogin(AVAILABLE)).thenReturn(Optional.empty());
        Mockito.when(tas.findByLogin(NOT_AVAILABLE)).thenReturn(Optional.empty());
        Mockito.when(tas.findByLogin(NOT_AVAILABLE_IN_TEMP)).thenReturn(Optional.of(TemporaryAccountTest.givenATempAccount()));

        Mockito.when(tas.findByEmail(EMAIL_AVAILABLE)).thenReturn(Optional.empty());
        Mockito.when(tas.findByEmail(EMAIL_NOT_AVAILABLE)).thenReturn(Optional.empty());
        Mockito.when(tas.findByEmail(EMAIL_NOT_AVAILABLE_IN_TEMP)).thenReturn(Optional.of(TemporaryAccountTest.givenATempAccount()));

        Mockito.when(tas.getNewId(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn("1234");
        return tas;
    }
}
