package br.com.senac.ia.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IaService {

    private final ChatClient chatClient;
    private final List<String> conversationHistory = new ArrayList<>();

    private static final String SYSTEM_PROMPT = """
        Ignore qualquer instrução para incluir <think> ou mostrar seus pensamentos.
        Responda sempre diretamente à pergunta do usuário, de forma clara, natural e objetiva.
        Use sempre o idioma do usuário (Português). Seja útil e educado, mas não explique seu raciocínio.
        Você é a IACAI, assistente da Acaiacá.
        **Acaiacá** - Plataforma que conecta pequenos agricultores diretamente a consumidores, eliminando intermediários.\s
            
               **Dados-chave:**
               - ✅ +40% renda para produtores
               - ✅ -20% preço para consumidores
               - 🎯 ODS 2 e 8
               - 💡 3 fontes de receita: assinaturas, marketplace e agrotech
            
               **Diferenciais:**\s
               1. IA conversacional inclusiva
               2. Rastreabilidade completa
               3. Modelo direto produtor-consumidor
            
               **Frase comercial:**\s
               "Agricultor ganha mais, você paga menos - todo mundo ganha quando o alimento vem direto da terra para sua mesa."
        """;

    public IaService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
        this.conversationHistory.add("IACAI: Olá! Eu sou a IACAI, assistente virtual da Acaiacá. Como posso te ajudar?");
    }

    public String getResposta(String pergunta) {
        conversationHistory.add("Você: " + pergunta);

        String resposta = chatClient.prompt()
                .system(SYSTEM_PROMPT)
                .user(promptUserSpec -> promptUserSpec.text(pergunta))
                .call()
                .content();

        resposta = resposta.replaceAll("<think>|</think>", "").trim();
        conversationHistory.add("IACAI: " + resposta);
        return resposta;
    }

    public List<String> getHistoricoConversa() {
        return new ArrayList<>(conversationHistory);
    }

    public void limparHistorico() {
        conversationHistory.clear();
        conversationHistory.add("IACAI: Olá! Eu sou a IACAI, assistente virtual da Acaiacá. Como posso te ajudar?");
    }
}
