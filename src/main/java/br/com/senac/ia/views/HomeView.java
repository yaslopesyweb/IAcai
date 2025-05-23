package br.com.senac.ia.views;

import br.com.senac.ia.IaService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.Uses;
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
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("")
@Uses(Avatar.class)
@CssImport("./styles/chat-theme.css")
public class HomeView extends VerticalLayout {

    private final IaService iaService;
    private final VerticalLayout chatLayout = new VerticalLayout();
    private final TextField textField = new TextField();

    public HomeView(IaService iaService) {
        this.iaService = iaService;

        setSizeFull();
        setPadding(false);
        setSpacing(false);
        setJustifyContentMode(JustifyContentMode.START);
        setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.STRETCH);
        addClassName("home-view");

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
        textField.setPlaceholder("Digite sua mensagem...");
        textField.addClassName("chat-input");
        textField.addKeyPressListener(Key.ENTER, event -> send());


        // Botão de envio
        Button sendButton = new Button("Enviar", VaadinIcon.PAPERPLANE.create());
        sendButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        sendButton.addClassName("send-button");
        sendButton.addClickListener(click -> send());

        // Barra de entrada
        HorizontalLayout inputBar = new HorizontalLayout(textField, sendButton);
        inputBar.setWidthFull();
        inputBar.setSpacing(true);
        inputBar.setAlignItems(Alignment.CENTER);
        inputBar.addClassName("input-bar");

        // Rodapé
        Paragraph footer = new Paragraph("© 2024 Acaiacá - Todos os direitos reservados");
        footer.addClassName("footer");
        footer.getStyle()
                .set("color", "gray")
                .set("font-size", "0.75rem")
                .set("text-align", "center")
                .set("padding", "0.5rem");

        add(header, scroller, inputBar, footer);
        expand(scroller);

        // Mensagem inicial
        addChatMessage("Olá, eu sou a IAcai, inteligência artificial da Acaiacá. Como posso te ajudar?", false);
    }

    private void send() {
        String userMessage = textField.getValue();
        if (userMessage == null || userMessage.trim().isEmpty()) return;

        addChatMessage(userMessage, true);
        String chatResponse = iaService.sentToAi(userMessage);
        addChatMessage(chatResponse, false);
        textField.clear();
    }

    private void addChatMessage(String text, boolean isUser) {
        Avatar avatar = new Avatar(isUser ? "Você" : "IAcai");
        avatar.setImage(isUser ? "images/User.jpeg" : "images/IAcai-face.png");

        Div messageBubble = new Div();
        messageBubble.getElement().setProperty("innerHTML", text);
        messageBubble.addClassName(isUser ? "v-user-bubble" : "v-ia-bubble");

        HorizontalLayout messageLayout = new HorizontalLayout();
        messageLayout.setWidthFull();
        messageLayout.setSpacing(true);
        messageLayout.setAlignItems(Alignment.END);

        if (isUser) {
            messageLayout.add(messageBubble, avatar);
            messageLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        } else {
            messageLayout.add(avatar, messageBubble);
            messageLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.START);
        }

        chatLayout.add(messageLayout);
    }
}