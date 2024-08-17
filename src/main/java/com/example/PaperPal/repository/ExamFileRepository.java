package com.example.PaperPal.repository;

import com.example.PaperPal.entity.ExamFile;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamFileRepository extends MongoRepository<ExamFile, ObjectId> {

    ExamFile deleteExamFileByExamId(ObjectId id);

}
