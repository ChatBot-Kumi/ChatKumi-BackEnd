package com.example.demo;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service; 

@Service

public class Chatbot { 
    private List<Modalidade> infantil;
    private List<Modalidade> adulto;
    private List<Duvida> duvidas;

    private enum Estado { MENU_PRINCIPAL, AULAS_INFANTIL, AULAS_ADULTO, DUVIDAS }
    private Estado estadoAtual;

    public Chatbot() {
        this.infantil = DadosAcademia.carregarModalidadesInfantil();
        this.adulto = DadosAcademia.carregarModalidadesAdulto();
        this.duvidas = DadosAcademia.carregarDuvidasFrequentes();
        this.estadoAtual = Estado.MENU_PRINCIPAL;
    }
    
    public String processarMensagem(String mensagem) {
        String msgLower = mensagem.toLowerCase().trim();
        
        if (msgLower.equals("sair") || msgLower.equals("tchau") || msgLower.contains("obrigado")) {
            return "Agradecemos o seu contato e esperamos te ver em breve! Tchau! 👋" ;
            
        }
        
        if (msgLower.equals("olá") || msgLower.equals("oi") || msgLower.contains("tudo bem?")) {
            return "Como posso te ajudar hoje?\n" +
               "Escolha uma opção:\n" +
               "1. Aulas para Crianças (Kids)\n" +
               "2. Aulas para Adultos (Adulto)\n" +
               "3. Dúvidas Frequentes (Duvidas)\n" +
               "Digite 'Sair' a qualquer momento.";
        }
    
        if (msgLower.equals("voltar") || msgLower.equals("menu")) {
            estadoAtual = Estado.MENU_PRINCIPAL;
            return getMenuPrincipal();
        }

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
    } 

    private String getMenuPrincipal() {
        return "Como posso te ajudar hoje?\n" +
               "Escolha uma opção:\n" +
               "1. Aulas para Crianças (Kids)\n" +
               "2. Aulas para Adultos (Adulto)\n" +
               "3. Dúvidas Frequentes (Duvidas)\n" +
               "Digite 'Sair' a qualquer momento.";
    }

    
    private String processarMenuPrincipal(String msg) {
        if (msg.contains("Criança") || msg.contains("1") || msg.contains("Kids")) {
            estadoAtual = Estado.AULAS_INFANTIL;
            return "Entendido! Menu Aulas Infantis.\nModalidades disponíveis: " + 
                   infantil.stream().map(Modalidade::getNome).collect(Collectors.joining(", ")) + 
                   ".\nDigite o nome da modalidade ou 'Voltar'.";
        }
        if (msg.contains("Adulto") || msg.contains("2")) {
            estadoAtual = Estado.AULAS_ADULTO;
            return "Entendido! Menu Aulas Adulto.\nModalidades disponíveis: " + 
                   adulto.stream().map(Modalidade::getNome).collect(Collectors.joining(", ")) + 
                   ".\nDigite o nome da modalidade ou 'Voltar'.";
        }
        if (msg.contains("Dúvidas") || msg.contains("3") || msg.contains("Frequentes")) {
            estadoAtual = Estado.DUVIDAS;
            return "Entendido! Menu Dúvidas Frequentes.\nPerguntas principais:\n" +
                   "* Gym Pass? \n* Horário de Funcionamento? \n* Formas de Pagamento? \n* Matrícula? \n* Aula Experimental é paga?\n" +
                   "Digite uma palavra-chave (ex: 'pagamento', 'horario') ou 'Voltar'.";
        }
        return "Opção inválida. Digite 'Kids', 'Adulto', 'Duvidas' ou 'Voltar'.";
    }

    private String processarAulas(String msg, List<Modalidade> lista, String titulo) {
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
        //sb.append("\n==================================\n");
        sb.append("🥋 ").append(titulo).append(" -> ").append(modalidade.getNome().toUpperCase()).append("\n");
        //sb.append("==================================\n");
        sb.append("ℹ️ ").append(modalidade.getDescricaoGeral()).append("\n\n");
        sb.append("🕒 **Horários e Turmas**:\n");

        for (Turma turma : modalidade.getTurmas()) {
            sb.append("* **").append(turma.getFaixaEtaria()).append("**:\n");
            sb.append("  ").append(turma.getHorarios().replace("\n", "\n  ")).append("\n");
        }
        
        sb.append("\nDigite 'Voltar' para o menu principal ou consulte outra modalidade deste menu.");
        return sb.toString();
    }
    
    private String formatarRespostaDuvida(Duvida duvida) {
        return "\n--- Resposta ---\n" +
               /*"P: " + duvida.getPergunta() + "\n" +*/
               "R: " + duvida.getResposta() + "\n" +
               "________________\n" +
               "Digite 'Voltar' para o menu principal ou outra palavra-chave de dúvida.";
    }
}
 