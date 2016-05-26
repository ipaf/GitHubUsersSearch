package ru.pascalman.githubuserssearch.view.adapters;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.pascalman.githubuserssearch.R;

import java.util.List;

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseAdapter.ViewHolder>
{

    protected List<T> list;

    private View.OnClickListener listener;

    public BaseAdapter(List<T> list, View.OnClickListener listener)
    {
        this.list = list;
        this.listener = listener;
    }

    public List<T> getList()
    {
        return list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        Context context = viewGroup.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.text_list_item, viewGroup, false);

        return new ViewHolder(v, listener);
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {

        TextView text;

        public ViewHolder(View itemView, View.OnClickListener listener)
        {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.textView);

            if (listener != null)
                text.setOnClickListener(listener);
        }

    }

}
