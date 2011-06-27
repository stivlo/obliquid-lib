package org.obliquid.util;

import java.io.IOException;
import java.util.concurrent.RejectedExecutionException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Class to download a URL from HTTP. Called Wget in honor of the swiss army knife download tool,
 * but here I don't have any advanced functionality at all.
 * 
 * @author stivlo
 */
public class Wget {

    /**
     * Fetch a URL and return the body
     * 
     * @param url
     *            the url to fetch, may include GET parameters
     * @return the response body
     */
    public static String fetchUrl(String url) {
        HttpClient httpClient = new DefaultHttpClient();
        String responseBody = null;
        try {
            HttpGet httpGet = new HttpGet(url);
            ResponseHandler<String> handler = new BasicResponseHandler();
            responseBody = httpClient.execute(httpGet, handler);
        } catch (ClientProtocolException ex) {
            throw new RejectedExecutionException(ex);
        } catch (IOException ex) {
            throw new RejectedExecutionException(ex);
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
        return responseBody;
    }

}
