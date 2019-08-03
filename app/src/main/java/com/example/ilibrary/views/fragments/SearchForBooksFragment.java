package com.example.ilibrary.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ilibrary.R;
import com.example.ilibrary.adapters.ViewBooksAdapter;
import com.example.ilibrary.customListeners.MyTextWatcher;
import com.example.ilibrary.databinding.SearchBookBinding;
import com.example.ilibrary.interfaces.SearchBooks;
import com.example.ilibrary.models.data.Book;
import com.example.ilibrary.presetners.SearchBooksPresenter;

import java.util.List;

public class SearchForBooksFragment extends Fragment implements SearchBooks.SearchBooksFragment {

    private SearchBookBinding searchBookBinding;
    private SearchBooks.SearchBooksPresenter presenter;
    private ViewBooksAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        searchBookBinding = DataBindingUtil.inflate(inflater, R.layout.search_book, container, false);
        presenter = new SearchBooksPresenter(this);

        initiateSpinner();
        initiateSearchButton();
        initiateArrowButton();
        initiateEditTextsOnChangeText();

        return searchBookBinding.getRoot();


    }

    @Override
    public void setSearchBooksAdapter(List<Book> books) {

        if (books == null || books.size() == 0) {
            books.add(new Book("", "Book Not Found!", "", "", ""));
        }

        searchBookBinding.searchBookRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        adapter = new ViewBooksAdapter(books, this.getActivity(), 0);

        searchBookBinding.searchBookRecyclerView.setAdapter(adapter);
        searchBookBinding.arrow.setVisibility(View.VISIBLE);

    }

    @Override
    public void initiateSearchButton() {

        searchBookBinding.searchButton.setOnClickListener(view -> {

            if (validate())
                if (!TextUtils.isEmpty(searchBookBinding.fromPages.getText()) && !TextUtils.isEmpty(searchBookBinding.toPages.getText()))
                    presenter.searchForBooks(Integer.parseInt(searchBookBinding.fromPages.getText().toString()), Integer.parseInt(searchBookBinding.toPages.getText().toString()), searchBookBinding.publisher.getText().toString());
                else
                    presenter.searchForBooks(0, 0, searchBookBinding.publisher.getText().toString());

        });

    }

    @Override
    public void showElements(String element) {

        if (element.equals("page")) {
            searchBookBinding.pagesText.setVisibility(View.VISIBLE);
            searchBookBinding.fromPages.setVisibility(View.VISIBLE);
            searchBookBinding.toPages.setVisibility(View.VISIBLE);
        } else if (element.equals("publisher")) {
            searchBookBinding.publisherText.setVisibility(View.VISIBLE);
            searchBookBinding.publisher.setVisibility(View.VISIBLE);
        } else if (element.equals("both")) {

            searchBookBinding.pagesText.setVisibility(View.VISIBLE);
            searchBookBinding.fromPages.setVisibility(View.VISIBLE);
            searchBookBinding.toPages.setVisibility(View.VISIBLE);
            searchBookBinding.publisherText.setVisibility(View.VISIBLE);
            searchBookBinding.publisher.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void hideElements(String element) {

        if (element.equals("page")) {
            searchBookBinding.pagesText.setVisibility(View.GONE);
            searchBookBinding.fromPages.setVisibility(View.GONE);
            searchBookBinding.toPages.setVisibility(View.GONE);
        } else if (element.equals("publisher")) {
            searchBookBinding.publisherText.setVisibility(View.GONE);
            searchBookBinding.publisher.setVisibility(View.GONE);
        } else if (element.equals("both")) {

            searchBookBinding.pagesText.setVisibility(View.GONE);
            searchBookBinding.fromPages.setVisibility(View.GONE);
            searchBookBinding.toPages.setVisibility(View.GONE);
            searchBookBinding.publisherText.setVisibility(View.GONE);
            searchBookBinding.publisher.setVisibility(View.GONE);

        }
    }

    @Override
    public void initiateArrowButton() {
        searchBookBinding.arrow.setOnClickListener(e -> {

            if (searchBookBinding.arrow.isActivated()) {
                searchBookBinding.searchLayout.setVisibility(View.VISIBLE);
            } else {
                searchBookBinding.searchLayout.setVisibility(View.GONE);
            }

            searchBookBinding.arrow.setActivated(!searchBookBinding.arrow.isActivated());


        });
    }


    @Override
    public void initiateSpinner() {

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.search_entries, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchBookBinding.spinner.setAdapter(adapter);

        searchBookBinding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                searchBookBinding.chooseOneError.setText("");

                String selection = adapterView.getItemAtPosition(i).toString();

                if (selection.equals("Num Of Pages")) {
                    showElements("page");
                    hideElements("publisher");
                } else if (selection.equals("Publisher")) {
                    showElements("publisher");
                    hideElements("page");
                } else if (selection.equals("Both")) {
                    showElements("both");
                } else
                    hideElements("both");

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public boolean validate() {

        boolean isValid = true;
        String from = searchBookBinding.fromPages.getText().toString();
        String to = searchBookBinding.toPages.getText().toString();
        String publisher = searchBookBinding.publisher.getText().toString();


        if (searchBookBinding.spinner.getSelectedItem().toString().equals("Choose One")) {
            isValid = false;
            searchBookBinding.chooseOneError.setText("* Please Select one of the following!");
        } else if (searchBookBinding.spinner.getSelectedItem().toString().equals("Publisher") && TextUtils.isEmpty(publisher)) {
            isValid = false;
            searchBookBinding.publisherError.setText("* Publisher is Required!");
        } else if (searchBookBinding.spinner.getSelectedItem().toString().equals("Num Of Pages") && ((TextUtils.isEmpty(from) && TextUtils.isEmpty(to))) || ((!TextUtils.isEmpty(from) && TextUtils.isEmpty(to)) || (!TextUtils.isEmpty(to) && TextUtils.isEmpty(from)))) {

            isValid = false;

            searchBookBinding.pagesError.setText("* From and To is Required!");
        } else if (searchBookBinding.spinner.getSelectedItem().toString().equals("Both")) {

            if ((TextUtils.isEmpty(from) && TextUtils.isEmpty(to)) || ((!TextUtils.isEmpty(from) && TextUtils.isEmpty(to)) || (!TextUtils.isEmpty(to) && TextUtils.isEmpty(from)))) {

                isValid = false;

                searchBookBinding.pagesError.setText("* From and To is Required!");
            }

            if (TextUtils.isEmpty(publisher)) {
                isValid = false;
                searchBookBinding.publisherError.setText("* Publisher is Required!");
            }


        }

        return isValid;
    }

    @Override
    public void initiateEditTextsOnChangeText() {

        searchBookBinding.fromPages.addTextChangedListener(new MyTextWatcher(() -> {
            searchBookBinding.pagesError.setText("");
        }));

        searchBookBinding.toPages.addTextChangedListener(new MyTextWatcher(() -> {
            searchBookBinding.pagesError.setText("");
        }));

        searchBookBinding.publisher.addTextChangedListener(new MyTextWatcher(() -> {
            searchBookBinding.publisherError.setText("");
        }));
    }
}
