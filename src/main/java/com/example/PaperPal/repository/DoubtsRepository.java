package com.example.PaperPal.repository;

import com.example.PaperPal.entity.Doubts;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DoubtsRepository extends MongoRepository<Doubts, ObjectId> {

    List<Doubts> findByUserName(String userName);
}
