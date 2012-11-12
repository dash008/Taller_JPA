package Controladores;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;

public class FabricaObjetos 
{
    @PersistenceUnit
     //crea la fabrica de objetos, utiliza el patron de diseño factoria
    private EntityManagerFactory factory;
    
    //CONSTRUCTOR DE LA CLASE
    public FabricaObjetos()
    {       
        factory = Persistence.createEntityManagerFactory("TallerJPA_PU");
    }  
    
    //METODO QUE RETORNA LA FABRICA DE OBJETOS
    public EntityManagerFactory getFactory()
    {
        return factory;
    }
    
    public EntityManagerFactory crear()
    {
        return factory;
    }
}
