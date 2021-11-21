package org.glsid3.securityservice.security;

public class JWTUTIL {
    public static final String SECRET="mySecret1234";
    public static final String AUTHORIZATION_HEADER="Authorization";
    public static final int EXPIRE_ACCESS_TOKEN=1*60*1000;
    public static final int EXPIRE_REFRESH_TOKEN=15*60*1000;
    public static final String AUTHORIZATION_PREFIX="Bearer ";
}


