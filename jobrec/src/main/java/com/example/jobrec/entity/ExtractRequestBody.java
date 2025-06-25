package com.example.jobrec.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExtractRequestBody {
    public String text;

    @JsonProperty("max_keywords")
    public int maxKeywords;

    public String language = "en";

    public String providers = "OpenAI";

    public String model = "gpt-3.5-turbo";

    public String fallback_provider = "OpenAI";

    @JsonProperty("response_as_dict")
    public boolean responseAsDict = true;

    public ExtractRequestBody(String text) {
        this.text = text;
    }


}
