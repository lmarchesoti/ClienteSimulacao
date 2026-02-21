package dev.lmarchesoti.ClienteSimulacao.model;

import dev.lmarchesoti.ClienteSimulacao.dto.ClienteDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TB_CLIENTE", schema = "CLIENTESIMULACAO")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CLIENTE")
    private Long id;

    @Column(name = "CPF")
    private String cpf;

    @Column(name = "NOME")
    private String nome;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_ENDERECO", referencedColumnName = "ID_ENDERECO")
    private Endereco endereco;

    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    private List<Simulacao> simulacoes;

    public Cliente(ClienteDto clienteDto) {
        this.cpf = clienteDto.cpf();
        this.nome = clienteDto.nome();
        if (clienteDto.endereco() != null) {
            this.endereco = new Endereco(clienteDto.endereco());
        }
    }

    public void merge(ClienteDto clienteDto) {
        this.cpf = clienteDto.cpf();
        this.nome = clienteDto.nome();

        if (this.endereco == null) {
            this.endereco = new Endereco(clienteDto.endereco());
        } else if (clienteDto.endereco() == null) {
            this.endereco = null;
        } else {
            this.endereco.merge(clienteDto.endereco());
        }
    }
}
