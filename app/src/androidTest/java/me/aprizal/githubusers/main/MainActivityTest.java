package me.aprizal.githubusers.main;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.view.KeyEvent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import me.aprizal.githubusers.R;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Before
    public void setUp() {
        ActivityScenario.launch(MainActivity.class);
    }

    @After
    public void tearDown() {

    }

    @Test
    public void onListUsers() {
        onView(withId(R.id.rv_users)).check(matches(isDisplayed()));
        delay1seconds();
    }

    @Test
    public void onSearchListUsers() {
        onView(withId(R.id.search_view)).perform(click());

        String searchUser = "aprizalrizalzal";
        onView(withId(R.id.search_src_text)).perform(typeText(searchUser),closeSoftKeyboard());
        onView(withId(R.id.search_view)).perform(pressKey(KeyEvent.KEYCODE_ENTER));
        onView(withId(R.id.rv_users)).check(matches(isDisplayed()));
        delay1seconds();
    }

    @Test
    public void onClickedListUsersToDetail() {
        onView(withId(R.id.rv_users)).check(matches(isDisplayed()));
        delay1seconds();

        onView(withId(R.id.rv_users)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        delay1seconds();
    }

    @Test
    public void onClickedSearchListUsersToDetail() {
        String searchUser = "aprizal";
        onView(withId(R.id.search_src_text)).perform(typeText(searchUser));
        onView(withId(R.id.search_view)).perform(pressKey(KeyEvent.KEYCODE_ENTER));
        delay1seconds();

        onView(withId(R.id.rv_users)).check(matches(isDisplayed()));
        delay1seconds();

        onView(withId(R.id.rv_users)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        delay1seconds();
    }

    @Test
    public void onClickedListUsersToFavorite() {
        onView(withId(R.id.rv_users)).check(matches(isDisplayed()));
        delay1seconds();

        onView(withId(R.id.rv_users)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.fab_favorite)).perform(click());
        delay1seconds();

        pressBack();
        onView(withId(R.id.favorite)).perform(click());
        onView(withId(R.id.rv_favorite)).check(matches(isDisplayed()));
        delay1seconds();
    }

    @Test
    public void onClickedListUsersFavoriteToDetail() {
        onView(withId(R.id.favorite)).perform(click());
        onView(withId(R.id.rv_favorite)).check(matches(isDisplayed()));
        delay1seconds();

        onView(withId(R.id.rv_favorite)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        delay1seconds();
    }

    @Test
    public void onClickedItemSettings() {
        onView(withId(R.id.settings)).perform(click());
        onView(withId(R.id.switch_theme)).perform(click());
        delay1seconds();

        onView(withId(R.id.img_btn_language)).perform(click());
    }

    public static void pressBack() {
        onView(isRoot()).perform(ViewActions.pressBack());
    }

    private void delay1seconds() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}