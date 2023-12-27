package br.com.bridi.rinhabackend.controllers.request;

import java.util.List;
import java.util.UUID;
import br.com.bridi.rinhabackend.utils.Utils;
import domain.Pessoa;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PessoaRequest {

  public String nome;
  public String nascimento;
  public String apelido;
  public List<String> stack;
  
  public Pessoa toDomain() {
    return Pessoa.builder()
        .id(UUID.randomUUID().toString())
        .apelido(apelido)
        .nome(nome)
        .nascimento(nascimento)
        .stack(Utils.getStackString(this.stack))
        .build();
  }
  
  public boolean valido() {
    var apelidoValido = apelido != null && !apelido.isEmpty() && apelido.length() < 32;
    var nomeValido = nome != null && !nome.isEmpty() && nome.length() < 100;    
    var stackValida = stack != null && stack.stream().allMatch(value -> value != null && value.length() < 32);
    return apelidoValido && nomeValido && stackValida;
  }
  
}
