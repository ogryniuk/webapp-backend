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

package be.yildizgames.web.webapp.infrastructure.controller.account;

import be.yildizgames.web.webapp.domain.account.Account;
import be.yildizgames.web.webapp.domain.account.TemporaryAccount;
import be.yildizgames.web.webapp.infrastructure.controller.AjaxResponse;
import be.yildizgames.web.webapp.infrastructure.services.AccountService;
import be.yildizgames.web.webapp.infrastructure.services.TemporaryAccountService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Grégory Van den Borre
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class AccountController {

    private final AccountService accountService;

    private final TemporaryAccountService temporaryAccountService;


    @Autowired
    public AccountController(AccountService accountService, TemporaryAccountService temporaryAccountService) {
        super();
        this.accountService = accountService;
        this.temporaryAccountService = temporaryAccountService;
    }

    @RequestMapping(value = "api/v1/accounts/creations", method = RequestMethod.POST)
    public AjaxResponse create(@RequestBody AccountForm form) {
        TemporaryAccount.create(
                temporaryAccountService,
                accountService,
                form.getLogin(),
                BCrypt.hashpw(form.getPassword(), BCrypt.gensalt()),
                form.getEmail());
        return new AjaxResponse();
    }


    @RequestMapping("api/v1/accounts/{id}")
    public Account find(@PathVariable String id) {
        return this.accountService.getById(id);
    }

    @RequestMapping("api/v1/accounts/validations/logins/unicities")
    public ResponseEntity<Void> isLoginAvailable(@RequestParam String login) {
        int status =  this.accountService.findByLogin(login).isPresent() ? 400 : 200;
        if(status == 200) {
            status = this.temporaryAccountService.findByLogin(login).isPresent() ? 400 : 200;
        }
        return ResponseEntity.status(status).build();
    }

    @RequestMapping("api/v1/accounts/validations/emails/unicities")
    public ResponseEntity<Void> isEmailAvailable(@RequestParam String email) {
        int status =  this.accountService.findByEmail(email).isPresent() ? 400 : 200;
        if(status == 200) {
            status = this.temporaryAccountService.findByEmail(email).isPresent() ? 400 : 200;
        }
        return ResponseEntity.status(status).build();
    }
}
