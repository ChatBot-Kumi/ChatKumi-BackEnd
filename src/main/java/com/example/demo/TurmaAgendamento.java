package com.example.demo;

// using arrays for beginner-friendly implementation

public class TurmaAgendamento {
    private String nome;
    private String dias;
    private String horario;
    private String modalidade;
    private int idadeMinima;
    private int idadeMaxima;

    public TurmaAgendamento(String nome, String dias, String horario, String modalidade, int idadeMinima, int idadeMaxima) {
        this.nome = nome;
        this.dias = dias;
        this.horario = horario;
        this.modalidade = modalidade;
        this.idadeMinima = idadeMinima;
        this.idadeMaxima = idadeMaxima;
    }

    public String getNome() { return nome; }
    public String getDias() { return dias; }
    public String getHorario() { return horario; }
    public String getModalidade() { return modalidade; }
    public int getIdadeMinima() { return idadeMinima; }
    public int getIdadeMaxima() { return idadeMaxima; }

    @Override
    public String toString() {
        return dias + " - " + horario;
    }

    public static TurmaAgendamento[] getTodasTurmas() {
        TurmaAgendamento[] todas = new TurmaAgendamento[] {
            new TurmaAgendamento("Karatê Kids Sábado 9h", "Sábado", "9h - 11h", "Karatê Shotokan", 5, 99),
            new TurmaAgendamento("Karatê Kids Seg/Qua 18h30", "Segunda e Quarta", "18h30 - 19h30", "Karatê Shotokan", 5, 99),
            new TurmaAgendamento("Karatê Adulto Seg/Qua 20h", "Segunda e Quarta", "20h - 21h", "Karatê Shotokan", 13, 99),
            new TurmaAgendamento("Karatê Geral Ter/Qui 19h30", "Terça e Quinta", "19h30 - 20h30", "Karatê Shotokan", 5, 99),

            new TurmaAgendamento("Taekwondo Kids Ter/Qui 18h30", "Terça e Quinta", "18h30 - 19h30", "Taekwondo", 6, 12),
            new TurmaAgendamento("Taekwondo Adulto Ter/Qui 19h30", "Terça e Quinta", "19h30 - 20h30", "Taekwondo", 13, 99),

            new TurmaAgendamento("Jiu Jitsu Geral Ter/Qui 19h30", "Terça e Quinta", "19h30 - 20h30", "Jiu Jitsu", 12, 99),

            new TurmaAgendamento("Ginástica 50+ Matinal", "Segunda, Quarta e Sexta", "08h30 - 09h30", "Ginástica 50+", 50, 99),

            new TurmaAgendamento("Ginástica Artística 5-8 anos", "Segunda e Quarta", "18h30 - 19h30", "Ginástica Artística", 5, 8),
            new TurmaAgendamento("Ginástica Artística 9-12 anos", "Segunda e Quarta", "19h30 - 20h30", "Ginástica Artística", 9, 12)
        };
        return todas;
    }

    public static TurmaAgendamento[] getTurmasPorModalidade(String modalidade) {
        try {
            TurmaAgendamento[] todas = getTodasTurmas();
            // primeiro conta quantas correspondem
            int count = 0;
            for (int i = 0; i < todas.length; i++) {
                if (todas[i].getModalidade().equalsIgnoreCase(modalidade)) {
                    count++;
                }
            }
            if (count == 0) {
                throw new IllegalArgumentException("Nenhuma turma encontrada para: " + modalidade);
            }
            TurmaAgendamento[] filtradas = new TurmaAgendamento[count];
            int idx = 0;
            for (int i = 0; i < todas.length; i++) {
                if (todas[i].getModalidade().equalsIgnoreCase(modalidade)) {
                    filtradas[idx++] = todas[i];
                }
            }
            return filtradas;
        } catch (IllegalArgumentException iae) {
            throw iae;
        } catch (Exception e) {
            throw new IllegalArgumentException("Erro ao buscar turmas para modalidade: " + modalidade, e);
        }
    }

    public static TurmaAgendamento[] getTurmasCompativeis(String modalidade, int idade) {
        try {
            TurmaAgendamento[] porModalidade = getTurmasPorModalidade(modalidade);
            int count = 0;
            for (int i = 0; i < porModalidade.length; i++) {
                TurmaAgendamento turma = porModalidade[i];
                if (idade >= turma.getIdadeMinima() && idade <= turma.getIdadeMaxima()) {
                    count++;
                }
            }
            TurmaAgendamento[] compat = new TurmaAgendamento[count];
            int idx = 0;
            for (int i = 0; i < porModalidade.length; i++) {
                TurmaAgendamento turma = porModalidade[i];
                if (idade >= turma.getIdadeMinima() && idade <= turma.getIdadeMaxima()) {
                    compat[idx++] = turma;
                }
            }
            return compat;
        } catch (IllegalArgumentException iae) {
            throw iae;
        } catch (Exception e) {
            throw new IllegalArgumentException("Erro ao filtrar turmas compatíveis.", e);
        }
    }

    public static boolean existeTurmaUnica(String modalidade) {
        return getTurmasPorModalidade(modalidade).length <= 1;
    }

    public static TurmaAgendamento getTurmaUnica(String modalidade) {
        TurmaAgendamento[] turmas = getTurmasPorModalidade(modalidade);
        if (turmas.length != 1) {
            throw new IllegalArgumentException("Modalidade não possui uma única turma: " + modalidade);
        }
        return turmas[0];
    }
}