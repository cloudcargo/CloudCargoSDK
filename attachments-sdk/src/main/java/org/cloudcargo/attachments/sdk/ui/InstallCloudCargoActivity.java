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

package org.cloudcargo.attachments.sdk.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;

import org.cloudcargo.attachments.sdk.R;
import org.dmfs.xmlobjects.pull.XmlObjectPullParserException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;


public final class InstallCloudCargoActivity extends AppCompatActivity implements AdapterView.OnItemClickListener
{
    private RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cloudcargo_activity_install_app);
        mRecyclerView = (RecyclerView) findViewById(R.id.app_list);
        try
        {
            // load the lost of apps
            mRecyclerView.setAdapter(new AppAdapter(Model.loadApps(this), Model.loadStores(this)));
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
        catch (XmlPullParserException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {

            e.printStackTrace();
        }
        catch (XmlObjectPullParserException e)
        {
            e.printStackTrace();
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
//        // an app has been clicked, fire the Play Store intent
//        App app = mAppList.get(position);
//        try
//        {
//            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + app.packageName));
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
//        }
//        catch (ActivityNotFoundException e)
//        {
//            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + app.packageName));
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
//        }
//        finish();
    }
}
