package ru.job4j.assertj;

import org.assertj.core.data.Index;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

class SimpleConvertTest {
    @Test
    void checkArray() {
        SimpleConvert simpleConvert = new SimpleConvert();
        String[] array = simpleConvert.toArray("first", "second", "three", "four", "five");
        assertThat(array).hasSize(5)
                .contains("second")
                .contains("first", Index.atIndex(0))
                .containsAnyOf("zero", "second", "six")
                .doesNotContain("first", Index.atIndex(1));
    }

    @Test
    void satisfyArray() {
        SimpleConvert simpleConvert = new SimpleConvert();
        String[] array = simpleConvert.toArray("first", "second", "three", "four", "five");
        assertThat(array).isNotNull()
                .allSatisfy(e -> {
                    assertThat(e.length()).isLessThan(10);
                    assertThat(e.length()).isGreaterThan(3);
                })
                .anySatisfy(e -> {
                    assertThat(e.length()).isLessThan(6);
                    assertThat(e.length()).isEqualTo(5);
                })
                .allMatch(e -> e.length() < 10)
                .anyMatch(e -> e.equals("second"))
                .noneMatch(e -> e.equals("seven"));
    }

    @Test
    void navigationArray() {
        SimpleConvert simpleConvert = new SimpleConvert();
        String[] array = simpleConvert.toArray("first", "second", "three", "four", "five");
        assertThat(array[0]).isEqualTo("first");
        assertThat(array[1]).isNotNull()
                .isEqualTo("second");
        assertThat(array[array.length - 1]).isNotNull()
                .isEqualTo("five");
    }

    @Test
    void filteredArray() {
        SimpleConvert simpleConvert = new SimpleConvert();
        String[] array = simpleConvert.toArray("first", "second", "three", "four", "five");
        assertThat(Arrays.stream(array)
                .filter(e -> e.length() > 5)
                .findFirst()
                .orElse(null)).isEqualTo("second");
        assertThat(Arrays.stream(array).filter(e -> e.length() < 5))
                .hasSize(2)
                .first().isEqualTo("four");
    }

    @Test
    void arrayToMap() {
        SimpleConvert simpleConvert = new SimpleConvert();
        Map<String, Integer> map = simpleConvert.toMap("first", "second", "three", "four", "five");

        assertThat(map).hasSize(5)
                .containsKeys("first", "second", "three")
                .containsValues(0, 1, 2)
                .doesNotContainKey("zero")
                .doesNotContainValue(5)
                .containsEntry("second", 1);
    }
}