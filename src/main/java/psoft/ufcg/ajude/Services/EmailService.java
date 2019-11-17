package psoft.ufcg.ajude.Services;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.io.IOException;


@Service
public class EmailService {
    @Value("${app.sendgrid.key}")
    private SendGrid appkey;

    @Value("${app.sendgrid.templateId}")
    private String templateId;

    public String sendEmail(String email) {

        try {
        Mail mail = prepareMail(email);
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());
        Response response = appkey.api(request);

        if (response != null)
            System.out.println("response code from sendgrid" + response.getHeaders());

        } catch (IOException e) {
            e.printStackTrace();
            return "Erro ao enviar o email";
        }
        return "Email enviado com sucesso";
    }

    private Mail prepareMail(String email) {
        Email fromEmail = new Email();
        fromEmail.setEmail("jpsobral2000@gmail.com");
        String sujeito = "AJuDE";
        Email to = new Email();
        to.setEmail(email);
        Content content = new Content("text/html", "Seja Bem Vindo");
        Mail mail = new Mail(fromEmail, sujeito, to, content);
        mail.setTemplateId(templateId);
        return mail;
    }
}
