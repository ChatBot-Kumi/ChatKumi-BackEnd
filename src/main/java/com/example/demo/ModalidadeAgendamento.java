package com.example.demo;

// Arquivo que representa uma modalidade para agendamento

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

    public static ModalidadeAgendamento[] getTodasModalidades() {
        return new ModalidadeAgendamento[] {
            new ModalidadeAgendamento("Karatê Shotokan", 5, 99),
            new ModalidadeAgendamento("Taekwondo", 6, 99),
            new ModalidadeAgendamento("Jiu Jitsu", 12, 99),
            new ModalidadeAgendamento("Ginástica 50+", 50, 99),
            new ModalidadeAgendamento("Ginástica Artística", 5, 12)
        };
    }

    public static String[] getNomesModalidades() {
        ModalidadeAgendamento[] modalidades = getTodasModalidades();
        String[] nomes = new String[modalidades.length];
        for (int i = 0; i < modalidades.length; i++) {
            nomes[i] = modalidades[i].getNome();
        }
        return nomes;
    }

    public static ModalidadeAgendamento getModalidadePorNome(String nome) {
        try {
            ModalidadeAgendamento[] modalidades = getTodasModalidades();
            for (ModalidadeAgendamento m : modalidades) {
                if (m.getNome().equalsIgnoreCase(nome)) return m;
            }
            throw new IllegalArgumentException("Modalidade não encontrada: " + nome);
        } catch (IllegalArgumentException iae) {
            throw iae;
        } catch (Exception e) {
            throw new IllegalArgumentException("Erro ao buscar modalidade: " + nome, e);
        }
    }
}