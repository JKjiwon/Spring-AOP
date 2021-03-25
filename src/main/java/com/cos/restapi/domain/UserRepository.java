package com.cos.restapi.domain;

import com.cos.restapi.config.UserNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepository {
    private static Map<Integer, User> store = new HashMap<>();
    private static int userId = 0;


    public List<User> findAll() {
        return new ArrayList<>(store.values());
    }

    public User findById(int id) {
        if(store.get(id) == null){
            throw new UserNotFoundException("해당 유저를 찾을 수 없습니다.");
        }
        return store.get(id);
    }

    public User save(User user) {
        if (user.getId() == 0) {
            //  저장
            user.setId(++userId);
            store.put(user.getId(), user);
        } else {
            // 업데이트
            userId = user.getId();
            store.remove(userId);
            store.put(userId, user);
        }

        return user;
    }

    public void deleteById(int id) {
        if(store.get(id) == null){
            throw new UserNotFoundException("해당 유저를 찾을 수 없습니다.");
        }
        store.remove(id);
    }
}
