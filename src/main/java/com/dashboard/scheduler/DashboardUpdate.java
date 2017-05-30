package com.dashboard.scheduler;

import com.dashboard.domain.Experiment;
import com.dashboard.repository.ExperimentRepository;
import com.dashboard.domain.Variation;
import com.dashboard.repository.VariationRepository;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DashboardUpdate {

    @Autowired
    private ExperimentRepository experimentRepository;
    @Autowired
    private VariationRepository variationRepository;

    private static final String TOKEN = "";


    private void persistExperiments() {

        JSONArray jsonArray = null;
        try {
            jsonArray = Unirest.get("").header("token", TOKEN).asJson().getBody().getArray();
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject row = jsonArray.getJSONObject(i);
            if (row.getString("status").equals("Running")) {
                JSONArray variations = row.getJSONArray("variation_ids");
                List<Variation> variationList = new ArrayList<>();
                experimentRepository.save(new Experiment(row.get("id").toString()));
                for (int j = 0; j < variations.length(); j++) {
                    variationRepository.save(new Variation(variations.get(j).toString(), experimentRepository.findOne(row.get("id").toString())));
                }
                experimentRepository.save(new Experiment(row.get("id").toString(), row.getString("description"), row.get("primary_goal_id").toString(), row.getString("created"), row.getString("shareable_results_link"), variationList));
            }
        }
    }

    private void persistVariations(Experiment experiment) {

        JSONArray jsonArray1 = null;
        try {
            jsonArray1 = Unirest.get("" + experiment.getId() + "/stats").header("token", TOKEN).asJson().getBody().getArray();
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        for (Variation var : experiment.getVariations()) {
            boolean revenueMain = false;

            for (int i = 0; i < jsonArray1.length(); i++) {
                JSONObject row = jsonArray1.getJSONObject(i);
                if (row.getString("goal_name").equals("Total Revenue") && row.get("goal_id").toString().equals(experiment.getMainGoalId()))
                    revenueMain = true;

            }

            for (int i = 0; i < jsonArray1.length(); i++) {
                JSONObject row = jsonArray1.getJSONObject(i);


                if (var.getId().equals(row.get("variation_id").toString())) {
                    var.setName(row.getString("variation_name"));

                    if (revenueMain) {
                        if (row.getString("goal_name").equals("Book it")) {
                            var.setCrFormat(row.getDouble("conversion_rate"));
                            var.setCrImprovementFormat(row.getDouble("improvement"));
                        }
                    } else {
                        if (row.get("goal_id").toString().equals(experiment.getMainGoalId())) {
                            var.setCrFormat(row.getDouble("conversion_rate"));
                            var.setCrImprovementFormat(row.getDouble("improvement"));
                        }
                    }

                    if (row.getBoolean("is_revenue")) {
                        var.setBsFormat(row.getDouble("revenue_per_visitor"));
                        if (row.get("improvement").toString().equals("null"))
                            var.setBsImprovementFormat(0.0);
                        else
                            var.setBsImprovementFormat(row.getDouble("improvement"));
                    }
                }
            }
            variationRepository.save(var);
        }
    }

    @Scheduled(fixedRate = 3600000)
    public void reportCurrentTime() {

        variationRepository.deleteAll();
        experimentRepository.deleteAll();

        persistExperiments();

        experimentRepository.findAll().forEach(experiment -> {
            persistVariations(experiment);
        });
    }


}
