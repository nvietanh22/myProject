package vn.lottefinance.pdms_core.component;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import vn.lottefinance.pdms_core.exception.RestException;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class HttpClient {
    OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(180, TimeUnit.SECONDS)
            .readTimeout(180, TimeUnit.SECONDS)
            .writeTimeout(180, TimeUnit.SECONDS)
            .callTimeout(180, TimeUnit.SECONDS)
            .build();

    public String get (String url, Headers headers){
        log.info("==== >> Call API Url: {}", url);
        Request request = new Request.Builder()
                .url(url)
                .headers(headers)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String resultCall = response.body().string();
            log.info("Response from API: {}", resultCall);
            return resultCall;
        } catch (Exception e) {
            log.error("Error call API: {}", e.getMessage());
            throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }

    public String post(String url, Headers headers, String requestBody) {
        log.info("==== >> Call API Url: {}", url);
        log.info("==== >> Request body :  {}", requestBody);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), requestBody);

        Request request = null;
        if (headers != null) {
            request = new Request.Builder()
                    .url(url)
                    .headers(headers)
                    .post(body)
                    .build();
        } else {
            request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
        }

        try (Response response = client.newCall(request).execute()) {
            String resultCall = response.body().string();
            log.info("Response from API: {}", resultCall);
            return resultCall;

        } catch (Exception e) {
            log.error("Error call API: {}", e.getMessage());
            throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }

    public Object put(String url, Headers headers, String requestBody){
        log.info("==== >> Call API Url: {}", url);
        log.info("==== >> Request body :  {}", requestBody);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), requestBody);
        Request request = new Request.Builder()
                .url(url)
                .headers(headers)
                .put(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String resultCall = response.body().string();
            log.info("Response from API: {}", resultCall);
            return resultCall;
        } catch (Exception e) {
            log.error("Error call API: {}", e.getMessage());
            throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }

    public String delete(String url, Headers headers) throws IOException {
        log.info("==== >> Call API Url: {}", url);

        Request request = new Request.Builder()
                .url(url)
                .headers(headers)
                .delete()
                .build();

        try (Response response = client.newCall(request).execute()) {
            String resultCall = response.body().string();
            log.info("Response from API: {}", resultCall);
            return  resultCall;
        } catch (Exception e) {
            log.error("Error call API: {}", e.getMessage());
            throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }
}
