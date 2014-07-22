package org.itsnat.droid.impl.browser.clientdoc.event;

import android.view.View;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.itsnat.droid.impl.browser.clientdoc.evtlistener.NormalEventListener;

import java.util.List;

/**
 * Created by jmarranz on 7/07/14.
 */
public class NormalEvent extends EventStful
{
    protected long timeStamp;

    public NormalEvent(NormalEventListener listener)
    {
        super(listener);
        this.timeStamp = System.currentTimeMillis();
    }

    public NormalEventListener getNormalEventListener()
    {
        return (NormalEventListener)listener;
    }

    public View getView()
    {
        return getNormalEventListener().getView();
    }

    public List<NameValuePair> genParamURL()
    {
        List<NameValuePair> params = super.genParamURL();
        params.add(new BasicNameValuePair("itsnat_evt_timeStamp","" + timeStamp)); // En vez del problematico Event.timeStamp
        return params;
    }
}

