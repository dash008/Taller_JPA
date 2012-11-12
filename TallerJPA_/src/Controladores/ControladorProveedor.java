/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

/**
 *
 * @author Dash
 */
import DAO.ProductoJpaController;
import DAO.ProveedorJpaController;
import Entidades.Producto;
import Entidades.Proveedor;
import javax.persistence.EntityManager;

    public class ControladorProveedor 
    {
            String id_proveedor ;
            String id_producto ;
            String nombre ;
            String direccion ;
            String telefono ;
            String ubicacion ;
            String e_mail ;

            private FabricaObjetos mi_fabrica;    
            ProveedorJpaController DaoProveedor;
            ProductoJpaController DaoProducto;
            EntityManager manager;

    public ControladorProveedor() 
    {
        mi_fabrica = new FabricaObjetos();   
       manager= mi_fabrica.crear().createEntityManager();

       //se requiere ingresar por parametro una fabrica de objetos para manipular las entidades (crear, modificar..en fin)
       DaoProducto = new ProductoJpaController(mi_fabrica.getFactory());
       DaoProveedor   = new ProveedorJpaController(mi_fabrica.getFactory());


    }




    public void agregarProveedor(String id_proveedor_in,String id_producto_in, String nombre_in, String direccion_in,String telefono_in,String ubicacion_in, String e_mail_in)
    {
        id_proveedor=id_proveedor_in;
        id_producto=id_producto_in;
        nombre=nombre_in;
        direccion=direccion_in;
        telefono=telefono_in;
        ubicacion=ubicacion_in;
        e_mail=e_mail_in;

        Proveedor proveedor= new Proveedor();
        Producto producto = DaoProducto.findProducto(id_producto);
        
        proveedor.setIdProveedor(id_proveedor);
        proveedor.setIdProducto(producto);
        proveedor.setNombre(nombre);
        proveedor.setDireccion(direccion);
        proveedor.setTelefono(telefono);
        proveedor.setUbicacion(ubicacion);
        proveedor.setEMail(e_mail);

        try
        {
            System.out.print("Da: "+id_producto_in+" "+ nombre_in+" "+direccion_in+" "+telefono_in+" "+ubicacion_in +" "+e_mail_in);
            DaoProveedor.create(proveedor);

        }
        catch(Exception e)
        {

        }

    }
}
