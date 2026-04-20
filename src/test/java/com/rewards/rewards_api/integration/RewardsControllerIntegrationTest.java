package com.rewards.rewards_api.integration;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RewardsControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllRewards_Integration_WithDatabase() throws Exception {
        ResponseEntity<String> response = restTemplate.getForEntity("/rewards", String.class);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());

        JsonNode root = objectMapper.readTree(response.getBody());
        assertTrue(root.isArray());

        // Find customer C4 and assert expected bucket values computed from data.sql
        JsonNode c4 = null;
        for (JsonNode node : root) {
            if (node.has("customerId") && "C4".equals(node.get("customerId").asText())) {
                c4 = node;
                break;
            }
        }

        assertNotNull(c4, "Response should contain customer C4");

        // Expected values computed against today's date (2026-04-20) and data.sql entries for C4
        assertEquals(315L, c4.get("within1month").asLong());
        assertEquals(45L, c4.get("within1to2months").asLong());
        assertEquals(280L, c4.get("within2to3months").asLong());
        assertEquals(640L, c4.get("totalPoints").asLong());
    }
}
