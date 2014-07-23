/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test.droid;

import org.itsnat.core.ItsNatDocument;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.events.EventTarget;

/**
 *
 * @author jmarranz
 */
public class TestDroidViewInsertionAndSetAttributes extends TestDroidBase implements EventListener
{
   
    public TestDroidViewInsertionAndSetAttributes(ItsNatDocument itsNatDoc)
    {
        super(itsNatDoc);
        Document doc = itsNatDoc.getDocument();
        Element testStyleAttr = doc.getElementById("testStyleAttrId");
        ((EventTarget)testStyleAttr).addEventListener("click", this, false);
    }
    
    public void handleEvent(Event evt)
    {
        Document doc = itsNatDoc.getDocument();        
        Element testStyleAttrHidden = doc.getElementById("testStyleAttrHiddenId");  
        
        Element textView = doc.createElement("TextView");        
        // Test definir atributos antes de insertar
        textView.setAttributeNS(ANDROID_NS, "text", "OK if text has left/right padding, background pink and width is match_parent");         
        textView.setAttribute("style","@style/test");
        
        testStyleAttrHidden.getParentNode().insertBefore(textView, testStyleAttrHidden);
        
        // Test definir atributos despu�s de insertar            
        textView.setAttributeNS(ANDROID_NS, "layout_width", "match_parent");        
        textView.setAttributeNS(ANDROID_NS, "layout_height", "wrap_content");        
        textView.setAttributeNS(ANDROID_NS, "background", "#ffdddd");         
        textView.removeAttributeNS(ANDROID_NS, "background");   
        String nodeRef = itsNatDoc.getScriptUtil().getNodeReference(textView);
        itsNatDoc.addCodeToSend("if (" + nodeRef + ".getBackground() != null) itsNatDoc.alert(\"FAIL removeAttributeNS\");");
        textView.setAttributeNS(ANDROID_NS, "background", "#ffdddd");  // Rosa   
        
        // Test uso del atributo DOM id
        textView.setAttribute("id", "BAD_ID");  
        textView.removeAttribute("id");         
        itsNatDoc.addCodeToSend("if (itsNatDoc.getPage().findViewByXMLId(\"BAD_ID\") != null) itsNatDoc.alert(\"FAIL removeAttribute XML Id\");");        
        
        textView.setAttribute("id", "testStyleAttrTextId");
        itsNatDoc.addCodeToSend("if (" + nodeRef + "!= itsNatDoc.getPage().findViewByXMLId(\"testStyleAttrTextId\")) itsNatDoc.alert(\"FAIL setAttribute XML Id\");");        
        
    }
    
}
