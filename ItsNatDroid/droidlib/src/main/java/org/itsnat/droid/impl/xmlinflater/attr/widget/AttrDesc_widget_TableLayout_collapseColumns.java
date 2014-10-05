package org.itsnat.droid.impl.xmlinflater.attr.widget;

import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;

import org.itsnat.droid.impl.xmlinflater.OneTimeAttrProcess;
import org.itsnat.droid.impl.xmlinflater.PendingPostInsertChildrenTasks;
import org.itsnat.droid.impl.xmlinflater.attr.AttrDesc;
import org.itsnat.droid.impl.xmlinflater.classtree.ClassDescViewBased;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDesc_widget_TableLayout_collapseColumns extends AttrDesc
{
    public AttrDesc_widget_TableLayout_collapseColumns(ClassDescViewBased parent)
    {
        super(parent,"collapseColumns");
    }

    public void setAttribute(final View view,final String value, OneTimeAttrProcess oneTimeAttrProcess, PendingPostInsertChildrenTasks pending)
    {
        final TableLayout tableView = (TableLayout)view;

        if (oneTimeAttrProcess == null) // Si es no nulo es que estamos creando el TableLayout y no hace falta ésto
        {
            int maxColumns = getMaxColumns((TableLayout) view);
            for (int i = 0; i < maxColumns; i++)
            {
                if (tableView.isColumnCollapsed(i))
                    tableView.setColumnCollapsed(i, false);
            }
        }

        Runnable task = new Runnable()
        {
            @Override
            public void run()
            {
                if ("".equals(value))
                {
                    int maxColumns = getMaxColumns((TableLayout) view);
                    for (int i = 0; i < maxColumns; i++)
                    {
                        if (tableView.isColumnCollapsed(i))
                            tableView.setColumnCollapsed(i, false);
                    }
                }
                else
                {
                    String[] columns = value.split(",");
                    for (int i = 0; i < columns.length; i++)
                    {
                        String columnStr = columns[i];
                        int column = Integer.parseInt(columnStr);
                        tableView.setColumnCollapsed(column, true);
                    }
                }
            }
        };
        if (pending != null)
            pending.addTask(task);
        else
            task.run();

    }

    public void removeAttribute(View view)
    {
        setAttribute(view,"",null,null);
    }

    private static int getMaxColumns(TableLayout view)
    {
        int maxColumns = 0;
        int childCount = view.getChildCount();
        for(int i = 0; i < childCount; i++)
        {
            View child = view.getChildAt(i);
            if (child instanceof TableRow)
            {
                int columns = ((TableRow)child).getChildCount();
                if (columns > maxColumns) maxColumns = columns;
            }
        }
        return maxColumns;
    }

}
