package psoft.ufcg.ajude.services;

import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


@Service
public class EmailService {

    public EmailService(){

    }

    public static void enviaEmail(String email) throws RuntimeException {
        Properties props = new Properties();
        /** Parâmetros de conexão com servidor Gmail */
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication()
                    {
                        return new PasswordAuthentication("ajude.org@gmail.com",
                                "ajude2019");
                    }
                });

    /** Ativa Debug para sessão
    session.setDebug(true); */

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("ajude.org@gmail.com"));
            //Remetente

            Address[] toUser = InternetAddress //Destinatário(s)
                    .parse(email);

            message.setRecipients(Message.RecipientType.TO, toUser);
            message.setSubject("AJuDE");//Assunto
            message.setText("Obrigado por ser cadastrar em nosso site" +
                    "volte para nosso site https://ajudepsoftw.herokuapp.com/");
            Transport.send(message);


        } catch (MessagingException e) {
           throw  new RuntimeException(e);
        }
    }
}

