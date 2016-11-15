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

package org.cloudcargo.attachments.sdk;

import android.app.Activity;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import org.cloudcargo.attachments.sdk.requests.ChooserResultAttachmentRequest;


/**
 * A chooser for specific content.
 *
 * @author Marten Gajda
 */
public interface Chooser extends Parcelable
{
    /**
     * Launches the chooser using the given title. The result will be delivered to the given {@link Activity}.
     * <p>
     * When receiving the result you can use {@link ChooserResultAttachmentRequest} to create an attachment request from it.
     *
     * @param activity
     *         The activity to deliver the result to.
     * @param title
     *         The title of the chooser.
     */
    void show(@NonNull Activity activity, @NonNull String title);
}
