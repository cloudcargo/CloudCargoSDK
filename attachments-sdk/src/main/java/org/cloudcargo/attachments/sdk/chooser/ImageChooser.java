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
 * A {@link Chooser} that will let the user choose an image source.
 *
 * @author Marten Gajda
 */
public final class ImageChooser extends AbstractMediaChooser
{

    /**
     * Creates a {@link Chooser} for an image source.
     *
     * @param requestCode
     *         The request code to use when launching the chooser.
     */
    public ImageChooser(int requestCode)
    {
        super(requestCode, "image/*", new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
    }


    /**
     * Private constructor for unparcelling.
     *
     * @param parcel
     *         A {@link Parcel}.
     */
    private ImageChooser(Parcel parcel)
    {
        super(parcel);
    }


    public final static Parcelable.Creator<ImageChooser> CREATOR = new Parcelable.Creator<ImageChooser>()
    {

        @Override
        public ImageChooser createFromParcel(Parcel source)
        {
            return new ImageChooser(source);
        }


        @Override
        public ImageChooser[] newArray(int size)
        {
            return new ImageChooser[size];
        }
    };
}
