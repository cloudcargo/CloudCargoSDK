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
 * An {@link Chooser} that lets the user choose any media source.
 *
 * @author Marten Gajda
 */
public final class AnyMediaChooser extends AbstractMediaChooser
{

    /**
     * Creates a {@link Chooser} for any media type.
     *
     * @param requestCode
     *         The request code to use when launching the chooser.
     */
    public AnyMediaChooser(int requestCode)
    {
        super(requestCode, "*/*", new Intent(MediaStore.ACTION_IMAGE_CAPTURE), new Intent(MediaStore.ACTION_VIDEO_CAPTURE),
                new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION));
    }


    /**
     * Private constructor for unparcelling.
     *
     * @param parcel
     *         A {@link Parcel}.
     */
    private AnyMediaChooser(Parcel parcel)
    {
        super(parcel);
    }


    public final static Parcelable.Creator<AnyMediaChooser> CREATOR = new Parcelable.Creator<AnyMediaChooser>()
    {

        @Override
        public AnyMediaChooser createFromParcel(Parcel source)
        {
            return new AnyMediaChooser(source);
        }


        @Override
        public AnyMediaChooser[] newArray(int size)
        {
            return new AnyMediaChooser[size];
        }
    };
}
