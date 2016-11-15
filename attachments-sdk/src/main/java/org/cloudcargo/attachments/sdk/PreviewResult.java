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

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import org.cloudcargo.attachments.sdk.exceptions.BitmapDecoderException;

import java.io.FileNotFoundException;


/**
 * The result of an operation to load the actual {@link Bitmap} data of an {@link AttachmentPreview}.
 *
 * @author Marten Gajda
 */
public interface PreviewResult
{
    /**
     * Returns the {@link AttachmentPreview} that this {@link PreviewResult} belongs to.
     *
     * @return The {@link AttachmentPreview}.
     */
    @NonNull
    AttachmentPreview attachmentPreview();

    /**
     * Returns the actual preview data in form of a {@link Bitmap}.
     *
     * @return A {@link Bitmap} that shows a preview of an {@link Attachment}.
     *
     * @throws FileNotFoundException
     *         if no preview could be found for this attachment.
     * @throws BitmapDecoderException
     *         if the preview could not be decoded.
     */
    @NonNull
    Bitmap previewData() throws FileNotFoundException, BitmapDecoderException;
}
