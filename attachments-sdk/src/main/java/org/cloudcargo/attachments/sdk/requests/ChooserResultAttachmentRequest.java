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
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import org.cloudcargo.attachments.sdk.AttachmentRequest;
import org.cloudcargo.attachments.sdk.Chooser;

import java.util.ArrayList;


/**
 * An {@link AttachmentRequest} that's derived from the result of a {@link Chooser}.
 *
 * @author Marten Gajda <marten@dmfs.org>
 */
public final class ChooserResultAttachmentRequest implements AttachmentRequest
{
    private final AttachmentRequest mAttachmentRequest;


    /**
     * Creates an {@link AttachmentRequest} from the result of a {@link Chooser}. The caller must ensure that the response comes from a chooser and had an
     * {@link Activity#RESULT_OK} response status.
     *
     * @param chooserIntentResult
     *         The {@link Intent} containing the chooser result.
     * @param requestCode
     *         The request code to use when starting the intent (not the request code of the chooser response).
     */
    public ChooserResultAttachmentRequest(@NonNull Intent chooserIntentResult, int requestCode)
    {
        this(new BasicAttachmentRequest(chooserIntentResult.getData(), chooserIntentResult.getType(), requestCode));
    }


    /**
     * Creates an {@link AttachmentRequest} with async delivery from the result of a {@link Chooser}. The caller must ensure that the response comes from a
     * chooser and had an {@link Activity#RESULT_OK} response status.
     *
     * @param chooserIntentResult
     *         The {@link Intent} containing the chooser result.
     * @param pendingIntent
     *         The {@link PendingIntent} to deliver the result to.
     */
    public ChooserResultAttachmentRequest(@NonNull Intent chooserIntentResult, PendingIntent pendingIntent)
    {
        this(new AsyncAttachmentRequest(chooserIntentResult.getData(), chooserIntentResult.getType(), pendingIntent));
    }


    private ChooserResultAttachmentRequest(AttachmentRequest delegate)
    {
        mAttachmentRequest = delegate;
    }


    /**
     * Private constructor for unparcelling.
     *
     * @param parcel
     *         A {@link Parcel}.
     */
    private ChooserResultAttachmentRequest(@NonNull Parcel parcel)
    {
        mAttachmentRequest = parcel.readParcelable(getClass().getClassLoader());
    }


    @Override
    public int describeContents()
    {
        return mAttachmentRequest.describeContents();
    }


    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeParcelable(mAttachmentRequest, flags);
    }


    @NonNull
    @Override
    public AttachmentRequest withSharees(@NonNull ArrayList<String> sharees)
    {
        return mAttachmentRequest.withSharees(sharees);
    }


    @NonNull
    @Override
    public AttachmentRequest withAttachmentTargetUri(@NonNull Uri target)
    {
        return mAttachmentRequest.withAttachmentTargetUri(target);
    }


    @NonNull
    @Override
    public AttachmentRequest withAttachmentTargetAccount(@NonNull Account account)
    {
        return mAttachmentRequest.withAttachmentTargetAccount(account);
    }


    @Override
    public void send(@NonNull Activity activity)
    {
        mAttachmentRequest.send(activity);
    }


    public final static Parcelable.Creator<ChooserResultAttachmentRequest> CREATOR = new Parcelable.Creator<ChooserResultAttachmentRequest>()
    {

        @Override
        public ChooserResultAttachmentRequest createFromParcel(Parcel source)
        {
            return new ChooserResultAttachmentRequest(source);
        }


        @Override
        public ChooserResultAttachmentRequest[] newArray(int size)
        {
            return new ChooserResultAttachmentRequest[size];
        }
    };
}
