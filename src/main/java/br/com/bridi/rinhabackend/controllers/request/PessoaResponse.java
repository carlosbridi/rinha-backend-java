package br.com.bridi.rinhabackend.controllers.request;

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
public class PessoaResponse {

  public String id;
  public String nome;
  public String nascimento;
  public String apelido;
  public String[] stack;
  
}
