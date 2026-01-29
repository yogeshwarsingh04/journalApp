package com.developer.journalApp.repository;

import com.developer.journalApp.entity.ConfigJournalAppEntity;
import com.developer.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigJournalAppRepository extends MongoRepository<ConfigJournalAppEntity, ObjectId> {

}
