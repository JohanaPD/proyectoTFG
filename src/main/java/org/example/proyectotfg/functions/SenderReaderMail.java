package org.example.proyectotfg.functions;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.sun.mail.imap.IMAPFolder;
//import javax.mail.AddressException;

public class SenderReaderMail {

    private Properties properties;
    private Session session;

    public void setPropertiesServerSMTP() {
        properties = System.getProperties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.starttls.enable", "true");
        session = Session.getInstance(properties, null);
    }

    private Transport connectServerSTMP(String directionEmail, String password) throws MessagingException {
        Transport t = (Transport) session.getTransport("smtp");
        t.connect(properties.getProperty("mail.smtp.host"), directionEmail, password);
        return t;
    }


    public Message createFirstPartEmail(String emitter, String destination, String subject) throws MessagingException, AddressException, IOException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(emitter));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(destination));
        message.setSubject(subject);
        return message;
    }

    private Message createTextMessage(String emitter, String destination, String subject, String textMessage) throws MessagingException, AddressException, IOException {
        Message message = createFirstPartEmail(emitter, destination, subject);
        message.setText(textMessage);
        return message;
    }

    public Message createMessageWithAttachedFiles(String emitter, String destination, String subject, String textMessage, String pathFile) throws MessagingException, AddressException, IOException {
        Message message = createFirstPartEmail(emitter, destination, subject);
        //Cuerpo del mensaje
        BodyPart bodyPart = new MimeBodyPart();
        bodyPart.setText(textMessage);
        //Adjunto del mensaje
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.attachFile(new File(pathFile));
        //Composición del mensaje (Cuerpo+Adjunto)
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(bodyPart);
        multipart.addBodyPart(mimeBodyPart);
        message.setContent(multipart);
        return message;
    }

    private Message createMessageHTML(String emitter, String destination, String subject, String htmlContent) throws MessagingException, AddressException, IOException {
        Message mensaje = createFirstPartEmail(emitter, destination, subject);
        mensaje.setContent(htmlContent, "text/html; charset=utf-8");
        return mensaje;
    }

    public void enviarMensajeHTML(String emitter, String destination, String subject, String htmlContent, String directionHTML, String password) throws AddressException, MessagingException, IOException {
        setPropertiesServerSMTP();
        Message message = createMessageHTML(emitter, destination, subject, htmlContent);
        Transport t = connectServerSTMP(directionHTML, password);
        t.sendMessage(message, message.getAllRecipients());
        t.close();
    }

    public void sendTextMessage(String emisor, String destinatario, String asunto, String textoMensaje, String direccionEmail, String password) throws AddressException, MessagingException, IOException {
        setPropertiesServerSMTP();
        Message mensaje = createTextMessage(emisor, destinatario, asunto, textoMensaje);
        Transport t = connectServerSTMP(direccionEmail, password);
        t.sendMessage(mensaje, mensaje.getAllRecipients());
        t.close();
    }

    public void sendMessageWithAttachedFiles(String emitter, String destination, String subject, String textMessage, String directionEmail, String password, String pathFile) throws AddressException, MessagingException, IOException {
        setPropertiesServerSMTP();
        Message message = createMessageWithAttachedFiles(emitter, destination, subject, textMessage, pathFile);
        Transport t = connectServerSTMP(directionEmail, password);
        t.sendMessage(message, message.getAllRecipients());
        t.close();
    }

    //Leer Mensaje
    private Session getSesionImap() {
        Properties properties = new Properties();
        properties.setProperty("mail.store.protocol", "imap");
        properties.setProperty("mail.imap.host", "imap.gmail.com");
        properties.setProperty("mail.imap.port", "993");
        properties.setProperty("mail.imap.ssl.enable", "true");
        properties.setProperty("mail.imap.ssl.trust", "imap.gmail.com");
        Session session = Session.getDefaultInstance(properties);
        return session;
    }

    public void leerCarpetaInbox(String mail, String password) throws Exception {

        Session session = this.getSesionImap();
        Store store = session.getStore("imaps");
        store.connect("imap.gmail.com", 993, mail, password);
        IMAPFolder inbox = (IMAPFolder) store.getFolder("INBOX");
        inbox.open(Folder.READ_WRITE);
        Message[] messages = inbox.getMessages();
        for (int i = 0; i < messages.length; i++) {
            Message mensaje = messages[i];
            Address[] directionOrigin = mensaje.getFrom();
            String from = directionOrigin[0].toString();
            Address[] destinationDirections = mensaje.getRecipients(Message.RecipientType.TO);
            String to = destinationDirections[0].toString();
            String subject = mensaje.getSubject();
            MimeMultipart mimeMultipart = (MimeMultipart) mensaje.getContent();
            if (mimeMultipart.getBodyPart(0).isMimeType("text/plain")) {
                String textoMensaje = (String) mimeMultipart.getBodyPart(0).getContent();
                System.out.printf("De %s A %s Asunto: %s Mensaje: %s\n", from, to, subject, textoMensaje);
            } else {
                System.out.printf("De %s A %s Asunto: %s Mensaje: %s\n", from, to, subject, "El mensaje no solo está compuesto de texto plano");
            }

        }
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public Properties getProperties() {
        return properties;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
