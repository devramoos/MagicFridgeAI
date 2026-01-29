package dev.java10x.MagicFridgeAI.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
public class ChatGptService {

    private final WebClient webClient;
    private String apiKey =  System.getenv("API_KEY");

    public ChatGptService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<String> generateRecepi(){
        String prompt = "Me sugira uma receita simples com ingredientes comuns. Não inclua alho, sou alergico";
        Map<String, Object> requestBody = Map.of(
                "model", "gpt-5.2",
                "input", List.of(
                        Map.of("role","system", "content", "Voce é um assitente que cria receitas"),
                        Map.of("role","user","content", prompt)
                )
        );

        return webClient.post()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, "Bearer" + apiKey)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> {
                    var choices = (List<Map<String, Object>>) response.get("choises");
                    if (choices != null && !choices.isEmpty()) {
                        Object inner = choices.get(0).get("choices");
                        if (inner instanceof Map<?, ?> innerMap) {
                            Object content = ((Map<String, Object>) innerMap).get("content");
                            if (content != null) {
                                return content.toString();
                            }
                        }
                        return "Nenhuma receita foi gerada";
                    }
                    return "Nenhuma receita foi gerada";
                });

    }
}
