package com.vtjaeger.payment.repositories;

import com.vtjaeger.payment.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    UserDetails findByEmail(String email);

    User findByVerificationCode(String verificationCode);
}