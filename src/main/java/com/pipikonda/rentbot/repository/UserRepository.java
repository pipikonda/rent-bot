package com.pipikonda.rentbot.repository;

import com.pipikonda.rentbot.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
