package com.jsfcompref.trainer.model;

import com.jsfcompref.trainer.controller.UserRegistry;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@NamedQueries({
        @NamedQuery(name = "user.getAll"
                , query = "select u from User as u "),
        @NamedQuery(name = "user.getTrainers"
                , query = "select u from User as u where u.trainer = TRUE"),
        @NamedQuery(name = "user.getUsersForTrainerId"
                , query = "select u from User as u where u.personalTrainerId = :theId")
})
@ManagedBean
@RequestScoped
public class User implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    private String firstName;

    private String lastName;

    @Temporal(TemporalType.DATE)
    private Date dob;

    private String sex;

    private String email;

    private String serviceLevel = "medium";

    @Column(nullable = false)
    private String userid;

    private String password;

    private boolean trainer;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<Event> subscribedEvents;

    private Long personalTrainerId;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<TrainingSession> sessions;

    private boolean sessionsInitialized = false;

    public User() {
        init();
    }

    public User(String firstName, String lastName,
                String sex, Date dob, String email, String serviceLevel,
                String userid, String password, boolean isTrainer) {
        this.init();
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setSex(sex);
        this.setDob(dob);
        this.setEmail(email);
        this.setServiceLevel(serviceLevel);
        this.setUserid(userid);
        this.setPassword(password);
        this.setTrainer(isTrainer);
    }

    private void init() {
        subscribedEvents = new HashSet<Event>();
        //sessions = new ArrayList<TrainingSession>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getServiceLevel() {
        return serviceLevel;
    }

    public void setServiceLevel(String serviceLevel) {
        this.serviceLevel = serviceLevel;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isTrainer() {
        return trainer;
    }

    public void setTrainer(boolean trainer) {
        this.trainer = trainer;
    }

    public Set<Event> getSubscribedEvents() {
        return subscribedEvents;
    }

    public void setSubscribedEvents(Set<Event> subscribedEvents) {
        this.subscribedEvents = subscribedEvents;
    }

    public Long getPersonalTrainerId() {

        return personalTrainerId;
    }

    public void setPersonalTrainerId(Long personalTrainerId) {
        this.personalTrainerId = personalTrainerId;
    }

    public User getPersonalTrainer() {
        // By default, you are your own trainer
        User result = this;
        Long trainerId = getPersonalTrainerId();
        if (null != trainerId) {
            result = UserRegistry.getCurrentInstance().userEJB.getUserById(trainerId);
        }
        return result;
    }

    public Set<TrainingSession> getSessions() {
        return sessions;
    }

    public void setSessions(Set<TrainingSession> sessions) {
        this.sessions = sessions;
    }

    public boolean isSessionsInitialized() {
        return sessionsInitialized;
    }

    public void setSessionsInitialized(boolean sessionsInitialized) {
        this.sessionsInitialized = sessionsInitialized;
    }

    public DataModel<Event> getMyEvents() {
        DataModel<Event> events = null; //
        Set<Event> myEvents = getSubscribedEvents();
        //EventRegistry eventRegistry = EventRegistry.getCurrentInstance();

        List<Event> myEventList = new ArrayList<Event>();
        myEventList.addAll(myEvents);

        events = new ListDataModel<Event>(myEventList);

        return events;
    }

    private void populateTrainingSessions() {
        if (null == sessions) {
            sessions = new HashSet<TrainingSession>();
        }

        Set<Event> events = getSubscribedEvents();
        for (Event ev : events) {
            TrainingSession ts1 = new TrainingSession(ev.getId(), this, new java.util.Date(1227817411), "a workout desc", true, "something for now", "something");
            TrainingSession ts2 = new TrainingSession(ev.getId(), this, new java.util.Date(1229459011), "a workout desc", true, "something for now", "something");

            UserRegistry.getCurrentInstance().userEJB.addTrainingSession(ts1);
            UserRegistry.getCurrentInstance().userEJB.addTrainingSession(ts2);

            sessions.add(ts1);
            sessions.add(ts2);
        }

        UserRegistry.getCurrentInstance().updateUser(this);
    }

    public DataModel<TrainingSession> getTrainingSessionsForEvent(Event e) {
        // lazily initialize training sessions
        if (!sessionsInitialized) {
            populateTrainingSessions();
            sessionsInitialized = true;
            UserRegistry.getCurrentInstance().updateUser(this);
        }

        DataModel<TrainingSession> sessionsForEvent = null;
        List<TrainingSession> sessionList = UserRegistry.getCurrentInstance().userEJB.getTrainingSessionsForUserAndEvent(this, e);

        sessionsForEvent = new ListDataModel<TrainingSession>(sessionList);

        return sessionsForEvent;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dob=" + dob +
                ", sex='" + sex + '\'' +
                ", email='" + email + '\'' +
                ", serviceLevel='" + serviceLevel + '\'' +
                ", userid='" + userid + '\'' +
                ", password='" + password + '\'' +
                ", trainer=" + trainer +
                ", subscribedEvents=" + subscribedEvents +
                ", personalTrainerId=" + personalTrainerId +
                ", sessions=" + sessions +
                ", sessionsInitialized=" + sessionsInitialized +
                '}';
    }
}