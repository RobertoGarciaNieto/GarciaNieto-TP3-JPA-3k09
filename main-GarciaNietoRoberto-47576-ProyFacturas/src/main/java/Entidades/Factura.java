package Entidades;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@Entity
@Table(name="Factura")
public class Factura implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fecha;
    private int numero;
    private int total;

    @ManyToOne(cascade = CascadeType.PERSIST) //CascadeType.PERSIST esto indica que si borramos un cliente se borran las facturas de ese cliente
                                              //pero si borramos una factura no borramos los clientes
    @JoinColumn(name="fk_cliente")
    private Cliente cliente;

    @Builder.Default
    @OneToMany(mappedBy = "factura",cascade = CascadeType.ALL, orphanRemoval = true) //orphanRemoval = true con esto al eliminar factura se elminan los detalles cumpliendo
                                                                                     // la composición y que cuando se cree factura se creen facturas detalles
    private List<DetalleFactura> detalles = new ArrayList<DetalleFactura>();

}
