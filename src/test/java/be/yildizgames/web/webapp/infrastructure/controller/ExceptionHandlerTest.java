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

import be.yildizgames.web.webapp.domain.account.exception.AccountValidationException;
import be.yildizgames.web.webapp.domain.account.exception.EmailExistsValidationException;
import be.yildizgames.web.webapp.domain.account.exception.LoginExistsValidationException;
import be.yildizgames.web.webapp.infrastructure.TechnicalException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.springframework.http.ResponseEntity;

/**
 * @author Grégory Van den Borre
 */
@RunWith(Enclosed.class)
public class ExceptionHandlerTest {

    public static class HandleValidationException {

        @Test
        public void happyFlow() {
            ExceptionsHandler e = new ExceptionsHandler();
            ResponseEntity<AjaxResponse> response = e.handleValidationException(new AccountValidationException("boum"));
            Assert.assertEquals(422, response.getStatusCode().value());
            Assert.assertEquals("account.validation.error", response.getBody().getNotifications().get(0).getTitle());
            Assert.assertEquals("boum", response.getBody().getNotifications().get(0).getContent());
            Assert.assertEquals("error", response.getBody().getNotifications().get(0).getType());
            Assert.assertEquals(1, response.getBody().getNotifications().size());

        }
    }

    public static class HandleLoginExistException {

        @Test
        public void happyFlow() {
            ExceptionsHandler e = new ExceptionsHandler();
            ResponseEntity<AjaxResponse> response = e.handleLoginExistException(new LoginExistsValidationException());
            Assert.assertEquals(422, response.getStatusCode().value());
            Assert.assertEquals("account.validation.error", response.getBody().getNotifications().get(0).getTitle());
            Assert.assertEquals("account.validation.error.login.exists", response.getBody().getNotifications().get(0).getContent());
            Assert.assertEquals("error", response.getBody().getNotifications().get(0).getType());
            Assert.assertEquals(1, response.getBody().getNotifications().size());
        }
    }

    public static class HandleEmailException {

        @Test
        public void happyFlow() {
            ExceptionsHandler e = new ExceptionsHandler();
            ResponseEntity<AjaxResponse> response = e.handleEmailException(new EmailExistsValidationException());
            Assert.assertEquals(422, response.getStatusCode().value());
            Assert.assertEquals("account.validation.error", response.getBody().getNotifications().get(0).getTitle());
            Assert.assertEquals("account.validation.error.email.exists", response.getBody().getNotifications().get(0).getContent());
            Assert.assertEquals("error", response.getBody().getNotifications().get(0).getType());
            Assert.assertEquals(1, response.getBody().getNotifications().size());
        }
    }

    public static class HandleTechnicalException {

        @Test
        public void happyFlow() {
            ExceptionsHandler e = new ExceptionsHandler();
            ResponseEntity<AjaxResponse> response = e.handleTechnicalException(new TechnicalException());
            Assert.assertEquals(500, response.getStatusCode().value());
            Assert.assertEquals("technical.error", response.getBody().getNotifications().get(0).getTitle());
            Assert.assertEquals("technical.error.content", response.getBody().getNotifications().get(0).getContent());
            Assert.assertEquals("error", response.getBody().getNotifications().get(0).getType());
            Assert.assertEquals(1, response.getBody().getNotifications().size());
        }
    }
}
