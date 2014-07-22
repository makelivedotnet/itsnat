/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.itsnat.impl.core.event.client.droid;

import org.itsnat.core.ItsNatException;
import org.itsnat.core.event.droid.DroidKeyEvent;
import org.itsnat.impl.core.listener.droid.ItsNatDroidEventListenerWrapperImpl;
import org.itsnat.impl.core.req.norm.RequestNormalEventImpl;

/**
 *
 * @author jmarranz
 */
public class ClientItsNatDroidKeyEventImpl extends ClientItsNatDroidInputEventImpl implements DroidKeyEvent
{

    public ClientItsNatDroidKeyEventImpl(ItsNatDroidEventListenerWrapperImpl listenerWrapper, RequestNormalEventImpl request)
    {
        super(listenerWrapper, request);
    }

    public int getKeyCode()
    {
        return getParameterInt("keyCode");
    }

    public void setKeyCode(int keyCode)
    {
       throw new ItsNatException("Not implemented",this);
    }
}