package com.example.demo;

import java.util.HashMap;
import java.util.Map;

public class ResultadoAgendamento {
    private final boolean sucesso;
    private final String mensagem;
    private final Map<String, Object> dados;

    public ResultadoAgendamento(boolean sucesso, String mensagem, Map<String, Object> dados) {
        this.sucesso = sucesso;
        this.mensagem = mensagem;
        this.dados = dados != null ? dados : new HashMap<>();
    }

    public boolean isSucesso() { return sucesso; }
    public String getMensagem() { return mensagem; }
    public Map<String, Object> getDados() { return dados; }
}