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
package com.kuromoji.core.buffer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class FeatureInfoMap {

    private Map<String, Integer> featureMap = new HashMap<>();

    private int maxValue = 0;

    public List<Integer> mapFeatures(String[] features) {
        List<Integer> posFeatureIds = new ArrayList<>();
        for (String feature : features) {
            if (featureMap.containsKey(feature)) {
                posFeatureIds.add(featureMap.get(feature));
            } else {
                featureMap.put(feature, maxValue);
                posFeatureIds.add(maxValue);
                maxValue++;
            }
        }
        return posFeatureIds;
    }

    public TreeMap<Integer, String> invert() {
        TreeMap<Integer, String> features = new TreeMap<>();

        for (String key : featureMap.keySet()) {
            features.put(featureMap.get(key), key);
        }

        return features;
    }

    public int getEntryCount() {
        return maxValue;
    }

    @Override
    public String toString() {
        return "FeatureInfoMap{" +
            "featureMap=" + featureMap +
            ", maxValue=" + maxValue +
            '}';
    }
}
