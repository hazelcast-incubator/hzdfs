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

package org.rapidpm.hazelcast.hzdfs.base.ops;

import com.hazelcast.spi.AbstractOperation;
import com.hazelcast.spi.BackupAwareOperation;

public abstract class HZDFSBaseOperation
    extends AbstractOperation
    implements BackupAwareOperation {


  @Override
  public boolean shouldBackup() {
    return true;
  }

  @Override
  public int getSyncBackupCount() {
    return 1;
  }

  @Override
  public int getAsyncBackupCount() {
    return 0;
  }


  @Override
  public void run() throws Exception {

  }
}
