package org.itsnat.droid.impl.browser.clientdoc.event;

import org.apache.http.NameValuePair;
import org.itsnat.droid.Event;
import org.itsnat.droid.impl.browser.clientdoc.evtlistener.EventGenericListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jmarranz on 7/07/14.
 */
public abstract class EventGeneric implements Event
{
    protected EventGenericListener listener;
    protected boolean mustBeSent = true;
    protected boolean ignoreHold = false;
    protected Map<String,Object> extraParams;

    public EventGeneric(EventGenericListener listener)
    {
        this.listener = listener;
    }

    public void setMustBeSent(boolean value) { this.mustBeSent = value; }
    public void sendEvent()
    {
        if (this.mustBeSent) listener.getItsNatDocImpl().getEventManager().sendEvent(this);
    }

    public boolean isIgnoreHold()
    {
        return ignoreHold;
    }

    public Object getNativeEvent()
    {
        return null; // Se redefine
    }

    public String getType()
    {
        return null; // Se redefine
    }

    public void saveEvent()
    {
        // Se redefine en derivada
    }

    public EventGenericListener getEventGenericListener()
    {
        return listener;
    }

    public Map<String,Object> getExtraParams()
    {
        return extraParams; // Puede ser null
    }

    public Object getExtraParam(String name)
    {
        if (extraParams == null) return null;
        return extraParams.get(name);
    }

    public void setExtraParam(String name,Object value)
    {
        if (extraParams == null) this.extraParams = new HashMap<String,Object>();
        extraParams.put(name,value);
    }

    public List<NameValuePair> genParamURL()
    {
        List<NameValuePair> params = listener.getItsNatDocImpl().genParamURL();
        listener.genParamURL(this,params);
        return params;
    }


}