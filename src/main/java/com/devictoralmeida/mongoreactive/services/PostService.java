package com.devictoralmeida.mongoreactive.services;

import com.devictoralmeida.mongoreactive.dto.PostDTO;
import com.devictoralmeida.mongoreactive.repositories.PostRepository;
import com.devictoralmeida.mongoreactive.services.exceptions.ResourceNotFoundException;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Service
public class PostService {
  @Autowired
  private PostRepository repository;

  public Mono<PostDTO> findById(String id) {
    return repository.findById(id).map(PostDTO::new).switchIfEmpty(Mono.error(new ResourceNotFoundException("Recurso não encontrado")));
  }

  public Flux<PostDTO> findByTitle(String text) {
    return repository.searchTitle(text).map(PostDTO::new);
  }

  public Flux<PostDTO> fullSearch(String text, Instant minDate, Instant maxDate) {
    maxDate = maxDate.plusSeconds(86400);
    return repository.fullSearch(text, minDate, maxDate).map(PostDTO::new);
  }

  public Flux<PostDTO> findByUser(String id) {
    return repository.findByUser(new ObjectId(id)).map(PostDTO::new);
  }
}
