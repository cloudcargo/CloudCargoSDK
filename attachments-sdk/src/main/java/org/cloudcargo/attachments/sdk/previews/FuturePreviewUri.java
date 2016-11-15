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

package org.cloudcargo.attachments.sdk.previews;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

import org.cloudcargo.attachments.sdk.Attachment;
import org.cloudcargo.attachments.sdk.AttachmentContract;

import java.net.URI;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


/**
 * A {@link Future} that resolves the {@link Uri} of the preview of an {@link Attachment}.
 *
 * @author Marten Gajda
 */
public final class FuturePreviewUri implements Future<Uri>
{
    private Uri mPreviewUri;
    private boolean mCanceled;
    private boolean mDone;
    private Exception mException;

    /**
     * The broadcast receiver that will receive the result.
     */
    private final BroadcastReceiver mResultReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            synchronized (this)
            {
                if (mCanceled || mDone)
                {
                    return;
                }
                try
                {
                    if (getResultCode() == Activity.RESULT_OK)
                    {
                        mPreviewUri = Uri.parse(getResultData());
                    }
                }
                catch (Exception e)
                {
                    mException = e;
                }
                finally
                {
                    mDone = true;
                    this.notify();
                }
            }
        }
    };


    /**
     * Creates a {@link Future} that resolves the preview {@link Uri} of the given attachment {@link URI}.
     *
     * @param context
     *         A {@link Context}.
     * @param attachmentUrl
     *         The URL of the attachment.
     */
    public FuturePreviewUri(@NonNull Context context, @NonNull URI attachmentUrl)
    {
        // start resolving now. This is not really lazy, but we want to avoid waiting any longer than we have to.
        context.sendOrderedBroadcast(
                new Intent(
                        AttachmentContract.ResolvePreview.ACTION_RESOLVE_PREVIEW,
                        Uri.parse(attachmentUrl.toASCIIString())),
                null,
                mResultReceiver,
                null,
                Activity.RESULT_CANCELED,
                null,
                null);
    }


    @Override
    public boolean cancel(boolean mayInterruptIfRunning)
    {
        synchronized (mResultReceiver)
        {
            if (mCanceled || mDone)
            {
                // nothing to do
                return mCanceled;
            }

            mCanceled = true;
            mResultReceiver.notify();
            return true;
        }
    }


    @Override
    public Uri get() throws InterruptedException, ExecutionException
    {
        try
        {
            return get(366 * 3000, TimeUnit.DAYS);
        }
        catch (TimeoutException e)
        {
            throw new RuntimeException("This piece of code has been running for more than 3000 years, stopping now.", e);
        }
    }


    @Override
    public Uri get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException
    {
        synchronized (mResultReceiver)
        {
            long now = System.currentTimeMillis();
            long end = now + unit.toMillis(timeout);

            // wait for the result being ready, someone cancelled us or the timeout expired.
            while (!mDone && !mCanceled && end > now)
            {
                mResultReceiver.wait(end - now);
                now = System.currentTimeMillis();
            }
        }
        if (mException != null)
        {
            throw new ExecutionException("Exception while resolving the preview Uri", mException);
        }
        if (mCanceled)
        {
            throw new CancellationException("Resolver was cancelled.");
        }
        if (!mDone)
        {
            throw new TimeoutException(String.format("Timeout after %d milliseconds", unit.toMillis(timeout)));
        }
        return mPreviewUri;
    }


    @Override
    public boolean isCancelled()
    {
        return mCanceled;
    }


    @Override
    public boolean isDone()
    {
        return mDone;
    }

}
