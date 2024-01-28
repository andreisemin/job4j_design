package ru.job4j.iterator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Predicate;

class ListUtilsTest {

    private List<Integer> input;

    @BeforeEach
    void setUp() {
        input = new ArrayList<>(Arrays.asList(1, 3));
    }

    @Test
    void whenAddBefore() {
        ListUtils.addBefore(input, 1, 2);
        assertThat(input).hasSize(3).containsSequence(1, 2, 3);
    }

    @Test
    void whenAddBeforeWithInvalidIndex() {
        assertThatThrownBy(() -> ListUtils.addBefore(input, 3, 2))
                .isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    void whenAddAfter() {
        ListUtils.addAfter(input, 0, 2);
        assertThat(input).hasSize(3).containsSequence(1, 2, 3);
    }

    @Test
    void whenAddAfterWithInvalidIndex() {
        assertThatThrownBy(() -> ListUtils.addAfter(input, 2, 2))
                .isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    void whenRemoveAll() {
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        List<Integer> elements = new ArrayList<>(Arrays.asList(2, 3, 4));
        ListUtils.removeAll(list, elements);
        assertThat(list).hasSize(2).containsSequence(1, 5);
    }

    @Test
    void whenRemoveAllElementsInEmptyList() {
        List<Integer> list = new ArrayList<>();
        List<Integer> elements = new ArrayList<>(Arrays.asList(1, 2, 3));
        ListUtils.removeAll(list, elements);
        assertThat(list).isEmpty();
    }

    @Test
    void whenRemoveIfFilterIsEven() {
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7));
        Predicate<Integer> filter = (n) -> n % 2 == 0;
        ListUtils.removeIf(list, filter);
        assertThat(list).containsExactly(1, 3, 5, 7);
    }

    @Test
    void whenReplaceIfFilterIsEven() {
        var list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6));
        var value = 10;
        Predicate<Integer> filter = (n) -> n % 2 == 0;
        ListUtils.replaceIf(list, filter, value);
        assertThat(list).containsExactly(1, 10, 3, 10, 5, 10);
    }
}