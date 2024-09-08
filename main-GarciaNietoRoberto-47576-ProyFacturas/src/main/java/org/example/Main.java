package org.example;

import Entidades.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        //conexión de apliación con la unidad de persistencia mediante EntityManagerFactory
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("example-unit");

        //intanciamos la entityManagerFactory
        EntityManager entityManager = entityManagerFactory.createEntityManager();


        try {
            entityManager.getTransaction().begin(); //Iniciar una transacción
                //CREO LAS FACTURAS
            Factura factura1 = Factura.builder().numero(13).fecha("05/09/2024").build();
            Factura factura2 = Factura.builder().numero(14).fecha("05/09/2024").build();
                //CREO LOS DOMICILIOS
            Domicilio domicilio1 = Domicilio.builder().nombreCalle("Mansilla").numero(411).build();
            Domicilio domicilio2 = Domicilio.builder().nombreCalle("Ojeda").numero(1086).build();
                //CREO LOS CLIENTES Y LES ASOCIO EL DOMICILIO
            Cliente cliente1 = Cliente.builder().nombre("Roberto").apellido("García Nieto").dni(43278170).domicilio(domicilio1).build();
            Cliente cliente2 = Cliente.builder().nombre("Alicia").apellido("Nieto").dni(18055035).domicilio(domicilio2).build();
                //ASOCIO LOS CLIENTES A LAS FACTURAS
            factura1.setCliente(cliente1);
            factura2.setCliente(cliente2);
                //ASOCIO LOS CLIENTES A LOS DOMICILIOS PARA COMPLETAR LA BIDIRECCIONALIDAD
            domicilio1.setCliente(cliente1); //A domicilio se le asigna el cliente para la bidireccionalidad
            domicilio2.setCliente(cliente2);
                //CREO LAS CATEGORIAS
            Categoria categoria1 = Categoria.builder().denominacion("Limpieza").build();
            Categoria categoria2 = Categoria.builder().denominacion("Alimentos").build();
                //CREO LOS ARTICULOS Y LOS ASOCIO A LAS CATEGORIA
            Articulo art1 = Articulo.builder().denominacion("Limpia Pisos Procenex").cantidad(200).precio(3000).categorias(Arrays.asList(categoria1)).build();
            Articulo art2 = Articulo.builder().denominacion("Jabón líquido Skip").cantidad(100).precio(6500).categorias(Arrays.asList(categoria1)).build();
            Articulo art3 = Articulo.builder().denominacion("Carne Vacuna").cantidad(1000).precio(8000).categorias(Arrays.asList(categoria2)).build();
            Articulo art4 = Articulo.builder().denominacion("Lomitos de atun al natural La Campagnola").cantidad(1500).precio(1300).categorias(Arrays.asList(categoria2)).build();
                //ASOCIO LAS CATEGORIAS A LOS ARTICULOS DE FORMA QUE SE CUMPLA LA BIDIRECCIONALIDAD
            categoria1.getArticulos().add(art1);
            categoria1.getArticulos().add(art2);
            categoria2.getArticulos().add(art3);
            categoria2.getArticulos().add(art4);
                //CREO LOS DETALLES FACTURAS
            DetalleFactura detfac1 = DetalleFactura.builder().articulo(art1).cantidad(2).build();
            detfac1.setSubtotal(detfac1.getCantidad()*art1.getPrecio());
            DetalleFactura detfac2 = DetalleFactura.builder().articulo(art3).cantidad(3).build();
            detfac2.setSubtotal(detfac2.getCantidad()*art3.getPrecio());
            DetalleFactura detfac3 = DetalleFactura.builder().articulo(art3).cantidad(5).build();
            detfac3.setSubtotal(detfac3.getCantidad()*art3.getPrecio());
            DetalleFactura detfac4 = DetalleFactura.builder().articulo(art4).cantidad(4).build();
            detfac4.setSubtotal(detfac4.getCantidad()*art4.getPrecio());
                //ASOCIO LOS ARTICULOS A LOS DETALLES FACTURA PARA LOGRAR LA BIDIRECCIONALIDAD
            art1.getDetallesFacturas().add(detfac1);
            art3.getDetallesFacturas().add(detfac2);
            art3.getDetallesFacturas().add(detfac3);
            art4.getDetallesFacturas().add(detfac4);
                // Asignar los detalles a la factura
            factura1.getDetalles().add(detfac1);
            factura1.getDetalles().add(detfac2);
                //DETERMINO LOS TOTALES DE FACTURAS
            factura1.setTotal(detfac1.getSubtotal()+ detfac2.getSubtotal());
            factura2.setTotal(detfac3.getSubtotal()+ detfac4.getSubtotal());


            entityManager.persist(art1);
            entityManager.persist(art2);
            entityManager.persist(art3);
            entityManager.persist(art4);

            entityManager.persist(factura1);
            entityManager.persist(factura2);

            entityManager.flush(); //para limpiar la conexión ??? se utiliza para forzar la escritura de todos los cambios pendientes en la sesión de persistencia a la base de datos.
            entityManager.getTransaction().commit(); //para hacer el commit y con esto terminar la persistencia

        }catch (Exception e){
            entityManager.getTransaction().rollback(); //hacer el rollback para evitar error si ingresamos registro inválido
        }
        entityManager.close(); //Cerrar conexión
        entityManagerFactory.close();  //Cerrar conexión
    }
}
