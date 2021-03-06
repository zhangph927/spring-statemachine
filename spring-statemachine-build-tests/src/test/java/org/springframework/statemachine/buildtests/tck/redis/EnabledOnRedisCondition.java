/*
 * Copyright 2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.statemachine.buildtests.tck.redis;

import static org.junit.jupiter.api.extension.ConditionEvaluationResult.disabled;
import static org.junit.jupiter.api.extension.ConditionEvaluationResult.enabled;

import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

public class EnabledOnRedisCondition implements ExecutionCondition {

	static final ConditionEvaluationResult ENABLED_ON_REDIS = enabled("Redis found");

	static final ConditionEvaluationResult DISABLED_ON_REDIS =	disabled("Redis not found");

	@Override
	public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
		JedisConnectionFactory connectionFactory = null;
		try {
			connectionFactory = new JedisConnectionFactory();
			connectionFactory.afterPropertiesSet();
			connectionFactory.getConnection().close();
			return ENABLED_ON_REDIS;
		} catch (Exception e) {
		} finally {
			if (connectionFactory != null) {
				connectionFactory.destroy();
			}
		}
		return DISABLED_ON_REDIS;
	}
}
