package com.maetdolvlog.maetdolvlog.entity;

import com.google.cloud.translate.Translate;

public interface TranslateService {

    String translateText(String text);

}
