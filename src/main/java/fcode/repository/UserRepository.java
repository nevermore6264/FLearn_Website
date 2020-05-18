package fcode.repository;

import fcode.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findByDisplayName(String displayName);

    User findByEmailAddress(String emailAddress);

}
