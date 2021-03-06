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

import be.yildiz.common.collections.Lists;
import be.yildizgames.web.webapp.domain.account.exception.AccountValidationException;
import be.yildizgames.web.webapp.domain.account.exception.EmailExistsValidationException;
import be.yildizgames.web.webapp.domain.account.exception.LoginExistsValidationException;
import be.yildizgames.web.webapp.infrastructure.TechnicalException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.stream.Collectors;

/**
 * @author Grégory Van den Borre
 */
@ControllerAdvice
public class ExceptionsHandler {

    private static final String ACCOUNT_VALIDATION_ERROR = "account.validation.error";

    private static final String TYPE_ERROR = "error";

    @ExceptionHandler(AccountValidationException.class)
    @ResponseBody
    public ResponseEntity<AjaxResponse> handleValidationException(final AccountValidationException e) {
        return this.build(new AjaxResponse(
                e.getErrors()
                        .stream()
                        .map(error -> new Notification(ACCOUNT_VALIDATION_ERROR, error, TYPE_ERROR))
                        .collect(Collectors.toList())), 422);
    }

    @ExceptionHandler(LoginExistsValidationException.class)
    @ResponseBody
    public ResponseEntity<AjaxResponse> handleLoginExistException(final LoginExistsValidationException e) {
        return this.build(new AjaxResponse(Lists.newList(
                new Notification(ACCOUNT_VALIDATION_ERROR, "account.validation.error.login.exists", TYPE_ERROR))), 422);
    }

    @ExceptionHandler(EmailExistsValidationException.class)
    @ResponseBody
    public ResponseEntity<AjaxResponse> handleEmailException(final EmailExistsValidationException e) {
        return this.build(new AjaxResponse(Lists.newList(
                new Notification(ACCOUNT_VALIDATION_ERROR, "account.validation.error.email.exists", TYPE_ERROR))), 422);
    }

    @ExceptionHandler(TechnicalException.class)
    @ResponseBody
    public ResponseEntity<AjaxResponse> handleTechnicalException(final TechnicalException e) {
        return this.build(new AjaxResponse(Lists.newList(
                new Notification("technical.error", "technical.error.content", TYPE_ERROR))), 500);
    }

    private ResponseEntity<AjaxResponse> build(AjaxResponse response, int status) {
        return new ResponseEntity<>(response, HttpStatus.valueOf(status));
    }
}
