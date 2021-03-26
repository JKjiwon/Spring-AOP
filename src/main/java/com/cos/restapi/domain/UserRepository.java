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


    public User findById(Long id) {
        return store.get(id);
    }

    public Optional<User> findById2(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    public User save(User user) {
        if (user.getId() == null) {
            //  저장
            System.out.println("UserRepository.save");
            user.giveDatabaseId(++userId);
            System.out.println(user);
            store.put(user.getId(), user);
        } else {
            // 업데이트
            System.out.println("UserRepository.update");
            userId = user.getId();
            store.remove(userId);
            store.put(userId, user);
        }
        return user;
    }

    public void deleteById(Long id) {
        store.remove(id);
    }
}
