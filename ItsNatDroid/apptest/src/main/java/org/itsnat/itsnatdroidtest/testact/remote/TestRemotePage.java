package org.itsnat.itsnatdroidtest.testact.remote;

import org.itsnat.droid.ItsNatDroidBrowser;
import org.itsnat.itsnatdroidtest.testact.TestActivityTabFragment;

/**
 * Created by jmarranz on 16/07/14.
 */
public class TestRemotePage extends TestRemotePageBase
{
    public TestRemotePage(final TestActivityTabFragment fragment, final ItsNatDroidBrowser droidBrowser)
    {
        super(fragment,droidBrowser);
    }

    public void test(String url)
    {
        executePageRequest(url);
    }
}
