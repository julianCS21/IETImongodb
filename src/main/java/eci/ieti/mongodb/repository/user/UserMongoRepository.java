package eci.ieti.mongodb.repository.user;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserMongoRepository  extends MongoRepository<User,String> {
}
