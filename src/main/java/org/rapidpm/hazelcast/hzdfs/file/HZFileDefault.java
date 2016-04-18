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

package org.rapidpm.hazelcast.hzdfs.file;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ILock;
import org.rapidpm.hazelcast.hzdfs.base.api.HZFile;
import org.rapidpm.hazelcast.hzdfs.base.model.HZFSAbstractNode;

public class HZFileDefault extends HZFSAbstractNode implements HZFile {

  private ILock lock;
  private HazelcastInstance hz;
  private byte[] data;

  public HZFileDefault() {
  }

  @Override
  public void writeFileData(final byte[] fileData) {
    this.data = fileData;
  }

  @Override
  public byte[] readFileData() {
    return data;
  }



  @Override
  public void delete() {
    //
  }

  @Override
  public void getAttributes() {
    //
  }


  @Override
  public void lock() {
    this.lock.lock();
  }

  @Override
  public void unLock() {
    this.lock.unlock();
  }

  @Override
  public void persist() {

  }

  @Override
  public void unPersist() {

  }


  //@Override
//  public void writeData(final ObjectDataOutput out) throws IOException {
//    out.writeUTF(this.uuid);
//    out.writeBoolean(this.isPersistent);
//    out.writeBoolean(this.isReadonly);
//    out.writeInt(this.userID);
//    out.writeInt(this.groupID);
//    out.writeByteArray(data);
//  }
//
//  //@Override
//  public void readData(final ObjectDataInput in) throws IOException {
//    this.uuid = in.readUTF();
//    this.isPersistent = in.readBoolean();
//    this.isReadonly = in.readBoolean();
//    this.userID = in.readInt();
//    this.groupID = in.readInt();
//    this.data = in.readByteArray();
//  }



}
