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

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import org.cloudcargo.attachments.sdk.R;

import java.util.ArrayList;
import java.util.List;


/**
 * An adapter for {@link App}s.
 *
 * @author Tristan Heinig <tristan@dmfs.org>
 * @author Marten Gajda <marten@dmfs.org>
 */
class AppAdapter extends RecyclerView.Adapter<AppViewHolder>
{
    private final List<App> mApps;
    private final List<Store> mStores;


    public AppAdapter(List<App> apps, List<Store> stores)
    {
        mApps = new ArrayList<>(apps);
        mStores = stores;
    }


    @Override
    public AppViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new AppViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cloudcargo_apps_list_item, parent, false), mStores);
    }


    @Override
    public void onBindViewHolder(AppViewHolder holder, int position)
    {
        holder.bindApp(mApps.get(position));
    }


    @Override
    public int getItemCount()
    {
        return mApps.size();
    }
}