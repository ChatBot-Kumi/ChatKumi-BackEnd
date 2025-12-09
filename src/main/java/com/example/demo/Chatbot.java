
//Arquivo que contém a lógica principal do chatbot

package com.example.demo;

import java.util.Map;
import java.util.HashMap;
import org.springframework.stereotype.Service; 



@Service
public class Chatbot { 
    //atributos
    private Modalidade[] infantil;
    private Modalidade[] adulto;
    private Duvida[] duvidas;
    private Agendamento agendamentoService;
 
    //usado para controlar em qual parte da conversa o usuário está
    private enum Estado { 
        MENU_PRINCIPAL, AULAS_INFANTIL, AULAS_ADULTO, DUVIDAS, 
        AGENDAMENTO_INICIO, AGENDAMENTO_COLETANDO_NOME, AGENDAMENTO_COLETANDO_IDADE, 
        AGENDAMENTO_SELECIONANDO_MODALIDADE, AGENDAMENTO_SELECIONANDO_TURMA, 
        AGENDAMENTO_SELECIONANDO_DATA, AGENDAMENTO_FINALIZADO
    }
    private Estado estadoAtual;
    
    private Map<String, Object> dadosAgendamento;

    public Chatbot() {
        this.infantil = DadosAcademia.carregarModalidadesInfantil();
        this.adulto = DadosAcademia.carregarModalidadesAdulto();
        this.duvidas = DadosAcademia.carregarDuvidasFrequentes();
        this.estadoAtual = Estado.MENU_PRINCIPAL;
        this.agendamentoService = new Agendamento(); 
        this.dadosAgendamento = new HashMap<>();
    }
    
    public String processarMensagem(String mensagem) {
        String msgLower = mensagem.toLowerCase().trim();
        
        if (msgLower.equals("sair") || msgLower.equals("tchau") || msgLower.contains("obrigado")) {
            estadoAtual = Estado.MENU_PRINCIPAL; 
            dadosAgendamento.clear(); 
            return "Agradecemos o seu contato e esperamos te ver em breve! Tchau!" ;
        }
        
        if (msgLower.equals("olá") || msgLower.equals("oi") || msgLower.contains("tudo bem?") || estadoAtual == Estado.AGENDAMENTO_FINALIZADO) {
            estadoAtual = Estado.MENU_PRINCIPAL;
            dadosAgendamento.clear();
            return getMenuPrincipal();
        }
    
        if (msgLower.equals("voltar") || msgLower.equals("menu")) {
            estadoAtual = Estado.MENU_PRINCIPAL;
            dadosAgendamento.clear();
            return getMenuPrincipal();
        }

        if (estadoAtual.name().startsWith("AGENDAMENTO_")) {
            return processarFluxoAgendamento(msgLower);
        }


        try {
            switch (estadoAtual) {
                case MENU_PRINCIPAL:
                    return processarMenuPrincipal(msgLower);
                case AULAS_INFANTIL:
                    return processarAulas(msgLower, infantil, "Aulas Infantis");
                case AULAS_ADULTO:
                    return processarAulas(msgLower, adulto, "Aulas para Adultos");
                case DUVIDAS:
                    return processarDuvidas(msgLower);
                default:
                    estadoAtual = Estado.MENU_PRINCIPAL;
                    return getMenuPrincipal();
            }
        } catch (IllegalArgumentException iae) {
            return iae.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            estadoAtual = Estado.MENU_PRINCIPAL;
            dadosAgendamento.clear();
            return "Erro interno. Por favor, tente novamente mais tarde.";
        }
    } 

    private String getMenuPrincipal() {
        return "Como posso te ajudar hoje?\n" +
            "Escolha uma opção:\n" +
            "1. Aulas para Crianças (Kids)\n" +
            "2. Aulas para Adultos (Adulto)\n" +
            "3. Dúvidas Frequentes (Duvidas)\n" +
            "4. Agendar Aula Experimental (Agendar)\n" + 
            "Digite 'Sair' a qualquer momento.";
    }

