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

import org.dmfs.xmlobjects.builder.reflection.Attribute;
import org.dmfs.xmlobjects.builder.reflection.Element;

import java.util.ArrayList;


/**
 * Holds the information about a specific attachment uploader app.
 *
 * @author Marten Gajda <marten@dmfs.org>
 */
public class App
{
    /**
     * The package name of the app.
     */
    @Attribute(name = "packageName")
    String packageName;

    /**
     * The title of the app.
     */
    @Attribute(name = "title")
    String title;

    /**
     * The description of the app.
     */
    @Attribute(name = "description")
    String description;

    /**
     * The resource id of the app icon.
     */
    @Attribute(name = "iconId")
    int iconId;

    /**
     * The stores this app is available in.
     */
    @Element(namespace = Model.NAMESPACE, name = "source")
    ArrayList<Source> sources;
}
