/*
 * Copyright (C) 2013-2014 FSquaDRA
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.

 * Author(s): Yury Zhauniarovich
 */

package com.zhauniarovich.fsquadra.optionsparser;

import java.io.File;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

public class PathsValidator implements IParameterValidator {
    @Override
    public void validate(String name, String value) throws ParameterException {
        if (!(new File(name)).exists()) { 
            throw new ParameterException("The path [" + name + "] does not exist!");
        }
    }
}
