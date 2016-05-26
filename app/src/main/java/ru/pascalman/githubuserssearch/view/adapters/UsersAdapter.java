package ru.pascalman.githubuserssearch.view.adapters;

import android.view.View;

import ru.pascalman.githubuserssearch.presenter.User;

import java.util.ArrayList;
import java.util.List;

public class UsersAdapter extends BaseAdapter<User>
{

    public UsersAdapter()
    {
        this(new ArrayList<>(), null);
    }

    public UsersAdapter(List<User> list)
    {
        this(list, null);
    }

    public UsersAdapter(List<User> list, View.OnClickListener listener)
    {
        super(list, listener);
    }

    @Override
    public void onBindViewHolder(BaseAdapter.ViewHolder viewHolder, int i)
    {
        User user = list.get(i);
        viewHolder.text.setText(user.getName());
    }

    public void setUsersList(List<User> list)
    {
        this.list = list;
        notifyDataSetChanged();
    }

}
