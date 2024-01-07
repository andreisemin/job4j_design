package ru.job4j.assertj;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class BoxTest {
    @Test
    void isThisSphere() {
        Box box = new Box(0, 10);
        String name = box.whatsThis();
        assertThat(name).isEqualTo("Sphere");
    }

    @Test
    void whatsThisTetrahedron() {
        Box box = new Box(4, 1);
        String name = box.whatsThis();
        assertThat(name).isEqualTo("Tetrahedron")
                .isGreaterThan("Sphere")
                .isNotEmpty();
    }

    @Test
    void whatsThisUnknown() {
        Box box = new Box(5, 1);
        String name = box.whatsThis();
        assertThat(name).isEqualTo("Unknown object")
                .isNotBlank()
                .containsIgnoringCase("Unknown")
                .startsWith("Unknown");
    }

    @Test
    void whatsAreaTetrahedron() {
        Box box = new Box(4, 1);
        double result = Math.round(box.getArea() / 0.01d) * 0.01d;
        assertThat(result).isNotZero()
                .isPositive()
                .isGreaterThan(1.72d)
                .isLessThan(1.74d)
                .isEqualTo(1.73d);
    }

    @Test
    void whatsAreaCube() {
        Box box = new Box(8, 2);
        double result = Math.round(box.getArea() / 0.01d) * 0.01d;
        assertThat(result).isNotZero()
                .isPositive()
                .isGreaterThan(23d)
                .isLessThan(25d)
                .isEqualTo(24d);
    }

    @Test
    void whatsAreaSphere() {
        Box box = new Box(0, 2);
        double result = Math.round(box.getArea() / 0.01d) * 0.01d;
        assertThat(result).isNotZero()
                .isPositive()
                .isGreaterThan(50.26d)
                .isLessThan(50.28d)
                .isEqualTo(50.27d);
    }

    @Test
    void whatIsExist() {
        Box box1 = new Box(0, 0);
        Box box2 = new Box(4, 1);
        Box box3 = new Box(6, 3);
        assertThat(box1.isExist()).isFalse();
        assertThat(box2.isExist()).isTrue();
        assertThat(box3.isExist()).isFalse();
    }

    @Test
    void whatsNumberOfVertices() {
        Box box1 = new Box(0, 2);
        Box box2 = new Box(-2, 1);
        Box box3 = new Box(6, 3);
        assertThat(box1.getNumberOfVertices()).isEqualTo(0);
        assertThat(box2.getNumberOfVertices()).isEqualTo(-1);
        assertThat(box3.getNumberOfVertices()).isEqualTo(-1);
    }
}