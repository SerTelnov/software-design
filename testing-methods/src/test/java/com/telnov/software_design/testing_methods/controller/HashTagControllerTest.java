package com.telnov.software_design.testing_methods.controller;

import com.telnov.software_design.testing_methods.FunctionalTest;
import com.telnov.software_design.testing_methods.service.HashTagService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class HashTagControllerTest extends FunctionalTest {

    @Mock
    private HashTagService service;
    private HashTagController controller;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        this.controller = new HashTagController(service);
    }

    private static Stream<Arguments> validateTestArgs() {
        return Stream.of(
                Arguments.of(
                        "Пероид времени отрицательный",
                        "hash_tag",
                        -42,
                        "Invalid hour interval"
                ),
                Arguments.of(
                        "Пероид времени больше дня",
                        "hash_tag",
                        42,
                        "Invalid hour interval"
                ),
                Arguments.of(
                        "Не валидный hash tag",
                        "   hash   tag    ",
                        3,
                        "Invalid hash tag"
                )
        );
    }

    @MethodSource("validateTestArgs")
    @ParameterizedTest(name = "{0}")
    void validateParamTest(
            @SuppressWarnings("unused") String description,
            String hashTag,
            int hourPeriod,
            String exMsg
    ) {
        IllegalArgumentException ex = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> controller.getHashTagPerHour(hashTag, hourPeriod)
        );
        assertThat(ex.getMessage()).isEqualTo(exMsg);
    }
}