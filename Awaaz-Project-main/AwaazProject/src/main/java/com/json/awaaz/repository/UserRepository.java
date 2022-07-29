package com.json.awaaz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.json.awaaz.model.User;

public interface UserRepository extends JpaRepository<User, Long> {


}
