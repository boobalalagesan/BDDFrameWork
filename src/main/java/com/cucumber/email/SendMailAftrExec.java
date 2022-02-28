package com.cucumber.email;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Service;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
//import org.omg.PortableInterceptor.TRANSPORT_RETRY;

import com.cucumber.util.RunConfig;
//import com.sun.*;
import javax.mail.*;
import javax.mail.internet.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;

import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;
 
public class SendMailAftrExec{
 
public static void sendMail() throws IOException {
 
    String password = ""; 
    final String from = RunConfig.SendingFrom; 
    String to = RunConfig.SendingTo;
    String host = "smtp.genpt.com";
    String env = System.getProperty("env");		
	if(StringUtils.isEmpty(env)){
		env = "dev";
	}
	
	
    final String subject = "ProLink-" + " Automation Execution Report"; //change to your subject
        
    String MsgBody="<p>Hi All, <br> <br> We have executed the ProLink automation test cases in current build. Please find the detailed report in the attachment.<br><br>";
    String signature="<br> Please do not reply, This is auto genereated email. <br><br> <p>Thanks,<br> Automation Team.";
    
    Properties props = new Properties();
    props.put("mail.smtp.auth","false");
    props.put("mail.smtp.starttls.enable","true");
    props.put("mail.smtp.host", "smtp.genpt.com");

    props.put("mail.smtp.port", "25");
    props.put("mail.smtp.debug", "true");
    props.put("mail.smtp.ssl.enable","false");
 
    Session session = Session.getDefaultInstance(props);
    session.setDebug(true);
 
    try {
 
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        InternetAddress[] parse = InternetAddress.parse(to , true);
        message.setRecipients(javax.mail.Message.RecipientType.TO,  parse);

        message.setSubject(subject);
       
        //Sorting the latest created file
        File dir = new File(System.getProperty("user.dir")+ File.separator+"test_reports");
        //File dir1 = new File(System.getProperty("user.dir")+ File.separator+"target"+ File.separator+"cucumber-reports");
        File[] files = dir.listFiles();
      //  File[] files1 = dir1.listFiles();
        File lastModifiedFile = files[0];
        for (int i = 1; i < files.length; i++) {
           if (lastModifiedFile.lastModified() < files[i].lastModified()) {
               lastModifiedFile = files[i];
           }
        }
        /*File lastModifiedFile1 = files1[0];
        for (int i = 1; i < files1.length; i++) {
           if (lastModifiedFile1.lastModified() < files1[i].lastModified()) {
               lastModifiedFile1 = files1[i];
           }
        }*/
        String attach = lastModifiedFile.toString();
       // String attach1 = lastModifiedFile1.toString();
        Multipart multipart = new MimeMultipart("alternative");
         
        //Customized report - BodyPart
        MimeBodyPart htmlpart = new MimeBodyPart();
        StringWriter writer = new StringWriter();      
        //IOUtils.copy(new FileInputStream(new File(System.getProperty("user.dir")+ File.separator+"target"+ File.separator+"surefire-reports"+File.separator+"customized-emailable-report.html")), writer);
      IOUtils.copy(new FileInputStream(new File(RunConfig.REPORTS_PATH+".html")), writer);
     //  IOUtils.copy(new FileInputStream(new File(RunConfig.CUCUMBER_REPORTS_PATH+".html")), writer);
        htmlpart.setContent(MsgBody/*+writer.toString()*/+signature, "text/html");
        multipart.addBodyPart(htmlpart);
        
   
        //Attachment BodyPart   
        BodyPart attachPart = new MimeBodyPart();
       // BodyPart attachPart1 = new MimeBodyPart();
        DataSource source = new FileDataSource(attach);
       // DataSource source1 = new FileDataSource(attach1);
        attachPart.setDataHandler(new DataHandler(source));
       // attachPart1.setDataHandler(new DataHandler(source1));
        String Filename = "ProLink_Automation_Report.html";
       // String CucumberFilename = env +"_CucumberAutomation Report.html";
        attachPart.setFileName(Filename);
        //attachPart1.setFileName(CucumberFilename);
       multipart.addBodyPart(attachPart);
       // multipart.addBodyPart(attachPart1);
         
       
         //Setting the multipart to message
         message.setContent(multipart);
       
        System.out.println("Sending");
 
        Transport transport = session.getTransport("smtp");
        transport.connect();//transport.connect(host,25,from, password);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
 
        System.out.println("Done");
 
    } catch (MessagingException e) {
        e.printStackTrace();
    }
  }
}


