package com.example.jobrec.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Extraction {
    @JsonProperty("tag_name")
    public String tagName;

    @JsonProperty("parsed_value")
    public String parsedValue;

    public int count;

    public String relevance;

}
