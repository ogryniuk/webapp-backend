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

package be.yildizgames.web.webapp.infrastructure.controller;

import be.yildiz.common.collections.Lists;
import be.yildizgames.web.webapp.domain.account.exception.AccountValidationException;
import be.yildizgames.web.webapp.domain.account.exception.EmailExistsValidationException;
import be.yildizgames.web.webapp.domain.account.exception.LoginExistsValidationException;
import be.yildizgames.web.webapp.infrastructure.TechnicalException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.stream.Collectors;

/**
 * @author Grégory Van den Borre
 */
@ControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(AccountValidationException.class)
    @ResponseBody
    public AjaxResponse handleValidationException(final AccountValidationException e) {
        return new AjaxResponse(
                e.getErrors()
                .stream()
                .map(error -> new Notification("account.validation.error", error, "error"))
                .collect(Collectors.toList()));
    }

    @ExceptionHandler(LoginExistsValidationException.class)
    @ResponseBody
    public AjaxResponse handleLoginExistException(final LoginExistsValidationException e) {
        return new AjaxResponse(Lists.newList(
                new Notification("account.validation.error", "account.validation.error.login.exists", "error")));
    }

    @ExceptionHandler(EmailExistsValidationException.class)
    @ResponseBody
    public AjaxResponse handleEmailException(final EmailExistsValidationException e) {
        return new AjaxResponse(Lists.newList(
                new Notification("account.validation.error", "account.validation.error.email.exists", "error")));
    }

    @ExceptionHandler(TechnicalException.class)
    @ResponseBody
    public AjaxResponse handleTechnicalException(final TechnicalException e) {
        return new AjaxResponse(Lists.newList(
                new Notification("technical.error", "technical.error.content", "error")));
    }
}
