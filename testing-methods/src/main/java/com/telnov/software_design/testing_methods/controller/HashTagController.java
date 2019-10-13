package com.telnov.software_design.testing_methods.controller;

import com.telnov.software_design.testing_methods.model.HashTagInfoDTO;
import com.telnov.software_design.testing_methods.service.HashTagService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/hashtag")
public class HashTagController {

    private static final int MAX_HOUR_INTERVAL = 24;
    private static final String HASH_TAG_REGEX = "(?:\\s|^)#[А-Яа-яA-Za-z0-9\\-\\.\\_]+(?:\\s|$)";

    private final HashTagService service;

    public HashTagController(HashTagService service) {
        this.service = service;
    }

    @GetMapping
    public List<HashTagInfoDTO> getHashTagPerHour(
            @RequestParam("hash_tag_txt")
            String hashTagTxt,
            @RequestParam(name = "hour_interval", required = false)
            Integer hourInterval
    ) {
        validate(hashTagTxt, hourInterval);

        if (hourInterval == null) {
            hourInterval = MAX_HOUR_INTERVAL;
        }

        return service.getHashTagPerHour(hashTagTxt, hourInterval);
    }

    private static void validate(String hashTagTxt, Integer hourInterval) {
        if (hourInterval != null && (hourInterval < 1 || hourInterval > MAX_HOUR_INTERVAL)) {
            throw new IllegalArgumentException("Invalid hour interval");
        } else if (!("#" + hashTagTxt).matches(HASH_TAG_REGEX)) {
            throw new IllegalArgumentException("Invalid hash tag");
        }
    }
}
