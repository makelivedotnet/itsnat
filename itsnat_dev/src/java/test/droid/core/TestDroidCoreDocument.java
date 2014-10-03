/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package test.droid.core;

import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import org.itsnat.core.ItsNatDocument;
import org.itsnat.core.ItsNatServletRequest;
import org.w3c.dom.Element;

public class TestDroidCoreDocument implements Serializable
{
    protected ItsNatDocument itsNatDoc;
    protected Element elem;


    public TestDroidCoreDocument(ItsNatDocument itsNatDoc,ItsNatServletRequest request)
    {      
        HttpServletRequest httpReq = (HttpServletRequest)request.getServletRequest();
              
        String model = httpReq.getHeader("ItsNat-model");        
        String sdk_int = httpReq.getHeader("ItsNat-sdk-int");        
        String widthPixels = httpReq.getHeader("ItsNat-display-width");
        String heightPixels = httpReq.getHeader("ItsNat-display-height");        
        String density = httpReq.getHeader("ItsNat-display-density");            
        
        this.itsNatDoc = itsNatDoc;

        new TestDroidMiscAutomatic(itsNatDoc);
        new TestDroidEventTimeout(itsNatDoc);    
        new TestDroidServerException(itsNatDoc);
        new TestDroidClientException(itsNatDoc);        
        new TestDroidNativeListeners(itsNatDoc); 
        new TestDroidStyleAttrAndViewInsertion(itsNatDoc);
        new TestDroidCustomViewInsertion(itsNatDoc);
        new TestDroidViewTreeInsertion(itsNatDoc);
        new TestDroidViewTreeRemoving(itsNatDoc);
        new TestDroidScriptUtil(itsNatDoc);
        new TestDroidTouchEvent(itsNatDoc);
        new TestDroidEventPreSendCode(itsNatDoc);
        new TestDroidParamTransport(itsNatDoc);
        new TestDroidEventCapture(itsNatDoc);   
        new TestDroidFragmentInsertionInnerXML(itsNatDoc);
        new TestDroidFragmentInsertionUsingAPI(itsNatDoc);
        new TestDroidContinueListener(itsNatDoc);
        new TestDroidUserListener(itsNatDoc);
        new TestDroidToDOM(itsNatDoc);
        new TestDroidAsyncServerTask(itsNatDoc);
        new TestDroidTimerListener(itsNatDoc);
        new TestDroidCometNotifier(itsNatDoc);
        new TestDroidKeyboardInput(itsNatDoc);
        new TestDroidGlobalDocAndClientListeners(itsNatDoc);
        new TestDroidFireEventInServerWithBrowser(itsNatDoc);
        new TestDroidFireEventInServerNoBrowser(itsNatDoc);
        new TestDroidDisconnectNode(itsNatDoc);
        new TestDroidReferrer(itsNatDoc,request);        
    }

}
