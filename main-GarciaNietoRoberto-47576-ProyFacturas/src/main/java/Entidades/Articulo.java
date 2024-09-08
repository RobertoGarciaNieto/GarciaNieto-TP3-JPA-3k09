package Entidades;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
//@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Artículo")
public class Articulo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int cantidad;
    private String denominacion;
    private int precio;

    @Builder.Default
    @OneToMany(mappedBy = "articulo", cascade = CascadeType.PERSIST)
    private List<DetalleFactura> detallesFacturas= new ArrayList<DetalleFactura>();


    @Builder.Default
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})    // vamos a usar estas dos porque solo se necesita que se persistan o acutalicen porque no se va a
                                                                       // necesitar que cuando se elimine un artículo se elimine su categoría
    @JoinTable(       //Tabla intermedia que sirve para ManyToMany y relaciona los id de una con el de la otra
            name="Articulo_Categoria",
            joinColumns = @JoinColumn(name="articulo_id"),
            inverseJoinColumns =  @JoinColumn(name="categoria_id")
    )
    private List<Categoria> categorias = new ArrayList<Categoria>();
}
