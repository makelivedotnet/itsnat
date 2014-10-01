package org.itsnat.droid.impl.xmlinflater.attr;

import android.view.View;

import org.itsnat.droid.impl.xmlinflater.OneTimeAttrProcess;
import org.itsnat.droid.impl.xmlinflater.PendingPostInsertChildrenTasks;
import org.itsnat.droid.impl.xmlinflater.classtree.ClassDescViewBased;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescReflecMethodString extends AttrDescReflecMethod
{
    public AttrDescReflecMethodString(ClassDescViewBased parent, String name, String methodName)
    {
        super(parent,name,methodName,getClassParam());
    }

    public AttrDescReflecMethodString(ClassDescViewBased parent, String name)
    {
        super(parent,name,getClassParam());
    }

    protected static Class<?> getClassParam()
    {
        return String.class;
    }

    public void setAttribute(View view, String value, OneTimeAttrProcess oneTimeAttrProcess, PendingPostInsertChildrenTasks pending)
    {
        String convValue = getString(value, view.getContext());
        callMethod(view, convValue);
    }

    public void removeAttribute(View view)
    {
        callMethod(view, "");
    }

}