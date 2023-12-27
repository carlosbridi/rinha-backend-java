package br.com.bridi.rinhabackend.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import br.com.bridi.rinhabackend.utils.DatabaseConnection;
import domain.Pessoa;

@Component
public class PessoaService {

  @Autowired
  private PessoaCache pessoaCache;
  
  public Pessoa insertPessoa(Pessoa pessoa) throws Exception {
  
    if (pessoaCache.hasApelidoCache(pessoa.getApelido())) {
      throw new Exception("Apelido already in cache");
    }
    
    DatabaseConnection instance = DatabaseConnection.getInstance();
    Connection con = instance.getConnection();

    String sql = "INSERT INTO PESSOA (id, nome, apelido, nascimento, stack) VALUES (?, ?, ?, ?, ?)";
    PreparedStatement stmt = con.prepareStatement(sql);
    stmt.setObject(1, pessoa.getId());
    stmt.setString(2, pessoa.getNome());
    stmt.setString(3, pessoa.getApelido());
    stmt.setString(4, pessoa.getNascimento());
    stmt.setString(5, pessoa.getStack());
    stmt.executeUpdate();
    
    pessoaCache.addApelidoCache(pessoa.getApelido());
    return pessoa;
  }
  
  public Pessoa getPessoa(UUID uuid) throws SQLException {
    DatabaseConnection instance = DatabaseConnection.getInstance();
    
    String sql = "select id, nome, apelido, stack, nascimento from pessoa where id = ?";
    Connection con = instance.getConnection();
    PreparedStatement stmt = con.prepareStatement(sql);
    stmt.setString(1, uuid.toString());
    
    ResultSet executeQuery = stmt.executeQuery();
    if (executeQuery.next()) {
      return Pessoa.builder()
          .id(executeQuery.getString(1))
          .nome(executeQuery.getString(2))
          .apelido(executeQuery.getString(3))
          .stack(executeQuery.getString(4))
          .nascimento(executeQuery.getString(5)) 
          .build();
    } 
    
    return null;
  }
  
  public List<Pessoa> getPessoaByTerm(String term) throws SQLException {
    DatabaseConnection instance = DatabaseConnection.getInstance();
    
    term = "%"+term+"%";
    String sql = "select id, nome, apelido, stack, nascimento from pessoa where termo like ?";
    Connection con = instance.getConnection();
    PreparedStatement stmt = con.prepareStatement(sql);
    stmt.setString(1, term);
    
    ResultSet executeQuery = stmt.executeQuery();
    List<Pessoa> pessoas = new ArrayList<Pessoa>();
    while (executeQuery.next()) {
      pessoas.add(Pessoa.builder()
          .id(executeQuery.getString(1))
          .nome(executeQuery.getString(2))
          .apelido(executeQuery.getString(3))
          .stack(executeQuery.getString(4))
          .nascimento(executeQuery.getString(5)) 
          .build());
    } 
    return pessoas;
  }
  
  public int countPessoa() throws SQLException {
    DatabaseConnection instance = DatabaseConnection.getInstance();
    Connection con = instance.getConnection();
    
    String sql = "select count(*) as count from pessoa";
    Statement stmt = con.createStatement();
    ResultSet executeQuery = stmt.executeQuery(sql);
    if (executeQuery.next()) {
      return executeQuery.getInt(1);
    } 
    
    return 0;
  }
  
}
