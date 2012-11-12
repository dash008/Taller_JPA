/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.NonexistentEntityException;
import DAO.exceptions.PreexistingEntityException;
import Entidades.Producto;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Proveedor;
import java.util.ArrayList;
import java.util.Collection;
import Entidades.Venta;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Dash
 */
public class ProductoJpaController implements Serializable {

    public ProductoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Producto producto) throws PreexistingEntityException, Exception {
        if (producto.getProveedorCollection() == null) {
            producto.setProveedorCollection(new ArrayList<Proveedor>());
        }
        if (producto.getVentaCollection() == null) {
            producto.setVentaCollection(new ArrayList<Venta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Proveedor> attachedProveedorCollection = new ArrayList<Proveedor>();
            for (Proveedor proveedorCollectionProveedorToAttach : producto.getProveedorCollection()) {
                proveedorCollectionProveedorToAttach = em.getReference(proveedorCollectionProveedorToAttach.getClass(), proveedorCollectionProveedorToAttach.getIdProveedor());
                attachedProveedorCollection.add(proveedorCollectionProveedorToAttach);
            }
            producto.setProveedorCollection(attachedProveedorCollection);
            Collection<Venta> attachedVentaCollection = new ArrayList<Venta>();
            for (Venta ventaCollectionVentaToAttach : producto.getVentaCollection()) {
                ventaCollectionVentaToAttach = em.getReference(ventaCollectionVentaToAttach.getClass(), ventaCollectionVentaToAttach.getIdVenta());
                attachedVentaCollection.add(ventaCollectionVentaToAttach);
            }
            producto.setVentaCollection(attachedVentaCollection);
            em.persist(producto);
            for (Proveedor proveedorCollectionProveedor : producto.getProveedorCollection()) {
                Producto oldIdProductoOfProveedorCollectionProveedor = proveedorCollectionProveedor.getIdProducto();
                proveedorCollectionProveedor.setIdProducto(producto);
                proveedorCollectionProveedor = em.merge(proveedorCollectionProveedor);
                if (oldIdProductoOfProveedorCollectionProveedor != null) {
                    oldIdProductoOfProveedorCollectionProveedor.getProveedorCollection().remove(proveedorCollectionProveedor);
                    oldIdProductoOfProveedorCollectionProveedor = em.merge(oldIdProductoOfProveedorCollectionProveedor);
                }
            }
            for (Venta ventaCollectionVenta : producto.getVentaCollection()) {
                Producto oldIdProductoOfVentaCollectionVenta = ventaCollectionVenta.getIdProducto();
                ventaCollectionVenta.setIdProducto(producto);
                ventaCollectionVenta = em.merge(ventaCollectionVenta);
                if (oldIdProductoOfVentaCollectionVenta != null) {
                    oldIdProductoOfVentaCollectionVenta.getVentaCollection().remove(ventaCollectionVenta);
                    oldIdProductoOfVentaCollectionVenta = em.merge(oldIdProductoOfVentaCollectionVenta);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProducto(producto.getIdProducto()) != null) {
                throw new PreexistingEntityException("Producto " + producto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Producto producto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto persistentProducto = em.find(Producto.class, producto.getIdProducto());
            Collection<Proveedor> proveedorCollectionOld = persistentProducto.getProveedorCollection();
            Collection<Proveedor> proveedorCollectionNew = producto.getProveedorCollection();
            Collection<Venta> ventaCollectionOld = persistentProducto.getVentaCollection();
            Collection<Venta> ventaCollectionNew = producto.getVentaCollection();
            Collection<Proveedor> attachedProveedorCollectionNew = new ArrayList<Proveedor>();
            for (Proveedor proveedorCollectionNewProveedorToAttach : proveedorCollectionNew) {
                proveedorCollectionNewProveedorToAttach = em.getReference(proveedorCollectionNewProveedorToAttach.getClass(), proveedorCollectionNewProveedorToAttach.getIdProveedor());
                attachedProveedorCollectionNew.add(proveedorCollectionNewProveedorToAttach);
            }
            proveedorCollectionNew = attachedProveedorCollectionNew;
            producto.setProveedorCollection(proveedorCollectionNew);
            Collection<Venta> attachedVentaCollectionNew = new ArrayList<Venta>();
            for (Venta ventaCollectionNewVentaToAttach : ventaCollectionNew) {
                ventaCollectionNewVentaToAttach = em.getReference(ventaCollectionNewVentaToAttach.getClass(), ventaCollectionNewVentaToAttach.getIdVenta());
                attachedVentaCollectionNew.add(ventaCollectionNewVentaToAttach);
            }
            ventaCollectionNew = attachedVentaCollectionNew;
            producto.setVentaCollection(ventaCollectionNew);
            producto = em.merge(producto);
            for (Proveedor proveedorCollectionOldProveedor : proveedorCollectionOld) {
                if (!proveedorCollectionNew.contains(proveedorCollectionOldProveedor)) {
                    proveedorCollectionOldProveedor.setIdProducto(null);
                    proveedorCollectionOldProveedor = em.merge(proveedorCollectionOldProveedor);
                }
            }
            for (Proveedor proveedorCollectionNewProveedor : proveedorCollectionNew) {
                if (!proveedorCollectionOld.contains(proveedorCollectionNewProveedor)) {
                    Producto oldIdProductoOfProveedorCollectionNewProveedor = proveedorCollectionNewProveedor.getIdProducto();
                    proveedorCollectionNewProveedor.setIdProducto(producto);
                    proveedorCollectionNewProveedor = em.merge(proveedorCollectionNewProveedor);
                    if (oldIdProductoOfProveedorCollectionNewProveedor != null && !oldIdProductoOfProveedorCollectionNewProveedor.equals(producto)) {
                        oldIdProductoOfProveedorCollectionNewProveedor.getProveedorCollection().remove(proveedorCollectionNewProveedor);
                        oldIdProductoOfProveedorCollectionNewProveedor = em.merge(oldIdProductoOfProveedorCollectionNewProveedor);
                    }
                }
            }
            for (Venta ventaCollectionOldVenta : ventaCollectionOld) {
                if (!ventaCollectionNew.contains(ventaCollectionOldVenta)) {
                    ventaCollectionOldVenta.setIdProducto(null);
                    ventaCollectionOldVenta = em.merge(ventaCollectionOldVenta);
                }
            }
            for (Venta ventaCollectionNewVenta : ventaCollectionNew) {
                if (!ventaCollectionOld.contains(ventaCollectionNewVenta)) {
                    Producto oldIdProductoOfVentaCollectionNewVenta = ventaCollectionNewVenta.getIdProducto();
                    ventaCollectionNewVenta.setIdProducto(producto);
                    ventaCollectionNewVenta = em.merge(ventaCollectionNewVenta);
                    if (oldIdProductoOfVentaCollectionNewVenta != null && !oldIdProductoOfVentaCollectionNewVenta.equals(producto)) {
                        oldIdProductoOfVentaCollectionNewVenta.getVentaCollection().remove(ventaCollectionNewVenta);
                        oldIdProductoOfVentaCollectionNewVenta = em.merge(oldIdProductoOfVentaCollectionNewVenta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = producto.getIdProducto();
                if (findProducto(id) == null) {
                    throw new NonexistentEntityException("The producto with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto producto;
            try {
                producto = em.getReference(Producto.class, id);
                producto.getIdProducto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The producto with id " + id + " no longer exists.", enfe);
            }
            Collection<Proveedor> proveedorCollection = producto.getProveedorCollection();
            for (Proveedor proveedorCollectionProveedor : proveedorCollection) {
                proveedorCollectionProveedor.setIdProducto(null);
                proveedorCollectionProveedor = em.merge(proveedorCollectionProveedor);
            }
            Collection<Venta> ventaCollection = producto.getVentaCollection();
            for (Venta ventaCollectionVenta : ventaCollection) {
                ventaCollectionVenta.setIdProducto(null);
                ventaCollectionVenta = em.merge(ventaCollectionVenta);
            }
            em.remove(producto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Producto> findProductoEntities() {
        return findProductoEntities(true, -1, -1);
    }

    public List<Producto> findProductoEntities(int maxResults, int firstResult) {
        return findProductoEntities(false, maxResults, firstResult);
    }

    private List<Producto> findProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Producto.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Producto findProducto(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Producto.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Producto> rt = cq.from(Producto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
