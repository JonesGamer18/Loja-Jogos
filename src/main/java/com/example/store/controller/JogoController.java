package com.example.store.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.store.dtos.JogoRecordDto;
import com.example.store.models.JogoModel;
import com.example.store.repositories.JogoRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/jogo")
public class JogoController {

    @Autowired
    JogoRepository jogoRepository;
    
    @PostMapping("/save")
    public ResponseEntity<JogoModel> saveJogo(@RequestBody @Valid JogoRecordDto jogoRecordDto){
        try {
            var jogoModel = new JogoModel();
            BeanUtils.copyProperties(jogoRecordDto, jogoModel);
            return ResponseEntity.status(HttpStatus.CREATED).body(jogoRepository.save(jogoModel));
        } catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleExceptions(Exception e) {
        // Log the exception (use a logging framework like Logback or Log4j)
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

    @GetMapping("/lista")
    public  ResponseEntity<List<JogoModel>> getAllJogos(){
        List<JogoModel> jogoList = jogoRepository.findAll();
        if (!jogoList.isEmpty()){
            for(JogoModel jogo: jogoList){
                UUID id = jogo.getIdJogo();
                jogo.add(linkTo(methodOn(JogoController.class).getOneJogo(id)).withSelfRel());
            }

        }
        return  ResponseEntity.status(HttpStatus.OK).body(jogoList);
    }

     @GetMapping("/jogo/{id}")
    public  ResponseEntity<Object> getOneJogo(@PathVariable(value = "id") UUID id){
        Optional<JogoModel> jogoO = jogoRepository.findById(id);
        if(jogoO.isEmpty()){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Jogo não encontrado");

        }
        jogoO.get().add(linkTo(methodOn(JogoController.class).getAllJogos()).withRel("Lista de jogos"));
        return  ResponseEntity.status(HttpStatus.OK).body(jogoO.get());

    }

    @PutMapping("/jogo/{id}")
    public ResponseEntity<Object> updateJogo(@PathVariable(value="id")UUID id, @RequestBody @Valid JogoRecordDto jogoRecordDto) {
        Optional<JogoModel> jogoO = jogoRepository.findById(id);
        if(jogoO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Jogo não encontrado");
        }
        var jogoModel = jogoO.get();
        BeanUtils.copyProperties(jogoRecordDto,jogoModel);
        return ResponseEntity.status(HttpStatus.OK).body(jogoRepository.save(jogoModel));
    }

     @DeleteMapping("/jogo/{id}")
    public  ResponseEntity<Object> deleteJogo(@PathVariable(value = "id") UUID id){
        Optional<JogoModel> jogoO = jogoRepository.findById(id);
        if(jogoO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Jogo não encontrado");
        }
        jogoRepository.delete(jogoO.get());
        return ResponseEntity.status(HttpStatus.OK).body("Jogo deletado com sucesso");

    }

}
