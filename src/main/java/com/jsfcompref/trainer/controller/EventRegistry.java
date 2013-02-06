package com.jsfcompref.trainer.controller;

import com.jsfcompref.trainer.ejb.TrainerEJB;
import com.jsfcompref.trainer.model.Event;
import com.jsfcompref.trainer.model.User;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

@ManagedBean(eager = true)
@SessionScoped
public class EventRegistry implements Serializable {

    @EJB
    public TrainerEJB eventEJB;

    private Event event = new Event();

    public static EventRegistry getCurrentInstance() {
        EventRegistry result = null;
        FacesContext context = FacesContext.getCurrentInstance();

        Map<String, Object> sessionMap = context.getExternalContext().getSessionMap();

        if (null == (result = (EventRegistry) sessionMap.get("eventRegistry"))) {
            result = context.getApplication().evaluateExpressionGet(context, "#{eventRegistry}",
                    EventRegistry.class);
        }
        assert (null != result);

        return result;
    }

    @PostConstruct
    public void perSessionConstructor() {
        List<Event> results = eventEJB.getEventList();
        if (results.isEmpty()) {
            populateEvents();
            results = eventEJB.getEventList();
            assert (!results.isEmpty());
        }
    }

    private void populateEvents() {
        eventEJB.addEvent(new Event("South Valley 10k", new java.util.Date(), "Casual", "Run"));
        eventEJB.addEvent(new Event("San Francisco 1/2 Marathon", new java.util.Date(1163017411), "Casual", "Run"));
        eventEJB.addEvent(new Event("Pleasanton Tri for Fun Triathlon", new java.util.Date(1227817411), "Basic Competitive", "Walk"));
        eventEJB.addEvent(new Event("San Diego Full Marathon", new java.util.Date(1229459011), "Medium Competitive", "Run"));
        eventEJB.addEvent(new Event("Bakersfield Triathlon", new java.util.Date(1167942211), "Medium Competitive", "Multi"));
        eventEJB.addEvent(new Event("Phoenix Extreme Challenge", new java.util.Date(1292185411), "Medium Competitive", "Multi"));
        eventEJB.addEvent(new Event("Portland Trailblazer Half Marathon", new java.util.Date(1368563011), "Basic Competitive", "Walk"));
        eventEJB.addEvent(new Event("Seattle 10k", new java.util.Date(1356121411), "Medium Competitive", "Multi"));
        eventEJB.addEvent(new Event("Reno 1/2 Marathon", new java.util.Date(1356207811), "Medium Competitive", "Multi"));
        eventEJB.addEvent(new Event("Las Vegas Marathon", new java.util.Date(1348777411), "Medium Competitive", "Multi"));
        eventEJB.addEvent(new Event("Multi Weekly Training", new java.util.Date(1317155011), "Basic Competitive", "Multi"));
    }

    public DataModel<Event> getEvents() {
        DataModel<Event> events = new ListDataModel<Event>(eventEJB.getEventList());
        return events;
    }

    public void removeEventFromRegistryAndFromUsers(final Event event) {
        eventEJB.removeEventForId(event.getId());

        UserRegistry userRegistry = UserRegistry.getCurrentInstance();

        List<User> userList = userRegistry.getUserList();

        for (User u : userList) {
            Set<Event> subscribedEvents = u.getSubscribedEvents();
            if (subscribedEvents.contains(event)) {
                subscribedEvents.remove(event);
                try {
                    userRegistry.updateUser(u);
                } catch (Exception ex) {
                    Logger.getLogger(TrainerEJB.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}