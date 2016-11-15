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

package org.cloudcargo.attachments.sdk.requests;

import android.accounts.Account;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.cloudcargo.attachments.sdk.AttachmentContract;
import org.cloudcargo.attachments.sdk.AttachmentRequest;

import java.util.ArrayList;


/**
 * An {@link AttachmentRequest} implementation with async result delivery.
 *
 * @author Marten Gajda <marten@dmfs.org>
 */
/*
 * TODO: this shares a lot of code with BasicAttachmentRequest. Can we consolidate this somehow?
 */
public final class AsyncAttachmentRequest implements AttachmentRequest
{
    private final Uri mAttachment;
    private final String mContentType;
    private final Bundle mExtras;
    private final PendingIntent mPendingIntent;


    /**
     * Creates a request for a new attachment with no specific content or content type.
     *
     * @param pendingIntent
     *         The {@link PendingIntent} to deliver the result to.
     */
    public AsyncAttachmentRequest(PendingIntent pendingIntent)
    {
        this(null, null, Bundle.EMPTY, pendingIntent);
    }


    /**
     * Creates a request for a new attachment of the given content type. The upload activity is expected to prompt the user for content that matches this
     * content type.
     *
     * @param contentType
     *         The content type of the attachment.
     * @param pendingIntent
     *         The {@link PendingIntent} to deliver the result to.
     */
    public AsyncAttachmentRequest(@NonNull String contentType, PendingIntent pendingIntent)
    {
        this(null, contentType, Bundle.EMPTY, pendingIntent);
    }


    /**
     * Creates a request for a new attachment with the content at the given {@link Uri}.
     *
     * @param content
     *         The content of the attachment.
     * @param pendingIntent
     *         The {@link PendingIntent} to deliver the result to.
     */
    public AsyncAttachmentRequest(@NonNull Uri content, PendingIntent pendingIntent)
    {
        this(content, null, Bundle.EMPTY, pendingIntent);
    }


    /**
     * Creates a request for a new attachment with the content at the given {@link Uri} and with the given content type. Consider to use just {@link
     * #AsyncAttachmentRequest(Uri, PendingIntent)} if the content type can be resolved automatically.
     *
     * @param content
     *         The content of the attachment.
     * @param contentType
     *         The content type of the attachment.
     * @param pendingIntent
     *         The {@link PendingIntent} to deliver the result to.
     */
    public AsyncAttachmentRequest(@NonNull Uri content, @NonNull String contentType, PendingIntent pendingIntent)
    {
        this(content, contentType, Bundle.EMPTY, pendingIntent);
    }


    private AsyncAttachmentRequest(@Nullable Uri attachment, @Nullable String contentType, @NonNull Bundle extras, PendingIntent pendingIntent)
    {
        mAttachment = attachment;
        mContentType = contentType;
        mExtras = extras;
        mPendingIntent = pendingIntent;
    }


    @NonNull
    @Override
    public AttachmentRequest withSharees(@NonNull ArrayList<String> sharees)
    {
        Bundle extras = (Bundle) mExtras.clone();
        extras.putStringArrayList(AttachmentContract.AttachmentRequest.EXTRA_SHAREES, new ArrayList<>(sharees));
        return new AsyncAttachmentRequest(mAttachment, mContentType, extras, mPendingIntent);
    }


    @NonNull
    @Override
    public AttachmentRequest withAttachmentTargetUri(@NonNull Uri target)
    {
        Bundle extras = (Bundle) mExtras.clone();
        extras.putParcelable(AttachmentContract.AttachmentRequest.EXTRA_TARGETURI, target);
        return new AsyncAttachmentRequest(mAttachment, mContentType, extras, mPendingIntent);
    }


    @NonNull
    @Override
    public AttachmentRequest withAttachmentTargetAccount(@NonNull Account account)
    {
        Bundle extras = (Bundle) mExtras.clone();
        extras.putParcelable(AttachmentContract.AttachmentRequest.EXTRA_TARGETACCOUNT, account);
        return new AsyncAttachmentRequest(mAttachment, mContentType, extras, mPendingIntent);
    }


    @Override
    public void send(@NonNull Activity activity)
    {
        Intent intent = new Intent(AttachmentContract.AttachmentRequest.ACTION_UPLOAD_ATTACHMENT);
        if (mAttachment != null && mContentType != null)
        {
            intent.setDataAndType(mAttachment, mContentType);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        else if (mAttachment != null)
        {
            intent.setData(mAttachment);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        else if (mContentType != null)
        {
            intent.setType(mContentType);
        }
        intent.putExtras(mExtras);
        intent.putExtra(AttachmentContract.AttachmentRequest.EXTRA_RESULT_PENDING_INTENT, mPendingIntent);
        activity.startActivity(intent);
    }


    @Override
    public int describeContents()
    {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeParcelable(mAttachment, flags);
        dest.writeString(mContentType);
        dest.writeParcelable(mExtras, flags);
        dest.writeParcelable(mPendingIntent, flags);
    }


    public final static Parcelable.Creator<AsyncAttachmentRequest> CREATOR = new Parcelable.Creator<AsyncAttachmentRequest>()
    {

        @Override
        public AsyncAttachmentRequest createFromParcel(Parcel source)
        {
            ClassLoader classLoader = getClass().getClassLoader();
            Uri attachment = source.readParcelable(classLoader);
            String contentType = source.readString();
            Bundle extras = source.readParcelable(classLoader);
            PendingIntent pendingIntent = source.readParcelable(classLoader);
            return new AsyncAttachmentRequest(attachment, contentType, extras, pendingIntent);
        }


        @Override
        public AsyncAttachmentRequest[] newArray(int size)
        {
            return new AsyncAttachmentRequest[size];
        }
    };
}
