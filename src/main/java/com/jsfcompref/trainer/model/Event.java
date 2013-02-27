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
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", completionDate=" + completionDate +
                ", skill='" + skill + '\'' +
                ", type='" + type + '\'' +
                ", user=" + user.getId() +
                '}';
    }
}


