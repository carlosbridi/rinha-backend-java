package domain;

import br.com.bridi.rinhabackend.controllers.request.PessoaResponse;
import br.com.bridi.rinhabackend.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pessoa {

  public String id;
  public String nome;
  public String apelido;
  public String nascimento;
  public String stack;
  public String term;
 
  
  public PessoaResponse toDto() {
    return PessoaResponse.builder()
        .id(this.id)
        .nome(this.getNome())
        .apelido(this.apelido)
        .nascimento(this.nascimento)
        .stack(Utils.getArrayStack(this.getStack()))
        .build();
  }
  
}
