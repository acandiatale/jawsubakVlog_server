package com.maetdolvlog.maetdolvlog.translate;

import com.google.api.gax.paging.Page;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import com.google.common.collect.Lists;
import com.maetdolvlog.maetdolvlog.entity.TranslateService;
import com.maetdolvlog.maetdolvlog.entity.TranslateServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.http.HttpRequest;

public class GoogleTranslateAPITest {

    @Test
    @DisplayName("Google")
    void getApi() throws IOException {
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream("/Users/acandiatale/translate-credential.json"))
                .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();

        System.out.println("Buckets: ");
        Page<Bucket> buckets = storage.list();
        for (Bucket bucket : buckets.iterateAll()) {
            System.out.println(bucket.toString());
        }
    }

    @Test
    @DisplayName("Create Bucket test")
    void createBucket() {
        Storage storage = StorageOptions.getDefaultInstance().getService();

        String bucketName = "jawsubak-bucket";

        Bucket bucket = storage.create(BucketInfo.of(bucketName));
        System.out.println("Bucket created : " + bucket.getName());
    }

    @Test
    @DisplayName("Test for APIKey")
    void requestAPI() {
        Translate translate = TranslateOptions.getDefaultInstance().toBuilder().setQuotaProjectId("translateejk").setTargetLanguage("ja").build().getService();
        Translation translation = translate.translate("¡Hola Mundo!");
        System.out.println(translation.getTranslatedText());
    }

    @Test
    @DisplayName("GetSourceLanguageTest")
    void getSourceLanguageTest() {
        Translate translate = TranslateOptions.getDefaultInstance().toBuilder().setQuotaProjectId("translateejk").setTargetLanguage("ko").build().getService();
        Translation translation = translate.translate("おはよう");
        System.out.println(translation.getSourceLanguage());
    }

    @Test
    @DisplayName("Test TranslateService")
    void translateServiceTest() {
        TranslateService translateService = new TranslateServiceImpl("translateejk");
        System.out.println(translateService.translateText("Defining routes for the HTTP server requires configuring the provided HttpServerRoutes builder."));

    }
}
