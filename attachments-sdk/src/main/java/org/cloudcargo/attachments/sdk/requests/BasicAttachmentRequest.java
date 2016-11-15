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
 * A simple {@link AttachmentRequest} implementation. The result of the request will be delivered to {@link Activity#onActivityResult(int, int, Intent)} of the
 * given {@link Activity}.
 *
 * @author Marten Gajda <marten@dmfs.org>
 */
public final class BasicAttachmentRequest implements AttachmentRequest
{
    private final Uri mAttachment;
    private final String mContentType;
    private final Bundle mExtras;
    private final int mRequestCode;


    /**
     * Creates a request for a new attachment with no specific content or content type.
     *
     * @param requestCode
     *         The request code to use when starting the intent.
     */
    public BasicAttachmentRequest(int requestCode)
    {
        this(null, null, Bundle.EMPTY, requestCode);
    }


    /**
     * Creates a request for a new attachment of the given content type. The upload activity is expected to prompt the user for content that matches this
     * content type.
     *
     * @param contentType
     *         The content type of the attachment.
     * @param requestCode
     *         The request code to use when starting the intent.
     */
    public BasicAttachmentRequest(@NonNull String contentType, int requestCode)
    {
        this(null, contentType, Bundle.EMPTY, requestCode);
    }


    /**
     * Creates a request for a new attachment with the content at the given {@link Uri}.
     *
     * @param content
     *         The content of the attachment.
     * @param requestCode
     *         The request code to use when starting the intent.
     */
    public BasicAttachmentRequest(@NonNull Uri content, int requestCode)
    {
        this(content, null, Bundle.EMPTY, requestCode);
    }


    /**
     * Creates a request for a new attachment with the content at the given {@link Uri} and with the given content type. Consider to use just {@link
     * #BasicAttachmentRequest(Uri, int)} if the content type can be resolved automatically.
     *
     * @param content
     *         The content of the attachment.
     * @param contentType
     *         The content type of the attachment.
     * @param requestCode
     *         The request code to use when starting the intent.
     */
    public BasicAttachmentRequest(@NonNull Uri content, @NonNull String contentType, int requestCode)
    {
        this(content, contentType, Bundle.EMPTY, requestCode);
    }


    private BasicAttachmentRequest(@Nullable Uri attachment, @Nullable String contentType, @NonNull Bundle extras, int requestCode)
    {
        mAttachment = attachment;
        mContentType = contentType;
        mExtras = extras;
        mRequestCode = requestCode;
    }


    @NonNull
    @Override
    public AttachmentRequest withSharees(@NonNull ArrayList<String> sharees)
    {
        Bundle extras = (Bundle) mExtras.clone();
        extras.putStringArrayList(AttachmentContract.AttachmentRequest.EXTRA_SHAREES, new ArrayList<>(sharees));
        return new BasicAttachmentRequest(mAttachment, mContentType, extras, mRequestCode);
    }


    @NonNull
    @Override
    public AttachmentRequest withAttachmentTargetUri(@NonNull Uri target)
    {
        Bundle extras = (Bundle) mExtras.clone();
        extras.putParcelable(AttachmentContract.AttachmentRequest.EXTRA_TARGETURI, target);
        return new BasicAttachmentRequest(mAttachment, mContentType, extras, mRequestCode);
    }


    @NonNull
    @Override
    public AttachmentRequest withAttachmentTargetAccount(@NonNull Account account)
    {
        Bundle extras = (Bundle) mExtras.clone();
        extras.putParcelable(AttachmentContract.AttachmentRequest.EXTRA_TARGETACCOUNT, account);
        return new BasicAttachmentRequest(mAttachment, mContentType, extras, mRequestCode);
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
        activity.startActivityForResult(intent, mRequestCode);
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
        dest.writeInt(mRequestCode);
    }


    public final static Parcelable.Creator<BasicAttachmentRequest> CREATOR = new Parcelable.Creator<BasicAttachmentRequest>()
    {

        @Override
        public BasicAttachmentRequest createFromParcel(Parcel source)
        {
            ClassLoader classLoader = getClass().getClassLoader();
            Uri attachment = source.readParcelable(classLoader);
            String contentType = source.readString();
            Bundle extras = source.readParcelable(classLoader);
            int requestCode = source.readInt();
            return new BasicAttachmentRequest(attachment, contentType, extras, requestCode);
        }


        @Override
        public BasicAttachmentRequest[] newArray(int size)
        {
            return new BasicAttachmentRequest[size];
        }
    };
}
