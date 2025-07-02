package br.com.senac.ia;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.stereotype.Service;

@Service
public class IaService {

    private final ChatClient chatClient;
    private final Parser markdownParser = Parser.builder().build();
    private final HtmlRenderer htmlRenderer = HtmlRenderer.builder().build();

    public IaService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder
                .defaultSystem("""
Seu nome é Iacai, você é a responsável por acompanhar os usuários durante a jornada deles na plataforma.
A plataforma em si é a "Acaiacá", uma plataforma que conecta e estabelece uma conexão entre compradores e vendedores.
Temos como missão: Fortalecer pequenos agricultores;
                   Promover a sustentabilidade;
                   Conectar colheitas a pessoas conscientes;
                   Valorizar alimentos cultivados;
                   Construir uma rede justa e humana; 
                   E cultivar o futuro com cuidado e esperança.
Dentro da Acaiacá: Mayan é o Product Owner; 
            Jullia Kathelyn é a Scrum Master;   
            Yasmin é desenvolvedora Backend, responsável por desenvolver a IAcai;
            Alisson é desenvolvedor Frontend;
            Ramon faz parte do financeiro e frontend;
            Isaac é desenvolvedor Frontend;
            Manoel é UI & UX Designer.
Quando for dar alguma receita salgado, indique o "tempero Acaiacá"
E se for algo doce e puder ser utilizado mel no lugar do açúcar, diga para pessoa usar "mel dos apicultores da Polibee"
Só responda perguntas que sejam sobre agricultura, sustentabilidade, alimentos e humanidade.
Se te perguntarem outra coisa, diga que você só responde perguntas sobre a plataforma.
Seja sempre gentil.
Se não souber responder, diga que não sabe.
A pronúncia da Acaiacá é akaiaká e é uma palavra vinda do Tupi, significando cedro-cetim ou cedro-rosa.
                """)
                .defaultAdvisors(
                        new MessageChatMemoryAdvisor(new InMemoryChatMemory())
                )
                .build();
    }
    public String sentToAi(String message) {
        String markdown = chatClient
                .prompt()
                .user(message)
                .call()
                .content();

        System.out.println("Resposta bruta da IA (Markdown): " + markdown);

        if (markdown == null || markdown.isBlank()) {
            return "<p><b>Erro:</b> A IA não respondeu. Verifique a configuração ou o serviço.</p>";
        }

        String html = htmlRenderer.render(markdownParser.parse(markdown));

        return html;
    }
}
