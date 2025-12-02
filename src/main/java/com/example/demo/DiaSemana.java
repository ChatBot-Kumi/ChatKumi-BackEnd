package com.example.demo;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

public class DiaSemana {
	
	private String diaSemana;
	private String[] datas;
	
	DiaSemana(String diaSemana){
		this.diaSemana = diaSemana;
	}
	
	public String getDiaSemana () {
		return this.diaSemana;
	}
	public void setDiaSemana(String diaSemana) {
		this.diaSemana = diaSemana;
	}
	public String[] getDatas () {
		return this.datas;
	}
	public void setDatas(String[] datas) {
		this.datas = datas;
	}
	
	
	// TO-DO: colocar validação de horário de aula, 
	// se faltar menos de 6h para a aula escolhida 
	// não poderá agendar par hoje, mas
	// caso seja mais tempo será possível
	
	public void calcularProximasDatas() {
		try {
			LocalDate hoje = LocalDate.now();
			DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			DayOfWeek dia = selecionarDiaSemana();
			LocalDate proximo = hoje.with(TemporalAdjusters.nextOrSame(dia));
			String[] proximasDatas = new String[4];

			for (int i = 0; i < 4; i++) {
				proximasDatas[i] = proximo.plusWeeks(i).format(format);
			}

			this.setDatas(proximasDatas);
		} catch (IllegalArgumentException iae) {
			throw iae;
		} catch (Exception e) {
			throw new IllegalArgumentException("Erro ao calcular próximas datas.", e);
		}
	}
	
    private DayOfWeek selecionarDiaSemana() {
        return switch (this.diaSemana.toLowerCase()) {
            case "segunda" -> DayOfWeek.MONDAY;
            case "terça", "terca" -> DayOfWeek.TUESDAY;
            case "quarta" -> DayOfWeek.WEDNESDAY;
            case "quinta" -> DayOfWeek.THURSDAY;
            case "sexta" -> DayOfWeek.FRIDAY;
            case "sábado", "sabado" -> DayOfWeek.SATURDAY;
            case "domingo" -> DayOfWeek.SUNDAY;
            default -> throw new IllegalArgumentException();
        };
    }
	

}
