/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.ignite.internal.processors.tracing;

import java.util.Map;
import java.util.Set;

/**
 * Logical piece of a trace that represents a single operation.
 * Each unit work is called a Span in a trace.
 * Spans include metadata about the work, including the time spent in the step (latency),
 * status, time events, attributes, links.
 * You can use tracing to debug errors and latency issues in your applications.
 */
public interface Span {

    /**
     * Adds tag to span with {@code String} value.
     *
     * @param tagName Tag name.
     * @param tagVal Tag value.
     */
    Span addTag(String tagName, String tagVal);

    /**
     * Adds tag to span with {@code long} value.
     *
     * @param tagName Tag name.
     * @param tagVal Tag value.
     */
    Span addTag(String tagName, long tagVal);

    /**
     * Logs work to span.
     *
     * @param logDesc Log description.
     */
    Span addLog(String logDesc);

    /**
     * Adds log to span with additional attributes.
     *
     * @param logDesc Log description.
     * @param attrs Attributes.
     */
    Span addLog(String logDesc, Map<String, String> attrs);

    /**
     * Explicitly set status for span.
     *
     * @param spanStatus Status.
     */
    Span setStatus(SpanStatus spanStatus);

    /**
     * Ends span. This action sets default status if not set and mark the span as ready to be exported.
     */
    Span end();

    /**
     * @return {@code true} if span has already ended.
     */
    boolean isEnded();

    /**
     * @return Type of given span.
     */
    SpanType type();

    /**
     * @return Set of included scopes.
     */
    Set<Scope> includedScopes();

    /**
     * @param scope Chainable scope candidate.
     * @return {@code true} if given span is chainable with other spans with specified scope.
     */
    default boolean isChainable(Scope scope) {
        return type().scope() == scope || includedScopes().contains(scope);
    }
}