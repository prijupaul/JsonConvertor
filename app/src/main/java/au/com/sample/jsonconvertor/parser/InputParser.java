package au.com.sample.jsonconvertor.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import au.com.sample.jsonconvertor.dto.ContentsHolder;
import au.com.sample.jsonconvertor.dto.URLHolder;

/**
 * Created by priju.jacobpaul on 28/11/15.
 */
public class InputParser {

    public interface InputParserContentsListener{
        void onParsingComplete(ContentsHolder contentsHolder);
    }

    private InputParserContentsListener mParserContentsListener;

    /**
     * function to parse the string
     * @param inputString
     */
    public void parseString(String inputString,InputParserContentsListener parserContentsListener){

        if( (inputString == null) || inputString.isEmpty()){
            return;
        }

        this.mParserContentsListener = parserContentsListener;

        ContentsHolder contentsHolder = new ContentsHolder();

        // Regular expression for mentions
        String nameRegex = "([@]\\w+\\s?)";
        // Regular expression for emoticons
        String emoticonRegEx = "([(]\\w+[)]\\s?)";
        // Regular expression for urls
        String urlRegEx = "(https?:\\/\\/(?:www\\.|(?!www))[^\\s\\.]+\\.[^\\s]{2,}|www\\.[^\\s]+\\.[^\\s]{2,}|[^\\s]+\\.[^\\s]{2,})";

        Pattern namePattern = Pattern.compile(nameRegex,Pattern.CASE_INSENSITIVE);
        Pattern emoticonPattern = Pattern.compile(emoticonRegEx,Pattern.CASE_INSENSITIVE);
        Pattern urlPattern = Pattern.compile(urlRegEx,Pattern.CASE_INSENSITIVE);

        Matcher nameMatcher = namePattern.matcher(inputString);
        Matcher emoticonMatcher = emoticonPattern.matcher(inputString);
        Matcher urlMatcher = urlPattern.matcher(inputString);

        while (nameMatcher.find()) {
            String name = nameMatcher.group();
            contentsHolder.addMention(name.replaceAll("@","").trim());
        }

        while (emoticonMatcher.find()) {
            String emoticon = emoticonMatcher.group();
            contentsHolder.addEmoticon(emoticon.replaceAll("\\(","").replaceAll("\\)","").trim());
        }

        while (urlMatcher.find()) {
            URLHolder urlHolder = new URLHolder();
            urlHolder.setUrl(urlMatcher.toMatchResult().group().trim());
            contentsHolder.addLink(urlHolder);
        }

        mParserContentsListener.onParsingComplete(contentsHolder);
    }



}
