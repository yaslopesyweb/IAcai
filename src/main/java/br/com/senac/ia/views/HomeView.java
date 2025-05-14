package br.com.senac.ia.views;

import br.com.senac.ia.IaService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.Route;

@Route("")
@CssImport("./styles/chat-theme.css")
public class HomeView extends VerticalLayout {

    private final IaService iaService;
    private final VerticalLayout chatLayout = new VerticalLayout();
    private final TextArea textArea = new TextArea();

    public HomeView(IaService iaService) {
        this.iaService = iaService;

        setSizeFull();
        setPadding(false);
        setSpacing(false);
        setJustifyContentMode(JustifyContentMode.START);
        setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.STRETCH);
        addClassName("home-view"); // opcional se quiser estilizar o layout principal

        // Header
        Div header = new Div();
        header.addClassName("header");

        H1 title = new H1("IAcai Chat");
        header.add(title);

        // Chat layout
        chatLayout.setWidthFull();
        chatLayout.setPadding(true);
        chatLayout.setSpacing(true);

        Scroller scroller = new Scroller(chatLayout);
        scroller.setHeight("100%");
        scroller.setScrollDirection(Scroller.ScrollDirection.VERTICAL);
        scroller.addClassName("chat-scroller");

        // Entrada de texto
        textArea.setPlaceholder("Digite sua mensagem...");
        textArea.setWidthFull();
        textArea.addClassName("chat-input");
        textArea.addKeyPressListener(Key.ENTER, event -> send());

        // Botão de envio
        Button sendButton = new Button("Enviar", VaadinIcon.PAPERPLANE.create());
        sendButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        sendButton.addClassName("send-button");
        sendButton.addClickListener(click -> send());

        // Barra de entrada
        HorizontalLayout inputBar = new HorizontalLayout(textArea, sendButton);
        inputBar.setWidthFull();
        inputBar.setSpacing(true);
        inputBar.setAlignItems(Alignment.END);
        inputBar.addClassName("input-bar");

        // Rodapé
        Paragraph footer = new Paragraph("© 2024 Acaiacá - Todos os direitos reservados");
        footer.addClassName("footer");

        add(header, scroller, inputBar, footer);
        expand(scroller);

        // Mensagem inicial
        addChatMessage("Olá, eu sou a IAcai, inteligência artificial da Acaiacá. Como posso te ajudar?", false);
    }

    private void send() {
        String userMessage = textArea.getValue();
        if (userMessage == null || userMessage.trim().isEmpty()) return;

        addChatMessage(userMessage, true);
        String chatResponse = iaService.sentToAi(userMessage);
        addChatMessage(chatResponse, false);
        textArea.clear();
    }

    private void addChatMessage(String text, boolean isUser) {
        Div messageBubble = new Div();

        // Usa innerHTML para renderizar Markdown convertido em HTML
        messageBubble.getElement().setProperty("innerHTML", text);

        messageBubble.addClassName(isUser ? "v-user-bubble" : "v-ia-bubble");
        chatLayout.add(messageBubble);
    }
}
