package com.example.demo;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;


@RestController
@RequestMapping("/api/chatbot")
@CrossOrigin(origins = "*") 
public class ChatbotController {

    private final Chatbot chatbot;

    @Autowired
    public ChatbotController(Chatbot chatbot) {
        this.chatbot = chatbot;
    }

    @PostMapping("/mensagem")
    public String enviarMensagem(@RequestBody MensagemRequest request) {
        if (request == null || request.getTexto() == null) {
            return "🤖 Bot: Por favor, envie uma mensagem válida.";
        }

        return chatbot.processarMensagem(request.getTexto());
    }
}


class MensagemRequest {
    private String texto;

    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto; }
}