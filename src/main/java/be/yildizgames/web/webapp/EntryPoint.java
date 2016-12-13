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

package be.yildizgames.web.webapp;

import be.yildiz.module.database.C3P0ConnectionProvider;
import be.yildiz.module.database.DataBaseConnectionProvider;
import be.yildiz.module.database.DbFileProperties;
import be.yildizgames.web.webapp.infrastructure.io.EmailService;
import be.yildizgames.web.webapp.infrastructure.io.FileEmailProperties;
import be.yildizgames.web.webapp.infrastructure.io.JavaMailEmailService;
import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.sql.SQLException;

/**
 * @author Grégory Van den Borre
 */
@SpringBootApplication
@ComponentScan("be.yildizgames.web.webapp.infrastructure.*")
public class EntryPoint {

    @Value("${dbconfig}")
    private String databaseConfigFile;

    @Value("${mailconfig}")
    private String mailConfigFile;

    @Value("${port}")
    private int port;

    @Bean
    public EmbeddedServletContainerFactory servletContainer() {

        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();

        Connector ajpConnector = new Connector("AJP/1.3");
        ajpConnector.setProtocol("AJP/1.3");
        ajpConnector.setPort(this.port);
        ajpConnector.setSecure(false);
        ajpConnector.setAllowTrace(false);
        ajpConnector.setScheme("http");
        tomcat.addAdditionalTomcatConnectors(ajpConnector);

        return tomcat;
    }

    @Bean
    public DataBaseConnectionProvider getConnectionProvider() throws SQLException {
        return new C3P0ConnectionProvider(
                DataBaseConnectionProvider.DBSystem.MYSQL,
                new DbFileProperties(databaseConfigFile));
    }

    @Bean
    public EmailService emailService() {
        return new JavaMailEmailService(new FileEmailProperties(mailConfigFile));
    }

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(EntryPoint.class, args);
    }
}
