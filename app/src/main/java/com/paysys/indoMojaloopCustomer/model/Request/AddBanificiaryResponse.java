package com.paysys.indMojaloopCustomer.model.Request;

public class AddBanificiaryResponse {
        private String displayMessage;

        public String getDisplayMessage ()
        {
            return displayMessage;
        }

        public void setDisplayMessage (String displayMessage)
        {
            this.displayMessage = displayMessage;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [displayMessage = "+displayMessage+"]";
        }
}
