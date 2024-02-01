package ru.job4j.map;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class User {
    private String name;
    private int children;
    private Calendar birthday;

    public User(String name, int children, Calendar birthday) {
        this.name = name;
        this.children = children;
        this.birthday = birthday;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return children == user.children && Objects.equals(name, user.name)
                && Objects.equals(birthday, user.birthday);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, children, birthday);
    }

    public static void main(String[] args) {
        Map<User, Object> map = new HashMap<>(16);
        Calendar birthday = Calendar.getInstance();
        User user1 = new User("Andrei", 19, birthday);
        int hashCode1 = user1.hashCode();
        int hash1 = hashCode1 ^ (hashCode1 >>> 16);
        int bucket1 = hash1 & 15;
        System.out.printf("user1, hashcode: %s, hash: %s, bucket: %s\n",
                hashCode1, hash1, bucket1);
        User user2 = new User("Andrei", 19, birthday);
        int hashCode2 = user2.hashCode();
        int hash2 = hashCode2 ^ (hashCode2 >>> 16);
        int bucket2 = hash2 & 15;
        System.out.printf("user2, hashcode: %s, hash: %s, bucket: %s\n",
                hashCode2, hash2, bucket2);
        map.put(user1, new Object());
        map.put(user2, new Object());
        for (User user : map.keySet()) {
            int hashCode = user.hashCode();
            int hash = hashCode ^ (hashCode >>> 16);
            int bucket = hash & 15;
            System.out.printf("%s, hashcode: %s, hash: %s, bucket: %s\n",
                    map.get(user), hashCode, hash, bucket);
        }
    }
}
