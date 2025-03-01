// package com.example.springbootpractice.config;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.http.converter.json.GsonHttpMessageConverter;

// import com.google.gson.Gson;
// import com.google.gson.GsonBuilder;

// import java.time.Instant;

// @Configuration
// public class GsonConfig {

//     @Bean
//     public Gson gson() {
//         return new GsonBuilder()
//                 .registerTypeAdapter(Instant.class, new InstantAdapter()) // Đăng ký Instant Adapter
//                 .setPrettyPrinting() // Format JSON dễ đọc hơn
//                 .create();
//     }

//     // @Bean
//     // public GsonHttpMessageConverter gsonHttpMessageConverter(Gson gson) {
//     //     return new GsonHttpMessageConverter(gson);
//     // }
// }
