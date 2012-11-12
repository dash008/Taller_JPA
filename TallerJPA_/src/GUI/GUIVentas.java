/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Controladores.ControladorProveedor;
import Controladores.ControladorVenta;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Dash
 */
public class GUIVentas extends JFrame implements ActionListener
{
    Container container;
    JPanel panel;
    JTextField campo_id_venta,campo_cantidad,campo_valorUnitario,campo_fecha,campo_id_Producto,campo_id_Cliente;
    JButton consultar,eliminar,agregar,modificar;
    ControladorVenta controladorVenta;

    public GUIVentas() 
    {
        super("Ventas");
        container=getContentPane();
    
        panel = new JPanel();
        controladorVenta = new ControladorVenta();
        
        panel.setLayout( new GridLayout(8,2));
        
        campo_id_venta= new JTextField(30) ;
        campo_cantidad= new JTextField(30) ;
        campo_valorUnitario = new JTextField(30);
        campo_fecha= new JTextField(30) ;
        campo_id_Producto = new JTextField(30);
        campo_id_Cliente= new JTextField(30) ;
        
        
        consultar = new JButton("Consultar");
        eliminar = new JButton("Eliminar");
        modificar= new JButton("Modificar");
        agregar= new JButton("Agregar");
        
        consultar.addActionListener(this);
        eliminar.addActionListener(this);
        modificar.addActionListener(this);
        agregar.addActionListener(this);
        
        
        panel.add(new JLabel("Id Venta"));
        panel.add(campo_id_venta);
        panel.add(new JLabel("Cantidad"));
        panel.add(campo_cantidad);
        panel.add(new JLabel("Valor unitario"));
        panel.add(campo_valorUnitario);
        panel.add(new JLabel("Fecha"));
        panel.add(campo_fecha);
        panel.add(new JLabel("Id Prod"));
        panel.add(campo_id_Producto);
        panel.add(new JLabel("Cliente"));
        panel.add(campo_id_Cliente);
        
        
        
        
        panel.add(consultar);
        panel.add(eliminar);
        panel.add(modificar);
        panel.add(agregar);
               
        add(panel);
                
           
        
        setSize(300,200);
        setVisible(true);
    }
    
    public static void main (String[] args)
    {
        GUIVentas guiVentas = new GUIVentas();
        guiVentas.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    public void actionPerformed(ActionEvent ae)
    {
        if(ae.getSource()==consultar)
        {
            String id_venta=campo_id_venta.getText();
            String cantidad=campo_cantidad.getText();
            String valorUnitario=campo_valorUnitario.getText(); 
            String fecha=campo_fecha.getText();
            String id_Producto=campo_id_Producto.getText(); 
            String id_Cliente=campo_id_Cliente.getText();
            
            if(id_venta.equals("")) 
            {
                controladorVenta.consultar();    
            }
            else
            {
                controladorVenta.consultar(id_venta);            
            }
            
        }
    }
    
}
