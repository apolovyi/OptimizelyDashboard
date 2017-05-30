package com.dashboard.controller;

import com.dashboard.domain.Experiment;
import com.dashboard.repository.ExperimentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class DashboardController {

    private final ExperimentRepository experimentRepository;

    @Autowired
    public DashboardController(ExperimentRepository experimentRepository) {
        this.experimentRepository = experimentRepository;
    }

    @RequestMapping("/")
    public ModelAndView home() {

        List<Experiment> experiments = experimentRepository.findAll();
        experiments = experiments.stream().sorted(Comparator.comparing(Experiment::getCreationDate).reversed()).collect(Collectors.toList());
        ModelAndView model = new ModelAndView("home", "experiments", null);

        if (experiments.stream().noneMatch(
                experiment -> experiment
                        .getVariations().stream()
                        .anyMatch(
                                variation -> variation.getName()==null
                        )
        )) {
            model = new ModelAndView("home", "experiments", experiments);
        }
        return model;
    }
}
