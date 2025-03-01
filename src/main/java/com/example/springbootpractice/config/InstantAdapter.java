// package com.example.springbootpractice.config;

// import java.io.IOException;
// import java.time.Instant;
// import java.time.format.DateTimeFormatter;

// import org.springframework.format.datetime.standard.InstantFormatter;

// import com.nimbusds.jose.shaded.gson.TypeAdapter;
// import com.nimbusds.jose.shaded.gson.stream.JsonReader;
// import com.nimbusds.jose.shaded.gson.stream.JsonWriter;

// public class InstantAdapter extends TypeAdapter<Instant> {

//     private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;

//     @Override
//     public void write(JsonWriter jsonWriter, Instant instant) throws IOException {
//         if (instant == null) {
//             jsonWriter.nullValue();
//         } else {
//             jsonWriter.value(formatter.format(instant)); // Chuyển thành chuỗi ISO-8601
//         }
//     }

//     @Override
//     public Instant read(JsonReader jsonReader) throws IOException {
//         String value = jsonReader.nextString();
//         return Instant.parse(value); // Instant hỗ trợ parse từ ISO-8601
//     }
// }