    private String processarMenuPrincipal(String msg) {
        if (msg.contains("Criança") || msg.contains("1") || msg.contains("Kids")) {
            estadoAtual = Estado.AULAS_INFANTIL;
            StringBuilder nomesInf = new StringBuilder();
            for (int i = 0; i < infantil.length; i++) {
                if (i > 0) nomesInf.append(", ");
                nomesInf.append(infantil[i].getNome());
            }
            return "Entendido! Menu Aulas Infantis.\nModalidades disponíveis: " + nomesInf.toString() + ".\nDigite o nome da modalidade ou 'Voltar'.";
        }
        if (msg.contains("Adulto") || msg.contains("2")) {
            estadoAtual = Estado.AULAS_ADULTO;
            StringBuilder nomesAd = new StringBuilder();
            for (int i = 0; i < adulto.length; i++) {
                if (i > 0) nomesAd.append(", ");
                nomesAd.append(adulto[i].getNome());
            }
            return "Entendido! Menu Aulas Adulto.\nModalidades disponíveis: " + nomesAd.toString() + ".\nDigite o nome da modalidade ou 'Voltar'.";
        }
        if (msg.contains("Dúvidas") || msg.contains("3") || msg.contains("Frequentes")) {
            estadoAtual = Estado.DUVIDAS;
            return "Entendido! Menu Dúvidas Frequentes.\nPerguntas principais:\n" +
                    "* Gym Pass? \n* Horário de Funcionamento? \n* Formas de Pagamento? \n* Matrícula? \n* Aula Experimental é paga?\n" +
                    "Digite uma palavra-chave (ex: 'pagamento', 'horario') ou 'Voltar'.";
        }

        if (msg.contains("Agendar") || msg.contains("4") || msg.contains("Experimental")) {
            estadoAtual = Estado.AGENDAMENTO_COLETANDO_NOME;
            dadosAgendamento.clear(); 
            return "Certo! Vamos agendar sua aula experimental.\n1 - Qual o nome do aluno que fará a aula?";
        }
        
        throw new IllegalArgumentException("Opção inválida. Digite 'Kids', 'Adulto', 'Duvidas', 'Agendar' ou 'Voltar'.");
    }

