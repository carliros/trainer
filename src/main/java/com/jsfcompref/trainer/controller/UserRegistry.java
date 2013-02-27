package com.jsfcompref.trainer.controller;

import com.jsfcompref.trainer.ejb.TrainerEJB;
import com.jsfcompref.trainer.model.Event;
import com.jsfcompref.trainer.model.TrainingSession;
import com.jsfcompref.trainer.model.User;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@ManagedBean(eager = true)
@ApplicationScoped
public class UserRegistry implements Serializable {
    @EJB
    public TrainerEJB userEJB;

    public static UserRegistry getCurrentInstance() {
        UserRegistry result = null;
        Map<String, Object> appMap = FacesContext.getCurrentInstance().getExternalContext().getApplicationMap();
        result = (UserRegistry) appMap.get("userRegistry");
        assert (null != result);

        return result;
    }

    @PostConstruct
    public void perApplicationConstructor() {
        List<User> results = userEJB.getUserList();
        if (results.isEmpty()) {
            populateUsers();
            results = userEJB.getUserList();
            assert (!results.isEmpty());
        }
    }

    private void populateUsers() {

        // the trainers
        userEJB.addUser(new User("Jake", "DeJoque", "male", new java.util.Date(), "jake@vtrainer.com", "Premium", "jake", "jake", true));
        userEJB.addUser(new User("Frauke", "Fuenochel", "female", new java.util.Date(), "frauke@vtrainer.com", "Premium", "frauke", "frauke", true));
        userEJB.addUser(new User("Andrew", "Abs", "male", new java.util.Date(), "andrew@vtrainer.com", "Premium", "andrew", "andrew", true));

        // the users
        userEJB.addUser(new User("Joe", "Fitness", "male", new java.util.Date(), "jfitness@vtrainer.com", "Premium", "jfitness", "iamcool", false));
        userEJB.addUser(new User("Scott", "Tiger", "male", new java.util.Date(), "stiger@vtrainer.com", "Medium", "stiger", "welcome", false));
        userEJB.addUser(new User("Karen", "Knees", "female", new java.util.Date(), "karen@vtrainer.com", "Premium", "karen", "karen", false));
        userEJB.addUser(new User("Gina", "Glutes", "female", new java.util.Date(), "gina@vtrainer.com", "Basic", "gina", "gina", false));
    }

    public List<User> getUserList() {
        return userEJB.getUserList();
    }

    public void updateUser(final User toUpdate) {
        userEJB.updateUser(toUpdate);
    }

    public void updateEvent(final Event toUpdate) {
        userEJB.updateEvent(toUpdate);
    }

    public void addTrainingSession(TrainingSession toAdd) {
        userEJB.addTrainingSession(toAdd);
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