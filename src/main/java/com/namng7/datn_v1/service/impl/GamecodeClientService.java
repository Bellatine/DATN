package com.namng7.datn_v1.service.impl;

import com.namng7.datn_v1.object.GenGamecodeRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class GamecodeClientService {

    private final WebClient.Builder webClientBuilder;

    public GamecodeClientService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public Mono<GenGamecodeRecord> sendPostRequest(String url, Object body) {
        return webClientBuilder
                .baseUrl(url)  // Đặt URL cơ sở
                .build()  // Xây dựng WebClient
                .post()  // Gọi phương thức POST trên WebClient
                .bodyValue(body)  // Gửi dữ liệu body
                .retrieve()  // Nhận phản hồi từ server
                .bodyToMono(GenGamecodeRecord.class);  // Chuyển phản hồi thành đối tượng GenGamecodeRecord
    }
}