    private String processarFluxoAgendamento(String msg) {
        ResultadoAgendamento resultado = null;
        String resposta = "";

        try {
            switch (estadoAtual) {
                
                case AGENDAMENTO_COLETANDO_NOME:
                    resultado = agendamentoService.coletarNome(msg);
                    if (resultado.isSucesso()) {
                        dadosAgendamento.putAll(resultado.getDados());
                        estadoAtual = Estado.AGENDAMENTO_COLETANDO_IDADE;
                        
                        resposta = "2 - Qual a idade?";
                    } else {
                        resposta = resultado.getMensagem();
                    }
                    break;

                case AGENDAMENTO_COLETANDO_IDADE:
                    String nomeAluno = (String) dadosAgendamento.get("nome");
                    resultado = agendamentoService.coletarIdade(msg, nomeAluno);
                    if (resultado.isSucesso()) {
                        dadosAgendamento.putAll(resultado.getDados());
                        estadoAtual = Estado.AGENDAMENTO_SELECIONANDO_MODALIDADE;
                        resposta = "3 - " + resultado.getMensagem();
                    } else {
                        resposta = resultado.getMensagem();
                    }
                    break;
                
                case AGENDAMENTO_SELECIONANDO_MODALIDADE:
                    int idade = (Integer) dadosAgendamento.get("idade");
                    nomeAluno = (String) dadosAgendamento.get("nome");
                    resultado = agendamentoService.selecionarModalidade(msg, nomeAluno, idade);
                    
                    if (resultado.isSucesso()) {
                        dadosAgendamento.putAll(resultado.getDados());
                        TurmaAgendamento turmaObj = (TurmaAgendamento) resultado.getDados().get("turma");
                        
                        if (turmaObj != null) { 
                            estadoAtual = Estado.AGENDAMENTO_SELECIONANDO_DATA;
                        } else {
                            estadoAtual = Estado.AGENDAMENTO_SELECIONANDO_TURMA;
                        }
                        resposta = resultado.getMensagem();
                    } else {
                        resposta = resultado.getMensagem();
                    }
                    break;
                    
                case AGENDAMENTO_SELECIONANDO_TURMA:

                    String modalidade = (String) dadosAgendamento.get("modalidade");
                    idade = (Integer) dadosAgendamento.get("idade");
                    nomeAluno = (String) dadosAgendamento.get("nome");
                    resultado = agendamentoService.selecionarTurma(msg, modalidade, nomeAluno, idade);
                    
                    if (resultado.isSucesso()) {
                        dadosAgendamento.putAll(resultado.getDados());
                        estadoAtual = Estado.AGENDAMENTO_SELECIONANDO_DATA;
                        resposta = resultado.getMensagem();
                    } else {
                        resposta = resultado.getMensagem();
                    }
                    break;

                case AGENDAMENTO_SELECIONANDO_DATA:
                    TurmaAgendamento turmaAgendamento = (TurmaAgendamento) dadosAgendamento.get("turma");
                    modalidade = (String) dadosAgendamento.get("modalidade");
                    idade = (Integer) dadosAgendamento.get("idade");
                    nomeAluno = (String) dadosAgendamento.get("nome");
                    resultado = agendamentoService.selecionarData(msg, nomeAluno, idade, modalidade, turmaAgendamento);

                    if (resultado.isSucesso()) {
                        dadosAgendamento.putAll(resultado.getDados());
                        estadoAtual = Estado.AGENDAMENTO_FINALIZADO;
                        resposta = resultado.getMensagem() + "\n\n**O agendamento foi finalizado!** Digite 'Olá' ou 'Menu' para começar novamente.";
                    } else {
                        resposta = resultado.getMensagem();
                    }
                    break;

                default:
                    estadoAtual = Estado.MENU_PRINCIPAL;
                    dadosAgendamento.clear();
                    resposta = "Ocorreu um erro no fluxo de agendamento. Retornando ao menu principal.\n" + getMenuPrincipal();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            estadoAtual = Estado.MENU_PRINCIPAL;
            dadosAgendamento.clear();
            resposta = "Erro inesperado durante o agendamento: " + e.getMessage() + "\nVoltando ao menu principal.\n" + getMenuPrincipal();
        }

        return resposta;
    }

    private String processarAulas(String msg, Modalidade[] lista, String titulo) {
        for (Modalidade mod : lista) {
            if (msg.contains(mod.getNome().toLowerCase().split(" ")[0])) {
                return formatarInformacoesModalidade(mod, titulo);
            }
        }
        
        return "Modalidade não encontrada no menu " + titulo + ". Digite o nome correto ou 'Voltar'.";
    }

    private String processarDuvidas(String msg) {
        for (Duvida duvida : duvidas) {
            if (msg.contains(duvida.getPalavraChave())) {
                return formatarRespostaDuvida(duvida);
            }
            if (msg.contains(duvida.getPergunta().toLowerCase().split(" ")[0])) {
                return formatarRespostaDuvida(duvida);
            }
        }
        
        return "Não encontrei uma resposta para essa palavra-chave. Tente 'pagamento', 'horario', 'gym pass' ou 'Voltar'.";
    }
    
    private String formatarInformacoesModalidade(Modalidade modalidade, String titulo) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n==================================\n");
        sb.append(titulo).append(" -> ").append(modalidade.getNome().toUpperCase()).append("\n");
        sb.append("==================================\n");
        sb.append(modalidade.getDescricaoGeral()).append("\n\n");
        sb.append("Horários e Turmas:\n");

        for (Turma turma : modalidade.getTurmas()) {
            sb.append("* ").append(turma.getFaixaEtaria()).append(":\n");
            sb.append(" ").append(turma.getHorarios().replace("\n", "\n ")).append("\n");
        }

        sb.append("\nPara agendar uma aula experimental, digite 'Agendar'.");
        sb.append("\nDigite 'Voltar' para o menu principal ou consulte outra modalidade deste menu.");
        return sb.toString();
    }
    
    private String formatarRespostaDuvida(Duvida duvida) {
        return "\n--- Resposta ---\n" +
                "R: " + duvida.getResposta() + "\n" +
                "________________\n" +
                "Digite 'Voltar' para o menu principal ou outra palavra-chave de dúvida.";
    }
}
