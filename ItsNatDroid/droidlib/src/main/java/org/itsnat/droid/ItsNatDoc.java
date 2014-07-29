package org.itsnat.droid;

import android.view.View;

import org.itsnat.droid.event.UserEvent;

/**
 *
 * Created by jmarranz on 12/06/14.
 */
public interface ItsNatDoc
{
    public Page getPage();

    public void alert(Object value);
    public void alert(String title,Object value);
    public void toast(Object value,int duration);
    public void toast(Object value);

    public UserEvent createUserEvent(String name);
    public void dispatchUserEvent(View currTarget,UserEvent evt);
    public void fireUserEvent(View currTarget,String name);
}

