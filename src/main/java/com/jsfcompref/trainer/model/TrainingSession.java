package com.jsfcompref.trainer.model;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@NamedQueries({
        @NamedQuery(name = "trainingSession.getAll"
                , query = "select t from TrainingSession as t"),
        @NamedQuery(name = "trainingSession.getSessionsForUserAndEvent"
                , query = "select t from TrainingSession as t where t.user.id = :theId and t.eventId = :eventId")
        //, query = "select t from TrainingSession as t where t.user.id = :theId and t.eventId = :eventId")
})
@ManagedBean
@RequestScoped
public class TrainingSession implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date workoutDate;

    private String workoutDescription;

    private boolean completed = false;

    private String personalNotes;

    private String trainerNotes;

    private Long eventId;

    @ManyToOne
    @JoinColumn
    private User user;

    public TrainingSession() {
    }

    public TrainingSession(Long eventId, User user, Date workoutdate,
                           String workoutdesc, boolean completed, String personalnotes,
                           String trainingnotes) {
        this.setUser(user);
        this.setEventId(eventId);
        this.setWorkoutDate(workoutdate);
        this.setWorkoutDescription(workoutdesc);
        this.setCompleted(completed);
        this.setPersonalNotes(personalnotes);
        this.setTrainerNotes(trainingnotes);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getWorkoutDate() {
        return workoutDate;
    }

    public void setWorkoutDate(Date workoutDate) {
        this.workoutDate = workoutDate;
    }

    public String getWorkoutDescription() {
        return workoutDescription;
    }

    public void setWorkoutDescription(String workoutDescription) {
        this.workoutDescription = workoutDescription;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getPersonalNotes() {
        return personalNotes;
    }

    public void setPersonalNotes(String personalNotes) {
        this.personalNotes = personalNotes;
    }

    public String getTrainerNotes() {
        return trainerNotes;
    }

    public void setTrainerNotes(String trainerNotes) {
        this.trainerNotes = trainerNotes;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "TrainingSession{" +
                "id=" + id +
                ", workoutDate=" + workoutDate +
                ", workoutDescription='" + workoutDescription + '\'' +
                ", completed=" + completed +
                ", personalNotes='" + personalNotes + '\'' +
                ", trainerNotes='" + trainerNotes + '\'' +
                ", eventId=" + eventId +
                ", user=" + user.getId() +
                '}';
    }
}