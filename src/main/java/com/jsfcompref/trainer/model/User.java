package com.jsfcompref.trainer.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "user.getAll", query = "select u from User as u"),
        @NamedQuery(name = "user.getTrainers", query = "select u from User as u where u.trainer = TRUE"),
        @NamedQuery(name = "user.getUsersForTrainerId", query = "select u from User as u where u.personalTrainerId = :theId")
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToMany(mappedBy = "user")
    private List<Event> subscribedEvents;

    private Long personalTrainerId;

    @OneToMany(mappedBy = "user")
    private List<TrainingSession> sessions;

    private boolean sessionsInitialized = false;

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

    public List<Event> getSubscribedEvents() {
        return subscribedEvents;
    }

    public void setSubscribedEvents(List<Event> subscribedEvents) {
        this.subscribedEvents = subscribedEvents;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (sessionsInitialized != user.sessionsInitialized) return false;
        if (trainer != user.trainer) return false;
        if (dob != null ? !dob.equals(user.dob) : user.dob != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
        if (!id.equals(user.id)) return false;
        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) return false;
        if (!password.equals(user.password)) return false;
        if (personalTrainerId != null ? !personalTrainerId.equals(user.personalTrainerId) : user.personalTrainerId != null)
            return false;
        if (serviceLevel != null ? !serviceLevel.equals(user.serviceLevel) : user.serviceLevel != null) return false;
        if (sessions != null ? !sessions.equals(user.sessions) : user.sessions != null) return false;
        if (sex != null ? !sex.equals(user.sex) : user.sex != null) return false;
        if (subscribedEvents != null ? !subscribedEvents.equals(user.subscribedEvents) : user.subscribedEvents != null)
            return false;
        if (!userid.equals(user.userid)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (dob != null ? dob.hashCode() : 0);
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (serviceLevel != null ? serviceLevel.hashCode() : 0);
        result = 31 * result + userid.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + (trainer ? 1 : 0);
        result = 31 * result + (subscribedEvents != null ? subscribedEvents.hashCode() : 0);
        result = 31 * result + (personalTrainerId != null ? personalTrainerId.hashCode() : 0);
        result = 31 * result + (sessions != null ? sessions.hashCode() : 0);
        result = 31 * result + (sessionsInitialized ? 1 : 0);
        return result;
    }

    public Long getPersonalTrainerId() {

        return personalTrainerId;
    }

    public void setPersonalTrainerId(Long personalTrainerId) {
        this.personalTrainerId = personalTrainerId;
    }

    public List<TrainingSession> getSessions() {
        return sessions;
    }

    public void setSessions(List<TrainingSession> sessions) {
        this.sessions = sessions;
    }

    public boolean isSessionsInitialized() {
        return sessionsInitialized;
    }

    public void setSessionsInitialized(boolean sessionsInitialized) {
        this.sessionsInitialized = sessionsInitialized;
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