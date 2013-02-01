package com.jsfcompref.trainer.backing;

import com.jsfcompref.trainer.controller.UserRegistry;
import com.jsfcompref.trainer.model.User;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import java.util.Collection;

@ManagedBean
public class EditUserBacking extends AbstractBacking {

    public Collection<User> getAvailableTrainers() {
        Collection<User> result = null;
        UserRegistry userRegistry = UserRegistry.getCurrentInstance();
        result = userRegistry.userEJB.getTrainerList();

        return result;
    }

    public void updateUser() {
        User newUser = (User) getSessionMap().get("currentUser");
        try {
            UserRegistry.getCurrentInstance().updateUser(newUser);
        } catch (Exception ex) {
            getFacesContext().addMessage(null,
                    new FacesMessage("Error when adding user" +
                            ((null != newUser) ? " " + newUser.toString() : "") + "."));
        }
    }


}
