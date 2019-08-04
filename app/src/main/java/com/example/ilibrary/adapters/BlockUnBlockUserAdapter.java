package com.example.ilibrary.adapters;

import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ilibrary.databinding.BlockUnblockCardviewBinding;
import com.example.ilibrary.models.DatabaseHelper;
import com.example.ilibrary.models.data.User;
import com.example.ilibrary.views.SignInActivity;
import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.github.angads25.toggle.model.ToggleableView;

import java.util.List;

public class BlockUnBlockUserAdapter extends RecyclerView.Adapter<BlockUnBlockUserAdapter.BlockUnBlockViewHolder> {


    private List<User> users;
    private DatabaseHelper databaseHelper;
    private FragmentActivity activity;
    private Cursor cursor;

    public BlockUnBlockUserAdapter(List<User> books, FragmentActivity activity) {
        databaseHelper = new DatabaseHelper(activity);
        this.cursor = databaseHelper.getReservedBooks(SignInActivity.username);
        this.activity = activity;
        this.users = books;

    }

    @NonNull
    @Override
    public BlockUnBlockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        BlockUnblockCardviewBinding blockUnblockCardviewBinding = BlockUnblockCardviewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        BlockUnBlockViewHolder blockUnBlockViewHolder = new BlockUnBlockViewHolder(blockUnblockCardviewBinding);
        return blockUnBlockViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BlockUnBlockViewHolder holder, int position) {

        User user = users.get(position);
        holder.blockUnblockCardviewBinding.blockSwitch.setOn(databaseHelper.getUserStatus(user.getUsername()) == 1);
        holder.blockUnblockCardviewBinding.setUser(user);
        initBlockUnBlockSwitch(holder, user.getUsername());


    }

    private void initBlockUnBlockSwitch(BlockUnBlockViewHolder holder, String username) {

        holder.blockUnblockCardviewBinding.blockSwitch.setOnToggledListener((toggleableView, isOn) -> {

            if (isOn) {

                databaseHelper.blockAUser(username);
            } else {

                databaseHelper.unBlockAUser(username);
            }

        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }


    public class BlockUnBlockViewHolder extends RecyclerView.ViewHolder {

        private BlockUnblockCardviewBinding blockUnblockCardviewBinding;

        public BlockUnBlockViewHolder(@NonNull BlockUnblockCardviewBinding itemView) {
            super(itemView.getRoot());
            this.blockUnblockCardviewBinding = itemView;
        }
    }

}
