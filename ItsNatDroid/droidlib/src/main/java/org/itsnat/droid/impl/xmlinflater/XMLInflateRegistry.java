package org.itsnat.droid.impl.xmlinflater;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.ItsNatDroidImpl;
import org.itsnat.droid.impl.model.XMLParsedCache;
import org.itsnat.droid.impl.model.drawable.DrawableParsed;
import org.itsnat.droid.impl.model.layout.LayoutParsed;
import org.itsnat.droid.impl.parser.drawable.DrawableParser;
import org.itsnat.droid.impl.parser.layout.LayoutParser;
import org.itsnat.droid.impl.parser.layout.LayoutParserPage;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;
import org.itsnat.droid.impl.xmlinflater.layout.ClassDescViewMgr;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jmarranz on 25/06/14.
 */
public class XMLInflateRegistry
{
    protected ItsNatDroidImpl parent;
    private int sNextGeneratedId = 1; // No usamos AtomicInteger porque no lo usaremos en multihilo
    protected Map<String,Integer> newIdMap = new HashMap<String,Integer>();
    protected ClassDescViewMgr classDescViewMgr = new ClassDescViewMgr(this);
    protected ClassDescDrawableMgr classDescDrawableMgr = new ClassDescDrawableMgr(this);
    protected XMLParsedCache<LayoutParsed> layoutParsedCache = new XMLParsedCache<LayoutParsed>();
    protected XMLParsedCache<DrawableParsed> drawableParsedCache = new XMLParsedCache<DrawableParsed>();

    public XMLInflateRegistry(ItsNatDroidImpl parent)
    {
        this.parent = parent;
    }

    public ClassDescViewMgr getClassDescViewMgr()
    {
        return classDescViewMgr;
    }

    public ClassDescDrawableMgr getClassDescDrawableMgr()
    {
        return classDescDrawableMgr;
    }

    public XMLParsedCache<LayoutParsed> getLayoutParsedCache()
    {
        return layoutParsedCache;
    }

    public XMLParsedCache<DrawableParsed> getDrawableParsedCache()
    {
        return drawableParsedCache;
    }

    public LayoutParsed getLayoutParsedCache(String markup,String itsNatServerVersion)
    {
        // Este método DEBE ser multihilo, layoutParsedCache ya lo es.
        // No pasa nada si por una rarísima casualidad dos Layout idénticos hacen put, quedará el último, ten en cuenta que esto
        // es un caché
        LayoutParsed cachedLayout = layoutParsedCache.get(markup);
        if (cachedLayout != null) return cachedLayout;
        else
        {
            boolean loadingPage = true;
            LayoutParser layoutParser = new LayoutParserPage(itsNatServerVersion, loadingPage);
            LayoutParsed layoutParsed = layoutParser.parse(markup);
            layoutParsedCache.put(markup, layoutParsed);
            return layoutParsed;
        }
    }

    public DrawableParsed getDrawableParsedCache(String markup)
    {
        // Ver notas de getLayoutParsedCache()
        DrawableParsed cachedDrawable = drawableParsedCache.get(markup);
        if (cachedDrawable != null) return cachedDrawable;
        else
        {
            DrawableParsed drawableParsed = DrawableParser.parse(markup);
            drawableParsedCache.put(markup, drawableParsed);
            return drawableParsed;
        }
    }

    public int generateViewId()
    {
        // Inspirado en el código fuente de Android View.generateViewId()
        final int result = sNextGeneratedId;
        // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
        int newValue = result + 1;
        if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
        // No usamos compareAndSet porque no se debe usar en multihilo
        this.sNextGeneratedId = newValue;
        return result;
    }

    public int findIdAddIfNecessary(String name)
    {
        int id = findId(name);
        if (id == 0)
            id = addNewId(name);
        return id;
    }

    public int findId(String name)
    {
        Integer res = newIdMap.get(name);
        if (res == null)
            return 0; // No existe
        return res;
    }

