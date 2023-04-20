package nl.tue.besportive;

import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import nl.tue.besportive.activities.ConfigureChallengesActivity;
import nl.tue.besportive.activities.CreateGroupActivity;
import nl.tue.besportive.activities.FeedActivity;
import nl.tue.besportive.activities.LeaderboardActivity;
import nl.tue.besportive.activities.ProfileActivity;
import nl.tue.besportive.activities.StartChallengeActivity;
import nl.tue.besportive.adapters.ChallengesAdapter;
import nl.tue.besportive.databinding.ActivityConfigureChallengesBinding;
import nl.tue.besportive.databinding.ActivityCreateGroupBinding;
import nl.tue.besportive.databinding.ActivityStartChallengeBinding;
import nl.tue.besportive.models.ConfigureChallengesViewModel;
import nl.tue.besportive.models.CreateGroupViewModel;
import nl.tue.besportive.models.FeedViewModel;
import nl.tue.besportive.models.ProfileViewModel;
import nl.tue.besportive.models.StartChallengeViewModel;


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class InstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = ApplicationProvider.getApplicationContext();
        assertEquals("nl.tue.besportive", appContext.getPackageName());
    }


    //In feed activity test if the ViewModel is initialized correctly
    @Test
    public void testViewModelInitialization() {
        ActivityScenario<FeedActivity> scenario = ActivityScenario.launch(FeedActivity.class);
        scenario.onActivity(activity -> {
            FeedViewModel viewModel = new ViewModelProvider(activity).get(FeedViewModel.class);
            assertNotNull(viewModel);
        });
    }

    //Test case for checking if the ViewModel is correctly initialized and not null in ProfileActivity
    @Test
    public void profileViewtestViewModelBinding() {
        ActivityScenario<ProfileActivity> scenario = ActivityScenario.launch(ProfileActivity.class);
        scenario.onActivity(activity -> {
            ProfileViewModel viewModel = new ViewModelProvider(activity).get(ProfileViewModel.class);
            assertNotNull(viewModel);
        });
    }


    // Test case for checking if the ViewModel is correctly initialized and bound to the layout
    @Test
    public void testCreateGroupViewModelBinding() {
        ActivityScenario<CreateGroupActivity> scenario = ActivityScenario.launch(CreateGroupActivity.class);
        scenario.onActivity(activity -> {
            CreateGroupViewModel viewModel = new ViewModelProvider(activity).get(CreateGroupViewModel.class);
            assertNotNull(viewModel);
        });
    }



}

