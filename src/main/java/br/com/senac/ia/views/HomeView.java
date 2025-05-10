package br.com.senac.ia.views;

import br.com.senac.ia.IaService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.messages.MessageList;
import com.vaadin.flow.component.messages.MessageListItem;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

import java.time.Instant;
import java.util.ArrayList;

@Route("")
@CssImport(value = "./styles/chat-theme.css")
public class HomeView extends VerticalLayout {

    private final IaService iaService;
    private final MessageList messageList = new MessageList();
    private final TextArea textArea = new TextArea();
    private final int charLimit = 500;

    public HomeView(IaService iaService) {
        this.iaService = iaService;

        setSizeFull();
        setPadding(false);
        setSpacing(false);
        setJustifyContentMode(JustifyContentMode.START);
        setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.STRETCH);

        // Header
        Div header = new Div();
        header.addClassName("header");
        header.getStyle().set("padding", "1rem");
        header.getStyle().set("display", "flex");
        header.getStyle().set("justify-content", "space-between");
        header.getStyle().set("align-items", "center");

        H1 title = new H1("IACAI - Inteligência Artificial da Acaiacá");
        title.getStyle().set("font-size", "1.5rem").set("margin", "0");

        Div version = new Div(new Text("v1.0"));
        version.getStyle()
                .set("background", "rgba(255,255,255,0.2)")
                .set("padding", "0.25rem 0.5rem")
                .set("border-radius", "999px")
                .set("font-size", "0.75rem");

        header.add(title, version);

        // Message Area
        messageList.addClassName("custom-message-list");
        messageList.setHeightFull();
        messageList.getStyle().set("background-color", "var(--dun)");

        Scroller scroller = new Scroller(messageList);
        scroller.setHeight("100%");
        scroller.setScrollDirection(Scroller.ScrollDirection.VERTICAL);
        scroller.getStyle().set("flex", "1");

        textArea.setLabel("Mensagem");
        textArea.setPlaceholder("Digite sua mensagem...");
        textArea.setWidthFull();
        textArea.addKeyDownListener(Key.ENTER, event -> send());

        Button sendButton = new Button(VaadinIcon.PAPERPLANE.create());
        sendButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        sendButton.getStyle()
                .set("background-color", "var(--apple-green)")
                .set("color", "white")
                .set("border-radius", "999px");
        sendButton.addClickListener(click -> send());

        HorizontalLayout inputBar = new HorizontalLayout(textArea, sendButton);
        inputBar.setWidthFull();
        inputBar.setPadding(true);
        inputBar.setSpacing(true);
        inputBar.getStyle().set("background-color", "var(--dun)");
        inputBar.setAlignItems(Alignment.END);

        // Footer
        Paragraph footer = new Paragraph("© 2024 Acaiacá - Todos os direitos reservados");
        footer.getStyle().set("text-align", "center")
                .set("font-size", "0.75rem")
                .set("color", "gray");

        // Layout principal
        add(header, scroller, inputBar, footer);
        expand(scroller);
    }

    private void send() {
        String userMessage = textArea.getValue();
        if (userMessage == null || userMessage.trim().isEmpty()) return;

        addMessage(userMessage, "Usuário", 1);
        String chatResponse = iaService.sentToAi(userMessage);
        addMessage(chatResponse, "IACAI", 2);
        textArea.clear();
    }

    private void addMessage(String message, String user, int colorIndex) {
        MessageListItem messageListItem = new MessageListItem(message, Instant.now(), user);
        messageListItem.setUserColorIndex(colorIndex); // Pode ser customizado por CSS
        var currentMessages = new ArrayList<>(messageList.getItems());
        currentMessages.add(messageListItem);
        messageList.setItems(currentMessages);
    }
}