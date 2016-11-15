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

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;

import org.cloudcargo.attachments.sdk.Chooser;


/**
 * A {@link Chooser} that lets the user choose a video source.
 *
 * @author Marten Gajda
 */
public final class VideoChooser extends AbstractMediaChooser
{

    /**
     * Creates a {@link Chooser} for a video source.
     *
     * @param requestCode
     *         The request code to use when launching the chooser.
     */
    public VideoChooser(int requestCode)
    {
        super(requestCode, "video/*", new Intent(MediaStore.ACTION_VIDEO_CAPTURE));
    }


    /**
     * Private constructor for unparcelling.
     *
     * @param parcel
     *         A {@link Parcel}.
     */
    private VideoChooser(Parcel parcel)
    {
        super(parcel);
    }


    public final static Parcelable.Creator<VideoChooser> CREATOR = new Parcelable.Creator<VideoChooser>()
    {

        @Override
        public VideoChooser createFromParcel(Parcel source)
        {
            return new VideoChooser(source);
        }


        @Override
        public VideoChooser[] newArray(int size)
        {
            return new VideoChooser[size];
        }
    };
}
