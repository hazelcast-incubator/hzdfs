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

package junit.org.rapidpm.hazelcast.hzdfs.hzfiles.v002;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.rapidpm.hazelcast.hzdfs.base.api.HZDFS;
import org.rapidpm.hazelcast.hzdfs.base.api.HZDirectory;
import org.rapidpm.hazelcast.hzdfs.base.api.HZFile;
import org.rapidpm.hazelcast.hzdfs.directory.container.model.HZDirectoryRootImpl;
import org.rapidpm.hazelcast.hzdfs.impl.fs.HZDFSBuilder;

import java.util.Collection;
import java.util.Optional;

import static org.rapidpm.hazelcast.hzdfs.impl.HZDFSConstants.HZDFS_DEFAULT_FS;

public class HZDFSDemo002 {


  public static final String NEXT_ROOT = "NextRoot";

  @Test
  @Ignore
  public void test001() throws Exception {

    final HZDFS hzdfs = HZDFSBuilder.newBuilder().build();
    final Optional<HZDirectory> hzRootDirectoryOptional = hzdfs.getRootDirectory(HZDFS_DEFAULT_FS);

    if (hzRootDirectoryOptional.isPresent()) {
      final HZDirectory hzRootDirectory = hzRootDirectoryOptional.get();
      final Collection<HZDirectory> hzDirectories = hzRootDirectory.listDirectories();
      Assert.assertTrue(hzDirectories.isEmpty());

      // add Directory

      hzRootDirectory.addDirectory(new HZDirectoryRootImpl(NEXT_ROOT));

      final Collection<HZDirectory> hzDirectories_A = hzRootDirectory.listDirectories();
      Assert.assertFalse(hzDirectories_A.isEmpty());




      final long directoryCount = hzDirectories
          .stream()
          .count();

      for (final HZDirectory hzDirectory : hzDirectories) {
        final Collection<HZDirectory> hzDirectoryCollection = hzDirectory.listDirectories();

      }


      printFiles(hzRootDirectory);
    }
  }

  private void printFiles(final HZDirectory hzRootDirectory) {
    final Collection<HZFile> hzFiles = hzRootDirectory.listFiles();
    hzFiles
        .stream()
        .forEach(f -> {
          System.out.println(" =============== ");
          System.out.println("f.nodeID() = " + f.nodeID());
          System.out.println("f.userID() = " + f.userID());
          System.out.println("f.groupID() = " + f.groupID());
          System.out.println("f.readFileData() = " + new String(f.readFileData()));
        });
  }
}
