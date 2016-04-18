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

package org.rapidpm.hazelcast.hzdfs.directory.container.model;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;
import org.rapidpm.hazelcast.hzdfs.base.api.HZDirectory;

import java.io.IOException;

public class HZDirectoryDefaultImpl extends HZDirectoryAbstractBaseImpl implements DataSerializable {


  public HZDirectoryDefaultImpl(final HZDirectory parent, final String name) {
    super(parent, name);
    // set me as child
    if (parent != null) parent.addDirectory(this);
  }

  public HZDirectoryDefaultImpl() {
  }

  @Override
  public void writeData(final ObjectDataOutput out) throws IOException {
    out.writeUTF(path());
    out.writeUTF(name());
    final HZDirectory parent = getParent();
    if (parent instanceof HZDirectoryDefaultImpl) ((HZDirectoryDefaultImpl) parent).writeData(out);
  }

  @Override
  public void readData(final ObjectDataInput in) throws IOException {
    this.path = in.readUTF();
    this.name = in.readUTF();
    this.parent = new HZDirectoryDefaultImpl();
    ((HZDirectoryDefaultImpl) this.parent).readData(in);
  }
}
