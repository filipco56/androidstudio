package recycle_view_with_volley.android.com.recycle;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

/**
 * Created by Filip on 05/04/2016.
 */
public class ListRowViewHolder extends RecyclerView.ViewHolder {

    protected NetworkImageView thumbnail;
    protected TextView title;
    protected TextView subreddit;
    protected TextView author;
    protected RelativeLayout relativeLayout;
    protected TextView url;

    public ListRowViewHolder(View view) {
        super(view);
        this.thumbnail = (NetworkImageView)view.findViewById(R.id.networkImage);
        this.title = (TextView) view.findViewById(R.id.article_title);
        this.url = (TextView) view.findViewById(R.id.text_view_URL);
        this.relativeLayout = (RelativeLayout) view.findViewById(R.id.rel_Layout);
        this.subreddit = (TextView) view.findViewById(R.id.textView2);
        this.author = (TextView)view.findViewById(R.id.autor);
        view.setClickable(true);
    }
}
