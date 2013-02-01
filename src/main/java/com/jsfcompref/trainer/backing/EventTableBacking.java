package com.jsfcompref.trainer.backing;

import com.jsfcompref.trainer.controller.EventRegistry;
import com.jsfcompref.trainer.controller.UserRegistry;
import com.jsfcompref.trainer.model.Event;

import javax.faces.bean.ManagedBean;
import javax.faces.component.UIData;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@ManagedBean
public class EventTableBacking extends AbstractBacking {

    private UIData events;
    private List<Event> subscribedEvents;

    /*public List<Long> getSubscribedEventIds() {
        if (null == subscribedEventIds) {
            subscribedEventIds = getCurrentUser().getSubscribedEventIds();
        }
        return subscribedEventIds;
    }*/

    public List<Event> getSubscribedEvents() {
        if (null == subscribedEvents) {
            subscribedEvents = getCurrentUser().getSubscribedEvents();
        }
        return subscribedEvents;
    }

    public UIData getEvents() {
        return events;
    }

    public void setEvents(UIData events) {
        this.events = events;
    }

    public boolean isSubscribedToEvent() {
        boolean result = false;
        Event currentEvent = (Event) getEvents().getRowData();
        result = getSubscribedEvents().contains(currentEvent);

        return result;
    }


    public void setSubscribedToEvent(boolean subscribedToEvent) {
        Event currentEvent = (Event) getEvents().getRowData();
        //Long id = currentEvent.getId();
        boolean isCurrentlySubscribed = getSubscribedEvents().contains(currentEvent);
        boolean doPersist = false;
        if (true == subscribedToEvent) {
            if (!isCurrentlySubscribed) {
                getSubscribedEvents().add(currentEvent);
                doPersist = true;
            }
        } else if (isCurrentlySubscribed) {

            getSubscribedEvents().remove(currentEvent);
            doPersist = true;
        }
        if (doPersist) {
            try {
                //TODO make it sure if it have to be static
                UserRegistry.getCurrentInstance().updateUser(getCurrentUser());

            } catch (Exception ex) {
                Logger.getLogger(EventTableBacking.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public boolean isDeleteEvent() {
        boolean result = false;

        return result;
    }


    public void setDeleteEvent(boolean deleteEvent) {
        if (deleteEvent) {
            Event currentEvent = (Event) getEvents().getRowData();
            //Long id = currentEvent.getId();
            EventRegistry.getCurrentInstance().removeEventFromRegistryAndFromUsers(currentEvent);
        }
    }

}
