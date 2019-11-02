package psoft.ufcg.ajude.Entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import psoft.ufcg.ajude.Enum.StatusCampanha;

import javax.persistence.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashSet;

@Entity
public class Campanha {


    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "campanha_sequence")
    @SequenceGenerator(name = "campanha_sequence", sequenceName = "camp_seq")
    private long identificador;

    private String nome;
    @Id
    private String urlCampanha;
    private String descricao;

    @JsonSerialize(as = Date.class)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date deadline;

    private StatusCampanha status;
    private Double meta;
    private String emailDono;
    private HashSet<String> likes;
    //  private HashSet<Comentario> comentarios;

    private static final String dateFormat = "yyyy-MM-dd";
    private static final String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> {
            builder.simpleDateFormat(dateTimeFormat);
            builder.serializers(new LocalDateSerializer(DateTimeFormatter.ofPattern(dateFormat)));
            builder.serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(dateTimeFormat)));
        };
    }

    public Campanha(String nome, String descricao, Date deadLine, String emailDono, StatusCampanha status){
        this.nome = nome;
        this.descricao = descricao;
        this.deadline = deadLine;
        this.emailDono = emailDono;
        this.status = status;

    }

    public Campanha(){
    }

    public long getIdentificador() {
        return identificador;
    }

    public void setIdentificador(long identificador) {
        this.identificador = identificador;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUrlCampanha() {
        return urlCampanha;
    }

    public void setUrlCampanha(String urlCampanha) {
        this.urlCampanha = urlCampanha;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public StatusCampanha getStatus() {
        return status;
    }

    public void setStatus(StatusCampanha status) {
        this.status = status;
    }

    public Double getMeta() {
        return meta;
    }

    public void setMeta(Double meta) {
        this.meta = meta;
    }

    public String getEmailDono() {
        return emailDono;
    }

    public void setEmailDono(String emailDono) {
        this.emailDono = emailDono;
    }

    public void adicionaLikes(String email) {
        this.likes.add(email);
    }

//    public int getQuantidadeLikes(){
//        return this.likes.size();
//    }

//    public void setComentarios(HashSet<Comentario> comentarios) {
//        this.comentarios = comentarios;
//    }

    public void setLikes(HashSet<String> likes) {
        this.likes = likes;
    }

//    public HashSet<Comentario> getComentarios() {
//        return comentarios;
//    }

    public HashSet<String> getLikes() {
        return likes;
    }
}
