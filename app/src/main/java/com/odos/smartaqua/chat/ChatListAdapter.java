//package com.odos.smartaqua.chat;
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.databinding.DataBindingUtil;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.odos.smartaqua.R;
//import com.odos.smartaqua.databinding.AdapterChatListBinding;
//
//import java.util.ArrayList;
//
//public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.MyViewHolder> {
//
//    ArrayList<ChatListModel> homeModelArrayList;
//    private LayoutInflater layoutInflater;
//    private Context _context;
//
//
//    public class MyViewHolder extends RecyclerView.ViewHolder {
//
//        private final AdapterChatListBinding binding;
//
//        public MyViewHolder(AdapterChatListBinding itemBinding) {
//            super(itemBinding.getRoot());
//            this.binding = itemBinding;
//        }
//    }
//
//    public ChatListAdapter(Context context, ArrayList<ChatListModel> arrayList) {
//        this.homeModelArrayList = arrayList;
//        this._context = context;
//    }
//
//    @Override
//    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        if (layoutInflater == null) {
//            layoutInflater = LayoutInflater.from(parent.getContext());
//        }
//        AdapterChatListBinding binding =
//                DataBindingUtil.inflate(layoutInflater, R.layout.adapter_chat_list, parent, false);
//        return new MyViewHolder(binding);
//    }
//
//    @Override
//    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
//        ChatListModel chatModel = (ChatListModel) homeModelArrayList.get(position);
//        holder.binding.setModel(homeModelArrayList.get(position));
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return homeModelArrayList.size();
//    }
//
//
//}