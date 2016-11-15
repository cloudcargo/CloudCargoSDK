/*
 * Copyright 2016 dmfs GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.cloudcargo.attachments.sdk.ui;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;

import org.cloudcargo.attachments.sdk.R;
import org.dmfs.android.xmlmagic.AndroidParserContext;
import org.dmfs.android.xmlmagic.builder.ReflectionObjectBuilder;
import org.dmfs.xmlobjects.ElementDescriptor;
import org.dmfs.xmlobjects.QualifiedName;
import org.dmfs.xmlobjects.XmlContext;
import org.dmfs.xmlobjects.builder.ListObjectBuilder;
import org.dmfs.xmlobjects.pull.XmlObjectPull;
import org.dmfs.xmlobjects.pull.XmlObjectPullParserException;
import org.dmfs.xmlobjects.pull.XmlPath;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;


/**
 * Holds the information about a specific attachment uploader app.
 *
 * @author Marten Gajda <marten@dmfs.org>
 */
public class Model
{
    final static XmlContext CONTEXT = new XmlContext();

    public final static String NAMESPACE = "http://cloudcargo.org/ns/apps";

    final static ElementDescriptor<App> APP = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "app"), new ReflectionObjectBuilder<>(App.class),
            CONTEXT);

    final static ElementDescriptor<List<App>> APPS = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "apps"), new ListObjectBuilder<>(APP),
            CONTEXT);

    final static ElementDescriptor<Store> STORE = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "store"), new ReflectionObjectBuilder<>(Store.class),
            CONTEXT);

    final static ElementDescriptor<List<Store>> STORES = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "stores"), new ListObjectBuilder<>(STORE),
            CONTEXT);

    final static ElementDescriptor<Source> SOURCE = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "source"),
            new ReflectionObjectBuilder<>(Source.class), CONTEXT);


    /**
     * Load the list of known CloudCargo apps from the resources.
     *
     * @param context
     *         A {@link Context}.
     *
     * @return A {@link List} of {@link Model} instances.
     *
     * @throws XmlPullParserException
     * @throws IOException
     * @throws XmlObjectPullParserException
     */
    public static List<App> loadApps(Context context) throws XmlPullParserException, IOException, XmlObjectPullParserException
    {
        Resources res = context.getResources();

        XmlResourceParser xmlParser = res.getXml(R.xml.cloudcargo_sdk_apps);

        XmlObjectPull pullParser = new XmlObjectPull(xmlParser, new AndroidParserContext(context, null));
        pullParser.setContext(CONTEXT);

        return pullParser.pull(APPS, null, new XmlPath());
    }


    /**
     * Load the list of known stores from the resources.
     *
     * @param context
     *         A {@link Context}.
     *
     * @return A {@link List} of {@link Model} instances.
     *
     * @throws XmlPullParserException
     * @throws IOException
     * @throws XmlObjectPullParserException
     */
    public static List<Store> loadStores(Context context) throws XmlPullParserException, IOException, XmlObjectPullParserException
    {
        Resources res = context.getResources();

        XmlResourceParser xmlParser = res.getXml(R.xml.cloudcargo_sdk_stores);

        XmlObjectPull pullParser = new XmlObjectPull(xmlParser, new AndroidParserContext(context, null));
        pullParser.setContext(CONTEXT);

        return pullParser.pull(STORES, null, new XmlPath());
    }

}
