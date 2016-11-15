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

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


/**
 * @author Marten Gajda
 */
public final class AppViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    private final ImageView mIcon;
    private final TextView mTitle;
    private final TextView mDescription;
    private final List<Store> mStores;
    private App mApp;


    public AppViewHolder(View itemView, List<Store> stores)
    {
        super(itemView);
        mStores = stores;
        itemView.setOnClickListener(this);
        mIcon = (ImageView) itemView.findViewById(android.R.id.icon);
        mTitle = (TextView) itemView.findViewById(android.R.id.title);
        mDescription = (TextView) itemView.findViewById(android.R.id.text1);
    }


    public void bindApp(App app)
    {
        mApp = app;
        mTitle.setText(app.title);
        mDescription.setText(app.description);
    }


    @Override
    public void onClick(View v)
    {
        v.getContext().startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(mApp.sources.get(0).href)));
    }
}
