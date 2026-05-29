package br.com.senac.ia.views;

import br.com.senac.ia.IaService;
import br.com.senac.ia.components.VoiceAssistant;
import br.com.senac.ia.services.OpenAiTtsService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.atomic.AtomicReference;

@Route("")
@Uses(Avatar.class)
@CssImport("./styles/chat-theme.css")
public class HomeView extends VerticalLayout {

    private final IaService iaService;
    private final OpenAiTtsService ttsService;

    private final VerticalLayout chatLayout = new VerticalLayout();
    private final TextField textField = new TextField();
    private final VoiceAssistant voiceAssistant = new VoiceAssistant();

    @Autowired
    public HomeView(IaService iaService, OpenAiTtsService ttsService) {
        this.iaService = iaService;
        this.ttsService = ttsService;

        configureLayout();
        configureVoiceAssistant();
        showWelcomeMessage();
    }

    private void configureLayout() {
        setSizeFull();
        setPadding(false);
        setSpacing(false);
        setJustifyContentMode(JustifyContentMode.START);
        setDefaultHorizontalComponentAlignment(Alignment.STRETCH);
        addClassName("home-view");

        // Header
        Div header = new Div();
        header.addClassName("header");
        header.add(new H1("IAcai Chat"));

        // Chat layout
        chatLayout.setWidthFull();
        chatLayout.setPadding(true);
        chatLayout.setSpacing(true);

        Scroller scroller = new Scroller(chatLayout);
        scroller.setHeight("100%");
        scroller.setScrollDirection(Scroller.ScrollDirection.VERTICAL);
        scroller.addClassName("chat-scroller");

        // Text input
        textField.setPlaceholder("Digite sua mensagem...");
        textField.addClassName("chat-input");
        textField.addKeyPressListener(Key.ENTER, event -> send());

        // Send button
        Button sendButton = new Button("Enviar", VaadinIcon.PAPERPLANE.create());
        sendButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        sendButton.addClassName("send-button");
        sendButton.addClickListener(click -> send());

        // Voice button
        Button voiceButton = new Button(new Icon(VaadinIcon.MICROPHONE));
        voiceButton.addClassName("voice-button");
        voiceButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        voiceButton.getStyle().set("background-color", "var(--dark-moss-green)");
        voiceButton.getStyle().set("color", "white");
        voiceButton.getStyle().set("border-radius", "15px");
        voiceButton.getStyle().set("padding", "0.5rem 1rem");
        voiceButton.getStyle().set("gap", "0.5rem");
        voiceButton.addClickListener(click -> voiceAssistant.startListening());

        // Input bar
        HorizontalLayout inputBar = new HorizontalLayout(textField, sendButton, voiceButton);
        inputBar.setWidthFull();
        inputBar.setSpacing(true);
        inputBar.setAlignItems(Alignment.CENTER);
        inputBar.addClassName("input-bar");

        // Footer
        Paragraph footer = new Paragraph("© 2024 Acaiacá - Todos os direitos reservados");
        footer.addClassName("footer");
        footer.getStyle()
                .set("color", "gray")
                .set("font-size", "0.75rem")
                .set("text-align", "center")
                .set("padding", "0.5rem");

        add(header, scroller, inputBar, voiceAssistant, footer);
        expand(scroller);
    }

    private void configureVoiceAssistant() {
        voiceAssistant.setVoiceListener(userSpeech -> {
            textField.setValue(userSpeech);
            send();
        });
    }

    private void showWelcomeMessage() {
        String welcomeText = "Olá, eu sou a IAcai, inteligência artificial da Acaiacá. Como posso te ajudar?";
        addChatMessage(welcomeText, false);

        String base64Audio = ttsService.gerarAudioBase64(welcomeText);
        if (base64Audio != null) {
            voiceAssistant.speakFromBase64(base64Audio);
        }
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
            messageLayout.setJustifyContentMode(JustifyContentMode.END);
        } else {
            // Botão de ouvir
            Button listenButton = new Button(new Icon(VaadinIcon.VOLUME_UP));
            listenButton.addClassName("ia-listen-button");
            listenButton.getStyle().set("margin-left", "0.5rem");

            String base64Audio = ttsService.gerarAudioBase64(text);
            listenButton.setEnabled(base64Audio != null);

            String audioId = "audio-" + Math.abs(text.hashCode());
            listenButton.getElement().setAttribute("button-id", audioId);

            listenButton.addClickListener(e -> {
                if (base64Audio == null) return;

                UI.getCurrent().getPage().executeJs("""
                    (function() {
                        const audioId = $0;
                        const base64 = $1;
                        let button = document.querySelector('[button-id="' + audioId + '"]');

                        if (!window.currentAudio || window.currentAudio.id !== audioId) {
                            if (window.currentAudio) {
                                window.currentAudio.pause();
                                window.currentAudio.currentTime = 0;
                                if (window.currentButton && window.currentButton.__originalIcon) {
                                    window.currentButton.innerHTML = window.currentButton.__originalIcon;
                                }
                            }

                            const audio = new Audio("data:audio/mp3;base64," + base64);
                            audio.id = audioId;
                            window.currentAudio = audio;
                            window.currentButton = button;

                            if (button) {
                                button.__originalIcon = button.innerHTML;
                                button.innerHTML = '<vaadin-icon icon="vaadin:volume-off"></vaadin-icon>';
                            }

                            audio.play();

                            audio.onended = () => {
                                if (button && button.__originalIcon) {
                                    button.innerHTML = button.__originalIcon;
                                }
                                window.currentAudio = null;
                                window.currentButton = null;
                            };
                        } else {
                            window.currentAudio.pause();
                            window.currentAudio.currentTime = 0;
                            if (window.currentButton && window.currentButton.__originalIcon) {
                                window.currentButton.innerHTML = window.currentButton.__originalIcon;
                            }
                            window.currentAudio = null;
                            window.currentButton = null;
                        }
                    })();
                """, audioId, base64Audio);
            });

            HorizontalLayout bubbleAndButton = new HorizontalLayout(messageBubble, listenButton);
            bubbleAndButton.setSpacing(true);
            bubbleAndButton.setAlignItems(Alignment.CENTER);
            bubbleAndButton.getStyle().set("maxWidth", "80%");

            messageLayout.add(avatar, bubbleAndButton);
            messageLayout.setJustifyContentMode(JustifyContentMode.START);
        }

        chatLayout.add(messageLayout);
    }
}