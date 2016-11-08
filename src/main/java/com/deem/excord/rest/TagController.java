package com.deem.excord.rest;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.deem.excord.domain.EcTag;
import com.deem.excord.repository.TagRepository;

@RestController
public class TagController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TagController.class);

    @Autowired
    TagRepository tagDao;

    @RequestMapping(value = "/tags", method = RequestMethod.GET)
    public List<String> tags() {
        List<EcTag> ecTagList = (List<EcTag>) tagDao.findAll();
        List<String> tags = new ArrayList<>();
        for(EcTag ecTag : ecTagList) {
        	tags.add(ecTag.getTag());
        }
        return tags;
    }
}
