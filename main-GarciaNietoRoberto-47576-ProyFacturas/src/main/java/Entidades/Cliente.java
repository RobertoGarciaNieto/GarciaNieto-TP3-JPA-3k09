package Entidades;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity //Aclara a JPA y a Hibernate que es una entidad y se debe guardar en la BD
@Table(name="Cliente") //Indica que será una tabla en la BD con el nombre cliente

public class Cliente implements Serializable {
    @Id //Indica que el atributo de abajo será la PK de la tabla
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Indica que el atributo de abajo será autoincremental
    private Long id;

    @Column(name="nombre") //Setea el nombre la columna, si no lo ponemos entonces la columna toma el nombre del atributo
    private String nombre;

    @Column(name="apellido")
    private String apellido;

    @Column(name="dni", unique = true) //setea el nombre e indica que el atributo será único
    private int dni;

    @OneToOne(cascade = CascadeType.ALL) //Crea la relación 1 a 1 entre cliente y domicilio
    //Con cascade = CascadeType.ALL indicamos que cualquier cambio que se realice en cliente se reflejará en domicilio
    @JoinColumn(name="fk_domicilio") //esta columna contiene la FK en cliente, o sea la PK de domicilio
    private Domicilio domicilio;

    @OneToMany(mappedBy = "cliente")
    @Builder.Default
    private List<Factura> facturas = new ArrayList<Factura>();

}
