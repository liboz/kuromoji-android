/**
 * Copyright © 2010-2018 Atilika Inc. and contributors (see CONTRIBUTORS.md)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.  A copy of the
 * License is distributed with this work in the LICENSE.md file.  You may
 * also obtain a copy of the License from
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kuromoji.core.compile;

import com.kuromoji.core.fst.Builder;
import com.kuromoji.core.io.ByteBufferIO;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class FSTCompiler implements Compiler {

    private final OutputStream output;
    private final String[] surfaces;

    public FSTCompiler(OutputStream output, List<String> surfaces) {
        this.output = output;
        this.surfaces = new HashSet<>(surfaces).toArray(new String[0]);
    }

    @Override
    public void compile() throws IOException {
        Arrays.sort(surfaces);

        Builder builder = new Builder();
        builder.build(surfaces, null) ;

        ByteBuffer fst = ByteBuffer.wrap(
            builder.getCompiler().getBytes()
        );

        ByteBufferIO.write(output, fst);
    }
}
