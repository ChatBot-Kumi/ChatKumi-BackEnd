package com.example.demo;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

public class Datas {

	
    private static List<String> calcularProximasAulas(DayOfWeek dia) {
        List<String> datas = new ArrayList<>();
        LocalDate hoje = LocalDate.now();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDate proximo = hoje.with(TemporalAdjusters.nextOrSame(dia));
        for (int i = 0; i < 4; i++) {
            datas.add(proximo.plusWeeks(i).format(fmt));
        }
        return datas;
    }
    

    private static DayOfWeek traduzDiaSemana(String nome) {
        return switch (nome.toLowerCase()) {
            case "segunda" -> DayOfWeek.MONDAY;
            case "terça", "terca" -> DayOfWeek.TUESDAY;
            case "quarta" -> DayOfWeek.WEDNESDAY;
            case "quinta" -> DayOfWeek.THURSDAY;
            case "sexta" -> DayOfWeek.FRIDAY;
            case "sábado", "sabado" -> DayOfWeek.SATURDAY;
            case "domingo" -> DayOfWeek.SUNDAY;
            default -> throw new IllegalArgumentException("Dia da semana inválido: " + nome);
        };
    }

}
