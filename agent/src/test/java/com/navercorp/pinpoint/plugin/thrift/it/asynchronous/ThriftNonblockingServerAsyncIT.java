/*
 * Copyright 2015 NAVER Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.navercorp.pinpoint.plugin.thrift.it.asynchronous;

import static org.junit.Assert.assertEquals;

import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.transport.TTransportException;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.navercorp.pinpoint.common.Version;
import com.navercorp.pinpoint.plugin.thrift.common.TestEnvironment;
import com.navercorp.pinpoint.plugin.thrift.common.server.EchoTestServer;
import com.navercorp.pinpoint.plugin.thrift.common.server.AsyncEchoTestServer.AsyncEchoTestServerFactory;
import com.navercorp.pinpoint.plugin.thrift.it.EchoTestRunner;
import com.navercorp.pinpoint.test.plugin.Dependency;
import com.navercorp.pinpoint.test.plugin.PinpointAgent;
import com.navercorp.pinpoint.test.plugin.PinpointPluginTestSuite;

/**
 * Integration test for TNonblockingServer with asynchronous processor.
 * 
 * Tests against libthrift 0.9.2+ due to THRIFT-2274
 * 
 * @author HyunGil Jeong
 */
@RunWith(PinpointPluginTestSuite.class)
@PinpointAgent("agent/target/pinpoint-agent-" + Version.VERSION)
@Dependency({ "org.apache.thrift:libthrift:[0.9.2,)", "log4j:log4j:1.2.16", "org.slf4j:slf4j-log4j12:1.5.8" })
public class ThriftNonblockingServerAsyncIT extends EchoTestRunner<TNonblockingServer> {

    @Override
    protected EchoTestServer<TNonblockingServer> createEchoServer(TestEnvironment environment)
            throws TTransportException {
        return AsyncEchoTestServerFactory.nonblockingServer(environment);
    }

    @Test
    public void testSynchronousRpcCall() throws Exception {
        // Given
        final String expectedMessage = "TEST_MESSAGE";
        // When
        final String result = super.invokeEcho(expectedMessage);
        // Then
        assertEquals(expectedMessage, result);
    }

    @Test
    public void testAsynchronousRpcCall() throws Exception {
        // Given
        final String expectedMessage = "TEST_MESSAGE";
        // When
        final String result = super.invokeEchoAsync(expectedMessage);
        // Then
        assertEquals(expectedMessage, result);
    }

}
