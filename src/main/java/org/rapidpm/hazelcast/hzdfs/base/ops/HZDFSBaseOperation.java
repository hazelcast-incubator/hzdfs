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

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.spi.AbstractOperation;
import com.hazelcast.spi.BackupAwareOperation;

import java.io.IOException;

public abstract class HZDFSBaseOperation
    extends AbstractOperation
    implements BackupAwareOperation {


  protected String objectName;

  public HZDFSBaseOperation() {
  }

  public HZDFSBaseOperation(final String objectName) {
    this.objectName = objectName;
  }

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
  protected void writeInternal(final ObjectDataOutput out) throws IOException {
    super.writeInternal(out);
    out.writeUTF(objectName);
  }

  @Override
  protected void readInternal(final ObjectDataInput in) throws IOException {
    super.readInternal(in);
    objectName = in.readUTF();
  }


}
