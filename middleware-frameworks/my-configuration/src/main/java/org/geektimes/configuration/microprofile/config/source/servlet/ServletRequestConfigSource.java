/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.geektimes.configuration.microprofile.config.source.servlet;

import org.eclipse.microprofile.config.spi.ConfigSource;
import org.geektimes.configuration.microprofile.config.source.MapBasedConfigSource;

import javax.servlet.ServletRequest;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * {@link ServletRequest} {@link ConfigSource}
 *
 * @author qrXun
 * @since 1.0.0
 */
public class ServletRequestConfigSource implements ConfigSource {

    protected ServletRequest servletRequest;

    private final String name;

    private final int ordinal;

    public ServletRequestConfigSource(ServletRequest servletRequest) {
        this.servletRequest = servletRequest;
        this.name = servletRequest.getLocalName();
        this.ordinal = 560;
    }

    @Override
    public Set<String> getPropertyNames() {
        return servletRequest.getParameterMap().keySet();
    }

    /**
     * 由于可能存在多个值，通过 "," 连接
     * @param propertyName
     * @return
     */
    @Override
    public String getValue(String propertyName) {
        String[] values = servletRequest.getParameterValues(propertyName);
        if (values == null || values.length == 0) {
            return null;
        } else {
            return String.join(",", values);
        }
    }

    @Override
    public final String getName() {
        return name;
    }

    @Override
    public final int getOrdinal() {
        return ordinal;
    }
}
