package demo.Repository;


import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

public interface UserCrud extends MongoRepository<UserEntity, String> {

    public Optional<UserEntity> findByUserEmail(@Param("userEmail") String userEmail);

    public Optional<UserEntity> findByUserEmailAndSystemId(@Param("userEmail") String userEmail, @Param("systemId") String systemId);

}