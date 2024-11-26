package com.devictoralmeida.mongoreactive.repositories;

import com.devictoralmeida.mongoreactive.entities.User;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<User, String> {
  @Query("{ 'email': { $regex: ?0, $options: 'i' } }")
  Mono<User> findByEmail(String email);
}
