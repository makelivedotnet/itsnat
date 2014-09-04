package org.itsnat.droid.impl.xmlinflater.attr;

import android.view.View;

import org.itsnat.droid.impl.xmlinflater.OneTimeAttrProcess;
import org.itsnat.droid.impl.xmlinflater.PendingAttrTasks;
import org.itsnat.droid.impl.xmlinflater.classtree.ClassDescViewBased;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescReflecFloat extends AttrDescReflection
{
    protected Float defaultValue;

    public AttrDescReflecFloat(ClassDescViewBased parent, String name, String methodName, Float defaultValue)
    {
        super(parent,name,methodName);
        this.defaultValue = defaultValue;
    }

    public AttrDescReflecFloat(ClassDescViewBased parent, String name, Float defaultValue)
    {
        super(parent,name);
        this.defaultValue = defaultValue;
    }

    protected Class<?> getClassParam()
    {
        return float.class;
    }

    public void setAttribute(View view, String value, OneTimeAttrProcess oneTimeAttrProcess, PendingAttrTasks pending)
    {
        float convValue = getFloat(value, view.getContext());
        callMethod(view, convValue);
    }

    public void removeAttribute(View view)
    {
        // En el caso de defaultValue nulo es que no sabemos qué poner, es el caso por ejemplo de poner a cero el tamaño texto, no tiene sentido, se tendría que extraer el tamaño por defecto del Theme actual, un follón y total será muy raro
        if (defaultValue != null)
            callMethod(view, defaultValue);
    }
}
