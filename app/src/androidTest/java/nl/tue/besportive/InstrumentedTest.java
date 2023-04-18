package nl.tue.besportive;

import android.content.Context;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.test.core.app.ActivityScenario;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import nl.tue.besportive.activities.ConfigureChallengesActivity;
import nl.tue.besportive.activities.FeedActivity;
import nl.tue.besportive.activities.LeaderboardActivity;
import nl.tue.besportive.databinding.ActivityConfigureChallengesBinding;
import nl.tue.besportive.models.ConfigureChallengesViewModel;
import nl.tue.besportive.models.FeedViewModel;


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class InstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("nl.tue.besportive", appContext.getPackageName());
    }

    //CONFIGURE CHALLENGES TESTS

    //Test in Configure Challenges that the activity is correctly created and its layout is inflated.
    @Test
    public void testActivityCreation() {
        ActivityScenario<ConfigureChallengesActivity> scenario = ActivityScenario.launch(ConfigureChallengesActivity.class);
        scenario.onActivity(activity -> {
            View view = activity.findViewById(R.id.activity_configure_challenges_layout);
            assertNotNull(view);
        });


    }
    //Test in Configure Challenges  that the ViewModel is correctly initialized and bound to the layout.
    @Test
    public void testViewModelBinding() {
        ActivityScenario<ConfigureChallengesActivity> scenario = ActivityScenario.launch(ConfigureChallengesActivity.class);
        scenario.onActivity(activity -> {
            ConfigureChallengesViewModel viewModel = new ViewModelProvider(activity).get(ConfigureChallengesViewModel.class);
            ActivityConfigureChallengesBinding binding = DataBindingUtil.getBinding(activity.findViewById(R.id.activity_configure_challenges_layout));

            assertNotNull(viewModel);
            assertNotNull(binding);

            assertEquals(viewModel, binding.getViewModel());
            assertEquals(activity, binding.getLifecycleOwner());
        });
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



}