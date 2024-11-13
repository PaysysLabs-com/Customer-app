package com.paysys.indMojaloopCustomer.model.Request;

public class GenerateOtpRequest {

    private String deviceToken;
    private String body;
    private String message;
    private String title;

    public GenerateOtpRequest(String deviceToken, String body, String message, String title) {
        this.deviceToken = deviceToken;
        this.body = body;
        this.message = message;
        this.title = title;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getBody ()
    {
        return body;
    }

    public void setBody (String body)
    {
        this.body = body;
    }

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    @Override
    public String toString()
    {
        return "GenerateOtpRequest [deviceToken = "+deviceToken+", body = "+body+", message = "+message+", title = "+title+"]";
    }
}
