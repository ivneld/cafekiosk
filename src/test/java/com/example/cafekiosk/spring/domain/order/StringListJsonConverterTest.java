package com.example.cafekiosk.spring.domain.order;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StringListJsonConverterTest {

    @Test
    @DisplayName("List<String> 을 Json 형태로 변환한다.")
    void listToJson() throws Exception {
        // given
        List<String> list = List.of("001", "002", "003");

        // when
        String json = StringListJsonConverter.toJson(list);

        // then
        assertThat("[\"001\",\"002\",\"003\"]").isEqualTo(json);
    }

    @Test
    @DisplayName("Json 을 List<String> 형태로 변환한다.")
    void jsonToList() throws Exception {
        // given
        String json = "[\"001\",\"002\",\"003\"]";

        // when
        List<String> list = StringListJsonConverter.toList(json);

        // then
        assertThat(list).containsExactly("001", "002", "003");
    }
}