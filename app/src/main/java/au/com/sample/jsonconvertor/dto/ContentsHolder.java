package au.com.sample.jsonconvertor.dto;

import java.util.ArrayList;

/**
 * Created by priju.jacobpaul on 29/11/15.
 */
public class ContentsHolder {

    ArrayList<String> mentions;
    ArrayList<String> emoticons;
    ArrayList<URLHolder> links;

    public ArrayList<String> getEmoticonsArray() {
        return emoticons;
    }

    /**
     * Add emotions
     * @param emoticon
     */
    public void addEmoticon(String emoticon) {
        if(getEmoticonsArray() == null){
            emoticons = new ArrayList<>();
        }
        emoticons.add(emoticon);
    }

    /**
     * Return links array
     * @return
     */
    public ArrayList<URLHolder> getLinksArray() {
        return links;
    }

    /**
     * add links
     * @param link
     */
    public void addLink(URLHolder link) {
        if(getLinksArray() == null){
            this.links = new ArrayList<>();
        }
        this.links.add(link);
    }

    /**
     * return mentions array
     * @return
     */
    public ArrayList<String> getMentionsArray() {
        return mentions;
    }

    /**
     * add mentions
     * @param mention
     */
    public void addMention(String mention) {
        if(getMentionsArray() == null){
            this.mentions = new ArrayList<>();
        }
        this.mentions.add(mention);
    }
}
