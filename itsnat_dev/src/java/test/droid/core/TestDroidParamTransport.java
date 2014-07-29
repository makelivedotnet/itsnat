/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test.droid.core;

import org.itsnat.core.ItsNatDocument;
import org.itsnat.core.event.CustomParamTransport;
import org.itsnat.core.event.ItsNatEvent;
import org.itsnat.core.event.ParamTransport;
import org.w3c.dom.Element;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.events.EventTarget;

/**
 *
 * @author jmarranz
 */
public class TestDroidParamTransport extends TestDroidBase implements EventListener
{
   
    public TestDroidParamTransport(ItsNatDocument itsNatDoc)
    {
        super(itsNatDoc);

        Element testLauncher = getDocument().getElementById("testEventParamTransportId");        
        ParamTransport manufacturerParam = new CustomParamTransport("manufacturer","android.os.Build.MANUFACTURER");
        ParamTransport modelParam = new CustomParamTransport("model","android.os.Build.MODEL");        
        ParamTransport multivalue = new CustomParamTransport("multivalue","new String[]{\"one\",\"2\"}");        
        itsNatDoc.addEventListener((EventTarget)testLauncher,"click",this,false,new ParamTransport[]{ manufacturerParam,modelParam,multivalue });        
    }
    
    public void handleEvent(Event evt)
    {     
        ItsNatEvent evt2 = (ItsNatEvent)evt;
        itsNatDoc.addCodeToSend("itsNatDoc.alert(\"OK " + evt2.getExtraParam("manufacturer") + " " + evt2.getExtraParam("model") + "\");");
        String[] multivalue = (String[])evt2.getExtraParamMultiple("multivalue");        
        itsNatDoc.addCodeToSend("itsNatDoc.alert(\"OK multivalue (expected: one 2): " + multivalue[0] + " " + multivalue[1] + "\");");
    }
    
}