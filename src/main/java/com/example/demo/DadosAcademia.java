package com.example.demo;

public class DadosAcademia {

    public static Modalidade[] carregarModalidadesInfantil() {
        // Karatê Shotokan (Infantil)
        Turma karateCriancas5 = new Turma("Para crianças a partir de 5 anos", "Sábado: 9h - 11h\nSegunda e Quarta: 18h30 - 19h30\nTerça e Quinta: 19h30 - 20h30");
        Modalidade karate = new Modalidade("Karatê Shotokan", "Arte marcial japonesa.", new Turma[] { karateCriancas5 });

        // Ginástica Artística (Infantil)
        Turma ginastica5a8 = new Turma("A partir de 5 a 8 anos", "Segunda e Quarta: 18h30 - 19h30");
        Turma ginastica9a12 = new Turma("A partir de 9 - 12 anos", "Segunda e Quarta: 19h30 - 20h30");
        Modalidade ginastica = new Modalidade("Ginástica Artística", "Foco em flexibilidade e coordenação.", new Turma[] { ginastica5a8, ginastica9a12 });
                                              
        // Taekwondo (Infantil)
        Turma taekwondoCriancas = new Turma("Para crianças a partir de 6 anos", "Terça e Quinta: 18h30 - 19h30");
        Modalidade taekwondo = new Modalidade("Taekwondo", "Arte marcial coreana.", new Turma[] { taekwondoCriancas });
                                            
        // Jiu Jitsu (Infantil)
        Turma jiuJitsuCriancas = new Turma("Para crianças a partir de 12 anos", "Terça e Quinta: 19h30 - 20h30");
        Modalidade jiuJitsu = new Modalidade("Jiu Jitsu", "Foco em luta de chão.", new Turma[] { jiuJitsuCriancas });

        return new Modalidade[] { karate, ginastica, taekwondo, jiuJitsu };
    }

    public static Modalidade[] carregarModalidadesAdulto() {
        // Karatê Shotokan (Adulto)
        Turma karateAdulto = new Turma("Geral", "Sábado: 9h - 11h\nSegunda e Quarta: 20h - 21h\nTerça e Quinta: 19h30 - 20h30");
        Modalidade karate = new Modalidade("Karatê Shotokan", "Arte marcial japonesa para adultos.", new Turma[] { karateAdulto });

        // Taekwondo (Adulto)
        Turma taekwondoAdulto = new Turma("Geral", "Terça e Quinta: 19h30 - 20h30");
        Modalidade taekwondo = new Modalidade("Taekwondo", "Arte marcial coreana para adultos.", new Turma[] { taekwondoAdulto });
                                            
        // Jiu Jitsu (Adulto)
        Turma jiuJitsuAdulto = new Turma("Geral", "Terça e Quinta: 19h30 - 20h30");
        Modalidade jiuJitsuMod = new Modalidade("Jiu Jitsu", "Luta de chão para adultos.", new Turma[] { jiuJitsuAdulto });
                                              
        // Ginástica 50+
        Turma ginastica50 = new Turma("50+", "Segunda, Quarta e Sexta: 08h30 - 09h30");
        Modalidade ginastica = new Modalidade("Ginástica 50+", "Foco em mobilidade e saúde.", new Turma[] { ginastica50 });

        return new Modalidade[] { karate, taekwondo, jiuJitsuMod, ginastica };
    }
    
    public static Duvida[] carregarDuvidasFrequentes() {
        return new Duvida[] {
            new Duvida("1. Gym Pass", "Não Aceitamos Gym Pass, Well Hub ou similares.", "gym pass"),
            new Duvida("2. Horário de Funcionamento", "Seg - Qui: 18h - 21h\nSeg, Qua, Sex: 8h30 - 9h30\nSáb: 9h - 11h", "horario"),
            new Duvida("3. Formas de Pagamento", "Aceitamos: Pix, débito e crédito.", "pagamento"),
            new Duvida("4. Matrícula", "A matrícula é feita por um aplicativo, após a primeira aula experimental.", "matricula"),
            new Duvida("5. Aula Experimental", "Sim, a aula experimental é gratuita.", "experimental")
        };
    }
}
