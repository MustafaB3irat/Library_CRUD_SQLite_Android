package com.example.ilibrary.views.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.ilibrary.R;
import com.example.ilibrary.adapters.BlockUnBlockUserAdapter;
import com.example.ilibrary.customListeners.MyTextWatcher;
import com.example.ilibrary.databinding.BlockUnblockUsersBinding;
import com.example.ilibrary.interfaces.BlockUnBlockUsers;
import com.example.ilibrary.models.data.User;
import com.example.ilibrary.presetners.BlockUnBlockUsersPresenter;

import java.util.List;

public class BlockUnBlockUserFragment extends Fragment implements BlockUnBlockUsers.BlockUnBlcokUsersFragment {


    private BlockUnblockUsersBinding blockUnblockUsersBinding;
    private BlockUnBlockUserAdapter blockUnBlockUserAdapter;
    private BlockUnBlockUsers.BlockUnBlockUsersPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        blockUnblockUsersBinding = DataBindingUtil.inflate(inflater, R.layout.block_unblock_users, container, false);
        presenter = new BlockUnBlockUsersPresenter(this);

        initOnEditTextChange();

        return blockUnblockUsersBinding.getRoot();
    }

    @Override
    public void initRecyclerView(List<User> userList) {

        blockUnblockUsersBinding.searchRecyclerview.setLayoutManager(new LinearLayoutManager(this.getContext()));
        blockUnBlockUserAdapter = new BlockUnBlockUserAdapter(userList, this.getActivity());
        blockUnblockUsersBinding.searchRecyclerview.setAdapter(blockUnBlockUserAdapter);

    }

    @Override
    public void initOnEditTextChange() {

        blockUnblockUsersBinding.searchEdittext.addTextChangedListener(new MyTextWatcher(() -> {

            if (!TextUtils.isEmpty(blockUnblockUsersBinding.searchEdittext.getText().toString())) {
                presenter.getUserByName(blockUnblockUsersBinding.searchEdittext.getText().toString());
            }

        }));

    }
}
