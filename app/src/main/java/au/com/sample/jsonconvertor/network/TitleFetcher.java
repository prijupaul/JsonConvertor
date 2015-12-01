package au.com.sample.jsonconvertor.network;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;

import au.com.sample.jsonconvertor.dto.ContentsHolder;
import au.com.sample.jsonconvertor.dto.URLHolder;
import au.com.sample.jsonconvertor.util.NetworkUtil;

/**
 * Created by priju.jacobpaul on 30/11/15.
 */
public class TitleFetcher {

    public interface TitleFetcherListener {
        void onTitleFetcherComplete(ContentsHolder contentsHolder);
    }

    private TitleFetcherListener mTitleFetcherListener;
    private ContentsHolder mContentsHolder;
    private GetTitleTask mGetTitleTask;
    private Context mContext;

    /**
     * constructor
     *
     * @param titleFetcherListener
     */
    public void setTitleFetcherListener(TitleFetcherListener titleFetcherListener, Context context) {
        mTitleFetcherListener = titleFetcherListener;
        mContext = context;
    }

    /**
     * Fetch the titles for URL
     *
     * @param contentsHolder
     */
    public void FetchTitles(ContentsHolder contentsHolder) {

        // If network is not available just try to fetch the title.
        if(!NetworkUtil.isNetworkAvailable(mContext)){
            Toast.makeText(mContext,"Network not available. Please check the connectivity",Toast.LENGTH_SHORT).show();
            mTitleFetcherListener.onTitleFetcherComplete(contentsHolder);
            return;
        }

        ArrayList<URLHolder> urlHolders = contentsHolder.getLinksArray();
        if ((urlHolders != null) && (urlHolders.size() > 0)) {
            mContentsHolder = contentsHolder;
            mGetTitleTask = new GetTitleTask();
            mGetTitleTask.execute(mContentsHolder);
        }
    }

    /**
     * Cancel Async tasks
     */
    public void cancelOperations() {

        if ((mGetTitleTask != null) && (!mGetTitleTask.isCancelled())) {
            mGetTitleTask.cancel(true);
            mTitleFetcherListener = null;
        }
    }

    /**
     * Class to fetch title
     */
    private class GetTitleTask extends AsyncTask<ContentsHolder, Void, ContentsHolder> {

        @Override
        protected ContentsHolder doInBackground(ContentsHolder... contentsHolders) {

            // Get the titles for each url using jsoup library.
            ContentsHolder contentsHolder = contentsHolders[0];
            for (URLHolder urlHolder : contentsHolder.getLinksArray()) {
                try {
                    Document doc = Jsoup.connect(urlHolder.getUrl()).get();
                    urlHolder.setTitle(doc.title());
                } catch (IOException e) {
                    e.printStackTrace();
                    urlHolder.setTitle("");
                }
            }
            return contentsHolder;
        }

        @Override
        protected void onPostExecute(ContentsHolder contentsHolder) {
            super.onPostExecute(contentsHolder);
            if (mTitleFetcherListener != null) {
                mTitleFetcherListener.onTitleFetcherComplete(contentsHolder);
            }

        }
    }


}
