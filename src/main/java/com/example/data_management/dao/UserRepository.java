package com.example.data_management.dao;

import com.example.data_management.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    public User findByUserNameAndUserPassword(String name,String password);
    public List<User> findAllBy();
    public void deleteByUserId(int dataSourceId);
}
