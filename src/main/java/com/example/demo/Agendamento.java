package com.example.demo; 

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Agendamento {
    
    public static class AgendamentoExperimental {
        private String nomeAluno;
        private int idade;
        private String modalidade;
        private TurmaAgendamento turma; 
        private String dataAula;

        public AgendamentoExperimental(String nomeAluno, int idade, String modalidade, TurmaAgendamento turma, String dataAula) {
            this.nomeAluno = nomeAluno;
            this.idade = idade;
            this.modalidade = modalidade;
            this.turma = turma;
            this.dataAula = dataAula;
        }
        
        public String getNomeAluno() { return nomeAluno; }
        public int getIdade() { return idade; }
        public String getModalidade() { return modalidade; }
        public TurmaAgendamento getTurma() { return turma; }
        public String getDataAula() { return dataAula; }
        
        public Map<String, Object> toMap() {
             Map<String, Object> mapa = new HashMap<>();
             mapa.put("nomeAluno", nomeAluno);
             mapa.put("idade", idade);
             mapa.put("modalidade", modalidade);
             mapa.put("turma", turma.toString());
             mapa.put("diasAula", turma.getDias());
             mapa.put("horarioAula", turma.getHorario());
             mapa.put("dataAula", dataAula);
             return mapa;
        }
    }

    private final List<AgendamentoExperimental> agendamentosConcluidos = new ArrayList<>(); 


    public ResultadoAgendamento coletarNome(String nome) {
        try {
            validarNome(nome);
            Map<String, Object> dados = new HashMap<>();
            dados.put("nome", nome);

            return new ResultadoAgendamento(true, 
                "1. Nome validado com sucesso.\n2 - Qual a idade de " + nome + "?", 
                dados);

        } catch (IllegalArgumentException e) {
            return new ResultadoAgendamento(false, 
                e.getMessage() + "\n1 - Por favor, digite o nome do aluno que fará a aula novamente.", 
                new HashMap<>());
        } catch (Exception e) {
            throw new IllegalArgumentException("Erro inesperado ao validar nome.", e);
        }
    }

    public ResultadoAgendamento coletarIdade(String idadeInput, String nomeAluno) {
        try {
            int idade = validarEConverterIdade(idadeInput);

            Map<String, Object> dados = new HashMap<>();
            dados.put("nome", nomeAluno);
            dados.put("idade", idade);

            String listaModalidades = ModalidadeAgendamento.getNomesModalidades().stream()
                .collect(Collectors.joining(", "));

            return new ResultadoAgendamento(true, 
                "2. Idade validada (" + idade + " anos).\n3 - Para qual aula " + nomeAluno + " gostaria de agendar a experimental?\n" +
                "Modalidades disponíveis: " + listaModalidades + ".", 
                dados);

        } catch (IllegalArgumentException e) {
            Map<String, Object> dados = new HashMap<>();
            dados.put("nome", nomeAluno); 
            return new ResultadoAgendamento(false, 
                e.getMessage() + "\n2 - Por favor, digite a idade novamente.", 
                dados);
        } catch (Exception e) {
            throw new IllegalArgumentException("Erro inesperado ao validar idade.", e);
        }
    }

    public ResultadoAgendamento selecionarModalidade(String modalidade, String nomeAluno, int idade) {
        try {
            ModalidadeAgendamento modalidadeObj = ModalidadeAgendamento.getModalidadePorNome(modalidade); 
            
            if (idade < modalidadeObj.getIdadeMinima() || idade > modalidadeObj.getIdadeMaxima()) {
                throw new IllegalArgumentException(
                    "Idade " + idade + " não é compatível com " + modalidadeObj.getNome() + 
                    " (idade permitida: " + modalidadeObj.getIdadeMinima() + " a " + modalidadeObj.getIdadeMaxima() + " anos)."
                );
            }
            
            Map<String, Object> dados = new HashMap<>();
            dados.put("nome", nomeAluno);
            dados.put("idade", idade);
            dados.put("modalidade", modalidadeObj.getNome());
            
            List<TurmaAgendamento> turmasCompativeis = TurmaAgendamento.getTurmasCompativeis(modalidadeObj.getNome(), idade);
            
            if (turmasCompativeis.isEmpty()) {
                 throw new IllegalArgumentException("Não encontramos nenhuma turma disponível para a modalidade " + modalidadeObj.getNome() + " e idade " + idade + ".");
            }
            
            if (turmasCompativeis.size() == 1) { 
                TurmaAgendamento turmaUnica = turmasCompativeis.get(0);
                dados.put("turma", turmaUnica);
                
                ResultadoAgendamento resDatas = listarDatasDisponiveis(turmaUnica.getDias());
                dados.putAll(resDatas.getDados()); 
                
                String mensagemTurmaUnica = "3. Modalidade **" + modalidadeObj.getNome() + "** selecionada.\n" + 
                                            "Turma única compatível: " + turmaUnica.getDias() + " às " + turmaUnica.getHorario() + ".\n\n" +
                                            "5 - " + resDatas.getMensagem();
                
                return new ResultadoAgendamento(true, mensagemTurmaUnica, dados);
                
            } else {
                dados.put("turmas", turmasCompativeis);
                
                StringBuilder sb = new StringBuilder();
                sb.append("3. Modalidade **").append(modalidadeObj.getNome()).append("** selecionada. \n4 - Para qual turma? (Compatível com ").append(idade).append(" anos)");
                
                for (int i = 0; i < turmasCompativeis.size(); i++) {
                    TurmaAgendamento t = turmasCompativeis.get(i);
                    sb.append("\n").append(i + 1).append(". ").append(t.getDias()).append(" às ").append(t.getHorario());
                }
                
                return new ResultadoAgendamento(true, sb.toString(), dados);
            }
            
        } catch (IllegalArgumentException e) {
            Map<String, Object> dados = new HashMap<>();
            dados.put("nome", nomeAluno);
            dados.put("idade", idade);
            String listaModalidades = ModalidadeAgendamento.getNomesModalidades().stream()
                .collect(Collectors.joining(", "));
                
            return new ResultadoAgendamento(false, 
                e.getMessage() + 
                "\n3 - Por favor, digite o nome da modalidade novamente. Opções: " + listaModalidades + ".", 
                dados);
        } catch (Exception e) {
            throw new IllegalArgumentException("Erro interno ao selecionar modalidade.", e);
        }

    }

    public ResultadoAgendamento selecionarTurma(String indiceTurmaStr, String modalidade, String nomeAluno, int idade) {
        try {
            int indiceTurma = Integer.parseInt(indiceTurmaStr.trim()) - 1;
            
            List<TurmaAgendamento> turmasCompativeis = TurmaAgendamento.getTurmasCompativeis(modalidade, idade);
            
            if (indiceTurma < 0 || indiceTurma >= turmasCompativeis.size()) {
                throw new IllegalArgumentException("Número de turma inválido.");
            }
            
            TurmaAgendamento turmaSelecionada = turmasCompativeis.get(indiceTurma);
            
            Map<String, Object> dados = new HashMap<>();
            dados.put("nome", nomeAluno);
            dados.put("idade", idade);
            dados.put("modalidade", modalidade);
            dados.put("turma", turmaSelecionada);
            
            ResultadoAgendamento resDatas = listarDatasDisponiveis(turmaSelecionada.getDias());
            dados.putAll(resDatas.getDados()); 
            
            String mensagemSucesso = "4. Turma selecionada: " + turmaSelecionada.getDias() + " às " + turmaSelecionada.getHorario() + 
                                     ".\n\n5 - " + resDatas.getMensagem();
            
            return new ResultadoAgendamento(true, mensagemSucesso, dados);
            
        } catch (NumberFormatException e) {
            return new ResultadoAgendamento(false, 
                "Por favor, digite apenas o **número** da turma desejada.", 
                new HashMap<>());
        } catch (IllegalArgumentException e) {
            List<TurmaAgendamento> turmasCompativeis = TurmaAgendamento.getTurmasCompativeis(modalidade, idade);
            StringBuilder sb = new StringBuilder();
            sb.append(e.getMessage()).append("\n4 - Escolha um número válido das opções:");
            for (int i = 0; i < turmasCompativeis.size(); i++) {
                TurmaAgendamento t = turmasCompativeis.get(i);
                sb.append("\n").append(i + 1).append(". ").append(t.getDias()).append(" às ").append(t.getHorario());
            }

            Map<String, Object> dados = new HashMap<>();
            dados.put("nome", nomeAluno);
            dados.put("idade", idade);
            dados.put("modalidade", modalidade);

            return new ResultadoAgendamento(false, sb.toString(), dados);
        } catch (Exception e) {
            throw new IllegalArgumentException("Erro interno ao selecionar turma.", e);
        }
    }

    public ResultadoAgendamento selecionarData(String indiceDataStr, String nomeAluno, int idade, String modalidade, TurmaAgendamento turma) {
        try {
            int indiceData = Integer.parseInt(indiceDataStr.trim()) - 1;

            String primeiroDia = extrairPrimeiroDiaSemana(turma.getDias());
            DiaSemana diaSemana = new DiaSemana(primeiroDia); 
            diaSemana.calcularProximasDatas(); 
            String[] proximasDatas = diaSemana.getDatas();

            if (indiceData < 0 || indiceData >= proximasDatas.length) {
                throw new IllegalArgumentException("Número de data inválido.");
            }

            String dataSelecionada = proximasDatas[indiceData];

            AgendamentoExperimental agendamento = new AgendamentoExperimental(nomeAluno, idade, modalidade, turma, dataSelecionada);
            agendamentosConcluidos.add(agendamento);

            String mensagemConfirmacao = String.format(
                "6 - SUA AULA EXPERIMENTAL FOI MARCADA!\n" +
                "==========================================\n" +
                "Data: **%s**\n" +
                "Turma: **%s** - %s, %s\n" +
                "Aluno: %s, %d anos\n" +
                "==========================================",
                dataSelecionada, modalidade.toUpperCase(), turma.getDias(), turma.getHorario(), nomeAluno, idade
            );

            Map<String, Object> dados = new HashMap<>();
            dados.put("agendamento", agendamento.toMap());

            return new ResultadoAgendamento(true, mensagemConfirmacao, dados);

        } catch (NumberFormatException nfe) {
            Map<String, Object> dados = new HashMap<>();
            dados.put("nome", nomeAluno);
            dados.put("idade", idade);
            dados.put("modalidade", modalidade);
            dados.put("turma", turma);
            ResultadoAgendamento resDatas = listarDatasDisponiveis(turma.getDias());
            dados.putAll(resDatas.getDados());

            return new ResultadoAgendamento(false,
                "Por favor, digite apenas o número da data.",
                dados);
        } catch (IllegalArgumentException iae) {
            Map<String, Object> dados = new HashMap<>();
            dados.put("nome", nomeAluno);
            dados.put("idade", idade);
            dados.put("modalidade", modalidade);
            dados.put("turma", turma);
            ResultadoAgendamento resDatas = listarDatasDisponiveis(turma.getDias());
            dados.putAll(resDatas.getDados());

            return new ResultadoAgendamento(false,
                iae.getMessage() + "\n\n5 - " + resDatas.getMensagem(),
                dados);
        } catch (Exception e) {
            throw new IllegalArgumentException("Erro inesperado ao selecionar data.", e);
        }
    }

    public ResultadoAgendamento listarDatasDisponiveis(String diasAula) {
        try {
            String primeiroDia = extrairPrimeiroDiaSemana(diasAula);
            DiaSemana diaSemana = new DiaSemana(primeiroDia); 
            diaSemana.calcularProximasDatas(); 
            String[] proximasDatas = diaSemana.getDatas();
            
            StringBuilder sb = new StringBuilder();
            sb.append("Qual data para a sua aula experimental?\n" +
                      "Datas disponíveis para o dia da semana: **").append(primeiroDia.toUpperCase()).append("**:");
            
            for (int i = 0; i < proximasDatas.length; i++) {
                sb.append("\n").append(i + 1).append(". ").append(proximasDatas[i]);
            }

            Map<String, Object> dados = new HashMap<>();
            dados.put("datas", Arrays.asList(proximasDatas));

            return new ResultadoAgendamento(true, sb.toString(), dados);
            
        } catch (IllegalArgumentException e) {
            Map<String, Object> dados = new HashMap<>();
            dados.put("erro", e.getMessage());
            return new ResultadoAgendamento(false, "Erro ao listar datas: " + e.getMessage(), dados);
        } catch (Exception e) {
            throw new IllegalArgumentException("Erro ao listar datas.", e);
        }
    }
    
    private void validarNome(String nome) {
        if (nome.isEmpty()) throw new IllegalArgumentException("Nome não pode estar vazio.");
        if (nome.length() < 2) throw new IllegalArgumentException("Nome deve ter pelo menos 2 caracteres.");
        if (!nome.matches("^[a-zA-ZÀ-ÿ\\s]{2,}$")) {
            throw new IllegalArgumentException("Nome deve conter apenas letras e espaços.");
        }
    }

    private int validarEConverterIdade(String input) {
        if (input.isEmpty()) throw new IllegalArgumentException("Idade não pode estar vazia.");
        try {
            int idade = Integer.parseInt(input);
            if (idade < 0) throw new IllegalArgumentException("Idade não pode ser negativa.");
            if (idade > 120) throw new IllegalArgumentException("Idade deve ser menor que 120 anos.");
            return idade;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Idade deve ser um número válido.");
        }
    }

    private String extrairPrimeiroDiaSemana(String dias) {
        if (dias.contains("Segunda")) return "segunda";
        if (dias.contains("Terça") || dias.contains("Terca")) return "terça";
        if (dias.contains("Quarta")) return "quarta";
        if (dias.contains("Quinta")) return "quinta";
        if (dias.contains("Sexta")) return "sexta";
        if (dias.contains("Sábado") || dias.contains("Sabado")) return "sábado";
        throw new IllegalArgumentException("Dia da semana não reconhecido: " + dias);
    }
}