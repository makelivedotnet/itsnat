/*
  ItsNat Java Web Application Framework
  Copyright (C) 2007-2014 Jose Maria Arranz Santamaria, Spanish citizen

  This software is free software; you can redistribute it and/or modify it
  under the terms of the GNU Lesser General Public License as
  published by the Free Software Foundation; either version 3 of
  the License, or (at your option) any later version.
  This software is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
  Lesser General Public License for more details. You should have received
  a copy of the GNU Lesser General Public License along with this program.
  If not, see <http://www.gnu.org/licenses/>.
*/

package org.itsnat.impl.core.scriptren.shared.listener;

import org.itsnat.impl.core.clientdoc.ClientDocumentStfulDelegateImpl;
import org.itsnat.impl.core.dompath.NodeLocationImpl;
import org.itsnat.impl.core.listener.dom.domext.ItsNatAsyncTaskEventListenerWrapperImpl;
import org.w3c.dom.Node;
import org.w3c.dom.events.EventTarget;

/**
 *
 * @author jmarranz
 */
public class JSAndBSRenderItsNatAsyncTaskEventListenerImpl
{
    public static String addItsNatAsyncTaskEventListenerCode(ItsNatAsyncTaskEventListenerWrapperImpl itsNatListener,ClientDocumentStfulDelegateImpl clientDoc,RenderItsNatAsyncTaskEventListener render)
    {
        EventTarget currentTarget = itsNatListener.getCurrentTarget();

        String listenerId = itsNatListener.getId();
        int sync = itsNatListener.getCommModeDeclared();
        long eventTimeout = itsNatListener.getEventTimeout();

        StringBuilder code = new StringBuilder();

        String functionVarName = render.addCustomFunctionCode(itsNatListener,code,clientDoc);

        NodeLocationImpl nodeLoc = clientDoc.getNodeLocation((Node)currentTarget,true);
        // Hay que tener en cuenta que el currentTarget puede ser nulo
        code.append( "itsNatDoc.sendAsyncTaskEvent(" + nodeLoc.toScriptNodeLocation(false) + ",\"" + listenerId + "\"," + functionVarName + "," + sync + "," + eventTimeout + ");\n" );

        return code.toString();
    }    
}
