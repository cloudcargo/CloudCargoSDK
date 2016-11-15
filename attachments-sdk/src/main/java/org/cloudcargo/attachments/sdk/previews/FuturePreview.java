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
import android.support.annotation.WorkerThread;

import org.cloudcargo.attachments.sdk.Attachment;
import org.cloudcargo.attachments.sdk.AttachmentPreview;
import org.cloudcargo.attachments.sdk.PreviewLoaderCallback;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


/**
 * An {@link AttachmentPreview} of which the preview {@link Uri} still needs to be resolved.
 *
 * @author Marten Gajda
 */
public final class FuturePreview implements AttachmentPreview
{
    private final Attachment mAttachment;
    private final Future<Uri> mPreviewUriFuture;


    /**
     * Create a new {@link AttachmentPreview} for the given {@link Attachment} using the {@link Uri} returned by the given {@link Future}.
     *
     * @param attachment
     *         The {@link Attachment}.
     * @param previewUriFuture
     *         A {@link Future} that resolves the {@link Uri} to the preview.
     */
    public FuturePreview(@NonNull Attachment attachment, @NonNull Future<Uri> previewUriFuture)
    {
        mAttachment = attachment;
        mPreviewUriFuture = previewUriFuture;
    }


    @NonNull
    @Override
    public Attachment attachment()
    {
        return mAttachment;
    }


    @WorkerThread
    @NonNull
    @Override
    public Uri previewUri()
    {
        try
        {
            return mPreviewUriFuture.get();
        }
        catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Unexpected interruption", e);
        }
        catch (ExecutionException e)
        {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void load(@NonNull Context context, @NonNull final PreviewLoaderCallback previewLoaderCallback)
    {
        new PreviewLoaderTask(context, previewLoaderCallback).execute(this);
    }

}
