package com.jyujyu.dayonetest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MyCalculatorRepeatableTest {

    @DisplayName("단순 반복 테스트")
    @RepeatedTest(5)
    public void repeatedAddTest() {

        MyCalculator myCalculator = new MyCalculator();

        myCalculator.add(10.0);

        assertEquals(10.0, myCalculator.getResult());
    }

    @DisplayName("덧셈 5번 파라미터를 넣어주며 테스트")
    @ParameterizedTest
    @MethodSource("parameterizedTestParameters")
    public void parameterizedTest(Double addValue, Double expectValue) {
        MyCalculator myCalculator = new MyCalculator();

        myCalculator.add(addValue);

        assertEquals(expectValue, myCalculator.getResult());
    }

    @DisplayName("파라미터를 넣으며 사칙연산 2번 반복 테스트")
    @ParameterizedTest
    @MethodSource("parameterizedComplicatedTestParameters")
    public void parameterizedComplicatedCalculateTest(Double addValue,
                                                      Double minusValue,
                                                      Double multiplyValue,
                                                      Double divideValue,
                                                      Double expectValue) {
        //given
        MyCalculator myCalculator = new MyCalculator(0.0);

        Double result = myCalculator.add(addValue)
                .minus(minusValue)
                .multiply(multiplyValue)
                .divide(divideValue)
                .getResult();

        assertEquals(expectValue, result);
    }

    public static Stream<Arguments> parameterizedTestParameters() {
        return Stream.of(
                Arguments.of(10.0,10.0),
                Arguments.of(8.0,8.0),
                Arguments.of(4.0,4.0),
                Arguments.of(18.0,18.0),
                Arguments.of(16.0,16.0)
        );
    }

    public static Stream<Arguments> parameterizedComplicatedTestParameters() {
        return Stream.of(
                Arguments.of(10.0,4.0,2.0,3.0,4.0),
                Arguments.of(4.0,2.0,4.0,4.0,2.0));
    }
}
