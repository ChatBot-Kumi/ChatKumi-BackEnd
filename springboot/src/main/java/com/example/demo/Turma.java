package com.example.demo;

public class Turma {
    private String faixaEtaria; 
    private String horarios; 

    public Turma(String faixaEtaria, String horarios) {
        this.faixaEtaria = faixaEtaria;
        this.horarios = horarios;
    }
    
    // Getters
    public String getFaixaEtaria() { 
        return faixaEtaria; 
    }

    public String getHorarios() { 
        return horarios; 
    }
}
