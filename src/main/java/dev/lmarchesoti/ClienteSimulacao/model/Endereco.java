package dev.lmarchesoti.ClienteSimulacao.model;

import dev.lmarchesoti.ClienteSimulacao.dto.EnderecoDto;
import dev.lmarchesoti.ClienteSimulacao.model.enums.EstadoEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TB_ENDERECO", schema = "CLIENTESIMULACAO")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ENDERECO")
    private Long id;

    @OneToOne(mappedBy = "endereco")
    private Cliente cliente;

    @Column(name = "RUA")
    private String rua;

    @Column(name = "NUMERO")
    private Integer numero;

    @Column(name = "BAIRRO")
    private String bairro;

    @Column(name = "CEP")
    private String CEP;

    @Column(name = "CIDADE")
    private String cidade;

    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO")
    private EstadoEnum estado;

    public Endereco(EnderecoDto endereco) {
        this.rua = endereco.rua();
        this.numero = endereco.numero();
        this.bairro = endereco.bairro();
        this.CEP = endereco.CEP();
        this.cidade = endereco.cidade();
        this.estado = endereco.estado();
    }

    public void merge(EnderecoDto endereco) {
        this.rua = endereco.rua();
        this.numero = endereco.numero();
        this.bairro = endereco.bairro();
        this.CEP = endereco.CEP();
        this.cidade = endereco.cidade();
        this.estado = endereco.estado();
    }
}
