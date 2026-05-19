package Projeto_SEA.IFSP.Model;

import Projeto_SEA.IFSP.Enum.TipoUsuario;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "cae")
public class Cae extends Usuario {

    public Cae() {

    }

    public Cae(Long id, String nome, String email,
               String prontuario, String senha,
               String imagem) {

        super(id, nome, email, prontuario, senha, imagem, TipoUsuario.CAE);

        this.tipoUsuario = TipoUsuario.CAE;
    }
}