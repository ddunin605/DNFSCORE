package com.dunin.dnfscore;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class DnfController {

  private final WebClient webClient;
  private final String apiBase;
  private final String apiKey;

  public DnfController(
      @Value("${neople.apiBase}") String apiBase,
      @Value("${neople.apiKey}") String apiKey
  ) {
    this.apiBase = apiBase;
    this.apiKey = apiKey;
    this.webClient = WebClient.builder().baseUrl(apiBase).build();
  }

  // 캐릭터 검색: /api/characters?server=bakal&name=떠닝&wordType=match&limit=5
  @GetMapping(value = "/characters", produces = MediaType.APPLICATION_JSON_VALUE)
  public Mono<String> searchCharacters(
      @RequestParam String server,
      @RequestParam String name,
      @RequestParam(defaultValue = "match") String wordType,
      @RequestParam(defaultValue = "5") int limit
  ) {
    String path = String.format("/servers/%s/characters", server);
    return webClient.get()
        .uri(uri -> uri.path(path)
            .queryParam("characterName", name)
            .queryParam("wordType", wordType)
            .queryParam("limit", limit)
            .queryParam("apikey", apiKey)
            .build())
        .retrieve()
        .bodyToMono(String.class);
  }

  // 장비: /api/equipment?server=bakal&characterId=86a4a9...
  @GetMapping(value = "/equipment", produces = MediaType.APPLICATION_JSON_VALUE)
  public Mono<String> equipment(
      @RequestParam String server,
      @RequestParam String characterId
  ) {
    String path = String.format("/servers/%s/characters/%s/equip/equipment", server, characterId);
    return webClient.get()
        .uri(uri -> uri.path(path)
            .queryParam("apikey", apiKey)
            .build())
        .retrieve()
        .bodyToMono(String.class);
  }
}
