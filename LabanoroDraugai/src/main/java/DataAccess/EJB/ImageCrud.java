/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccess.EJB;


import DataAccess.JPA.Image;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.Part;

/**
 *
 * @author Povilas <your.name at your.org>
 */

@Stateless
@LocalBean
public class ImageCrud {
    @PersistenceContext
    EntityManager img;
   
    public List<Image> images = new ArrayList<>();
    
    public List<Image> findAllImage() {
	List<Image> list = img.createNamedQuery("Image.findAll").getResultList();
	return list;
    }
    
    public Image findImage(String Id){
        try{
         System.out.println("Image id: " + Id);
         Image image = (Image) img.createNamedQuery("Image.findById").setParameter("id", Integer.parseInt(Id)).getResultList().get(0);
         //Image image2 =(Image) img.createNamedQuery("Image.findById").setParameter("id", "2").getResultList().get(0);
         return image;
        }catch(Exception e){
            System.out.println("ismetė exception");
        }
        return null;
    }
    
    public Image updateImage(Image changedImage){
       Image b = img.merge(changedImage); // reference to another object than the one passed in when the object was already loaded in the current context.
       img.flush();
       return b;
    }
    
    private String error="none";

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
    
    @PostConstruct
    public void init() {
       // System.out.println("inicializuoju");
    }
    public int addImage(Image image){
        img.persist(image);
        img.flush();
        return image.getId();
    }
    
    public List<Image> getImage() {
        return images;
    }
    
    public Image imageExistImage(String id) {
        try{
            Query query = img.createQuery("SELECT s FROM Image s WHERE s.id =:id ").setParameter("id", id);
            
            List<Image> images = (List<Image>) query.getResultList();
            if(images.isEmpty())
            {
                return null;
            }
            return images.get(0);
        }
        catch(Exception ex){
            return null;
        }

    }
    
    public void deleteImages(Image account){
        img.remove(img.merge(account));
    }
    
    public boolean imagesExistBoolean(String id) {
        try{
            Query query = img.createQuery("SELECT s FROM Image s WHERE s.id =:id ").setParameter("id", id);
            
            List<Image> accounts = (List<Image>) query.getResultList();
            if(images.isEmpty())
            {
                return false;
            }
            return true;
        }
        catch(Exception ex){
            setError(ex.getMessage());
            return true;
        }
    }
}
