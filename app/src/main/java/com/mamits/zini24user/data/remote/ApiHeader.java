package com.mamits.zini24user.data.remote;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.inject.Singleton;

@Singleton
public class ApiHeader {


    public static final class  ProtectedApiHeader{

        @Expose
        @SerializedName("access_token")
        private String access_token;


        public ProtectedApiHeader(String access_token) {
            this.access_token = access_token;
        }

        public String getAccess_token() {
            return access_token;
        }

        public void setAccess_token(String access_token) {
            this.access_token = access_token;
        }
    }


    public static final class PublicApiHeader{

        @Expose
        @SerializedName("access_token")
        private String access_token;


        public PublicApiHeader(String access_token) {
            this.access_token = access_token;
        }

        public String getAccess_token() {
            return access_token;
        }

        public void setAccess_token(String access_token) {
            this.access_token = access_token;
        }
    }

}
