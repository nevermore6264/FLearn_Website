package fcode.repository;

import fcode.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {
    User findByDisplayName(String displayName);

    User findByEmailAddress(String emailAddress);

}
