package org.itsnat.itsnatdroidtest.testact.local;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TabHost;

import org.itsnat.droid.InflatedLayout;
import org.itsnat.itsnatdroidtest.R;
import org.itsnat.itsnatdroidtest.testact.TestActivity;
import org.itsnat.itsnatdroidtest.testact.TestActivityTabFragment;
import org.itsnat.itsnatdroidtest.testact.util.CustomScrollView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jmarranz on 16/07/14.
 */
public class TestLayoutLocal1 extends TestLayoutLocalBase
{
    public TestLayoutLocal1(TestActivityTabFragment fragment)
    {
        super(fragment);
    }

    public void test()
    {
        final TestActivity act = fragment.getTestActivity();
        final View compiledRootView = loadCompiledAndBindBackReloadButtons(R.layout.test_local_layout_compiled_1);

        final View buttonReload = compiledRootView.findViewById(R.id.buttonReload);
        buttonReload.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // TEST de carga dinámica de layout guardado localmente
                InputStream input = act.getResources().openRawResource(R.raw.test_local_layout_dynamic_1);
                // XmlResourceParser input = act.getResources().getXml(R.xml.test_local_layout_dynamic_1); devuelve un XMLBlock parser que no funciona igual que un parser normal y falla

                InflatedLayout layout = loadDynamicAndBindBackReloadButtons(input);
                View dynamicRootView = layout.getRootView();

                defineInitalData(act,dynamicRootView);

                TestLocalXMLInflate1.test((CustomScrollView) compiledRootView, (CustomScrollView) dynamicRootView,layout);
            }
        });

        defineInitalData(act,compiledRootView);
    }

    private static void defineInitalData(TestActivity act,View rootView)
    {
        if ("sdk".equals( Build.PRODUCT ) || "sdk_x86".equals( Build.PRODUCT ))
        {
            // La aceleración hardware hace caer el emulador 4.0.3 en estos componentes
            // http://kevsaidwhat.blogspot.com.es/2012/09/intel-atom-emulator-crashes-with.html
            View calendarView = rootView.findViewById(R.id.calendarViewTestId);
            calendarView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            View datePicker = rootView.findViewById(R.id.datePickerTestId);
            datePicker.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        defineGridView(act, rootView);
        defineExpandableListView(act, rootView);
        defineTabHost(act,rootView);
    }

    private static void defineGridView(TestActivity act,View rootView)
    {
        Resources res = act.getResources();
        GridView gridView = (GridView)rootView.findViewById(R.id.gridViewTestId);
        CharSequence[] entries = res.getTextArray(R.array.sports_array);
        ((GridView)gridView).setAdapter(new ArrayAdapter<CharSequence>(act, android.R.layout.simple_list_item_1, entries));
    }

    private static void defineExpandableListView(TestActivity act,View rootView)
    {
        // http://stackoverflow.com/questions/17636735/expandable-listview-in-fragment

        Resources res = act.getResources();
        ExpandableListView listView = (ExpandableListView)rootView.findViewById(R.id.expanListViewTestId);

        final int NUM_GROUPS = 10;

        final String NAME = "NAME";
        final String IS_EVEN = "IS_EVEN";

        List<Map<String, String>> groupData = new ArrayList<Map<String, String>>();
        for (int i = 0; i < NUM_GROUPS; i++) // 10 grupos
        {
            Map<String, String> curGroupMap = new HashMap<String, String>(); // Grupo
            groupData.add(curGroupMap);
            curGroupMap.put(NAME, "Group " + i);
            // Comentamos el segundo texto del item de grupo porque simple_expandable_list_item_1 sólo tiene text1
            //curGroupMap.put(IS_EVEN, (i % 2 == 0) ? "This group is even" : "This group is odd"); // No se muestra porque
        }

        List<List<Map<String, String>>> childData = new ArrayList<List<Map<String, String>>>();
        for (int i = 0; i < NUM_GROUPS; i++) // 10 grupos
        {
            List<Map<String, String>> children = new ArrayList<Map<String, String>>();
            for (int j = 0; j < 2; j++)
            {
                Map<String, String> curChildMap = new HashMap<String, String>();
                children.add(curChildMap);
                curChildMap.put(NAME, "Child " + i + " " + j);
                curChildMap.put(IS_EVEN, (j % 2 == 0) ? "This child is even" : "This child is odd");
            }
            childData.add(children);
        }

        // Set up our adapter
        SimpleExpandableListAdapter mAdapter = new SimpleExpandableListAdapter(
                act,
                groupData,
                android.R.layout.simple_expandable_list_item_1, // https://github.com/android/platform_frameworks_base/blob/master/core/res/res/layout/simple_expandable_list_item_1.xml
                new String[] { NAME }, //new String[] { NAME, IS_EVEN },
                new int[] { android.R.id.text1 }, // new int[] { android.R.id.text1, android.R.id.text2 },
                childData,
                android.R.layout.simple_expandable_list_item_2, // https://github.com/android/platform_frameworks_base/blob/master/core/res/res/layout/simple_expandable_list_item_2.xml
                new String[] { NAME, IS_EVEN },
                new int[] { android.R.id.text1, android.R.id.text2 }
        );

        listView.setAdapter(mAdapter);
    }

    private static void defineTabHost(TestActivity act,View rootView)
    {
        Resources res = act.getResources();
        TabHost tabHost = (TabHost)rootView.findViewById(R.id.tabHostTest);
        tabHost.setup();

        setNewTab(act, tabHost, "tab1", "Tab 1", android.R.drawable.star_on, R.id.tab1);
        setNewTab(act, tabHost, "tab2", "Tab 2", android.R.drawable.star_on, R.id.tab2);
        setNewTab(act, tabHost, "tab3", "Tab 3", android.R.drawable.star_on, R.id.tab3);

        //tabHost.setCurrentTabByTag("tab2"); //-- optional to set a tab programmatically.
    }

    private static void setNewTab(Context context,final TabHost tabHost, String tag, String title, int icon,final int contentID)
    {
        TabHost.TabSpec tabSpec = tabHost.newTabSpec(tag);
        tabSpec.setIndicator(title, context.getResources().getDrawable(icon));
        tabSpec.setContent(contentID);
        tabHost.addTab(tabSpec);
    }
}
