package com.jsfcompref.trainer.model;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@NamedQueries({@NamedQuery(name = "event.getAll"
        , query = "select e from Event as e")})
@ManagedBean
@RequestScoped
public class Event implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date completionDate;

    private String skill;

    private String type;

    @ManyToOne
    @JoinColumn
    private User user;

    public Event() {
    }

    public Event(String name, Date completionDate, String skill, String type) {
        this.setName(name);
        this.setCompletionDate(completionDate);
        this.setSkill(skill);
        this.setType(type);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Event)) return false;

        Event event = (Event) o;

        if (completionDate != null ? !completionDate.equals(event.completionDate) : event.completionDate != null)
            return false;
        if (!id.equals(event.id)) return false;
        if (!name.equals(event.name)) return false;
        if (skill != null ? !skill.equals(event.skill) : event.skill != null) return false;
        if (type != null ? !type.equals(event.type) : event.type != null) return false;
        if (user != null ? !user.equals(event.user) : event.user != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + (completionDate != null ? completionDate.hashCode() : 0);
        result = 31 * result + (skill != null ? skill.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", completionDate=" + completionDate +
                ", skill='" + skill + '\'' +
                ", type='" + type + '\'' +
                ", user=" + user +
                '}';
    }
}


