package com.telnov.software_design.testing_methods.service;

import com.telnov.software_design.testing_methods.client.ClientApi;
import com.telnov.software_design.testing_methods.model.HashTagInfoDTO;
import com.telnov.software_design.testing_methods.model.NewsFeed;
import org.springframework.stereotype.Service;

import javax.annotation.ParametersAreNonnullByDefault;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@ParametersAreNonnullByDefault
public class HashTagService {

    private final ClientApi clientApi;

    public HashTagService(ClientApi clientApi) {
        this.clientApi = clientApi;
    }

    public List<HashTagInfoDTO> getHashTagPerHour(String hashTagTxt, int hourInterval) {
        List<HashTagInfoDTO> hashTagsInfo = new ArrayList<>();

        clientApi.getNewsFeedsByHashTag(hashTagTxt, hourInterval)
                .stream()
                .sorted(Comparator.comparing(NewsFeed::getInstant))
                .map(item -> item.getInstant()
                        .atZone(ZoneOffset.UTC)
                        .getHour())
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting())
                )
                .forEach((hour, count) ->
                        hashTagsInfo.add(new HashTagInfoDTO(hour, count))
                );

        hashTagsInfo.sort(Comparator.comparing(HashTagInfoDTO::getHour).reversed());
        return hashTagsInfo;
    }
}
