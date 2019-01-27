package com.groupsix.mapFood.repositories;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.groupsix.mapFood.entities.Motoboy;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class MotoboyRepositoryTest {

	@Autowired
	private MotoboyRepository motoboyRepository;
	
	@Before
	public void setUp() {
		Motoboy motoboy = new Motoboy();
		motoboy.setId(1);
		motoboy.setLat(-30.0001124);
		motoboy.setLon(-51.1987111);
		motoboyRepository.save(motoboy);
	}
	
	@After
	public void tearDown() {
		motoboyRepository.deleteAll();
	}
	
	@Test
	public void testFindMotoboyNearby() {
		List<Motoboy> motoboys = motoboyRepository.findNearby(-30.000148, -51.198440);
		
		assertEquals(1, motoboys.size());
	}
	
}
