package com.paysys.indMojaloopCustomer.api;

import com.paysys.indMojaloopCustomer.BuildConfig;
import com.paysys.indMojaloopCustomer.interceptors.AuthTokenInterceptor;
import com.paysys.indMojaloopCustomer.interceptors.BodyTokenInterceptor;
import com.paysys.indMojaloopCustomer.utils.Constants;

import java.util.concurrent.TimeUnit;

import okhttp3.CertificatePinner;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.paysys.indMojaloopCustomer.utils.Constants.CONNECT_TIMEOUT;
import static com.paysys.indMojaloopCustomer.utils.Constants.READ_TIMEOUT;

/**
 * Created by Dell on 12/31/2016.
 */

public class MojaloopService {

    private static MojaLoopApi api;
    private static MojaLoopPublicApi publicApi;
    private static AuthTokenInterceptor autenticationInterceptor = new AuthTokenInterceptor();

    private static BodyTokenInterceptor bodyTokenInterceptor = new BodyTokenInterceptor();

    public static MojaLoopApi getApi() {
        if (api == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

            if (BuildConfig.DEBUG)
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            else
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            CertificatePinner certificatePinner = null;

            /*if (Constants.SERVER_TYPE == 1) { // Live number change live(0)
                certificatePinner = new CertificatePinner.Builder()
                        .add(Constants.BASE_URL, "sha256/+8woArQN4IWyZ4o5NvgKXXaadavuqXMghSk4c5d91LQ=")
                        .add(Constants.BASE_URL, "sha256/l2yRLumd//mpm/WYi63mkNBZrNOpv+iqbuuqZHz2FZk=")
                        .add(Constants.BASE_URL, "sha256/h6801m+z8v3zbgkRHpq6L29Esgfzhj89C1SyUCOQmqU=")
                        .build();
            }

            else if (Constants.SERVER_TYPE == 0) { // STAGING OR UAT number change UAT (1)
                certificatePinner = new CertificatePinner.Builder()
                        .add(Constants.BASE_URL, "sha256/aR8gwjGp2IISfUuobEUJkIAfMag7b2J113pHiK9OkUc=")
                        .add(Constants.BASE_URL, "sha256/5kJvNEMw0KjrCAu7eXY5HZdvyCS13BbA0VJG1RSP91w=")
                        .add(Constants.BASE_URL, "sha256/r/mIkG3eEpVdm+u/ko/cwxzOMo1bk4TyHIlByibiA5E=")
                        .build();
            }*/

            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                    .addInterceptor(autenticationInterceptor)
                    .addInterceptor(logging);

            /*if (certificatePinner != null)
                builder.certificatePinner(certificatePinner);*/

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://" + Constants.BASE_URL+"/" )
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(builder.build())
                    .build();

            api = retrofit.create(MojaLoopApi.class);
        }

        return api;
    }

    public static MojaLoopPublicApi getPublicApi() {
        if (publicApi == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

            if (BuildConfig.DEBUG)
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            else
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            CertificatePinner certificatePinner = null;

            /* if (Constants.SERVER_TYPE == 1) { // Live
                certificatePinner = new CertificatePinner.Builder()
                        .add(Constants.BASE_URL, "sha256/+8woArQN4IWyZ4o5NvgKXXaadavuqXMghSk4c5d91LQ=")
                        .add(Constants.BASE_URL, "sha256/l2yRLumd//mpm/WYi63mkNBZrNOpv+iqbuuqZHz2FZk=")
                        .add(Constants.BASE_URL, "sha256/h6801m+z8v3zbgkRHpq6L29Esgfzhj89C1SyUCOQmqU=")
                        .build();
            }

            else if (Constants.SERVER_TYPE == 0) { // STAGING OR UAT
                certificatePinner = new CertificatePinner.Builder()
                        .add(Constants.BASE_URL, "sha256/aR8gwjGp2IISfUuobEUJkIAfMag7b2J113pHiK9OkUc=")
                        .add(Constants.BASE_URL, "sha256/5kJvNEMw0KjrCAu7eXY5HZdvyCS13BbA0VJG1RSP91w=")
                        .add(Constants.BASE_URL, "sha256/r/mIkG3eEpVdm+u/ko/cwxzOMo1bk4TyHIlByibiA5E=")
                        .build();
            }*/

            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                    .addInterceptor(logging);

            /*if (certificatePinner != null)
                builder.certificatePinner(certificatePinner);*/

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://" + Constants.BASE_URL+"/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(builder.build())
                    .build();

            publicApi = retrofit.create(MojaLoopPublicApi.class);
        }
        return publicApi;
    }

    public static void updateServiceWithToken(final String token) {
        autenticationInterceptor.setToken(token);
    }


    public static void updateServiceBodyToken(final String token) {
        bodyTokenInterceptor.setToken(token);
    }
}

