import java.util.Arrays;
import java.util.List;

public class DadosAcademia {

    public static List<Modalidade> carregarModalidadesInfantil() {
        // Karatê Shotokan (Infantil)
        Turma karateCriancas5 = new Turma("Para crianças a partir de 5 anos", "Sábado: 9h - 11h\nSegunda e Quarta: 18h30 - 19h30\nTerça e Quinta: 19h30 - 20h30");
        Modalidade karate = new Modalidade("Karatê Shotokan", "Arte marcial japonesa.", Arrays.asList(karateCriancas5));

        // Ginástica Artística (Infantil)
        Turma ginastica5a8 = new Turma("A partir de 5 a 8 anos", "Segunda e Quarta: 18h30 - 19h30");
        Turma ginastica9a12 = new Turma("A partir de 9 - 12 anos", "Segunda e Quarta: 19h30 - 20h30");
        Modalidade ginastica = new Modalidade("Ginástica Artística", "Foco em flexibilidade e coordenação.", Arrays.asList(ginastica5a8, ginastica9a12));
                                              
        // Taekwondo (Infantil)
        Turma taekwondoCriancas = new Turma("Para crianças a partir de 6 anos", "Terça e Quinta: 18h30 - 19h30");
        Modalidade taekwondo = new Modalidade("Taekwondo", "Arte marcial coreana.", Arrays.asList(taekwondoCriancas));
                                            
        // Jiu Jitsu (Infantil)
        Turma jiuJitsuCriancas = new Turma("Para crianças a partir de 12 anos", "Terça e Quinta: 19h30 - 20h30");
        Modalidade jiuJitsuMod = new Modalidade("Jiu Jitsu", "Foco em luta de chão.", Arrays.asList(jiuJitsuCriancas));

        return Arrays.asList(karate, ginastica, taekwondo, jiuJitsuMod);
    }

    public static List<Modalidade> carregarModalidadesAdulto() {
        // Karatê Shotokan (Adulto)
        Turma karateAdulto = new Turma("Geral", "Sábado: 9h - 11h\nSegunda e Quarta: 20h - 21h\nTerça e Quinta: 19h30 - 20h30");
        Modalidade karate = new Modalidade("Karatê Shotokan", "Arte marcial japonesa para adultos.", Arrays.asList(karateAdulto));

        // Taekwondo (Adulto)
        Turma taekwondoAdulto = new Turma("Geral", "Terça e Quinta: 19h30 - 20h30");
        Modalidade taekwondo = new Modalidade("Taekwondo", "Arte marcial coreana para adultos.", Arrays.asList(taekwondoAdulto));
                                            
        // Jiu Jitsu (Adulto)
        Turma jiuJitsuAdulto = new Turma("Geral", "Terça e Quinta: 19h30 - 20h30");
        Modalidade jiuJitsuMod = new Modalidade("Jiu Jitsu", "Luta de chão para adultos.", Arrays.asList(jiuJitsuAdulto));
                                              
        // Ginástica 50+
        Turma ginastica50 = new Turma("50+", "Segunda, Quarta e Sexta: 08h30 - 09h30");
        Modalidade ginastica = new Modalidade("Ginástica 50+", "Foco em mobilidade e saúde.", Arrays.asList(ginastica50));

        return Arrays.asList(karate, taekwondo, jiuJitsuMod, ginastica);
    }
    
    public static List<Duvida> carregarDuvidasFrequentes() {
        return Arrays.asList(
            new Duvida("Gym Pass", "Não Aceitamos Gym Pass, Well Hub ou similares.", "gym pass"),
            new Duvida("Horário de Funcionamento", "Seg - Qui: 18h - 21h\nSeg, Qua, Sex: 8h30 - 9h30\nSáb: 9h - 11h", "horario"),
            new Duvida("Formas de Pagamento", "Aceitamos: Pix, débito e crédito.", "pagamento"),
            new Duvida("Matrícula", "A matrícula é feita por um aplicativo, após a primeira aula experimental.", "matricula"),
            new Duvida("Aula Experimental", "Sim, a aula experimental é gratuita.", "pago")
        );
    }
}