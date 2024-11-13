package com.paysys.indMojaloopCustomer.model.Respose;

public class CreditUnionListRespose {
        private CreditUnionList[] creditUnionList;

        public CreditUnionList[] getCreditUnionList ()
        {
            return creditUnionList;
        }

        public void setCreditUnionList (CreditUnionList[] creditUnionList)
        {
            this.creditUnionList = creditUnionList;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [creditUnionList = "+creditUnionList+"]";
        }

}
