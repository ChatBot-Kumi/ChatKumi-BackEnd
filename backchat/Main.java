import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Chatbot bot = new Chatbot();
        Scanner scanner = new Scanner(System.in);
        String entradaUsuario;
        
        System.out.println("🤖 Bot: " + bot.processarMensagem("")); 

        do {
            System.out.print("\n🙋 Você: ");
            entradaUsuario = scanner.nextLine();
            
            String resposta = bot.processarMensagem(entradaUsuario);
            System.out.println("\n🤖 Bot: " + resposta);

            if (resposta.contains("Agradecemos") || resposta.contains("Tchau!")) {
                break;
            }

        } while (true);

        scanner.close();
    }
}