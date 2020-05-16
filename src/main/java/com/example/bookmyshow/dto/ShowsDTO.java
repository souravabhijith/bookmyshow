package com.example.bookmyshow.dto;

import com.example.bookmyshow.entities.Show;
import com.example.bookmyshow.entities.Theatre;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by AbhijithRavuri.
 */
public class ShowsDTO {

    List<Show> shows;
    ObjectMapper mapper;
    public ShowsDTO(ObjectMapper mapper, List<Show> shows) {
        this.shows = shows;
        this.mapper = mapper;
    }
    public String toJson() {
        ArrayNode result = mapper.createArrayNode();
        Map<Theatre, List<Show>> map = new HashMap<>();

        for (Show show : shows) {
            if (!map.containsKey(show.getTheatre())) {
                map.put(show.getTheatre(), new ArrayList<>());
            }
            map.get(show.getTheatre()).add(show);
        }

        for (Theatre theatre : map.keySet()) {
            result.add(getShowsForTheatre(theatre, map.get(theatre)));
        }
        return result.toString();
    }

    public ObjectNode getShowsForTheatre(Theatre theatre, List<Show> shows) {
        ObjectNode result = mapper.createObjectNode();
        result.put("theatre_id", theatre.getId());
        result.put("theatre_name", theatre.getName());
        ArrayNode showsArr = mapper.createArrayNode();
        for (Show show : shows) {
            ObjectNode showObjNode = mapper.createObjectNode();
            showObjNode.put("id", show.getId());
            showObjNode.put("time", show.getStarttime().toString());
            showsArr.add(showObjNode);
        }
        result.set("shows", showsArr);
        return result;
    }
}
