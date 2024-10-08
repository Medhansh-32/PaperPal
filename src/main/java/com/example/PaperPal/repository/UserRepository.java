package com.example.PaperPal.repository;


import com.example.PaperPal.entity.Users;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<Users,ObjectId> {

    Users findByUserName(String username);
    Users findByEmail(String email);
}
