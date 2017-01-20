package com.bakerbeach.market.core.service.order;

import org.apache.camel.ProducerTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bakerbeach.market.core.api.model.Order;
import com.bakerbeach.market.core.service.order.model.SimpleOrder;
import com.bakerbeach.market.core.service.order.service.OrderServiceImpl;
import com.bakerbeach.market.order.api.service.OrderService;

@ActiveProfiles(profiles = { "env.test" })
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/test-*.xml" })
public class OrderServiceTest {
	
	@Autowired
	private ProducerTemplate producerTemplate;
	
	@Test
	public void foo() {
		System.out.println("test");

		
		producerTemplate.sendBody("direct:foo", "Das ist ein Test!");
		
	}

}
