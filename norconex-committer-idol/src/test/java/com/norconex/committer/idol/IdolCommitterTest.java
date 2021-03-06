/* Copyright 2010-2017 Norconex Inc.
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
package com.norconex.committer.idol;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.commons.lang3.ClassUtils;
import org.apache.log4j.Level;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.norconex.commons.lang.config.XMLConfigurationUtil;
import com.norconex.commons.lang.log.CountingConsoleAppender;

/**
 * @author Pascal Essiembre
 */
public class IdolCommitterTest {


    @Before
    public void setup() throws Exception {
    }

    @After
    public void teardown() throws Exception {
    }

    @Test
    public void testWriteRead() throws IOException {
        IdolCommitter outCommitter = new IdolCommitter();
        outCommitter.setQueueDir("C:\\FakeTestDirectory\\");
        outCommitter.setSourceContentField("sourceContentField");
        outCommitter.setTargetContentField("targetContentField");
        outCommitter.setSourceReferenceField("idTargetField");
        outCommitter.setTargetReferenceField("idTargetField");
        outCommitter.setKeepSourceContentField(true);
        outCommitter.setKeepSourceReferenceField(false);
        outCommitter.setQueueSize(100);
        outCommitter.setCommitBatchSize(50);
        outCommitter.setHost("fake.idol.host.com");
        outCommitter.setCfsPort(9100);
        outCommitter.setIndexPort(9001);
        outCommitter.setDatabaseName("Fake Database");
        outCommitter.addDreAddDataParam("aparam1", "avalue1");
        outCommitter.addDreAddDataParam("aparam2", "avalue2");
        outCommitter.addDreDeleteRefParam("dparam1", "dvalue1");
        outCommitter.addDreDeleteRefParam("dparam2", "dvalue2");
        System.out.println("Writing/Reading this: " + outCommitter);
        XMLConfigurationUtil.assertWriteRead(outCommitter);
    }
    
    @Test
    public void testValidation() throws IOException {
        CountingConsoleAppender appender = new CountingConsoleAppender();
        appender.startCountingFor(XMLConfigurationUtil.class, Level.WARN);
        try (Reader r = new InputStreamReader(getClass().getResourceAsStream(
                ClassUtils.getShortClassName(getClass()) + ".xml"))) {
            XMLConfigurationUtil.newInstance(r);
        } finally {
            appender.stopCountingFor(XMLConfigurationUtil.class);
        }
        Assert.assertEquals("Validation warnings/errors were found.", 
                0, appender.getCount());
    }    
}
