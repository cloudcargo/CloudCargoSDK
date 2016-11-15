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
import android.net.Uri;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.net.URI;
import java.util.ArrayList;


/**
 * A request to upload an attachment to a cloud service and return a {@link URI} that points to the attachment.
 * <p>
 * Instances of this interface must be immutable. All <code>with*</code> methods return a new instance, while the original instance will be left unchanged.
 *
 * @author Marten Gajda
 */
public interface AttachmentRequest extends Parcelable
{
    /**
     * Sets a list of sharees to give access to the attachment. Sharees are identified by their email addresses.
     * <p>
     * Note that this may not be supported by all cloud services. If a cloud service doesn't support sharing this will be ignored.
     * <p>
     * Also note that subsequent calls to this method will override previously added sharees.
     *
     * @param sharees
     *         A list of email addresses to share the attachment with.
     *
     * @return A new {@link AttachmentRequest} with sharees.
     */
    @NonNull
    AttachmentRequest withSharees(@NonNull ArrayList<String> sharees);

    /**
     * Sets the target {@link Uri} of the {@link AttachmentRequest}, which is the {@link Uri} of the item that the attachment is attached to.
     * <p>
     * If the target is not accessible via a content {@link Uri} you may omit this.
     *
     * @param target
     *         The {@link Uri} of the object the attachment belongs to.
     *
     * @return A new {@link AttachmentRequest} with a target {@link Uri}.
     */
    @NonNull
    AttachmentRequest withAttachmentTargetUri(@NonNull Uri target);

    /**
     * Sets the target {@link Account} of the {@link AttachmentRequest}, which is the {@link Account} of the item that the attachment is attached to.
     * <p>
     * If the target does not belong to a specific account you may omit this.
     * <p>
     * Upload apps may use this information to (pre-) select the right account.
     *
     * @param account
     *         The {@link Account} of the object the attachment belongs to.
     *
     * @return A new {@link AttachmentRequest} with a target {@link Account}.
     */
    @NonNull
    AttachmentRequest withAttachmentTargetAccount(@NonNull Account account);

    /**
     * Send this AttachmentRequest to upload an attachment to the cloud service.
     *
     * @param activity
     *         An {@link Activity}.
     */
    void send(@NonNull Activity activity);
}
