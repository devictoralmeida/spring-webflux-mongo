package com.devictoralmeida.mongoreactive.controllers;

import com.devictoralmeida.mongoreactive.dto.UserDTO;
import com.devictoralmeida.mongoreactive.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/users")
public class UserController {
  @Autowired
  private UserService service;

  @GetMapping
  public Flux<UserDTO> findAll() {
    return service.findAll();
  }

  @GetMapping(value = "/{id}")
  public Mono<ResponseEntity<UserDTO>> findById(@PathVariable String id) {
    return service.findById(id).map(userDTO -> ResponseEntity.ok().body(userDTO));
  }

  @PostMapping
  public Mono<ResponseEntity<UserDTO>> insert(@RequestBody UserDTO dto, UriComponentsBuilder builder) {
    return service.insert(dto).map(userDTO -> ResponseEntity.created(builder.path("/users/{id}").buildAndExpand(userDTO.getId()).toUri()).body(userDTO));
  }

  @PutMapping(value = "/{id}")
  public Mono<ResponseEntity<UserDTO>> update(@PathVariable String id, @RequestBody UserDTO dto) {
    return service.update(id, dto).map(userDTO -> ResponseEntity.ok().body(userDTO));
  }

  @DeleteMapping(value = "/{id}")
  public Mono<ResponseEntity<Void>> delete(@PathVariable String id) {
    // O metodo then é utilizado para executar uma ação após a execução de um metodo reativo
    // O metodo just é utilizado para criar um Mono com um valor específico
    return service.delete(id).then(Mono.just(ResponseEntity.noContent().<Void>build()));
  }
}
