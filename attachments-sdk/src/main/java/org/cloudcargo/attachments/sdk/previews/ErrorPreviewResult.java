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

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import org.cloudcargo.attachments.sdk.AttachmentPreview;
import org.cloudcargo.attachments.sdk.PreviewResult;
import org.cloudcargo.attachments.sdk.exceptions.BitmapDecoderException;

import java.io.FileNotFoundException;


/**
 * A {@link PreviewResult} that throws an Exception instead of returning any data.
 *
 * @author Marten Gajda
 */
final class ErrorPreviewResult implements PreviewResult
{
    private final Exception mError;
    private final AttachmentPreview mPreview;


    public ErrorPreviewResult(@NonNull AttachmentPreview preview, @NonNull Exception error)
    {
        mError = error;
        mPreview = preview;
    }


    @NonNull
    @Override
    public AttachmentPreview attachmentPreview()
    {
        return mPreview;
    }


    @NonNull
    @Override
    public Bitmap previewData() throws FileNotFoundException, BitmapDecoderException, IllegalStateException
    {
        try
        {
            throw mError;
        }
        catch (FileNotFoundException | BitmapDecoderException | IllegalStateException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            throw new RuntimeException("Unsupported Exception type passed.", e);
        }
    }
}
