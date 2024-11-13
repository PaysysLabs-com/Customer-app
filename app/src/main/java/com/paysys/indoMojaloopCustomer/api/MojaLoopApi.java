package com.paysys.indMojaloopCustomer.api;

import com.paysys.indMojaloopCustomer.model.Request.AddBanificiaryResponse;
import com.paysys.indMojaloopCustomer.model.Request.AddBeneficiaryRequest;
import com.paysys.indMojaloopCustomer.model.Request.BalanceInquiryRequest;
import com.paysys.indMojaloopCustomer.model.Request.TitleFetchRequest;
import com.paysys.indMojaloopCustomer.model.Request.TransactionRequest;
import com.paysys.indMojaloopCustomer.model.Respose.BalanceInquiryResponse;
import com.paysys.indMojaloopCustomer.model.Respose.BeneficiaryDetailsListResponse;
import com.paysys.indMojaloopCustomer.model.Respose.GenericResponse;
import com.paysys.indMojaloopCustomer.model.Respose.TitleFetchResponse;
import com.paysys.indMojaloopCustomer.model.Respose.TransactionResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface MojaLoopApi {

    @POST("api/v1/my/balanceInquiry")
    Call<GenericResponse<BalanceInquiryResponse>>balanceInquiry(@Body BalanceInquiryRequest request);

    @POST("api/v1/beneficiary/addBenficiary")
    Call<GenericResponse<AddBanificiaryResponse>>addbeneficiary(@Body AddBeneficiaryRequest request);

    @GET("api/v1/beneficiary/getBenficiary")
    Call<GenericResponse<BeneficiaryDetailsListResponse>>getbeneficiaryList();

    @POST("api/v1/my/titleFetch")
    Call<GenericResponse<TitleFetchResponse>>titleFetch(@Body TitleFetchRequest request);

    @POST("api/v1/transfer/merchantTransfer")
    Call<GenericResponse<TransactionResponse>>merchantTransfer(@Body TransactionRequest request);

    @POST("api/v1/transfer/p2pTransfer")
    Call<GenericResponse<TransactionResponse>>p2pTransfer(@Body TransactionRequest request);

}
