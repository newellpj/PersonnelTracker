package co.srsp.rss.handlers;

import java.net.URL;

import org.apache.log4j.Logger;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import co.srsp.rss.model.FeedMessage;

public class RSSHandlerImpl implements RSSHandlerInterface {

	private final static Logger log = Logger.getLogger(RSSHandlerImpl.class); 
	
	//https://www.nasa.gov/rss/dyn/lg_image_of_the_day.rss
	//https://www.nasa.gov/rss/dyn/educationnews.rss
	//https://www.nasa.gov/rss/dyn/breaking_news.rss
	//https://www.nasa.gov/rss/dyn/earth.rss
	//https://www.nasa.gov/rss/dyn/solar_system.rss
	//https://www.nasa.gov/rss/dyn/shuttle_station.rss
	//https://www.nasa.gov/rss/dyn/aeronautics.rss
	
	@Override
	public FeedMessage[] readRSSFeed(String feedUrl) {
		SyndFeedInput input = new SyndFeedInput();
		
		FeedMessage[] feedArr = null;
		FeedMessage feedMsg = null;		
				
		try{
			SyndFeed feed = input.build(new XmlReader(new URL(feedUrl)));
			
			feedArr = new FeedMessage[feed.getEntries().size()];
			
			int count = 0;
			
			for(SyndEntry syndEntry : feed.getEntries()){
				feedArr[count].setAuthor(syndEntry.getAuthor());
				feedArr[count].setTitle(syndEntry.getTitle());
				feedArr[count].setDescription(syndEntry.getDescription().getValue());
				feedArr[count].setLink(syndEntry.getLink());
				
				if(syndEntry.getEnclosures() != null && syndEntry.getEnclosures().size() > 0){
					feedArr[count].setUrl(syndEntry.getEnclosures().get(0).getUrl());
				}
				
				count++;
			}
			
	
			return feedArr;
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
		}
		
		return null;
	}

	@Override
	public FeedMessage[] readRSSFeed() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public static void main(String[] args) {
        boolean ok = false;
     
        try {
            URL feedUrl = new URL("https://www.nasa.gov/rss/dyn/lg_image_of_the_day.rss");

            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(feedUrl));

            System.out.println("The feed is "+feed);
            System.out.println("The feed is "+feed.getEntries().size());
            
            
            ok = true;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("ERROR: "+ex.getMessage());
        }
        

        if (!ok) {
            System.out.println();
            System.out.println("FeedReader reads and prints any RSS/Atom feed type.");
            System.out.println("The first parameter must be the URL of the feed to read.");
            System.out.println();
        }
    }
}
