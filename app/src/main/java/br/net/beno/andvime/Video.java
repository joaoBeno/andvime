package br.net.beno.andvime;

/**
 * Created by joao.junior on 03/09/2015.
 */
public class Video {
    int ID;
    String titulo;
    String descricao;
    String duracao;
    String link;
    String imagem;

    Video(int ID, String titulo, String descricao, int duracao, String link, String imagem) {
        this.ID = ID;
        this.titulo = titulo;
        this.descricao = descricao;
        this.link = link;
        this.imagem = imagem;

        if (duracao<3600) {
            this.duracao = String.format("%02d", duracao/60) + ":" + String.format("%02d", duracao % 60);
        }

    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDuracao() {
        return duracao;
    }

    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }
}
