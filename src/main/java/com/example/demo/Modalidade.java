package com.example.demo;

public class Modalidade {
    private String nome;
    private String descricaoGeral;
    private Turma[] turmas;

    public Modalidade(String nome, String descricaoGeral, Turma[] turmas) {
        this.nome = nome;
        this.descricaoGeral = descricaoGeral;
        this.turmas = turmas;
    }
    
    public String getNome() { 
        return nome; 
    }

    public String getDescricaoGeral() { 
        return descricaoGeral; 
    }

    public Turma[] getTurmas() { 
        return turmas; 
    }
}