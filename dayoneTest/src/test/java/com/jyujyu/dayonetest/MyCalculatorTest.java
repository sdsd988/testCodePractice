package com.jyujyu.dayonetest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyCalculatorTest {

    @Test
    @DisplayName("MyCalculator 더하기 테스트")
    void addTest() {

        MyCalculator myCalculator = new MyCalculator();

        myCalculator.add(10.0);

        assertEquals(10.0, myCalculator.getResult());
    }

    @Test
    void minusTest() {
        MyCalculator myCalculator = new MyCalculator(10.0);

        myCalculator.minus(5.0);

        assertEquals(5.0, myCalculator.getResult());

    }

    @Test
    void multiplyTest() {

        MyCalculator myCalculator = new MyCalculator(2.0);

        myCalculator.multiply(2.0);

        assertEquals(4.0, myCalculator.getResult());

    }

    @Test
    void divideTest() {

        MyCalculator myCalculator = new MyCalculator(10.0);

        myCalculator.divide(2.0);

        assertEquals(5.0, myCalculator.getResult());

    }

    @Test
    void complicatedCalculatedTest() {
        //given
        MyCalculator myCalculator = new MyCalculator(0.0);

        Double result = myCalculator.add(10.0)
                .minus(4.0)
                .multiply(2.0)
                .divide(3.0)
                .getResult();

        assertEquals(4.0, result);
    }

    @Test
    void divideZeroTest() {
        //given
        MyCalculator myCalculator = new MyCalculator(10.0);


        //then
        assertThrows(MyCalculator.ZeroDivisionException.class,()->{
            myCalculator.divide(0.0);
        });
    }
}