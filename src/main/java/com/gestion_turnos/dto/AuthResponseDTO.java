package com.gestion_turnos.dto;

public class AuthResponseDTO {

    private String accessToken;
    private String tokenType = "Bearer";
    private long expieresIn;
    private String username;

    public AuthResponseDTO(String accessToken, long expireIn, String username){
        this.accessToken = accessToken;
        this.expieresIn = expireIn;
        this.username = username;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public long getExpieresIn() {
        return expieresIn;
    }

    public String getUsername() {
        return username;
    }

    


}
