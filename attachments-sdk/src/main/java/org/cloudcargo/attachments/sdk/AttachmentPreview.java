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

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;


/**
 * The preview of an attachment.
 *
 * @author Marten Gajda
 */
public interface AttachmentPreview
{
    /**
     * Returns the content {@link Uri} to the preview data. To get the actual preview image data use {@link #load(Context, PreviewLoaderCallback)}.
     * <p>
     * Don't call this from the main thread. If the {@link Uri} needs to be resolved first this will block.
     *
     * @return A {@link Uri} suitable to retrieve a bitmap of the preview.
     */
    @NonNull
    @WorkerThread
    Uri previewUri();

    /**
     * Returns the attachment this preview is for.
     *
     * @return The {@link Attachment} this preview shows.
     */
    @NonNull
    Attachment attachment();

    /**
     * Loads a {@link Bitmap} of the preview if possible.
     *
     * @param context
     *         A {@link Context}.
     * @param previewLoaderCallback
     *         A {@link PreviewLoaderCallback} to deliver the result to.
     */
    void load(@NonNull Context context, @NonNull PreviewLoaderCallback previewLoaderCallback);
}
