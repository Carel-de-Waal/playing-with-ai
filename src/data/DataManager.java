/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import entities.NeuralNetwork;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author dewaa
 */
public class DataManager
{

    public <T> T persist(T entity)
    {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("AIcdw01PU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try
        {
            em.persist(entity);
            em.getTransaction().commit();
        } catch (Exception e)
        {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally
        {
            em.close();
        }
        return entity;
    }

    public <T> T merge(T entity)
    {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("AIcdw01PU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try
        {
            em.merge(entity);
            em.getTransaction().commit();
        } catch (Exception e)
        {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally
        {
            em.close();
        }
        return entity;
    }
    
        public <T> T remove(T entity)
    {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("AIcdw01PU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try
        {
            em.remove(entity);
            em.getTransaction().commit();
        } catch (Exception e)
        {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally
        {
            em.close();
        }
        return entity;
    }

}
