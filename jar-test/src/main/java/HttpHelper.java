
/**
 * @author LvLiuWei
 * @date 2018/2/6
 */

import com.google.common.base.CharMatcher;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.turbomanage.httpclient.BasicHttpClient;
import com.turbomanage.httpclient.ConsoleRequestLogger;
import com.turbomanage.httpclient.HttpMethod;
import com.turbomanage.httpclient.HttpResponse;
import com.turbomanage.httpclient.RequestLogger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpHelper {
    private static final String TAG = "HttpHelper";

    private static final String BASIC = "Basic ";
    private static final String DIGEST = "Digest ";

    private static final String NONCE = "nonce";
    private static final String QOP = "qop";
    private static final String REALM = "realm";
    private static final String OPAQUE = "opaque";

    private static final String USERNAME = "username";
    private static final String NC = "nc";
    private static final String nc = "00000001";
    private static final String CNONCE = "cnonce";
    private static final String cnonce = "0a4f113b";
    private static final String RESPONSE = "response";
    private static final String URI = "uri";

    // URL of the remote service
    private String mUri = null;

    private String mRemoteService = null;

    private String mTimestamp = null;

    private String mUsername = null;

    private String mPassword = null;

    private Context mContext = null;

    Callback mCallback = null;

    static {
        // HTTP connection reuse which was buggy pre-froyo
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.FROYO) {
            System.setProperty("http.keepAlive", "false");
        } else {
            System.setProperty("http.keepAlive", "true");
        }
    }

    private RequestLogger mQuietLogger = new ConsoleRequestLogger();

    public HttpHelper(String uri, Context context) {
        mUri = uri;
        mContext = context;
        mRemoteService = "Server IP" + mUri;
    }

    public HttpHelper(String url, String timestamp, Context context) {
        this(url, context);
        mTimestamp = timestamp;
    }

    public HttpHelper(String url, String username, String password, Context context) {
        this(url, context);
        mUsername = username;
        mPassword = password;
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    private void fetchHttpResponse(HttpResponse httpResponse) throws IOException {
        int status = httpResponse.getStatus();
        if (status == HttpURLConnection.HTTP_OK) {
            String str = httpResponse.getBodyAsString();
            mCallback.onSuccess(str);
        } else if (status == HttpURLConnection.HTTP_UNAUTHORIZED) {
            //服务器获得401响应，并解析WWW-Authenticate header的类型
            String str = httpResponse.getBodyAsString();
            mCallback.onUnauthorized(str);
            Map<String, List<String>> headers = httpResponse.getHeaders();
            List<String> wwwAuthenticate = headers.get("WWW-Authenticate");
            String auth = wwwAuthenticate.get(0);
            if (auth == null) {
            }
            // Digest
            if (auth.startsWith(DIGEST.trim())) {
                //这里实现Digest Auth逻辑
                HashMap<String, String> authFields = splitAuthFields(auth.substring(7));
                Joiner colonJoiner = Joiner.on(':');
                String A1 = null; //A1 = MD5("usarname:realm:password");
                String A2 = null; //A2 = MD5("httpmethod:uri");
                String response = null; //response = MD5("A1:nonce:nc:cnonce:qop:A2");

                MessageDigest md5 = null;
                try {
                    md5 = MessageDigest.getInstance("MD5");
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                try {
                    md5.reset();
                    String A1Str = colonJoiner.join(mUsername, authFields.get(REALM), mPassword);
                    md5.update(A1Str.getBytes("ISO-8859-1"));
                    A1 = bytesToHexString(md5.digest());
                } catch (UnsupportedEncodingException e) {
                }
                try {
                    md5.reset();
                    String A2Str = colonJoiner.join(HttpMethod.GET.toString(), mUri);
                    md5.update(A2Str.getBytes("ISO-8859-1"));
                    A2 = bytesToHexString(md5.digest());
                } catch (UnsupportedEncodingException e) {
                }
                try {
                    md5.reset();
                    String A2Str = colonJoiner.join(A1, authFields.get(NONCE), nc, cnonce, authFields.get(QOP), A2);
                    md5.update(A2Str.getBytes("ISO-8859-1"));
                    response = bytesToHexString(md5.digest());
                } catch (UnsupportedEncodingException e) {
                }
                // 拼接 Authorization Header,格式如 Digest username="admin",realm="Restricted area",nonce="554a3304805fe",qop=auth,opaque="cdce8a5c95a1427d74df7acbf41c9ce0", nc=00000001,response="391bee80324349ea1be02552608c0b10",cnonce="0a4f113b",uri="/MyBlog/home/Response/response_last_modified"
                StringBuilder sb = new StringBuilder();
                sb.append(DIGEST);
                sb.append(USERNAME).append("=\"").append(mUsername).append("\",");
                sb.append(REALM).append("=\"").append(authFields.get(REALM)).append("\",");
                sb.append(NONCE).append("=\"").append(authFields.get(NONCE)).append("\",");
                sb.append(QOP).append("=").append(authFields.get(QOP)).append(",");
                sb.append(OPAQUE).append("=\"").append(authFields.get(OPAQUE)).append("\",");
                sb.append(NC).append("=").append(nc).append(",");
                sb.append(RESPONSE).append("=\"").append(response).append("\",");
                sb.append(CNONCE).append("=\"").append(cnonce).append("\",");
                sb.append(URI).append("=\"").append(mUri).append("\"");
                //再请求一次
                BasicHttpClient httpClient = new BasicHttpClient();
                httpClient.setRequestLogger(mQuietLogger);

                if (mTimestamp != null && !TextUtils.isEmpty(mTimestamp)) {
                    if (TimeUtils.isValidFormatForIfModifiedSinceHeader(mTimestamp)) {
                        httpClient.addHeader("If-Modified-Since", mTimestamp);
                    } else {
                        Log.w(TAG, "Could not set If-Modified-Since HTTP header. Potentially downloading " +
                                "unnecessary data. Invalid format of refTimestamp argument: " + mTimestamp);
                    }
                }
                httpClient.addHeader("Authorization", sb.toString());
                fetchHttpResponse(httpClient.get(mRemoteService, null));
            } else if (auth.startsWith(BASIC.trim())) { // Basic
                //这里实现Basic Auth逻辑
            }
        } else if (status == HttpURLConnection.HTTP_NOT_MODIFIED) {
            Log.d(TAG, "HTTP_NOT_MODIFIED: data has not changed since " + mTimestamp);
        } else {
            Log.e(TAG, "Error fetching conference data: HTTP status " + status);
            throw new IOException("Error fetching conference data: HTTP status " + status);
        }
    }

    public void fetchHttpData() throws IOException {
        if (TextUtils.isEmpty(mUri)) {
            Log.w(TAG, "Manifest URL is empty.");
        }
        BasicHttpClient httpClient = new BasicHttpClient();
        httpClient.setRequestLogger(mQuietLogger);

        if (mTimestamp != null && !TextUtils.isEmpty(mTimestamp)) {
            if (TimeUtils.isValidFormatForIfModifiedSinceHeader(mTimestamp)) {
                httpClient.addHeader("If-Modified-Since", mTimestamp);
            } else {
                Log.w(TAG, "Could not set If-Modified-Since HTTP header. Potentially downloading " +
                        "unnecessary data. Invalid format of refTimestamp argument: " + mTimestamp);
            }
        }

        HttpResponse response = httpClient.get(mRemoteService, null);
        fetchHttpResponse(response);
    }

    private static HashMap<String, String> splitAuthFields(String authString) {
        final HashMap<String, String> fields = Maps.newHashMap();
        final CharMatcher trimmer = CharMatcher.anyOf("\"\t ");
        final Splitter commas = Splitter.on(',').trimResults().omitEmptyStrings();
        final Splitter equals = Splitter.on('=').trimResults(trimmer).limit(2);
        String[] valuePair;
        for (String keyPair : commas.split(authString)) {
            valuePair = Iterables.toArray(equals.split(keyPair), String.class);
            fields.put(valuePair[0], valuePair[1]);
        }
        return fields;
    }

    private static final String HEX_LOOKUP = "0123456789abcdef";

    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(HEX_LOOKUP.charAt((bytes[i] & 0xF0) >> 4));
            sb.append(HEX_LOOKUP.charAt((bytes[i] & 0x0F) >> 0));
        }
        return sb.toString();
    }


    public static interface Callback {
        void onSuccess(String result);

        void onUnauthorized(String result);
    }

}
