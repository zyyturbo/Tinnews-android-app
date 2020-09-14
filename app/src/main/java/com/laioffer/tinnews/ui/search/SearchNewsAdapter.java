package com.laioffer.tinnews.ui.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.laioffer.tinnews.R;
import com.laioffer.tinnews.databinding.SearchNewsItemBinding;
import com.laioffer.tinnews.model.Article;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SearchNewsAdapter extends RecyclerView.Adapter<SearchNewsAdapter.SearchNewsViewHolder> {
    interface ItemCallback {
        void onOpenDetails(Article article);
    }

    private ItemCallback itemCallback;

    public void setItemCallback(ItemCallback itemCallback) {
        this.itemCallback = itemCallback;
    }

    // 1. Supporting data:
    // TODO
    private List<Article> articles = new ArrayList<>();

    public void setArticles(List<Article> newsList) {
        articles.clear();
        articles.addAll(newsList);
        notifyDataSetChanged();
    }

    public void updateItemAtIndex(int position, Article article) {
        articles.set(position, article);
        notifyItemRangeChanged(position, 1);  // 只会rebind单个item
    }

    // 2. Adapter overrides:
    // TODO
    @NonNull
    @Override
    public SearchNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_news_item, parent, false);
        return new SearchNewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchNewsViewHolder holder, int position) {
        Article article = articles.get(position);
//        holder.favoriteImageView.setImageResource(R.drawable.ic_favorite_24dp);
        if (article.favorite)
            holder.favoriteImageView.setImageResource(R.drawable.ic_favorite_24dp);
        else
            holder.favoriteImageView.setImageResource((R.drawable.ic_favorite_border_24dp));
        holder.favoriteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                article.favorite = !article.favorite;  // only 61 will not work, because need to rebind
                notifyDataSetChanged();  // 61 + 62 could work but bot good  每点一次，加载过的都要rebind一次
//                callback.updateItemIndex();
            }
        });
        holder.itemTitleTextView.setText(article.title);
        Picasso.get().load(article.urlToImage).into(holder.itemImageView);
        holder.itemView.setOnClickListener(v -> itemCallback.onOpenDetails(article));
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    // 3. SearchNewsViewHolder:
    // TODO
    public static class SearchNewsViewHolder extends RecyclerView.ViewHolder {
        ImageView favoriteImageView;
        ImageView itemImageView;
        TextView itemTitleTextView;

        public SearchNewsViewHolder(@NonNull View itemView) {
            super(itemView);
            SearchNewsItemBinding binding = SearchNewsItemBinding.bind(itemView);
            favoriteImageView = binding.searchItemFavorite;
            itemImageView = binding.searchItemImage;
            itemTitleTextView = binding.searchItemTitle;
        }
    }
}
