package br.com.bridi.rinhabackend.controllers;

import java.net.URI;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import br.com.bridi.rinhabackend.controllers.request.PessoaRequest;
import br.com.bridi.rinhabackend.controllers.request.PessoaResponse;
import br.com.bridi.rinhabackend.service.PessoaService;
import domain.Pessoa;

@RestController
@RequestMapping
public class PessoasController {

  @Autowired
  private PessoaService pessoaService;
  
  @GetMapping("/contagem-pessoas")
  public ResponseEntity<Integer> getCount() throws SQLException{
    return ResponseEntity.ok(pessoaService.countPessoa());
  }
  
  @GetMapping("/pessoas/{id}")
  public ResponseEntity<PessoaResponse> findById(@PathVariable UUID id) throws SQLException {
    
    Pessoa pessoa = pessoaService.getPessoa(id);
    if (pessoa != null) 
      return ResponseEntity.ok(pessoa.toDto());    
    
    return ResponseEntity.notFound().build();
  }
  
  @GetMapping("/pessoas")
  public ResponseEntity<List<PessoaResponse>> findById(@RequestParam(value = "t", required = false) String t)  throws SQLException {
    if (t == null || t.isEmpty() || t.isBlank()) {
      return ResponseEntity.badRequest().build();
    }
    List<PessoaResponse> collect = pessoaService.getPessoaByTerm(t).stream().map(Pessoa::toDto).collect(Collectors.toList());
    
    return ResponseEntity.ok(collect);
  }
  
  
  @PostMapping("/pessoas")
  public ResponseEntity<PessoaResponse> insertPessoa(@RequestBody PessoaRequest pessoaRequest) throws Exception, UnprocessableException {

    if (!pessoaRequest.valido()){
      return ResponseEntity.unprocessableEntity().build();
    }
    
    try {
      DateTimeFormatter.ofPattern("yyyy[-MM[-dd]]").parseBest(pessoaRequest.getNascimento(), LocalDate::from, YearMonth::from, Year::from);
    } catch(Exception e) {
      return ResponseEntity.unprocessableEntity().build();
    }
    
    try {
      Pessoa p = pessoaService.insertPessoa(pessoaRequest.toDomain());


      return ResponseEntity.created(URI.create("/pessoas/" + p.getId())).build();
    }catch(Exception e) {
      return ResponseEntity.unprocessableEntity().build();
    }    
  }
  
}
