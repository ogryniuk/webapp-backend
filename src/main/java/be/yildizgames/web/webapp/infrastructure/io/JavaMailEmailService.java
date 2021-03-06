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

package be.yildizgames.web.webapp.infrastructure.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * @author Grégory Van den Borre
 */
@Service
public class JavaMailEmailService implements EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JavaMailEmailService.class);

    private final Session session;

    private final String username;

    public JavaMailEmailService(FileEmailProperties properties) {
        super();
        this.username = properties.getUser();
        this.session = Session.getInstance(properties.getProperties(), new Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, properties.getPassword());
            }
        });
    }

    @Override
    public void send(EmailTemplate template) {
        try {
            MimeMessage message = new MimeMessage(this.session);
            message.setFrom(new InternetAddress(this.username));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(template.getEmail()));
            message.setSubject(template.getTitle());
            message.setText(template.getBody());
            Transport.send(message);
        } catch (MessagingException mex) {
            LOGGER.error("Error sending mail", mex);
        }
    }
}
