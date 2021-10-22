package com.rewards.program.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public final class RewardsUtilTest {

	@Test
	public void calculatePointsTest() {
		assertThat(RewardsUtil.calculatePoints(0L)).isEqualTo(0L);
		assertThat(RewardsUtil.calculatePoints(50L)).isEqualTo(0L);
		assertThat(RewardsUtil.calculatePoints(51L)).isEqualTo(1L);
		assertThat(RewardsUtil.calculatePoints(74L)).isEqualTo(24L);
		assertThat(RewardsUtil.calculatePoints(100L)).isEqualTo(50L);
		assertThat(RewardsUtil.calculatePoints(120L)).isEqualTo(90L);
		assertThat(RewardsUtil.calculatePoints(150L)).isEqualTo(150L);
		assertThat(RewardsUtil.calculatePoints(1186L)).isEqualTo(2222L);
	}

	@Test
	public void isWithinLastThreeMonthsTest() {
		LocalDate today = LocalDate.now();
		assertThat(RewardsUtil
				.isWithinLastThreeMonths(today.format(DateTimeFormatter.ofPattern("d-MM-yyyy", Locale.US)).toString()))
						.isTrue();

	}

	@Test
	public void isNotWithinLastThreeMonthsTest() {
		LocalDate today = LocalDate.now();
		today = today.minusMonths(3L);
		assertThat(RewardsUtil
				.isWithinLastThreeMonths(today.format(DateTimeFormatter.ofPattern("d-MM-yyyy", Locale.US)).toString()))
						.isFalse();

	}

	@Test
	public void isDateValidTrueTest() {
		assertThat(RewardsUtil
				.isDateValid("10-10-2021"))
						.isTrue();

	}
	
	@Test
	public void isDateValidFalseTest() {
		assertThat(RewardsUtil
				.isDateValid("10-54-20"))
						.isFalse();

	}
	
}