    private int addNewId(String name)
    {
        int newId = generateViewId();
        newIdMap.put(name,newId);
        return newId;
    }

    public int getIdentifierAddIfNecessary(String value, Context ctx)
    {
        // Procesamos aquí los casos de "@+id/...", la razón es que cualquier atributo que referencie un id (más allá
        // de android:id) puede registrar un nuevo atributo lo cual es útil si el android:id como tal está después,
        // después en android:id ya no hace falta que sea "@+id/...".
        // http://stackoverflow.com/questions/11029635/android-radiogroup-checkedbutton-property
        int id = 0;
        if (value.startsWith("@+id/") || value.startsWith("@id/")) // Si fuera el caso de "@+mypackage:id/name" ese caso no lo soportamos, no lo he visto nunca aunque en teoría está sintácticamente permitido
        {
            id = getIdentifier(value, ctx, false); // Tiene prioridad el recurso de Android, pues para qué generar un id nuevo si ya existe o bien ya fue registrado dinámicamente
            if (id <= 0)
            {
                int pos = value.indexOf('/');
                String idName = value.substring(pos + 1);
                if (value.startsWith("@+id/")) id = findIdAddIfNecessary(idName);
                else id = findId(idName);
                if (id <= 0) throw new ItsNatDroidException("Not found resource with id \"" + value + "\"");
            }
        }
        else id = getIdentifier(value, ctx);
        return id;
    }

    public int getIdentifier(String attrValue, Context ctx)
    {
        return getIdentifier(attrValue,ctx,true);
    }

    public int getIdentifier(String value, Context ctx,boolean throwErr)
    {
        if ("0".equals(value) || "-1".equals(value) || "@null".equals(value)) return 0;

        int id;
        char first = value.charAt(0);
        if (first == '?')
        {
            id = getIdentifierTheme(value, ctx);
        }
        else if (first == '@')
        {
            // En este caso es posible que se haya registrado dinámicamente el id via "@+id/..." Tiene prioridad el registro de Android que el de ItsNat, para qué generar un id si ya existe como recurso
            id = getIdentifierResource(value, ctx);
            if (id > 0)
                return id;
            id = getIdentifierDynamicallyAdded(value,ctx);
        }
        else
            throw new ItsNatDroidException("INTERNAL ERROR");

        if (throwErr && id <= 0) throw new ItsNatDroidException("Not found resource with id \"" + value + "\"");
        return id;
    }

    private static int getIdentifierTheme(String value, Context ctx)
    {
        // http://stackoverflow.com/questions/12781501/android-setting-linearlayout-background-programmatically
        // Ej. android:textAppearance="?android:attr/textAppearanceMedium"
        TypedValue outValue = new TypedValue();
        ctx.getTheme().resolveAttribute(getIdentifierResource(value, ctx), outValue, true);
        return outValue.resourceId;
    }

    private static int getIdentifierResource(String value, Context ctx)
    {
        Resources res = ctx.getResources();

        value = value.substring(1); // Quitamos el @ o #
        if (value.startsWith("+id/"))
            value = value.substring(1); // Quitamos el +
        String packageName;
        if (value.indexOf(':') != -1) // Tiene package el value, ej "android:" delegamos en Resources.getIdentifier() que lo resuelva
        {
            packageName = null;
        }
        else
        {
            packageName = ctx.getPackageName(); // El package es necesario como parámetro sólo cuando no está en la string (recursos locales)
        }

        return res.getIdentifier(value, null, packageName);
    }

    public int getIdentifierDynamicallyAdded(String value, Context ctx)
    {
        if (value.indexOf(':') != -1) // Tiene package, ej "@+android:id/", no se encontrará un id registrado como "@+id/..." y los posibles casos con package NO los hemos contemplado
            return 0; // No encontrado

        value = value.substring(1); // Quitamos el @ o #
        int pos = value.indexOf('/');
        String idName = value.substring(pos + 1);

        return findId(idName);
    }
}
