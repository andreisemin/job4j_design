package ru.job4j.generics;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class RoleStoreTest {
    @Test
    void whenAddAndFindRoleNameIsStudent() {
        RoleStore store = new RoleStore();
        store.add(new Role("2", "Student"));
        Role result = store.findById("2");
        assertThat(result.getRoleName()).isEqualTo("Student");
    }

    @Test
    void whenAddAndFindThenRoleIsNull() {
        RoleStore store = new RoleStore();
        store.add(new Role("1", "Student"));
        Role result = store.findById("2");
        assertThat(result).isNull();
    }

    @Test
    void whenAddDuplicateAndFindUsernameIsStudent() {
        RoleStore store = new RoleStore();
        store.add(new Role("0", "Student"));
        store.add(new Role("0", "Admin"));
        Role result = store.findById("0");
        assertThat(result.getRoleName()).isEqualTo("Student");
    }

    @Test
    void whenReplaceRoleNameIsUser() {
        RoleStore store = new RoleStore();
        store.add(new Role("0", "Student"));
        store.replace("0", new Role("0", "Admin"));
        Role result = store.findById("0");
        assertThat(result.getRoleName()).isEqualTo("Admin");
    }

    @Test
    void whenNoReplaceRoleNameThenNoChangeRoleName() {
        RoleStore store = new RoleStore();
        store.add(new Role("0", "Student"));
        store.replace("1", new Role("0", "Admin"));
        Role result = store.findById("0");
        assertThat(result.getRoleName()).isEqualTo("Student");
    }

    @Test
    void whenDeleteRoleThenRoleIsNull() {
        RoleStore store = new RoleStore();
        store.add(new Role("1", "Student"));
        store.delete("1");
        Role result = store.findById("1");
        assertThat(result).isNull();
    }

    @Test
    void whenNoDeleteRoleThenRoleNameIsStudent() {
        RoleStore store = new RoleStore();
        store.add(new Role("0", "Student"));
        store.delete("1");
        Role result = store.findById("0");
        assertThat(result.getRoleName()).isEqualTo("Student");
    }

    @Test
    void whenReplaceOkThenTrue() {
        RoleStore store = new RoleStore();
        store.add(new Role("0", "Student"));
        boolean result = store.replace("0", new Role("0", "Admin"));
        assertThat(result).isTrue();
    }

    @Test
    void whenReplaceNotOkThenFalse() {
        RoleStore store = new RoleStore();
        store.add(new Role("0", "Student"));
        boolean result = store.replace("1", new Role("1", "Admin"));
        assertThat(result).isFalse();
    }
}