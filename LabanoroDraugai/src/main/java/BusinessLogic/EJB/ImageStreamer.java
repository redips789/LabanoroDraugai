
package BusinessLogic.EJB;

import DataAccess.EJB.AccountDao;
import DataAccess.JPA.Account;
import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.inject.Named;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Laurute
 */
@ManagedBean
@ApplicationScoped // veikia ir request, hm
public class ImageStreamer {

    @EJB
    private AccountDao service;

    public StreamedContent getOneImage() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();

        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the HTML. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        } else {
            // So, browser is requesting the image. Return a real StreamedContent with the image bytes.
            String imageId = context.getExternalContext().getRequestParameterMap().get("id"); //accountId/StudentId????
            Account account = service.findAccount(imageId); // pagal save keista
           // Account account=service.findAccount(loginBean.getId()); sitas veiktu
            return new DefaultStreamedContent(new ByteArrayInputStream((byte[]) account.getPhotoBlob()));
        }
    }
}