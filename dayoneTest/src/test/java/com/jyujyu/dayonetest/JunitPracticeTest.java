package com.jyujyu.dayonetest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JunitPracticeTest {

    @Test
    @DisplayName("Assert Equals 테스트")
    public void assertEqualsTest() {

       String expect = "Something";
       String actual = "Something";

        assertEquals(expect, actual);
    }

    @Test
    public void assertNotEqualsTest() {

        String expect = "Something";
        String actual = "Hello";

        assertNotEquals(expect,actual);
    }

    @Test
    public void assertTrueTest() {
        Integer a = 10;
        Integer b = 10;

        assertTrue(a.equals(b));

    }

    @Test
    public void assertFalseTest() {
        Integer a = 10;
        Integer b = 20;

        assertFalse(a.equals(b));

    }

    @Test
    public void assertThrowTest() {
        assertThrows(RuntimeException.class, () -> {
            throw new RuntimeException("임의 발생 에러");
        });

    }

    @Test
    public void assertNotNullTest() {
        String value = "Hello";

        assertNotNull(value);
    }

    @Test
    public void assertIterableEqualsTest() {
        List<Integer> list1 = List.of(1, 2);
        List<Integer> list2 = List.of(1, 2);


        assertIterableEquals(list1,list2);
    }

    @Test
    public void assertAllTest() {
        String expect = "Something";
        String actual = "Something";

        List<Integer> list1 = List.of(1, 2);
        List<Integer> list2 = List.of(1, 2);

        assertAll("Assert All",List.of(
                ()-> {assertEquals(expect, actual);},
                ()->{assertIterableEquals(list1,list2);}
        ));
    }
}
