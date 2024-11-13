package com.paysys.indMojaloopCustomer.interceptors;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

public class BodyTokenInterceptor implements Interceptor {
    //private Context context; //This is here because I needed it for some other cause
    private String token;
    //private static final String TOKEN_IDENTIFIER = "token_id";
   /* public BodyTokenInterceptor(Context context) {
        this.context = context;
    }*/

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        RequestBody requestBody = request.body();
        //String token = "toku";//whatever or however you get it.
        assert requestBody != null;
        try{
            if (requestBody.contentType() != null && requestBody.contentType().subtype() != null) {
                String subtype = requestBody.contentType().subtype();
                if (subtype.contains("json")) {
                    requestBody = processApplicationJsonRequestBody(requestBody, token);
                } else if (subtype.contains("form")) {
                    requestBody = processFormDataRequestBody(requestBody, token);
                }
                if (requestBody != null) {
                    Request.Builder requestBuilder = request.newBuilder();
                    request = requestBuilder
                            .post(requestBody)
                            .build();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return chain.proceed(request);
    }
    private String bodyToString(final RequestBody request){
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            if(copy != null)
                copy.writeTo(buffer);
            else
                return "";
            return buffer.readUtf8();
        }
        catch (final IOException e) {
            return "did not work";
        }
    }
    private RequestBody processApplicationJsonRequestBody(RequestBody requestBody,String token){
        String customReq = bodyToString(requestBody);
        try {
            JSONObject obj = new JSONObject(customReq);
            obj.put("token", token);
            return RequestBody.create(requestBody.contentType(), obj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    private RequestBody processFormDataRequestBody(RequestBody requestBody, String token){
        RequestBody formBody = new FormBody.Builder()
                .add("token", token)
                .build();
        String postBodyString = bodyToString(requestBody);
        postBodyString += ((postBodyString.length() > 0) ? "&" : "") +  bodyToString(formBody);
        return RequestBody.create(requestBody.contentType(), postBodyString);
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void resetToken() {
        this.token = null;
    }

}
