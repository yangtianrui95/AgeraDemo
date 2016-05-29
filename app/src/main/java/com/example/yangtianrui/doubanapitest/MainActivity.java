package com.example.yangtianrui.doubanapitest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.agera.BaseObservable;
import com.google.android.agera.Repositories;
import com.google.android.agera.Repository;
import com.google.android.agera.Result;
import com.google.android.agera.Updatable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// MainActivity中使用观察者更新数据
public class MainActivity extends AppCompatActivity implements Updatable {

    private ListView mLvAllBooks;
    private EditText mEtKey;
    private Button mBtnQuery;

    private MyAdapter mAdapter;
    private List<Book> mBooks = new ArrayList<>();
    private ExecutorService mExecutor;
    private BooksSupplier mSupplier;
    private Repository<Result<List<Book>>> mRepository;
    private SearchObserver mObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupRepository();
        initView();
    }

    /**
     * 初始化Repository
     */
    private void setupRepository() {
        // 单线程线程池
        mExecutor = Executors.newSingleThreadExecutor();
        mObserver = new SearchObserver();
        mSupplier = new BooksSupplier("Hello");
        mRepository = Repositories
                .repositoryWithInitialValue(Result.<List<Book>>absent())
                .observe(mObserver)
                .onUpdatesPerLoop()
                .goTo(mExecutor)
                .thenGetFrom(mSupplier)
                .compile();

    }

    private void initView() {
        mLvAllBooks = (ListView) findViewById(R.id.id_lv_all_items);
        mEtKey = (EditText) findViewById(R.id.id_et_key_word);
        mBtnQuery = (Button) findViewById(R.id.id_btn_query);
        mRepository.addUpdatable(MainActivity.this);
        mAdapter = new MyAdapter(this, mBooks);
        mLvAllBooks.setAdapter(mAdapter);
        mBtnQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String key = mEtKey.getText().toString();
                mObserver.doSearch(key);

                Log.v("LOG", "Activity  onClick  " + key);
            }
        });
    }


    private class SearchObserver extends BaseObservable {
        public void doSearch(String key) {
            mSupplier.setKey(key);
            dispatchUpdate();
            Log.v("LOG", "Activity  doSearch");
        }
    }


    // 执行查询操作
    @Override
    public void update() {
        Log.v("LOG", "Activity  update");
        if (mRepository.get().isPresent()) {
            ///TODO
            mBooks.clear();
            mBooks.addAll(mRepository.get().get());
            mAdapter.notifyDataSetChanged();
            Log.v("LOG", mBooks.toString());
        }
    }
}
