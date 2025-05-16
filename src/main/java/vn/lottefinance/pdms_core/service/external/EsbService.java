package vn.lottefinance.pdms_core.service.external;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.lottefinance.pdms_core.properties.EsbProperties;
import vn.lottefinance.pdms_core.service.external.dto.esb.EsbDTO;

import java.util.Base64;
import java.util.Map;
import java.util.UUID;


@Service
@Slf4j
public class EsbService {
    @Autowired
    private EsbProperties esbProperties;

    private final OkHttpClient httpClient = new OkHttpClient();


    public EsbDTO.EmailResponse sendEmail(EsbDTO.EmailRequest request) {
        JsonObject body = new JsonObject();
        String transId = UUID.randomUUID().toString();
        body.addProperty("TransId", transId);

        JsonObject data = new JsonObject();
        data.addProperty("emailTo", request.getMailTo());
        data.addProperty("templateId", request.getTemplateId());

        JsonObject subject = new JsonObject();
        subject.addProperty("title", request.getTitle());
        data.add("subject", subject);

        JsonObject contentData = new JsonObject();
        if (!request.getData().isEmpty()) {
            for (Map.Entry<String, String> entry : request.getData().entrySet()) {
                contentData.addProperty(entry.getKey(), entry.getValue());
            }
            data.add("data", contentData);
        }

        if (!request.getAttachment().isEmpty()) {
            JsonObject attachItem = new JsonObject();
            for (Map.Entry<String, String> entry : request.getAttachment().entrySet()) {
                attachItem.addProperty(entry.getKey(), entry.getValue());
            }
            JsonArray attachments = new JsonArray();
            attachments.add(attachItem);

            data.add("assetAttachments", attachments);
        }

        body.add("Data", data);

        RequestBody httpBody = RequestBody.create(MediaType.parse("application/json"), body.toString());

        Request requestEsb = new Request.Builder()
                .url(esbProperties.getBaseUrl() + esbProperties.getServices().getNotification().getEmail())
                .post(httpBody)
                .header("Authorization", "Basic " + new String(Base64.getEncoder().encode((esbProperties.getCredential().getUser() + ":" + esbProperties.getCredential().getPass()).getBytes())))
                .build();

        log.info("Start send email via ESB for {}", request.getMailTo());
        log.info("Endpoint: {}", esbProperties.getBaseUrl() + esbProperties.getServices().getNotification().getEmail());
        log.info("Body: {}", body.toString());

        try (Response response = httpClient.newCall(requestEsb).execute()) {
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                log.info("Response: {}", responseBody);
                Gson gson = new Gson();
                JsonObject resData = gson.fromJson(responseBody, JsonObject.class);

                String errorCode = resData.has("ErrorCode") ? resData.get("ErrorCode").getAsString() : null;
                String errorMsg = resData.has("ErrorMessage") ? resData.get("ErrorMessage").getAsString() : null;
                return EsbDTO.EmailResponse.builder()
                        .errorCode(errorCode)
                        .errorMsg(errorMsg)
                        .build();

            } else {
                log.error("Response error: {}", response.message());
                return EsbDTO.EmailResponse.builder()
                        .errorCode("500")
                        .errorMsg(response.message())
                        .build();

            }
        } catch (Exception ex) {
            log.error("Response error: {}", ex.getMessage());
            return EsbDTO.EmailResponse.builder()
                    .errorCode("500")
                    .errorMsg(ex.getMessage())
                    .build();

        }
    }

    public EsbDTO.ZnsResponse sendZns(EsbDTO.ZnsRequest request) {
        JsonObject body = new JsonObject();
        String transId = UUID.randomUUID().toString();
        body.addProperty("TransId", transId);

        JsonObject data = new JsonObject();
        data.addProperty("Phone", request.getPhone());
        data.addProperty("Channel", request.getChannel());
        data.addProperty("Code", request.getCode());


        JsonObject params = new JsonObject();
        if (!request.getParam().isEmpty()) {
            for (Map.Entry<String, String> entry : request.getParam().entrySet()) {
                params.addProperty(entry.getKey(), entry.getValue());
            }
            data.add("Param", params);
        }
        body.add("Data", data);

        RequestBody httpBody = RequestBody.create(MediaType.parse("application/json"), body.toString());
        Request requestEsb = new Request.Builder()
                .url(esbProperties.getBaseUrl() + esbProperties.getServices().getNotification().getZns())
                .post(httpBody)
                .header("Authorization", "Basic " + new String(Base64.getEncoder().encode((esbProperties.getCredential().getUser() + ":" + esbProperties.getCredential().getPass()).getBytes())))
                .build();

        log.info("Start send zns via ESB for {}", request.getPhone());
        log.info("Endpoint: {}", esbProperties.getBaseUrl() + esbProperties.getServices().getNotification().getEmail());
        log.info("Body: {}", body.toString());

        try (Response response = httpClient.newCall(requestEsb).execute()) {
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                log.info("Response: {}", responseBody);
                Gson gson = new Gson();
                JsonObject resData = gson.fromJson(responseBody, JsonObject.class);

                String errorCode = resData.has("ErrorCode") ? resData.get("ErrorCode").getAsString() : null;
                String errorMsg = resData.has("ErrorMessage") ? resData.get("ErrorMessage").getAsString() : null;
                return EsbDTO.ZnsResponse.builder()
                        .errorCode(errorCode)
                        .errorMsg(errorMsg)
                        .build();

            } else {
                log.error("Response error: {}", response.message());
                return EsbDTO.ZnsResponse.builder()
                        .errorCode("500")
                        .errorMsg(response.message())
                        .build();

            }
        } catch (Exception ex) {
            log.error("Response error: {}", ex.getMessage());
            return EsbDTO.ZnsResponse.builder()
                    .errorCode("500")
                    .errorMsg(ex.getMessage())
                    .build();

        }
    }

    public EsbDTO.SmsResponse sendSms(EsbDTO.SmsRequest request) {
        JsonObject body = new JsonObject();
        String transId = UUID.randomUUID().toString();
        body.addProperty("TransId", transId);

        JsonObject data = new JsonObject();
        data.addProperty("Phone", request.getPhone());
        data.addProperty("Channel", request.getChannel());
        data.addProperty("Message", request.getMessage());

        body.add("Data", data);

        RequestBody httpBody = RequestBody.create(MediaType.parse("application/json"), body.toString());
        Request requestEsb = new Request.Builder()
                .url(esbProperties.getBaseUrl() + esbProperties.getServices().getNotification().getSms())
                .post(httpBody)
                .header("Authorization", "Basic " + new String(Base64.getEncoder().encode((esbProperties.getCredential().getUser() + ":" + esbProperties.getCredential().getPass()).getBytes())))
                .build();

        log.info("Start send sms via ESB for {}", request.getPhone());
        log.info("Endpoint: {}", esbProperties.getBaseUrl() + esbProperties.getServices().getNotification().getEmail());
        log.info("Body: {}", body.toString());

        try (Response response = httpClient.newCall(requestEsb).execute()) {
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                log.info("Response: {}", responseBody);
                Gson gson = new Gson();
                JsonObject resData = gson.fromJson(responseBody, JsonObject.class);

                String errorCode = resData.has("ErrorCode") ? resData.get("ErrorCode").getAsString() : null;
                String errorMsg = resData.has("ErrorMessage") ? resData.get("ErrorMessage").getAsString() : null;
                return EsbDTO.SmsResponse.builder()
                        .errorCode(errorCode)
                        .errorMsg(errorMsg)
                        .build();

            } else {
                log.error("Response error: {}", response.message());
                return EsbDTO.SmsResponse.builder()
                        .errorCode("500")
                        .errorMsg(response.message())
                        .build();

            }
        } catch (Exception ex) {
            log.error("Response error: {}", ex.getMessage());
            return EsbDTO.SmsResponse.builder()
                    .errorCode("500")
                    .errorMsg(ex.getMessage())
                    .build();

        }
    }
}