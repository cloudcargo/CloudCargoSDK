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
 * An {@link Chooser} that lets the user choose an audio source.
 *
 * @author Marten Gajda
 */
public final class AudioChooser extends AbstractMediaChooser
{

    /**
     * Creates a {@link Chooser} for an audio source.
     *
     * @param requestCode
     *         The request code to use when launching the chooser.
     */
    public AudioChooser(int requestCode)
    {
        super(requestCode, "audio/*", new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION));
    }


    /**
     * Private constructor for unparcelling.
     *
     * @param parcel
     *         A {@link Parcel}.
     */
    private AudioChooser(Parcel parcel)
    {
        super(parcel);
    }


    public final static Parcelable.Creator<AudioChooser> CREATOR = new Parcelable.Creator<AudioChooser>()
    {

        @Override
        public AudioChooser createFromParcel(Parcel source)
        {
            return new AudioChooser(source);
        }


        @Override
        public AudioChooser[] newArray(int size)
        {
            return new AudioChooser[size];
        }
    };
}
