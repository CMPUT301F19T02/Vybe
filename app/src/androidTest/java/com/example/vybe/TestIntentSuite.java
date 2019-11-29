package com.example.vybe;
import com.example.vybe.Activities.CreateAccountActivity;
import com.example.vybe.Activities.MyVibesActivity;
import com.example.vybe.Activities.ViewProfileActivity;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        CreateAccountActivity.class,
        LoginActivityTest.class,
        ViewProfileActivity.class,
        MyVibesActivity.class,
        SocialActivityTest.class,
        MapFragmentTest.class
})

public class TestIntentSuite {
}
