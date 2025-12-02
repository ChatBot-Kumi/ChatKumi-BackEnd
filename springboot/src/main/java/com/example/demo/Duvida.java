package com.example.demo;

public class Duvida {
    private String pergunta;
    private String resposta;
    private String palavraChave; 

    public Duvida(String pergunta, String resposta, String palavraChave) {
        this.pergunta = pergunta;
        this.resposta = resposta;
        this.palavraChave = palavraChave;
    }

    // Getters
    public String getPergunta() { 
        return pergunta; 
    }

    public String getResposta() { 
        return resposta; 
    }

    public String getPalavraChave() { 
        return palavraChave; 
    }
}
