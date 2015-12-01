package au.com.sample.jsonconvertor.parser;

import junit.framework.TestCase;

import au.com.sample.jsonconvertor.dto.ContentsHolder;

/**
 * Created by priju.jacobpaul on 1/12/15.
 */
public class InputParserTest extends TestCase {

    private InputParser mInputParser;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mInputParser = new InputParser();
    }


    public void testParseStringWithOnlyMentions() throws Exception {

        // Test Parse String
        mInputParser.parseString("Good morning!  @John", new InputParser.InputParserContentsListener() {
            @Override
            public void onParsingComplete(ContentsHolder contentsHolder) {
                assertEquals(contentsHolder.getMentionsArray().size(),1);
                assertEquals(contentsHolder.getMentionsArray().get(0),"John");
                assertNull(contentsHolder.getEmoticonsArray());
                assertNull(contentsHolder.getLinksArray());
            }
        });
    }

    public void testParseStringWithOnlyEmoticons() throws Exception {
        // Test Parse String
        mInputParser.parseString("Good morning!  what do you like? (coffee), (tea) or (softdrinks)?  ", new InputParser.InputParserContentsListener() {
            @Override
            public void onParsingComplete(ContentsHolder contentsHolder) {
                assertEquals(contentsHolder.getEmoticonsArray().size(),3);
                assertEquals(contentsHolder.getEmoticonsArray().get(0),"coffee");
                assertEquals(contentsHolder.getEmoticonsArray().get(1),"tea");
                assertEquals(contentsHolder.getEmoticonsArray().get(2),"softdrinks");
                assertEquals(contentsHolder.getLinksArray(),null);
                assertEquals(contentsHolder.getMentionsArray(),null);
            }
        });
    }


    public void testParseStringWithOnlyLinks() throws Exception {
        // Test Parse String
        mInputParser.parseString("Good morning!  Did you watch AllBlacks play? What a game. Check the highlights at www.allblacks.com or check this allblacks.com.nz", new InputParser.InputParserContentsListener() {
            @Override
            public void onParsingComplete(ContentsHolder contentsHolder) {
                assertEquals(contentsHolder.getLinksArray().size(),2);
                assertEquals(contentsHolder.getLinksArray().get(0).getUrl(),"www.allblacks.com");
                assertEquals(contentsHolder.getLinksArray().get(1).getUrl(),"allblacks.com.nz");
                assertEquals(contentsHolder.getEmoticonsArray(),null);
                assertEquals(contentsHolder.getMentionsArray(),null);
            }
        });
    }

    public void testParseStringWithMentionsEmoticonsLinks() throws Exception {
        // Test Parse String
        mInputParser.parseString("GDay @Paul. Grab me a (coffee) or (tea) please. Did you watch AllBlacks play? Check the highlights at http://allblacks.com or check this www.allblacks.com.nz", new InputParser.InputParserContentsListener() {
            @Override
            public void onParsingComplete(ContentsHolder contentsHolder) {
                assertEquals(contentsHolder.getLinksArray().size(),2);
                assertEquals(contentsHolder.getLinksArray().get(0).getUrl(),"http://allblacks.com");
                assertEquals(contentsHolder.getLinksArray().get(1).getUrl(),"www.allblacks.com.nz");
                assertEquals(contentsHolder.getEmoticonsArray().size(),2);
                assertEquals(contentsHolder.getEmoticonsArray().get(0),"coffee");
                assertEquals(contentsHolder.getEmoticonsArray().get(1),"tea");
                assertEquals(contentsHolder.getMentionsArray().size(),1);
                assertEquals(contentsHolder.getMentionsArray().get(1),"paul");
            }
        });
    }

}