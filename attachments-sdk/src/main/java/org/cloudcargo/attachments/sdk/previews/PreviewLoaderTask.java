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
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import org.cloudcargo.attachments.sdk.AttachmentPreview;
import org.cloudcargo.attachments.sdk.PreviewLoaderCallback;
import org.cloudcargo.attachments.sdk.PreviewResult;
import org.cloudcargo.attachments.sdk.exceptions.BitmapDecoderException;

import java.io.FileNotFoundException;
import java.lang.ref.WeakReference;


/**
 * Background task to load a preview from the given content {@link Uri}.
 *
 * @author Marten Gajda
 */
final class PreviewLoaderTask extends AsyncTask<AttachmentPreview, Void, PreviewResult>
{
    private final WeakReference<Context> mContext;
    private final WeakReference<PreviewLoaderCallback> mCallbackRef;


    /**
     * Constructor for {@link PreviewLoaderTask}.
     *
     * @param context
     *         A {@link Context}.
     * @param callback
     *         Handler for result and errors, must not be <code>null</code>.
     */
    public PreviewLoaderTask(@NonNull Context context, @NonNull PreviewLoaderCallback callback)
    {
        mContext = new WeakReference<>(context);
        mCallbackRef = new WeakReference<>(callback);
    }


    @Override
    protected PreviewResult doInBackground(AttachmentPreview... previews)
    {
        AttachmentPreview preview = previews[0];

        Context context = mContext.get();
        if (context == null)
        {
            return new ErrorPreviewResult(preview, new IllegalStateException("Lost context"));
        }

        if (preview.previewUri().equals(Uri.EMPTY))
        {
            return new ErrorPreviewResult(preview, new FileNotFoundException("No preview available"));
        }

        try
        {
            AssetFileDescriptor fileDescriptor = context.getContentResolver().openAssetFileDescriptor(preview.previewUri(), "r");
            if (fileDescriptor == null)
            {
                return new ErrorPreviewResult(preview, new FileNotFoundException("asset file descriptor was null"));
            }

            Bitmap bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor.getFileDescriptor());
            if (bitmap == null)
            {
                return new ErrorPreviewResult(preview, new BitmapDecoderException("Could not decode FileDescriptor to Bitmap."));
            }

            return new BitMapPreviewResult(preview, bitmap);
        }
        catch (FileNotFoundException e)
        {
            return new ErrorPreviewResult(preview, e);
        }
    }


    @Override
    protected void onPostExecute(PreviewResult previewResult)
    {
        PreviewLoaderCallback callback = mCallbackRef.get();
        if (callback == null)
        {
            // our callback no longer exists
            return;
        }

        callback.onPreview(previewResult);
    }
}