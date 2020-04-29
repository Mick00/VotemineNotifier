package com.votemine;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.votemine.models.MineVote;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class API {

    private String bearerToken;

    public API(String bearerToken){
        this.bearerToken = bearerToken;
    }

    public InputStream getStream(String path) throws IOException {
        String httpsURL = "https://votemine.com/api/"+path;
        URL apiUrl = new URL(httpsURL);
        HttpsURLConnection conn = (HttpsURLConnection) apiUrl.openConnection();
        conn.setRequestProperty("Accept","application/json");
        conn.setRequestProperty("Authorization","Bearer "+bearerToken);
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        return conn.getInputStream();
    }

    public String call(String path) throws IOException {
        InputStreamReader isr = new InputStreamReader(getStream(path));
        BufferedReader br = new BufferedReader(isr);
        String inputLine;
        StringBuilder buffer = new StringBuilder();
        while ((inputLine = br.readLine()) != null) {
            buffer.append(inputLine);
        }
        return buffer.toString();
    }

    public List<MineVote> getNewVotes() throws IOException {
        APIResponse<List<MineVote>> response = getResponseList("v1/myserver/newvotes", MineVote.class);
        return response.getData();
    }

    private <T> APIResponse<List<T>> getResponseList(String url, Class<T> clazz) throws IOException {
        InputStream stream = this.getStream(url);
        ObjectMapper mapper = new ObjectMapper();
        JavaType data = mapper.getTypeFactory().constructCollectionType(List.class, clazz);
        JavaType responseType = mapper.getTypeFactory().constructParametricType(APIResponse.class,data);
        return mapper.readValue(stream, responseType);
    }

    private <T> APIResponse<T> getResponse(String url, Class<T> clazz) throws IOException {
        InputStream stream = this.getStream(url);
        ObjectMapper mapper = new ObjectMapper();
        JavaType responseType = mapper.getTypeFactory().constructParametricType(APIResponse.class, clazz);
        return mapper.readValue(stream, responseType);
    }

    public void setBearerToken(String token){
        this.bearerToken = token;
    }
}
