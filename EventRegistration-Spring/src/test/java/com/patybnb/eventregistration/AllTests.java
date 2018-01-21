package com.patybnb.eventregistration;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.patybnb.eventregistration.persistenece.TestPersistence;
import com.patybnb.eventregistration.service.TestEventRegistrationService;

@RunWith(Suite.class)
@SuiteClasses({ TestEventRegistrationService.class, TestPersistence.class })
public class AllTests {

}
