package com.paysys.indMojaloopCustomer.interceptors;



import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Qasim on 7/17/2017.
 */

public class AuthTokenInterceptor implements Interceptor {

    private static final String TOKEN_HEADER = "X-Auth-Token";
    private static final String NEXT_TOKEN_HEADER = "X-Auth-Next-Token";

    private String token;

    public AuthTokenInterceptor() {
        super();
        this.token = null;
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request original = chain.request();

        // if token is not present
        if (token == null)
            return chain.proceed(original);

        // updated request with authentication token
        Request.Builder requestBuilder = original.newBuilder().header(TOKEN_HEADER, token);
        Request request = requestBuilder.build();
        Response response = chain.proceed(request);

        String token = response.header(NEXT_TOKEN_HEADER);
        if (token != null)
            setToken(token);

        return response;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void resetToken() {
        this.token = null;
    }
}
