package com.example.demo;

import java.util.List;

public class Modalidade {
    private String nome;
    private String descricaoGeral;
    private List<Turma> turmas;

    public Modalidade(String nome, String descricaoGeral, List<Turma> turmas) {
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

    public List<Turma> getTurmas() { 
        return turmas; 
    }
}