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

package org.cloudcargo.attachments.sdk.exceptions;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;


/**
 * Indicates that it was not possible to transform binary data into {@link Bitmap} data.
 *
 * @author Tristan Heinig
 */
public final class BitmapDecoderException extends Exception
{

    /**
     * Generated serial id.
     */
    private static final long serialVersionUID = 0L;


    public BitmapDecoderException(@NonNull String msg)
    {
        super(msg);
    }

}
