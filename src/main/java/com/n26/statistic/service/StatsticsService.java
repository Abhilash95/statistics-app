package com.n26.statistic.service;

import org.springframework.stereotype.Service;

import com.n26.statistic.dto.StatsticsResponse;

/**
 * @author Abhilash
 *
 */
@Service
public interface StatsticsService {

	StatsticsResponse getStatstics();
}
