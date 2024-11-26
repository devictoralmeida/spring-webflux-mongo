package com.devictoralmeida.mongoreactive.config;

import com.devictoralmeida.mongoreactive.entities.Post;
import com.devictoralmeida.mongoreactive.entities.User;
import com.devictoralmeida.mongoreactive.repositories.PostRepository;
import com.devictoralmeida.mongoreactive.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Arrays;

@Configuration
public class SeedingDatabase implements CommandLineRunner {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PostRepository postRepository;

  @Override
  public void run(String... args) throws Exception {

    Mono<Void> deleteUsers = userRepository.deleteAll();
    deleteUsers.subscribe();

    // Outra forma
    postRepository.deleteAll().subscribe();

    User maria = new User(null, "Maria Brown", "maria@gmail.com");
    User alex = new User(null, "Alex Green", "alex@gmail.com");
    User bob = new User(null, "Bob Grey", "bob@gmail.com");

    Flux<User> insertUser = userRepository.saveAll(Arrays.asList(maria, alex, bob));
    insertUser.subscribe();

    // Agora vamos atribuir a maria ao resultado do metodo findByEmail, aqui ela virá com o id
    maria = userRepository.findByEmail("maria@gmail.com").toFuture().get();
    alex = userRepository.findByEmail("alex@gmail.com").toFuture().get();
    bob = userRepository.findByEmail("bob@gmail.com").toFuture().get();

    Post post1 = new Post(null, Instant.parse("2022-11-21T18:35:24.00Z"), "Partiu viagem",
            "Vou viajar para São Paulo. Abraços!", maria.getId(), maria.getName());
    Post post2 = new Post(null, Instant.parse("2022-11-23T17:30:24.00Z"), "Bom dia", "Acordei feliz hoje!",
            maria.getId(), maria.getName());

    post1.addComment("Boa viagem mano!", Instant.parse("2022-11-21T18:52:24.00Z"), alex.getId(), alex.getName());
    post1.addComment("Aproveite!", Instant.parse("2022-11-22T11:35:24.00Z"), bob.getId(), bob.getName());
    post1.setUser(userRepository.findByEmail("maria@gmail.com").toFuture().get());

    post2.addComment("Tenha um ótimo dia!", Instant.parse("2022-11-23T18:35:24.00Z"), alex.getId(), alex.getName());
    post2.setUser(userRepository.findByEmail("maria@gmail.com").toFuture().get());

    postRepository.saveAll(Arrays.asList(post1, post2)).subscribe();
  }
}
