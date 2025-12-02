public class Duvida {
    private String pergunta;
    private String resposta;
    private String palavraChave; 

    public Duvida(String pergunta, String resposta, String palavraChave) {
        this.pergunta = pergunta;
        this.resposta = resposta;
        this.palavraChave = palavraChave;
    }

    public String getPergunta() { return pergunta; }
    public String getResposta() { return resposta; }
    public String getPalavraChave() { return palavraChave; }
}