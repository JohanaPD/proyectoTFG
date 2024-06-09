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

/**
 * This class manages sending and reading emails.
 */
public class SenderReaderMail {

    private Properties properties;
    private Session session;
    /**
     * Sets the properties for the SMTP server.
     */
    public void setPropertiesServerSMTP() {
        properties = System.getProperties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.starttls.enable", "true");
        session = Session.getInstance(properties, null);
    }

    /**
     * Connects to the SMTP server.
     *
     * @param directionEmail The email address used to connect.
     * @param password       The password of the email address.
     * @return The Transport object.
     * @throws MessagingException If an error occurs during the connection process.
     */
    private Transport connectServerSTMP(String directionEmail, String password) throws MessagingException {
        Transport t = (Transport) session.getTransport("smtp");
        t.connect(properties.getProperty("mail.smtp.host"), directionEmail, password);
        return t;
    }

    /**
     * Creates the first part of an email message.
     *
     * @param emitter     The sender's email address.
     * @param destination The recipient's email address.
     * @param subject     The subject of the email.
     * @return The created Message object.
     * @throws MessagingException If an error occurs while creating the message.
     * @throws AddressException   If there is an error with the email addresses.
     * @throws IOException        If an error occurs while reading input.
     */
    public Message createFirstPartEmail(String emitter, String destination, String subject) throws MessagingException, AddressException, IOException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(emitter));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(destination));
        message.setSubject(subject);
        return message;
    }
    /**
     * Creates a text message.
     *
     * @param emitter      The sender's email address.
     * @param destination  The recipient's email address.
     * @param subject      The subject of the email.
     * @param textMessage  The text content of the email.
     * @return The created Message object.
     * @throws MessagingException If an error occurs while creating the message.
     * @throws AddressException   If there is an error with the email addresses.
     * @throws IOException        If an error occurs while reading input.
     */
    private Message createTextMessage(String emitter, String destination, String subject, String textMessage) throws MessagingException, AddressException, IOException {
        Message message = createFirstPartEmail(emitter, destination, subject);
        message.setText(textMessage);
        return message;
    }
    /**
     * Creates an email message with attached files.
     *
     * @param emitter      The sender's email address.
     * @param destination  The recipient's email address.
     * @param subject      The subject of the email.
     * @param textMessage  The text content of the email.
     * @param pathFile     The path to the file to be attached.
     * @return The created Message object.
     * @throws MessagingException If an error occurs while creating the message.
     * @throws AddressException   If there is an error with the email addresses.
     * @throws IOException        If an error occurs while reading input.
     */
    public Message createMessageWithAttachedFiles(String emitter, String destination, String subject, String textMessage, String pathFile) throws MessagingException, AddressException, IOException {
        Message message = createFirstPartEmail(emitter, destination, subject);
        BodyPart bodyPart = new MimeBodyPart();
        bodyPart.setText(textMessage);
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.attachFile(new File(pathFile));
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(bodyPart);
        multipart.addBodyPart(mimeBodyPart);
        message.setContent(multipart);
        return message;
    }
    /**
     * Creates an HTML email message.
     *
     * @param emitter      The sender's email address.
     * @param destination  The recipient's email address.
     * @param subject      The subject of the email.
     * @param htmlContent  The HTML content of the email.
     * @return The created Message object.
     * @throws MessagingException If an error occurs while creating the message.
     * @throws AddressException   If there is an error with the email addresses.
     * @throws IOException        If an error occurs while reading input.
     */
    private Message createMessageHTML(String emitter, String destination, String subject, String htmlContent) throws MessagingException, AddressException, IOException {
        Message mensaje = createFirstPartEmail(emitter, destination, subject);
        mensaje.setContent(htmlContent, "text/html; charset=utf-8");
        return mensaje;
    }
    /**
     * Sends an HTML email message.
     *
     * @param emitter       The sender's email address.
     * @param destination   The recipient's email address.
     * @param subject       The subject of the email.
     * @param htmlContent   The HTML content of the email.
     * @param directionHTML The email address used to connect.
     * @param password      The password of the email address.
     * @throws AddressException   If there is an error with the email addresses.
     * @throws MessagingException If an error occurs while sending the message.
     * @throws IOException        If an error occurs while reading input.
     */
    public void enviarMensajeHTML(String emitter, String destination, String subject, String htmlContent, String directionHTML, String password) throws AddressException, MessagingException, IOException {
        setPropertiesServerSMTP();
        Message message = createMessageHTML(emitter, destination, subject, htmlContent);
        Transport t = connectServerSTMP(directionHTML, password);
        t.sendMessage(message, message.getAllRecipients());
        t.close();
    }
    /**
     * Sends a text message.
     *
     * @param emisor          The sender's email address.
     * @param destinatario    The recipient's email address.
     * @param asunto          The subject of the email.
     * @param textoMensaje    The text content of the email.
     * @param direccionEmail  The email address used to connect.
     * @param password        The password of the email address.
     * @throws AddressException   If there is an error with the email addresses.
     * @throws MessagingException If an error occurs while sending the message.
     * @throws IOException        If an error occurs while reading input.
     */
    public void sendTextMessage(String emisor, String destinatario, String asunto, String textoMensaje, String direccionEmail, String password) throws AddressException, MessagingException, IOException {
        setPropertiesServerSMTP();
        Message mensaje = createTextMessage(emisor, destinatario, asunto, textoMensaje);
        Transport t = connectServerSTMP(direccionEmail, password);
        t.sendMessage(mensaje, mensaje.getAllRecipients());
        t.close();
    }
    /**
     * Sends a message with attached files.
     *
     * @param emitter         The sender's email address.
     * @param destination     The recipient's email address.
     * @param subject         The subject of the email.
     * @param textMessage     The text content of the email.
     * @param directionEmail  The email address used to connect.
     * @param password        The password of the email address.
     * @param pathFile        The path to the file to be attached.
     * @throws AddressException   If there is an error with the email addresses.
     * @throws MessagingException If an error occurs while sending the message.
     * @throws IOException        If an error occurs while reading input.
     */
    public void sendMessageWithAttachedFiles(String emitter, String destination, String subject, String textMessage, String directionEmail, String password, String pathFile) throws AddressException, MessagingException, IOException {
        setPropertiesServerSMTP();
        Message message = createMessageWithAttachedFiles(emitter, destination, subject, textMessage, pathFile);
        Transport t = connectServerSTMP(directionEmail, password);
        t.sendMessage(message, message.getAllRecipients());
        t.close();
    }

    /**
     * Retrieves the session for IMAP.
     *
     * @return The IMAP session.
     */
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
    /**
     * Reads the Inbox folder.
     *
     * @param mail     The email address.
     * @param password The email password.
     * @throws Exception If an error occurs while reading the Inbox folder.
     */
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

    /**
     * Sets the properties for the SMTP server.
     *
     * @param properties The properties to be set.
     */
    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    /**
     * Retrieves the properties.
     *
     * @return The properties.
     */
    public Properties getProperties() {
        return properties;
    }

    /**
     * Retrieves the session.
     *
     * @return The session.
     */
    public Session getSession() {
        return session;
    }

    /**
     * Sets the session.
     *
     * @param session The session to be set.
     */
    public void setSession(Session session) {
        this.session = session;
    }

}
