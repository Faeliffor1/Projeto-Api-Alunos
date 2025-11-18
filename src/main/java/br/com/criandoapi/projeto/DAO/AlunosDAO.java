package br.com.criandoapi.projeto.DAO;

import br.com.criandoapi.projeto.Model.Alunos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface AlunosDAO extends JpaRepository<Alunos, Integer> {

}
