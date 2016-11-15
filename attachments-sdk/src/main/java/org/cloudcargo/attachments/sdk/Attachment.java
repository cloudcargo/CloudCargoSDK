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

package org.cloudcargo.attachments.sdk;

import android.content.Context;
import android.support.annotation.NonNull;

import java.net.URI;


/**
 * An Attachment stored on a cloud service.
 *
 * @author Marten Gajda
 */
public interface Attachment
{
    /**
     * Returns the URL of this attachment as a {@link URI}. Opening this URL in a browser results in downloading the attachment. The user may be asked to
     * authenticate first though.
     *
     * @return A {@link URI} that points to the attachment.
     */
    @NonNull
    URI url();

    /**
     * Returns an {@link AttachmentPreview} for this attachment.
     *
     * @param context
     *         A {@link Context}.
     *
     * @return A {@link AttachmentPreview}.
     */
    @NonNull
    AttachmentPreview preview(@NonNull Context context);
}
