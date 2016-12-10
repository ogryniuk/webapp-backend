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

package be.yildizgames.web.webapp.infrastructure.io;

import be.yildiz.common.resource.PropertiesHelper;

import java.io.File;
import java.util.Properties;

/**
 * @author Grégory Van den Borre
 */
public class FileEmailProperties {

    private final Properties properties;
    private final String login;
    private final String password;

    public FileEmailProperties(final String path) {
        super();
        this.properties = PropertiesHelper.getPropertiesFromFile(new File(path));
        this.login = properties.getProperty("mail.login");
        this.password = properties.getProperty("mail.password");
        /*this.database = properties.getProperty("mail.smtp.auth");
        this.host = properties.getProperty("mail.smtp.starttls.enable");
        this.host = properties.getProperty("mail.smtp.host");
        this.host = properties.getProperty("mail.smtp.port");*/
    }

    public Properties getProperties() {
        return properties;
    }

    public String getUser() {
        return login;
    }

    public String getPassword() {
        return this.password;
    }
}
