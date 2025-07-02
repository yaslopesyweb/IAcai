package br.com.senac.ia.components;

import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;

@Tag( "voice-assistant")
public class VoiceAssistant extends com.vaadin.flow.component.Component {

    private VoiceListener voiceListener;

    public void setVoiceListener(VoiceListener listener){
        this.voiceListener = listener;
    }

    public void startListening(){
        getElement().executeJs(
                """
                const SpeechRecognition = window.SpeechRecognition || window.webkitSpeechRecognition;
                if (!SpeechRecognition) {
                    alert("Seu navegador não suporta reconhecimento de voz.");
                    return;
                }
                const recognition = new SpeechRecognition();
                recognition.lang = 'pt-BR';
                recognition.onresult = function(event) {
                    const text = event.results[0][0].transcript;
                    $0.$server.receiveVoice(text);
                };
                recognition.start();
                """, getElement()
        );
    }

    // NOVO MÉTODO PARA TOCAR ÁUDIO BASE64 GERADO PELA OPENAI
    public void speakFromBase64(String base64) {
        getElement().executeJs("""
            const audio = new Audio("data:audio/mp3;base64," + $0);
            audio.play();
        """, base64);
    }

    @ClientCallable
    public void receiveVoice(String userSpeech) {
        if (voiceListener != null) {
            voiceListener.onVoiceInput(userSpeech);
        }
    }

    public interface VoiceListener {
        void onVoiceInput(String text);
    }
}