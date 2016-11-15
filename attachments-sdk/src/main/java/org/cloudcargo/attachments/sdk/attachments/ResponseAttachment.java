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

package org.cloudcargo.attachments.sdk.attachments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

import org.cloudcargo.attachments.sdk.Attachment;
import org.cloudcargo.attachments.sdk.AttachmentContract;
import org.cloudcargo.attachments.sdk.AttachmentPreview;
import org.cloudcargo.attachments.sdk.AttachmentRequest;
import org.cloudcargo.attachments.sdk.previews.ContentUriPreview;
import org.cloudcargo.attachments.sdk.previews.FuturePreview;
import org.cloudcargo.attachments.sdk.previews.FuturePreviewUri;

import java.net.URI;


/**
 * An {@link Attachment} that's derived from the response of an {@link AttachmentRequest}.
 *
 * @author Marten Gajda
 */
public final class ResponseAttachment implements Attachment
{
    private final Intent mResponseIntent;


    /**
     * Creates a new {@link Attachment} from the response of an attachment upload request. Don't pass any other intent in here. You have to make sure that the
     * {@link Intent} actually belongs to the successful response of an attachment request.
     *
     * @param uploadResponseIntent
     *         The {@link Intent} that has been returned by a successful upload request.
     */
    public ResponseAttachment(@NonNull Intent uploadResponseIntent)
    {
        // Intents are not immutable (#BigFail), so we have to copy it to make this instance immutable
        mResponseIntent = new Intent(uploadResponseIntent);
    }


    @NonNull
    @Override
    public URI url()
    {
        return (URI) mResponseIntent.getSerializableExtra(AttachmentContract.AttachmentResponse.EXTRA_ATTACHMENT_URL);
    }


    @NonNull
    @Override
    public AttachmentPreview preview(@NonNull Context context)
    {
        if (mResponseIntent.hasExtra(AttachmentContract.AttachmentResponse.EXTRA_PREVIEW_URI))
        {
            return new ContentUriPreview(this, (Uri) mResponseIntent.getParcelableExtra(AttachmentContract.AttachmentResponse.EXTRA_PREVIEW_URI));
        }
        return new FuturePreview(this, new FuturePreviewUri(context, url()));
    }
}
