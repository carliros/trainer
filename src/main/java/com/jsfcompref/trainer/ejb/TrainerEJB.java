package com.jsfcompref.trainer.ejb;

import com.jsfcompref.trainer.model.Event;
import com.jsfcompref.trainer.model.TrainingSession;
import com.jsfcompref.trainer.model.User;

import javax.ejb.Stateless;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class TrainerEJB {
    @PersistenceContext(unitName = "trainerPU")
    private EntityManager em;

    public Event getEventForId(final Long id) {
        Event result = null;
        try {
            result = em.find(Event.class, id);
        } catch (Exception ex) {
            Logger.getLogger(TrainerEJB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    public List<Event> getEventList() {
        List<Event> result = Collections.emptyList();
        try {
            Query query = em.createNamedQuery("event.getAll");
            result = query.getResultList();
        } catch (Exception ex) {
            Logger.getLogger(TrainerEJB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    public Event removeEventForId(final Long id) {
        Event result = null;
        try {
            result = em.find(Event.class, id);
            if (null != result) {
                em.remove(result);
            }
        } catch (Exception ex) {
            Logger.getLogger(TrainerEJB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public void addEvent(final Event toAdd) {
        em.persist(toAdd);
    }

    public void updateEvent(final Event toAdd) {
        em.merge(toAdd);
    }

    /*private void init() {
        user.setSubscribedEvents(new ArrayList<Event>());
    }*/

    /*private void populateTrainingSessions() {
        if (null == user.getSessions()) {
            user.setSessions(new ArrayList<TrainingSession>());
        }

        //EventRegistry eventReg = EventRegistry.getCurrentInstance();

        List<Event> events = user.getSubscribedEvents();
        for (Event ev : events) {
            TrainingSession ts = new TrainingSession();
            ts.setEvent(ev);
            ts.setWorkoutDate(new Date(1227817411));
            ts.setWorkoutDescription("a workout desc");
            ts.setUser(user);
            ts.setCompleted(true);
            ts.setPersonalNotes("something for now");
            ts.setTrainerNotes("something");

            user.getSessions().add(ts);

            //user.getSessions().add(new TrainingSession(ev.getId(), this, new Date(1229459011), "a workout desc", true, "something for now", "something"));
        }
        try {
            //UserRegistry userReg = UserRegistry.getCurrentInstance();
            // necessary? userReg.addTrainingSessions(sessions);
            updateUser(user);
        } catch (Exception ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/

    public void updateUser(final User toUpdate) {
        em.merge(toUpdate);
    }

    public User getUserByUserId(final String userId) {
        User result = null;

        // PENDING do a query to get this information, don't iterate over the list
        List<User> users = getUserList();
        for (User user : users) {
            if (user.getUserid().equals(userId)) {
                result = user;
                break;
            }
        }

        return result;
    }

    public List<User> getUserList() {
        List<User> result = Collections.emptyList();
        try {
            Query query = em.createNamedQuery("user.getAll");
            result = query.getResultList();
        } catch (Exception ex) {
            Logger.getLogger(TrainerEJB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    public DataModel<User> getUsers() {
        DataModel<User> users = new ListDataModel<User>(getUserList());
        return users;
    }

    public User getUserById(final Long id) {
        User result = null;
        try {
            result = em.find(User.class, id);
        } catch (Exception ex) {
            Logger.getLogger(TrainerEJB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public List<User> getTrainerList() {
        List<User> result = Collections.emptyList();
        try {
            Query query = em.createNamedQuery("user.getTrainers");
            result = query.getResultList();
        } catch (Exception ex) {
            Logger.getLogger(TrainerEJB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    public List<User> getTraineesForTrainer(final User trainer) {
        List<User> result = Collections.emptyList();
        try {
            Query query = em.createNamedQuery("user.getUsersForTrainerId");
            query.setParameter("theId", trainer.getId());
            result = query.getResultList();
        } catch (Exception ex) {
            Logger.getLogger(TrainerEJB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    public void addUser(final User toAdd) {
        em.persist(toAdd);
    }

    public void removeTrainingSession(final TrainingSession toRemove) {
        em.remove(em.contains(toRemove) ? toRemove : em.merge(toRemove));
    }

    public List<TrainingSession> getTrainingSessionsForUserAndEvent(final User user, final Event e) {
        List<TrainingSession> result = null;
        try {
            Query query = em.createNamedQuery("trainingSession.getSessionsForUserAndEvent");
            query.setParameter("theId", user.getId());
            query.setParameter("eventId", e.getId());
            result = query.getResultList();
        } catch (Exception ex) {
            Logger.getLogger(TrainerEJB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    public void addTrainingSessions(final List<TrainingSession> toAdd) {
        for (TrainingSession t : toAdd) {
            em.persist(t);
        }
    }

    public void updateTrainingSession(final TrainingSession toUpdate) {
        em.merge(toUpdate);
    }

    public void updateTrainingSessionForUser(final User user, final TrainingSession trainingSession) {
        try {
            em.merge(trainingSession);
            em.merge(user);
        } catch (Exception ex) {
            Logger.getLogger(TrainerEJB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
