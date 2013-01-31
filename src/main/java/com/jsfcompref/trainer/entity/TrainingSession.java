package com.jsfcompref.trainer.entity;

import com.jsfcompref.trainer.entity.accessor.EntityAccessorException;
import com.jsfcompref.trainer.entity.accessor.UserRegistry;
import java.io.Serializable;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.*;

@Entity
//@Table(name = "trainingSessions")
@NamedQueries({
    @NamedQuery(name = "trainingSession.getAll", query = "select t from TrainingSession as t"),
    @NamedQuery(name = "trainingSession.getSessionsForUserAndEvent", query = "select t from TrainingSession as t where t.user.id = :theId and t.eventId = :eventId")
})
@ManagedBean
@RequestScoped
public class TrainingSession {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Temporal(TemporalType.DATE)
  //@Column(name = "workout_date")
  Date workoutDate;

  String workoutDescription;

  boolean completed = false;

  String personalNotes;

  String trainerNotes;

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
  
    @Override
    public String toString() {
        return workoutDescription + " " + completed;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TrainingSession other = (TrainingSession) obj;
        if (this.workoutDate != other.workoutDate && (this.workoutDate == null || !this.workoutDate.equals(other.workoutDate))) {
            return false;
        }
        if ((this.workoutDescription == null) ? (other.workoutDescription != null) : !this.workoutDescription.equals(other.workoutDescription)) {
            return false;
        }
        if (this.completed != other.completed) {
            return false;
        }
        if ((this.personalNotes == null) ? (other.personalNotes != null) : !this.personalNotes.equals(other.personalNotes)) {
            return false;
        }
        if ((this.trainerNotes == null) ? (other.trainerNotes != null) : !this.trainerNotes.equals(other.trainerNotes)) {
            return false;
        }
        if (this.getEventId() != other.getEventId() && (this.getEventId() == null || !this.eventId.equals(other.eventId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (this.workoutDate != null ? this.workoutDate.hashCode() : 0);
        hash = 17 * hash + (this.workoutDescription != null ? this.workoutDescription.hashCode() : 0);
        hash = 17 * hash + (this.completed ? 1 : 0);
        hash = 17 * hash + (this.personalNotes != null ? this.personalNotes.hashCode() : 0);
        hash = 17 * hash + (this.trainerNotes != null ? this.trainerNotes.hashCode() : 0);
        hash = 17 * hash + (this.getEventId() != null ? this.getEventId().hashCode() : 0);
        return hash;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setWorkoutDate(Date workoutDate)
  {
    this.workoutDate = workoutDate;
  }

  public Date getWorkoutDate()
  {
    return workoutDate;
  }

  public void setWorkoutDescription(String workoutDescription)
  {
    this.workoutDescription = workoutDescription;
  }

  public String getWorkoutDescription()
  {
    return workoutDescription;
  }

  public void setCompleted(boolean completed)
  {
    this.completed = completed;
  }

  public boolean isCompleted()
  {
    return completed;
  }

  public void setPersonalNotes(String personalNotes)
  {
    this.personalNotes = personalNotes;
  }

  public String getPersonalNotes()
  {
    return personalNotes;
  }

  public void setTrainerNotes(String trainerNotes) {
      this.trainerNotes = trainerNotes;
        try {
            UserRegistry.getCurrentInstance().updateTrainingSession(this);
        } catch (EntityAccessorException ex) {
            Logger.getLogger(TrainingSession.class.getName()).log(Level.SEVERE, null, ex);
        }
  }

  public String getTrainerNotes()
  {
    return trainerNotes;
  }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }
}