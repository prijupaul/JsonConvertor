package au.com.sample.jsonconvertor.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import au.com.sample.jsonconvertor.R;
import au.com.sample.jsonconvertor.dto.ContentsHolder;
import au.com.sample.jsonconvertor.network.TitleFetcher;
import au.com.sample.jsonconvertor.parser.InputParser;

public class MainActivity extends AppCompatActivity implements InputParser.InputParserContentsListener, TitleFetcher.TitleFetcherListener {

    private InputParser mInputParser;
    private TitleFetcher mTitleFetcher;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView)findViewById(R.id.textView);
        mInputParser = new InputParser();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Parse these strings.
        mInputParser.parseString("@chris you around?",this);
        mInputParser.parseString("Good morning! (megusta) (coffee)",this);
        mInputParser.parseString("Olympics are starting soon; http://www.nbcolympics.com",this);
        mInputParser.parseString("@bob @john (success) such a cool feature; https://twitter.com/jdorfman/status/430511497475670016",this);
        mInputParser.parseString("@bob @john (success) such a cool feature; https://twitter.com/jdorfman/status/430511497475670016 and check this page http://www.nbcolympics.com",this);

    }

    /**
     * Activity stopped
     */
    @Override
    protected void onStop() {
        super.onStop();
        if(mTitleFetcher != null){
            // Cancel all operations if its going on.
            mTitleFetcher.cancelOperations();
            mTitleFetcher = null;
        }
    }


    /**
     * Invoked one parsing is complete
     * @param contentsHolder
     */
    @Override
    public void onParsingComplete(ContentsHolder contentsHolder) {

        if( (contentsHolder.getLinksArray()!= null) && !contentsHolder.getLinksArray().isEmpty()){
            mTitleFetcher = new TitleFetcher();
            mTitleFetcher.setTitleFetcherListener(this,getApplicationContext());
            mTitleFetcher.FetchTitles(contentsHolder);
        }
        else {
            createJson(contentsHolder);
        }

    }

    /**
     * onTitleFetcherComplete
     * Invoked once the title is fetched
     * @param contentsHolder
     */
    @Override
    public void onTitleFetcherComplete(ContentsHolder contentsHolder) {
        if(contentsHolder != null) {
            createJson(contentsHolder);
        }
    }

    /**
     * Creates the JSON string for the contents passed.
     * @param contentsHolder
     */
    private void createJson(ContentsHolder contentsHolder){
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        String gsonStr = gson.toJson(contentsHolder);
        StringBuilder text = new StringBuilder(mTextView.getText().toString());
        text.append(gsonStr);
        text.append(("\n\n"));

        mTextView.setText("");
        mTextView.setText(text);
        mTextView.invalidate();
    }

}

