package com.monolitico.repository;

import com.monolitico.model.ImagenMongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagenMongoRepository extends MongoRepository<ImagenMongo,String> {

}
