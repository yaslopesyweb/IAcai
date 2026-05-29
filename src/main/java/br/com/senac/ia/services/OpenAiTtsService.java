package br.com.senac.ia.services;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

@Service
public class OpenAiTtsService {

    @Value("${openai.api.key}")
    private String apiKey;

    public String gerarAudioBase64(String text) {
        try {
            URL url = new URL("https://api.openai.com/v1/audio/speech");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);
            connection.setRequestProperty("Content-Type", "application/json");

            // Corpo da requisição com modelo válido e voz compatível
            JSONObject body = new JSONObject();
            body.put("model", "tts-1"); // ou "tts-1-hd" para qualidade superior
            body.put("input", text);
            body.put("voice", "nova"); // voz feminina natural suportada pela OpenAI
            body.put("response_format", "mp3");

            try (OutputStream os = connection.getOutputStream()) {
                os.write(body.toString().getBytes());
                os.flush();
            }

            // Verifica se houve erro
            if (connection.getResponseCode() != 200) {
                InputStream errorStream = connection.getErrorStream();
                if (errorStream != null) {
                    System.err.println("Erro da OpenAI TTS: " + new String(errorStream.readAllBytes()));
                } else {
                    System.err.println("Erro desconhecido. Código: " + connection.getResponseCode());
                }
                return null;
            }

            // Lê resposta de áudio e converte para Base64
            try (InputStream inputStream = connection.getInputStream()) {
                byte[] audioBytes = inputStream.readAllBytes();
                return Base64.getEncoder().encodeToString(audioBytes);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
