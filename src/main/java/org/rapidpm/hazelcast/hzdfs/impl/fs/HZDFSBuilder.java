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

package org.rapidpm.hazelcast.hzdfs.impl.fs;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.rapidpm.hazelcast.hzdfs.base.api.HZDFS;
import org.rapidpm.hazelcast.hzdfs.impl.HZDFSConstants;

public class HZDFSBuilder {


  private final String hzdfsName;

  private HZDFSBuilder(String name) {
    this.hzdfsName = name;
  }

  public static HZDFSBuilder newBuilder(final String hzdfsName) {
    if (hzdfsName == null) {
      return newBuilder();
    } else {
      return new HZDFSBuilder(hzdfsName);
    }
  }

  public static HZDFSBuilder newBuilder() {
    return new HZDFSBuilder(HZDFSConstants.HZDFS_DEFAULT_FS);
  }

  public HZDFS build() {

    final Config config = new Config()
        .setInstanceName(hzdfsName);
    final HazelcastInstance hazelcastInstance = Hazelcast.getOrCreateHazelcastInstance(config);


    final HZDFSDefault hzdfsDefault = new HZDFSDefault(hzdfsName, hazelcastInstance);

    return hzdfsDefault;
  }


}
