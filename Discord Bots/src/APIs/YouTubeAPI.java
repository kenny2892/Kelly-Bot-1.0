package APIs;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Thumbnail;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class YouTubeAPI 
{
	private static YouTubeAPI INSTANCE;
	public static final String KEY = "Your YouTube API Key Here!";
	private static final long NUMBER_OF_VIDEOS_RETURNED = 1;
	private static YouTube youtube;
	
	private YouTubeAPI()
	{
        // This object is used to make YouTube Data API requests. The last
        // argument is required, but since we don't need anything
        // initialized when the HttpRequest is initialized, we override
        // the interface and provide a no-op function.
        youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, new HttpRequestInitializer() 
        {
            public void initialize(HttpRequest request) throws IOException 
            {
            	
            }
        }).setApplicationName("discord-bot-yt-search").build();
	}
	
	public static synchronized YouTubeAPI getInstance()
	{
		if(INSTANCE == null)
			INSTANCE = new YouTubeAPI();
		
		return INSTANCE;
	}
	
	public YouTubeVideo getSearchResult(String searchTerms)
	{
		try 
		{
            // Define the API request for retrieving search results.
            YouTube.Search.List search = youtube.search().list("id,snippet");

            // Set your developer key from the {{ Google Cloud Console }} for
            // non-authenticated requests. See:
            // {{ https://cloud.google.com/console }}
            search.setKey(KEY);
            search.setQ(searchTerms);

            // Restrict the search results to only include videos. See:
            // https://developers.google.com/youtube/v3/docs/search/list#type
            search.setType("video");

            // To increase efficiency, only retrieve the fields that the
            // application uses.
            search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
            search.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);

            // Call the API and print results.
            SearchListResponse searchResponse = search.execute();
            List<SearchResult> searchResultList = searchResponse.getItems();
            
            if (searchResultList != null) 
            {
                return getResults(searchResultList.iterator(), searchTerms);
            }
        } 
		
		catch (GoogleJsonResponseException e) 
		{
            System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
        } 
		
		catch (IOException e) 
		{
            System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
        } 
		
		catch (Throwable t) 
		{
            t.printStackTrace();
        }
		
		return null;
	}

	private static YouTubeVideo getResults(Iterator<SearchResult> iteratorSearchResults, String query) 
	{
		if (!iteratorSearchResults.hasNext()) 
			return null;

		SearchResult singleVideo = iteratorSearchResults.next();
		ResourceId rId = singleVideo.getId();

		// Confirm that the result represents a video. Otherwise, the
		// item will not contain a video ID.
		if (rId.getKind().equals("youtube#video")) 
		{
			Thumbnail thumbnail = singleVideo.getSnippet().getThumbnails().getDefault();
			
			String url = "https://www.youtube.com/watch?v=" + rId.getVideoId(); // Source: https://stackoverflow.com/questions/22209128/how-to-get-url-of-a-video-using-youtube-api-v3-using-java
			String title = singleVideo.getSnippet().getTitle();
			String desc = singleVideo.getSnippet().getDescription();
			String thumbURL = thumbnail.getUrl();

			return new YouTubeVideo(title, desc, url, thumbURL);
		}
		
		return null;
	}
}
