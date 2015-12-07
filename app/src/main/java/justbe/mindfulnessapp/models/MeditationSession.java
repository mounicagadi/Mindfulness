package justbe.mindfulnessapp.models;

import android.content.Intent;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import justbe.mindfulnessapp.App;
import justbe.mindfulnessapp.rest.GenericHttpRequestTask;
import justbe.mindfulnessapp.rest.RestUtil;
import justbe.mindfulnessapp.rest.UserPresentableException;

/**
 * Model for representing daily Meditation Sessions.
 * API: meditation_session
 */
public class MeditationSession extends PlainOldDBO<MeditationSession> {
    private Integer meditation_id;
    private Double percent_completed;

    public Integer getMeditation_id() {
        return meditation_id;
    }

    // id is in form of week followed by day i.e. week 2 day 5, id = 25
    public void setMeditation_id(Integer week, Integer day) {
        this.meditation_id = week * 10 + day;
    }

    public Double getPercent_completed() {
        return percent_completed;
    }

    public void setPercent_completed(Double percent_completed) {
        this.percent_completed = percent_completed;
    }

    /**
     *  Generates the meditations for the given week in the database for the user
     *  @param week 1 -> 8 representing the week to create meditations for
     */
    public static void populateDatabaseForWeek(Integer week) {
        // Create an HTTPRequestTask that sends a MeditationSession Object and Returns a MeditationSession Object
        GenericHttpRequestTask<MeditationSession, MeditationSession> task;

        // Unstarted meditation session for the user without meditation_id
        MeditationSession ms = new MeditationSession();
        ms.setPercent_completed(0.0);

        // Create one meditation for each day of the week 0 Monday -> 6 Saturday
        for (int d = 0; d < 7; d = d + 1) {
            task = new GenericHttpRequestTask(MeditationSession.class, MeditationSession.class);

            ms.setMeditation_id(week, d);

            task.execute("/api/v1/meditation_session/", HttpMethod.POST, ms);

            try {
                ResponseEntity<MeditationSession> result = task.waitForResponse();

                RestUtil.checkResponseHazardously(result);

            } catch (Exception e) {
                new UserPresentableException(e);
            }
        }
    }
}
