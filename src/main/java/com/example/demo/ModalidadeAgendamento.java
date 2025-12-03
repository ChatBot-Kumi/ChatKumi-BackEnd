package com.example.demo;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ModalidadeAgendamento {
    private String nome;
    private int idadeMinima;
    private int idadeMaxima;

    public ModalidadeAgendamento(String nome, int idadeMinima, int idadeMaxima) {
        this.nome = nome;
        this.idadeMinima = idadeMinima;
        this.idadeMaxima = idadeMaxima;
    }

    public String getNome() { return nome; }
    public int getIdadeMinima() { return idadeMinima; }
    public int getIdadeMaxima() { return idadeMaxima; }

    public static List<ModalidadeAgendamento> getTodasModalidades() {
        return Arrays.asList(
            new ModalidadeAgendamento("Karatê Shotokan", 5, 99),
            new ModalidadeAgendamento("Taekwondo", 6, 99),
            new ModalidadeAgendamento("Jiu Jitsu", 12, 99),
            new ModalidadeAgendamento("Ginástica 50+", 50, 99),
            new ModalidadeAgendamento("Ginástica Artística", 5, 12)
        );
    }

    public static List<String> getNomesModalidades() {
        return getTodasModalidades().stream()
            .map(ModalidadeAgendamento::getNome)
            .collect(Collectors.toList());
    }

    public static ModalidadeAgendamento getModalidadePorNome(String nome) {
        try {
            return getTodasModalidades().stream()
                .filter(modalidade -> modalidade.getNome().equalsIgnoreCase(nome))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Modalidade não encontrada: " + nome));
        } catch (IllegalArgumentException iae) {
            throw iae;
        } catch (Exception e) {
            throw new IllegalArgumentException("Erro ao buscar modalidade: " + nome, e);
        }
    }
}