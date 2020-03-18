/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.beautysaloonaudit;

import com.mycompany.beautysaloonaudit.Controllers.Controller;
import com.mycompany.beautysaloonaudit.Controllers.OrderWindowSceneController;
import java.util.ArrayList;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Artur
 */
public class TimeManagerTest {
    
    public TimeManagerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of addBookedTimeInterval method, of class TimeManager.
     */
    @Test
    public void testAddBookedTimeInterval() {
        TimeInterval workingTime=new TimeInterval(new DateTime(2019, 8, 27, 9, 0), new DateTime(2019, 8, 27, 12, 0));
        TimeInterval bookedTime1=new TimeInterval(new DateTime(2019, 8, 27, 10, 0), new DateTime(2019, 8, 27, 11, 0));
        TimeInterval bookedTime2=new TimeInterval(new DateTime(2019, 8, 27, 9, 0), new DateTime(2019, 8, 27, 9, 30));
        TimeInterval bookedTime3=new TimeInterval(new DateTime(2019, 8, 27, 11, 30), new DateTime(2019, 8, 27, 12, 0));
        ArrayList<TimeInterval> freeTime=new ArrayList<>();
        freeTime.add(workingTime);
        TimeManager manager=new TimeManager(freeTime);
        manager.addBookedTimeInterval(bookedTime1);
        System.out.println(manager);
        manager.addBookedTimeInterval(bookedTime2);
        System.out.println(manager);
        manager.addBookedTimeInterval(bookedTime3);
        System.out.println(manager);
    }
    
}
