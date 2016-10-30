package com.qtpselenium.facebook.pom.util;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

//set CLASSPATH=%CLASSPATH%;activation.jar;mail.jar

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendMail {

    public static void main(String[] args) throws IOException {
        String latestExtRepPath = ReportUtil.latestExtentReport();
        String[] temp = latestExtRepPath.split("\\\\");
        String latestExtRepName = temp[temp.length - 1];

        String[] to = {
            "akunuri.sam@gmail.com"
        };
        String[] cc = {};
        String[] bcc = {};

        //Email Extent Reports
        SendMail.sendMail("itprofessional.hyderabad@gmail.com",
            "Reset!123*",
            "smtp.gmail.com",
            "465",
            "true",
            "true",
            true,
            "javax.net.ssl.SSLSocketFactory",
            "false",
            to,
            cc,
            bcc,
            latestExtRepName,
            "Please find the reports attached - attachmentName.\n\n Regards\nWebMaster",
            latestExtRepPath,
            latestExtRepName);

        //Loading config properties
    /*    Properties CONFIG = new Properties();
        FileInputStream fs = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\config.properties");
        CONFIG.load(fs);*/

        //Generating zip file 
        String xsltRepPath = System.getProperty("user.dir") + FBConstants.XSLT_REPORTS_PATH;
        ReportUtil.zip(xsltRepPath);

        //Setting zip file path
        String xsltZipPath = System.getProperty("user.dir") + FBConstants.XSLTZipFolder + "\\XSLT_Reports.zip";

        //Setting subject & attachment name
        Date d = new Date();
        String XSLT_Rep_Name = "XSLT_Reports_" + d.toString().replace(":", "_").replace(" ", "_") + ".zip";

        //Email XSLP Zip Report
        SendMail.sendMail("itprofessional.hyderabad@gmail.com",
            "Reset!123*",
            "smtp.gmail.com",
            "465",
            "true",
            "true",
            true,
            "javax.net.ssl.SSLSocketFactory",
            "false",
            to,
            cc,
            bcc,
            XSLT_Rep_Name,
            "Please find the reports attached - attachmentName.\n\n Regards\nWebMaster",
            xsltZipPath,
            XSLT_Rep_Name);
    }

    public static boolean sendMail(String userName,
        String passWord,
        String host,
        String port,
        String starttls,
        String auth,
        boolean debug,
        String socketFactoryClass,
        String fallback,
        String[] to,
        String[] cc,
        String[] bcc,
        String subject,
        String text,
        String attachmentPath,
        String attachmentName) {

        Properties props = new Properties();
        props.put("mail.smtp.user", userName);
        props.put("mail.smtp.host", host);

        if (!"".equals(port))
            props.put("mail.smtp.port", port);

        if (!"".equals(starttls))
            props.put("mail.smtp.starttls.enable", starttls);

        props.put("mail.smtp.auth", auth);

        if (debug) {
            props.put("mail.smtp.debug", "true");
        } else {
            props.put("mail.smtp.debug", "false");
        }

        if (!"".equals(port))
            props.put("mail.smtp.socketFactory.port", port);

        if (!"".equals(socketFactoryClass))
            props.put("mail.smtp.socketFactory.class", socketFactoryClass);

        if (!"".equals(fallback))
            props.put("mail.smtp.socketFactory.fallback", fallback);

        try {
            Session session = Session.getDefaultInstance(props, null);
            session.setDebug(debug);
            MimeMessage msg = new MimeMessage(session);
            msg.setText(text);
            msg.setSubject(subject);

            //attachment start
            //create the message part 

            Multipart multipart = new MimeMultipart();
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            DataSource source =
                new FileDataSource(attachmentPath);
            messageBodyPart.setDataHandler(
                new DataHandler(source));
            messageBodyPart.setFileName(attachmentName);
            multipart.addBodyPart(messageBodyPart);

            //attachment ends
            // Put parts in message

            msg.setContent(multipart);
            msg.setFrom(new InternetAddress("itsthakur@gmail.com"));

            for (int i = 0; i < to.length; i++) {
                msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to[i]));
            }

            for (int i = 0; i < cc.length; i++) {
                msg.addRecipient(Message.RecipientType.CC, new InternetAddress(cc[i]));
            }

            for (int i = 0; i < bcc.length; i++) {
                msg.addRecipient(Message.RecipientType.BCC, new InternetAddress(bcc[i]));
            }

            msg.saveChanges();
            Transport transport = session.getTransport("smtp");
            transport.connect(host, userName, passWord);
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();

            return true;

        } catch (Exception mex) {
            mex.printStackTrace();
            return false;
        }
    }
}