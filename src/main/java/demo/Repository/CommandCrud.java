package demo.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommandCrud extends MongoRepository<CommandEntity, String> {
    
}
