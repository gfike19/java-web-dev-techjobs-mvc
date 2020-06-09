package org.launchcode.javawebdevtechjobsmvc.controllers;

import org.launchcode.javawebdevtechjobsmvc.models.Job;
import org.launchcode.javawebdevtechjobsmvc.models.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import static org.launchcode.javawebdevtechjobsmvc.controllers.ListController.columnChoices;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("search")
public class SearchController {

    @RequestMapping(value = "")
    public String search(Model model) {
        model.addAttribute("columns", columnChoices);
        String checkedValue = "all";
        model.addAttribute("checkedValue", checkedValue);
        return "search";
    }

    // TODO #3 - Create a handler to process a search request and render the updated search view.

    @RequestMapping(value="results")
    public String results(@RequestParam String searchType, @RequestParam String
            searchTerm, Model model) {
        ArrayList<Job> results;

        if (searchType.toLowerCase().equals("all")){
            results = JobData.findAll();
            model.addAttribute("title", "All Jobs");
        } else {
            results = JobData.findByColumnAndValue(searchType, searchTerm);
            model.addAttribute("title", "Jobs with " + columnChoices.get(searchType) + ": " + searchTerm);
        }
        String checkedValue = searchType + "";
        model.addAttribute("checkedValue", checkedValue);
        model.addAttribute("results", results);
        model.addAttribute("columns", columnChoices);
        return "search";
    }

    @RequestMapping(value="/{col},{val}")
    public String linkedResults(@PathVariable String col, @PathVariable String val, Model model) {
        ArrayList<Job> results = JobData.findByColumnAndValue(col, val);
        String checkedValue = col + "";
        model.addAttribute("checkedValue", checkedValue);
        model.addAttribute("results", results);
        model.addAttribute("columns", columnChoices);
        return "search";
    }
}
