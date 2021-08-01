package com.meant.deliver.resource;

import java.math.BigDecimal;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.meant.delivery.dto.DeliveryCostDto;
import com.meant.delivery.dto.DeliveryCostResponseDto;
import com.meant.delivery.exception.CannotCategorizeparcelException;
import com.meant.delivery.exception.InvalidVoucherException;
import com.meant.delivery.param.validator.ParamCombinationChecker;
import com.meant.delivery.service.DeliveryCostService;

import lombok.extern.log4j.Log4j2;

@Service
@Path("/delivery")
@Log4j2
public class DeliveryCostResource {

    @Autowired
    DeliveryCostService deliveryCostService;

    @GET
    @Path("/cost")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public DeliveryCostResponseDto getDeliveryCost(final @ParamCombinationChecker @BeanParam DeliveryCostDto dto)
	    throws JsonMappingException, JsonProcessingException, InvalidVoucherException,
	    CannotCategorizeparcelException {
	BigDecimal finalCost = deliveryCostService.calculateParcelCost(dto);
	String category = deliveryCostService.getParcelCategory(dto);
	return DeliveryCostResponseDto.builder().category(category).cost(finalCost).build();

    }
}
