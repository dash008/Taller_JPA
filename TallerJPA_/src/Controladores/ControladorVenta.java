/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import DAO.ClienteJpaController;
import DAO.ProductoJpaController;
import DAO.ProveedorJpaController;
import DAO.VentaJpaController;
import Entidades.Cliente;
import Entidades.Venta;
import java.awt.Container;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Dash
 */
public class ControladorVenta 
{
    FabricaObjetos mi_fabrica;    
    VentaJpaController DaoVenta;
    ProductoJpaController DaoProducto;
    ClienteJpaController DaoCliente;
    EntityManager manager;
    String[] columnNames = {"Id venta","Cantidad","Valor por Unidad","Fecha","Cliente","Producto"};
    DefaultListModel modelo = new DefaultListModel();
    public ControladorVenta() 
    {
       mi_fabrica = new FabricaObjetos();   
       manager= mi_fabrica.crear().createEntityManager();

       //se requiere ingresar por parametro una fabrica de objetos para manipular las entidades (crear, modificar..en fin)
       DaoProducto = new ProductoJpaController(mi_fabrica.getFactory());
       DaoVenta   = new VentaJpaController(mi_fabrica.getFactory());
       DaoCliente = new ClienteJpaController(mi_fabrica.getFactory());
    }

    public void consultar()
    {
                        
            Iterator i;
            Vector v=new Vector();
            List list=new ArrayList();
            //sirve para ejecutar consultas
            list=manager.createQuery("SELECT v.idVenta,v.cantidad,v.valorUnitario,v.fecha,c.nombreCompleto,p.nombre FROM Venta v JOIN v.idProducto p JOIN v.idCliente c").getResultList().subList(1, 10);
            int contador=0;
            while(contador<list.size())
            {
                System.out.print(list.get(contador));
                contador++;
            }
            
    }
    public void consultar(String idVenta)
    {
                        
            Iterator i;
            //sirve para ejecutar consultas
            i = manager.createQuery("SELECT v FROM Venta v WHERE v.idVenta='"+idVenta+"'").getResultList().iterator();
            System.out.print("id |\t Nombre  |\t serial |\t proveedor\n");
            while(i.hasNext())
            {
                Venta v = (Venta) i.next();              
                
            }
    }



    
    
}
