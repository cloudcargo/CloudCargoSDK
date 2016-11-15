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

package org.cloudcargo.sdk.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.cloudcargo.attachments.sdk.PreviewLoaderCallback;
import org.cloudcargo.attachments.sdk.PreviewResult;
import org.cloudcargo.attachments.sdk.attachments.ResponseAttachment;
import org.cloudcargo.attachments.sdk.chooser.ImageChooser;
import org.cloudcargo.attachments.sdk.exceptions.BitmapDecoderException;
import org.cloudcargo.attachments.sdk.requests.BasicAttachmentRequest;
import org.cloudcargo.attachments.sdk.requests.ChooserResultAttachmentRequest;

import java.io.FileNotFoundException;


public class MainActivity extends AppCompatActivity implements View.OnClickListener
{

    private static final String TAG = "debug DemoActivity";
    private static final int REQUEST_CODE_UPLOAD_ATTACHMENT = 1;
    private static final int REQUEST_CODE_URI_CHOOSER = 2;

    private TextView mUriView;
    private ImageView mImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_1).setOnClickListener(this);
        findViewById(R.id.btn_2).setOnClickListener(this);

        mUriView = (TextView) findViewById(R.id.tv_1);
        mImageView = (ImageView) findViewById(R.id.img_1);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent activityResultIntent)
    {
        if (resultCode == RESULT_OK)
        {
            if (requestCode == REQUEST_CODE_URI_CHOOSER)
            {
                new ChooserResultAttachmentRequest(activityResultIntent, REQUEST_CODE_UPLOAD_ATTACHMENT).send(this);
            }
            else if (requestCode == REQUEST_CODE_UPLOAD_ATTACHMENT)
            {
                // response from attachment app
                //		mUriView.setText("attachment url: \n" + activityResultIntent.getSerializableExtra(AttachmentContract.EXTRA_ATTACHMENT_URL).toString());

                new ResponseAttachment(activityResultIntent).preview(this).load(this, new PreviewLoaderCallback()
                {

                    @Override
                    public void onPreview(@NonNull PreviewResult previewResult)
                    {
                        try
                        {
                            mImageView.setImageBitmap(previewResult.previewData());
                        }
                        catch (FileNotFoundException e)
                        {
                            e.printStackTrace();
                        }
                        catch (BitmapDecoderException e)
                        {
                            e.printStackTrace();
                        }
                    }

                });
            }

        }
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_1:
            {
                new BasicAttachmentRequest(REQUEST_CODE_UPLOAD_ATTACHMENT).send(this);
                break;
            }
            case R.id.btn_2:
            {
                new ImageChooser(REQUEST_CODE_URI_CHOOSER).show(this, "get some audio");
                break;
            }
        }
    }
}
