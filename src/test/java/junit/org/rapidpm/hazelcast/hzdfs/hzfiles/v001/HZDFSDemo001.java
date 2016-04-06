/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package junit.org.rapidpm.hazelcast.hzdfs.hzfiles.v001;

import com.hazelcast.config.Config;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.test.HazelcastParallelClassRunner;
import com.hazelcast.test.HazelcastTestSupport;
import com.hazelcast.test.TestHazelcastInstanceFactory;
import com.hazelcast.test.annotation.ParallelTest;
import com.hazelcast.test.annotation.QuickTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.rapidpm.hazelcast.hzdfs.file.HZFileDefault;
import org.rapidpm.hazelcast.hzdfs.file.api.HZFile;

import java.util.Random;
import java.util.UUID;

@RunWith(HazelcastParallelClassRunner.class)
@Category({QuickTest.class, ParallelTest.class})
public class HZDFSDemo001 extends HazelcastTestSupport {

  static final String HZ_FILE_MAP = HZDFSDemo001.class.getSimpleName();


  static final int instanceCount = 3;
  static final Random rand = new Random();

  HazelcastInstance[] instances;

  @Before
  public void init() {
    TestHazelcastInstanceFactory factory = createHazelcastInstanceFactory(instanceCount);
    Config config = getConfig();
    instances = factory.newInstances(config);
  }

  @Test
  @Ignore
  public void hzdfsTest001() throws Exception {
    final HazelcastInstance hazelcastInstance = getInstance();

    final UUID uuid = UUID.randomUUID();
    final HZFile hzFileDefault = HZFileDefault.newBuilder()
        .withUuid(uuid.toString())
        .withGroupID(-1)
        .withUserID(-1)
        .withIsReadonly(false)
        .withIsPersistent(false)
//        .withLock(hazelcastInstance.getLock(uuid.toString()))
        .build();

    hzFileDefault.writeFileData("HelloWorld".getBytes());
    final IMap<String, HZFile> hzFileMap = hazelcastInstance.getMap(HZ_FILE_MAP);
    hzFileMap.put(hzFileDefault.nodeID(), hzFileDefault);

    final HZFile file = getHzFile(uuid.toString());
    Assert.assertEquals(hzFileDefault, file);
    Assert.assertEquals(new String(hzFileDefault.readFileData()), new String(file.readFileData()));

    hzFileDefault.writeFileData("HelloWorld - Again".getBytes());
    hzFileMap.put(hzFileDefault.nodeID(), hzFileDefault);

    Assert.assertEquals(new String(hzFileDefault.readFileData()), new String(getHzFile(uuid.toString()).readFileData()));
  }

  HazelcastInstance getInstance() {
    return instances[rand.nextInt(instanceCount)];
  }

  private HZFile getHzFile(final String nodeID) {
    return (HZFile) getInstance().getMap(HZ_FILE_MAP).get(nodeID);
  }
}
