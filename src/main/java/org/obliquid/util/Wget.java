package org.obliquid.util;

import java.io.IOException;
import java.util.concurrent.RejectedExecutionException;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Class to download a URL from HTTP. Called Wget in honour of the swiss army
 * knife download tool, although here I don't have any advanced functionality at
 * all.
 * 
 * @author stivlo
 */
public final class Wget {

        /** SSL port number. */
        private static final int SSL_PORT = 443;

        /**
         * Utility class.
         */
        private Wget() {
        }

        /**
         * Fetch an URL and return the body.
         * 
         * @param url
         *                the URL to fetch, may include GET parameters
         * @return the response body
         */
        public static String fetchUrl(final String url) {
                HttpClient httpClient = new DefaultHttpClient();
                return fetchUrl(url, httpClient);

        }

        /**
         * Fetch an URL without checking the certificate.
         * 
         * @param url
         *                the URL to fetch, may include GET parameters
         * @return the response body
         */
        public static String fetchTrustedUrl(final String url) {
                HttpClient httpClient = wrapHttpClient(new DefaultHttpClient());
                return fetchUrl(url, httpClient);
        }

        /**
         * Fetch an URL with the provided HttpClient.
         * 
         * @param url
         *                the URL to fetch, may include GET parameters
         * @param httpClient
         *                the HttpClient to use
         * @return the response body
         */
        private static String fetchUrl(final String url, final HttpClient httpClient) {
                String responseBody = null;
                try {
                        HttpGet httpGet = new HttpGet(url);
                        ResponseHandler<String> handler = new BasicResponseHandler();
                        responseBody = httpClient.execute(httpGet, handler);
                } catch (ClientProtocolException ex) {
                        throw new RejectedExecutionException(ex);
                } catch (IOException ex) {
                        throw new RejectedExecutionException(ex);
                } catch (IllegalStateException ex) {
                        throw new IllegalArgumentException(ex);
                } finally {
                        httpClient.getConnectionManager().shutdown();
                }
                return responseBody;
        }

        /**
         * Avoiding the javax.net.ssl.SSLPeerUnverifiedException: peer not
         * authenticated with HttpClient.
         * "http://javaskeleton.blogspot.com/2010/07/avoiding-peer-not-authenticated-with.html"
         * 
         * @param base
         *                the HttpClient to wrap with a Null Trust Manager
         * @return a wrapped HttpClient
         */
        @SuppressWarnings("deprecation")
        private static HttpClient wrapHttpClient(final HttpClient base) {
                try {
                        SSLContext ctx = SSLContext.getInstance("TLS");
                        X509TrustManager tm = new X509TrustManager() {

                                @Override
                                public void checkClientTrusted(final X509Certificate[] xcs,
                                                final String string) throws CertificateException {
                                        //no checks, anything goes, method intentionally left blank
                                }

                                @Override
                                public void checkServerTrusted(final X509Certificate[] xcs,
                                                final String string) throws CertificateException {
                                        //no check
                                }

                                @Override
                                public X509Certificate[] getAcceptedIssuers() {
                                        return null;
                                }

                        };
                        ctx.init(null, new TrustManager[] { tm }, null);
                        SSLSocketFactory ssf = new SSLSocketFactory(ctx);
                        ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
                        ClientConnectionManager ccm = base.getConnectionManager();
                        SchemeRegistry sr = ccm.getSchemeRegistry();
                        sr.register(new Scheme("https", ssf, SSL_PORT));
                        return new DefaultHttpClient(ccm, base.getParams());
                } catch (Exception ex) {
                        ex.printStackTrace();
                        return null;
                }
        }

}
