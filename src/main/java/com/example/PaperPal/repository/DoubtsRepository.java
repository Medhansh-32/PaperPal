package com.example.PaperPal.repository;

import com.example.PaperPal.entity.Doubts;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DoubtsRepository extends MongoRepository<Doubts, ObjectId> {
}
