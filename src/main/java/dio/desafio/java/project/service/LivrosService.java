package dio.desafio.java.project.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import dio.desafio.java.project.dto.LivrosDTO;
import dio.desafio.java.project.model.Livros;
import dio.desafio.java.project.repository.LivrosRepository;

@SuppressWarnings("unused")
@Service
public class LivrosService {
    @Autowired
    LivrosRepository repository;

    public Livros create(LivrosDTO livrosDTO){
        if(livrosDTO.titulo() == null || livrosDTO.descricao() == null || livrosDTO.genero()==null){
            throw new IllegalArgumentException("O livro não pode conter dados nulos");
        }
        Livros livro = new Livros();
        BeanUtils.copyProperties(livrosDTO, livro);
        repository.save(livro);
        return livro;
    }

    public List<Livros> findAll(){
        return repository.findAll();
    }

    public Livros updLivro(LivrosDTO livrosDTO){
        if(livrosDTO.titulo() == null || livrosDTO.descricao() == null || livrosDTO.genero()==null){
            throw new IllegalArgumentException("O livro não pode conter dados nulos");
        }
        Livros livro = new Livros();
        if(repository.findById(livrosDTO.id()).isPresent()){
            BeanUtils.copyProperties(livrosDTO,livro);
            repository.save(livro);
            return livro;
        }
        else{
            throw new RuntimeException("Livro não encontrado");
        }
    }

    public String removeLivro(LivrosDTO livrosDTO){
        Livros livro = new Livros();
        if(repository.findById(livrosDTO.id()).isPresent()){
            BeanUtils.copyProperties(livrosDTO, livro);
            repository.delete(livro);
            return "Livro "+livro.getTitulo()+" foi removido";
        }
        else{
            throw new RuntimeException("Livro não encontrado");
        }
    }
}
