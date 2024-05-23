package org.example.proyectotfg.functions;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.sun.mail.imap.IMAPFolder;
//import javax.mail.AddressException;

public class SenderReaderMail {

    private Properties propiedades;
    private Session sesion;

    public void setPropiedadesServidorSMTP() {
        propiedades = System.getProperties();
        propiedades.put("mail.smtp.auth", "true");
        propiedades.put("mail.smtp.host", "smtp.gmail.com");
        propiedades.put("mail.smtp.port", "587");
        propiedades.put("mail.smtp.starttls.enable", "true");
        sesion = Session.getInstance(propiedades, null);
    }

    private Transport conectarServidorSMTP(String direccionEmail, String password) throws NoSuchProviderException, MessagingException {
        Transport t = (Transport) sesion.getTransport("smtp");
        t.connect(propiedades.getProperty("mail.smtp.host"), direccionEmail, password);
        return t;
    }


    public Message crearNucleoMensaje(String emisor, String destinatario, String asunto) throws MessagingException, AddressException, IOException {
        Message mensaje = new MimeMessage(sesion);
        mensaje.setFrom(new InternetAddress(emisor));
        mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
        mensaje.setSubject(asunto);
        return mensaje;
    }

    private Message crearMensajeTexto(String emisor, String destinatario, String asunto, String textoMensaje) throws MessagingException, AddressException, IOException {
        Message mensaje = crearNucleoMensaje(emisor, destinatario, asunto);
        mensaje.setText(textoMensaje);
        return mensaje;
    }

    public Message crearMensajeConAdjunto(String emisor, String destinatario, String asunto, String textoMensaje, String pathFichero) throws MessagingException, AddressException, IOException {
        Message mensaje = crearNucleoMensaje(emisor, destinatario, asunto);
        //Cuerpo del mensaje
        BodyPart bodyPart = new MimeBodyPart();
        bodyPart.setText(textoMensaje);
        //Adjunto del mensaje
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.attachFile(new File(pathFichero));
        //Composición del mensaje (Cuerpo+Adjunto)
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(bodyPart);
        multipart.addBodyPart(mimeBodyPart);
        mensaje.setContent(multipart);
        return mensaje;
    }

    private Message crearMensajeHTML(String emisor, String destinatario, String asunto, String htmlContent) throws MessagingException, AddressException, IOException {
        Message mensaje = crearNucleoMensaje(emisor, destinatario, asunto);
        mensaje.setContent(htmlContent, "text/html; charset=utf-8");
        return mensaje;
    }

    public void enviarMensajeHTML(String emisor, String destinatario, String asunto, String htmlContent, String direccionEmail, String password) throws AddressException, MessagingException, IOException {
        setPropiedadesServidorSMTP();
        Message mensaje = crearMensajeHTML(emisor, destinatario, asunto, htmlContent);
        Transport t = conectarServidorSMTP(direccionEmail, password);
        t.sendMessage(mensaje, mensaje.getAllRecipients());
        t.close();
    }

    public void enviarMensajeTexto(String emisor, String destinatario, String asunto, String textoMensaje, String direccionEmail, String password) throws AddressException, MessagingException, IOException {
        setPropiedadesServidorSMTP();
        Message mensaje = crearMensajeTexto(emisor, destinatario, asunto, textoMensaje);
        Transport t = conectarServidorSMTP(direccionEmail, password);
        t.sendMessage(mensaje, mensaje.getAllRecipients());
        t.close();
        return;
    }

    public void enviarMensajeConAdjunto(String emisor, String destinatario, String asunto, String textoMensaje, String direccionEmail, String password, String pathFichero) throws AddressException, MessagingException, IOException {
        setPropiedadesServidorSMTP();
        Message mensaje = crearMensajeConAdjunto(emisor, destinatario, asunto, textoMensaje, pathFichero);
        Transport t = conectarServidorSMTP(direccionEmail, password);
        t.sendMessage(mensaje, mensaje.getAllRecipients());
        t.close();
        return;
    }

    //Leer Mensaje
    private Session getSesionImap() {
        Properties propiedades = new Properties();
        propiedades.setProperty("mail.store.protocol", "imap");
        propiedades.setProperty("mail.imap.host", "imap.gmail.com");
        propiedades.setProperty("mail.imap.port", "993");
        propiedades.setProperty("mail.imap.ssl.enable", "true");
        propiedades.setProperty("mail.imap.ssl.trust", "imap.gmail.com");
        Session sesion = Session.getDefaultInstance(propiedades);
        return sesion;
    }

    public void leerCarpetaInbox(String email, String password) throws Exception {

        Session sesion = this.getSesionImap();
        Store almacen = sesion.getStore("imaps");
        almacen.connect("imap.gmail.com", 993, email, password);
        IMAPFolder inbox = (IMAPFolder) almacen.getFolder("INBOX");
        inbox.open(Folder.READ_WRITE);
        Message[] mensajes = inbox.getMessages();
        for (int i = 0; i < mensajes.length; i++) {
            Message mensaje = mensajes[i];
            Address[] direccionesOrigen = mensaje.getFrom();
            String from = direccionesOrigen[0].toString();
            Address[] direccionesDestino = mensaje.getRecipients(Message.RecipientType.TO);
            String to = direccionesDestino[0].toString();
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

    public void setPropiedades(Properties propiedades) {
        this.propiedades = propiedades;
    }

    public Properties getPropiedades() {
        return propiedades;
    }

    public Session getSesion() {
        return sesion;
    }

    public void setSesion(Session sesion) {
        this.sesion = sesion;
    }
}
