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

package com.kuromoji.jumandic;

import com.kuromoji.core.TokenizerBase;
import com.kuromoji.core.dict.Dictionary;
import com.kuromoji.jumandic.compile.DictionaryEntry;
import com.kuromoji.core.util.SimpleResourceResolver;
import com.kuromoji.core.viterbi.TokenFactory;
import com.kuromoji.core.viterbi.ViterbiNode;

import java.util.List;
import android.content.Context;

/**
 * A tokenizer based on the JUMAN DIC dictionary
 * <p>
 * See {@link Token} for details on the morphological features produced by this tokenizer
 * <p>
 * The following code example demonstrates how to use the Kuromoji tokenizer:
 * <pre>{@code
 * package com.kuromoji.core.example;
 * import com.kuromoji.core.jumandic.Token;
 * import com.kuromoji.core.jumandic.Tokenizer;
 * import java.util.List;
 * <p>
 * public class KuromojiExample {
 *     public static void main(String[] args) {
 *         Tokenizer tokenizer = new Tokenizer() ;
 *         List<Token> tokens = tokenizer.tokenize("お寿司が食べたい。");
 *         for (Token token : tokens) {
 *             System.out.println(token.getSurface() + "\t" + token.getAllFeatures());
 *         }
 *     }
 * }
 * }
 * </pre>
 */
public class Tokenizer extends TokenizerBase {

    /**
     * Construct a default tokenizer
     */
    public Tokenizer(Context context) {
        this(context, new Builder());
    }

    /**
     * Construct a customized tokenizer
     * <p>
     * See {@see com.kuromoji.core.jumandic.Tokenizer#Builder}
     */
    private Tokenizer(Context context, Builder builder) {
        configure(context, builder);
    }

    /**
     * Tokenizes the provided text and returns a list of tokens with various feature information
     * <p>
     * This method is thread safe
     *
     * @param text  text to tokenize
     * @return list of Token, not null
     */
    @Override
    public List<Token> tokenize(String text) {
        return createTokenList(text);
    }

    /**
     * Builder class for creating a customized tokenizer instance
     */
    public static class Builder extends TokenizerBase.Builder {

        /**
         * Creates a default builder
         */
        public Builder() {
            totalFeatures = DictionaryEntry.TOTAL_FEATURES;
            readingFeature = DictionaryEntry.READING_FEATURE;
            partOfSpeechFeature = DictionaryEntry.PART_OF_SPEECH_FEATURE;

            resolver = new SimpleResourceResolver();

            tokenFactory = new TokenFactory<Token>() {
                @Override
                public Token createToken(int wordId,
                                         String surface,
                                         ViterbiNode.Type type,
                                         int position,
                                         Dictionary dictionary) {
                    return new Token(wordId, surface, type, position, dictionary);
                }
            };
        }

        /**
         * Creates the custom tokenizer instance
         *
         * @return tokenizer instance, not null
         */
        @Override
        public Tokenizer build(Context context) {
            return new Tokenizer(context, this);
        }
    }
}
