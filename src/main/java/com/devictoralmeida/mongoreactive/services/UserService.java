package com.devictoralmeida.mongoreactive.services;

import com.devictoralmeida.mongoreactive.dto.UserDTO;
import com.devictoralmeida.mongoreactive.entities.User;
import com.devictoralmeida.mongoreactive.repositories.UserRepository;
import com.devictoralmeida.mongoreactive.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {
  @Autowired
  private UserRepository repository;

  public Flux<UserDTO> findAll() {
    Flux<UserDTO> result = repository.findAll().map(UserDTO::new);
    return result;
  }

  public Mono<UserDTO> findById(String id) {
    return repository.findById(id).map(UserDTO::new).switchIfEmpty(Mono.error(new ResourceNotFoundException("Recurso não encontrado")));
  }

  public Mono<UserDTO> insert(UserDTO dto) {
    User entity = new User();
    copyDtoToEntity(dto, entity);
    Mono<UserDTO> result = repository.save(entity).map(UserDTO::new);
    return result;
  }

  public Mono<UserDTO> update(String id, UserDTO dto) {
    // Com o flatmap é possível fazer o merge, transformando uma ou mais streams em uma nova stream
    // O findById retorna um Mono<User>, e com o flatMap é possível atualizar a entidade e retornar um novo Mono<User>
    return repository.findById(id).flatMap(existingUser -> {
              copyDtoToEntity(dto, existingUser);
              return repository.save(existingUser);
            })
            .map(UserDTO::new)
            .switchIfEmpty(Mono.error(new ResourceNotFoundException("Recurso não encontrado")));
  }

  public Mono<Void> delete(String id) {
    return repository.findById(id)
            .switchIfEmpty(Mono.error(new ResourceNotFoundException("Recurso não encontrado")))
            .flatMap(user -> repository.delete(user));
  }

  private void copyDtoToEntity(UserDTO dto, User entity) {
    entity.setName(dto.getName());
    entity.setEmail(dto.getEmail());
  }
}
