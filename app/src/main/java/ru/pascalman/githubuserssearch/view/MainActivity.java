package ru.pascalman.githubuserssearch.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import ru.pascalman.githubuserssearch.R;
import ru.pascalman.githubuserssearch.other.di.view.DaggerViewComponent;
import ru.pascalman.githubuserssearch.other.di.view.ViewComponent;
import ru.pascalman.githubuserssearch.other.di.view.ViewDynamicModule;
import ru.pascalman.githubuserssearch.presenter.User;
import ru.pascalman.githubuserssearch.presenter.UsersListPresenter;
import ru.pascalman.githubuserssearch.view.adapters.UsersAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.OnFocusChange;

public class MainActivity extends AppCompatActivity implements UsersListView, View.OnClickListener
{

    private static final String BUNDLE_USERS_LIST_KEY = "BUNDLE_USERS_LIST_KEY";
    private static final String BUNDLE_QUICK_USERS_LIST_KEY = "BUNDLE_QUICK_USERS_LIST_KEY";

    @Bind(R.id.toolbar)
    protected Toolbar toolbar;

    @Bind(R.id.recycler_view)
    protected RecyclerView mainView;

    @Bind(R.id.edit_text)
    protected EditText editText;

    @Bind(R.id.button_cancel)
    protected ImageButton btnCancel;

    @Inject
    protected UsersListPresenter mainPresenter;

    private UsersAdapter mainAdapter;

    private ViewComponent viewComponent;

    @Bind(R.id.quick_search_view)
    protected RecyclerView quickSearchView;

    @Inject
    protected UsersListPresenter quickSearchPresenter;

    private UsersAdapter quickSearchAdapter;

    @OnFocusChange(R.id.edit_text)
    public void onFocusChange(View v, boolean hasFocus)
    {
        if (hasFocus)
        {
            setDefaultToQuickSearch();
            quickSearchView.setVisibility(View.VISIBLE);
        }
        else
            quickSearchView.setVisibility(View.GONE);
    }

    private void setDefaultToQuickSearch()
    {
        quickSearchAdapter.setUsersList(new ArrayList<>());
        quickSearchView.getLayoutParams().height = 0;
    }

    @OnEditorAction(R.id.edit_text)
    public boolean onClickSearch(TextView v, int actionId, KeyEvent event)
    {
        if (mainPresenter != null)
            if (actionId == EditorInfo.IME_ACTION_SEARCH)
            {
                mainSearch();
                return true;
            }

        return false;
    }

    private void mainSearch()
    {
        mainPresenter.onSearchButtonClick();
        quickSearchView.setVisibility(View.GONE);
        hideSoftKeyboard(this);
        editText.clearFocus();
    }

    @OnClick(R.id.button_cancel)
    public void onClickCancel(View v)
    {
        editText.setText("");
        setDefaultToQuickSearch();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        if (viewComponent == null)
            viewComponent = DaggerViewComponent.builder()
                    .viewDynamicModule(new ViewDynamicModule(this))
                    .build();
        viewComponent.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        initRecyclerView();
        initQuickSearchView();

        mainPresenter.setKey(BUNDLE_USERS_LIST_KEY);
        mainPresenter.onCreateView(savedInstanceState);
        quickSearchPresenter.setKey(BUNDLE_QUICK_USERS_LIST_KEY);
        quickSearchPresenter.onCreateView(savedInstanceState);

        editText.addTextChangedListener(new TextWatcher()
        {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {}

            @Override
            public void afterTextChanged(Editable s)
            {
                if (quickSearchPresenter != null)
                    quickSearchPresenter.onSearchButtonClick();
            }

        });
    }

    private void initRecyclerView()
    {
        LinearLayoutManager llm = new LinearLayoutManager(this);
        mainView.setLayoutManager(llm);
        mainAdapter = new UsersAdapter();
        mainView.setAdapter(mainAdapter);
    }

    private void initQuickSearchView()
    {
        LinearLayoutManager llm = new LinearLayoutManager(this);
        quickSearchView.setLayoutManager(llm);
        quickSearchAdapter = new UsersAdapter(new ArrayList<>(), this);
        quickSearchView.setAdapter(quickSearchAdapter);
    }

    @Override
    public void onStop()
    {
        super.onStop();

        if (mainPresenter != null)
            mainPresenter.onStop();

        if (quickSearchPresenter != null)
            quickSearchPresenter.onStop();
    }

    public void setViewComponent(ViewComponent viewComponent)
    {
        this.viewComponent = viewComponent;
    }

    private void makeToast(String text)
    {
        Snackbar.make(mainView, text, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showError(String error)
    {
        makeToast(error);
    }

    @Override
    public void showUsersList(List<User> userList, String key)
    {
        switch (key)
        {
            case BUNDLE_USERS_LIST_KEY:
                mainAdapter.setUsersList(userList);
                break;
            case BUNDLE_QUICK_USERS_LIST_KEY:
                quickSearchAdapter.setUsersList(userList);

                updateUserSearchHeightByItemCount(userList.size());
                break;
        }
    }

    private void updateUserSearchHeightByItemCount(int count)
    {
        float itemHeight = getListPreferredItemHeight();
        int maxHeight = (int) (3 * itemHeight);
        int realHeight = (int) (count * itemHeight);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) quickSearchView.getLayoutParams();

        if (realHeight > maxHeight)
            params.height = maxHeight;
        else
            params.height = realHeight;
    }

    private float getListPreferredItemHeight()
    {
        TypedValue value = new TypedValue();
        DisplayMetrics metrics = new DisplayMetrics();

        getTheme().resolveAttribute(android.R.attr.listPreferredItemHeight, value, true);
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        return TypedValue.complexToDimension(value.data, metrics);
    }

    @Override
    public void showEmptyList()
    {
        makeToast(getString(R.string.empty_list));
    }

    @Override
    public String getUserName()
    {
        return editText.getText().toString();
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        mainPresenter.onSaveInstanceState(outState);
        quickSearchPresenter.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View v)
    {
        TextView textView = (TextView) v;

        editText.setText(textView.getText());

        mainSearch();
    }

    public static void hideSoftKeyboard(Activity activity)
    {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    public void onBackPressed()
    {
        quickSearchView.setVisibility(View.GONE);
        moveTaskToBack(true);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) //backend
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            onBackPressed();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

}
