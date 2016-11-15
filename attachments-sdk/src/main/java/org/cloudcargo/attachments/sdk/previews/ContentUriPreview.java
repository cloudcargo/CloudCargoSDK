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

package org.cloudcargo.attachments.sdk.previews;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;

import org.cloudcargo.attachments.sdk.Attachment;
import org.cloudcargo.attachments.sdk.AttachmentPreview;
import org.cloudcargo.attachments.sdk.PreviewLoaderCallback;


/**
 * An {@link AttachmentPreview} to an {@link Attachment} whose preview {@link Uri} has already been resolved.
 *
 * @author Marten Gajda
 */
public final class ContentUriPreview implements AttachmentPreview
{
    private final Attachment mAttachment;
    private final Uri mPreviewUri;


    /**
     * Creates an {@link AttachmentPreview} for the given {@link Attachment} using the given preview {@link Uri}.
     *
     * @param attachment
     *         The {@link Attachment}.
     * @param previewUri
     *         The {@link Uri} to the preview.
     */
    public ContentUriPreview(@NonNull Attachment attachment, @NonNull Uri previewUri)
    {
        mAttachment = attachment;
        mPreviewUri = previewUri;
    }


    @NonNull
    @Override
    public Attachment attachment()
    {
        return mAttachment;
    }


    @NonNull
    @Override
    public Uri previewUri()
    {
        return mPreviewUri;
    }


    @Override
    public void load(@NonNull Context context, @NonNull PreviewLoaderCallback previewLoaderCallback)
    {
        new PreviewLoaderTask(context, previewLoaderCallback).execute(this);
    }

}
