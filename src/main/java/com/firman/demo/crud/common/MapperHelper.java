package com.firman.demo.crud.common;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.time.ZoneId;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MapperHelper {

    private static final ModelMapper MODEL_MAPPER = new ModelMapper();

    public static <S, T> T map(S source, Class<T> target) {
        if (ObjectUtils.isEmpty(source) || ObjectUtils.isEmpty(target)) {
            return null;
        } else {
            return MODEL_MAPPER.map(source, target);
        }
    }

    public static <S, T> List<T> mapAsList(Collection<S> sources, Class<T> target) {
        if (CollectionUtils.isEmpty(sources) || ObjectUtils.isEmpty(target)) {
            return Collections.emptyList();
        } else {
            return sources.stream()
                    .map(source -> map(source, target))
                    .collect(Collectors.toList());
        }
    }

    public static String convert(Object source) {
        try {
            return objectMapper().writeValueAsString(source);
        } catch (Exception e) {
            log.error("#ERROR convert() for source : {}, caused by ", source, e);
            return StringUtils.EMPTY;
        }
    }

    public static <T> T convert(String source, Class<T> target) {
        try {
            return objectMapper().readValue(source, target);
        } catch (Exception e) {
            log.error("#ERROR convert() for source : {} and target : {}, caused by ", source, target, e);
            return null;
        }
    }

    public static <T> List<T> convertAsList(String source, Class<T> target) {
        try {
            return objectMapper().readValue(source, objectMapper().getTypeFactory().constructCollectionType(List.class, target));
        } catch (Exception e) {
            log.error("#ERROR convertAsList() for source : {} and target : {}, caused by ",
                    source,
                    target,
                    e);
            return Collections.emptyList();
        }
    }

    public static String camelToSnake(String str) {
        StringBuilder result = new StringBuilder();
        char c = str.charAt(0);
        result.append(Character.toLowerCase(c));
        for (int i = 1; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (Character.isUpperCase(ch)) {
                result.append('_');
                result.append(Character.toLowerCase(ch));
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }

    public static ObjectMapper objectMapper(){
        var mapper = new ObjectMapper();
        return mapper
            .setTimeZone(TimeZone.getTimeZone(ZoneId.of("Asia/Jakarta")))
            .registerModule(new JavaTimeModule())
            .configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false)
            .configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS,false)
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false)
            .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
            .setVisibility(mapper.getSerializationConfig()
                .getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withCreatorVisibility(JsonAutoDetect.Visibility.NONE));
    }
}
