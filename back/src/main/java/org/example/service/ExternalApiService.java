package org.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@Service
public class ExternalApiService {

    @Autowired
    private WebClient webClient;

    // GET 请求，带 URL 参数
    public Mono<String> getData(String id) {
        // 构建完整的 URI，包含查询参数
        String url = UriComponentsBuilder.fromHttpUrl("http://10.199.0.170:8088/api/getToken.htm")
                .queryParam("client_id", id)
                .queryParam("client_secret", "QmKEqhd1IGabctxCTVemki5UCkb2ojkQ")
                .queryParam("grant_type", "code")
                .queryParam("response_type", "json")
                .toUriString(); // 生成完整的 URI 字符串
        return webClient.get()
                .uri(url) // 设置 URL 参数
                //.header("Authorization", "Bearer token") // 设置请求头
                .retrieve() // 发送请求并获取响应
                .bodyToMono(String.class); // 将响应体解析为字符串
    }

    public Mono<String> getData2(String id) {
        // 构建完整的 URI，包含查询参数
        String url = UriComponentsBuilder.fromHttpUrl("http://10.199.0.170:8088/api/getToken.htm")
                .queryParam("client_id", id)
                .queryParam("client_secret", "QmKEqhd1IGabctxCTVemki5UCkb2ojkQ")
                .queryParam("grant_type", "code")
                .queryParam("response_type", "json")
                .toUriString(); // 生成完整的 URI 字符串
        return webClient.get()
                .uri(url) // 设置 URL 参数
                //.header("Authorization", "Bearer token") // 设置请求头
                .retrieve() // 发送请求并获取响应
                .onStatus(HttpStatus::is4xxClientError, response -> {
                    // 处理 4xx 错误
                    return Mono.error(new RuntimeException("Client Error: " + response.statusCode()));
                })
                .onStatus(HttpStatus::is5xxServerError, response -> {
                    // 处理 5xx 错误
                    return Mono.error(new RuntimeException("Server Error: " + response.statusCode()));
                })
                .bodyToMono(String.class) // 将响应体解析为字符串
                .map(response -> "Processed Response: " + response) // 对正常响应进行处理
                .onErrorResume(e -> {
                    // 处理其他异常（如网络错误）
                    return Mono.just("Fallback Data: " + e.getMessage());
                });
    }

    // POST 请求，带请求体参数
    public Mono<String> postData(String requestBody) {
        String url = "https://api.example.com/data";
        return webClient.post()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON) // 设置请求头
                .bodyValue(requestBody) // 设置请求体
                .retrieve()
                .bodyToMono(String.class); // 发送 POST 请求并返回响应
    }

    // PUT 请求，带请求体参数
    public Mono<Void> updateData(String id, String requestBody) {
        String url = "https://api.example.com/data/{id}";
        return webClient.put()
                .uri(url, id) // 设置 URL 参数
                .contentType(MediaType.APPLICATION_JSON) // 设置请求头
                .bodyValue(requestBody) // 设置请求体
                .retrieve()
                .bodyToMono(Void.class); // 发送 PUT 请求
    }

    // DELETE 请求，带 URL 参数
    public Mono<Void> deleteData(String id) {
        String url = "https://api.example.com/data/{id}";
        return webClient.delete()
                .uri(url, id) // 设置 URL 参数
                .retrieve()
                .bodyToMono(Void.class); // 发送 DELETE 请求
    }
}
