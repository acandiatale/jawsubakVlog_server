package com.maetdolvlog.maetdolvlog.entity;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

public class TranslateServiceImpl implements TranslateService{

    private final Translate translate;

    public TranslateServiceImpl(String quotaProjectId) {
        this.translate = TranslateOptions.getDefaultInstance().toBuilder().setQuotaProjectId("translateejk").build().getService();
    }

    @Override
    public String translateText(String text) {
        return translate(text);
    }

    private String translate(String text) {
            String translatedJP = translateToJP(text);
            return translateToKO(translatedJP);
    }

    private String translateToJP(String text) {
        return translate.translate(text, Translate.TranslateOption.sourceLanguage("en"), Translate.TranslateOption.targetLanguage("ja")).getTranslatedText();
    }

    private String translateToKO(String text) {
        return translate.translate(text, Translate.TranslateOption.sourceLanguage("ja"), Translate.TranslateOption.targetLanguage("ko")).getTranslatedText();
    }

}
