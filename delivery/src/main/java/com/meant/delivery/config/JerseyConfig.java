package com.meant.delivery.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import com.meant.deliver.resource.DeliveryCostResource;

@Configuration
public class JerseyConfig extends ResourceConfig{

	public JerseyConfig(){
		register(DeliveryCostResource.class);
	}
}
