package org.itsnat.droid.impl.xmlinflater.classtree;

import org.itsnat.droid.impl.xmlinflater.ClassDescViewMgr;
import org.itsnat.droid.impl.xmlinflater.attr.widget.AttrDesc_widget_Chronometer_format;

/**
 * Created by jmarranz on 30/04/14.
 */
public class ClassDesc_widget_Chronometer extends ClassDescViewBased
{
    public ClassDesc_widget_Chronometer(ClassDescViewMgr classMgr, ClassDescViewBased parentClass)
    {
        super(classMgr,"android.widget.Chronometer",parentClass);
    }

    protected void init()
    {
        super.init();

        addAttrDesc(new AttrDesc_widget_Chronometer_format(this));
    }
}

