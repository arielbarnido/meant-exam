package com.meant.delivery.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.meant.delivery.dto.DeliveryCostDto;
import com.meant.delivery.exception.CannotCategorizeparcelException;
import com.meant.delivery.exception.InvalidVoucherException;

@SpringBootTest
public class DeliveryCostServiceTest {

    @InjectMocks
    DeliveryCostService service;

    @Mock
    Discountservice discountService;

    @BeforeEach
    public void setup() {

    }

    @Test
    public void testCalculateRejectDelivery() throws JsonMappingException, JsonProcessingException,
	    InvalidVoucherException, CannotCategorizeparcelException {
	BigDecimal computedCost = service.calculateParcelCost(DeliveryCostDto.builder().weight(new BigDecimal(51))
		.height(new BigDecimal(1)).width(new BigDecimal(2)).length(new BigDecimal(3)).build());
	assertThat(computedCost, equalTo(new BigDecimal(-51).setScale(2, BigDecimal.ROUND_HALF_EVEN)));
    }

    @Test
    public void testCalculateHeavyParcel() throws JsonMappingException, JsonProcessingException,
	    InvalidVoucherException, CannotCategorizeparcelException {
	BigDecimal computedCost = service.calculateParcelCost(DeliveryCostDto.builder().weight(new BigDecimal(11))
		.height(new BigDecimal(1)).width(new BigDecimal(2)).length(new BigDecimal(3)).build());
	assertThat(computedCost, equalTo(new BigDecimal(220).setScale(2, BigDecimal.ROUND_HALF_EVEN)));
    }

    @Test
    public void testCalculateSmallParcel() throws JsonMappingException, JsonProcessingException,
	    InvalidVoucherException, CannotCategorizeparcelException {
	BigDecimal computedCost = service.calculateParcelCost(DeliveryCostDto.builder().weight(new BigDecimal(9))
		.height(new BigDecimal(1)).width(new BigDecimal(2)).length(new BigDecimal(3)).build());
	assertThat(computedCost, equalTo(new BigDecimal(0.18).setScale(2, BigDecimal.ROUND_HALF_EVEN)));
    }

    @Test
    public void testCalculateMediumParcel() throws JsonMappingException, JsonProcessingException,
	    InvalidVoucherException, CannotCategorizeparcelException {
	BigDecimal computedCost = service.calculateParcelCost(DeliveryCostDto.builder().weight(new BigDecimal(9))
		.height(new BigDecimal(10)).width(new BigDecimal(10)).length(new BigDecimal(23)).build());
	assertThat(computedCost, equalTo(new BigDecimal(92.00).setScale(2, BigDecimal.ROUND_HALF_EVEN)));
    }

    @Test
    public void testCalculateLargeParcel() throws JsonMappingException, JsonProcessingException,
	    InvalidVoucherException, CannotCategorizeparcelException {
	BigDecimal computedCost = service.calculateParcelCost(DeliveryCostDto.builder().weight(new BigDecimal(9))
		.height(new BigDecimal(10)).width(new BigDecimal(10)).length(new BigDecimal(30)).build());
	assertThat(computedCost, equalTo(new BigDecimal(150.00).setScale(2, BigDecimal.ROUND_HALF_EVEN)));
    }

    // @Test
    public void testCalculateLargeParcelWithVoucherCode() throws JsonMappingException, JsonProcessingException,
	    InvalidVoucherException, CannotCategorizeparcelException {
	when(discountService.getDiscountPercentageUsingViaVoucher("TEST"))
		.thenReturn(new BigDecimal(1.02).setScale(2, BigDecimal.ROUND_HALF_EVEN));
	BigDecimal computedCost = service.calculateParcelCost(DeliveryCostDto.builder().weight(new BigDecimal(9))
		.height(new BigDecimal(10)).width(new BigDecimal(10)).length(new BigDecimal(30)).build());
	assertThat(computedCost, equalTo(new BigDecimal(147.00).setScale(2, BigDecimal.ROUND_HALF_EVEN)));
    }
}
