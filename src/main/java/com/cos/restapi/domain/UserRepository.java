package com.cos.restapi.domain;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserRepository {
    private static Map<Long, User> store = new HashMap<>();
    private static Long userId = 0L;


    public List<User> findAll() {
        return new ArrayList<>(store.values());
    }


    public Optional<User> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    public User save(User user) {
        //  저장
        System.out.println("UserRepository.save");
        user.giveDatabaseId(++userId);
        store.put(user.getId(), user);
        return user;
    }

    public void deleteById(Long id) {
        store.remove(id);
    }
}
