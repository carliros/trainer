package com.jsfcompref.trainer.controller;

import com.jsfcompref.trainer.ejb.TrainerEJB;
import com.jsfcompref.trainer.model.Event;
import com.jsfcompref.trainer.model.TrainingSession;
import com.jsfcompref.trainer.model.User;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@ManagedBean
@RequestScoped
public class UserRegistry {
    @EJB
    public TrainerEJB userEJB;

    public User user = new User();

    public static UserRegistry getCurrentInstance() {
        UserRegistry result = null;
        Map<String, Object> appMap = FacesContext.getCurrentInstance().getExternalContext().getApplicationMap();
        result = (UserRegistry) appMap.get("userRegistry");
        assert (null != result);

        return result;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /*@PostConstruct
    public void perApplicationConstructor() {
        try {
            abstractEntityAccessor.doInTransaction(new AbstractEntityAccessor.PersistenceActionWithoutResult() {

                public void execute(EntityManager em) {
                    Query query = em.createNamedQuery("user.getAll");
                    List<User> results = query.getResultList();
                    if (results.isEmpty()) {
                        populateUsers(em);
                        query = em.createNamedQuery("user.getAll");
                        results = query.getResultList();
                        assert (!results.isEmpty());
                    }
                }
            });
        } catch (EntityAccessorException ex) {
            Logger.getLogger(UserRegistry.class.getName()).log(Level.SEVERE, null, ex);
        }

    }*/

    public List<User> getUserList() {
        return userEJB.getUserList();
    }

    public void updateUser(final User toUpdate) {
        userEJB.updateUser(toUpdate);
    }

    public void removeTrainingSessionForUserAndEvent(final User usr, final Event e, final TrainingSession trainingSession) {
        try {
            //em.remove(em.contains(trainingSession) ? trainingSession : em.merge(trainingSession)); // em.contains(r) ? r : em.merge(r)
            userEJB.removeTrainingSession(trainingSession);
            usr.getSessions().remove(trainingSession);

            userEJB.updateUser(usr);
        } catch (Exception ex) {
            Logger.getLogger(TrainerEJB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}