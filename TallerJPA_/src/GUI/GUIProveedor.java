/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Controladores.ControladorProveedor;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Dash
 */
public class GUIProveedor extends JFrame implements ActionListener
{
    Container container;
    JPanel panel;
    JTextField campo_id_producto,campo_nombre,campo_direccion,campo_telefono,campo_ubicacion,campo_e_mail,campo_id_proveedor;
    JButton consultar,eliminar,agregar,modificar;
    ControladorProveedor controladorProveedor;
    
    public GUIProveedor()  
    {
        super("Proveedor");
        
        container= getContentPane();
        panel = new JPanel();
        controladorProveedor = new ControladorProveedor();
        
        panel.setLayout( new GridLayout(9,2));
        
        campo_id_proveedor= new JTextField(30) ;
        campo_id_producto= new JTextField(30) ;
        campo_nombre = new JTextField(30);
        campo_direccion= new JTextField(30) ;
        campo_telefono = new JTextField(30);
        campo_ubicacion= new JTextField(30) ;
        campo_e_mail= new JTextField(30) ;
        
        consultar = new JButton("Consultar");
        eliminar = new JButton("Eliminar");
        modificar= new JButton("Modificar");
        agregar= new JButton("Agregar");
        
        consultar.addActionListener(this);
        eliminar.addActionListener(this);
        modificar.addActionListener(this);
        agregar.addActionListener(this);
        
        
        panel.add(new JLabel("Id Proovedor"));
        panel.add(campo_id_proveedor);
        panel.add(new JLabel("Id Prod"));
        panel.add(campo_id_producto);
        panel.add(new JLabel("Nombre"));
        panel.add(campo_nombre);
        panel.add(new JLabel("Direccion"));
        panel.add(campo_direccion);
        panel.add(new JLabel("Telefono"));
        panel.add(campo_telefono);
        panel.add(new JLabel("Ubicacion"));
        panel.add(campo_ubicacion);
        panel.add(new JLabel("Email"));
        panel.add(campo_e_mail);
        
        panel.add(consultar);
        panel.add(eliminar);
        panel.add(modificar);
        panel.add(agregar);
               
        add(panel);
                
           
        
        setSize(300,200);
        setVisible(true);
    }
 
    public static void main(String[] args)
    {
        GUIProveedor guiProveedor = new GUIProveedor();
        guiProveedor.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    public void actionPerformed(ActionEvent ae)
    {
        if(ae.getSource()==agregar)
        {
            System.out.print("Entra a agregar");
            String id_proveedor= campo_id_proveedor.getText() ;
            String id_producto= campo_id_producto.getText() ;
            String nombre = campo_nombre.getText();
            String direccion = campo_direccion.getText();
            String telefono = campo_telefono.getText();
            String ubicacion = campo_ubicacion.getText();
            String e_mail = campo_e_mail.getText();
            
            controladorProveedor.agregarProveedor(id_proveedor,id_producto, nombre, direccion, telefono, ubicacion, e_mail);
        }
    }
}
