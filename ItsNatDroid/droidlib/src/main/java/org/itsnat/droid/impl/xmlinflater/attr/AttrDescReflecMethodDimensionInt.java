package org.itsnat.droid.impl.xmlinflater.attr;

import android.view.View;

import org.itsnat.droid.impl.xmlinflater.OneTimeAttrProcess;
import org.itsnat.droid.impl.xmlinflater.PendingPostInsertChildrenTasks;
import org.itsnat.droid.impl.xmlinflater.classtree.ClassDescViewBased;

/**
 * Aunque la entrada de datos sea una dimensión float con sufijo y todo, el método que define el valor sólo admite un entero
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescReflecMethodDimensionInt extends AttrDescReflecMethod
{
    protected Float defaultValue;

    public AttrDescReflecMethodDimensionInt(ClassDescViewBased parent, String name, String methodName, Float defaultValue)
    {
        super(parent,name,methodName,getClassParam());
        this.defaultValue = defaultValue;
    }

    public AttrDescReflecMethodDimensionInt(ClassDescViewBased parent, String name, Float defaultValue)
    {
        super(parent,name,getClassParam());
        this.defaultValue = defaultValue;
    }

    protected static Class<?> getClassParam()
    {
        return int.class;
    }

    public void setAttribute(View view, String value, OneTimeAttrProcess oneTimeAttrProcess, PendingPostInsertChildrenTasks pending)
    {
        int convValue = getDimensionInt(value, view.getContext());
        callMethod(view, convValue);
    }

    public void removeAttribute(View view)
    {
        // En el caso de defaultValue nulo es que no sabemos qué poner, es el caso por ejemplo de poner a cero el tamaño texto, no tiene sentido, se tendría que extraer el tamaño por defecto del Theme actual, un follón y total será muy raro
        if (defaultValue != null)
            callMethod(view, defaultValue);
    }

}
