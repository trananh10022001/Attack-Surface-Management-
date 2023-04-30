package com.example.asm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.asm.entity.UserEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<UserEntity, Integer> {
        public UserEntity findByUsername(String username);

        public int countAllByUsername(String username);
}
