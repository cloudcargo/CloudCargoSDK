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

import android.accounts.Account;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;

import java.net.URI;
import java.util.ArrayList;


public final class AttachmentContract
{
    public final class AttachmentRequest
    {
        /**
         * Activity action to upload an attachment into the cloud.
         */
        public final static String ACTION_UPLOAD_ATTACHMENT = "org.cloudcargo.action.UPLOAD_ATTACHMENT";

        /**
         * An extra that contains a PendingIntent to deliver the result to.
         */
        public final static String EXTRA_RESULT_PENDING_INTENT = "org.cloudcargo.extra.RESULT_PENDING_INTENT";

        /**
         * An extra that contains an {@link ArrayList} of sharees to share the attachment with, if supported.
         */
        public final static String EXTRA_SHAREES = "org.cloudcargo.extra.SHAREES";

        /**
         * An extra that contains a {@link Uri} to the item that the attachment is attached to.
         */
        public final static String EXTRA_TARGETURI = "org.cloudcargo.extra.TARGETURI";

        /**
         * An extra that contains the {@link Account} of the item that the attachment is attached to.
         */
        public final static String EXTRA_TARGETACCOUNT = "org.cloudcargo.extra.TARGETACCOUNT";


        /**
         * No instances constructor.
         */
        private AttachmentRequest()
        {
        }
    }


    public final class ResolvePreview
    {
        /**
         * Broadcast action to resolve the preview {@link Uri} of a specific attachment {@link URI}. At this time, the intent doesn't take any additional
         * parameters. The data {@link Uri} is expected to have the value of the attachment {@link URI}.
         */
        public static final String ACTION_RESOLVE_PREVIEW = "org.cloudcargo.action.RESOLVE_PREVIEW";


        /**
         * No instances constructor.
         */
        private ResolvePreview()
        {
        }
    }


    public final class AttachmentResponse
    {

        /**
         * The key of an extra that contains any error message.
         */
        public static final String EXTRA_ERROR_MESSAGE = "org.cloudcargo.extra.MESSAGE";

        /**
         * The key of an extra that contains the result code of the operation.
         * <p>
         * This is mandatory when the response is delivered via a {@link PendingIntent} and has the same meaning as the respective parameter in {@link
         * Activity#onActivityResult(int, int, Intent)}.
         * <p>
         * The value is either {@link Activity#RESULT_OK} or {@link Activity#RESULT_CANCELED}.
         */
        public static final String EXTRA_RESULT_CODE = "org.cloudcargo.extra.RESULT_CODE";

        /**
         * Extra key of the upload response that contains the attachment {@link URI}.
         */
        public static final String EXTRA_ATTACHMENT_URL = "org.cloudcargo.extras.ATTACHMENT_URL";

        /**
         * Extra key of the upload response that contains the attachment preview {@link Uri}. This is optional.
         */
        public static final String EXTRA_PREVIEW_URI = "org.cloudcargo.extras.PREVIEW_URI";
    }


    /**
     * No instances constructor.
     */
    private AttachmentContract()
    {
    }
}
