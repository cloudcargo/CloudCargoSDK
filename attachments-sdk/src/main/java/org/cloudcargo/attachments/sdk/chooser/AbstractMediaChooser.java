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

package org.cloudcargo.attachments.sdk.chooser;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import org.cloudcargo.attachments.sdk.Chooser;


/**
 * An abstract {@link Chooser} for content that can be opened.
 *
 * @author Marten Gajda <marten@dmfs.org>
 */
public abstract class AbstractMediaChooser implements Chooser
{
    private final int mRequestCode;
    private final String mContentType;
    private final Intent[] mCaptureIntents;


    /**
     * Creates a MediaChooser instance.
     * <p>
     * Note: this constructor is protected to prevent instantiation of anonymous classes. An anonymous instance would break the {@link Parcelable} contract.
     *
     * @param requestCode
     *         The request code to use when starting the {@link Intent}.
     * @param contentType
     *         The expected content type of the selected file.
     * @param captureIntents
     *         Any additional {@link Intent}s that capture the expected content.
     */
    protected AbstractMediaChooser(int requestCode, @NonNull String contentType, @NonNull Intent... captureIntents)
    {
        mRequestCode = requestCode;
        mContentType = contentType;
        mCaptureIntents = captureIntents.clone();
    }


    protected AbstractMediaChooser(@NonNull Parcel parcel)
    {
        mRequestCode = parcel.readInt();
        mContentType = parcel.readString();
        mCaptureIntents = (Intent[]) parcel.readParcelableArray(getClass().getClassLoader());
    }


    @Override
    public final int describeContents()
    {
        return 0;
    }


    @Override
    public final void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(mRequestCode);
        dest.writeString(mContentType);
        dest.writeParcelableArray(mCaptureIntents, flags);
    }


    @Override
    public final void show(Activity activity, String title)
    {
        Intent getContentIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getContentIntent.setType(mContentType);
        getContentIntent.addCategory(Intent.CATEGORY_OPENABLE);

        Intent result = Intent.createChooser(getContentIntent, title);
        result.putExtra(Intent.EXTRA_INITIAL_INTENTS, mCaptureIntents);
        activity.startActivityForResult(result, mRequestCode);
    }
}
